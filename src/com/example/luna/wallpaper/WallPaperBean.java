package com.example.luna.wallpaper;

public class WallPaperBean {

    private int id;
    private int times;
    private String date;
    private String name;
    private String image_pic;

    @Override
    public String toString() {
        return "WallPaperBean{" +
                "id=" + id +
                ", times=" + times +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", image_pic='" + image_pic + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_pic() {
        return image_pic;
    }

    public void setImage_pic(String image_pic) {
        this.image_pic = image_pic;
    }
}
