package com.luna.practice.lyx.basic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author luna@mac
 * @className Play.java
 * @description TODO
 * @createTime 2021年03月04日 11:34:00
 */
public class Play {

    private final static List<String> COLORS     = Arrays.asList("红桃", "黑桃", "梅花", "方片");

    private final static Integer      ALL_NUMBER = 54;

    private final static Integer      NUMBER     = 13;

    private List<PlayCard>            cards;

    public List<PlayCard> getCards() {
        return cards;
    }

    public Play setCards(List<PlayCard> cards) {
        this.cards = cards;
        return this;
    }

    public Play() {
        List<PlayCard> cards = new ArrayList<>(ALL_NUMBER);
        PlayCard big = new PlayCard(0, "大王");
        cards.add(big);
        PlayCard small = new PlayCard(0, "小王");
        cards.add(small);
        for (int j = 1; j <= NUMBER; j++) {
            for (int k = 0; k < COLORS.size(); k++) {
                String color = COLORS.get(k);
                Integer number = j;
                cards.add(new PlayCard(number, color));
            }
        }
        this.cards = cards;
    }


    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public static String game(List<PlayCard> cards) throws IOException {
        List<PlayCard> playCards = new ArrayList<>(4);
        Random random = new Random();
        int size = 4;
        for (int i = 0; i < cards.size(); i++) {
            int number = random.nextInt(54);
            PlayCard playCard = cards.get(number);
            if (playCard.getNumber() != 0) {
                playCards.add(playCard);
                size--;
            }
            if (size == 0) {
                break;
            }
        }
        System.out.println(playCards);
        StringBuffer sb = new StringBuffer();
        sb.append("下列给出四张牌，使用+，-，*，/进行计算使最后计算结果为24\n");
        System.out.println("下列给出四个数字，使用+，-，*，/进行计算使最后计算结果为24");
        int[] numbers = new int[playCards.size()];
        for (int i = 0; i < playCards.size(); i++) {
            Integer number = playCards.get(i).getNumber();
            if (number == 1) {
                sb.append("A\n");
                // 如果随机生成的数为1，则显示为扑克牌牌面中的A
            } else if (number == 11) {
                sb.append("J\n");
                // 如果随机生成的数为11，则显示为扑克牌牌面中的J
            } else if (number == 12) {
                sb.append("Q\n");
                // 如果随机生成的数为12，则显示为扑克牌牌面中的Q
            } else if (number == 13) {
                sb.append("K\n");
                // 如果随机生成的数为13，则显示为扑克牌牌面中的K
            } else {
                sb.append(i + "\n");
            }
            numbers[i] = number;
            System.out.println(number);
        }
        FileWriter fw = new FileWriter("./luna-practice/src/main/resources/lyxdoc/TopList.txt");
        fw.write(sb.toString());
        fw.close();
        sb.append("可能的结果有：\n");
        System.out.println("可能的结果有：");
        CardGame.calculate(numbers);
        return sb.toString();
    }


    public static void main(String[] args) throws IOException {
        Play play = new Play();
        System.out.println("========初始化扑克========");
        System.out.println(play.getCards());
        play.shuffle();
        System.out.println("========洗牌========");
        System.out.println(play.getCards());
        System.out.println("========随机4张24点游戏========");
        Play.game(play.getCards());
    }
}

class PlayCard {

    private Integer number;

    private String  color;

    public PlayCard(Integer number, String color) {
        this.number = number;
        this.color = color;
    }

    public Integer getNumber() {
        return number;
    }

    public PlayCard setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public String getColor() {
        return color;
    }

    public PlayCard setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public String toString() {
        return "PlayCard{" +
            "number=" + number +
            ", color='" + color + '\'' +
            '}';
    }
}