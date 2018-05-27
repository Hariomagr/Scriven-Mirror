package com.example.dellpc.scrivenmirror;

public class Submissions {
    Integer likes;
    String name;
    String data;
    String status;
    String mail;
    String lang;
    public Submissions(){}
    public Submissions(String name,String data,String status,String mail,String lang,Integer likes){
        this.name=name;
        this.data=data;
        this.status=status;
        this.mail=mail;
        this.lang=lang;
        this.likes=likes;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
