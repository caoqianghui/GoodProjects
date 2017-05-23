package com.go.cqh.goodprojects.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by caoqianghui on 2016/12/16.
 */
@Entity
public class DBean {
    @Id
    private Long id;
    private String itemId;
    private String json;
    private boolean isCollect;
    @Generated(hash = 1418694175)
    public DBean(Long id, String itemId, String json, boolean isCollect) {
        this.id = id;
        this.itemId = itemId;
        this.json = json;
        this.isCollect = isCollect;
    }
    @Generated(hash = 1325397296)
    public DBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    public boolean getIsCollect() {
        return this.isCollect;
    }
    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }
    public String getItemId() {
        return this.itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
