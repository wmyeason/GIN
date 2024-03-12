package com.cuc.gin.vo;

import com.cuc.gin.model.ConsultInfoEntity;
import com.cuc.gin.model.StudentInfoEntity;
import com.cuc.gin.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : rf.
 * @since : 2024/2/22,
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserExtendInfo implements Serializable {
    private UserEntity userEntity;

    private ConsultInfoEntity consultInfo;

    private StudentInfoEntity studentInfo;
}
