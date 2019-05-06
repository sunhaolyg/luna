package com.example.luna.leetcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.luna.R;
import com.example.luna.base.BaseActivity;
import com.example.luna.utils.Logutils;

import java.util.Collections;

public class BeforeActivity extends BaseActivity {

    private static final int[] A = {2, 1, 2};
    private static final int[] B = {4, 1, 2, 1, 2};
    private static final String TAG = "BeforeActivity";

    private static final int[] C = {3, 2, 3};
    private static final int[] D = {2, 2, 1, 1, 1, 2, 2};
    private static final int[] E = {2, 7, 11, 15};
    private static final String F = "pwwkew";
    private static final String G = "abcabcbb";
    private static final String H = "LEETCODEISHIRING";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before);
        int i = singleNumber(A);
        int j = singleNumber(B);
        Logutils.d(TAG, "i = " + i + ",j = " + j);

        int m = majorityElement(C);
        int n = majorityElement(D);
        Logutils.d(TAG, "m = " + m + ",n = " + n);

        int[] towSum = twoSum(E, 9);
        Logutils.d(TAG, "towSum 1 = " + towSum[0] + ",towSum 2 = " + towSum[1]);

        Logutils.d(TAG, "tag = ");

        String convert2 = convert4(H, 6);
        Logutils.d(TAG, "tag = " + convert2);
        String convert3 = convert2(H, 6);
        Logutils.d(TAG, "tag = " + convert3);
    }

    private String convert4(String s, int numRows) {
        StringBuffer result = new StringBuffer();
        char[][] arr = new char[numRows][s.length()];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                result.append(arr[i][i]);
            }
        }

        /**
         * ###################################
         * 输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
         * L   C   I   R
         * E T O E S I I G
         * E   D   H   N
         * ###################################
         * 输入字符串为 "LEETCODEISHIRING" 行数为 4 时，排列如下：
         * L     D     R
         * E   O E   I I
         * E C   I H   N
         * T     S     G
         * ###################################
         * 输入字符串为 "LEETCODEISHIRING" 行数为 5 时，排列如下：
         * L       I
         * E     E S   G
         * E   D   H  N
         * T O     I I
         * C       R
         * ###################################
         * 输入字符串为 "LEETCODEISHIRING" 行数为 6 时，排列如下：
         * L     H
         * E    SI
         * E   I R
         * T  E  I
         * C D   N
         * O     G
         */
        return result.toString();
    }


    /**
     * https://blog.csdn.net/zhangjingao/article/details/82994097
     * 通过
     *
     * @param s
     * @param numRows
     * @return
     */
    private String convert3(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        int index = 0;
        char[][] arr = new char[numRows][s.length()];
        int col = 0;
        int row = 0;
        while (index < s.length()) {
            while (row < numRows && index < s.length()) {
                arr[row++][col] = s.charAt(index++);
            }
            row--;
            while (row > 0 && index < s.length()) {
                arr[--row][++col] = s.charAt(index++);
            }
            row++;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != '\u0000') {
                    result.append(arr[i][j]);
                }
            }

        }
        return result.toString();
    }

    /**
     * 未通过，时间，内存超出限制
     *
     * @param s
     * @param numRows
     * @return
     */
    private String convert2(String s, int numRows) {
        int index = 0;
        StringBuffer result = new StringBuffer();
        int splitIndex1 = (numRows - 1) * 2;

        for (int i = 0; i < numRows; i++) {
            index = i;
            while (index < s.length()) {
                result.append(s.charAt(index));
                if (i == numRows - 1) {
                    index += splitIndex1;
                } else if (index % splitIndex1 == i) {
                    index += (numRows - (i + 1)) * 2;
                } else {
                    index += i * 2;
                }
            }
        }
        return result.toString();
    }

