package com.cuc.gin.mapper;

import com.cuc.gin.model.ChatMsgEntity;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @author : Wang SM.
 * @since : 2024/2/2,  
 **/
public interface ChatMsgMapper {
    void insert(ChatMsgEntity msg);

    List<ChatMsgEntity> queryAll();

    List<ChatMsgEntity> queryByFromTo(@Param("fromId")Long fromId, @Param("toId")Long toId);

}
