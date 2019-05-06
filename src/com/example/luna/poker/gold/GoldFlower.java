package com.example.luna.poker.gold;

import android.text.TextUtils;
import com.example.luna.poker.Poker;

import java.util.ArrayList;
import java.util.List;

public class GoldFlower {

    /**
     * 0:figure
     * 1:pair
     * 2:number
     * 3:color
     * 4:number and color
     * 5:leopard
     */
    private int type;

    private List<Poker> pokers = new ArrayList<>();

    private Poker i, j, k;

    public GoldFlower() {
    }

    public GoldFlower(Poker i, Poker j, Poker k) {
        init(i, j, k);
    }

    private void init(Poker i, Poker j, Poker k) {
        this.i = i;
        this.j = j;
        this.k = k;
        pokers.add(i);
        pokers.add(j);
        pokers.add(k);
    }

    public GoldFlower(List<Poker> pokers) {
        init(pokers.get(0), pokers.get(1), pokers.get(2));
    }

    public int getType() {
        type = PokerUtils.transResult(pokers);
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Poker> getPokers() {
        return pokers;
    }

    public void setPokers(List<Poker> pokers) {
        this.pokers = pokers;
        type = PokerUtils.transResult(pokers);
    }

    public String pokers2String() {
        String text = "";
        for (Poker poker : pokers) {
            text += poker.getNumberWithColor() + ",";
        }
        if (TextUtils.isEmpty(text)) {
            text += i.getNumberWithColor() + ",";
            text += j.getNumberWithColor() + ",";
            text += k.getNumberWithColor();
        }
        return text;
    }

    @Override
    public String toString() {
        return pokers2String();
    }
}
