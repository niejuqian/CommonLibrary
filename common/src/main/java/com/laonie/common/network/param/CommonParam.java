package com.laonie.common.network.param;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 20:29
 * @DESCRIPTION:
 *          请求公共参数
 */

public class CommonParam {
    private CommonHeaderParam headerParam;
    private CommonQueryParam queryParam;

    public CommonHeaderParam getHeaderParam() {
        return headerParam;
    }

    public void setHeaderParam(CommonHeaderParam headerParam) {
        this.headerParam = headerParam;
    }

    public CommonQueryParam getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(CommonQueryParam queryParam) {
        this.queryParam = queryParam;
    }

    @Override
    public String toString() {
        return "CommonParam{" +
                "headerParam=" + headerParam +
                ", queryParam=" + queryParam +
                '}';
    }
}
