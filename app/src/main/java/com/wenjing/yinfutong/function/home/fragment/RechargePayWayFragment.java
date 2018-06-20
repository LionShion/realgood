package com.wenjing.yinfutong.function.home.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.function.home.adapter.ChooseBankListAdapter;
import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.retrofit.YinFuTongFactory;
import com.wenjing.yinfutong.retrofit.api.HomeApi;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.TLog;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by wenjing on 2017/10/17.
 */

public class RechargePayWayFragment extends DialogFragment implements View.OnClickListener {

    private ImageView imageCloseOne;

    private List<BankCardBean> infoList = new ArrayList<>();
    private ChooseBankListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private HomeApi sHomeApi = YinFuTongFactory.getHomeApiSingleton();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_recharge_online_pay_way);
        dialog.setCanceledOnTouchOutside(true);
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimLeftRight);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() / 2;
        window.setAttributes(lp);

        initView(dialog);
        initRecyclerView();
        getData();

        return dialog;
    }

    private void getData() {
        int accountId = AppContext.instance().getLoginUser().getAccountId();
        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage() , accountId);

        TLog.log(this , body.toString());


        /**
         * 发现
         * 没有数据  的时候
         * 走 onSubscribe   和  onError
         *
         *04-27 17:12:42.459 20176-20176/silverpay.com I/RechargePayWayFragment: onSubscribe : 0
         04-27 17:12:42.504 20176-20176/silverpay.com I/RechargePayWayFragment: onError : java.lang.Throwable: 登录已失效，请先登录！

         */
        sHomeApi.getBankCardList(body)
                .compose(RxResultHelper.<List<BankCardBean>>handleRespose())
                .compose(RxSchedulers.<List<BankCardBean>>applyObservableAsync())
                .subscribe(new Observer<List<BankCardBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        TLog.log(RechargePayWayFragment.this , "onSubscribe : " +  d.toString());
                    }

                    @Override
                    public void onNext(List<BankCardBean> beans) {
                        if (beans.size() > 0) {
                            for (BankCardBean bean : beans) {
                                infoList.add(bean);
                                TLog.log(RechargePayWayFragment.this , bean.toString());
                            }
                            mAdapter.notifyDataSetChanged();
                        }

                        TLog.log(RechargePayWayFragment.this ,"onNext : " + beans.toArray().toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.log(RechargePayWayFragment.this ,"onError : " +  e.toString());
                    }

                    @Override
                    public void onComplete() {
                        TLog.log(RechargePayWayFragment.this , "onComplete");
                    }
                });

    }

    private void initView(Dialog dialog) {
        imageCloseOne = dialog.findViewById(R.id.close);
        mRecyclerView = dialog.findViewById(R.id.rv_recyclerView);


        imageCloseOne.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                getDialog().dismiss();
                break;
        }
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ChooseBankListAdapter(getContext(), infoList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BankCardBean dataBean = infoList.get(position);

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("dataBean", dataBean);
                intent.putExtras(bundle);
                getTargetFragment().onActivityResult(RequestCode.CHOOSE_PAY_TYPE,
                        Activity.RESULT_OK,
                        intent);

                getDialog().dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

}
