package com.cuc.gin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cuc.gin.mapper.UserMapper;
import com.cuc.gin.entity.ReservationInfoEntity;
import com.cuc.gin.entity.UserEntity;
import com.cuc.gin.service.ReservationInfoService;
import com.cuc.gin.util.HTTPMessage;
import com.cuc.gin.util.HTTPMessageCode;
import com.cuc.gin.util.HTTPMessageText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author : rf.
 * @since : 2024/3/10,
 **/
@RestController
@RequestMapping("/reservation")
public class ReservationInfoController {
    @Autowired
    private ReservationInfoService reservationInfoService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/getConsultReservationByDate")
    public HTTPMessage<IPage<ReservationInfoEntity>> getConsultReservationByDate(@RequestParam Map map) {
        String startTime = map.get("startTime").toString();
        String endTime = map.get("endTime").toString();
        String consultId = map.get("consultId").toString();
        String currentPage = map.get("currentPage").toString();
        String pageSize = map.get("pageSize").toString();

        IPage<ReservationInfoEntity> consultReservationByDate = reservationInfoService.getConsultReservationByDate(startTime, endTime, consultId, currentPage, pageSize);

        List<ReservationInfoEntity> records = consultReservationByDate.getRecords();
        for(ReservationInfoEntity reservationInfo : records){
            if(reservationInfo.getUserId()!=null && StringUtils.hasText(reservationInfo.getUserId())){
                UserEntity one = userMapper.getOne(Long.valueOf(reservationInfo.getUserId()));
                reservationInfo.setUserName(one.getUsername());
                reservationInfo.setNickName(one.getNickname());
            }
        }

        return new HTTPMessage<>(
                HTTPMessageCode.Register.OK,
                HTTPMessageText.Register.OK,
                consultReservationByDate
        );
    }

    @GetMapping("/getConsultReservationInfo")
    public HTTPMessage<IPage<ReservationInfoEntity>> getConsultReservationInfo(@RequestParam Map map) {
        String consultId = map.get("consultId").toString();
        String currentPage = map.get("currentPage").toString();
        String pageSize = map.get("pageSize").toString();

        IPage<ReservationInfoEntity> consultReservationByDate = reservationInfoService.getConsultReservationInfo( consultId, currentPage, pageSize);


        return new HTTPMessage<>(
                HTTPMessageCode.Register.OK,
                HTTPMessageText.Register.OK,
                consultReservationByDate
        );
    }

    @PostMapping(value = "/userAddReservationInfoById")
    public HTTPMessage<Void> userAddReservationInfoById(@RequestBody Map map) {
        String id = map.get("id").toString();
        String userId = map.get("userId").toString();

        reservationInfoService.userAddReservationInfoById(id , userId);
        return new HTTPMessage<>(
                HTTPMessageCode.Register.OK,
                HTTPMessageText.Register.OK,
                null
        );
    }

    @PostMapping(value = "/consultAddReservationByDate")
    public HTTPMessage<String> consultAddReservationByDate(@RequestBody Map map) {
        String startTime = map.get("reservationDate").toString();
        String startHour = map.get("reservationStartHour").toString();
        String endHour = map.get("reservationEndHour").toString();
        String consultId = map.get("consultId").toString();
        String place = map.get("place").toString();

        //String formatDate = DateUtils.format(startTime);

        //添加前先校验 这个时间段是否与别人预约时间重复
        boolean  res = reservationInfoService.checkReservationTimeRepeat(startTime,startHour,endHour,consultId);
        if(res){
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE,
                    "已存在相冲突的时间！"
            );
        }
        reservationInfoService.AddReservationByDate(startTime,startHour,endHour,consultId,place);

        return new HTTPMessage<>(
                HTTPMessageCode.Register.OK,
                HTTPMessageText.Register.OK,
                null
        );
    }

    @DeleteMapping(value = "/deleteReservationInfoById/{id}")
    public HTTPMessage<Void> deleteReservationInfoById(@PathVariable String id) {
        reservationInfoService.deleteReservationInfoById(id);

        return new HTTPMessage<>(
                HTTPMessageCode.Register.OK,
                HTTPMessageText.Register.OK,
                null
        );
    }
}
