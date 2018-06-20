package com.wenjing.yinfutong.function.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
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
import com.wenjing.yinfutong.model.RegionBean;
import com.wenjing.yinfutong.model.body.SMSSendRequestBody;
import com.wenjing.yinfutong.model.body.SMSVerifyRequestBody;
import com.wenjing.yinfutong.utils.BaseTimerTextHandler;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TextUtil;
import com.wenjing.yinfutong.utils.Validator;
import com.wenjing.yinfutong.utils.VerifyCodeUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by luoyingtao on 2018\3\14 0014.
 */

public class RegisterFragment extends BaseFragment {

    @BindView(R.id.et_r_phone)
    MaterialEditText etRPhone;
    @BindView(R.id.et_r_verifycode)
    MaterialEditText etRVerifycode;
    @BindView(R.id.tv_r_obtain_verifycode)
    TextView tvRObtainVerifycode;
    @BindView(R.id.tv_r_nextstep)
    TextView tvRNextstep;
    @BindView(R.id.tv_r_regioncode)
    TextView tvRRegioncode;
    @BindView(R.id.tv_r_regionname)
    TextView tvRRegionname;


    private TxtTimer txtTimer = new TxtTimer(this);

    private int down_regionposition;
    private RegionBean regionBean = RegionBean.getDefault();

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View view) {
        initDatas();

        etRPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtil.isEmpty(String.valueOf(charSequence))) {
                    tvRNextstep.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gray_btn_normal));
                } else {
                    tvRNextstep.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.green_btn_pressed));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initDatas() {
        tvRRegionname.setText(regionBean.getSupportArea());
        tvRRegioncode.setText("+" + regionBean.getSupportPre());
    }

    @OnClick({R.id.r_region_line, R.id.tv_r_obtain_verifycode, R.id.tv_r_nextstep})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.r_region_line:
                selectRegion();
                break;
            case R.id.tv_r_obtain_verifycode:
                obtainVerifyCode();
                break;
            case R.id.tv_r_nextstep:
                nextStep();
                break;
        }
    }

    private void selectRegion() {
        Bundle bundle = new Bundle();
        bundle.putInt(TabCodeConstant.KEY_TABCODE, TabCodeConstant.TAB_REGISTER);
        bundle.putInt(TabCodeConstant.KEY_REGION_POSITION,down_regionposition);
        UIHelper.showRegionForResult(this, bundle);
    }

    private void nextStep() {
        if (isPhoneEmpty())
            return;

        if (isVerifyCodeEmpty())
            return;

        final String phone = etRPhone.getText().toString().trim();
        String smsCode = etRVerifycode.getText().toString().trim();

        SMSVerifyRequestBody bean = new SMSVerifyRequestBody();
        bean.setAccountType(RequestConstant.ACCOUNT_TYPE_PHONE);
        bean.setAccount(regionBean == null ? "" : regionBean.getSupportPre() + phone);
        bean.setSmsType(RequestConstant.SMS_TYPE_REGISTER);
        bean.setSmsCode(smsCode);

        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.verifySMS(bean),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if (data.getCode() == 0) {
                            AppContext.showToast(R.string.sms_verify_success);

                            Bundle bundle = new Bundle();
                            bundle.putString(TabCodeConstant.KEY_ABOVE_VERIFIED_PHONE, phone);
                            bundle.putInt(TabCodeConstant.KEY_ABOVE_REGIONID, regionBean == null ? -1 : regionBean.getId());

                            getCurActivity().finish();
                            UIHelper.showSetPsd(getContext(), bundle);
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

    private void obtainVerifyCode() {
        if (isPhoneEmpty())
            return;

        if(!isFormatOk()) return;

        String phone = etRPhone.getText().toString().trim();

        SMSSendRequestBody bean = new SMSSendRequestBody();
        bean.setAccountType(RequestConstant.getAccountType(phone));
        bean.setAccount(phone);
        bean.setSmsType(RequestConstant.SMS_TYPE_REGISTER);
        bean.setRegisterLang(AppContext.getLangulage());
        bean.setAttributionId(regionBean == null ? -1 : regionBean.getId());

        //暂时不需要  回调
        VerifyCodeUtils.obtainSmsVerifyCode(getContext(), sMineApi, bean, new OnResponseListener<BaseResponse>() {
            @Override
            public void onNext(BaseResponse data) {
                if(data.getCode() == 0){
                    AppContext.showToast(R.string.verifycode_sent);
                    txtTimer.start();// 开始计时
                }else {
                    AppContext.showToast(data.getMsg());
                    tvRObtainVerifycode.setClickable(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                AppContext.showToast(e.getMessage());
                tvRObtainVerifycode.setClickable(true);
            }
        });
        tvRObtainVerifycode.setClickable(false);
    }

    private boolean isFormatOk() {
        String phone = etRPhone.getText().toString().trim();
        if(Validator.isMobile(phone)){
            return true;
        }
        AppContext.showToast(R.string.format_no_phone);
        return false;
    }

    private boolean isPhoneEmpty() {
        String phone = etRPhone.getText().toString().trim();
        if (TextUtil.isEmpty(phone)) {
            AppContext.showToast(R.string.phone_empty);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(txtTimer != null){
            txtTimer.stop();
            txtTimer = null;
        }
    }

    private boolean isVerifyCodeEmpty() {
        String verifyCode = etRVerifycode.getText().toString().trim();
        if (TextUtil.isEmpty(verifyCode)) {
            AppContext.showToast(R.string.verifycode_empty);
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TabCodeConstant.REQUESTCODE_REGIONSELECT) {
                RegionBean bean = data.getParcelableExtra(TabCodeConstant.KEY_REGION_BEAN);
                down_regionposition = data.getIntExtra(TabCodeConstant.KEY_REGION_POSITION,0);
                tvRRegionname.setText(bean.getSupportArea());
                tvRRegioncode.setText("+" + bean.getSupportPre());

                regionBean = bean;
            }
        }
    }


    private static class TxtTimer extends BaseTimerTextHandler {

        private WeakReference<RegisterFragment> reference;

        public TxtTimer(RegisterFragment fragment){
            reference = new WeakReference<RegisterFragment>(fragment);
        }

        @Override
        public void waitingRun(int leaveTime) {
            reference.get().tvRObtainVerifycode.setText(String.valueOf(leaveTime) + getUnit());
        }

        @Override
        public void waitingComplete() {
            reference.get().tvRObtainVerifycode.setText(R.string.resend);
            reference.get().tvRObtainVerifycode.setClickable(true);
        }
    }

}
