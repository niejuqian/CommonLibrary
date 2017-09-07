package com.laonie.common.network;

import com.laonie.common.enuns.RespCodeEnum;
import com.laonie.common.network.callback.HttpResult;
import com.laonie.common.network.exception.BllException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-09-07 17:36
 * @DESCRIPTION:
 */

public class RxUtil {
    private static <T> Observable<T> observable(Observable<HttpResult<T>> observable) {
        return observable
                .flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final HttpResult<T> httpResult) {
                        if (httpResult.getCode() != RespCodeEnum.SUCCESS.getCode()) {
                            return Observable.error(new BllException(httpResult.getCode(),httpResult.getMsg()));
                        } else {
                            return Observable.just(httpResult.getData());
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 会切换线程
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<HttpResult<T>,T> tTransformer(){
        return httpResultObservable -> observable(httpResultObservable);
    }

    private static <T> Observable<T> observable1(Observable<HttpResult<T>> observable) {
        return observable
                .flatMap(new Func1<HttpResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(final HttpResult<T> httpResult) {
                        if (httpResult.getCode() != RespCodeEnum.SUCCESS.getCode()) {
                            return Observable.error(new BllException(httpResult.getCode(),httpResult.getMsg()));
                        } else {
                            return Observable.just(httpResult.getData());
                        }
                    }
                });
    }

    /**
     * 不切换线程
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<HttpResult<T>,T> tTransformer1(){
        return httpResultObservable -> observable1(httpResultObservable);
    }

    public <T> Observable valid(HttpResult<T> result,Observable<T> next) {
        if (null == result || !result.isSuccess() || null == result.getData()) {
            return Observable.error(new BllException(result.getCode(),result.getMsg()));
        }else {
            return next;
        }
    }

    public <T> Observable errorObservable(HttpResult<T> result) {
        if (null == result) {
            return Observable.error(new BllException(RespCodeEnum.SYS_ERROR));
        } else {
            return Observable.error(new BllException(result.getCode(),result.getMsg()));
        }
    }
}
