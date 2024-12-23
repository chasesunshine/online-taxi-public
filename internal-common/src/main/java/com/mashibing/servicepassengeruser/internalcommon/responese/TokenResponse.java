package com.mashibing.servicepassengeruser.internalcommon.responese;

import lombok.Data;

@Data
public class TokenResponse {

    private String accessToken;

    private String refreshToken;
}
