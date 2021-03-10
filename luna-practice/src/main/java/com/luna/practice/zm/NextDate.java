package com.luna.practice.zm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * @author luna
 */
public class NextDate {

    public static String getNextDate(String dateStr) {
        try {
            String[] split = dateStr.split("-");
            // 格式化日期
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateStr);
            Date nextdate = null;
            // 判断年份合法性
            if (date.compareTo(dateFormat.parse("1800-01-01")) >= 0
                    && date.compareTo(dateFormat.parse("2050-12-31")) <= 0) {
                // 判断月份合法性
                if (Integer.parseInt(split[1]) >= 1
                        && Integer.parseInt(split[1]) <= 12) {
                    // 判断天数合法性
                    if (Integer.parseInt(split[2]) >= 1
                            && Integer.parseInt(split[2]) <= 31) {
                        // 增加一天
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        nextdate = calendar.getTime();
                        return dateFormat.format(nextdate);
                    } else {
                        throw new RuntimeException("请填入一个在1和12之间的整数");
                    }
                } else {
                    throw new RuntimeException("请填入一个在1和12之间的整数");
                }
            } else {
                throw new RuntimeException("请填入一个在1800和2050之间的整数");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException("格式错误");
        }
    }

    public static String formatDate(String date) throws Exception {
        String[] strs = date.split("-");
        if (strs[1].startsWith("0")) {
            strs[1] = strs[1].substring(1, 2);
        }
        if (strs[2].startsWith("0")) {
            strs[2] = strs[2].substring(1, 2);
        }
        String nextDate = strs[0] + "年" + strs[1] + "月" + strs[2] + "日";
        return nextDate;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String date = null;
        while (true) {
            // 获取键盘输入日期
            date = in.nextLine();
            if (date.equals("") == false) {
                System.err.println("输入日期:" + date);
                // 获取第二天日期
                String nextDate = getNextDate(date);
                if (nextDate != null) {
                    System.err.println("第二天的日期是：" + nextDate);
                } else {
                    System.err.println("系统错误");
                }
            }
        }
    }

}
