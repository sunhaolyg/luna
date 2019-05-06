package com.example.luna.note;

/**
 * Created by sunhao on 17-11-1.
 */

public class NoteBean {

    private String date;
    private String total;
    private String income;
    private String scale;

    @Override
    public String toString() {
        return "NoteBean{" +
                "date='" + date + '\'' +
                ", total='" + total + '\'' +
                ", income='" + income + '\'' +
                ", scale='" + scale + '\'' +
                '}';
    }

    public NoteBean(String date, String total, String income, String scale) {
        this.date = date;
        this.total = total;
        this.income = income;
        this.scale = scale;
    }

    public NoteBean() {
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

}