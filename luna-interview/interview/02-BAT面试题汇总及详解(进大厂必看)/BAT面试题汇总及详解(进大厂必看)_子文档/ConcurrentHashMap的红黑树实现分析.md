# ConcurrentHashMap的红黑树实现分析



### 红黑树

红黑树是一种特殊的二叉树，主要用它存储有序的数据，提供高效的数据检索，时间复杂度为O(lgn)，每个节点都有一个标识位表示颜色，红色或黑色，有如下5种特性：
1、每个节点要么红色，要么是黑色；
2、根节点一定是黑色的；
3、每个空叶子节点必须是黑色的；
4、如果一个节点是红色的，那么它的子节点必须是黑色的；
5、从一个节点到该节点的子孙节点的所有路径包含相同个数的黑色节点；

##### 结构示意图

![2184951-2b09c2c8a76f5d6d](ConcurrentHashMap的红黑树实现分析.assets/2184951-2b09c2c8a76f5d6d.png)

只要满足以上5个特性的二叉树都是红黑树，当有新的节点加入时，有可能会破坏其中一些特性，需要通过左旋或右旋操作调整树结构，重新着色，使之重新满足所有特性。

### ConcurrentHashMap红黑树实现

[《谈谈ConcurrentHashMap1.7和1.8的不同实现》](https://www.jianshu.com/p/e694f1e868ec)一文中已经提到，在1.8的实现中，当一个链表中的元素达到8个时，会调用`treeifyBin()`方法把链表结构转化成红黑树结构，实现如下：



```java
/**
 * Replaces all linked nodes in bin at given index unless table is
 * too small, in which case resizes instead.
 */
private final void treeifyBin(Node<K,V>[] tab, int index) {
    Node<K,V> b; int n, sc;
    if (tab != null) {
        if ((n = tab.length) < MIN_TREEIFY_CAPACITY)
            tryPresize(n << 1);
        else if ((b = tabAt(tab, index)) != null && b.hash >= 0) {
            synchronized (b) {
                if (tabAt(tab, index) == b) {
                    TreeNode<K,V> hd = null, tl = null;
                    for (Node<K,V> e = b; e != null; e = e.next) {
                        TreeNode<K,V> p =
                            new TreeNode<K,V>(e.hash, e.key, e.val,
                                              null, null);
                        if ((p.prev = tl) == null)
                            hd = p;
                        else
                            tl.next = p;
                        tl = p;
                    }
                    setTabAt(tab, index, new TreeBin<K,V>(hd));
                }
            }
        }
    }
}
```

从上述实现可以看出：并非一开始就创建红黑树结构，如果当前`Node`数组长度小于阈值`MIN_TREEIFY_CAPACITY`，默认为64，先通过扩大数组容量为原来的两倍以缓解单个链表元素过大的性能问题。

#### 红黑树构造过程

下面对红黑树的构造过程进行分析：
1、通过遍历`Node`链表，生成对应的`TreeNode`链表，其中`TreeNode`在实现上继承了`Node`类；



```ruby
class TreeNode<K,V> extends Node<K,V> {
    TreeNode<K,V> parent;  // red-black tree links
    TreeNode<K,V> left;
    TreeNode<K,V> right;
    TreeNode<K,V> prev;    
    // needed to unlink next upon deletion
    boolean red;
}
```

假设`TreeNode`链表如下，其中节点中的数值代表`hash`值：

![2184951-f53c864498c7f199](ConcurrentHashMap的红黑树实现分析.assets/2184951-f53c864498c7f199.png)

2、根据`TreeNode`链表初始化`TreeBin`类对象，`TreeBin`在实现上同样继承了`Node`类，所以初始化完成的`TreeBin`类对象可以保持在`Node`数组中；



```java
class TreeBin<K,V> extends Node<K,V> {
    TreeNode<K,V> root;
    volatile TreeNode<K,V> first;
    volatile Thread waiter;
    volatile int lockState;
    // values for lockState
    // set while holding write lock
    static final int WRITER = 1;
    // set when waiting for write lock
    static final int WAITER = 2; 
    // increment value for setting read lock
    static final int READER = 4; 
}
```

3、遍历`TreeNode`链表生成红黑树，一开始二叉树的根节点`root`为空，则设置链表中的第一个节点80为`root`，并设置其`red`属性为`false`，因为在红黑树的特性1中，明确规定根节点必须是黑色的；



```php
for (TreeNode<K,V> x = b, next; x != null; x = next) {
    next = (TreeNode<K,V>)x.next;
    x.left = x.right = null;
    if (r == null) {
        x.parent = null;
        x.red = false;
        r = x;
    }
    ...
```

二叉树结构：

![2184951-5d95c7e4bd22d60f](ConcurrentHashMap的红黑树实现分析.assets/2184951-5d95c7e4bd22d60f.png)

4、加入节点60，如果`root`不为空，则通过比较节点`hash`值的大小将新节点插入到指定位置，实现如下：



```csharp
K k = x.key;
int h = x.hash;
Class<?> kc = null;
for (TreeNode<K,V> p = r;;) {
    int dir, ph;
    K pk = p.key;
    if ((ph = p.hash) > h)
        dir = -1;
    else if (ph < h)
        dir = 1;
    else if ((kc == null &&
              (kc = comparableClassFor(k)) == null) ||
             (dir = compareComparables(kc, k, pk)) == 0)
        dir = tieBreakOrder(k, pk);
        TreeNode<K,V> xp = p;
    if ((p = (dir <= 0) ? p.left : p.right) == null) {
        x.parent = xp;
        if (dir <= 0)
            xp.left = x;
        else
            xp.right = x;
        r = balanceInsertion(r, x);
        break;
    }
}
```

其中`x`代表即将插入到红黑树的节点，`p`指向红黑树中当前遍历到的节点，从根节点开始递归遍历，`x`的插入过程如下：

1)、如果`x`的`hash`值小于`p`的`hash`值，则判断`p`的左节点是否为空，如果不为空，则把`p`指向其左节点，并继续和`p`进行比较，如果`p`的左节点为空，则把`x`指向的节点插入到该位置；

