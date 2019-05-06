package com.example.luna.poker;

import com.example.luna.poker.gold.PokerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PokerFactory {

    private static PokerFactory sInstance;
    private List<Poker> pokers;

    private PokerFactory() {
    }

    public static PokerFactory getInstance() {
        if (sInstance == null) {
            sInstance = new PokerFactory();
        }
        return sInstance;
    }

    public PokerFactory init() {
        pokers = new ArrayList<>();
        return sInstance;
    }

    public Poker createPoker() {
        int id = 0;
        Poker poker = new Poker();
        do {
            id = PokerUtils.random();
            poker.setId(id);
        } while (pokers.contains(poker));
        pokers.add(poker);
        return poker;
    }

    public List<Poker> createCow() {
        return createPokers(5);
    }

    public List<Poker> createPokers(int size) {
        return createPokers(size, null);
    }

    public List<Poker> createPokers(int size, List<Poker> pokerList) {
        if (pokerList != null) {
            pokers.addAll(pokerList);
        }
        List<Poker> pokers = new ArrayList<>();
        do {
            pokers.add(createPoker());
        } while (pokers.size() != size);
        sortPokers(pokers);
        return pokers;
    }

    private void sortPokers(List<Poker> pokers) {
        Collections.sort(pokers, new Comparator<Poker>() {
            @Override
            public int compare(Poker o1, Poker o2) {
                return o1.getId() - o2.getId();
            }
        });
    }
}
