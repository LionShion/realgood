package com.wenjing.yinfutong.function.mine.fragment;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.PersonalModifyRequestBody;
import com.wenjing.yinfutong.model.body.SMSSendRequestBody;
import com.wenjing.yinfutong.utils.BaseTimerTextHandler;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TextUtil;
import com.wenjing.yinfutong.utils.VerifyCodeUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\15 0015.
 */

public class TieEmailFragment extends BaseFragment {


    @BindView(R.id.et_emailaddress)
    MaterialEditText etEmailaddress;
    @BindView(R.id.et_email_verifycode)
    MaterialEditText etEmailVerifycode;
    @BindView(R.id.tv_email_obtain_verifycode)
    TextView tvEmailObtainVerifycode;
    @BindView(R.id.tv_email_commit)
    TextView tvEmailCommit;

    private boolean isEmailEmpty = true;
    private boolean isVerifyEmpty = true;

    private TxtTimer txtTimer = new TxtTimer(this);

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tieemail;
    }

    @Override
    protected void initView(View view) {
        initCommit();
    }

    private void initCommit() {
        tvEmailCommit.setClickable(false);
        etEmailaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isEmailEmpty = TextUtil.isEmpty(editable.toString().trim());
                isTVCommitActive();
            }
        });

        etEmailVerifycode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isVerifyEmpty = TextUtil.isEmpty(editable.toString().trim());
                isTVCommitActive();
            }
        });
    }

    private void isTVCommitActive(){
        if(!isEmailEmpty
                && !isVerifyEmpty
                && LocalUtils.isEmailFormatOk(etEmailaddress)
                && LocalUtils.isVerifyCodeFormatOk(etEmailVerifycode)
                ){
            tvEmailCommit.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.green_btn_pressed));
            tvEmailCommit.setClickable(true);
        }else {
            tvEmailCommit.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.gray_btn_normal));
            tvEmailCommit.setClickable(false);
        }
    }

    @OnClick({R.id.tv_email_obtain_verifycode, R.id.tv_email_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_email_obtain_verifycode:
                obtainEmailVerifycode();
                break;
            case R.id.tv_email_commit:
                emailCommit();
                break;
        }
    }

    private void obtainEmailVerifycode() {
        if (isEmailEmpty) {
            AppContext.showToast(R.string.email_empty);
            return;
        }
        if(!LocalUtils.isEmailFormatOk(etEmailaddress)) return;

        String emailAddress = etEmailaddress.getText().toString().trim();

        SMSSendRequestBody body = new SMSSendRequestBody();
        Customer customer = AppContext.instance().getLoginUser();

        body.setAccount(emailAddress);
        body.setAccountType(RequestConstant.ACCOUNT_TYPE_EMAIL );
        body.setSmsType(RequestConstant.SMS_TYPE_BINDEMAIL);

        VerifyCodeUtils.obtainSmsVerifyCode(getContext(),
                sMineApi,
                body,
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if(data.getCode() == 0){
                            txtTimer.start();
                            AppContext.showToast(R.string.verifycode_sent);
                        }else {
                            AppContext.showToast(data.getMsg());
                            tvEmailObtainVerifycode.setClickable(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        tvEmailObtainVerifycode.setClickable(true);
                    }
                });
        tvEmailObtainVerifycode.setClickable(false);
    }

    private void emailCommit() {
//        if (isEmailEmpty) {
//            AppContext.showToast(R.string.email_empty);
//            return;
//        }
//
//        if (isVerifyEmpty) {
//            AppContext.showToast(R.string.verifycode_empty);
//            return;
//        }

        final String email = etEmailaddress.getText().toString().trim();

        PersonalModifyRequestBody body = new PersonalModifyRequestBody();
        Customer customer = AppContext.instance().getLoginUser();
        body.setAccountId(customer.getAccountId());
        body.setCellphone(customer.getCellphone());
        body.setRegisterLang(AppContext.getLangulage());
        body.setNickName(customer.getNickName());
        body.setEmail(email);
        body.setValiCode(etEmailVerifycode.getText().toString().trim());

        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.modifyPersonInfo(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if(data.getCode() == 0){
                            //先 本地静态保存  邮箱
                            AppContext.instance().getLoginUser().setEmail(email);//

                            getCurActivity().finish();
                            AppContext.showToast(R.string.success_tie_email);
                        }else {
                            AppContext.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.getMessage());
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(txtTimer != null){
            txtTimer.stop();
            txtTimer = null;
        }
    }

    private static class TxtTimer extends BaseTimerTextHandler {

        private WeakReference<TieEmailFragment> reference;

        public TxtTimer(TieEmailFragment fragment){
            reference = new WeakReference<TieEmailFragment>(fragment);
        }

        @Override
        public void waitingRun(int leaveTime) {
            reference.get().tvEmailObtainVerifycode.setText(String.valueOf(leaveTime) + getUnit());
        }

        @Override
        public void waitingComplete() {
            reference.get().tvEmailObtainVerifycode.setText(R.string.resend);
            reference.get().tvEmailObtainVerifycode.setClickable(true);
        }
    }

}
