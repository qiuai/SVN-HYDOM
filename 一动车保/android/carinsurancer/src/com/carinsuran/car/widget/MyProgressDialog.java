package com.carinsuran.car.widget;

import com.carinsuran.car.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 通用圆形进度条
 * @author zc
 *
 */
public class MyProgressDialog extends Dialog {  
	  
    private Context context = null;  
    private static MyProgressDialog customProgressDialog = null;  
      
    public MyProgressDialog(Context context) {  
        super(context);  
        this.context = context;  
    }  
  
    public MyProgressDialog(Context context, int theme) {  
        super(context, theme);  
        this.context = context;  
        setCanceledOnTouchOutside(false);//设置点击dialog外面dialog不消失

    }  
  
    public static MyProgressDialog createDialog(Context context) {  
        customProgressDialog = new MyProgressDialog(context,  
                R.style.CustomProgressDialog);  
        customProgressDialog.setContentView(R.layout.common_progress_dialog);  
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;  
        return customProgressDialog;  
    }  
  
    public void onWindowFocusChanged(boolean hasFocus) {  
        if (customProgressDialog == null) {  
            return;  
        }  
        ImageView imageView = (ImageView) customProgressDialog  
                .findViewById(R.id.loadingImageView);  
        Animation rotate = AnimationUtils.loadAnimation(context, R.anim.rotate);
        imageView.setAnimation(rotate);
        imageView.startAnimation(rotate);   
    }  
  
  
  
    public MyProgressDialog setTitile(String strTitle) {  
        return customProgressDialog;  
    }  
  
    public MyProgressDialog setMessage(String strMessage) {  
        TextView tvMsg = (TextView) customProgressDialog  
                .findViewById(R.id.tv_loadingmsg);  
        if (tvMsg != null) {  
            tvMsg.setText(strMessage);  
        }  
        return customProgressDialog;  
    }  
  
}  

