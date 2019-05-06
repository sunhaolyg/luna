package com.example.luna.poker.gold;

import java.util.ArrayList;
import java.util.List;

public class IndexComb {

    private GoldIndex a, b, c;

    public IndexComb(GoldIndex a, GoldIndex b, GoldIndex c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public List<Integer> getIndexList() {
        List<Integer> integers = new ArrayList<>();
        integers.add(a.getI());
        integers.add(a.getJ());
        integers.add(a.getK());
        integers.add(b.getI());
        integers.add(b.getJ());
        integers.add(b.getK());
        integers.add(c.getI());
        integers.add(c.getJ());
        integers.add(c.getK());
        return integers;
    }

    public GoldIndex getA() {
        return a;
    }

    public void setA(GoldIndex a) {
        this.a = a;
    }

    public GoldIndex getB() {
        return b;
    }

    public void setB(GoldIndex b) {
        this.b = b;
    }

    public GoldIndex getC() {
        return c;
    }

    public void setC(GoldIndex c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return a + "," + b + "," + c;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IndexComb)) {
            return false;
        }
        IndexComb compare = (IndexComb) obj;

        if (this.a.equals(compare.a) && this.b.equals(compare.c) && this.c.equals(compare.b)) {
            return true;
        }

        if (this.a.equals(compare.b)) {
            if (this.b.equals(compare.a) && this.c.equals(compare.c)) {
                return true;
            }
            if (this.b.equals(compare.c) && this.c.equals(compare.a)) {
                return true;
            }
        }
        if (this.a.equals(compare.c)) {
            if (this.b.equals(compare.a) && this.c.equals(compare.b)) {
                return true;
            }
            if (this.b.equals(compare.b) && this.c.equals(compare.a)) {
                return true;
            }
        }
        if (this.b.equals(compare.a)) {
            if (this.a.equals(compare.c) && this.c.equals(compare.b)) {

            }
        }
        if (!this.a.equals(compare.a) || !this.b.equals(compare.b) || !this.c.equals(compare.c)) {
            return false;
        }
        return true;
    }

    public static class GoldIndex {
        private int i, j, k;

        public GoldIndex(Integer[] a) {
            this.i = a[0];
            this.j = a[1];
            this.k = a[2];
        }

        public GoldIndex(int i, int j, int k) {
            this.i = i;
            this.j = j;
            this.k = k;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }

        public int getK() {
            return k;
        }

        public void setK(int k) {
            this.k = k;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof GoldIndex)) {
                return false;
            }
            GoldIndex three = (GoldIndex) obj;
            if (this.i != three.i || this.j != three.j || this.k != three.k) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return " " + i + "," + j + "," + k;
        }
    }
}
