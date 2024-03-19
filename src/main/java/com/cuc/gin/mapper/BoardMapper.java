package com.cuc.gin.mapper;

import com.cuc.gin.entity.BoardEntity;

import java.util.List;

/**
 * @author : Wang SM.
 * @since : 2024/2/26,  
 **/
public interface BoardMapper {

    List<BoardEntity> getAll();

    void updateOne(BoardEntity boardEntity);
}
