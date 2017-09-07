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
    RetrofitBuilder builder = new RetrofitBuilder()
            .setApiClass(ServiceApi.class)
            .setHostAddress("https://xxx.xxx.xxx");
    RetrofitControl.getSingleton().initRegrofit(builder);
}
```