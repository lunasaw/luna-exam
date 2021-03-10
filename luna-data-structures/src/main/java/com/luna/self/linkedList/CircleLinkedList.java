package com.luna.self.linkedList;

/**
 * @author luna@mac
 * @className CircleLinkedList.java
 * @description TODO
 * @createTime 2021年02月17日 21:00:00
 */
public class CircleLinkedList {
    public static void main(String[] args) {
        // 测试一把看看构建环形链表，和遍历是否ok
        CircularLinkedList circularLinkedList = new CircularLinkedList();
        // 加入5个小孩节点
        circularLinkedList.add(5);
//        circularLinkedList.show();
        circularLinkedList.countNode(2, 2, 5);
    }
}

class CircularLinkedList {

    private Node3 node3;

    /**
     * 环形链表添加节点
     *
     * @param nums
     */
    public void add(int nums) {
        // nums 做一个数据校验
        if (nums < 1) {
            System.out.println("nums的值不正确");
            return;
        }
        Node3 curNode = null;
        // 使用for来创建我们的环形链表
        for (int i = 1; i <= nums; i++) {
            // 根据编号，创建小孩节点
            Node3 node = new Node3(i);
            // 如果是第一个小孩
            if (i == 1) {
                node3 = node;
                // 构成环
                node3.setNext(node);
                // 让curBoy指向第一个小孩
                curNode = node;
            } else {
                curNode.setNext(node);
                node.setNext(node3);
                curNode = node;
            }
        }
    }


    /**
     * 展示环形链表
     */
    public void show() {
        // 判断链表是否为空
        if (node3 == null) {
            System.out.println("没有任何小孩~~");
            return;
        }
        Node3 curNode = node3;
        while (curNode.getNext() != null) {
            System.out.printf("小孩的编号 %d \n", curNode.getNo());
            curNode = curNode.getNext();
        }
    }

    /**
     * 根据用户的输入，计算出小孩出圈的顺序
     *
     * @param startNo  表示从第几个小孩开始数数
     * @param countNum 表示数几下
     * @param nums     表示最初有多少小孩在圈中
     */
    public void countNode(int startNo, int countNum, int nums) {
        // 先对数据进行校验
        if (node3 == null || startNo < 1 || startNo > nums) {
            System.out.println("参数输入有误， 请重新输入");
            return;
        }
        Node3 curNode = node3;
        // 需求创建一个辅助指针(变量) helper , 事先应该指向环形链表的最后这个节点
        while (true) {
            if (curNode.getNext() == node3) {
                break;
            }
            curNode = curNode.getNext();
        }
        System.out.printf("当前node3：%d\n", node3.getNo());
        System.out.printf("当curNode：%d\n", curNode.getNo());
        for (int j = 0; j < startNo - 1; j++) {
            node3 = node3.getNext();
            curNode = curNode.getNext();
        }
        System.out.printf("当前node3：%d\n", node3.getNo());
        System.out.printf("当curNode：%d\n", curNode.getNo());
        while (true) {
            //说明圈中只有一个节点
            if (node3 == curNode) {
                break;
            }
            //让 node3 和 curNode 指针同时 的移动 countNum - 1
            for (int j = 0; j < countNum - 1; j++) {
                node3 = node3.getNext();
                curNode = curNode.getNext();
            }
            System.out.printf("小孩%d出圈\n", node3.getNo());
            node3 = node3.getNext();
            curNode.setNext(node3);
            System.out.println("=================");
            System.out.printf("当前node3：%d\n", node3.getNo());
            System.out.printf("当curNode：%d\n", curNode.getNo());
        }
        System.out.printf("最后留在圈中的小孩编号%d \n", node3.getNo());

    }
}

class Node3 {

    private int no;

    private Node3 next;

    public Node3(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Node3 getNext() {
        return next;
    }

    public void setNext(Node3 next) {
        this.next = next;
    }
}