2)、如果`x`的`hash`值大于`p`的`hash`值，则判断`p`的右节点是否为空，如果不为空，则把`p`指向其右节点，并继续和`p`进行比较，如果`p`的右节点为空，则把`x`指向的节点插入到该位置；

3)、如果`x`的`hash`值和`p`的`hash`值相等，怎么办？
解决：首先判断节点中的`key`对象的类是否实现了`Comparable`接口，如果实现`Comparable`接口，则调用`compareTo`方法比较两者`key`的大小，但是如果`key`对象没有实现`Comparable`接口，或则`compareTo`方法返回了0，则继续调用`tieBreakOrder`方法计算`dir`值，`tieBreakOrder`方法实现如下：



```dart
static int tieBreakOrder(Object a, Object b) {
    int d;
    if (a == null || b == null ||
        (d = a.getClass().getName().
         compareTo(b.getClass().getName())) == 0)
        d = (System.identityHashCode(a) <= System.identityHashCode(b) ?
             -1 : 1);
    return d;
}
```

最终比较`key`对象的默认`hashCode()`方法的返回值，因为`System.identityHashCode(a)`调用的是对象`a`默认的`hashCode()`；

插入节点60之后的二叉树：

![2184951-e30f413906e3727a](ConcurrentHashMap的红黑树实现分析.assets/2184951-e30f413906e3727a.png)

5、当有新节点加入时，可能会破坏红黑树的特性，需要执行`balanceInsertion()`方法调整二叉树，使之重新满足特性，方法中的变量`xp`指向`x`的父节点，`xpp`指向`xp`父节点，`xppl`和`xppr`分别指向`xpp`的左右子节点，`balanceInsertion()`方法首先会把新加入的节点设置成红色。

①、加入节点60之后，此时`xp`指向节点80，其父节点为空，直接返回。



