package com.cuc.gin.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * @author : Wang SM.
 * @since : 2024/2/28,  
 **/
public class TestResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private String result;
    private Integer value;
    private Long createTime;

    @TableField(exist = false)
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TestResultEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
