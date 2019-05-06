package com.example.luna.poker.gold;

import android.text.TextUtils;
import com.example.luna.poker.Poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PokerUtils {

    public static final String[] NUMBER_LIST = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    public static final String[] COLOR_LIST = {"D", "C", "H", "S"};

    public static int random() {
        Random random = new Random();
        return random.nextInt(COLOR_LIST.length * NUMBER_LIST.length);
    }

    public static String id2Poker(int id) {
        int number = id / COLOR_LIST.length;
        int color = id % COLOR_LIST.length;
        return COLOR_LIST[color] + NUMBER_LIST[number];
    }

    public static int poker2Id(String poker) {
        if (TextUtils.isEmpty(poker) || poker.length() < 2) {
            return -1;
        }
        String color = poker.substring(0, 1);
        String number = poker.substring(1);
        int c = 0, n = 0;
        for (int i = 0; i < NUMBER_LIST.length; i++) {
            if (number.equals(NUMBER_LIST[i])) {
                n = i;
                break;
            }
        }
        for (int i = 0; i < COLOR_LIST.length; i++) {
            if (color.equals(COLOR_LIST[i])) {
                c = i;
                break;
            }
        }
        return n * COLOR_LIST.length + c;
    }

    public static int transResult(List<Poker> pokers) {
        Poker poker1 = pokers.get(0);
        Poker poker2 = pokers.get(1);
        Poker poker3 = pokers.get(2);
        int sameNumber = sameNumber(poker1, poker2, poker3);
        if (sameNumber != -1) {
            return 50000000 + sameNumber;
        }
        int compareColor = compareColor(poker1, poker2, poker3);
        int compareNumber = compareNumber(poker1, poker2, poker3);
        if (compareColor != -1) {
            if (compareNumber != -1) {
                return 40000000 + compareColor + compareNumber;
            }
            return 30000000 + compareColor;
        }
        if (compareNumber != -1) {
            return 20000000 + compareNumber;
        }
        int pairNumber = pairNumber(poker1, poker2, poker3);
        if (pairNumber != -1) {
            return 10000000 + pairNumber;
        }
        return 0;
    }

    public static String result2String(int result) {
        result = result / 10000000;
        String stringResult = "figure";
        switch (result) {
            case 0:
                stringResult = "figure";
                break;
            case 1:
                stringResult = "pair";
                break;
            case 2:
                stringResult = "number";
                break;
            case 3:
                stringResult = "color";
                break;
            case 4:
                stringResult = "color and number";
                break;
            case 5:
                stringResult = "leopard!!!";
                break;
            default:
                stringResult = "figure";
                break;
        }
        return stringResult;
    }

    private static int compareColor(Poker poker1, Poker poker2, Poker poker3) {
        if (poker1.getColor() == poker2.getColor() && poker2.getColor() == poker3.getColor()) {

            return indexOfColor(poker1.getNumberWithColor()) +
                    indexOfNumber(poker3.getNumberWithColor()) * 100000 +
                    indexOfNumber(poker2.getNumberWithColor()) * 1000 +
                    indexOfNumber(poker1.getNumberWithColor()) * 10;
        }
        return -1;
    }

    private static int sameNumber(Poker poker1, Poker poker2, Poker poker3) {
        if (poker1.getNumber() == poker2.getNumber() && poker2.getNumber() == poker3.getNumber()) {
            return indexOfNumber(poker1.getNumberWithColor());
        }
        return -1;
    }

    private static int pairNumber(Poker poker1, Poker poker2, Poker poker3) {
        int i1 = poker1.getNumber();
        int i2 = poker2.getNumber();
        int i3 = poker3.getNumber();
        if (i1 == i2) {
            return indexOfNumber(poker1.getNumberWithColor()) * 10000 +
                    indexOfNumber(poker3.getNumberWithColor());
        }
        if (i1 == i3) {
            return indexOfNumber(poker1.getNumberWithColor()) * 10000 +
                    indexOfNumber(poker2.getNumberWithColor());
        }
        if (i2 == i3) {
            return indexOfNumber(poker2.getNumberWithColor()) * 10000 +
                    indexOfNumber(poker1.getNumberWithColor());
        }
        return -1;
    }

    private static int compareNumber(Poker poker1, Poker poker2, Poker poker3) {
        int i1 = poker1.getNumber();
        int i2 = poker2.getNumber();
        int i3 = poker3.getNumber();
        if (i1 == 0 && i2 == 1 && i3 == NUMBER_LIST.length - 1) {
            return 1;
        }
        if ((i3 - i2) == 1 && (i2 - i1) == 1) {
            return indexOfNumber(poker3.getNumberWithColor());
        }
        return -1;
    }

    public static int indexOfNumber(String value) {
        String number = value.substring(1);
        List<String> arrays = new ArrayList<>(Arrays.asList(NUMBER_LIST));
        return arrays.indexOf(number) + 2;
    }

    public static int indexOfColor(String value) {
        String number = value.substring(0, 1);
        List<String> arrays = new ArrayList<>(Arrays.asList(COLOR_LIST));
        return arrays.indexOf(number) + 1;
    }

}
