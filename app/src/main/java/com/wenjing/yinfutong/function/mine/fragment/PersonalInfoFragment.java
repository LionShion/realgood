package com.wenjing.yinfutong.function.mine.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.Validator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\15 0015.
 */

public class PersonalInfoFragment extends BaseFragment {


    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_tied_phonenum)
    TextView tvTiedPhonenum;
    @BindView(R.id.tv_tied_email)
    TextView tvTiedEmail;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_personalinfo;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        initDatas();
    }

    private void initDatas() {
        Customer customer = AppContext.instance().getLoginUser();
        String nickName = customer.getNickName();
        tvNickname.setText(nickName);

        tvTiedPhonenum.setText(Validator.getStarPhone(customer.getCellphone()));

        String email = customer.getEmail();
        tvTiedEmail.setText(TextUtils.isEmpty(email) ? getResources().getString(R.string.email_no) : email);
    }

    /**
     * 单行点击跳转
     */
    @OnClick({R.id.nickname_line, R.id.tied_phone_line, R.id.tied_email_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nickname_line:
                nicknameModify();
                break;
            case R.id.tied_phone_line:
//                tiePhonenum();
                break;
            case R.id.tied_email_line:
                tieEmail();
                break;
        }
    }

    private void tieEmail() {
        Bundle bundle = getArguments();
        UIHelper.showTiedEmail(getContext(),bundle);
    }

    private void tiePhonenum() {
        Bundle bundle = getArguments();//吧数据传过去
        UIHelper.showTiedPhone(getCurActivity(),bundle);
    }

    private void nicknameModify() {
        Bundle bundle = getArguments();//吧数据传过去
        UIHelper.showNickname(getContext(),bundle);
    }
}
