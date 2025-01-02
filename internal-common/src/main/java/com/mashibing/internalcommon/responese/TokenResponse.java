package com.mashibing.internalcommon.responese;

import lombok.Data;

/**
 * token返回参数
 *
* @author 马佳健
* @date 2025/1/2
* @description
*/
@Data
public class TokenResponse {

    /**
     * 请求token
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;
}
