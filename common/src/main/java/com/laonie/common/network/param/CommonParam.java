package com.laonie.common.network.param;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 20:29
 * @DESCRIPTION:
 *          请求公共参数
 */

public class CommonParam {
    private BaseCommonHeaderParam headerParam;
    private BaseCommonQueryParam queryParam;

    public BaseCommonHeaderParam getHeaderParam() {
        return headerParam;
    }

    public void setHeaderParam(BaseCommonHeaderParam headerParam) {
        this.headerParam = headerParam;
    }

    public BaseCommonQueryParam getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(BaseCommonQueryParam queryParam) {
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
