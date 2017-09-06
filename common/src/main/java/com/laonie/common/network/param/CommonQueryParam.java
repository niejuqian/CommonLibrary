package com.laonie.common.network.param;

import com.laonie.common.annotation.FormBodyParam;
import com.laonie.common.annotation.QueryParam;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-07 9:38
 * @DESCRIPTION:
 */

public class CommonQueryParam {
    @FormBodyParam
    @QueryParam
    private String store_id;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    @Override
    public String toString() {
        return "CommonQueryParam{" +
                "store_id='" + store_id + '\'' +
                '}';
    }
}
