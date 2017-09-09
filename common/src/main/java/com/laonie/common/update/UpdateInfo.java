/*
 * Copyright 2016 czy1121
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.laonie.common.update;

import com.laonie.common.Constant;
import com.laonie.common.enuns.RespCodeEnum;
import com.laonie.common.util.DeviceUtil;
import com.laonie.common.util.JsonUtils;
import com.laonie.common.util.Logger;
import com.laonie.common.util.StringUtils;

import org.json.JSONException;

public class UpdateInfo {
    // 是否有新版本
    private boolean hasUpdate = false;
    // 是否静默下载：有新版本时不提示直接下载
    private boolean isSilent = false;
    // 是否强制安装：不安装无法使用app
    private boolean isForce = false;
    // 是否下载完成后自动安装
    private boolean isAutoInstall = true;
    // 是否可忽略该版本
    private boolean isIgnorable = false;
    // 是否是增量补丁包
    private boolean isPatch = false;

    private int versionCode;
    private String versionName;

    private String updateContent;

    private String url;
    private String md5;
    public long size;

    private String patchUrl;
    private String patchMd5;
    private long patchSize;

    public static UpdateInfo parse(String s) throws JSONException {
        Logger.e("parse","======================版本检测返回值:" + s);
        return convert(s);
    }

    private static UpdateInfo convert(String jsonStr) throws JSONException {
        UpdateInfo info = new UpdateInfo();
        info.hasUpdate = false;
        if (StringUtils.isEmpty(jsonStr))return info;
        Integer code = JsonUtils.getJsonIntByKey(jsonStr, Constant.CODE_KEY);
        if (null == code || code != RespCodeEnum.SUCCESS.getCode()) return info;
        String dataJson = JsonUtils.getJsonStrByKey(jsonStr,Constant.DATA_KEY);
        if (StringUtils.isEmpty(dataJson)) return info;
        VersionInfo versionInfo = JsonUtils.fromJson(dataJson,VersionInfo.class);
        if (wrapperInfo(info, versionInfo)) return info;
        return info;
    }

    private static boolean wrapperInfo(UpdateInfo info, VersionInfo versionInfo) {
        if (null == versionInfo) return true;
        info.hasUpdate = isNewVersion(versionInfo);
        if (!info.hasUpdate) return true;
        info.md5 = versionInfo.getMd5();
        if (StringUtils.isEmpty(info.md5)) return true;
        info.url = versionInfo.getUrl();
        if (StringUtils.isEmpty(info.url)) return true;
        info.isForce = isForce(versionInfo);
        info.isSilent = !info.isForce;
        info.versionCode = versionInfo.getCode();
        info.versionName = versionInfo.getName();
        String description = versionInfo.getDecription();
        info.updateContent = StringUtils.isEmpty(description) ? "" : description;
        return false;
    }

    public static UpdateInfo convert(VersionInfo versionInfo) {
        UpdateInfo info = new UpdateInfo();
        info.hasUpdate = false;
        if (wrapperInfo(info, versionInfo)) return info;
        return info;
    }

    /**
     * 是否强制更新
     * @param info
     * @return
     */
    private static boolean isForce(VersionInfo info){
        if (info.isForce()) return true;
        Integer forceVersionCode = info.getForceVersionCode();
        int localVersionCode = DeviceUtil.getVersionCode();
        return forceVersionCode != null && (localVersionCode < forceVersionCode);
    }


    /**
     * 是否是新版本
     * @param info
     * @return
     */
    private static boolean isNewVersion(VersionInfo info){
        if (null != info) {
            int localCode = DeviceUtil.getVersionCode();
            Integer cloudCode = info.getCode();
            if (cloudCode != null && localCode != -1 && localCode < cloudCode) {
                return true;
            }
        }
        return false;
    }



    public boolean isHasUpdate() {
        return hasUpdate;
    }

    public void setHasUpdate(boolean hasUpdate) {
        this.hasUpdate = hasUpdate;
    }

    public boolean isSilent() {
        return isSilent;
    }

    public void setSilent(boolean silent) {
        isSilent = silent;
    }

    public boolean isForce() {
        return isForce;
    }

    public void setForce(boolean force) {
        isForce = force;
    }

    public boolean isAutoInstall() {
        return isAutoInstall;
    }

    public void setAutoInstall(boolean autoInstall) {
        isAutoInstall = autoInstall;
    }

    public boolean isIgnorable() {
        return isIgnorable;
    }

    public void setIgnorable(boolean ignorable) {
        isIgnorable = ignorable;
    }

    public boolean isPatch() {
        return isPatch;
    }

    public void setPatch(boolean patch) {
        isPatch = patch;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

    public String getPatchMd5() {
        return patchMd5;
    }

    public void setPatchMd5(String patchMd5) {
        this.patchMd5 = patchMd5;
    }

    public long getPatchSize() {
        return patchSize;
    }

    public void setPatchSize(long patchSize) {
        this.patchSize = patchSize;
    }
}