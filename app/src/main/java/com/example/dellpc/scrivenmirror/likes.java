package com.example.dellpc.scrivenmirror;

/**
 * Created by DELL PC on 09-Jan-18.
 */

public class likes {
    Integer llikes;
    String name;
    String data;
    public likes(){}
    public likes(String name,String data,Integer llikes){
        this.name=name;
        this.data=data;
        this.llikes=llikes;
    }

    public Integer getLlikes() {
        return llikes;
    }

    public void setLlikes(Integer llikes) {
        this.llikes = llikes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
