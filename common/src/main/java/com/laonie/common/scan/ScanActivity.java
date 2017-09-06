package com.laonie.common.scan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.laonie.common.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2016 12 17 16:31
 * @DESC：
 */
public class ScanActivity extends Activity   implements QRCodeView.Delegate{
    private final String TAG = this.getClass().getSimpleName();
    private QRCodeView mQRCodeView;
    private RelativeLayout code_cancle_rl;
    private TextView code_title_tv;
    private Toolbar toolbar;

    public static final String SCAN_RESULT = "scan_result";//正常返回
    public static final String TOOLBAR_BACK_COLOR = "toolbar_back_color";//标题栏背景色 '#000000'
    public static final String TITLE = "title";//标题 '扫码'
    public static final String RESULT_ERROR_MSG = "result_error_msg";
    public static final String QR_DESC = "qr_desc";//二维码框上面文字说明
    public static final int RESULT_ERROR_RESP_CODE = 0;//错误返回code

    private String color;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_scan);
        setView();
        initView();
        setListener();
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpot();
    }

    /**
     * 设置view
     */
    private void setView(){
        mQRCodeView = (QRCodeView) findViewById(R.id.zxingview);
    }

    /**
     * 初始化
     */
    private void initView(){
        Intent intent = getIntent();
        if (null != intent) {
            color = intent.getStringExtra(TOOLBAR_BACK_COLOR);
            title = intent.getStringExtra(TITLE);
        }
        if (null != color && color.length() > 0) {
            toolbar.setBackgroundColor(Color.parseColor(color));
        }
        if (null != title && title.length() > 0) {
            code_title_tv.setText(title);
        }
    }

    /**
     * 设置事件
     */
    private void setListener(){
        code_cancle_rl.setOnClickListener(v -> finish());
    }
    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "扫码返回:" + result);
        mQRCodeView.stopSpot();
        Intent intent = new Intent();
        intent.putExtra(SCAN_RESULT,result);
        this.setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
        Intent intent = new Intent();
        intent.putExtra(RESULT_ERROR_MSG,"打开相机出错");
        this.setResult(RESULT_ERROR_RESP_CODE,intent);
        finish();
    }
}
