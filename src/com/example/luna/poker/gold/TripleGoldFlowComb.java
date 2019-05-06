package com.example.luna.poker.gold;

import com.example.luna.poker.Poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TripleGoldFlowComb {

    private List<Poker> pokers;

    private List<IndexComb> compareIndices;

    private List<TripleGoldFlow> tripleGoldFlows;

    public TripleGoldFlowComb(List<Poker> pokers, List<IndexComb> compareIndices) {
        this.pokers = pokers;
        this.compareIndices = compareIndices;
        tripleGoldFlows = new ArrayList<>();
    }

    public void calculate() {
        List<Integer> indexList = null;
        List<Poker> goldPokers = new ArrayList<>();
        for (IndexComb index : compareIndices) {
            indexList = index.getIndexList();
            goldPokers.clear();
            for (int i : indexList) {
                goldPokers.add(pokers.get(i));
            }
            tripleGoldFlows.add(new TripleGoldFlow(goldPokers));
//            String s = "";
//            String p = "";
//            for (int i = 0; i < indexList.size(); i++) {
//                s += indexList.get(i) + "";
//                p += goldPokers.get(i).getNumberWithColor();
//            }
//            Logutils.d("WaterFallLayout", "s =====" + s + ",p = " + p);
        }
        Collections.sort(tripleGoldFlows, new Comparator<TripleGoldFlow>() {
            @Override
            public int compare(TripleGoldFlow o1, TripleGoldFlow o2) {
                return o1.getTypePlus() - o2.getTypePlus();
            }
        });
    }

    public List<TripleGoldFlow> getTripleGoldFlows() {
        return tripleGoldFlows;
    }
}
