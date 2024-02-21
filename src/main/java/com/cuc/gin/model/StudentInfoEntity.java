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
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("student_info")
public class StudentInfoEntity implements Serializable {
    //关联user表ID
    @TableId(type = IdType.INPUT,value = "user_id")
    private Long userId;

    //学号
    @TableField(value = "student_id")
    private String studentId;

    //专业
    @TableField(value = "field")
    private String field;

    //就读学校
    @TableField(value = "school_name")
    private String schoolName;

    //最后咨询事件
    @TableField(value = "last_consult_time")
    private Date lastConsultTime;


}
