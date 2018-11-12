package com.androidtown.blackout;

public class ItemList {
    String itemDate;
    String itemTime;

    // 이전 기록 불러오는 item 형식
    //TODO item 형식 만들기
    public ItemList(String itemDate, String itemTime) {
        this.itemDate = itemDate;
        this.itemTime = itemTime;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemTime() {
        return itemTime;
    }

    public void setItemTime(String itemTime) {
        this.itemTime = itemTime;
    }
}
