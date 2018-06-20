package com.wenjing.yinfutong.function.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.RegionBean;
import com.wenjing.yinfutong.model.body.SMSSendRequestBody;
import com.wenjing.yinfutong.model.body.SMSVerifyRequestBody;
import com.wenjing.yinfutong.utils.BaseTimerTextHandler;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.TextUtil;
import com.wenjing.yinfutong.utils.Validator;
import com.wenjing.yinfutong.utils.VerifyCodeUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by luoyingtao on 2018\3\14 0014.
 */

public class RetrievePsdFragment extends BaseFragment {
    public static final String TAG = RetrievePsdFragment.class.getSimpleName();

    @BindView(R.id.et_rp_poe)
    MaterialEditText etRpPoe;
    @BindView(R.id.et_rp_verifycode)
    MaterialEditText etRpVerifycode;
    @BindView(R.id.tv_rp_obtain_verifycode)
    TextView tvRpObtainVerifycode;
    @BindView(R.id.tv_rp_regioncode)
    TextView tvRpRegioncode;
    @BindView(R.id.tv_rp_regionname)
    TextView tvRpRegionname;
    @BindView(R.id.tv_rp_first_step)
    TextView tvRpFirstStep;

    private int tabCode;

    private TxtTimer txtTimer = new TxtTimer(this);

    private int down_regionposition;
    private RegionBean regionBean = RegionBean.getDefault();

