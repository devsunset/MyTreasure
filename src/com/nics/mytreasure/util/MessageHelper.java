/*
* @(#)MessageHelper.java
* Date : 2010. 04. 01.
* Copyright: (C) 2010 by NICS Android Developer Team All right reserved.
*/
package com.nics.mytreasure.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nics.mytreasure.R;


/**
 * <PRE>
 * My Treasure MessageHelper
 * </PRE>
 * 
 * @author 강경희
 * @version 1.0
 * @since mytreasure1.0
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MessageHelper {
	private static ProgressDialog progressDialog = null; 
	
	public static final int YES = 0;
	public static final int NO = -1;
	
	public static void alert(Context context, int id) {
		messageBox(context, context.getResources().getString(R.string.common_info_title), context.getResources().getString(id));
	}
	
	public static void alert(Context context, String msg){
		messageBox(context, context.getResources().getString(R.string.common_info_title), msg);
	}

	public static void sysConfirm(final Context context, int id, final Handler resHandler) {
		sysConfirm(context, id, resHandler, YES, NO);
	}

	public static void sysConfirm(final Context context, int id, final Handler resHandler, final int yesWhat, final int noWhat) {
		sysConfirm(context, context.getResources().getString(id), resHandler, yesWhat, noWhat);
	}

	public static void sysConfirm(final Context context, String msg, final Handler resHandler, final int yesWhat, final int noWhat) {
		AlertDialog.Builder builder = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		} else {
			builder = new AlertDialog.Builder(context);
		}
		builder.setTitle(context.getResources().getString(R.string.common_info_title));
		builder.setMessage(msg);
		builder.setPositiveButton(context.getResources().getString(R.string.common_yes), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Message.obtain(resHandler, yesWhat, null).sendToTarget();
			}
		});
		builder.setNegativeButton(context.getResources().getString(R.string.common_no), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				Message.obtain(resHandler, noWhat, null).sendToTarget();
			}
		});
		builder.show();
	}

	public static void confirm(final Context context, int id, final Handler resHandler) {
		confirm(context, context.getResources().getString(id), resHandler, YES, NO);
	}

	public static void confirm(final Context context, int id, final Handler resHandler, final int yesWhat, final int noWhat) {
		confirm(context, context.getResources().getString(id), resHandler, yesWhat, noWhat);
	}

	public static void confirm(final Context context, String msg, final Handler resHandler, final int yesWhat, final int noWhat) {
		View layout = View.inflate(context, R.layout.common_alert_dialog, null);
		layout.findViewById(R.id.commonListDialogListView).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogContentsTextView).setVisibility(View.VISIBLE);
		((TextView) layout.findViewById(R.id.commonListDialogContentsTextView)).setText(msg);

		final AlertDialog _ab = new AlertDialog.Builder(context).show();
		_ab.getWindow().setBackgroundDrawable(new ColorDrawable(0x0000ff00));
		_ab.setContentView(layout);
		_ab.findViewById(R.id.commonListDialogNoButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_ab.dismiss();
				Message.obtain(resHandler, noWhat, _ab).sendToTarget();
			}
		});
		_ab.findViewById(R.id.commonListDialogYesButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_ab.dismiss();
				Message.obtain(resHandler, yesWhat, null).sendToTarget();
			}
		});
	}

	public static void messageBox(Context context, String msgTitle, String msgText) {
		View layout = View.inflate(context, R.layout.common_alert_dialog, null);
		layout.findViewById(R.id.commonListDialogListView).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogContentsTextView).setVisibility(View.VISIBLE);

		((TextView) layout.findViewById(R.id.commonListDialogTitleTextView)).setText(msgTitle);
		((TextView) layout.findViewById(R.id.commonListDialogContentsTextView)).setText(msgText);

		final AlertDialog _ab = new AlertDialog.Builder(context).show();
		_ab.getWindow().setBackgroundDrawable(new ColorDrawable(0x0000ff00));
		_ab.setContentView(layout);
		_ab.findViewById(R.id.commonListDialogButtonLayout).setVisibility(View.GONE);
		_ab.findViewById(R.id.commonListDialogButton2Layout).setVisibility(View.VISIBLE);
		_ab.findViewById(R.id.commonListDialogOkButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_ab.dismiss();
			}
		});
	}

	public static void messageBox(Activity act, String msgTitle, String msgText) {
		View layout = View.inflate(act, R.layout.common_alert_dialog, null);
		layout.findViewById(R.id.commonListDialogListView).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogContentsTextView).setVisibility(View.VISIBLE);

		((TextView) layout.findViewById(R.id.commonListDialogTitleTextView)).setText(msgTitle);
		((TextView) layout.findViewById(R.id.commonListDialogContentsTextView)).setText(msgText);

		final AlertDialog _ab = new AlertDialog.Builder(act).show();
		_ab.getWindow().setBackgroundDrawable(new ColorDrawable(0x0000ff00));
		_ab.setContentView(layout);
		_ab.findViewById(R.id.commonListDialogButtonLayout).setVisibility(View.GONE);
		_ab.findViewById(R.id.commonListDialogButton2Layout).setVisibility(View.VISIBLE);
		_ab.findViewById(R.id.commonListDialogOkButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_ab.dismiss();
			}
		});
	}

	public static void messageBoxAndFinish(final Activity act, String msgTitle, String msgText) {
		View layout = View.inflate(act, R.layout.common_alert_dialog, null);
		layout.findViewById(R.id.commonListDialogListView).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogContentsTextView).setVisibility(View.VISIBLE);

		((TextView) layout.findViewById(R.id.commonListDialogTitleTextView)).setText(msgTitle);
		((TextView) layout.findViewById(R.id.commonListDialogContentsTextView)).setText(msgText);

		final AlertDialog _ab = new AlertDialog.Builder(act).show();
		_ab.getWindow().setBackgroundDrawable(new ColorDrawable(0x0000ff00));
		_ab.setContentView(layout);
		_ab.findViewById(R.id.commonListDialogButtonLayout).setVisibility(View.GONE);
		_ab.findViewById(R.id.commonListDialogButton2Layout).setVisibility(View.VISIBLE);
		_ab.findViewById(R.id.commonListDialogOkButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_ab.dismiss();
				act.finish();
			}
		});
	}

	public static void alert(Context context, String msg, DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
		} else {
			builder = new AlertDialog.Builder(context);
		}
		builder.setTitle(context.getResources().getString(R.string.common_info_title));
		builder.setMessage(msg);
		builder.setPositiveButton(context.getResources().getString(R.string.common_ok), listener);

		builder.show();
	}
	
	public static AlertDialog alert(Context context, String msg, final Runnable runnable) {
		View layout = View.inflate(context, R.layout.common_alert_dialog, null);
		layout.findViewById(R.id.commonListDialogButtonLayout).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogButton2Layout).setVisibility(View.VISIBLE);
		layout.findViewById(R.id.commonListDialogListView).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogContentsTextView).setVisibility(View.VISIBLE);
		((TextView) layout.findViewById(R.id.commonListDialogContentsTextView)).setText(msg);

		final AlertDialog _ab = new AlertDialog.Builder(context).show();
		_ab.getWindow().setBackgroundDrawable(new ColorDrawable(0x0000ff00));
		_ab.setContentView(layout);
		_ab.findViewById(R.id.commonListDialogOkButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_ab.dismiss();
				if(runnable != null){
					runnable.run();
				}
			}
		});

		return _ab;
	}

	public static void alert(Context context, int id, boolean finishYn) {
		messageBox(context, context.getResources().getString(R.string.common_info_title), context.getResources().getString(id));
	}

	public static void alert(Context context, String str, boolean finishYn) {
		messageBox(context, context.getResources().getString(R.string.common_info_title), str);
	}

	public static AlertDialog alert(Context context, String msg, OnClickListener listener) {
		View layout = View.inflate(context, R.layout.common_alert_dialog, null);
		layout.findViewById(R.id.commonListDialogButtonLayout).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogButton2Layout).setVisibility(View.VISIBLE);
		layout.findViewById(R.id.commonListDialogListView).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogContentsTextView).setVisibility(View.VISIBLE);
		((TextView) layout.findViewById(R.id.commonListDialogContentsTextView)).setText(msg);

		AlertDialog _ab = new AlertDialog.Builder(context).show();
		_ab.getWindow().setBackgroundDrawable(new ColorDrawable(0x0000ff00));
		_ab.setContentView(layout);
		_ab.findViewById(R.id.commonListDialogOkButton).setOnClickListener(listener);

		return _ab;
	}

	public static void alert(final Context context, String msg, final Handler resHandler, final int okWhat) {
		View layout = View.inflate(context, R.layout.common_alert_dialog, null);
		layout.findViewById(R.id.commonListDialogButtonLayout).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogButton2Layout).setVisibility(View.VISIBLE);
		layout.findViewById(R.id.commonListDialogListView).setVisibility(View.GONE);
		layout.findViewById(R.id.commonListDialogContentsTextView).setVisibility(View.VISIBLE);
		((TextView) layout.findViewById(R.id.commonListDialogContentsTextView)).setText(msg);

		final AlertDialog _ab = new AlertDialog.Builder(context).show();
		_ab.getWindow().setBackgroundDrawable(new ColorDrawable(0x0000ff00));
		_ab.setContentView(layout);
		_ab.findViewById(R.id.commonListDialogOkButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_ab.dismiss();
				Message.obtain(resHandler, okWhat, _ab).sendToTarget();
			}
		});
	}
	
	@SuppressLint("SetJavaScriptEnabled") 
	public static void webViewMessageBox(Context context, String url, String title) {

		View layout = View.inflate(context, R.layout.help_webview, null);
		WebView webView;
		TextView textView;
		
		final AlertDialog _ab = new AlertDialog.Builder(context).show();
		_ab.getWindow().setBackgroundDrawable(new ColorDrawable(0x0000ff00));
		_ab.setContentView(layout);
		
		LayoutParams params = _ab.getWindow().getAttributes();
	    params.width = LayoutParams.MATCH_PARENT;
	    params.height = LayoutParams.MATCH_PARENT;
	    _ab.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
	    
	    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)layout.getLayoutParams();
	    layoutParams.leftMargin = 20;
	    layoutParams.rightMargin = 20;
	    layout.setLayoutParams(layoutParams);
	    
		webView = (WebView)_ab.findViewById(R.id.helpPopupWebView);
		textView = (TextView)_ab.findViewById(R.id.tv_title);
		textView.setText(title);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
		});
		webView.setWebChromeClient(new WebChromeClient());
		
		((WebView)_ab.findViewById(R.id.helpPopupWebView)).loadUrl(url);
		_ab.findViewById(R.id.btn_close).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				_ab.dismiss();
			}
		});
	}
	
	public static void showProgressDialog( Context context ){
		progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
		progressDialog.setMessage(context.getString(R.string.common_wait_please));
		progressDialog.show();
		TextView messageView = (TextView)progressDialog.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);
	}
	
	public static void dimiss(){
		if( progressDialog != null && progressDialog.isShowing() ){
			progressDialog.dismiss();
			progressDialog = null;
		}
	}
	
	
}