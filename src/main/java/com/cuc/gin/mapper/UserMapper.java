package com.cuc.gin.mapper;

import com.cuc.gin.model.UserEntity;

import java.util.List;

/**
 * @author : Wang SM.
 * @since : 2020/1/26, 周日
 **/
public interface UserMapper {
    List<UserEntity> getAll(Integer type);

    UserEntity getOne(Long id);

    UserEntity getOneByUsername(String username);

    String getPasswordByUsername(String username);

    void add(UserEntity user);

    void updateOne(UserEntity user);

    void removeOne(Long id);

}
