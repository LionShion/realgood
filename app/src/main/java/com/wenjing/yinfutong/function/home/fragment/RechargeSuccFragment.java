package com.wenjing.yinfutong.function.home.fragment;

import android.view.View;
import android.widget.TextView;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wenjing on 2017/11/27.
 */

public class RechargeSuccFragment extends BaseFragment {

    @BindView(R.id.tv_finish)
    TextView tvFinish;
    
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recharge_succ;
    }

    @Override
    protected void initView(View view) {
    }


    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        getCurActivity().finish();


        // 语音合成
//        Intent intent = new Intent(getContext(), TtsDemo.class);
//        startActivity(intent);
    }

}
