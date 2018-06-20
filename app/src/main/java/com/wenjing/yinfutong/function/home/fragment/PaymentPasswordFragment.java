package com.wenjing.yinfutong.function.home.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.constant.ResponseConstant;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnPayPsdErrorListener;
import com.wenjing.yinfutong.interf.OnPayPsdLockedListener;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.CashOutToSignOrderNo;
import com.wenjing.yinfutong.model.PayPsdBean;
import com.wenjing.yinfutong.model.QRCodeResultBean;
import com.wenjing.yinfutong.model.RechargeOfflineToSignOrderNo;
import com.wenjing.yinfutong.model.RechargeOnlineBean;
import com.wenjing.yinfutong.model.UsQRBean;
import com.wenjing.yinfutong.model.body.CashOutRequestBody;
import com.wenjing.yinfutong.model.body.PayQRRequestBody;
import com.wenjing.yinfutong.model.body.PaypsdVerifyRequestBody;
import com.wenjing.yinfutong.model.body.RechargeOfflineRequestBody;
import com.wenjing.yinfutong.model.body.RechargeOnlineRequestBody;
import com.wenjing.yinfutong.retrofit.YinFuTongFactory;
import com.wenjing.yinfutong.retrofit.api.HomeApi;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.DialogUtils;
import com.wenjing.yinfutong.utils.MD5Util;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.RechargeOnlineToSignOrderNO;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.ToSignOrderNO;
import com.wenjing.yinfutong.utils.sign.SignHelper;
import com.wenjing.yinfutong.view.CustomerKeyboard;
import com.wenjing.yinfutong.view.PassWordEditText;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wenjing on 2018/3/15.
 */

public class PaymentPasswordFragment extends DialogFragment implements CustomerKeyboard.CustomerKeyboardClickListener, PassWordEditText.PWCommitListener {
    public static final String TAG = PaymentPasswordFragment.class.getSimpleName();

    public static final int RECHARGE_ONLINE = 0;
    public static final int PAY_QR = 1;
    public static final int CASH_OUT = 2;
    public static final int RECHARGE_OFFLINE = 3;
    private int type;
    private HomeApi sHomeApi = YinFuTongFactory.getHomeApiSingleton();
    private String money;
    private String bankCardNo;
    private int bankId;
    private PassWordEditText pwEditText;
    private int merchantId;
    private String desc;
    private String orderNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        type = bundle.getInt("type");
        money = bundle.getString("money");

        if (type == PAY_QR) {
            UsQRBean dataBean = bundle.getParcelable("DataBean");
            desc = dataBean.getDesc();
            merchantId = dataBean.getId();
            orderNo = bundle.getString("orderNo");
        }
        if (type == RECHARGE_ONLINE) {
            bankId = bundle.getInt("bankId");
        }
        if (type == CASH_OUT) {
            bankCardNo = bundle.getString("cardNo");
        }
        if (type == RECHARGE_OFFLINE) {
            QRCodeResultBean dataBean = bundle.getParcelable("DataBean");
            desc = dataBean.getDesc();
            merchantId = dataBean.getId();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_payment_password);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = (int) (getActivity().getWindowManager().getDefaultDisplay().getHeight() * 0.57);

        TLog.e("------",lp.height+"---"+lp.width);
        window.setAttributes(lp);

        initView(dialog);

