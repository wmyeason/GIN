package com.cuc.gin.mapper;

import com.cuc.gin.model.BoardEntity;

import java.util.List;

/**
 * @author : Wang SM.
 * @since : 2020/2/26, 周三
 **/
public interface BoardMapper {

    List<BoardEntity> getAll();

    void updateOne(BoardEntity boardEntity);
}
