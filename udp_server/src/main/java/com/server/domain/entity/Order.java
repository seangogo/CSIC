package com.server.domain.entity;

import com.server.domain.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by sean on 2017/6/6.
 */
@Entity
@Table(name = "orders")
public class Order extends IdEntity {
    @Column(length = 10, updatable = false, nullable = false)
    private String developers;
    @Column(length = 2, updatable = false, nullable = false)
    private Short sort;

    private boolean bo;
    private Float num;

    public Order() {
    }

    public Order(boolean bo, Float num) {
        this.bo = bo;
        this.num = num;
    }

    public String getDevelopers() {
        return developers;
    }

    public void setDevelopers(String developers) {
        this.developers = developers;
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public boolean isBo() {
        return bo;
    }

    public void setBo(boolean bo) {
        this.bo = bo;
    }

    public Float getNum() {
        return num;
    }

    public void setNum(Float num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Order{" +
                "developers='" + developers + '\'' +
                ", sort=" + sort +
                ", bo=" + bo +
                ", num=" + num +
                '}';
    }
}
