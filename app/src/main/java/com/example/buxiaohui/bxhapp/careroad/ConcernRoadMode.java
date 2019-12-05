package com.example.buxiaohui.bxhapp.careroad;

public class ConcernRoadMode {
    private String name;
    private String desc;
    private Object obj;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConcernRoadMode{");
        sb.append("name='").append(name).append('\'');
        sb.append(", desc='").append(desc).append('\'');
        sb.append(", obj=").append(obj);
        sb.append('}');
        return sb.toString();
    }
}
