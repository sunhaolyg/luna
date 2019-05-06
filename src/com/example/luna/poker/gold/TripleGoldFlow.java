package com.example.luna.poker.gold;

import com.example.luna.poker.Poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TripleGoldFlow {

    private GoldFlower a, b, c;

    private List<GoldFlower> goldFlowers = new ArrayList<>();

    public TripleGoldFlow(List<Poker> pokers) {
        List<Poker> pokers1 = new ArrayList<>();
        for (Poker poker : pokers) {
            pokers1.add(poker);
            if (pokers1.size() == 3) {
                goldFlowers.add(new GoldFlower(pokers1));
                pokers1.clear();
            }
        }
        init(goldFlowers.get(0), goldFlowers.get(1), goldFlowers.get(2));
    }

    public TripleGoldFlow(GoldFlower a, GoldFlower b, GoldFlower c) {
        init(a, b, c);
    }

    private void init(GoldFlower a, GoldFlower b, GoldFlower c) {
        if (goldFlowers.size() == 0) {
            goldFlowers.add(a);
            goldFlowers.add(b);
            goldFlowers.add(c);
        }
        Collections.sort(goldFlowers, new Comparator<GoldFlower>() {
            @Override
            public int compare(GoldFlower o1, GoldFlower o2) {
                return o1.getType() - o2.getType();
            }
        });
        this.a = goldFlowers.get(0);
        this.b = goldFlowers.get(1);
        this.c = goldFlowers.get(2);
    }

    public GoldFlower getA() {
        return a;
    }

    public void setA(GoldFlower a) {
        this.a = a;
    }

    public GoldFlower getB() {
        return b;
    }

    public void setB(GoldFlower b) {
        this.b = b;
    }

    public GoldFlower getC() {
        return c;
    }

    public void setC(GoldFlower c) {
        this.c = c;
    }

    public List<GoldFlower> getGoldFlowers() {
        return goldFlowers;
    }

    public void setGoldFlowers(List<GoldFlower> goldFlowers) {
        this.goldFlowers = goldFlowers;
    }

    public int getTypePlus() {
        return a.getType() + b.getType() + c.getType();
    }

    @Override
    public String toString() {
        return a + " " + b + " " + c + " " + a.getType() + "," + b.getType() + "," + c.getType();
    }
}
