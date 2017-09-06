package com.laonie.common.network;

import com.laonie.common.enuns.RespCodeEnum;
import com.laonie.common.network.callback.Callback;
import com.laonie.common.network.callback.EndCallback;
import com.laonie.common.network.callback.ErrCallback;
import com.laonie.common.network.callback.StartCallback;
import com.laonie.common.network.callback.SuccCallback;
import com.laonie.common.network.exception.BllException;

import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:58
 * @DESCRIPTION:
 */

public class MySubscriber<T> extends Subscriber<T> {
    private SuccCallback<T> succCallback;//成功回调
    private ErrCallback errCallback;//失败回调
    private StartCallback startCallback;//开始调用前回调
    private EndCallback endCallback;//完成回调，不管成功、失败都会回调
    private Callback<T> callback;
    public MySubscriber(){
    }
    public MySubscriber(Callback<T> callback){
        this.callback = callback;
    }
    public MySubscriber(SuccCallback<T> succCallback){
        this.succCallback = succCallback;
    }
    public MySubscriber(EndCallback endCallback){
        this.endCallback = endCallback;
    }
    public MySubscriber(SuccCallback<T> succCallback, ErrCallback errCallback){
        this.succCallback = succCallback;
        this.errCallback = errCallback;
    }
    @Override
    public void onStart() {
        if (null != startCallback) startCallback.call();
    }
    @Override
    public void onCompleted() {
        if (null != endCallback) endCallback.call();
    }
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }
    /**
     * 统一异常处理分发
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof BllException) {
            BllException exception = (BllException) e;
            //特定的code，需要重新登录
            int code = exception.getCode();
            onError(exception.getCode(),exception.getMsg());
        }else if (e instanceof SocketTimeoutException) {
            onError(RespCodeEnum.NETWORK_ERROR.getCode(),RespCodeEnum.NETWORK_ERROR.getMessage());
        } else {
            onError(RespCodeEnum.SYS_ERROR.getCode(),RespCodeEnum.SYS_ERROR.getMessage());
        }
    }
    public void onSuccess(T t){
        if (null != succCallback) {
            succCallback.call(t);
        }
        if (null != callback) {
            callback.call(t);
        }
    }

    public void onError(int code,String msg){
        if (null != errCallback) {
            errCallback.call(code,msg);
        }
        if (null != callback) {
            callback.call(null);
        }
    }


    public void setSuccCallback(SuccCallback<T> succCallback) {
        this.succCallback = succCallback;
    }

    public void setErrCallback(ErrCallback errCallback) {
        this.errCallback = errCallback;
    }

    public void setStartCallback(StartCallback startCallback) {
        this.startCallback = startCallback;
    }

    public void setEndCallback(EndCallback endCallback) {
        this.endCallback = endCallback;
    }
}
