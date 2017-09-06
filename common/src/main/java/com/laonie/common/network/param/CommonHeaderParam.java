package com.laonie.common.network.param;

import com.laonie.common.annotation.HeaderParam;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-07 9:38
 * @DESCRIPTION:
 */

public class CommonHeaderParam {
    @HeaderParam
    private String appVersion; //应用版本号
    @HeaderParam
    private String token;//token令牌，在登录成功之后返回

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "CommonHeaderParam{" +
                ", appVersion='" + appVersion + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
