package leetcode2024;

import java.util.Objects;
import java.util.Stack;

/**
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * <p>
 * 实现 MinStack_155 类:
 * <p>
 * MinStack () 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 */
public class MinStack_155 {

    Stack<Integer> stack;
    Stack<Integer> minStack;

    public MinStack_155() {
        stack = new Stack<>();
        minStack = new Stack<>();
        minStack.push(Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        MinStack_155 stack155 = new MinStack_155();
        stack155.push(0);
        stack155.push(1);
        stack155.push(0);

        stack155.getMin();
        stack155.pop();

        System.out.println(stack155.getMin());

    }

    public void push(int val) {
        stack.push(val);
        minStack.push(Math.min(minStack.peek(), val));
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}