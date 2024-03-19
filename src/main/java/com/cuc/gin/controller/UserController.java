package com.cuc.gin.controller;

import com.cuc.gin.annotation.AdminRequired;
import com.cuc.gin.mapper.UserMapper;
import com.cuc.gin.entity.ConsultInfoEntity;
import com.cuc.gin.entity.StudentInfoEntity;
import com.cuc.gin.entity.UserEntity;
import com.cuc.gin.service.ConsultInfoService;
import com.cuc.gin.service.StudentInfoService;
import com.cuc.gin.util.*;
import com.cuc.gin.vo.UserExtendInfo;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Wang SM.
 * @since : 2024/2/29,
 **/
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ConsultInfoService consultInfoService;

    @Autowired
    private StudentInfoService studentInfoService;


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @AdminRequired
    public HTTPMessage<List<UserEntity>> getAll(HttpServletRequest request, HttpServletResponse response, @RequestParam("type") Integer type) {
        return new HTTPMessage<>(
                HTTPMessageCode.Common.OK,
                HTTPMessageText.Common.OK,
                userMapper.getAll(type)
        );
    }

    @RequestMapping(value = "/user/getExtendUserInfoByAdmin", method = RequestMethod.GET)
    public HTTPMessage<Object> getExtendUserInfoByAdmin(@RequestParam("isConsultant") boolean isConsultant, @RequestParam("id") String id) {
        if (isConsultant) {
            ConsultInfoEntity byId = consultInfoService.getById(Long.valueOf(id));
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.OK,
                    HTTPMessageText.Common.OK,
                    byId
            );
        } else {
            StudentInfoEntity byId = studentInfoService.getById(Long.valueOf(id));
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.OK,
                    HTTPMessageText.Common.OK,
                    byId
            );
        }
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public HTTPMessage<Void> updateUser(@PathVariable Long id, @RequestBody Map map) {
        UserEntity user = userMapper.getOne(id);
        if (user == null) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE
            );
        }
        String username = (String) map.get("username");
        String nickname = (String) map.get("nickname");
        String contactInfo = (String) map.get("contactInfo");
        String age = (String) map.get("age");
        String gender = (String) map.get("gender");
        if (Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(nickname)) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE
            );
        }
        if (!Strings.isNullOrEmpty(age)) {
            user.setAge(Integer.parseInt(age));
        }
        if (!Strings.isNullOrEmpty(gender)) {
            switch (gender) {
                case "M":
                    user.setGender(Gender.MALE);
                    break;
                case "F":
                    user.setGender(Gender.FEMALE);
                    break;
                default:
                    break;
            }
        }
        user.setUsername(username);
        user.setNickname(nickname);
        user.setContactInfo(contactInfo);
        userMapper.updateOne(user);
        return new HTTPMessage<>(
                HTTPMessageCode.Common.OK,
                HTTPMessageText.Common.OK
        );
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @AdminRequired
    public HTTPMessage<Void> deleteOne(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        UserEntity user = userMapper.getOne(id);
        if (user == null) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE
            );
        }
        userMapper.removeOne(id);
        //删除用户关联表信息
        if (user.getIsConsultant() == 1) {
            consultInfoService.removeById(id);
        } else if (user.getIsConsultant() == 0) {
            studentInfoService.removeById(id);
        }
        response.setStatus(HttpStatus.NO_CONTENT.value());
        return new HTTPMessage<>(
                HTTPMessageCode.Common.OK,
                HTTPMessageText.Common.OK
        );
    }

    @RequestMapping(value = "/user/editUserInfoByAdmin", method = RequestMethod.POST)
    @Transactional
    public HTTPMessage<Void> updateUserByAdmin(@RequestBody Map map) {
        String id = (String) map.get("id");
        UserEntity user = userMapper.getOne(Long.valueOf(id));
        if (user == null) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE
            );
        }
        String newPassword = (String) map.get("password");
        String passAble = (String) map.get("passAble");
        Integer age = (Integer) map.get("age");
        String sex = (String) map.get("sex");
        String contactInfo = (String) map.get("contactInfo");
        user.setContactInfo(contactInfo);

        if (passAble.equals("true") && Strings.isNullOrEmpty(newPassword)) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE
            );
        }
        if (passAble.equals("true")) {
            user.setPassword(CryptoUtil.getHashedPassword(newPassword));
        }
        user.setAge(age);
        if (sex.equals("男") || sex.equals("MALE")) {
            user.setGender(Gender.MALE);
        } else if (sex.equals("女") || sex.equals("FEMALE")) {
            user.setGender(Gender.FEMALE);
        } else {
            user.setGender(Gender.OTHER);
        }

        boolean isConsult = (boolean) map.get("isConsultant");
        if (isConsult) {
            //咨询师修改
            String professionField = (String) map.get("professionField");
            String education = (String) map.get("education");
            String graduationSchool = (String) map.get("graduationSchool");
            String personalProfile = (String) map.get("personalProfile");
            String workExperience = (String) map.get("workExperience");

            ConsultInfoEntity consultInfo = new ConsultInfoEntity();
            consultInfo.setEducation(education);
            consultInfo.setProfessionField(professionField);
            consultInfo.setGraduationSchool(graduationSchool);
            consultInfo.setPersonalProfile(personalProfile);
            consultInfo.setWorkExperience(workExperience);

            consultInfo.setUserId(Long.valueOf(id));
            //更新咨询师信息表
            consultInfoService.updateById(consultInfo);

        } else {
            //学生用户修改
            String studentId = (String) map.get("studentId");
            String field = (String) map.get("field");
            String schoolName = (String) map.get("schoolName");

            StudentInfoEntity studentInfo = new StudentInfoEntity();
            studentInfo.setField(field);
            studentInfo.setStudentId(studentId);
            studentInfo.setSchoolName(schoolName);

            studentInfo.setUserId(Long.valueOf(id));

            studentInfoService.updateById(studentInfo);
        }

        userMapper.updateOne(user);
        return new HTTPMessage<>(
                HTTPMessageCode.Common.OK,
                HTTPMessageText.Common.OK
        );
    }

    @RequestMapping(value = "/user/{id}/password", method = RequestMethod.PUT)
    public HTTPMessage<Void> updateUserPassword(@PathVariable Long id, @RequestBody Map map) {
        UserEntity user = userMapper.getOne(id);
        if (user == null) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE
            );
        }
        String oldPassword = (String) map.get("oldPassword");
        String newPassword = (String) map.get("newPassword");
        if (Strings.isNullOrEmpty(oldPassword)
                || Strings.isNullOrEmpty(newPassword)
                || !CryptoUtil.getHashedPassword(oldPassword).equals(user.getPassword())) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE
            );
        }
        user.setPassword(CryptoUtil.getHashedPassword(newPassword));
        userMapper.updateOne(user);
        return new HTTPMessage<>(
                HTTPMessageCode.Common.OK,
                HTTPMessageText.Common.OK
        );
    }

    @GetMapping(value = "/user/{id}")
    public HTTPMessage<UserEntity> getUserById(@PathVariable Long id) {
        UserEntity user = userMapper.getOne(id);
        if (user == null) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE
            );
        }
        return new HTTPMessage<>(
                HTTPMessageCode.Common.OK,
                HTTPMessageText.Common.OK,
                user
        );
    }

    @GetMapping(value = "/user/getUserByTypeExtendInfo/{type}")
    public HTTPMessage<List<UserExtendInfo>> getUserByTypeExtendInfo(@PathVariable Integer type) {
        List<UserEntity> user = userMapper.getAll(type);
        List<UserExtendInfo> userExtendInfo = new ArrayList<>();
        if(type == 1){
            List<ConsultInfoEntity> consultInfoEntities = (List<ConsultInfoEntity>) consultInfoService.listByIds(user.stream().map(UserEntity::getId).collect(Collectors.toList()));
            for(UserEntity u : user){
                UserExtendInfo extendInfo = new UserExtendInfo();
                extendInfo.setUserEntity(u);
                for(ConsultInfoEntity c : consultInfoEntities){
                    if(c.getUserId().equals(u.getId())){
                        extendInfo.setConsultInfo(c);
                        userExtendInfo.add(extendInfo);
                    }
                }
            }
        }else{
            List<StudentInfoEntity> studentInfoEntities = (List<StudentInfoEntity>) studentInfoService.listByIds(user.stream().map(UserEntity::getId).collect(Collectors.toList()));
            for(UserEntity u : user){
                UserExtendInfo extendInfo = new UserExtendInfo();
                extendInfo.setUserEntity(u);
                for(StudentInfoEntity c : studentInfoEntities){
                    if(c.getUserId().equals(u.getId())){
                        extendInfo.setStudentInfo(c);
                        userExtendInfo.add(extendInfo);
                    }
                }
            }
        }

        if (user == null) {
            return new HTTPMessage<>(
                    HTTPMessageCode.Common.FAILURE,
                    HTTPMessageText.Common.FAILURE
            );
        }
        return new HTTPMessage<>(
                HTTPMessageCode.Common.OK,
                HTTPMessageText.Common.OK,
                userExtendInfo
        );
    }
}
