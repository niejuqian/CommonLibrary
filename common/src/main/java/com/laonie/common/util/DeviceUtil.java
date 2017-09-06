package com.laonie.common.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:38
 * @DESCRIPTION: 设备信息帮助类
 */

public class DeviceUtil {
    public static final String SYS_OTHER = "sys_other";//其他系统
    public static final String SYS_EMUI = "sys_emui";//华为系统
    public static final String SYS_MIUI = "sys_miui";//小米系统
    public static final String SYS_FLYME = "sys_flyme";//魅族系统
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    /**
     * 获取当前手机系统
     *
     * @return
     */
    public static String getSystem() {
        String SYS = SYS_OTHER;
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if (prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null) {
                SYS = SYS_MIUI;//小米
            } else if (prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    || prop.getProperty(KEY_EMUI_VERSION, null) != null
                    || prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null) {
                SYS = SYS_EMUI;//华为
            } else if (getMeizuFlymeOSFlag().toLowerCase().contains("flyme")) {
                SYS = SYS_FLYME;//魅族
            }
        } catch (IOException e) {
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    private static String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String) get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * 2  * 获取版本名称
     * 3  * @return 当前应用的版本号
     * 4
     */
    public static String getVersionName() {
        try {
            PackageManager manager = AppUtil.getCtx().getPackageManager();
            PackageInfo info = manager.getPackageInfo(AppUtil.getCtx().getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "获取版本号失败";
    }

    /**
     * 获取应用包名
     *
     * @return
     */
    public static String getPackageName() {
        try {
            PackageManager manager = AppUtil.getCtx().getPackageManager();
            PackageInfo info = manager.getPackageInfo(AppUtil.getCtx().getPackageName(), 0);
            return info.packageName;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "获取版本号失败";
    }

    /**
     * 保存图片
     *
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp, String fileName) {
        // 首先保存图片
        File file = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos); //设置为JPEG时，背景显示为黑色
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    /**
     * 获取当前栈顶的活动
     *
     * @param context
     * @return
     */
    public static Activity getTopActivity(Context context) {
        Activity topActivity = null;
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            String activityClsName = runningTaskInfos.get(0).topActivity.getClassName();
            try {
                Class cls = Class.forName(activityClsName);
                if (null != cls) {
                    topActivity = (Activity) cls.newInstance();
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return topActivity;
    }

    public static String getCurrentAppPackage(Context context) {
        String result = "";
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < 21) {
            // 如果没有就用老版本
            List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
            if (runningTaskInfos != null && runningTaskInfos.size() > 0) {
                result = runningTaskInfos.get(0).topActivity.getPackageName();
            }
        } else {
            List<ActivityManager.RunningAppProcessInfo> runningApp = manager.getRunningAppProcesses();
            if (runningApp != null && runningApp.size() > 0) {
                result = runningApp.get(0).processName;
            }
        }
        if (TextUtils.isEmpty(result)) {
            result = "";
        }
        return result;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode() {
        try {
            PackageManager manager = AppUtil.getCtx().getPackageManager();
            PackageInfo info = manager.getPackageInfo(AppUtil.getCtx().getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 判断应用是否是在后台
     *
     * @return
     */
    public static boolean isAppIsInBackground() {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) AppUtil.getCtx().getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(AppUtil.getCtx().getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(AppUtil.getCtx().getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    public static boolean isServiceWorked(String serviceName) {
        try {
            ActivityManager myManager = (ActivityManager) AppUtil.getCtx().getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(Integer.MAX_VALUE);
            ActivityManager.RunningServiceInfo runningServiceInfo = null;
            for (int i = 0; i < runningService.size(); i++) {
                runningServiceInfo = runningService.get(i);
                if (runningServiceInfo.service.getClassName().toString().equals(serviceName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获取设备厂商
     *
     * @return
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取设备名称
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取deviceId
     *
     * @return
     */
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) AppUtil.getCtx().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return deviceId;
    }

    public static String getImsi() {
        String imsi = ((TelephonyManager) AppUtil.getCtx().getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        return imsi;
    }

    /**
     * 获取网络状态
     *
     * @return
     */
    public static String getAPNType() {
        //结果返回值
        String netType = "nono_connect";
        //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager) AppUtil.getCtx().getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //NetworkInfo对象为空 则代表没有网络
        if (networkInfo == null) {
            return netType;
        }
        //否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
            //WIFI
            netType = "wifi";
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager = (TelephonyManager) AppUtil.getCtx().getSystemService(Context.TELEPHONY_SERVICE);
            //4G
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                netType = "4G";
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0 && !telephonyManager.isNetworkRoaming()) {
                netType = "3G";
                //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS || nSubType == TelephonyManager.NETWORK_TYPE_EDGE || nSubType == TelephonyManager.NETWORK_TYPE_CDMA && !telephonyManager.isNetworkRoaming()) {
                netType = "2G";
            } else {
                netType = "2G";
            }
        }
        return netType;
    }

    /**
     * 获取系统版本信息
     *
     * @return
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    private String getMemoryInfo() {
        return "总内存-".concat(getTotalMemory()).concat("，可用内存-").concat(getAvailMemory());
    }

    public static String getAvailMemory() {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) AppUtil.getCtx().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(AppUtil.getCtx(), mi.availMem);// 将获取的内存大小规格化
    }

    public static String getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2 = null;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split(":");
            for (String num : arrayOfString) {
                Log.i(str2, num + "/t");
            }
            initial_memory = Long.valueOf(arrayOfString[1].replace("kB", "").trim()) * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Formatter.formatFileSize(AppUtil.getCtx(), initial_memory);// Byte转换为KB或者MB，内存大小规格化
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    public static int getScreenWidth(){
        WindowManager manager = (WindowManager) AppUtil.getCtx()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static int getScreenHeight(){
        WindowManager manager = (WindowManager) AppUtil.getCtx()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = AppUtil.getCtx().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = AppUtil.getCtx().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
