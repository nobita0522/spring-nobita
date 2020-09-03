package tech.chenx.core;

import lombok.Getter;

/**
 * @author chenxiong
 * @email nobita0522@qq.com
 * @date 2020/9/3 16:20
 * @description this is description about this file...
 */
public enum RequestMethod {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");

    @Getter
    private String value;

    RequestMethod(String value) {
        this.value = value;
    }
}
