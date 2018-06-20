package com.wenjing.yinfutong.function.mine.fragment;

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
 * Created by ${luoyingtao} on 2018\3\20 0020.
 */

public class ResetLoginPsdFragment extends BaseFragment {

    @BindView(R.id.et_reset_lpsd_oldpsd)
    MaterialEditText etResetLpsdOldpsd;
    @BindView(R.id.et_rlpsd_first_newpsd)
    MaterialEditText etRlpsdFirstNewpsd;
    @BindView(R.id.et_rlpsd_again_newpsd)
    MaterialEditText etRlpsdAgainNewpsd;
    @BindView(R.id.tv_rlpsd_commit)
    TextView tvRlpsdCommit;


    // 每个EditText  是否为空的状态   默认为空   false
    private Map<Integer, Boolean> etFlagMap = new HashMap<Integer, Boolean>() {
        {
            put(R.id.et_reset_lpsd_oldpsd, false);
            put(R.id.et_rlpsd_first_newpsd, false);
            put(R.id.et_rlpsd_again_newpsd, false);
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_resetloginpsd;
    }

    @Override
    protected void initView(View view) {
        tvRlpsdCommit.setClickable(false);
        addETTxtChangeListen(view,R.id.et_reset_lpsd_oldpsd);
        addETTxtChangeListen(view,R.id.et_rlpsd_first_newpsd);
        addETTxtChangeListen(view,R.id.et_rlpsd_again_newpsd);
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
        if (etFlagMap.get(R.id.et_reset_lpsd_oldpsd)
                && etFlagMap.get(R.id.et_rlpsd_first_newpsd)
                && etFlagMap.get(R.id.et_rlpsd_again_newpsd)
                && LocalUtils.isLoginPsdFormatOk(etRlpsdFirstNewpsd)
                && isNewPsdEqual()) {
            tvRlpsdCommit.setBackgroundResource(R.drawable.green_btn_pressed);
            tvRlpsdCommit.setClickable(true);
        }else {
            tvRlpsdCommit.setBackgroundResource(R.drawable.gray_btn_normal);
            tvRlpsdCommit.setClickable(false);
        }

    }

    @OnClick(R.id.tv_rlpsd_commit)
    public void onViewClicked() {

//        if (isOldPsdEmpty()) return;
//
//        if (isFirstNewPsdEmpty()) return;
//
//        if(!LocalUtils.isLoginPsdFormatOk(etRlpsdFirstNewpsd)) return;
//
//        if (isAgainNewPsdEmpty()) return;
//
//        if (!isNewPsdEqual()) return;


        String oldPsd = etResetLpsdOldpsd.getText().toString().trim();
        String newPsd = etRlpsdAgainNewpsd.getText().toString().trim();

        PersonalModifyRequestBody body = new PersonalModifyRequestBody();
        body.setRegisterLang(AppContext.getLangulage());
        body.setOldLoginPwd(MD5Util.encrypt(oldPsd));
        body.setNewLoginPwd(MD5Util.encrypt(newPsd));
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
        String oldPsd = etResetLpsdOldpsd.getText().toString().trim();
        if (TextUtil.isEmpty(oldPsd)) {
            AppContext.showToast(R.string.old_psd_empty);
            return true;
        }
        return false;
    }

    private boolean isFirstNewPsdEmpty() {
        String firstNewPsd = etRlpsdFirstNewpsd.getText().toString().trim();
        if (TextUtil.isEmpty(firstNewPsd)) {
            AppContext.showToast(R.string.new_psd_empty);
            return true;
        }
        return false;
    }

    private boolean isAgainNewPsdEmpty() {
        String againNewPsd = etRlpsdAgainNewpsd.getText().toString().trim();
        if (TextUtil.isEmpty(againNewPsd)) {
            AppContext.showToast(R.string.again_new_psd_empty);
            return true;
        }
        return false;
    }

    private boolean isNewPsdEqual() {
        String firstNewPsd = etRlpsdFirstNewpsd.getText().toString().trim();
        String againNewPsd = etRlpsdAgainNewpsd.getText().toString().trim();

        if (firstNewPsd.equals(againNewPsd)) {
            return true;
        }
//        AppContext.showToast(R.string.newpsd_not_equal);
        return false;
    }

}
