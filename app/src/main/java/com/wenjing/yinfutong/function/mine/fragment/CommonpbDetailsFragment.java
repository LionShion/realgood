package com.wenjing.yinfutong.function.mine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.model.CommonPbBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${luoyingtao} on 2018\4\8 0008.
 */

public class CommonpbDetailsFragment extends BaseFragment {


    @BindView(R.id.commonpb_title)
    TextView commonpbTitle;
    @BindView(R.id.commonpb_answer)
    TextView commonpbAnswer;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_commonpd_details;
    }

    @Override
    protected void initView(View view) {

        Bundle bundle = getArguments();
        CommonPbBean bean = bundle.getParcelable(TabCodeConstant.KEY_COMMONPB_BEAN);

        commonpbTitle.setText(bean.getAsk());
        commonpbAnswer.setText(bean.getAnswer());


    }

}
