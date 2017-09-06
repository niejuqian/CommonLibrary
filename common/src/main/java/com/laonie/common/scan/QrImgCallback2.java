package com.laonie.common.scan;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2016 12 19 10:14
 * @DESC：
 */
public interface QrImgCallback2<T1> {
    void call(T1 t1, String code);
}
