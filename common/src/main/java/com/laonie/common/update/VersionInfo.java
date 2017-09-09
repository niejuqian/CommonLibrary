package com.laonie.common.update;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:33
 * @DESCRIPTION:
 *          版本信息
 */

public class VersionInfo {
    private Integer code;//app应用版本号:10102
    private String name;//app应用版本名称:1.1.2
    private Integer versionType;//应用版本类型：测试版1，正式版2
    private boolean enable;//是否最新版本：非最新版本-0，最新版本-1
    private String isForce;//是否强制更新:1=是,0=否
    private String url;//下载的url
    private String decription;//版本更新说明
    private String md5;//apk文件的md5值
    private Integer forceVersionCode;//最近一个强制更新版本号

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersionType() {
        return versionType;
    }

    public void setVersionType(Integer versionType) {
        this.versionType = versionType;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public boolean isForce(){
        return null != isForce && isForce.equals("1");
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getForceVersionCode() {
        return forceVersionCode;
    }

    public void setForceVersionCode(Integer forceVersionCode) {
        this.forceVersionCode = forceVersionCode;
    }
}
