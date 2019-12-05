package com.example.buxiaohui.bxhapp.histogram;

public class ItemData {
    private String name;
    private boolean select;
    private int itemState;
    private float progress;
    private int width;
    private boolean specialTimeStamp;

    public boolean isSpecialTimeStamp() {
        return specialTimeStamp;
    }

    public void setSpecialTimeStamp(boolean specialTimeStamp) {
        this.specialTimeStamp = specialTimeStamp;
    }

    public ItemData(String name) {
        this.name = name;
    }

    public ItemData(String name, float progress) {
        this.name = name;
        this.progress = progress;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getItemState() {
        return itemState;
    }

    public void setItemState(int itemState) {
        this.itemState = itemState;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

}
