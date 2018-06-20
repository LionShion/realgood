package com.wenjing.yinfutong.function.mine.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.PersonalModifyRequestBody;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MD5Util;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ${luoyingtao} on 2018\3\28 0028.
 */

public class RetrievepaymentPsdFragment extends BaseFragment {

    @BindView(R.id.et_rpayment_psd)
    MaterialEditText etRpaymentPsd;
    @BindView(R.id.et_rpayment_confirm)
    MaterialEditText etRpaymentConfirm;
    @BindView(R.id.tv_rpayment_commit)
    TextView tvRpaymentCommit;

    private boolean isPsdEmpty = true;
    private boolean isConfirmEmpty = true;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_retrievepaymentpsd;
    }

    @Override
    protected void initView(View view) {
        initCommit();
    }

    private void initCommit() {
        tvRpaymentCommit.setClickable(false);

        etRpaymentPsd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isPsdEmpty = TextUtils.isEmpty(editable.toString());
                setTvCommitActive();
            }
        });

        etRpaymentConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                isConfirmEmpty = TextUtils.isEmpty(editable.toString());
                setTvCommitActive();
            }
        });

    }

    private void setTvCommitActive() {
        if(!isPsdEmpty
                && !isConfirmEmpty
                && LocalUtils.isPayPsdFormatOk(etRpaymentPsd)
                && isPsdEqual()){
            tvRpaymentCommit.setBackgroundResource(R.drawable.green_btn_pressed);
            tvRpaymentCommit.setClickable(true);
        }else {
            tvRpaymentCommit.setBackgroundResource(R.drawable.gray_btn_normal);
            tvRpaymentCommit.setClickable(false);
        }
    }


    @OnClick(R.id.tv_rpayment_commit)
    public void onViewClicked() {
//        if (isPsdEmpty) return;
//        if (!LocalUtils.isPayPsdFormatOk(etRpaymentPsd)) return;
//        if (isConfirmEmpty) return;
//        if (!isPsdEqual()) return;

        Bundle bundle = getArguments();
        String phone = bundle.getString(TabCodeConstant.KEY_ABOVE_VERIFIED_PHONE);
        String verifyCode = bundle.getString(TabCodeConstant.KEY_ABOVE_VERIFIED_VERIFYCODE);

        Customer customer = AppContext.instance().getLoginUser();

        String psd = etRpaymentConfirm.getText().toString();

        PersonalModifyRequestBody body = new PersonalModifyRequestBody();
        body.setAccountId(customer.getAccountId());
        body.setCellphone(phone);
        body.setValiCode(verifyCode);
        body.setNewTradePwd(MD5Util.encrypt(psd));
        body.setRegisterLang(AppContext.getLangulage());

        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.modifyPersonInfo(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if (data.getCode() == 0) {
                            AppContext.showToast(R.string.succ_retrievepsd);
                            getCurActivity().finish();
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

    private boolean isPsdEqual() {
        String psd = etRpaymentPsd.getText().toString().trim();
        String confirmPsd = etRpaymentConfirm.getText().toString().trim();

        if (psd.equals(confirmPsd)) {
            return true;
        }
//        AppContext.showToast(R.string.psd_not_equal);
        return false;
    }

}
