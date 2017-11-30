package com.wollon.tourgass.dto;

import com.amap.api.services.core.PoiItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JimMa on 2017/11/30.
 */

public class PoiListZb implements Serializable {
    private List<PoiItem> poiItemList;

    public PoiListZb(List<PoiItem> poiItemList) {
        this.poiItemList = poiItemList;
    }

    public List<PoiItem> getPoiItemList() {
        return poiItemList;
    }

    public void setPoiItemList(List<PoiItem> poiItemList) {
        this.poiItemList = poiItemList;
    }
}
