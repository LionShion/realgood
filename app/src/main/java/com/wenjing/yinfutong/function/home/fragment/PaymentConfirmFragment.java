package com.wenjing.yinfutong.function.home.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.model.QRCodeResultBean;
import com.wenjing.yinfutong.utils.ArithUtils;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;

/**
 * Created by wenjing on 2018/3/14.
 */

public class PaymentConfirmFragment extends DialogFragment implements View.OnClickListener {


    private ImageView close;
    private TextView orderInfo, orderMoney, payAmountMoney, confirmPayment, tvPayWay, tvPrivilegeMoney;
    private RelativeLayout rlChooseWay;
    private String money;
    private String desc;
    private double rebate;
    private QRCodeResultBean dataBean;
    private BankCardBean bankCardBean;
    private double practicalMoney;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        dataBean = bundle.getParcelable("DataBean");
        money = bundle.getString("money");
        desc = dataBean.getDesc();
        rebate = dataBean.getRebate();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_confirm_payment);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = (int) (getActivity().getWindowManager().getDefaultDisplay().getHeight() * 0.6);
        window.setAttributes(lp);

        initView(dialog);

        return dialog;

    }

    private void initView(Dialog dialog) {

        close = dialog.findViewById(R.id.close);
        orderInfo = dialog.findViewById(R.id.tv_order_info);
        orderMoney = dialog.findViewById(R.id.tv_order_money);
        payAmountMoney = dialog.findViewById(R.id.tv_pay_amount_money);
        confirmPayment = dialog.findViewById(R.id.tv_confirm_payment);
        rlChooseWay = dialog.findViewById(R.id.rl_choose_way);
        tvPayWay = dialog.findViewById(R.id.tv_pay_way);
        tvPrivilegeMoney = dialog.findViewById(R.id.tv_privilege_money);

        close.setOnClickListener(this);
        confirmPayment.setOnClickListener(this);
        rlChooseWay.setOnClickListener(this);



        orderInfo.setText(desc);
        orderMoney.setText(DecimalFormatUtil.defFormat(Double.parseDouble(money)));
        tvPrivilegeMoney.setText(DecimalFormatUtil.defFormat(rebate));

        double orderMoneyD = Double.parseDouble(orderMoney.getText().toString().trim());
        double privilegeMoneyD = Double.parseDouble(tvPrivilegeMoney.getText().toString().trim());
        practicalMoney = ArithUtils.sub(orderMoneyD, privilegeMoneyD);
        payAmountMoney.setText(DecimalFormatUtil.defFormat(practicalMoney));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                getDialog().dismiss();
                break;
            case R.id.tv_confirm_payment:

                PaymentPasswordFragment fragment = new PaymentPasswordFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("type", RequestCode.PAY_QR);
                bundle.putString("money", String.valueOf(practicalMoney));
                bundle.putParcelable("bankCardBean", bankCardBean);
                bundle.putParcelable("DataBean", dataBean);
                fragment.setArguments(bundle);
                fragment.show(getActivity().getSupportFragmentManager(), "paymentPasswordFragment");

                break;
            case R.id.rl_choose_way:

                PayWayFragment payWayFragment = new PayWayFragment();
                payWayFragment.setTargetFragment(this, RequestCode.CHOOSE_PAY_TYPE);
                payWayFragment.show(getActivity().getSupportFragmentManager(), "payWayFragment");

                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.CHOOSE_PAY_TYPE:
                if (resultCode == Activity.RESULT_OK && data != null) {//获取从DialogFragmentB中传递的mB2A
                    Bundle bundle = data.getExtras();
                    bankCardBean = bundle.getParcelable("dataBean");
                    String payWay = bundle.getString("payWay");
                    tvPayWay.setText(payWay);
                }
                break;

        }
    }
}
