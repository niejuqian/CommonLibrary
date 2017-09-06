/**
 * 该项目主要承载的是基础架构的框架内容，放置通用数据的目录
 */
package com.laonie.common.util;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrashHandler implements UncaughtExceptionHandler {

	private final UncaughtExceptionHandler mExceptionHandler;
	
	public static void start(Context context) {
		CrashHandler handler = new CrashHandler(context);
		Thread.setDefaultUncaughtExceptionHandler(handler);
	}

	CrashHandler(Context context){
		mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
	}
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e("crash", "", ex);
		final String string = saveToFile(ex);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(AppUtil.getCtx(),
						"错误日志存放在：" + string, Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}).start();
		
		mExceptionHandler.uncaughtException(thread, ex);
	}
	
	private String saveToFile(Throwable ex) {
		File mSaveDir = FileManager.getLogDir();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.SIMPLIFIED_CHINESE);
		String filename = format.format(new Date());

		File file = new File(mSaveDir,filename + ".txt");
		FileOutputStream stream = null;
		try {
			 stream = new FileOutputStream(file);
			 String log = Log.getStackTraceString(ex);
			 stream.write(log.getBytes());
		} catch (Exception e) {
//			Logger.e(e);
			e.printStackTrace();
		} finally {
			if(stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
		return file.getAbsolutePath();
	}
	
}