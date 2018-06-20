package com.wenjing.yinfutong.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.interf.OnPayPsdErrorListener;
import com.wenjing.yinfutong.interf.OnPayPsdLockedListener;
import com.wenjing.yinfutong.interf.OnWaitListener;
import com.wenjing.yinfutong.view.CustomDialog;

/**
 * Created by ${luoyingtao} on 2018\4\4 0004.
 */

public class DialogUtils {

    /**
     * 提示    加载异常
     * @param context
     * @param message
     */
    public static void showOneTipDialog(Context context, String message) {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create(R.layout.dialog_layout).show();

    }

    /**
     * 提示   是否去设置交易密码
     *
     * @param context
     */
    public static void showSetPaymentPsdDialog(final Context context){
        new QMUIDialog.MessageDialogBuilder(context)
        .setMessage(R.string.no_set_trade_psd)
        .addAction(R.string.to_set, new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                UIHelper.showSetPaymentPsd(context);

                dialog.dismiss();
            }
        })
       .show();
    }

    /**
     *提示是否    开通商家服务
     * @param context
     */
    public static void showOpenMerchantService(final Context context){
        //创建dialog
        new CustomDialog.Builder(context)
        .setMessage(R.string.merchant_services_tips)
        .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        })
        .setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                UIHelper.showOpenMerchantServices(context);
                dialog.dismiss();
            }
        })
        .create(R.layout.dialog_layout).show();
    }

    public static void showPayPsdErrorDialog(Context context, int restCount , final OnPayPsdErrorListener listener){
        String msg = String.valueOf(context.getText(R.string.pay_inputerror_msg_front)) + restCount + String.valueOf(context.getText(R.string.times));

        SpannableString ss = new SpannableString(context.getText(R.string.re_enter));
        ForegroundColorSpan span = new ForegroundColorSpan(context.getColor(R.color.re_enter_color));
        ss.setSpan(span , 0 , ss.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        new CustomDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(R.string.forget_psd, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if(listener != null){
                            listener.forget();
                        }
                    }
                })
                .setNegativeButton(String.valueOf(ss), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if(listener != null){
                            listener.reEnter();
                        }
                    }
                })
                .create(R.layout.dialog_layout)
                .show();
    }

    public static void showPayPsdLockedDialog(Context context , final OnPayPsdLockedListener listener){
        String msg = String.valueOf(context.getText(R.string.pay_locked_msg));

        new CustomDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(R.string.cancel , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if(listener != null){
                            listener.cancel();
                        }
                    }
                })
                .setNegativeButton(R.string.retrieve_psd , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if(listener != null){
                            listener.retrievePsd();
                        }
                    }
                })
                .create(R.layout.dialog_layout)
                .show();
    }

    public static void showCancelWaitDialog(Context context , final OnWaitListener listener){
        String msg = String.valueOf(context.getText(R.string.tip_cancelqueue));

        SpannableString ss = new SpannableString(context.getText(R.string.wait_again));
        ForegroundColorSpan span = new ForegroundColorSpan(context.getColor(R.color.re_enter_color));
        ss.setSpan(span , 0 , ss.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        new CustomDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(R.string.quit , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if(listener != null){
                            listener.quit();
                        }
                    }
                })
                .setNegativeButton(String.valueOf(ss) , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        if(listener != null){
                            listener.waitAgain();
                        }
                    }
                })
                .create(R.layout.dialog_layout)
                .show();
    }


}
