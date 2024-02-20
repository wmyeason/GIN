package com.cuc.gin.web;

import com.cuc.gin.annotation.AdminRequired;
import com.cuc.gin.mapper.UserMapper;
import com.cuc.gin.model.UserEntity;
import com.cuc.gin.util.*;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author : Wang SM.
 * @since : 2024/2/29,  
 **/
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @AdminRequired
    public HTTPMessage<List<UserEntity>> getAll(HttpServletRequest request, HttpServletResponse response,@RequestParam("type") Integer  type) {
        return new HTTPMessage<>(
                HTTPMessageCode.Common.OK,
                HTTPMessageText.Common.OK,
                userMapper.getAll(type)
        );
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
        if(sex.equals("男")||sex.equals("MALE")){
            user.setGender(Gender.MALE);
        }else if(sex.equals("女")||sex.equals("FEMALE")){
            user.setGender(Gender.FEMALE);
        }else{
            user.setGender(Gender.OTHER);
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
}
