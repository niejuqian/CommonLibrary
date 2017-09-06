package com.laonie.common.network;


import com.laonie.common.Constant;

import java.io.File;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-08-09 18:20
 * @DESCRIPTION:
 */

public class FileUtil {
    /**
     * 版本检测更新接口地址
     */
    public static final String CHECK_VERSION_AFTER = "appversion";
    public static final String CHECK_VERION_LAST = "last";
    /**
     * 检测版本更新地址
     * @return
     */
    public static String getCheckVersionUrl(){
        return Constant.HTTP + RetrofitControl.seviceEnvEnum.getHost() + File.separator + CHECK_VERSION_AFTER + File.separator + CHECK_VERION_LAST;
    }
}
