package com.cuc.gin.mapper;

import com.cuc.gin.entity.TestResultEntity;

import java.util.List;

/**
 * @author : Wang SM.
 * @since : 2024/2/28,  
 **/
public interface TestResultMapper {
    List<TestResultEntity> getAll();
    void add(TestResultEntity entity);
}
