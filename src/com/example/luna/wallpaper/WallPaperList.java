package com.example.luna.wallpaper;

import java.util.List;

public class WallPaperList {

    private int success;
    private List<WallPaperBean> paper_list;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<WallPaperBean> getPaper_list() {
        return paper_list;
    }

    public void setPaper_list(List<WallPaperBean> paper_list) {
        this.paper_list = paper_list;
    }
}