    private boolean isPhoneEmpty       = true;
    private boolean isVerifyCodeEmpty  = true;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_retrieve;
    }

    @Override
    protected void initView(View view) {
        //区分  前一个页面
        tabCode = getArguments().getInt(TabCodeConstant.KEY_TABCODE);
        initDatas();
        initCommit();
    }

    private void initCommit() {
        tvRpFirstStep.setClickable(false);

        etRpPoe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPhoneEmpty = TextUtils.isEmpty(editable.toString());
                setTvCommitActive();
            }
        });

        etRpVerifycode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isVerifyCodeEmpty = TextUtils.isEmpty(editable.toString());
                setTvCommitActive();
            }
        });

    }

    private void setTvCommitActive() {
        if(!isPhoneEmpty
                && !isVerifyCodeEmpty
                && LocalUtils.isPhoneFormatOk(etRpPoe)
                && LocalUtils.isVerifyCodeFormatOk(etRpVerifycode)){
                tvRpFirstStep.setBackgroundResource(R.drawable.green_btn_pressed);
                tvRpFirstStep.setClickable(true);
        }else {
            tvRpFirstStep.setBackgroundResource(R.drawable.gray_btn_normal);
            tvRpFirstStep.setClickable(false);
        }
    }

    private void initDatas() {
        tvRpRegionname.setText(regionBean.getSupportArea());
        tvRpRegioncode.setText("+" + regionBean.getSupportPre());
    }

    @OnClick({R.id.rp_region_line, R.id.tv_rp_obtain_verifycode, R.id.tv_rp_first_step})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rp_region_line:
                selectRegion();
                break;

            case R.id.tv_rp_obtain_verifycode:
                obtainVerifycode();
                break;
            case R.id.tv_rp_first_step:
                nextStep();
                break;
        }
    }

    private void selectRegion() {
        Bundle bundle = new Bundle();
        bundle.putInt(TabCodeConstant.KEY_TABCODE, TabCodeConstant.TAB_RETRIEVEPSD);
        bundle.putInt(TabCodeConstant.KEY_REGION_POSITION, down_regionposition);
        UIHelper.showRegionForResult(this, bundle);
    }

    private void nextStep() {
        //        if (isPoeEmpty()) return;
        //        if (isVerifyCodeEmpty()) return;

        String phone = etRpPoe.getText().toString().trim();
        String smsCode = etRpVerifycode.getText().toString().trim();

        SMSVerifyRequestBody body = new SMSVerifyRequestBody();
        body.setAccount(regionBean == null ? "" : regionBean.getSupportPre() + phone);
        body.setAccountType(RequestConstant.getAccountType(phone));

        if (tabCode == TabCodeConstant.TAB_LOGIN) {
            body.setSmsType(RequestConstant.SMS_TYPE_LOGIN);
        } else if (tabCode == TabCodeConstant.TAB_RESETPAYMENTPSD || tabCode == TabCodeConstant.TAB_PAYMENTPASSWORD) {
            body.setSmsType(RequestConstant.SMS_TYPE_TRADE);

            //只有  在线状态 才有下面缓存数据
            Customer customer = AppContext.instance().getLoginUser();
            body.setUserName(customer.getAccount());
            body.setIdcard(customer.getIdcard());
        }

        body.setSmsCode(smsCode);

        TLog.log(TAG, body.toString());

        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.verifySMS(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if (data.getCode() == 0) {
                            AppContext.showToast(R.string.sms_verify_success);
                            verifiedToRetrievePsd();
                        } else {
                            AppContext.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.getMessage());
                    }
                });

    }

    private void verifiedToRetrievePsd() {
        Bundle bundle = new Bundle();
        bundle.putString(TabCodeConstant.KEY_ABOVE_VERIFIED_PHONE, etRpPoe.getText().toString().trim());
        bundle.putString(TabCodeConstant.KEY_ABOVE_VERIFIED_VERIFYCODE, etRpVerifycode.getText().toString().trim());

        if (tabCode == TabCodeConstant.TAB_LOGIN) {
            UIHelper.showRetrievePsdSecond(getContext(), bundle);
        } else if (tabCode == TabCodeConstant.TAB_RESETPAYMENTPSD || tabCode == TabCodeConstant.TAB_PAYMENTPASSWORD) {
            UIHelper.showRetrievePaymentPsd(getContext(), bundle);
        }
        getCurActivity().finish();
    }

    private void obtainVerifycode() {
        if (isPoeEmpty()) return;

        if (!isPhoneFormatOk()) return;

        String phone = etRpPoe.getText().toString().trim();

        SMSSendRequestBody bean = new SMSSendRequestBody();
        bean.setAccountType(RequestConstant.getAccountType(phone));
        bean.setAccount(phone);

        if (tabCode == TabCodeConstant.TAB_LOGIN) {
            bean.setSmsType(RequestConstant.SMS_TYPE_LOGIN);
        } else if (tabCode == TabCodeConstant.TAB_RESETPAYMENTPSD || tabCode == TabCodeConstant.TAB_PAYMENTPASSWORD) {
            bean.setSmsType(RequestConstant.SMS_TYPE_TRADE);
        }

        bean.setRegisterLang(AppContext.getLangulage());
        bean.setAttributionId(regionBean == null ? -1 : regionBean.getId());

        VerifyCodeUtils.obtainSmsVerifyCode(getContext(),
                sMineApi,
                bean,
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if (data.getCode() == 0) {
                            AppContext.showToast(R.string.verifycode_sent);
                            txtTimer.start();
                        } else {
                            AppContext.showToast(data.getMsg());
                            tvRpObtainVerifycode.setClickable(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.getMessage());
                        tvRpObtainVerifycode.setClickable(true);
                    }
                });
        tvRpObtainVerifycode.setClickable(false);
    }

    private boolean isPhoneFormatOk() {
        String phone = etRpPoe.getText().toString().trim();
        if (Validator.isMobile(phone)) {
            return true;
        }
        AppContext.showToast(R.string.format_no_phone);
        return false;
    }

    private boolean isPoeEmpty() {
        String poe = etRpPoe.getText().toString().trim();
        if (TextUtil.isEmpty(poe)) {
            AppContext.showToast(R.string.poe_empty);
            return true;
        }
        return false;
    }

    private boolean isVerifyCodeEmpty() {
        String verifyCode = etRpVerifycode.getText().toString().trim();
        if (TextUtil.isEmpty(verifyCode)) {
            AppContext.showToast(R.string.verifycode_empty);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (txtTimer != null) {
            txtTimer.stop();
            txtTimer = null;
        }
    }

    private static class TxtTimer extends BaseTimerTextHandler {

        private WeakReference<RetrievePsdFragment> reference;

        public TxtTimer(RetrievePsdFragment fragment) {
            reference = new WeakReference<RetrievePsdFragment>(fragment);
        }

        @Override
        public void waitingRun(int leaveTime) {
            reference.get().tvRpObtainVerifycode.setText(String.valueOf(leaveTime) + getUnit());
        }

        @Override
        public void waitingComplete() {
            reference.get().tvRpObtainVerifycode.setText(R.string.resend);
            reference.get().tvRpObtainVerifycode.setClickable(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TabCodeConstant.REQUESTCODE_REGIONSELECT) {
                RegionBean bean = data.getParcelableExtra(TabCodeConstant.KEY_REGION_BEAN);
                down_regionposition = data.getIntExtra(TabCodeConstant.KEY_REGION_POSITION, 0);
                tvRpRegionname.setText(bean.getSupportArea());
                tvRpRegioncode.setText("+" + bean.getSupportPre());
                regionBean = bean;
            }
        }

    }
}
