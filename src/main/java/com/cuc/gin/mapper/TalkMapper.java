package com.cuc.gin.mapper;

import com.cuc.gin.entity.TalkEntity;

import java.util.List;

/**
 * @author : Wang SM.
 * @since : 2024/3/9,  
 **/
public interface TalkMapper {
    void add(TalkEntity talkEntity);
    void remove(Long id);
    List<TalkEntity> getAll();
    void update(TalkEntity talkEntity);
}
