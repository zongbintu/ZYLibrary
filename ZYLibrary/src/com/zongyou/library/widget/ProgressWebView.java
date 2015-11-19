package com.zongyou.library.widget;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * 带进度条的WebView
 * 
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time 2015-3-27 上午10:09:19
 */
public class ProgressWebView extends WebView {
	private ValueCallback<Uri> mUploadMessage;
	public final static int FILECHOOSER_RESULTCODE = 1;
	private boolean isok=true;

	public interface OnTitleChange {
		public void updateTitle(String title);
	}

	public interface OnFileUpload {
		public void uploadFile(Intent intent, int requestCode);
	}

	public void setFileUri(Uri fileUri) {
		mUploadMessage.onReceiveValue(fileUri);
	}
	
	/**
	 * 监听 状态
	 */
	public interface OnProgressTypeLinterner {
		public void startType(WebView view,ProgressBar progressbar);
		
		public void endType(WebView view,ProgressBar progressbar);
	}
	
	private Context mContext;
	private ProgressWebView views;
	private ProgressBar progressbar;
	private OnTitleChange ontitle;
	private OnFileUpload mOnFileUpload;
	private OnProgressTypeLinterner onProgressType;
	
	public void setOnProgressTypeLinterner(OnProgressTypeLinterner onProgressType){
		this.onProgressType=onProgressType;
	}

	public void setOnTitleChange(OnTitleChange ontitle) {
		this.ontitle = ontitle;
	}

	public void setOnFileUpload(OnFileUpload onUp) {
		this.mOnFileUpload = onUp;
	}

	public ProgressWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		views=this;
        progressbar = new ProgressBar(context, null);
	}
	
	public void	setProgressWebView(int defStyle){
		 progressbar = new ProgressBar(mContext, null,defStyle);
		 progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10, 0, 0));
		 addView(progressbar);
		 setWebChromeClient(new WebChromeClient());
	}
	

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if(onProgressType!=null&& isok){
				isok=false;
				onProgressType.startType(views, progressbar);
			}
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
				isok=true;
				if(onProgressType!=null){
					onProgressType.endType(views,progressbar);
				}
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			if (ontitle != null) {
				ontitle.updateTitle(title);
			}
		}

		// The undocumented magic method override
		// Eclipse will swear at you if you try to put @Override here
		// For Android 3.0+
		public void openFileChooser(ValueCallback<Uri> uploadMsg) {

			mUploadMessage = uploadMsg;
			Intent i = new Intent(Intent.ACTION_GET_CONTENT);
			i.addCategory(Intent.CATEGORY_OPENABLE);
			i.setType("image/*");
			if (null != mOnFileUpload)
				mOnFileUpload.uploadFile(i, FILECHOOSER_RESULTCODE);
		}

		// For Android 3.0+
		public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
			mUploadMessage = uploadMsg;
			Intent i = new Intent(Intent.ACTION_GET_CONTENT);
			i.addCategory(Intent.CATEGORY_OPENABLE);
			i.setType("*/*");
			if (null != mOnFileUpload)
				mOnFileUpload.uploadFile(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
		}

		// For Android 4.1
		public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
			mUploadMessage = uploadMsg;
			Intent i = new Intent(Intent.ACTION_GET_CONTENT);
			i.addCategory(Intent.CATEGORY_OPENABLE);
			i.setType("image/*");
			if (null != mOnFileUpload)
				mOnFileUpload.uploadFile(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		if(null ==lp)
			return;
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}
}