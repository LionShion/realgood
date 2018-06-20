package com.wenjing.yinfutong.function.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.RegionBean;
import com.wenjing.yinfutong.model.body.LeastRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.view.LoadRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${luoyingtao} on 2018\3\19 0019.
 */

public class RegionFragment extends BaseFragment {

    private CommonAdapter<RegionBean> adapter;
    private List<RegionBean> regionBeanList = new ArrayList<>();

    private int cur_position;

    @BindView(R.id.recycler_region)
    LoadRecyclerView recyclerRegion;

    private int lastSelectedPos = 0;//默认第一条


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_region;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        int tab_code = bundle .getInt(TabCodeConstant.KEY_TABCODE);
        cur_position = bundle.getInt(TabCodeConstant.KEY_REGION_POSITION);

        initRecycler();
        loadData();
    }

    private void loadData() {
        LeastRequestBody body = new LeastRequestBody();
        body.setRegisterLang(AppContext.getLangulage());

        TLog.log("RegionRequestBody","registerLang : " + body.getRegisterLang());
        new MyObservable<BaseResponse<List<RegionBean>>>().observe(getContext(),
                sMineApi.getResion(body),
                new OnResponseListener<BaseResponse<List<RegionBean>>>() {
                    @Override
                    public void onNext(BaseResponse<List<RegionBean>> data) {
                        if(data.getCode() == 0 && data.getData() != null && data.getData().size() > 0){
                            //改造数据   记录上一条
                            List<RegionBean> regionBeans = data.getData();
                            RegionBean bean = regionBeans.get(cur_position);
                            bean.setActive(!bean.isActive());
                            regionBeans.set(cur_position,bean);

                            regionBeanList.addAll(regionBeans);
                            adapter.notifyDataSetChanged();
                        }else {
                            recyclerRegion.showEmpty(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.getMessage());
                        recyclerRegion.showError(null);
                    }
                });


    }

    private void initAdapter() {
        adapter = new CommonAdapter<RegionBean>(getContext(),
                R.layout.item_select,
                regionBeanList) {
            @Override
            protected void convert(ViewHolder holder, RegionBean regionBean, int position) {
                holder.setText(R.id.item_tv_name,regionBean.getSupportArea() + "(+" + regionBean.getSupportPre() + ")");
                holder.setTextColorRes(R.id.item_tv_name,regionBean.isActive() ? R.color.item_selected_txtcolor : R.color.item_normal_txtcolor);
                holder.setVisible(R.id.item_ic_agree,regionBean.isActive());
            }
        };

        recyclerRegion.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                cur_position = position;

                Intent intent = new Intent();
                intent.putExtra(TabCodeConstant.KEY_REGION_BEAN,regionBeanList.get(position));
                intent.putExtra(TabCodeConstant.KEY_REGION_POSITION,position);
                getCurActivity().setResult(Activity.RESULT_OK,intent);
                getCurActivity().finish();

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerRegion.init(layoutManager);
        recyclerRegion.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));
        recyclerRegion.showData();

        initAdapter();
    }

}
