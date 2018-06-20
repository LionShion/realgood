package com.wenjing.yinfutong.function.mine.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.interf.OnWaitListener;
import com.wenjing.yinfutong.jpush.LocalBroadcastManager;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.body.CancelQueueRequestBody;
import com.wenjing.yinfutong.utils.DialogUtils;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;

import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\5\2 0002.
 */

public class ToRechargeFragment extends BaseFragment {

    private int distributorId;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragmen_torecharge;

    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        distributorId = bundle.getInt("distributorId");
    }

    @Override
    public void onStart() {
        super.onStart();
        registerOfflineRechargeReceiver();
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(moffOfflineRechargeReceiver);
    }

    @OnClick(R.id.tv_cancelqueue)
    public void onViewClicked() {
        DialogUtils.showCancelWaitDialog(getContext(), new OnWaitListener() {
            @Override
            public void waitAgain() {

            }

            @Override
            public void quit() {
                cancelQueue();
            }
        });
    }

    private void cancelQueue() {
        CancelQueueRequestBody body = new CancelQueueRequestBody();
        body.setAccountId(AppContext.instance().getLoginUser().getAccountId());
        body.setDistributorId(distributorId);

        TLog.log(this , body.toString());
        new MyObservable<BaseResponse>().observe(getContext(),
                sHomeApi.cancelQueue(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if (data.getCode() == 0){
                            getCurActivity().finish();
                        }else {
                            AppContext.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private OfflineRechargeReceiver moffOfflineRechargeReceiver;

    public static final String ACTION_OFFLINERECHARGE_SUCC = "com.wenjing.yinfutong.ACTION_OFFLINERECHARGE_SUCC";
    public static final String ACTION_OFFLINERECHARGE_FAIL = "com.wenjing.yinfutong.ACTION_OFFLINERECHARGE_FAIL";

    public static final String KEY_MESSAGE = "offline_recharge_message";
    public static final String KEY_EXTRAS = "offline_recharge_extra";

    private void registerOfflineRechargeReceiver(){
        moffOfflineRechargeReceiver = new OfflineRechargeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(ACTION_OFFLINERECHARGE_SUCC);
        filter.addAction(ACTION_OFFLINERECHARGE_FAIL);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(moffOfflineRechargeReceiver , filter);
    }

    public class OfflineRechargeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            TLog.e("------" , intent.getExtras().toString());
            if(intent.getAction().equals(ACTION_OFFLINERECHARGE_SUCC)){
                //跳转充值   成功页
                UIHelper.showRechargeSucc(getCurActivity() , intent.getExtras());
                getCurActivity().finish();
            }else if (intent.getAction().equals(ACTION_OFFLINERECHARGE_FAIL)){
                //返回首页
                getCurActivity().finish();
            }
        }
    }

}
