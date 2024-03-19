package com.cuc.gin.controller;

import com.cuc.gin.mapper.UserMapper;
import com.cuc.gin.entity.ConsultInfoEntity;
import com.cuc.gin.entity.StudentInfoEntity;
import com.cuc.gin.entity.UserEntity;
import com.cuc.gin.service.ConsultInfoService;
import com.cuc.gin.service.StudentInfoService;
import com.cuc.gin.util.CryptoUtil;
import com.cuc.gin.util.HTTPMessage;
import com.cuc.gin.util.HTTPMessageCode;
import com.cuc.gin.util.HTTPMessageText;
import com.cuc.gin.vo.UserInfoVo;
import com.google.common.base.Strings;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * @author : Wang SM.
 * @since : 2024/1/26,  
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Key jwtKey;

    @Autowired
    private ConsultInfoService consultInfoService;

    @Autowired
    private StudentInfoService studentInfoService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HTTPMessage<UserInfoVo> login(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");

        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password)) {
            throw new IllegalArgumentException();
        }

        UserEntity userEntity = userMapper.getOneByUsername(username);
        if (userEntity != null && userEntity.getPassword().equals(CryptoUtil.getHashedPassword(password))) {
            // Sign JWT token
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.HOUR_OF_DAY, 1);
            String jws = Jwts.builder().setSubject(userEntity.getId().toString()).setExpiration(cal.getTime()).signWith(jwtKey).compact();
            UserInfoVo userInfo = new UserInfoVo(userEntity.getId(), userEntity.getUsername(), jws, userEntity.getStatus(),userEntity.getIsConsultant()+"");
            return new HTTPMessage<>(
                    HTTPMessageCode.Login.OK,
                    HTTPMessageText.Login.OK,
                    userInfo
            );
        } else {
            return new HTTPMessage<>(
                    HTTPMessageCode.Login.FAILURE,
                    HTTPMessageText.Login.FAILURE
            );
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @Transactional
    public HTTPMessage<UserEntity> register(@RequestBody Map map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        Boolean isConsult = (Boolean) map.get("isConsult");
        if (Strings.isNullOrEmpty(username)
                || Strings.isNullOrEmpty(password)) {
            throw new IllegalArgumentException();
        }
        UserEntity userEntity = userMapper.getOneByUsername(username);
        if (userEntity != null) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Register.EXISTS,
                    HTTPMessageText.Register.EXISTS
            );
        }
        Integer consult = isConsult?1:0;//   1 是咨询师 关联ConsultInfo表  0 普通学生用户 关联StudentInfo表
        UserEntity user = new UserEntity(username,CryptoUtil.getHashedPassword(password),consult);
        userMapper.add(user);
        Long userId = user.getId();
        if(isConsult){
            //关联ConsultInfo表
            ConsultInfoEntity consultInfo = new ConsultInfoEntity();
            consultInfo.setUserId(userId);
            consultInfoService.save(consultInfo);
        }else{
            //关联StudentInfo表
            StudentInfoEntity studentInfo = new StudentInfoEntity();
            studentInfo.setUserId(userId);
            studentInfoService.save(studentInfo);
        }
        return new HTTPMessage<>(
                HTTPMessageCode.Register.OK,
                HTTPMessageText.Register.OK,
                user
        );
    }


}
