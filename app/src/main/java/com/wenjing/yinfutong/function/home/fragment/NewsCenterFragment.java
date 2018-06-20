package com.wenjing.yinfutong.function.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.InstantMsgBean;
import com.wenjing.yinfutong.model.body.OnlyAccountIdRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.view.DotImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wenjing on 2017/10/24.
 */

public class NewsCenterFragment extends BaseFragment {

    @BindView(R.id.rl_platform_announcement)
    RelativeLayout rlPlatformAnnouncement;
    @BindView(R.id.rl_trade_information)
    RelativeLayout rlTradeInformation;
    @BindView(R.id.iv_system_notice)
    DotImageView dotNotice;
    @BindView(R.id.iv_my_message)
    DotImageView dotMessage;


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_news_center;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshInstantMsg();
    }

    private void refreshInstantMsg() {
        OnlyAccountIdRequestBody body = new OnlyAccountIdRequestBody();
        body.setAccountId(AppContext.instance().getLoginUser().getAccountId());

        new MyObservable<BaseResponse<InstantMsgBean>>().observe(getContext(),
                sHomeApi.getInstantMsg(body),
                new OnResponseListener<BaseResponse<InstantMsgBean>>() {
                    @Override
                    public void onNext(BaseResponse<InstantMsgBean> data) {
                        if (data.getCode() == 0 && data.getData() != null) {
                            InstantMsgBean bean = data.getData();
                            int msgCount = bean.getMsgCount();
                            int noticeCount = bean.getNoticeCount();

                            dotMessage.showDot(msgCount > 0);
                            dotNotice.showDot(noticeCount > 0);

                            TLog.log(this, "msgCount : " + msgCount + " , noticeCount : " + noticeCount);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    @OnClick({R.id.rl_platform_announcement, R.id.rl_trade_information})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_platform_announcement:
                UIHelper.showPlatformAnnouncement(getContext());

                break;
            case R.id.rl_trade_information:
                UIHelper.showTradeInformation(getContext());
                break;
        }
    }

}
