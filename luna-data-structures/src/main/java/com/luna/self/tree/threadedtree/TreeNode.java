package com.luna.self.tree.threadedtree;

/**
 * @author luna@mac
 * @className TreeNode.java
 * @description TODO
 * @createTime 2021年03月02日 14:32:00
 */
public class TreeNode {

    public static void main(String[] args) {
        BinaryTreeNode hero1 = new BinaryTreeNode(1, "宋江");
        BinaryTreeNode hero2 = new BinaryTreeNode(2, "卢俊义");
        BinaryTreeNode hero3 = new BinaryTreeNode(3, "吴用");
        BinaryTreeNode hero4 = new BinaryTreeNode(4, "林冲");
        BinaryTreeNode hero5 = new BinaryTreeNode(5, "关胜");
        BinaryTree binaryTree = new BinaryTree(hero3);
        binaryTree.add(hero2);
        binaryTree.add(hero4);
        binaryTree.add(hero5);
        binaryTree.add(hero1);
        System.out.println("中序线索化二叉树");
        binaryTree.threadedMidNode(binaryTree.getRoot());
        binaryTree.threadMidList();

    }

}

class BinaryTree {

    private BinaryTreeNode root;

    /**
     * 需要指向当前结点的前驱结点  线索化的前驱  并不是树的父节点
     */
    private BinaryTreeNode preNode;

    public BinaryTreeNode getPreNode() {
        return preNode;
    }

    public BinaryTree setPreNode(BinaryTreeNode preNode) {
        this.preNode = preNode;
        return this;
    }

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

    /**
     * 线索化结点
     *
     * @param node 需要线索化的结点
     */
    public void threadedMidNode(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        // 线索化左子树
        threadedMidNode(node.getLeft());
        // 当前结点
        // 处理前驱结点
        if (node.getLeft() == null) {
            node.setLeft(preNode);
            node.setLeftType(1);
        }
        // 处理后继结点
        if (preNode != null && preNode.getRight() == null) {
            preNode.setRight(node);
            preNode.setRightType(1);
        }
        preNode = node;
        // 右子树
        threadedMidNode(node.getRight());
    }

    /**
     * 线索化中序遍历二叉树
     */
    public void threadMidList() {
        BinaryTreeNode node = root;
        while (node != null) {
            while (node.getLeftType() == 0) {
                node = node.getLeft();
            }

            //输出
            System.out.println(node);
            while (node.getRightType() == 1) {
                node = node.getRight();
                System.out.println(node);
            }

            node = node.getRight();
        }
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

    //1. 如果leftType == 0 表示指向的是左子树, 如果 1 则表示指向前驱结点
    //2. 如果rightType == 0 表示指向是右子树, 如果 1表示指向后继结点
    private int leftType;
    private int rightType;

    public int getLeftType() {
        return leftType;
    }

    public BinaryTreeNode setLeftType(int leftType) {
        this.leftType = leftType;
        return this;
    }

    public int getRightType() {
        return rightType;
    }

    public BinaryTreeNode setRightType(int rightType) {
        this.rightType = rightType;
        return this;
    }

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
        return "BinaryTreeNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    /**
     * 根据Id删除结点
     * 1.如果删除的节点是叶子节点，则删除该节点
     * 2.如果删除的节点是非叶子节点，则删除该子树
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
