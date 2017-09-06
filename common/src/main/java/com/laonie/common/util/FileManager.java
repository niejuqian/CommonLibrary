package com.laonie.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileManager {
    public static String VOICE_DIR = "voice";
    private static String ROOT_DIR = Environment.getExternalStorageDirectory().getPath() + "/com.cpcash/";
    /** 缓存文件夹地址	 */
    private static String CACHE_DIR = ROOT_DIR + "cache";
    private static String APK_DIR = ROOT_DIR + "apk";
    public static String APK_FILE_NAME = "cp_cash.apk";
    private static String CACHE_FILE_NAME = "playVoice.mp3";
    private static String SUFFIX = ".mp3";

    public static List<String> drr = new ArrayList<String>();

    public static String getApkDirPath(){
        File file = new File(APK_DIR);
        if (!file.exists()){
            file.mkdirs();
        }
        return APK_DIR;
    }

    public static File getLogDir() {
        File file = new File(ROOT_DIR + "logs");
        if (!file.exists()) {
            file.mkdirs();
        }
        return  file;
    }

    public static void initSDDir() {
        FileManager.creatSDDir(ROOT_DIR);
        FileManager.creatSDDir(CACHE_DIR);
        FileManager.creatSDDir(ROOT_DIR + "logs");
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public static File creatSDDir(String dirName) {
        File dir = new File(dirName);
        dir.mkdirs();
        return dir;
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public static File creatSDFile(String fileName) {
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
        }
        return file;
    }

    /**
     * 生成UUID通用唯一识别码（去掉-）
     */
    public static String getUUID() {

        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isImage(String type) {
        if (type != null
                && (type.equals("jpg") || type.equals("gif")
                || type.equals("png") || type.equals("jpeg")
                || type.equals("bmp") || type.equals("wbmp")
                || type.equals("ico") || type.equals("jpe"))) {
            return true;
        }
        return false;
    }

    /**
     * 读取文本数据
     *
     * @param context
     *            程序上下文
     * @param fileName
     *            文件名
     * @return String, 读取到的文本内容，失败返回null
     */
    public static String readAssets(Context context, String fileName)
    {
        InputStream is = null;
        String content = null;
        try
        {
            is = context.getAssets().open(fileName);
            if (is != null)
            {

                byte[] buffer = new byte[1024];
                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                while (true)
                {
                    int readLength = is.read(buffer);
                    if (readLength == -1) break;
                    arrayOutputStream.write(buffer, 0, readLength);
                }
                is.close();
                arrayOutputStream.close();
                content = new String(arrayOutputStream.toByteArray());

            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            content = null;
        }
        finally
        {
            try
            {
                if (is != null) is.close();
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        return content;
    }

    /**
     * 压缩图片
     */
    public static Bitmap compressionAndSavePhoto(Bitmap bitmap, File file) {

        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static String getFilePath(){
        return CACHE_DIR + File.separator + CACHE_FILE_NAME;
    }

    public static void clearFile(){
        File file = new File(getFilePath());
        if (file.exists()) {
            file.delete();
        }
    }

    public static void appendFile(String fileName) {
        RandomAccessFile accessFile = null;
        InputStream is = null;
        try {
            is = readAssetsFile(fileName);
            if (null == is) return;
            String filePath = CACHE_DIR;
            dirExists(filePath);
            accessFile = new RandomAccessFile(getFilePath(),"rw");
            long offset = accessFile.length();
            accessFile.seek(offset);//跳过指定长度，将文件追加到末尾
            byte[] b = new byte[1024];
            int len;
            while((len = is.read(b)) != -1) {
                //将文件写入目录
                accessFile.write(b,0,len);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (accessFile != null) {
                    accessFile.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static InputStream readAssetsFile(String fileName) throws IOException {
        return AppUtil.getCtx().getAssets().open(VOICE_DIR + File.separator + fileName);
    }
    public static void dirExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    public static void fileExists(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
