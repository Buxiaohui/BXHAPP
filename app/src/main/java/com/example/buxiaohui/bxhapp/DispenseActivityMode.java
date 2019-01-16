package com.example.buxiaohui.bxhapp;

public class DispenseActivityMode {
    private Class aClass;
    private String desc;

    public DispenseActivityMode(String desc, Class aClass) {
        this.aClass = aClass;
        this.desc = desc;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mode{");
        sb.append("aClass=").append(aClass);
        sb.append(", desc='").append(desc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
