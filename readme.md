# CommonLibrary说明
### 说明
此项目可作为所有Android项目的公共依赖组件

### 包含组件
- 基于`Retrofit+Okhttp+RxJava`网络请求封装
- `Activity`父类`BaseAppCompatActivity`
- `ListView/GridView`公共适配器封装`CommonAdapter`
- `RecyclerView`公共适配器封装`QuickAdapter`
- 提供了大部分帮助类（日期转换/数字格式化/字符串处理等）
- 基于`Gidle`图片加载再封装`ImageLoader`
- 基于`ZXing`扫码组件`ScanActivity`
- 基于`ZXing`生成二维码组件`CreateQrImage`
- 弹性`ScrollView`组件`ReboundScrollView`
- 上拉加载/下拉刷新组件`AbPullToRefreshView`，完美支持`RecyclerView/ListView/GridView`

### 如何进行依赖
- 首先本项目最好跟你的APP项目放在同级目录下
- 其次在APP项目的`build.gradle`文件中最上面增加如下配置
```gradle
// 这个配置文件中放置了公共的一些组件版本信息，如果不需要，则忽略
apply from: '../CommonLibrary/dependencies.gradle'
```
- 在项目的`setting.gradle`文件中增加如下配置
```gradle
include ':app', ':CommonLibrary'
project(':CommonLibrary').projectDir = new File('../CommonLibrary')
include ':CommonLibrary:common'
```
- 在需要依赖使用的`module`的`build.gradle`中增加如下配置
```gradle
compile project(':CommonLibrary:common')
```
### 部分组件使用
- 网络请求
```java
//在APP的Application中，需要对RetrofitControl进行初始化
private void initRetrofit(){
    //其中ServiceApi为定义接口的API
    RetrofitControl.Builder builder = new RetrofitControl.Builder()
            .setApiClass(ServiceApi.class)
            .setHostAddress("https://xxx.xxx.xxx");
    RetrofitControl.getSingleton().initRegrofit(builder);
}
```
- 上拉刷新/下拉加载
```xml
<!--布局文件 -->
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <com.laonie.common.view.refresh.AbPullToRefreshView
        android:id="@+id/refresh_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <android.support.v7.widget.RecyclerView
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        </android.support.v7.widget.RecyclerView>
    </com.laonie.common.view.refresh.AbPullToRefreshView>
</LinearLayout>
```
```java
package com.laonie.addcommonlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.laonie.bll.CommonBll;
import com.laonie.common.base.BaseAppCompatActivity;
import com.laonie.common.network.callback.SuccCallback;
import com.laonie.common.view.refresh.AbPullToRefreshView;
import com.laonie.model.response.ConfigResponse;

import butterknife.BindView;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-09-08 17:48
 * @DESCRIPTION:
 */

public class TestActivity extends BaseAppCompatActivity implements AbPullToRefreshView.OnFooterLoadListener,AbPullToRefreshView.OnHeaderRefreshListener {
    @BindView(R.id.refresh_view)
    AbPullToRefreshView refreshView;
    
    CommonBll commonBll;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_test);
    }

    @Override
    protected void initView() {
        //设置监听事件
        refreshView.setOnFooterLoadListener(this);
        refreshView.setOnHeaderRefreshListener(this);
    }

    @Override
    protected void initData() {
        commonBll = new CommonBll();
        commonBll.getSysConfig(new SuccCallback<ConfigResponse>() {
            @Override
            public void call(ConfigResponse configResponse) {
                //请求完成，要将下拉/上拉显示的view隐藏掉
                refreshView.onHeaderRefreshFinish();
                refreshView.onFooterLoadFinish();
            }
        }, null);
    }
    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        //上拉刷新
    }
    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        //下拉加载
    }
}
```

### 展示
- ![依赖效果](http://note.youdao.com/yws/public/resource/9ebef5d7fb785976a2e4759ea7df63ba/xmlnote/747C839CFF434B87B7E1E66A031D3CFC/4452)
- ![项目存放目录](http://note.youdao.com/yws/public/resource/9ebef5d7fb785976a2e4759ea7df63ba/xmlnote/2E3BAEF687BB4D0CB22B23564653179E/4455)