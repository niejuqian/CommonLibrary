package com.laonie.common.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laonie.common.network.callback.CommonParamsInterceptor;
import com.laonie.common.network.param.CommonParam;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:23
 * @DESCRIPTION:
 *              retrofit 控制器
 */

public class RetrofitControl {
    //添加okhttp 网络请求，公共参数拦截器
    private OkHttpClient okHttpClient;
    private CommonParamsInterceptor commonParamsInterceptor;
    private Gson gson;
    private static final int TIME_OUT_TIME = 10;//超时时间
    private Retrofit retrofit;
    private RetrofitBuilder builder;
    private static Map<String, Object> map = new HashMap<>();

    private RetrofitControl(){
        commonParamsInterceptor = new CommonParamsInterceptor();
        //日志输出拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)//添加日志打印拦截器
                .addInterceptor(commonParamsInterceptor)//公共参数添加拦截器
                .connectTimeout(TIME_OUT_TIME, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_TIME,TimeUnit.SECONDS);
        okHttpClient = httpBuilder.build();
        gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }
    public static RetrofitControl getSingleton(){
        return SingletonHolder.singleton;
    }
    private static class SingletonHolder{
        private final static RetrofitControl singleton = new RetrofitControl();
    }
    public void initRegrofit(RetrofitBuilder builder){
        if (null == builder) {
            throw new NullPointerException("必要参数为空，请传入builder信息");
        }
        this.builder = builder;
        retrofit = new Retrofit.Builder()
                .baseUrl(builder.getHostAddress())//
                .client(okHttpClient)//底层使用的http请求框架
                .addConverterFactory(GsonConverterFactory.create(gson))//数据转换
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加rx支持
                .build();
        map.put(builder.getApiClass().getName(), retrofit.create(builder.getApiClass()));
        updateCommParams(builder.getCommonParam());
    }

    public <w> w getServiceApi(Class<w> wClass) {
        if(map.size() == 0) {
            RetrofitControl.getSingleton().initRegrofit(RetrofitControl.getSingleton().getBuilder());
        }
        if (map.containsKey(wClass.getName())) {
            return (w) map.get(wClass.getName());
        } else {
            throw new IllegalArgumentException("Api为空，可能没有进行初始化！");
        }
    }

    /**
     * 更新公共参数
     * @param params
     */
    public void updateCommParams(CommonParam params){
        this.commonParamsInterceptor.updateParams(params);
    }

    public RetrofitBuilder getBuilder(){
        return this.builder;
    }
}
