package com.wenjing.yinfutong.function.mine.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.body.RetrieveLPsdRequestBody;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MD5Util;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ${luoyingtao} on 2018\3\19 0019.
 */

public class RetrievePsdSecondFragment extends BaseFragment {
    public static final String TAG = "RetrievePsdSecond";

    @BindView(R.id.et_rp_psd)
    MaterialEditText etRpPsd;
    @BindView(R.id.et_rp_psd_confirm)
    MaterialEditText etRpPsdConfirm;
    @BindView(R.id.tv_rp_commit)
    TextView tvRpCommit;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_retrievepsd_second;
    }

    @Override
    protected void initView(View view) {
        tvRpCommit.setClickable(false);
        addTextListener(etRpPsd);
        addTextListener(etRpPsdConfirm);
    }

    private void addTextListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!isPsdEmpty()
                        && !isPsdConfirmEmpty()
                        && LocalUtils.isLoginPsdFormatOk(etRpPsd)
                        && isPsdEqual()) {
                    tvRpCommit.setBackgroundResource(R.drawable.green_btn_pressed);
                    tvRpCommit.setClickable(true);
                }else {
                    tvRpCommit.setBackgroundResource(R.drawable.gray_btn_normal);
                    tvRpCommit.setClickable(false);
                }
            }
        });
    }

    @OnClick(R.id.tv_rp_commit)
    public void onViewClicked() {
        //        if(isPsdEmpty()) return;
        //
        //        if(!LocalUtils.isLoginPsdFormatOk(etRpPsd)) return;
        //
        //        if(isPsdConfirmEmpty()) return;
        //
        //        if(!isPsdEqual()) return;

        Bundle bundle = getArguments();
        String phone = bundle.getString(TabCodeConstant.KEY_ABOVE_VERIFIED_PHONE);
        String verifycode = bundle.getString(TabCodeConstant.KEY_ABOVE_VERIFIED_VERIFYCODE);


        String psd = etRpPsdConfirm.getText().toString().trim();

        RetrieveLPsdRequestBody body = new RetrieveLPsdRequestBody();
        body.setCellphone(phone);
        body.setNewPWD(MD5Util.encrypt(psd));
        body.setRegisterLang(AppContext.getLangulage());
        body.setValiCode(verifycode);

        Log.i(TAG, body.toString());


        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.retrieveLoginPsd(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        Log.i(TAG, data.toString());
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

    private boolean isPsdEmpty() {
        String psd = etRpPsd.getText().toString().trim();
        if (TextUtil.isEmpty(psd)) {
//            AppContext.showToast(R.string.psd_empty);
            return true;
        }
        return false;
    }

    private boolean isPsdConfirmEmpty() {
        String psdConfirm = etRpPsdConfirm.getText().toString().trim();
        if (TextUtil.isEmpty(psdConfirm)) {
//            AppContext.showToast(R.string.psd_confirm_empty);
            return true;
        }
        return false;
    }

    private boolean isPsdEqual() {
        String psd = etRpPsd.getText().toString().trim();
        String psdConfirm = etRpPsdConfirm.getText().toString().trim();

        if (psd.equals(psdConfirm)) {
            return true;
        }
//        AppContext.showToast(R.string.psd_not_equal);
        return false;
    }

}
