package com.cuc.gin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuc.gin.entity.ReservationInfoEntity;
import com.cuc.gin.mapper.ReservationInfoMapper;
import com.cuc.gin.service.ReservationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationInfoServiceImpl extends ServiceImpl<ReservationInfoMapper, ReservationInfoEntity> implements ReservationInfoService {

    @Autowired
    private ReservationInfoMapper reservationInfoMapper;

    @Override
    public IPage<ReservationInfoEntity> getConsultReservationByDate(String startTime, String endTime, String consultId, String currentPage, String pageSize) {
        Page<ReservationInfoEntity> page = new Page<>(Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        QueryWrapper<ReservationInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("reservationDate", startTime,endTime);
        queryWrapper.eq("consultId", consultId);

        queryWrapper.orderByAsc("reservationDate");

        return reservationInfoMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void userAddReservationInfoById(String id, String userId) {
        ReservationInfoEntity reservationInfoEntity = new ReservationInfoEntity();
        reservationInfoEntity.setId(id);
        reservationInfoEntity.setUserId(userId);
        reservationInfoMapper.updateById(reservationInfoEntity);
    }

    @Override
    public boolean checkReservationTimeRepeat(String startTime, String startHour, String endHour, String consultId) {
        QueryWrapper<ReservationInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper->wrapper.eq("reservationDate",startTime));
        queryWrapper.and(wrapper->wrapper.eq("consultId",consultId));
//        queryWrapper.and(wrapper -> wrapper
//                        .le("startTime", endHour)
//                        .ge("endTime", startHour))
//                .or(wrapper -> wrapper
//                        .ge("startTime", startHour)
//                        .le("startTime", endHour))
//                .or(wrapper -> wrapper
//                        .ge("endTime", startHour)
//                        .le("endTime", endHour));



        queryWrapper.and(wrapper -> wrapper
                        .lt("startTime", endHour)
                        .gt("endTime", startHour)
                        .or().gt("startTime", startHour).lt("startTime", endHour)
                        .or().gt("endTime", startHour).lt("endTime", endHour));


        List<ReservationInfoEntity> reservationInfoEntities = reservationInfoMapper.selectList(queryWrapper);
        return null != reservationInfoEntities && reservationInfoEntities.size()>0;
    }

    @Override
    public void AddReservationByDate(String startTime, String startHour, String endHour, String consultId, String place) {
        ReservationInfoEntity reservationInfoEntity = new ReservationInfoEntity();
        reservationInfoEntity.setStartTime(startHour);
        reservationInfoEntity.setEndTime(endHour);
        reservationInfoEntity.setReservationDate(startTime);
        reservationInfoEntity.setId(UUID.randomUUID().toString());
        reservationInfoEntity.setConsultId(consultId);
        reservationInfoEntity.setCreatedTime(new Date());
        reservationInfoEntity.setCreatedBy(consultId);
        reservationInfoEntity.setPlace(place);
        reservationInfoMapper.insert(reservationInfoEntity);
    }

    @Override
    public void deleteReservationInfoById(String id) {
        reservationInfoMapper.deleteById(id);
    }

    @Override
    public IPage<ReservationInfoEntity> getConsultReservationInfo(String consultId, String currentPage, String pageSize) {
        Page<ReservationInfoEntity> page = new Page<>(Integer.parseInt(currentPage), Integer.parseInt(pageSize));
        QueryWrapper<ReservationInfoEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("consultId", consultId);
        queryWrapper.and(wrapper -> wrapper.isNull("userId").or().eq("userId", ""));

        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(currentDate);

        queryWrapper.ge("reservationDate", formattedDate);

        return reservationInfoMapper.selectPage(page, queryWrapper);
    }
}
