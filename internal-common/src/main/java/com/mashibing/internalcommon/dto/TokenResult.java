package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * token解析返回结果
 *
* @author 马佳健
* @date 2025/1/2
* @description
*/
@Data
public class TokenResult {

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 身份信息
     */
    private String identity;
}
