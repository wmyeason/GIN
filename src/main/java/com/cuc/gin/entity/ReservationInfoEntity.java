package com.cuc.gin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : rf.
 * @since : 2024/3/9,
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("reservationinfo")
public class ReservationInfoEntity implements Serializable {
    @TableId
    private String id;

    @TableField("startTime")
    private String startTime;

    @TableField("endTime")
    private String endTime;

    @TableField("userId")
    private String userId;

    @TableField("createdBy")
    private String createdBy;

    @TableField("createdTime")
    private Date createdTime;

    private String place;

    @TableField("consistentTime")
    private Float consistentTime;

    @TableField("reservationDate")
    private String reservationDate;

    @TableField("consultId")
    private String consultId;

    @TableField(exist = false)
    private String userName;

}