```kotlin
if ((xp = x.parent) == null) {
    x.red = false;
    return x;
}
else if (!xp.red || (xpp = xp.parent) == null)
    return root;
```

调整之后的二叉树：

![2184951-54471494cd85c1d0](ConcurrentHashMap的红黑树实现分析.assets/2184951-54471494cd85c1d0.png)

②、加入节点50，二叉树如下：

![2184951-f3a62b35ea0ec208](ConcurrentHashMap的红黑树实现分析.assets/2184951-f3a62b35ea0ec208.png)

继续执行`balanceInsertion()`方法调整二叉树，此时节点50的父节点60是左儿子，走如下逻辑：



```php
if (xp == (xppl = xpp.left)) {
    if ((xppr = xpp.right) != null && xppr.red) {
        xppr.red = false;
        xp.red = false;
        xpp.red = true;
        x = xpp;
    }
    else {
        if (x == xp.right) {
            root = rotateLeft(root, x = xp);
            xpp = (xp = x.parent) == null ? null : xp.parent;
        }
        if (xp != null) {
            xp.red = false;
            if (xpp != null) {
                xpp.red = true;
                root = rotateRight(root, xpp);
            }
        }
    }
}
```

根据上述逻辑，把节点60设置成黑色，把节点80设置成红色，并对节点80执行右旋操作，右旋实现如下：



```php
static <K,V> TreeNode<K,V> rotateRight(TreeNode<K,V> root,
                                       TreeNode<K,V> p) {
    TreeNode<K,V> l, pp, lr;
    if (p != null && (l = p.left) != null) {
        if ((lr = p.left = l.right) != null)
            lr.parent = p;
        if ((pp = l.parent = p.parent) == null)
            (root = l).red = false;
        else if (pp.right == p)
            pp.right = l;
        else
            pp.left = l;
        l.right = p;
        p.parent = l;
    }
    return root;
}
```

右旋之后的红黑树如下：

![2184951-bf45215356cc088b](ConcurrentHashMap的红黑树实现分析.assets/2184951-bf45215356cc088b.png)

③、加入节点70，二叉树如下：

![2184951-0895baf9035af95e](ConcurrentHashMap的红黑树实现分析.assets/2184951-0895baf9035af95e.png)

继续执行`balanceInsertion()`方法调整二叉树，此时父节点80是个右儿子，节点70是左儿子，且叔节点50不为空，且是红色的，则执行如下逻辑：



```csharp
if (xppl != null && xppl.red) {
    xppl.red = false;
    xp.red = false;
    xpp.red = true;
    x = xpp;
}
```

此时二叉树如下：

![2184951-418a7d0fc3ce9bbc](ConcurrentHashMap的红黑树实现分析.assets/2184951-418a7d0fc3ce9bbc.png)

此时`x`指向`xpp`，即节点60，继续循环处理`x`，设置其颜色为黑色，最终二叉树如下：

![2184951-868bf4b005648354](ConcurrentHashMap的红黑树实现分析.assets/2184951-868bf4b005648354.png)

④、加入节点20，二叉树变化如下：

![2184951-982cdee76bce2126](ConcurrentHashMap的红黑树实现分析.assets/2184951-982cdee76bce2126.png)

因为节点20的父节点50是一个黑色的节点，不需要进行调整；

⑤、加入节点65，二叉树变化如下：

![2184951-b7c069d41e93f368](ConcurrentHashMap的红黑树实现分析.assets/2184951-b7c069d41e93f368.png)

对节点80进行右旋操作。

⑥、加入节点40，二叉树变化如下：

![2184951-8f56b424033d5a45](ConcurrentHashMap的红黑树实现分析.assets/2184951-8f56b424033d5a45.png)

1、对节点20执行左旋操作；
2、对节点50执行右旋操作；

最后加入节点10，二叉树变化如下：

![2184951-6834543be5b23f88](ConcurrentHashMap的红黑树实现分析.assets/2184951-6834543be5b23f88.png)

重新对节点进行着色，到此为止，红黑树已经构造完成；