package com.luna.practice.lyx.design.flyweight;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * @author luna@mac
 * @className Pieces.java
 * @description TODO
 * @createTime 2021年03月10日 11:06:00
 */
public interface Following {

    void follow(String[][] array);
}

class Pieces implements Following {

    private int    x, y;

    private String pieces;

    public int getX() {
        return x;
    }

    public Pieces setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Pieces setY(int y) {
        this.y = y;
        return this;
    }

    public Pieces(String pieces) {
        this.pieces = pieces;
    }

    @Override
    public void follow(String[][] array) {
        array[x][y] = pieces;
    }
}

class PiecesFactory {

    private static final HashMap<String, Following> PIECE_MAP = new HashMap<>();

    public static Pieces getPieces(String color) {
        Pieces pieces = (Pieces)PIECE_MAP.get(color);

        if (pieces == null) {
            pieces = new Pieces(color);
            PIECE_MAP.put(color, pieces);
            System.out.println("pieces of color : " + color);
        }
        return pieces;
    }
}

class PlayingChess {
    private static final String[] COLORS =
        {"⚫️", "⚪️"};

    private static String[][]     chess  = new String[15][15];

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < chess.length; i++) {
            for (int j = 0; j < chess[i].length; j++) {
                int nextInt = random.nextInt(2);
                if (nextInt == 0) {
                    Pieces pieces = PiecesFactory.getPieces(COLORS[nextInt]);
                    pieces.setX(i);
                    pieces.setY(j);
                    pieces.follow(chess);
                } else if (nextInt == 1) {
                    Pieces pieces = PiecesFactory.getPieces(COLORS[nextInt]);
                    pieces.setX(i);
                    pieces.setY(j);
                    pieces.follow(chess);
                }
            }

        }
        for (String[] strings : chess) {
            System.out.println(Arrays.toString(strings));
        }
    }
}
