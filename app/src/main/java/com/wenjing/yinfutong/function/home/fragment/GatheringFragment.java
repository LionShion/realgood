package com.wenjing.yinfutong.function.home.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.utils.TextUtil;
import com.wenjing.yinfutong.view.CashierInputFilter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wenjing on 2018/4/19.
 */

public class GatheringFragment extends BaseFragment {

    @BindView(R.id.et_money)
    MaterialEditText etMoney;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_gathering;
    }

    @Override
    protected void initView(View view) {
        setToolbarLayout();

        etMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        etMoney.setFilters(new InputFilter[]{new CashierInputFilter()});
        etMoney.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        etMoney.setText(s);
                        etMoney.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    etMoney.setText(s);
                    etMoney.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        etMoney.setText(s.subSequence(0, 1));
                        etMoney.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {


            }

        });
    }

    private void setToolbarLayout() {
        BaseActivity activity = (BaseActivity) getCurActivity();
        QMUITopBarLayout layout = activity.getmQmuiTopBarlayout();
        layout.setBackgroundColor(getResources().getColor(R.color.green_00bc9c));
        layout.removeAllLeftViews();
        layout.addLeftImageButton(R.mipmap.icon_back, -1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCurActivity().finish();
                    }
                });
        activity.setTitleColor(getResources().getColor(R.color.white));
        activity.getStatusBarHelper().setStatusBarDarkMode(getCurActivity());

        Button button = layout.addRightTextButton(getResources().getString(R.string.collection_record), R.id.iv_right);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("listBean", null);
                bundle.putInt("type", RequestCode.DAY_TYPE);
                bundle.putInt("buttonType", 1);
                UIHelper.showMerchantBillsList(getContext(), bundle);
            }
        });
        button.setTextColor(getResources().getColor(R.color.white));

    }


    @OnClick({R.id.rb_alipay, R.id.rb_wepay, R.id.rb_ourpay})
    public void onViewClicked(View view) {
        String money = etMoney.getText().toString().trim();
        if (TextUtil.isEmpty(money) || money.equals("0")) {
            AppContext.showToast(R.string.tip_right_money);
            return;
        }
        if (Double.parseDouble(money)>1000000){
            AppContext.showToast("金额过大");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString("money", money);
        //默认用   系统支付
        int payChannel = RequestConstant.PAYCHANNEL_TYPE_SYSTEM;
        switch (view.getId()) {
            case R.id.rb_alipay:
                payChannel = RequestConstant.PAYCHANNEL_TYPE_ALIPAY;
                break;
            case R.id.rb_wepay:
                payChannel = RequestConstant.PAYCHANNEL_TYPE_WEPAY;
                break;
            case R.id.rb_ourpay:
                payChannel = RequestConstant.PAYCHANNEL_TYPE_SYSTEM;
                break;
        }

        bundle.putInt(TabCodeConstant.KEY_PAYCHANNEL, payChannel);
        UIHelper.showTwoDimensionalCode(getContext(), bundle);
        getCurActivity().finish();
    }


}
