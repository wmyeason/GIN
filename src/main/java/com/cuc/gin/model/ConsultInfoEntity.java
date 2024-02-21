package com.cuc.gin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("consult_info")
public class ConsultInfoEntity implements Serializable {
    //关联user表ID
    @TableId(type = IdType.INPUT)
    private Long userId;

    //专业领域
    @TableField(value = "profession_field")
    private String professionField;

    //学历
    @TableField(value = "education")
    private String education;

    //毕业学校
    @TableField(value = "graduation_school")
    private String graduationSchool;

    //个人简介
    @TableField(value = "personal_profile")
    private String personalProfile;

    //工作经验
    @TableField(value = "work_experience")
    private String workExperience;
}
