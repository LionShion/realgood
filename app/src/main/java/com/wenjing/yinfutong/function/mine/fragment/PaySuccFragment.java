package com.wenjing.yinfutong.function.mine.fragment;

import android.view.View;
import android.widget.TextView;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by luoyingtao on 2018\3\22 0022.
 */

public class PaySuccFragment extends BaseFragment {

    @BindView(R.id.tv_finish)
    TextView tvFinish;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_pay_succ;
    }

    @Override
    protected void initView(View view) {

    }



    @OnClick({ R.id.tv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_finish:
                getCurActivity().finish();
                break;
        }
    }
}
