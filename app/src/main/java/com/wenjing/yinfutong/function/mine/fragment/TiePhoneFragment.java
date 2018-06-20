package com.wenjing.yinfutong.function.mine.fragment;

import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\15 0015.
 */

public class TiePhoneFragment extends BaseFragment {

    @BindView(R.id.et_phonenum)
    MaterialEditText etPhonenum;
    @BindView(R.id.et_verifycode)
    MaterialEditText etVerifycode;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tiephone;
    }

    @Override
    protected void initView(View view) {

    }

    @OnClick({R.id.tv_obtain_verifycode, R.id.tv_phone_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_obtain_verifycode:
                obtainVerifycode();
                break;
            case R.id.tv_phone_commit:
                phoneCommit();
                break;
        }
    }

    private void phoneCommit() {
        String phonenum = etPhonenum.getText().toString().trim();
        String verifyCode = etVerifycode.getText().toString().trim();

        if(TextUtil.isEmpty(phonenum)){
            AppContext.showToast(R.string.phone_empty);
            return;
        }

        if(TextUtil.isEmpty(verifyCode)){
            AppContext.showToast(R.string.verifycode_empty);
            return;
        }



    }

    private void obtainVerifycode() {
        String phonenum = etPhonenum.getText().toString().trim();
        if(TextUtil.isEmpty(phonenum)){
            AppContext.showToast(R.string.phone_empty);
            return;
        }

        //网络活动   发送请求验证码


    }


}
