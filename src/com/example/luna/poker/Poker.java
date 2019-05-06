package com.example.luna.poker;

import com.example.luna.poker.gold.PokerUtils;

import static com.example.luna.poker.gold.PokerUtils.COLOR_LIST;

public class Poker {

    private int id;
    private int color;
    private int number;
    private String numberWithColor;
    private int cowNum;

    public String getNumberWithColor() {
        return numberWithColor;
    }

    public void setNumberWithColor(String numberWithColor) {
        this.numberWithColor = numberWithColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.numberWithColor = PokerUtils.id2Poker(id);
        this.number = id / COLOR_LIST.length;
        this.color = id % COLOR_LIST.length;
        cowNum = PokerUtils.indexOfNumber(numberWithColor);
        if (cowNum == 14) {
            cowNum = 1;
        }
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCowNum() {
        return cowNum;
    }

    public void setCowNum(int cowNum) {
        this.cowNum = cowNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Poker)) {
            return false;
        }
        Poker poker = (Poker) obj;
        if (poker.getId() != this.id) {
            return false;
        }
        return true;
    }
}
