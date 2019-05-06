package com.example.luna.java;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;


/**
 * Created by sunhao on 17-10-19.
 */

public class ArrayActivity extends BaseActivity {

    private int[] arrs = {1, 8, 9, 45, 36, 15, 7, 819, 190, 699, 100};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fizzed(arrs);
//        intsert(arrs);
        select(arrs);
    }

    private void select(int[] arrs) {
    }

    private void intsert(int[] arrs) {
        int lengh=arrs.length;
        for(int i=0;i<lengh-1;i++){
            
        }
    }

    private void fizzed(int[] arr) {
        int lengh = arr.length;

        for (int i = 0; i < lengh - 1; i++) {
            for (int j = 0; j < lengh - i - 1; j++) {
                if (arr[j + 1] < arr[j]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < lengh - 1; i++) {

            Logutils.d("SwitchView", "end=" + arr[i]);
        }
    }
}
