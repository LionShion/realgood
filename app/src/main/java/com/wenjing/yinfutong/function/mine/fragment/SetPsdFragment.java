package com.wenjing.yinfutong.function.mine.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.jpush.JpushUtils;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.UserBean;
import com.wenjing.yinfutong.model.body.LoginRequestBody;
import com.wenjing.yinfutong.model.body.RegisterRequestBody;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MD5Util;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TDevice;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\19 0019.
 */

public class SetPsdFragment extends BaseFragment {
    private static final String TAG = "SetPsdFragment";

    @BindView(R.id.et_setpsd_psd)
    MaterialEditText etSetpsdPsd;
    @BindView(R.id.et_setpsd_confirm)
    MaterialEditText etSetpsdConfirm;
    @BindView(R.id.tv_setpsd_nextstep)
    TextView tvSetpsdNextstep;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_setpsd;
    }

    @Override
    protected void initView(View view) {
        tvSetpsdNextstep.setClickable(false);
        addTextListener(etSetpsdPsd);
        addTextListener(etSetpsdConfirm);
    }

    private void addTextListener(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isPsdEmpty()
                        && !isPsdConfirmEmpty()
                        && LocalUtils.isLoginPsdFormatOk(etSetpsdPsd)
                        && isPsdEqual()){
                    tvSetpsdNextstep.setBackgroundResource(R.drawable.green_btn_pressed);
                    tvSetpsdNextstep.setClickable(true);
                }else {
                    tvSetpsdNextstep.setBackgroundResource(R.drawable.gray_btn_normal);
                    tvSetpsdNextstep.setClickable(false);
                }
            }
        });
    }

    @OnClick({R.id.tv_setpsd_nextstep, R.id.yft_protocol_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_setpsd_nextstep:
                nextStep();
                break;
            case R.id.yft_protocol_line:

                break;
        }
    }

    private void nextStep() {
//        if (isPsdEmpty()) return;
//
//        if(!LocalUtils.isLoginPsdFormatOk(etSetpsdPsd)) return;
//
//        if (isPsdConfirmEmpty()) return;
//
//        if (!isPsdEqual()) return;

        Bundle bundle = getArguments();
        String verified_phone = bundle.getString(TabCodeConstant.KEY_ABOVE_VERIFIED_PHONE);
        int regionId = bundle.getInt(TabCodeConstant.KEY_ABOVE_REGIONID);

        String psd = etSetpsdPsd.getText().toString().trim();

        RegisterRequestBody body = new RegisterRequestBody();
        body.setRegisterLang(AppContext.getLangulage());
        body.setAccountType(RequestConstant.getAccountType(verified_phone));
        body.setAccount(verified_phone);
        body.setPassword(MD5Util.encrypt(psd));
        body.setDevice(TDevice.getIMEI(getContext()));
        body.setAttributionId(regionId);

        Log.i(TAG,body.toString());

        showWaitDialog();

        new MyObservable<BaseResponse<UserBean>>().observe(getContext(),
                sMineApi.register(body),
                new OnResponseListener<BaseResponse<UserBean>>() {
                    @Override
                    public void onNext(BaseResponse<UserBean> data) {
                        if(data.getCode() == 0 && data.getData() != null){
                            AppContext.showToast(R.string.register_success);
                            registerSucc(data.getData());
                        }else{
                            AppContext.showToast(data.getMsg());
                            autoLoginFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                    }
                });
    }

    private void registerSucc(UserBean data) {
        //情况一 直接退出页面
//        getCurActivity().finish();

        //情况 二  直接调登录接口,进入主页，或者失败进入登录页面
        final String psd = etSetpsdConfirm.getText().toString().trim();

        LoginRequestBody body = new LoginRequestBody();
        body.setAccount(data.getCellphone());
        body.setAccountType(data.getAccountType());
        body.setLoginLang(AppContext.getLangulage());
        body.setPassword(MD5Util.encrypt(psd));
        body.setDevice(TDevice.getIMEI(getContext()));
        body.setDeviceType(TDevice.getPhoneName());
        showWaitDialog();

        new MyObservable<BaseResponse<Customer>>().observe(getContext(),
                sMineApi.getLogin(body),
                new OnResponseListener<BaseResponse<Customer>>() {
                    @Override
                    public void onNext(BaseResponse<Customer> data) {
                        hideWaitDialog();

                        if(data.getCode() == 0 && data.getData() != null){
//                            AppContext.showToast(R.string.login_success);
                            Customer customer = data.getData();
                            AppContext.instance().saveLoginInfo(getContext(), customer);//本地缓存 客户信息
                            AppContext.saveLoginPsd(psd);

                            JpushUtils.setMyJpushAlias(customer);
                            getCurActivity().finish();
                        }else {
                            AppContext.showToast(data.getMsg());
                            autoLoginFail();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();

                        AppContext.showToast(e.getMessage());
                        autoLoginFail();
                    }
                });

    }

    private void autoLoginFail() {
        UIHelper.showLogin(getContext());
        getCurActivity().finish();
    }

    private boolean isPsdEmpty() {
        String psd = etSetpsdPsd.getText().toString().trim();
        if (TextUtil.isEmpty(psd)) {
//            AppContext.showToast(R.string.psd_empty);
            return true;
        }
        return false;
    }

    private boolean isPsdConfirmEmpty() {
        String psdConfirm = etSetpsdConfirm.getText().toString().trim();
        if (TextUtil.isEmpty(psdConfirm)) {
//            AppContext.showToast(R.string.psd_confirm_empty);
            return true;
        }
        return false;
    }

    private boolean isPsdEqual() {
        String psd = etSetpsdPsd.getText().toString().trim();
        String psdConfirm = etSetpsdConfirm.getText().toString().trim();

        if (psd.equals(psdConfirm)) {
            return true;
        }
//        AppContext.showToast(R.string.psd_not_equal);
        return false;
    }

}
