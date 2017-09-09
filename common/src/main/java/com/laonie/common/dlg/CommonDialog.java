package com.laonie.common.dlg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laonie.common.R;
import com.laonie.common.listener.BtnClickListener;
import com.laonie.common.util.AppUtil;
import com.laonie.common.util.StringUtils;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-24 20:04
 * @DESCRIPTION:
 *          公共提示框
 */

public class CommonDialog {
    private Dialog dialog;
    private Display display;

    private RelativeLayout common_root_layout;
    private TextView common_title_tv,common_content_tv;
    private Button common_left_btn,common_right_btn;
    private Context context;

    private static CommonDialog singleton = new CommonDialog();

    private CommonDialog() {
        WindowManager windowManager = (WindowManager) AppUtil.getCtx().getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public static CommonDialog builder(){
        return singleton;
    }

    public CommonDialog init(Context context) {
        this.context = context;
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_common, null);
        common_root_layout = view.findViewById(R.id.common_root_layout);
        common_title_tv = view.findViewById(R.id.common_title_tv);
        common_content_tv = view.findViewById(R.id.common_content_tv);
        common_left_btn = view.findViewById(R.id.common_left_btn);
        common_right_btn = view.findViewById(R.id.common_right_btn);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        common_root_layout.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return singleton;
    }

    public CommonDialog setCancelable(boolean cancel){
        dialog.setCancelable(cancel);
        dialog.setCanceledOnTouchOutside(cancel);
        if (cancel) {
            common_root_layout.setOnClickListener(view -> hide());
        }
        return singleton;
    }

    public CommonDialog setTitle(String title){
        if (StringUtils.isEmpty(title)) title = "";
        common_title_tv.setText(title);
        return singleton;
    }

    public CommonDialog setContent(String content) {
        if (StringUtils.isEmpty(content)) content = "";
        common_content_tv.setText(content);
        return singleton;
    }

    public CommonDialog setContentGravity(int gravity){
        common_content_tv.setGravity(gravity);
        return singleton;
    }

    public CommonDialog setLeftBtnText(String text) {
        if (StringUtils.isEmpty(text)) text = "取消";
        common_left_btn.setText(text);
        return singleton;
    }

    public CommonDialog setLeftVisibility(boolean isVisi) {
        common_left_btn.setVisibility(isVisi ? View.GONE : View.VISIBLE);
        return singleton;
    }

    public CommonDialog setRightBtnText(String text) {
        if (StringUtils.isEmpty(text)) text = "确认";
        common_right_btn.setText(text);
        return singleton;
    }

    public CommonDialog setRightVisibility(boolean isVisi) {
        common_right_btn.setVisibility(isVisi ?  View.GONE : View.VISIBLE);
        return singleton;
    }

    public CommonDialog setLeftBtnListener(final BtnClickListener clickListener){
        if ( null != common_left_btn && common_left_btn.getVisibility() != View.GONE) {
            common_left_btn.setOnClickListener(view -> {
                hide();
                if (null != clickListener ){
                    clickListener.click();
                }
            });
        }
        return singleton;
    }

    public CommonDialog setRightBtnListener(final BtnClickListener clickListener){
        if ( null != common_right_btn && common_right_btn.getVisibility() != View.GONE) {
            common_right_btn.setOnClickListener(view -> {
                hide();
                if (null != clickListener) {
                    clickListener.click();
                }
            });
        }
        return singleton;
    }

    public void show(){
        if (null != dialog) {
            if(context instanceof Activity && !((Activity)context).isFinishing()){
                dialog.show();
            }else {
                dialog.show();
            }
        }
    }

    public void hide(){
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
