package com.luna.practice.lyx.basic;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.File;

public class CardGame {
    /** 定义随机产生的四个数 */
    static int            number[] = new int[4];
    /** 转换后的num1，num2,num3,num4 */
    static int            m[]      = new int[4];
    static String         n[]      = new String[4];
    /** 用来判断是否有解 */
    static boolean        flag     = false;
    /** 存放操作符 */
    static char[]         operator = {'+', '-', '*', '/'};
    private static Object key;

    /**
     * 创建TopList.txt文件
     */
    public static void CreateFile() {
        File file = new File("./luna-practice/src/main/resources/lyxdoc/TopList.txt");
        boolean CreatFile = file.exists();
        // System.out.println(CreatFile);
        try {
            boolean CreatFile1 = file.createNewFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        Random rand = new Random();
        sb.append("下列给出四个数字，使用+，-，*，/进行计算使最后计算结果为24\n");
        System.out.println("下列给出四个数字，使用+，-，*，/进行计算使最后计算结果为24");
        for (int i = 0; i < 4; i++) {
            number[i] = rand.nextInt(13) + 1;// 随机生成四个int型数
            if (number[i] == 1) {
                sb.append("A\n");
                System.out.println("A");// 如果随机生成的数为1，则显示为扑克牌牌面中的A
            } else if (number[i] == 11) {
                sb.append("J\n");
                System.out.println("J");// 如果随机生成的数为11，则显示为扑克牌牌面中的J
            } else if (number[i] == 12) {
                sb.append("Q\n");
                System.out.println("Q");// 如果随机生成的数为12，则显示为扑克牌牌面中的Q
            } else if (number[i] == 13) {
                sb.append("K\n");
                System.out.println("K");// 如果随机生成的数为13，则显示为扑克牌牌面中的K
            } else
                sb.append(i + "\n");
            System.out.println(number[i]);
        }
        try {
            FileWriter fw = new FileWriter("./luna-practice/src/main/resources/lyxdoc/TopList.txt");
            fw.write(sb.toString());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sb.append("可能的结果有：\n");
        System.out.println("可能的结果有：");
        calculate(number);
    }

    /**
     * 给定2个数和指定操作符的计算
     * 
     * @param count1
     * @param count2
     * @param operator
     * @return
     */
    public static int calcute(int count1, int count2, char operator) {
        if (operator == '+') {
            return count1 + count2;
        } else if (operator == '-') {
            return count1 - count2;
        } else if (operator == '*') {
            return count1 * count2;
        } else if ((operator == '/') && (count2 != 0) && (count1 % count2 == 0)) {
            return count1 / count2;
        } else {
            return -1;
        }
    }

    /**
     * 计算生成24的函数
     */
    public static void calculate(int[] number) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // 存放数字，用来判断输入的4个数字中有几个重复的，和重复的情况
        for (int i = 0; i < number.length; i++) {
            if (map.get(number[i]) == null) {
                map.put(number[i], 1);
            } else {
                map.put(number[i], map.get(number[i]) + 1);
            }
        }
        if (map.size() == 1) {
            // 如果只有一种数字，此时只有一种排列组合，如5，5，5，5
            calculation(number[0], number[1], number[2], number[3]);
        } else if (map.size() == 2) {
            // 如果只有2种数字，有2种情况，如1,1,2,2和1,1,1,2
            int index = 0;// 用于数据处理
            int state = 0;// 判断是哪种情况
            for (Integer key : map.keySet()) {
                if (map.get(key) == 1) {
                    // 如果是有1个数字和其他3个都不同，将number变为 number[0]=number[1]=number[2]，
                    // 将不同的那个放到number[3]，方便计算
                    number[3] = key;
                    state = 1;
                } else if (map.get(key) == 2) {
                    // 如果是两两相同的情况，将number变为number[0]=number[1],number[2]=number[3]的情况
                    number[index++] = key;
                    number[index++] = key;
                } else {
                    number[index++] = key;
                }
            }
            // 列出2种情况的所有排列组合，并分别计算
            if (state == 1) {
                calculation(number[3], number[1], number[1], number[1]);
                calculation(number[1], number[3], number[1], number[1]);
                calculation(number[1], number[1], number[3], number[1]);
                calculation(number[1], number[1], number[1], number[3]);
            }
            if (state == 0) {
                calculation(number[1], number[1], number[3], number[3]);
                calculation(number[1], number[3], number[1], number[3]);
                calculation(number[1], number[3], number[3], number[1]);
                calculation(number[3], number[3], number[1], number[1]);
                calculation(number[3], number[1], number[3], number[1]);
                calculation(number[3], number[1], number[1], number[3]);
            }
        } else if (map.size() == 3) {
            // 有3种数字的情况
            int index = 0;
            for (Integer key : map.keySet()) {
                if (map.get(key) == 2) {
                    // 将相同的2个数字放到number[2]=number[3]
                    number[2] = key;
                    number[3] = key;
                } else {
                    number[index++] = key;
                }
            }
            // 排列组合，所有情况
            calculation(number[0], number[1], number[3], number[3]);
            calculation(number[0], number[3], number[1], number[3]);
            calculation(number[0], number[3], number[3], number[1]);
            calculation(number[1], number[0], number[3], number[3]);
            calculation(number[1], number[3], number[0], number[3]);
            calculation(number[1], number[3], number[3], number[0]);
            calculation(number[3], number[3], number[0], number[1]);
            calculation(number[3], number[3], number[1], number[0]);
            calculation(number[3], number[1], number[3], number[0]);
            calculation(number[3], number[0], number[3], number[1]);
            calculation(number[3], number[0], number[1], number[3]);
            calculation(number[3], number[1], number[0], number[3]);
        } else if (map.size() == 4) {
            // 4个数都不同的情况
            calculation(number[0], number[1], number[2], number[3]);
            calculation(number[0], number[1], number[3], number[2]);
            calculation(number[0], number[2], number[1], number[3]);
            calculation(number[0], number[2], number[3], number[1]);
            calculation(number[0], number[3], number[1], number[2]);
            calculation(number[0], number[3], number[2], number[1]);
            calculation(number[1], number[0], number[2], number[3]);
            calculation(number[1], number[0], number[3], number[2]);
            calculation(number[1], number[2], number[3], number[0]);
            calculation(number[1], number[2], number[0], number[3]);
            calculation(number[1], number[3], number[0], number[2]);
            calculation(number[1], number[3], number[2], number[0]);
            calculation(number[2], number[0], number[1], number[3]);
            calculation(number[2], number[0], number[3], number[1]);
            calculation(number[2], number[1], number[0], number[3]);
            calculation(number[2], number[1], number[3], number[0]);
            calculation(number[2], number[3], number[0], number[1]);
            calculation(number[2], number[3], number[1], number[0]);
            calculation(number[3], number[0], number[1], number[2]);
            calculation(number[3], number[0], number[2], number[1]);
            calculation(number[3], number[1], number[0], number[2]);
            calculation(number[3], number[1], number[2], number[0]);
            calculation(number[3], number[2], number[0], number[1]);
            calculation(number[3], number[2], number[1], number[0]);
        }
        if (flag == false)
            System.out.println("这四张牌面数字无法经过运算得到24！");
    }

    public static void calculation(int num1, int num2, int num3, int num4) {
        for (int i = 0; i < 4; i++) {
            // 第1次计算，先从四个数中任意选择两个进行计算
            char operator1 = operator[i];
            int firstResult = calcute(num1, num2, operator1);// 先选第一，和第二个数进行计算
            int midResult = calcute(num2, num3, operator1);// 先选第二和第三两个数进行计算
            int tailResult = calcute(num3, num4, operator1);// 先选第三和第四俩个数进行计算
            for (int j = 0; j < 4; j++) {
                // 第2次计算，从上次计算的结果继续执行，这次从三个数中选择两个进行计算
                char operator2 = operator[j];
                int firstMidResult = calcute(firstResult, num3, operator2);
                int firstTailResult = calcute(num3, num4, operator2);
                int midFirstResult = calcute(num1, midResult, operator2);
                int midTailResult = calcute(midResult, num4, operator2);
                int tailMidResult = calcute(num2, tailResult, operator2);
                for (int k = 0; k < 4; k++) {
                    // 第3次计算，也是最后1次计算，计算两个数的结果，如果是24则输出表达式
                    char operator3 = operator[k];
                    // 在以上的计算中num1，num2,num3,num4都是整型数值，但若要输出为带有A，J,Q,K的表达 式，则要将这四个表达式变为String类型，下同
                    if (calcute(firstMidResult, num4, operator3) == 24) {
                        m[0] = num1;
                        m[1] = num2;
                        m[2] = num3;
                        m[3] = num4;
                        for (int p = 0; p < 4; p++) {
                            if (m[p] == 1) {
                                n[p] = "A";
                            }
                            if (m[p] == 2) {
                                n[p] = "2";
                            }
                            if (m[p] == 3) {
                                n[p] = "3";
                            }
                            if (m[p] == 4) {
                                n[p] = "4";
                            }
                            if (m[p] == 5) {
                                n[p] = "5";
                            }
                            if (m[p] == 6) {
                                n[p] = "6";
                            }
                            if (m[p] == 7) {
                                n[p] = "7";
                            }
                            if (m[p] == 8) {
                                n[p] = "8";
                            }
                            if (m[p] == 9) {
                                n[p] = "9";
                            }
                            if (m[p] == 10) {
                                n[p] = "10";
                            }
                            if (m[p] == 11) {
                                n[p] = "J";
                            }
                            if (m[p] == 12) {
                                n[p] = "Q";
                            }
                            if (m[p] == 13) {
                                n[p] = "k";
                            }
                        }
                        System.out.println("((" + n[0] + operator1 + n[1] + ")" + operator2 + n[2] + ")" + operator3 +
                            n[3]);
                        // 若有表达式输出，则将说明有解，下同
                        flag = true;
                    }
                    if (calcute(firstResult, firstTailResult, operator3) == 24) {
                        System.out.println("(" + n[0] + operator1 + n[1] + ")" + operator3 + "(" + n[2] + operator2 +
                            n[3] + ")");
                        flag = true;
                    }
                    if (calcute(midFirstResult, num4, operator3) == 24) {
                        m[0] = num1;
                        m[1] = num2;
                        m[2] = num3;
                        m[3] = num4;
                        for (int p = 0; p < 4; p++) {
                            if (m[p] == 1) {
                                n[p] = "A";
                            }
                            if (m[p] == 2) {
                                n[p] = "2";
                            }
                            if (m[p] == 3) {
                                n[p] = "3";
                            }
                            if (m[p] == 4) {
                                n[p] = "4";
                            }
                            if (m[p] == 5) {
                                n[p] = "5";
                            }
                            if (m[p] == 6) {
                                n[p] = "6";
                            }
                            if (m[p] == 7) {
                                n[p] = "7";
                            }
                            if (m[p] == 8) {
                                n[p] = "8";
                            }
                            if (m[p] == 9) {
                                n[p] = "9";
                            }
                            if (m[p] == 10) {
                                n[p] = "10";
                            }
                            if (m[p] == 11) {
                                n[p] = "J";
                            }
                            if (m[p] == 12) {
                                n[p] = "Q";
                            }
                            if (m[p] == 13) {
                                n[p] = "k";
                            }
                        }
                        // System.out.println("(" + n[0] + operator2 + "(" + n[1] + operator1 + n[2] + "))" + operator3
                        // + n[3]);
                        flag = true;
                    }
                    if (calcute(num1, midTailResult, operator3) == 24) {
                        m[0] = num1;
                        m[1] = num2;
                        m[2] = num3;
                        m[3] = num4;
                        for (int p = 0; p < 4; p++) {
                            if (m[p] == 1) {
                                n[p] = "A";
                            }
                            if (m[p] == 2) {
                                n[p] = "2";
                            }
                            if (m[p] == 3) {
                                n[p] = "3";
                            }
                            if (m[p] == 4) {
                                n[p] = "4";
                            }
                            if (m[p] == 5) {
                                n[p] = "5";
                            }
                            if (m[p] == 6) {
                                n[p] = "6";
                            }
                            if (m[p] == 7) {
                                n[p] = "7";
                            }
                            if (m[p] == 8) {
                                n[p] = "8";
                            }
                            if (m[p] == 9) {
                                n[p] = "9";
                            }
                            if (m[p] == 10) {
                                n[p] = "10";
                            }
                            if (m[p] == 11) {
                                n[p] = "J";
                            }
                            if (m[p] == 12) {
                                n[p] = "Q";
                            }
                            if (m[p] == 13) {
                                n[p] = "k";
                            }
                        }
                        System.out.println(" " + n[0] + operator3 + "((" + n[1] + operator1 + n[2] + ")" + operator2 +
                            n[3] + ")");
                        flag = true;
                    }

                    if (calcute(num1, tailMidResult, operator3) == 24) {
                        m[0] = num1;
                        m[1] = num2;
                        m[2] = num3;
                        m[3] = num4;
                        for (int p = 0; p < 4; p++) {
                            if (m[p] == 1) {
                                n[p] = "A";
                            }
                            if (m[p] == 2) {
                                n[p] = "2";
                            }
                            if (m[p] == 3) {
                                n[p] = "3";
                            }
                            if (m[p] == 4) {
                                n[p] = "4";
                            }
                            if (m[p] == 5) {
                                n[p] = "5";
                            }
                            if (m[p] == 6) {
                                n[p] = "6";
                            }
                            if (m[p] == 7) {
                                n[p] = "7";
                            }
                            if (m[p] == 8) {
                                n[p] = "8";
                            }
                            if (m[p] == 9) {
                                n[p] = "9";
                            }
                            if (m[p] == 10) {
                                n[p] = "10";
                            }
                            if (m[p] == 11) {
                                n[p] = "J";
                            }
                            if (m[p] == 12) {
                                n[p] = "Q";
                            }
                            if (m[p] == 13) {
                                n[p] = "k";
                            }
                        }
                        System.out.println(" " + n[0] + operator3 + "(" + n[1] + operator2 + "(" + n[2] + operator1 +
                            n[3] + "))");
                        flag = true;
                        CreateFile();
                    }
                }
            }
        }
    }
}
