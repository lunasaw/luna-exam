package com.luna.self.linkedList;

/**
 * @author luna@mac
 * @className SimpleLinkedList.java
 * @description TODO
 * @createTime 2021年02月16日 23:11:00
 */
public class SimpleLinkedList {

    public static void main(String[] args) {
        //进行测试
        //先创建节点
        Node hero1 = new Node(1, "宋江");
        Node hero2 = new Node(2, "卢俊义");
        Node hero3 = new Node(3, "吴用");
        Node hero4 = new Node(4, "林冲");

        SingleLinkedList singleLinkedList = new SingleLinkedList();

        singleLinkedList.addByEnd(hero1);
        singleLinkedList.addByEnd(hero2);
        singleLinkedList.addByHead(hero3);
        singleLinkedList.addByHead(hero4);
        singleLinkedList.display();

        singleLinkedList.delete(hero4);
        singleLinkedList.delete(hero1);
        singleLinkedList.display();

        hero3.name = "李四";
        singleLinkedList.update(hero3);
        singleLinkedList.display();

        Node hero5 = new Node(5, "王二");
        System.out.println(singleLinkedList.findNode(hero5));
    }

}

class SingleLinkedList {

    private Node node = new Node(0, "");

    public Node getNode() {
        return node;
    }

    /**
     * 单链表尾部添加结点
     *
     * @param node
     * @return
     */
    public void addByEnd(Node node) {
        Node temp = this.node;
        while (true) {
            //找到链表的最后
            if (temp.next == null) {
                break;
            }
            //如果没有找到最后, 将将temp后移
            temp = temp.next;
        }
        temp.next = node;
    }

    /**
     * 单链表头插法
     *
     * @param node
     */
    public void addByHead(Node node) {
        Node temp = getNode();
        node.next = temp.next;
        temp.next = node;
    }

    /**
     * 单链表删除结点
     *
     * @param node
     */
    public void delete(Node node) {
        Node temp = getNode();
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == node.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.next = temp.next.next;
        }
    }

    /**
     * 修改单链表
     *
     * @param node
     */
    public void update(Node node) {
        Node temp = getNode();
        boolean flag = false;
        while (temp.next != null) {
            if (temp.next.no == node.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.next.name = node.name;
        }
    }

    /**
     * 单链表查找结点
     *
     * @param node
     */
    public boolean findNode(Node node) {
        Node temp = getNode();
        while (temp.next != null) {
            if (temp.next.no == node.no) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    /**
     * 打印链表
     */
    public void display() {
        Node temp = getNode();
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }
}

/**
 * 定义HeroNode ， 每个HeroNode 对象就是一个节点
 */
class Node {

    public int no;
    public String name;
    /**
     * 指向下一个节点
     */
    public Node next;

    public Node(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
