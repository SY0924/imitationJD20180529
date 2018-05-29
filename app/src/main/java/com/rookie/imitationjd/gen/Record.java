package com.rookie.imitationjd.gen;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 暗夜 on 2018/5/26.
 */
@Entity
public class Record {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    @Generated(hash = 623897879)
    public Record(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 477726293)
    public Record() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
