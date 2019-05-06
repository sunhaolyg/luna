package com.example.luna.poker.cow;

import com.example.luna.poker.Poker;
import com.example.luna.poker.gold.PokerUtils;

import java.util.List;

public class BullCow {

    private List<Poker> pokers;
    String p = "", n = "";

    /**
     * 0:none
     * 10000:normal
     * 200000:min
     * 3000o0:bomb
     * 4000o0:fiveflow
     */
    private int type = 0;

    public BullCow(List<Poker> pokers) {
        this.pokers = pokers;
    }

    public void calculate() {
        p = "";
        n = "";
        for (Poker poker : pokers) {
            p += poker.getNumberWithColor() + ",";
            n += poker.getCowNum() + ",";
        }

        for (int i = 0; i < pokers.size(); i++) {
            for (int j = i + 1; j < pokers.size(); j++) {
                for (int k = j + 1; k < pokers.size(); k++) {
                    if (plus(i, j, k)) {
                        type = cowResult(i, j, k);
                    }

                }
            }
        }

//        Logutils.d("WaterFallLayout", "p = " + p + ",n = " + n);
        boolean fiveflow = true;
        int sum = 0;
        for (int i = 0; i < pokers.size(); i++) {
            if (pokers.get(i).getCowNum() <= 10) {
                fiveflow = false;
            }
            sum += getNumFromIndex(i);
        }
        if (sum < 10) {
            type = 200000 + figureMax();
        }
        int bomb = isBomb();
        if (bomb != 0) {
            type = 300000 + bomb;
        }
        if (fiveflow) {
            type = 400000 + figureMax();
        }
        if (type == 0) {
            figureMax();
        }
        n += " " + type;
    }

    private int figureMax() {
        Poker maxNum = null;
        int temp = 0;
        for (int a = 0; a < pokers.size(); a++) {
            Poker poker = pokers.get(a);
            int sum = poker.getCowNum() * 10 + PokerUtils.indexOfColor(poker.getNumberWithColor());
            if (sum > temp) {
                maxNum = poker;
                temp = sum;
            }
        }
        return type = maxNum.getCowNum() * 10 + PokerUtils.indexOfColor(maxNum.getNumberWithColor());
    }

    private int cowResult(int i, int j, int k) {
        int result = 0;
        Poker maxNum = null;
        int temp = 0;
        for (int a = 0; a < pokers.size(); a++) {
            Poker poker = pokers.get(a);
            int sum = poker.getCowNum() * 10 + PokerUtils.indexOfColor(poker.getNumberWithColor());
            if (sum > temp) {
                maxNum = poker;
                temp = sum;
            }
            if (a == i || a == j || a == k) {
                continue;
            }
            result += getNumFromIndex(a);
        }
        result %= 10;
        result *= 1000;
        if (result == 0) {
            result = 10000;
        }
        result += maxNum.getCowNum() * 10 + PokerUtils.indexOfColor(maxNum.getNumberWithColor());
        return result;
    }

    private boolean plus(int i, int j, int k) {
        int a = getNumFromIndex(i);
        int b = getNumFromIndex(j);
        int c = getNumFromIndex(k);
        return (a + b + c) % 10 == 0;
    }

    private int getNumFromIndex(int index) {
        int num = pokers.get(index).getCowNum();
        if (num > 10) {
            return 10;
        }
        return num;
    }

    private int isBomb() {
        int count = 1;
        int count1 = 1;
        int z1 = 100;
        int z2 = 100;
        for (int i = 0; i < 5; i++) {
            int numi = pokers.get(i).getNumber();
            if (z1 == numi || z2 == numi) {
                continue;
            }
            for (int j = i + 1; j < 5; j++) {
                int numj = pokers.get(j).getNumber();
                if (numi == numj) {
                    if (z1 == 100 || z1 == numi) {
                        z1 = numi;
                        count++;
                    } else {
                        z2 = numi;
                        count1++;
                    }
                }
            }
        }
        if (count == 4 || count1 == 4) {
            int same1 = pokers.get(0).getCowNum();
            int same2 = pokers.get(1).getCowNum();
            int same3 = pokers.get(2).getCowNum();
            if (same1 == same3) {
                return same1;
            }
            if (same2 == same3) {
                return same2;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "p = " + p + ",n = " + n;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
