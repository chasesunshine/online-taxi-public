package com.mashibing.servicepassengeruser.apipassenger.service;

import com.mashibing.servicepassengeruser.apipassenger.remote.ServiceVefificationcodeClient;
import com.mashibing.servicepassengeruser.internalcommon.constant.CommonStatusEnum;
import com.mashibing.servicepassengeruser.internalcommon.constant.IdentityConstants;
import com.mashibing.servicepassengeruser.internalcommon.dto.ResponseResult;
import com.mashibing.servicepassengeruser.internalcommon.responese.NumberCodeResponse;
import com.mashibing.servicepassengeruser.internalcommon.responese.TokenResponse;
import com.mashibing.servicepassengeruser.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {
    @Autowired
    private ServiceVefificationcodeClient serviceVefificationcodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult generatorCode(String passengerPhone){
        // 调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVefificationcodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        // 存入redis
        // key,value,过期时间
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone, IdentityConstants.PASSENGER_IDENTITY) ;
        // 存入redis
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);

        // 通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，容联
        return ResponseResult.success("");
    }

    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        // 根据手机号，去redis读取验证码
        // 生成key
        String key = RedisPrefixUtils.generatorKeyByPhone(passengerPhone,IdentityConstants.PASSENGER_IDENTITY) ;
        // 根据key获取value
        String codeRedis = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的value："+codeRedis);

        // 校验验证码
        if (StringUtils.isBlank(codeRedis)){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        if (!verificationCode.trim().equals(codeRedis.trim())){
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        // 判断原来是否有用户，并进行对应的处理


        // 颁发令牌，不应该用魔法值，用常量
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("token value");
        return ResponseResult.success(tokenResponse);
    }
}