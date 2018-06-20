package com.wenjing.yinfutong.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.interf.OnErrorResponseListener;

/**
 * Created by ${luoyingtao} on 2018\3\20 0020.
 */

public class ErrorResponseDialog extends Dialog {

    private OnErrorResponseListener mOnErrorResponseListener;

    public ErrorResponseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public ErrorResponseDialog(Context context, OnErrorResponseListener onErrorResponseListener) {
        this(context,R.style.dialog_customestyle);
        this.mOnErrorResponseListener = onErrorResponseListener;
        initView();
    }

    private void initView() {
        setContentView(R.layout.dialog_error);
        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.CENTER;
//        lp.dimAmount = 0.2f;//本浮窗下面    Activity模糊度
        dialogWindow.setAttributes(lp);;


        findViewById(R.id.btn_error_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnErrorResponseListener != null){
                    cancel();
                    mOnErrorResponseListener.onCancel();
                }
            }
        });

        findViewById(R.id.btn_error_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnErrorResponseListener != null){
                    mOnErrorResponseListener.onRetry();
                }
            }
        });


    }



}
