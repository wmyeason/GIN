package com.cuc.gin.mapper;

import com.cuc.gin.model.PostEntity;

import java.util.List;

/**
 * @author : Wang SM.
 * @since : 2024/2/26, 周三
 **/
public interface PostMapper {
    PostEntity getOne(Long id);
    List<PostEntity> getAll();
    void insertOne(PostEntity postEntity);
    void deleteOne(Long id);
}
