package com.luna.self.tree.avl;

/**
 * @Package: avl
 * @ClassName: AvlTree
 * @Author: luna
 * @CreateTime: 2021/3/16 20:35
 * @Description:
 */
public class AvlTree {

    public static void main(String[] args) {
        int[] array = new int[] {4, 3, 6, 5, 7, 8};
        BinaryAvlTree binaryAvlTree = new BinaryAvlTree(null);
        for (int i : array) {
            binaryAvlTree.add(new AvlNode(i, ""));
        }
        binaryAvlTree.infixOrder();

        System.out.println("未平衡前：" + binaryAvlTree.getRoot().getHeight()); // 4
        System.out.println("未平衡前左子树高度：" + binaryAvlTree.getRoot().getLeft().getHeight()); // １
        System.out.println("未平衡前右子树高度：" + binaryAvlTree.getRoot().getRight().getHeight()); // ３
        // 右＞左+1了需要 往左旋转

        int[] array2 = new int[] {10, 12, 8, 9, 7, 6};
        BinaryAvlTree binaryAvlTree2 = new BinaryAvlTree(null);
        for (int i : array2) {
            binaryAvlTree2.add(new AvlNode(i, ""));
        }
        binaryAvlTree2.infixOrder();

        System.out.println("平衡：" + binaryAvlTree2.getRoot().getHeight()); // 4
        System.out.println("平衡左子树高度：" + binaryAvlTree2.getRoot().getLeft().getHeight()); // 3
        System.out.println("平衡右子树高度：" + binaryAvlTree2.getRoot().getRight().getHeight()); // 1
        // 左＞右+1了需要 往右旋转

        int[] array3 = {10, 11, 7, 6, 8, 9};
        BinaryAvlTree binaryAvlTree3 = new BinaryAvlTree(null);
        for (int i : array3) {
            binaryAvlTree3.add(new AvlNode(i, ""));
        }
        binaryAvlTree3.infixOrder();
        System.out.println("平衡：" + binaryAvlTree3.getRoot().getHeight()); // 4
        System.out.println("平衡左子树高度：" + binaryAvlTree3.getRoot().getLeft().getHeight()); // 3
        System.out.println("平衡右子树高度：" + binaryAvlTree3.getRoot().getRight().getHeight()); // 1
    }

}

class BinaryAvlTree {

    private AvlNode root;

    public BinaryAvlTree(AvlNode root) {
        this.root = root;
    }

    public AvlNode getRoot() {
        return root;
    }

    public BinaryAvlTree setRoot(AvlNode root) {
        this.root = root;
        return this;
    }

    public AvlNode search(int id) {
        if (root == null) {
            return null;
        } else {
            return this.root.search(id);
        }
    }

    public AvlNode searchParent(int id) {
        if (root == null) {
            return null;
        } else {
            return this.root.searchParent(id);
        }
    }

