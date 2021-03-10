package com.luna.self.stack;

/**
 * @author luna@mac
 * @className ArrayStack.java
 * @description TODO
 * @createTime 2021年02月18日 18:13:00
 */
public class ArrayStack {

    public static void main(String[] args) {
        Stack stack = new Stack(5);
        stack.push(4);
        stack.push(1);
        stack.push(3);
        stack.push(2);
        stack.push(5);
        stack.push(0);
        stack.display();
        System.out.println("============");
        Calculator calculator = new Calculator("7*2+3*4");
        calculator.scanExpression();
        System.out.println(calculator.getNumberStack());
        System.out.println(calculator.getOperatorStack());
        System.out.println(calculator.scanStack());
    }

}

class Calculator {

    private String expression = "";

    private Stack numberStack = new Stack(10);

    private Stack operatorStack = new Stack(10);


    public Calculator(String expression) {
        this.expression = expression;
    }

    public int getResult() {
        this.scanExpression();
        return this.scanStack();
    }

    /**
     * 开始while循环的扫描expression
     */
    public void scanExpression() {
        char[] chars = expression.toCharArray();
        // 定义入栈数字串拼接
        String temp = "";
        for (char c : chars) {
            if (Calculator.isOperator(c)) {
                // 是字符则入数字栈，并初始化temp
                numberStack.push(Integer.parseInt(temp));
                temp = "";
                if (!operatorStack.isEmpty()) {
                    // 待入栈操作符号优先级小于stack优先级 则取出数字栈中两个数字进行计算
                    if (Calculator.priority(operatorStack.peek()) >= Calculator.priority(c)) {
                        int num1 = numberStack.pop();
                        int num2 = numberStack.pop();
                        int operator = operatorStack.pop();
                        int result = Calculator.calculator(num1, num2, operator);
                        // 将结果入栈
                        numberStack.push(result);
                    }
                    // 待入栈操作符号优先级大于stack优先级
                }
                operatorStack.push(c);
            } else {
                // 是数字则拼接
                temp += c;
            }
        }
        // 最后一个字符数 直接入数字栈
        numberStack.push(Integer.parseInt(temp));
    }

    /**
     * 扫描栈计算
     */
    public int scanStack() {
        while (!operatorStack.isEmpty()) {
            int num1 = numberStack.pop();
            int num2 = numberStack.pop();
            int operator = operatorStack.pop();
            int result = Calculator.calculator(num1, num2, operator);
            // 将结果入栈
            numberStack.push(result);
        }
        return numberStack.peek();
    }


    /**
     * 返回运算符的优先级，优先级是程序员来确定, 优先级使用数字表示
     * 数字越大，则优先级就越高.
     *
     * @param operator
     * @return
     */
    public static int priority(int operator) {
        // 假定目前的表达式只有 +, - , * , /
        if (operator == '*' || operator == '/') {
            return 1;
        } else if (operator == '+' || operator == '-') {
            return 0;
        } else {
            return -1;
        }
    }


    /**
     * 判断是不是一个运算符
     *
     * @param val
     * @return
     */
    public static boolean isOperator(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    /**
     * 计算方法
     *
     * @param num1
     * @param num2
     * @param operator
     * @return
     */
    public static int calculator(int num1, int num2, int operator) {
        // res 用于存放计算的结果
        int res = 0;
        switch (operator) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1;
                // 注意顺序
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Stack getNumberStack() {
        return numberStack;
    }

    public void setNumberStack(Stack numberStack) {
        this.numberStack = numberStack;
    }

    public Stack getOperatorStack() {
        return operatorStack;
    }

    public void setOperatorStack(Stack operatorStack) {
        this.operatorStack = operatorStack;
    }
}

class Stack {
    private int maxSize;
    private int[] stack;
    private int top = -1;

    public Stack(int maxSize) {
        this.stack = new int[maxSize];
        this.maxSize = maxSize;
    }

    public void push(int num) {
        if (!isFull()) {
            stack[++top] = num;
        }
    }

    /**
     * 增加一个方法，可以返回当前栈顶的值, 但是不是真正的pop
     *
     * @return
     */
    public int peek() {
        return stack[top];
    }


    public int pop() {
        if (!isEmpty()) {
            return stack[top--];
        }
        return top;
    }

    /**
     * 栈满
     *
     * @return
     */
    public boolean isFull() {
        return top == maxSize - 1;
    }

    /**
     * 栈空
     *
     * @return
     */
    public boolean isEmpty() {
        return top == -1;
    }

    public void display() {
        if (isEmpty()) {
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = top; i >= 0; i--) {
            stringBuilder.append("stack[" + i + "]=" + stack[i] + "\n");
        }
        return stringBuilder.toString();
    }
}