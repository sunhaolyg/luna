package com.example.luna.poker.gold;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.poker.Poker;
import com.example.luna.utils.Logutils;

import java.util.*;

public class GoldActivity extends BaseActivity {

    private List<Poker> mPokers = new ArrayList<>();
    private Button show;
    private Button result, random;

    private int count;
    private List<IndexComb> mCompare = new ArrayList<>();

    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            mHander.sendEmptyMessageDelayed(1, 100);
//            random.callOnClick();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold);

        random = findViewById(R.id.poker_random);
        show = findViewById(R.id.poker_id);
        result = findViewById(R.id.poker_result);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                random.setText(count++ + "");
//                refresh();
                mPokers.clear();
                mCompare.clear();
                createCompare();

            }
        });
//        refresh();
//        mHander.sendEmptyMessageDelayed(1, 2000);
        createCompare();
    }

    private void createCompare() {
        one();
//        for (IndexComb compareIndex : mCompare) {
//            Logutils.d("WaterFallLayout", "poker =====" + compareIndex);
//
//        }
        List<Poker> pokers = createGoldFlower(9);
        TripleGoldFlowComb tripleGoldFlowComb = new TripleGoldFlowComb(pokers, mCompare);
        tripleGoldFlowComb.calculate();
        List<TripleGoldFlow> tripleGoldFlows = tripleGoldFlowComb.getTripleGoldFlows();
        result.setText("");
        show.setText("");
        for (Poker poker : pokers) {
            show.append(poker.getNumberWithColor() + ",");
        }
        result.append(tripleGoldFlows.get(tripleGoldFlows.size() - 1).toString());
        for (TripleGoldFlow poker : tripleGoldFlows) {
            if (poker.getTypePlus() != 0) {
                Logutils.d("WaterFallLayout", "poker =====" + poker);
                result.append(poker.toString());
            }
        }
        Logutils.d("WaterFallLayout", mCompare.size() + " =====" + tripleGoldFlows.size());
    }

    private void one() {
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                for (int k = j + 1; k < 9; k++) {
//                    Logutils.d("WaterFallLayout", "one =" + i + "," + j + "," + k);
                    two(i, j, k);
                }
            }
        }
    }

    private void two(Integer... a) {
        List<Integer> b = new ArrayList<>(Arrays.asList(a));
        for (int i = 0; i < 9; i++) {
            if (b.contains(i)) {
                continue;
            }
            for (int j = i + 1; j < 9; j++) {
                if (b.contains(j)) {
                    continue;
                }
                for (int k = j + 1; k < 9; k++) {
                    if (b.contains(k)) {
                        continue;
                    }
//                    Logutils.d("WaterFallLayout", "two =====" + i + "," + j + "," + k);
                    three(a, i, j, k);
                }
            }
        }
    }

    private void three(Integer[] a, Integer... c) {
        IndexComb compare = null;
        List<Integer> b = new ArrayList<>(Arrays.asList(a));
        for (Integer i : c) {
            b.add(i);
        }
        for (int i = 0; i < 9; i++) {
            if (b.contains(i)) {
                continue;
            }
            for (int j = i + 1; j < 9; j++) {
                if (b.contains(j)) {
                    continue;
                }
                for (int k = j + 1; k < 9; k++) {
                    if (b.contains(k)) {
                        continue;
                    }
                    compare = new IndexComb(new IndexComb.GoldIndex(a), new IndexComb.GoldIndex(c), new IndexComb.GoldIndex(i, j, k));
                    if (!mCompare.contains(compare)) {
                        mCompare.add(compare);
//                        Logutils.d("WaterFallLayout", "three =======================" + i + "," + j + "," + k);
                    }

                }
            }
        }

    }

    private List<Poker> createGoldFlower(int size) {
        List<Poker> pokers = new ArrayList<>();
        do {
            pokers.add(createPoker(mPokers));
        } while (pokers.size() != size);
        sortPokers(pokers);
        return pokers;
    }

    private void createGoldFlower() {
        createGoldFlower(3);
    }

    private Poker createPoker(List<Poker> pokers) {
        int id = 0;
        Poker poker = new Poker();
        do {
            id = PokerUtils.random();
            poker.setId(id);
        } while (pokers.contains(poker));
        pokers.add(poker);
        return poker;
    }

    private void refresh() {
        mPokers.clear();

        do {
            createPoker(mPokers);
        } while (mPokers.size() == 3);

        sortPokers(mPokers);

        String text = pokers2String(mPokers);
        show.setText(text);
        int re = PokerUtils.transResult(mPokers);
        result.setText(PokerUtils.result2String(re));
        if (re == 5) {
            mHander.removeMessages(1);
        }
    }

    private void sortPokers(List<Poker> pokers) {
        Collections.sort(pokers, new Comparator<Poker>() {
            @Override
            public int compare(Poker o1, Poker o2) {
                return o1.getId() - o2.getId();
            }
        });
    }

    private String pokers2String(List<Poker> pokers) {
        String text = "";
        for (Poker poker : pokers) {
            text += poker.getNumberWithColor() + ",";
        }
        return text;
    }
}
