package com.cuc.gin.entity;

import java.io.Serializable;

/**
 * @author : Wang SM.
 * @since : 2024/2/2,  
 **/
public class ChatMsgEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long fromId;
    private Long toId;
    private String msgContent;
    private Long createTime;

    public ChatMsgEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public ChatMsgEntity(Long fromId, Long toId, String msgContent, Long createTime) {
        this.fromId = fromId;
        this.toId = toId;
        this.msgContent = msgContent;
        this.createTime = createTime;
    }
}
