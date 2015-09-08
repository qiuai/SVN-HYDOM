package com.carinsurance.my_view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.webkit.WebView;

public class MWebView extends WebView {

	private boolean isXiaoMIMobil = false;

	public MWebView(Context context, AttributeSet attrs) {
		super(context, attrs);

		String model = android.os.Build.MODEL;
		if (!TextUtils.isEmpty(model) && model.contains("MI")) {
			isXiaoMIMobil = true;
		}

	}

	public MWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		String model = android.os.Build.MODEL;
		if (!TextUtils.isEmpty(model) && model.contains("MI")) {
			isXiaoMIMobil = true;
		}
	}

	@Override
	public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
		InputConnection inputConnection = new BaseInputConnection(this, false) {
			@Override
			public boolean commitText(CharSequence text, int newCursorPosition) {
				MWebView.this.loadUrl("javascript:insertText('" + text + "')");
				return true;
			}

			@Override
			public boolean deleteSurroundingText(int beforeLength,
					int afterLength) {
				MWebView.this.loadUrl("javascript:backspace()");
				return true;
			}
		};

		if (isXiaoMIMobil) {
			return new InputConnectionWrapper(inputConnection, false);
		} else {
			return new CordovaInputConnection(this, false);
			// return super.onCreateInputConnection(outAttrs);
		}

	}

	// 解决Html5中非输入框不能接受Backspace事件
	class CordovaInputConnection extends BaseInputConnection {

		public CordovaInputConnection(View targetView, boolean fullEditor) {
			super(targetView, fullEditor);
		}

		// 解决输入一行字符串的时候只显示第一个字符
		@Override
		public boolean commitText(CharSequence text, int newCursorPosition) {
			if (text.length() == 1) {
				return super.commitText(text, newCursorPosition);
			}
			for (int i = 0; i < text.length(); i++) {
				super.commitText(text.charAt(i) + "", newCursorPosition);
			}
			return false;
		}

		// 删除周围文本
		@Override
		public boolean deleteSurroundingText(int beforeLength, int afterLength) {
			// magic: in latest Android, deleteSurroundingText(1, 0) will be
			// called for backspace
			if (beforeLength == 1 && afterLength == 0) {
				// backspace
				return super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
						KeyEvent.KEYCODE_DEL))
						&& super.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
								KeyEvent.KEYCODE_DEL));
			}

			return super.deleteSurroundingText(beforeLength, afterLength);
		}

	}

}
