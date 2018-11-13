package com.cesgroup.zw.module.test.entity;

import com.cesgroup.zw.framework.base.entity.BaseEntity;
import lombok.Data;

@Data
public class Action implements BaseEntity<String> {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String s) {
        this.id=s;
    }
}
