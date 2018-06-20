package com.wenjing.yinfutong.function.mine.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.PersonalModifyRequestBody;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MD5Util;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TextUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\21 0021.
 */

public class ResetPaymentPsdFragment extends BaseFragment {

    @BindView(R.id.et_rpp_oldpsd)
    MaterialEditText etRppOldpsd;
    @BindView(R.id.et_rpp_first_newpsd)
    MaterialEditText etRppFirstNewpsd;
    @BindView(R.id.et_rpp_again_newpsd)
    MaterialEditText etRppAgainNewpsd;
    @BindView(R.id.tv_rpp_commit)
    TextView tvRppCommit;

    // 每个EditText  是否为空的状态   默认为空   false
    private Map<Integer, Boolean> etFlagMap = new HashMap<Integer, Boolean>() {
        {
            put(R.id.et_rpp_oldpsd, false);
            put(R.id.et_rpp_first_newpsd, false);
            put(R.id.et_rpp_again_newpsd, false);
        }
    };


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_resetpaymentpsd;
    }

    @Override
    protected void initView(View view) {
        tvRppCommit.setClickable(false);
        addETTxtChangeListen(view,R.id.et_rpp_oldpsd);
        addETTxtChangeListen(view,R.id.et_rpp_first_newpsd);
        addETTxtChangeListen(view,R.id.et_rpp_again_newpsd);
    }

    private void addETTxtChangeListen(View view, @IdRes final int resId) {
        EditText editText = view.findViewById(resId);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                etFlagMap.put(resId, !TextUtil.isEmpty(editable.toString()));
                setTvCommitActive();
            }
        });

    }

    private void setTvCommitActive() {
        if (etFlagMap.get(R.id.et_rpp_oldpsd)
                && etFlagMap.get(R.id.et_rpp_first_newpsd)
                && etFlagMap.get(R.id.et_rpp_again_newpsd)
                && LocalUtils.isPayPsdFormatOk(etRppFirstNewpsd)
                && isNewPsdEqual()) {
            tvRppCommit.setBackgroundResource(R.drawable.green_btn_pressed);
            tvRppCommit.setClickable(true);
        }else {
            tvRppCommit.setBackgroundResource(R.drawable.gray_btn_normal);
            tvRppCommit.setClickable(false);
        }

    }

    @OnClick({R.id.cb_rpp_forgetpsd, R.id.tv_rpp_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_rpp_forgetpsd:
                forgetPaymentPsd();
                break;
            case R.id.tv_rpp_commit:
                rppCommit();
                break;
        }
    }

    private void forgetPaymentPsd() {
        Bundle bundle = new Bundle();
        bundle.putInt(TabCodeConstant.KEY_TABCODE, TabCodeConstant.TAB_RESETPAYMENTPSD);
        getCurActivity().finish();
        UIHelper.showRetrievePsd(getContext(), bundle);
    }

    private void rppCommit() {
//        if (isOldPsdEmpty()) return;
//
//        if (isFirstNewPsdEmpty()) return;
//
//        if(!LocalUtils.isPayPsdFormatOk(etRppFirstNewpsd)) return;
//
//        if (isAgainNewPsdEmpty()) return;
//
//
//        if (!isNewPsdEqual()) return;


        String oldPsd = etRppOldpsd.getText().toString().trim();
        String newPsd = etRppAgainNewpsd.getText().toString().trim();

        PersonalModifyRequestBody body = new PersonalModifyRequestBody();
        body.setRegisterLang(AppContext.getLangulage());
        body.setOldTradePwd(MD5Util.encrypt(oldPsd));
        body.setNewTradePwd(MD5Util.encrypt(newPsd));
        Customer customer = AppContext.instance().getLoginUser();

        body.setAccountId(customer.getAccountId());

        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.modifyPersonInfo(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if (data.getCode() == 0) {
                            AppContext.showToast(R.string.succ_resetpsd);
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

    private boolean isOldPsdEmpty() {
        String oldPsd = etRppOldpsd.getText().toString().trim();
        if (TextUtil.isEmpty(oldPsd)) {
            AppContext.showToast(R.string.old_psd_empty);
            return true;
        }
        return false;
    }

    private boolean isFirstNewPsdEmpty() {
        String firstNewPsd = etRppFirstNewpsd.getText().toString().trim();
        if (TextUtil.isEmpty(firstNewPsd)) {
            AppContext.showToast(R.string.new_psd_empty);
            return true;
        }
        return false;
    }

    private boolean isAgainNewPsdEmpty() {
        String againNewPsd = etRppAgainNewpsd.getText().toString().trim();
        if (TextUtil.isEmpty(againNewPsd)) {
            AppContext.showToast(R.string.again_new_psd_empty);
            return true;
        }
        return false;
    }

    private boolean isNewPsdEqual() {
        String firstNewPsd = etRppFirstNewpsd.getText().toString().trim();
        String againNewPsd = etRppAgainNewpsd.getText().toString().trim();

        if (firstNewPsd.equals(againNewPsd)) {
            return true;
        }
//        AppContext.showToast(R.string.newpsd_not_equal);
        return false;
    }


}