    public void delNode(int id) {
        if (root == null) {
            return;
        } else {
            // 1. 需求先去找到要删除的节点
            AvlNode targetNode = search(id);
            if (targetNode == null) {
                return;
            }
            // 如果当前树只有一个节点
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            }
            // 2. 删除叶子节点
            // 找到targetNode 的父节点
            AvlNode searchParent = searchParent(id);
            // 如果是叶子节点
            if (targetNode.getLeft() == null && targetNode.getRight() == null) {
                // 判断targetNode 属于父节点的左还是右
                if (searchParent.getLeft() != null && searchParent.getLeft().getId() == targetNode.getId()) {
                    // 左就置空
                    searchParent.setLeft(null);
                } else if (searchParent.getRight() != null && searchParent.getRight().getId() == targetNode.getId()) {
                    // 右置空
                    searchParent.setRight(null);
                }
                // 删除有两颗子树的节点
            } else if (targetNode.getLeft() != null && targetNode.getRight() != null) {
                AvlNode rightMid = getRightMid(targetNode.getRight());
                targetNode.setId(rightMid.getId());
                targetNode.setName(rightMid.getName());
            } else {
                // 删除只有一棵子树的节点
                // 如果targetNode 只有左子点
                if (targetNode.getLeft() != null) {
                    if (searchParent != null) {
                        // 如果父节点不为空
                        if (searchParent.getLeft().getId() == targetNode.getId()) {
                            // 且为父亲的左子节点
                            searchParent.setLeft(targetNode.getLeft());
                        } else {
                            // 且为父亲的右子节点
                            searchParent.setRight(targetNode.getLeft());
                        }
                    } else {
                        root = targetNode.getLeft();
                    }

                } else {
                    // 如果targetNode 只有右子点
                    if (searchParent != null) {
                        // 如果父节点不为空
                        if (searchParent.getLeft().getId() == targetNode.getId()) {
                            // 且为父亲的左子节点
                            searchParent.setLeft(targetNode.getRight());
                        } else {
                            // 且为父亲的右子节点
                            searchParent.setRight(targetNode.getRight());
                        }
                    } else {
                        root = targetNode.getRight();
                    }
                }
            }
        }
    }

    /**
     * 返回当前树的最小节点值 并删除最小节点
     *
     * @param node
     */
    public AvlNode getRightMid(AvlNode node) {
        AvlNode target = node;
        // 循环查找左节点 底下就是最小值
        while (target.getLeft() != null) {
            target = target.getLeft();
        }
        // target 指向最小节点 删除它
        delNode(target.getId());
        // 返回当前节点值
        return target;
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(int id) {
        if (root != null) {
            if (root.getId() == id) {
                root = null;
            } else {
                root.delete(id);
            }
        } else {
            System.out.println("二叉树为空");
        }
    }

    public void preOrder() {
        if (root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空");
        }
    }

    public void infixOrder() {
        if (root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空");
        }
    }

    public void postOrder() {
        if (root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空");
        }
    }

    public AvlNode preFind(String name) {
        if (root != null) {
            return this.root.preFind(name);
        } else {
            System.out.println("二叉树为空");
            return null;
        }
    }

    public AvlNode infixFind(String name) {
        if (root != null) {
            return this.root.infixFind(name);
        } else {
            System.out.println("二叉树为空");
            return null;
        }
    }

    public AvlNode postFind(String name) {
        if (root != null) {
            return this.root.postFind(name);
        } else {
            System.out.println("二叉树为空");
            return null;
        }
    }

    public void add(AvlNode node) {
        if (root != null) {
            root.add(node);
        } else {
            root = node;
        }
    }

}

class AvlNode {

    private int     id;

    private String  name;

    private AvlNode left  = null;

    private AvlNode right = null;

    public AvlNode() {}

    public AvlNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public AvlNode setId(int id) {
        this.id = id;
        return this;
    }

    public AvlNode getLeft() {
        return left;
    }

    public AvlNode setLeft(AvlNode left) {
        this.left = left;
        return this;
    }

    public AvlNode getRight() {
        return right;
    }

    public AvlNode setRight(AvlNode right) {
        this.right = right;
        return this;
    }

    public String getName() {
        return name;
    }

    public AvlNode setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "BinaryTreeNode{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    /**
     * 返回当前节点的高度 该节点的度为节点树的高度
     */
    public int getHeight() {
        return Math.max(left == null ? 0 : left.getHeight(), right == null ? 0 : right.getHeight()) + 1;
    }

    /**
     * 左旋 往右平移
     */
    private void leftRotate() {
        // 创建新节点 以当前跟节点的值创建
        AvlNode avlNode = new AvlNode(id, name);
        // 把新节点的左子树设置为当前节点的左子树
        avlNode.left = left;
        // 把新节点的右子树设置为当前节点的右子树的左子树
        avlNode.right = right.left;
        // 把当前节点的值的替换为右子节点的值
        id = right.id;
        name = right.name;
        // 把当前节点的右子树设置为当前节点右子树的右子树
        right = right.right;
        // 把当前节点的左子树(左子节点)设置成新的节点
        left = avlNode;
    }

    public void rightRotate() {
        // 创建新节点 以当前跟节点的值创建
        AvlNode avlNode = new AvlNode(id, name);
        // 把新节点的右子树设置为当前节点的右子树
        avlNode.right = right;
        // 把新节点的左子树设置为当前节点的左子树的右子树
        avlNode.left = left.right;
        // 把当前节点的值的替换为左子节点的值
        id = left.id;
        name = left.name;
        // 把当前节点的左子树设置为当前节点左子树的左子树
        left = left.left;
        // 把当前节点的右子树(右子节点)设置成新的节点
        right = avlNode;
    }

