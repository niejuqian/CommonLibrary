package com.laonie.common.network;

import com.laonie.common.network.param.CommonParam;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-09-07 17:01
 * @DESCRIPTION:
 */

public class RetrofitBuilder {
    //请求接口地址，最后字符必须是/
    private String hostAddress;
    //接口定义
    private Class apiClass;
    //公共参数[heander,query]
    private CommonParam commonParam;

    public String getHostAddress() {
        return hostAddress;
    }

    public RetrofitBuilder setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
        return this;
    }

    public Class getApiClass() {
        return apiClass;
    }

    public RetrofitBuilder setApiClass(Class apiClass) {
        this.apiClass = apiClass;
        return this;
    }

    public CommonParam getCommonParam() {
        return commonParam;
    }

    public RetrofitBuilder setCommonParam(CommonParam commonParam) {
        this.commonParam = commonParam;
        return this;
    }

    @Override
    public String toString() {
        return "RetrofitBuilder{" +
                "hostAddress='" + hostAddress + '\'' +
                ", apiClass=" + apiClass +
                ", commonParam=" + commonParam +
                '}';
    }
}
