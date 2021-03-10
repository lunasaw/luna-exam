package com.luna.self.linkedList;

/**
 * @author luna@mac
 * @className DoubleLinkedList.java
 * @description TODO
 * @createTime 2021年02月17日 15:59:00
 */
public class DoubleLinkedList {

    public static void main(String[] args) {
        //进行测试
        //先创建节点
        Node2 hero1 = new Node2(1, "宋江");
        Node2 hero2 = new Node2(2, "卢俊义");
        Node2 hero3 = new Node2(3, "吴用");
        Node2 hero4 = new Node2(4, "公孙胜");
        Node2 hero5 = new Node2(5, "关胜");
        Node2 hero6 = new Node2(6, "林冲");

        ComplexLinkedList complexLinkedList = new ComplexLinkedList();

        complexLinkedList.addByEnd(hero1);
        complexLinkedList.displayPre(hero1);
        complexLinkedList.displayNext(hero1);
        complexLinkedList.addByEnd(hero2);
        complexLinkedList.displayPre(hero2);
        complexLinkedList.displayNext(hero2);
        complexLinkedList.addByHead(hero3);
        complexLinkedList.displayPre(hero3);
        complexLinkedList.displayNext(hero3);
        complexLinkedList.displayPre(hero1);
        complexLinkedList.displayNext(hero1);
        complexLinkedList.addByHead(hero4);
        complexLinkedList.displayPre(hero3);
        complexLinkedList.displayNext(hero3);
        complexLinkedList.displayPre(hero4);
        complexLinkedList.displayNext(hero4);
        complexLinkedList.display();

        complexLinkedList.addByEnd(hero5);
        complexLinkedList.addByHead(hero6);
        System.out.println("=====================");
        complexLinkedList.delete(hero4);
        complexLinkedList.delete(hero1);
        complexLinkedList.displayPre(hero3);
        complexLinkedList.displayNext(hero3);
        complexLinkedList.displayPre(hero2);
        complexLinkedList.displayNext(hero2);
        complexLinkedList.delete(hero5);
        complexLinkedList.display();
        System.out.println("=====================");
        hero3.name = "李四";
        complexLinkedList.update(hero3);
        complexLinkedList.display();

        Node2 herox = new Node2(5, "王二");
        System.out.println(complexLinkedList.findNode(herox));
    }

}

class ComplexLinkedList {

    private Node2 node2 = new Node2(0, "");

    public Node2 getNode2() {
        return node2;
    }

    /**
     * 双向链表尾插法
     *
     * @param node2
     */
    public void addByEnd(Node2 node2) {
        Node2 temp = getNode2();
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node2;
        node2.pre = temp;
    }

    /**
     * 双向链表头插法
     *
     * @param node2
     */
    public void addByHead(Node2 node2) {
        Node2 temp = getNode2();
        node2.next = temp.next;
        node2.pre = temp;
        temp.next.pre = node2;
        temp.next = node2;
    }

    /**
     * 双向链表删除节点
     *
     * @param node2
     */
    public void delete(Node2 node2) {
        Node2 temp = getNode2();
        while (temp != null) {
            if (temp.no == node2.no) {
                temp.pre.next = temp.next;
                if (temp.next != null) {
                    temp.next.pre = temp.pre;
                }
                break;
            }
            temp = temp.next;
        }
    }

    /**
     * 双向链表遍历
     */
    public void display() {
        Node2 temp = getNode2();
        while (temp != null) {
            System.out.println(temp);
            temp = temp.next;
        }
    }

    public void displayPre(Node2 node2) {
        Node2 temp = getNode2();
        while (temp != null) {
            if (temp.no == node2.no) {
                System.out.println(node2.name + " 的 pre:" + node2.pre);
            }
            temp = temp.next;
        }
    }

    public void displayNext(Node2 node2) {
        Node2 temp = getNode2();
        while (temp != null) {
            if (temp.no == node2.no) {
                System.out.println(node2.name + " 的 next:" + node2.next);
            }
            temp = temp.next;
        }
    }

    /**
     * 双向链表更新
     *
     * @param node2
     */
    public void update(Node2 node2) {
        Node2 temp = getNode2();
        while (temp != null) {
            if (temp.no == node2.no) {
                temp.name = node2.name;
            }
            temp = temp.next;
        }
    }

    /**
     * 双向链表查找节点
     *
     * @param node2
     * @return
     */
    public boolean findNode(Node2 node2) {
        Node2 temp = getNode2();
        while (temp != null) {
            if (temp.no == node2.no) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
}

class Node2 {

    public int no;
    public String name;
    /**
     * 指向下一个节点, 默认为null
     */
    public Node2 next;
    /**
     * 指向前一个节点, 默认为null
     */
    public Node2 pre;

    public Node2(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}