    public int leftHeight() {
        if (left == null) {
            return 0;
        } else {
            return left.getHeight();
        }
    }

    public int rightHeight() {
        if (right == null) {
            return 0;
        } else {
            return right.getHeight();
        }
    }

    public AvlNode search(int id) {
        if (id == this.getId()) {
            return this;
        } else if (id <= this.id) {
            if (this.left != null) {
                return this.left.search(id);
            } else {
                return null;
            }
        } else {
            if (this.right != null) {
                return this.right.search(id);
            } else {
                return null;
            }
        }
    }

    /**
     * 查找父节点
     *
     * @param id
     */
    public AvlNode searchParent(int id) {
        if ((this.left != null && this.left.id == id) || (this.right != null && this.right.id == id)) {
            return this;
        } else {
            // 查找值小于当前节点值 并且当前节点的左子节点不为空
            if (id < this.id && this.left != null) {
                // 左子树递归查找
                return this.left.searchParent(id);
            } else if (id > this.id && this.right != null) {
                // 右子树递归查找
                return this.right.searchParent(id);
            } else {
                // 没有父节点
                return null;
            }
        }
    }

    /**
     * 根据Id删除结点 1.如果删除的节点是叶子节点，则删除该节点 2.如果删除的节点是非叶子节点，则删除该子树
     *
     * @param id
     */
    public void delete(int id) {
        if (this.left != null && left.id == id) {
            this.left = null;
            return;
        }
        if (this.right != null && right.id == id) {
            this.right = null;
            return;
        }
        if (this.left != null) {
            this.left.delete(id);
        }
        if (this.right != null) {
            this.right.delete(id);
        }
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }

    /**
     * 前序查找
     *
     * @param name
     * @return
     */
    public AvlNode preFind(String name) {
        AvlNode temp = null;
        if (this.name.equals(name)) {
            System.out.println("找到：" + this);
            temp = this;
        }
        if (this.left != null) {
            temp = this.left.preFind(name);
        }
        if (this.right != null) {
            temp = this.right.preFind(name);
        }
        return temp;
    }

    /**
     * 中序查找
     *
     * @param name
     * @return
     */
    public AvlNode infixFind(String name) {
        AvlNode temp = null;
        if (this.left != null) {
            temp = this.left.infixFind(name);
        }
        if (this.name.equals(name)) {
            System.out.println("找到：" + this);
            temp = this;
        }
        if (this.right != null) {
            temp = this.right.infixFind(name);
        }
        return temp;
    }

    /**
     * 后序查找
     *
     * @param name
     * @return
     */
    public AvlNode postFind(String name) {
        AvlNode temp = null;
        if (this.left != null) {
            temp = this.left.postFind(name);
        }
        if (this.right != null) {
            temp = this.right.postFind(name);
        }
        if (this.name.equals(name)) {
            System.out.println("找到：" + this);
            temp = this;
        }
        return temp;
    }

    /**
     * 添加结点
     *
     * @param node
     */
    public void add(AvlNode node) {
        if (node.getId() < this.id) {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }

        // 当添加为接节点后 右子树的高度 - 左子树的高度 ＞ １ 则需要左旋转
        if (rightHeight() - leftHeight() > 1) {
            // 如果右子树的左子树高度大于大右子树的左子树高度
            if (right != null && right.getHeight() > right.rightHeight()) {
                // 先对右子结点右旋
                right.rightRotate();
                // 然后对当前结点左旋
                leftRotate();
            } else {
                // 直接左旋转
                leftRotate();
                return;
            }
        } else if (leftHeight() - rightHeight() > 1) {
            // 如果它的左子树的右子树高度大于它的左子树的高度
            if (left != null && left.rightHeight() > left.leftHeight()) {
                // 先对当前结点的左结点(左子树)->左旋转
                left.leftRotate();
                // 再对当前结点进行右旋转
                rightRotate();
            } else {
                // 直接进行右旋转即可
                rightRotate();
            }
        }
    }
}