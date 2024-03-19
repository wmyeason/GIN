package com.cuc.gin.mapper;

import com.cuc.gin.entity.TestEntryEntity;

import java.util.List;

/**
 * @author : Wang SM.
 * @since : 2024/2/27,  
 **/
public interface TestEntryMapper {
    List<TestEntryEntity> getAll();
    void removeAll();
    void add(TestEntryEntity entity);

}
