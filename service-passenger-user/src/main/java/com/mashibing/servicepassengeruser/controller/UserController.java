package com.mashibing.servicepassengeruser.controller;

import com.mashibing.servicepassengeruser.service.UserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据手机号插入用户
     *
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/user")
    public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO){

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("手机号："+passengerPhone);
        return userService.loginOrRegister(passengerPhone);
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param passengerPhone
     * @return
     */
    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String passengerPhone){
        System.out.println("service-passenger-user: phone:"+passengerPhone);
        return userService.getUserByPhone(passengerPhone);
    }

}