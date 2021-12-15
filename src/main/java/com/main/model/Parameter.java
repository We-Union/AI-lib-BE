package com.main.model;

public class Parameter {
    private long id;
    public long uid;
    public String name;
    public String value;
    private String model;
    public String type;

    public long getId()
    {
        return id;
    }
    public long getUid()
    {
        return uid;
    }
    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }
}
