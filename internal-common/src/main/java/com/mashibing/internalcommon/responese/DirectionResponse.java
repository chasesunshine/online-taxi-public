package com.mashibing.internalcommon.responese;

import lombok.Data;

@Data
public class DirectionResponse {

    /**
     * 行驶距离 - 单位：米
     */
    private Integer distance;

    /**
     * 预计行驶时间 - 单位：秒
     */
    private Integer duration;
}
