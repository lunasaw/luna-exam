package com.luna.self.tree;

/**
 * @author luna@mac
 * @className TreeNode.java
 * @description TODO
 * @createTime 2021年03月02日 14:32:00
 */
public class TreeNode {

    public static void main(String[] args) {
        BinaryTreeNode hero1 = new BinaryTreeNode(7, "宋江");
        BinaryTreeNode hero2 = new BinaryTreeNode(3, "卢俊义");
        BinaryTreeNode hero3 = new BinaryTreeNode(10, "吴用");
        BinaryTreeNode hero4 = new BinaryTreeNode(12, "林冲");
        BinaryTreeNode hero5 = new BinaryTreeNode(5, "秦明");
        BinaryTreeNode hero6 = new BinaryTreeNode(1, "呼延灼");
        BinaryTreeNode hero7 = new BinaryTreeNode(9, "花荣");
        BinaryTreeNode hero8 = new BinaryTreeNode(2, "柴进");
        BinaryTree binaryTree = new BinaryTree(hero1);
        binaryTree.add(hero2);
        binaryTree.add(hero3);
        binaryTree.add(hero4);
        binaryTree.add(hero5);
        binaryTree.add(hero6);
        binaryTree.add(hero7);
        binaryTree.add(hero8);

        binaryTree.infixOrder();
        System.out.println("中序 ==========");

        binaryTree.delNode(3);
        System.out.println("中序 ==========");
        binaryTree.infixOrder();
        binaryTree.delNode(1);
        binaryTree.delNode(2);
        binaryTree.delNode(5);
        binaryTree.delNode(8);
        binaryTree.delNode(12);
        binaryTree.infixOrder();

    }

}

class BinaryTree {

    private BinaryTreeNode root;

    public BinaryTree(BinaryTreeNode root) {
        this.root = root;
    }

    public BinaryTreeNode getRoot() {
        return root;
    }

    public BinaryTree setRoot(BinaryTreeNode root) {
        this.root = root;
        return this;
    }

    public BinaryTreeNode search(int id) {
        if (root == null) {
            return null;
        } else {
            return this.root.search(id);
        }
    }


    public BinaryTreeNode searchParent(int id) {
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
            BinaryTreeNode targetNode = search(id);
            if (targetNode == null) {
                return;
            }
            // 如果当前树只有一个节点
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            }
            // 2. 删除叶子节点
            // 找到targetNode 的父节点
            BinaryTreeNode searchParent = searchParent(id);
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
                BinaryTreeNode rightMid = getRightMid(targetNode.getRight());
                targetNode.setId(rightMid.getId());
                targetNode.setName(rightMid.getName());
                System.out.println(rightMid);
            } else {
                // 删除只有一棵子树的节点
                // 如果targetNode 只有左子点
                if (targetNode.getLeft() != null) {
                    if (searchParent.getLeft().getId() == targetNode.getId()) {
                        // 且为父亲的左子节点
                        searchParent.setLeft(targetNode.getLeft());
                    } else {
                        // 且为父亲的右子节点
                        searchParent.setRight(targetNode.getLeft());
                    }
                } else {
                    // 如果targetNode 只有右子点
                    if (searchParent.getLeft().getId() == targetNode.getId()) {
                        // 且为父亲的左子节点
                        searchParent.setLeft(targetNode.getRight());
                    } else {
                        // 且为父亲的右子节点
                        searchParent.setRight(targetNode.getRight());
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
    public BinaryTreeNode getRightMid(BinaryTreeNode node) {
        BinaryTreeNode target = node;
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

    public BinaryTreeNode preFind(String name) {
        if (root != null) {
            return this.root.preFind(name);
        } else {
            System.out.println("二叉树为空");
            return null;
        }
    }

    public BinaryTreeNode infixFind(String name) {
        if (root != null) {
            return this.root.infixFind(name);
        } else {
            System.out.println("二叉树为空");
            return null;
        }
    }

    public BinaryTreeNode postFind(String name) {
        if (root != null) {
            return this.root.postFind(name);
        } else {
            System.out.println("二叉树为空");
            return null;
        }
    }

    public void add(BinaryTreeNode node) {
        if (root != null) {
            root.add(node);
        } else {
            System.out.println("二叉树为空");
        }
    }

}

class BinaryTreeNode {

    private int id;

    private String name;

    private BinaryTreeNode left = null;

    private BinaryTreeNode right = null;

    public BinaryTreeNode() {
    }

    public BinaryTreeNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public BinaryTreeNode setId(int id) {
        this.id = id;
        return this;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public BinaryTreeNode setLeft(BinaryTreeNode left) {
        this.left = left;
        return this;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public BinaryTreeNode setRight(BinaryTreeNode right) {
        this.right = right;
        return this;
    }

    public String getName() {
        return name;
    }

    public BinaryTreeNode setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "BinaryTreeNode{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    public BinaryTreeNode search(int id) {
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
    public BinaryTreeNode searchParent(int id) {
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
    public BinaryTreeNode preFind(String name) {
        BinaryTreeNode temp = null;
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
    public BinaryTreeNode infixFind(String name) {
        BinaryTreeNode temp = null;
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
    public BinaryTreeNode postFind(String name) {
        BinaryTreeNode temp = null;
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
    public void add(BinaryTreeNode node) {
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
    }
}
