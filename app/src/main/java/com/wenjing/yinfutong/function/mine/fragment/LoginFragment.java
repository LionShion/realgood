package com.wenjing.yinfutong.function.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.wenjing.yinfutong.jpush.JpushUtils;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.RegionBean;
import com.wenjing.yinfutong.model.body.LoginRequestBody;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MD5Util;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TDevice;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.TextUtil;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by luoyingtao on 2018\3\14 0014.
 */

public class LoginFragment extends BaseFragment {
    public static final String TAG = "LoginFragment";

    @BindView(R.id.et_phone)
    MaterialEditText etPhone;
    @BindView(R.id.et_password)
    MaterialEditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_experience)
    TextView tvExperience;
    @BindView(R.id.tv_l_regioncode)
    TextView tvLRegioncode;
    @BindView(R.id.tv_l_regionname)
    TextView tvLRegionname;

    //默认为空
    private boolean isPhoneEmpty = true;
    private boolean isPsdEmpty = true;

    private int down_regionposition;

    private RegionBean regionBean = RegionBean.getDefault();

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View view) {
        initDatas();
        tvLogin.setClickable(false);
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPhoneEmpty = TextUtil.isEmpty(editable.toString());
                setTvLoginActive();
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPsdEmpty = TextUtil.isEmpty(editable.toString());
                setTvLoginActive();
            }
        });

    }

    private void initDatas() {
        tvLRegionname.setText(regionBean.getSupportArea());
        tvLRegioncode.setText("+" + regionBean.getSupportPre());
    }

    private void setTvLoginActive() {
        if (!isPhoneEmpty
                && !isPsdEmpty
                && isFormatAllOk()) {
            tvLogin.setBackgroundResource(R.drawable.green_btn_pressed);
            tvLogin.setClickable(true);
        } else {
            tvLogin.setBackgroundResource(R.drawable.gray_btn_normal);
            tvLogin.setClickable(false);
        }
    }

    @OnClick({R.id.tv_login, R.id.tv_forget, R.id.tv_register, R.id.tv_experience, R.id.l_region_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                login();

                break;
            case R.id.tv_forget:
                retrievePsd();
                break;
            case R.id.tv_register:
                register();
                break;
            case R.id.tv_experience:
                getCurActivity().finish();
                break;
            case R.id.l_region_line:
                regionSelect();
                break;
        }
    }

    private void regionSelect() {
        Bundle bundle = new Bundle();
        bundle.putInt(TabCodeConstant.KEY_TABCODE, TabCodeConstant.TAB_LOGIN);
        bundle.putInt(TabCodeConstant.KEY_REGION_POSITION, down_regionposition);
        UIHelper.showRegionForResult(this, bundle);
    }

    private void register() {
        UIHelper.showRegister(getContext());
        getCurActivity().finish();//结束当前页面
    }

    private void retrievePsd() {
        Bundle bundle = new Bundle();
        bundle.putInt(TabCodeConstant.KEY_TABCODE, TabCodeConstant.TAB_LOGIN);
        UIHelper.showRetrievePsd(getContext(), bundle);
    }

    private void login() {

        //        if (isPhoneEmpty) {
        //            AppContext.showToast(R.string.phone_empty);
        //            return;
        //        }
        //        if (isPsdEmpty) {
        //            AppContext.showToast(R.string.password_empty);
        //            return;
        //        }
        //        if (!isFormatAllOk())
        //            return;


        String phone = etPhone.getText().toString().trim();
        final String psd = etPassword.getText().toString().trim();
        String md5Psd = MD5Util.encrypt(psd);

        LoginRequestBody bean = new LoginRequestBody();
        bean.setAccount(phone);
        bean.setAccountType(RequestConstant.getAccountType(phone));
        bean.setLoginLang(AppContext.getLangulage());
        bean.setPassword(md5Psd);
        bean.setDevice(TDevice.getIMEI(getContext()));
        bean.setDeviceType(TDevice.getPhoneName());

        showWaitDialog();
        new MyObservable<BaseResponse<Customer>>().observe(getContext(),
                sMineApi.getLogin(bean),
                new OnResponseListener<BaseResponse<Customer>>() {
                    @Override
                    public void onNext(BaseResponse<Customer> data) {
                        hideWaitDialog();
                        if (data.getCode() == 0 && data.getData() != null) {
                            Customer customer = data.getData();
                            AppContext.instance().saveLoginInfo(getContext(), customer);
                            AppContext.saveLoginPsd(psd);
                            AppContext.showToast(R.string.login_success);

                            JpushUtils.setMyJpushAlias(customer);
                            getCurActivity().finish();
                        } else {
                            AppContext.showToast(data.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                    }
                });

    }

    private boolean isFormatAllOk() {
        if (!LocalUtils.isPhoneFormatOk(etPhone))
            return false;

        if (!LocalUtils.isLoginPsdFormatOk(etPassword))
            return false;

        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TabCodeConstant.REQUESTCODE_REGIONSELECT) {
                RegionBean bean = data.getParcelableExtra(TabCodeConstant.KEY_REGION_BEAN);
                down_regionposition = data.getIntExtra(TabCodeConstant.KEY_REGION_POSITION, 0);
                tvLRegionname.setText(bean.getSupportArea());
                tvLRegioncode.setText("+" + bean.getSupportPre());
            }
        }
    }

}
