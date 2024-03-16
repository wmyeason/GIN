package com.cuc.gin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cuc.gin.model.ReservationInfoEntity;

public interface ReservationInfoService extends IService<ReservationInfoEntity>{
    IPage<ReservationInfoEntity> getConsultReservationByDate(String startTime, String endTime, String consultId, String currentPage, String pageSize);

    void AddReservationByDate(String startTime, String startHour, String endHour, String consultId, String place);

    void deleteReservationInfoById(String id);

    IPage<ReservationInfoEntity> getConsultReservationInfo(String consultId, String currentPage, String pageSize);

    void userAddReservationInfoById(String id, String userId);

    boolean checkReservationTimeRepeat(String startTime, String startHour, String endHour, String consultId);
}