        return dialog;

    }

    private void initView(final Dialog dialog) {
        CustomerKeyboard customerKeyboard = dialog.findViewById(R.id.keyboard_View);
        pwEditText = dialog.findViewById(R.id.pw_EditText);
        ImageView ivClose = dialog.findViewById(R.id.close);
        TextView tvForget = dialog.findViewById(R.id.tv_forget);


        customerKeyboard.setOnCustomerKeyboardClickListener(this);
        pwEditText.setOnPWCommitListener(this);


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrievePaymentPsd();
            }
        });

    }

    private void retrievePaymentPsd() {
        Bundle bundle = new Bundle();
        bundle.putInt(TabCodeConstant.KEY_TABCODE, TabCodeConstant.TAB_PAYMENTPASSWORD);
        UIHelper.showRetrievePsd(getContext(), bundle);
    }


    @Override
    public void click(String pw) {
        pwEditText.addPW(pw);
    }

    @Override
    public void remove() {
        pwEditText.removePW();
    }

    @Override
    public void commit_PW(String pw) {

        int accountId = 0;
        if (AppContext.instance().isLogin()) {
            accountId = AppContext.instance().getLoginUser().getAccountId();
        }
        final String passWrodMD5 = MD5Util.encrypt(pw);

        PaypsdVerifyRequestBody body = new PaypsdVerifyRequestBody();
        body.setAccountId(accountId);
        body.setPassword(passWrodMD5);
        TLog.log(TAG, body.toString());

        final int finalAccountId = accountId;
        new MyObservable<BaseResponse<PayPsdBean>>().observe(getContext(),
                sHomeApi.verifyPayPsd(body),
                new OnResponseListener<BaseResponse<PayPsdBean>>() {
                    @Override
                    public void onNext(BaseResponse<PayPsdBean> data) {
                        if(data.getCode() == 0){//交易密码   校验成功
                            switch (type) {
                                case RECHARGE_ONLINE:
                                    toRecharge(finalAccountId, passWrodMD5);
                                    break;
                                case PAY_QR:
                                    toPay(finalAccountId, passWrodMD5);
                                    break;
                                case CASH_OUT:
                                    toCashOut(finalAccountId, passWrodMD5);
                                    break;
                                case RECHARGE_OFFLINE:
                                    toRechargeOffline(finalAccountId);
                                    break;
                            }
                        }else if (data.getCode() == ResponseConstant.RESPONSECODE_PAYPSD_ERROR) {
                            pwEditText.setText("");//置空

                            DialogUtils.showPayPsdErrorDialog(getContext(), data.getData().getCount(), new OnPayPsdErrorListener() {
                                @Override
                                public void reEnter() {

                                }

                                @Override
                                public void forget() {
                                    retrievePaymentPsd();
                                }
                            });
                        }else if(data.getCode() == ResponseConstant.RESPONSECODE_PAYPSD_LOCKED){
                            pwEditText.setText("");//置空

                            DialogUtils.showPayPsdLockedDialog(getContext(), new OnPayPsdLockedListener() {
                                @Override
                                public void retrievePsd() {
                                    retrievePaymentPsd();
                                }

                                @Override
                                public void cancel() {

                                }
                            });
                        }else {
                            AppContext.showToast(data.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        pwEditText.setText("");//置空
                    }
                });

    }

    /**
     *
     * 改动后   这里直接废弃了
     * 变成   扫描分销商   后直接  充值
     * @param accountId
     */
    private void toRechargeOffline(int accountId) {
        RechargeOfflineToSignOrderNo toSignOrderNo = new RechargeOfflineToSignOrderNo(accountId, merchantId, Double.parseDouble(money));
        String signContent = SignHelper.sortParamForSignCard(toSignOrderNo);
        String sign = MD5Util.getInstance().sign(signContent, RequestConstant.PRIVATE_KEY);
        RechargeOfflineRequestBody body = new RechargeOfflineRequestBody(accountId, merchantId, Double.parseDouble(money), desc, AppContext.getLangulage(), sign);
        sHomeApi.getRechargeOffline(body)
                .compose(RxResultHelper.<RechargeOnlineBean>handleRespose())
                .compose(RxSchedulers.<RechargeOnlineBean>applyObservableAsync())
                .subscribe(new Observer<RechargeOnlineBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RechargeOnlineBean bean) {
                        pwEditText.setText("");//置空

                        Bundle bundle = new Bundle();
                        bundle.putString("money", money);
                        UIHelper.showRechargeSucc(getContext(), bundle);
                        getActivity().finish();

                    }

                    @Override
                    public void onError(Throwable e) {
                        pwEditText.setText("");//置空

                        AppContext.showToast(e.toString());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void toCashOut(int accountId, String passWrodMD5) {
        CashOutToSignOrderNo toSignOrderNo = new CashOutToSignOrderNo(Double.parseDouble(money), accountId, bankCardNo);
        String signContent = SignHelper.sortParamForSignCard(toSignOrderNo);
        String sign = MD5Util.getInstance().sign(signContent, RequestConstant.PRIVATE_KEY);

        CashOutRequestBody cashOutRequestBody = new CashOutRequestBody(
                AppContext.getLangulage(),
                Double.parseDouble(money),
                accountId,
                bankCardNo,
                passWrodMD5,
                2,
                sign);

        TLog.log(TAG, cashOutRequestBody.toString());
        new MyObservable<BaseResponse>().observe(getContext(),
                sHomeApi.getCashOut(cashOutRequestBody),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        pwEditText.setText("");//置空

                        if(data.getCode() == 0){
                            UIHelper.showWithdrawWait(getContext());
                            getActivity().finish();
                        }else {
                            AppContext.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        pwEditText.setText("");//置空
                    }
                });

    }


    private void toPay(int accountId, String passWrodMD5) {
        String moneyStr = DecimalFormatUtil.defFormat(Double.parseDouble(money));

        ToSignOrderNO toSignOrderNO = new ToSignOrderNO(accountId, merchantId, Double.parseDouble(moneyStr));

        String orderContent = SignHelper.sortParamForSignCard(toSignOrderNO);
        String orderSign = MD5Util.getInstance().sign(orderContent, RequestConstant.PRIVATE_KEY);

        PayQRRequestBody qrRequestBody = new PayQRRequestBody(accountId,
                passWrodMD5,
                merchantId,
                Double.parseDouble(money),
                orderNo,
                -1,
                AppContext.getLangulage(),
                orderSign);

        TLog.log(TAG, "PayQRRequestBody : " + qrRequestBody.toString());

        new MyObservable<BaseResponse<RechargeOnlineBean>>().observe(getContext(),
                sHomeApi.payMoney(qrRequestBody),
                new OnResponseListener<BaseResponse<RechargeOnlineBean>>() {
                    @Override
                    public void onNext(BaseResponse<RechargeOnlineBean> data) {
                        pwEditText.setText("");//置空

                        if (data.getCode() == 0) {
                            UIHelper.showPaySucc(getContext());
                            getActivity().finish();
                        }else {
                            AppContext.showToast(data.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        pwEditText.setText("");//置空
                    }
                });
    }

    private void toRecharge(int accountId, String passWrodMD5) {
        RechargeOnlineToSignOrderNO rechargeOnlineSign = new RechargeOnlineToSignOrderNO(accountId, Double.parseDouble(money));
        String orderContent = SignHelper.sortParamForSignCard(rechargeOnlineSign);
        String sign = MD5Util.getInstance().sign(orderContent, RequestConstant.PRIVATE_KEY);

        RechargeOnlineRequestBody body = new RechargeOnlineRequestBody(accountId, Double.parseDouble(money), "", sign, 0, bankId, AppContext.getLangulage(), passWrodMD5);

        TLog.log(TAG, body.toString());
        new MyObservable<BaseResponse<RechargeOnlineBean>>().observe(getContext(),
                sHomeApi.getRechargeOnline(body),
                new OnResponseListener<BaseResponse<RechargeOnlineBean>>() {
                    @Override
                    public void onNext(BaseResponse<RechargeOnlineBean> data) {
                        pwEditText.setText("");//置空

                        if(data.getCode() == 0 && data.getData() != null){
                            Bundle bundle = new Bundle();
                            bundle.putString("money", money);
                            UIHelper.showRechargeSucc(getContext(), bundle);

                            getActivity().finish();
                        }else {
                            AppContext.showToast(data.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        pwEditText.setText("");//置空
                    }
                });

    }
}
