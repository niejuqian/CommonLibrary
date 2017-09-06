package com.laonie.common.enuns;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:49
 * @DESCRIPTION:
 */

public enum RespCodeEnum {
    SUCCESS(0,"响应成功"),
    SYS_ERROR(-1,"请求失败"),
    NETWORK_ERROR(-2,"网络异常"),
    TOKEN_INVALID(105002,"令牌失效，请重新登录"),
    ;
    int code;
    String message;
    RespCodeEnum(int code, String message) {
        this.code = code;
        this.message  = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 是否需要重新登录
     * @param code
     * @return
     */
    public static boolean reLogin(int code) {
        RespCodeEnum codeEnum = null;
        for (RespCodeEnum respCodeEnum : RespCodeEnum.values()) {
            if (code == respCodeEnum.getCode()) {
                codeEnum = respCodeEnum;
                break;
            }
        }
        if (codeEnum != null &&
                codeEnum == TOKEN_INVALID ){
            return true;
        }
        return false;
    }
}
