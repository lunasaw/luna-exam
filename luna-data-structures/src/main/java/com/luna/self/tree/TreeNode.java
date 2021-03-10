package com.luna.self.tree;

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
        System.out.println("中序 ==========");
        binaryTree.infixOrder();
        System.out.println("后序 ==========");
        binaryTree.postOrder();
        System.out.println("前序 ==========");
        binaryTree.preOrder();

        System.out.println("中序 ==========");
        binaryTree.infixFind("林冲");
        System.out.println("后序 ==========");
        binaryTree.postFind("林冲");
        System.out.println("前序 ==========");
        System.out.println(binaryTree.preFind("林冲"));

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

    private int            id;

    private String         name;

    private BinaryTreeNode left  = null;

    private BinaryTreeNode right = null;

    public BinaryTreeNode() {}

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
