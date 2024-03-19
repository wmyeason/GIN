package com.cuc.gin.mapper;

import com.cuc.gin.entity.PostEntity;

import java.util.List;

/**
 * @author : Wang SM.
 * @since : 2024/2/26,  
 **/
public interface PostMapper {
    PostEntity getOne(Long id);
    List<PostEntity> getAll();
    void insertOne(PostEntity postEntity);
    void deleteOne(Long id);
}
