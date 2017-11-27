package com.wollon.tourgass.dao;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by JimMa on 2017/11/27.
 */

public class Plan extends DataSupport {
    private int id;
    private String title;
    private String Detail;
    private Date date;
    //依赖
    private User user;
    private Scene scene;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
