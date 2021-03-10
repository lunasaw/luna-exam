package com.luna.self.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author luna@mac
 * @className CalculatorNotation.java
 * @description TODO
 * ### 中缀表达式求后缀表达式
 * <p>
 * 给出一个中缀表达式如下：
 * a+b*c-(d+e)
 * 第一步：按照运算符的优先级对所有的运算单位加括号，
 * 式子变成了：((a+(b*c))-(d+e))
 * 第二步：转换前缀与后缀表达式
 * 前缀：把运算符号移动到对应的括号前面
 * 则变成了：-( +(a *(bc)) +(de))
 * 把括号去掉：-+a*bc+de  前缀式子出现
 * 后缀：把运算符号移动到对应的括号后面
 * 则变成了：((a(bc)* )+ (de)+ )-
 * 把括号去掉：abc*+de+-  后缀式子出现
 * <p>
 * <p>
 * <p>
 * 后缀表达式逆向求解中缀表达式
 * <p>
 * 1 2 3 + 4 *5 - +
 * 基本思路和上面的一样：递归，碰到操作符就进入递归。
 * 从左往右扫描先碰到+号，取+号前面两个操作数：2，3 得到：2+3.
 * 继续往下扫碰到*号，取4 和2+3 得到：（2+3）*4
 * -号，取（2+3）*4和5得到：：（2+3）*4-5
 * +号：取（2+3）*4-5和1得到：：1+（2+3）*4-5
 * @createTime 2021年02月19日 18:50:00
 */


public class CalculatorNotation {

    public static void main(String[] args) {
        String expression = "1+((2+3)*4)-5";
        List<String> list = PolandNotation.getString(expression);

        System.out.println(list);
        List<String> strings = PolandNotation.middleToReverse(expression);
        System.out.println(strings);

        int calculate = PolandNotation.calculate(strings);
        System.out.println(calculate);

    }

}

class PolandNotation {

    /**
     * 中缀转后缀（逆波兰表达式）
     *
     * @param middle
     */
    public static List<String> middleToReverse(String middle) {
        List<String> string = getString(middle);
        // 符号栈
        Stack<String> s1 = new Stack<>();
        // 数字栈
        List<String> s2 = new Stack<>();
        for (String item : string) {
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                //如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while (!"(".equals(s1.peek())) {
                    s2.add(s1.pop());
                }
                s1.pop();
            } else {
                //当item的优先级小于等于s1栈顶运算符, 将s1栈顶的运算符弹出并加入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    s2.add(s1.pop());
                }
                //还需要将item压入栈
                s1.push(item);
            }
        }

        //将s1中剩余的运算符依次弹出并加入s2
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        //注意因为是存放到List, 因此按顺序输出就是对应的后缀表达式对应的List
        return s2;
    }


    /**
     * 将一个逆波兰表达式 依次将数据和运算符 放入到 ArrayList中
     *
     * @param expresion
     * @return
     */
    public static List<String> getString(String expresion) {
        char[] chars = expresion.toCharArray();
        List<String> temp = new ArrayList<>();
        String num = "";
        for (char c : chars) {
            if (c < 48 || c > 57) {
                if (num != "") {
                    temp.add(num);
                    num = "";
                }
                temp.add(String.valueOf(c));
            } else {
                num += c;
            }
        }
        temp.add(num);
        return temp;
    }


    /**
     * 方法：将 中缀表达式转成对应的List
     * s="1+((2+3)×4)-5";
     *
     * @param s
     * @return
     */
    public static List<String> toInfixExpressionList(String s) {
        //定义一个List,存放中缀表达式 对应的内容
        List<String> ls = new ArrayList<String>();
        //这时是一个指针，用于遍历 中缀表达式字符串
        int i = 0;
        // 对多位数的拼接
        String str;
        // 每遍历到一个字符，就放入到c
        char c;
        do {
            //如果c是一个非数字，我需要加入到ls
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                ls.add("" + c);
                //i需要后移
                i++;
            } else {
                //如果是一个数，需要考虑多位数
                //先将str 置成"" '0'[48]->'9'[57]
                str = "";
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    //拼接
                    str += c;
                    i++;
                }
                ls.add(str);
            }
        } while (i < s.length());
        //返回
        return ls;
    }

    public static int calculate(List<String> list) {
        // 创建给栈, 只需要一个栈即可
        Stack<String> stack = new Stack<String>();
        // 遍历 ls
        for (String item : list) {
            // 这里使用正则表达式来取出数
            // 匹配的是多位数
            if (item.matches("\\d+")) {
                // 入栈
                stack.push(item);
            } else {
                // pop出两个数，并运算， 再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误");
                }
                //把res 入栈
                stack.push("" + res);
            }

        }
        //最后留在stack中的数据是运算结果
        return Integer.parseInt(stack.pop());
    }

}

/**
 * 编写一个类 Operation 可以返回一个运算符 对应的优先级
 */
class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    /**
     * 写一个方法，返回对应的优先级数字
     *
     * @param operation
     * @return
     */
    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                break;
        }
        return result;
    }

}
