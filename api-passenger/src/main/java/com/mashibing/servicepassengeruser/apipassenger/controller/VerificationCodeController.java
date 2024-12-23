package com.mashibing.servicepassengeruser.apipassenger.controller;

import com.mashibing.servicepassengeruser.apipassenger.service.VerificationCodeService;
import com.mashibing.servicepassengeruser.internalcommon.dto.ResponseResult;
import com.mashibing.servicepassengeruser.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
* @author 马佳健
* @date 2024/12/16
* @description
*/
@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 乘客获取验证码
     *
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return verificationCodeService.generatorCode(passengerPhone);
    }

    /**
     * 乘客校验验证码
     *
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO){

        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();

        System.out.println("手机号"+passengerPhone+",验证码："+verificationCode);

        return verificationCodeService.checkCode(passengerPhone,verificationCode);
    }

}
