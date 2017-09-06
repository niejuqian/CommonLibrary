package com.laonie.common.network.exception;

import com.laonie.common.enuns.RespCodeEnum;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:23
 * @DESCRIPTION:
 *                  自定义异常
 */
public class BllException extends Throwable {
    private int code;
    private String msg;
    public BllException(){
    }

    public BllException(int code,String msg){
        this.code = code;
        this.msg = msg;
    }


    public BllException(RespCodeEnum codeEnum){
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
