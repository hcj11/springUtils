package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;

@Data
@Entity(name="goods")
public class Goods implements Serializable {
    public static final long serialVersionUID = 1L;

    // @Version 乐观锁，redis? 提供?
    @Id
    private Integer id;
    @Column
    private String name;
    // 数量
    @Column
    private Integer quantity;
    @Version
    @Column
    private Integer version;

    public Goods(String name, Integer quantity, Integer version) {
        this.name = name;
        this.quantity = quantity;
        this.version = version;
    }
}