package com.cuc.gin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuc.gin.mapper.StudentInfoMapper;
import com.cuc.gin.entity.StudentInfoEntity;
import com.cuc.gin.service.StudentInfoService;
import org.springframework.stereotype.Service;


@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfoEntity> implements StudentInfoService {
}
