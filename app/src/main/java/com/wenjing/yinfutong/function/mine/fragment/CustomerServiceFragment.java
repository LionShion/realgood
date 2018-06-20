package com.wenjing.yinfutong.function.mine.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joker.api.Permissions4M;
import com.joker.api.wrapper.ListenerWrapper;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.CommonPbBean;
import com.wenjing.yinfutong.model.body.LeastRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.view.LoadRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ${luoyingtao} on 2018\4\8 0008.
 */

public class CustomerServiceFragment extends BaseFragment implements ListenerWrapper.PermissionRequestListener, ListenerWrapper.PermissionPageListener {

    @BindView(R.id.common_pb_recycler)
    LoadRecyclerView commonPbRecycler;
    @BindView(R.id.tv_hotline)
    TextView tvHotline;

    private CommonAdapter<CommonPbBean> adapter;
    private List<CommonPbBean> commonPbDataList = new ArrayList<>();


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_customerservice;
    }

    @Override
    protected void initView(View view) {
        initRecycler();
        initAdapter();
        loadData();
    }

    private void loadData() {
        LeastRequestBody body = new LeastRequestBody();
        body.setRegisterLang(AppContext.getLangulage());
        new MyObservable<BaseResponse<List<CommonPbBean>>>().observe(getContext(),
                sMineApi.getCommonPbList(body),
                new OnResponseListener<BaseResponse<List<CommonPbBean>>>() {
                    @Override
                    public void onNext(BaseResponse<List<CommonPbBean>> data) {
                        if (data.getCode() == 0 && data.getData() != null && data.getData().size() > 0) {
                            commonPbDataList.addAll(data.getData());
                            adapter.notifyDataSetChanged();
                        } else {
                            commonPbRecycler.showEmpty(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        commonPbRecycler.showEmpty(null);
                    }
                });

    }

    private void initAdapter() {
        adapter = new CommonAdapter<CommonPbBean>(getContext(),
                R.layout.item_commonpb,
                commonPbDataList) {
            @Override
            protected void convert(ViewHolder holder, CommonPbBean commonPbBean, int position) {
                holder.setText(R.id.item_pbname, commonPbBean.getAsk());

            }
        };


        commonPbRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Bundle bundle = new Bundle();
                CommonPbBean commonPbBean = commonPbDataList.get(position);
                bundle.putParcelable(TabCodeConstant.KEY_COMMONPB_BEAN, commonPbBean);
                UIHelper.showCommonPbDetails(getContext(), bundle);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initRecycler() {
        commonPbRecycler.init(new LinearLayoutManager(getContext()));
    }

    public static final int REQUESTCODE_CALL_PHONE = 0x100;
    @OnClick(R.id.cs_upper)
    public void onViewClicked() {
        Permissions4M.get(getCurActivity())
                .requestForce(true)
                .requestPermission(Manifest.permission.CALL_PHONE)
                .requestCode(REQUESTCODE_CALL_PHONE)
                .requestCallback(this)
                .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                .requestPage(this)
                .request();

    }

    @Override
    public void permissionGranted() {
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:18166969539"));
        startActivity(callIntent);
    }

    @Override
    public void permissionDenied() {

    }

    @Override
    public void permissionRationale() {

    }

    @Override
    public void pageIntent(final Intent intent) {
        new AlertDialog.Builder(getContext())
                .setMessage("拨打电话，我们需要您开启CALL_PHONE权限")
                .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
