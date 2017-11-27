package com.wollon.tourgass.dao;

import org.litepal.crud.DataSupport;

/**
 * Created by JimMa on 2017/11/27.
 */

public class Scene extends DataSupport {
    private int id;
    private String poiKey;
    private double lat;
    private double log;
    private String Title;
    private String inturduce;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoiKey() {
        return poiKey;
    }

    public void setPoiKey(String poiKey) {
        this.poiKey = poiKey;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getInturduce() {
        return inturduce;
    }

    public void setInturduce(String inturduce) {
        this.inturduce = inturduce;
    }
}