//convert3 = LCIR ETOESIIG EDHN,convert4 = LDR EOEII ECIHN TSG,convert5 = LI EESG EDHN TOII CR,convert6 = LH ESI EIR TEI CDN OG

    /**
     * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
     * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
     * L   C   I   R
     * E T O E S I I G
     * E   D   H   N
     * s[0] : L      s[4] : C      s[8] : I      s[12]: R
     * <p>
     * s[1] : E     s[3] : T     s[5] : O     s[7] : E     s[9] : S     s[11]: I     s[13]: I     s[15]: G
     * <p>
     * s[2] : E     s[6] : D     s[10]: H     s[14]: N
     * ###################################
     * 输入字符串为 "LEETCODEISHIRING" 行数为 4 时，排列如下：
     * L     D     R
     * E   O E   I I
     * E C   I H   N
     * T     S     G
     * s[0] : L     s[6] : D     s[12]: R
     * <p>
     * s[1] : E     s[5] : O     s[7] : E     s[11]: I     s[13]: I
     * <p>
     * s[2] : E     s[4] : C     s[8] : I     s[10]: H     s[14]: N
     * <p>
     * s[3]: T     s[9]: S     s[15]:G
     * ###################################
     * 输入字符串为 "LEETCODEISHIRING" 行数为 5 时，排列如下：
     * L       I
     * E     E S   G
     * E   D   H  N
     * T O     I I
     * C       R
     * ###################################
     * 输入字符串为 "LEETCODEISHIRING" 行数为 6 时，排列如下：
     * L     H
     * E    SI
     * E   I R
     * T  E  I
     * C D   N
     * O     G
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        StringBuffer result = new StringBuffer();
        int index = 0;
        //第一行下标间隔
        int splitIndex1 = (numRows - 1) * 2;
        /**
         * 第一行
         * 3:0,4,8,12
         * 4:0,6,12
         * 5:0,8**/
        while (index < s.length()) {
            result.append(s.charAt(index));
            index += splitIndex1;
        }

        /**
         * 第二行
         * 3:1,3,5,7,9,11,13,15
         * 4:1,5,7,11,13
         * 5:1,7,9,15
         * 6:1,9,11
         */
        index = 1;
        while (index < s.length()) {
            result.append(s.charAt(index));
            if (index % splitIndex1 == 1) {
                index += (numRows - (1 + 1)) * 2;
            } else {
                index += 2;
            }
        }
        /**
         * 第三行
         * numRows(splitIndex1)
         * 3(4):2,6,10,14;4,4
         * 4(6):2,4,8,10,14;2,4
         * 5(8):2,6,10,14;  4,4
         * 6(10):2,8,12;    6,4
         * **/
        index = 2;
        while (index < s.length()) {
            result.append(s.charAt(index));
            if (numRows == 2 + 1) {
                index += splitIndex1;
            } else if (index % splitIndex1 == 2) {
                index += (numRows - (2 + 1)) * 2;
            } else {
                index += 4;
            }
        }
        /**
         * 第四行
         * 4(6):3,9,15
         * 5(8):3,5,11,13; 2,6
         * 6(10):3,7,13;   4,6
         */
        if (numRows <= 3) {
            return result.toString();
        }
        index = 3;
        while (index < s.length()) {
            result.append(s.charAt(index));
            if (numRows == 3 + 1) {
                index += splitIndex1;
            }
            if (index % splitIndex1 == 3) {
                index += (numRows - (3 + 1)) * 2;
            } else {
                index += 6;
            }
        }
        /**
         * 第五行
         * 5:4,12
         * 6:4,6,14
         */
        if (numRows <= 4) {
            return result.toString();
        }
        index = 4;
        while (index < s.length()) {
            result.append(s.charAt(index));
            if (numRows == 4 + 1) {
                index += splitIndex1;
            }
            if (index % splitIndex1 == 4) {
                index += (numRows - (4 + 1)) * 2;
            } else {
                index += 8;
            }
        }
        return result.toString();
    }

    /**
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!temp.toString().contains(c + "")) {
                temp.append(c);
            }
        }
        return temp.length();
    }

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 众数
     *
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            count = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    count++;
                }
            }
            if (count > nums.length / 2) {
                return nums[i];
            }
        }
        return 0;
    }

    /**
     * 找出值只出现一次的数字
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            count = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    count++;
                }
            }
            if (count == 1) {
                return nums[i];
            }
        }
        return 0;
    }

}
