package com.laonie.common.dlg;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.laonie.common.R;


public class LoadingDialog extends Dialog {

	public LoadingDialog(Context context) {

		super(context, R.style.custom_dialog);
		setContentView(R.layout.loading);

		setCancelable(true);
	}

	public void setMessage(CharSequence message) {
		((TextView) findViewById(R.id.progress_dialog_msg)).setText(message);
	}
}
