package com.wenjing.yinfutong.function.home.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by wenjing on 2017/10/17.
 */

public class PayWayFragment extends DialogFragment implements View.OnClickListener {

    private ImageView imageCloseOne;
    private TextView tvAccountBalance, tvRecharge;

    private List<BankCardBean> infoList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RelativeLayout rlBackground;
    private ChooseBankListAdapter mAdapter;
    private HomeApi sHomeApi = YinFuTongFactory.getHomeApiSingleton();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_pay_way);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimLeftRight);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() / 2;
        window.setAttributes(lp);

        initView(dialog);
        getData();

        return dialog;
    }

    private void getData() {
        int id = AppContext.instance().getLoginUser().getAccountId();
        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(), id);
        sHomeApi.getBankCardList(body)
                .compose(RxResultHelper.<List<BankCardBean>>handleRespose())
                .compose(RxSchedulers.<List<BankCardBean>>applyObservableAsync())
                .subscribe(new Observer<List<BankCardBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(List<BankCardBean> value) {
                        if (value.size() > 0) {
                            for (BankCardBean bankCardBean : value) {
                                infoList.add(bankCardBean);
                            }
                            initRecyclerView();
                            return;
                        }
                        AppContext.showToast(getResources().getString(R.string.no_bind_card));

                    }


                    @Override
                    public void onError(@NonNull Throwable e) {
                        AppContext.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(Dialog dialog) {
        imageCloseOne = dialog.findViewById(R.id.close);
        tvAccountBalance = dialog.findViewById(R.id.tv_account_balance);
        tvRecharge = dialog.findViewById(R.id.tv_to_recharge);
        mRecyclerView = dialog.findViewById(R.id.rv_recyclerView);
        rlBackground = dialog.findViewById(R.id.rl_background);

        imageCloseOne.setOnClickListener(this);
        tvAccountBalance.setOnClickListener(this);
        tvRecharge.setOnClickListener(this);
        rlBackground.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                getDialog().dismiss();
                break;
            case R.id.tv_account_balance:
            case R.id.rl_background:

                Intent resultIntent = new Intent();
                resultIntent.putExtra("payWay", getResources().getString(R.string.balance));
                getTargetFragment().onActivityResult(RequestCode.CHOOSE_PAY_TYPE,
                        Activity.RESULT_OK,
                        resultIntent);

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

                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("dataBean", dataBean);
                resultIntent.putExtra("payWay", dataBean.getBankName());
                resultIntent.putExtras(bundle);
                getTargetFragment().onActivityResult(RequestCode.CHOOSE_PAY_TYPE,
                        Activity.RESULT_OK,
                        resultIntent);
                getDialog().dismiss();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

}
