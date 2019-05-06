package com.example.luna.view.dn.attr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;

public class AttrActivity extends BaseActivity {

    private static final int[] SORT = {4, 3, 5, 7, 2, 8, 1, 9, 6};
    /*
     * i=0:
     *   j=0:3,4,5,7,2, 8, 1, 9, 6
     *   j=1:3,4,5,7,2, 8, 1, 9, 6
     *   j=2:3,4,5,7,2, 8, 1, 9, 6
     *   j=3:3,4,5,2,7, 8, 1, 9, 6
     *   j=4:3,4,5,2,7, 8, 1, 9, 6
     *   j=5:3,4,5,2,7, 1, 8, 9, 6
     *   j=6:3,4,5,2,7, 1, 8, 9, 6
     *   j=7:3,4,5,2,7, 1, 8, 6, 9
     *  i=1:
     *   j=0 : 3,4,5,2,7, 1, 8, 6, 9
     *   j=1 : 3,4,5,2,7, 1, 8, 6, 9
     *   j=2 : 3,4,2,5,7, 1, 8, 6, 9
     *   j=3 : 3,4,2,5,7, 1, 8, 6, 9
     *   j=4 : 3,4,2,5,1, 7, 8, 6, 9
     *   j=5 : 3,4,2,5,1, 7, 8, 6, 9
     *   j=6 : 3,4,2,5,1, 7, 6, 8, 9
     * i=2:
     *   j=0 : 3,4,5,2,1, 7, 6, 8, 9
     *   j=1 : 3,4,5,2,1, 7, 6, 8, 9
     *   j=2 : 3,4,2,5,1, 7, 6, 8, 9
     *   j=3 : 3,4,2,5,1, 7, 6, 8, 9
     *   j=4 : 3,4,2,5,1, 7, 6, 8, 9
     *   j=5 : 3,4,2,5,1, 6, 7, 8, 9
     * i=3:
     *   j=0 : 3,4,2,5,1, 6, 7, 8, 9
     *   j=1 : 3,2,4,5,1, 6, 7, 8, 9
     *   j=2 : 3,2,4,5,1, 6, 7, 8, 9
     *   j=3 : 3,2,4,1,5, 6, 7, 8, 9
     *   j=4 : 3,2,4,1,5, 6, 7, 8, 9
     * i=4:
     *   j=0 : 2,3,4,1,5, 6, 7, 8, 9
     *   j=1 : 2,3,4,1,5, 6, 7, 8, 9
     *   j=2 : 2,3,1,4,5, 6, 7, 8, 9
     *   j=3 : 2,3,1,4,5, 6, 7, 8, 9
     * i=5:
     *   j=0 : 2,3,1,4,5, 6, 7, 8, 9
     *   j=1 : 2,1,3,4,5, 6, 7, 8, 9
     *   j=2 : 2,1,3,4,5, 6, 7, 8, 9
     * i=6:
     *    j=0 : 1,2,3,4,5, 6, 7, 8, 9
     *    j=1 : 1,2,3,4,5, 6, 7, 8, 9
     * */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attr);
        bubbleSort(SORT);
    }

    private void bubbleSort(int[] nums) {
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        String string = "";
        for (int i = 0; i < length; i++) {
            string += nums[i] + ",";
        }
        Logutils.d("BeforeActivity", string);
    }

}
