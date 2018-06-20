package com.wenjing.yinfutong.function.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.model.ProvinceClassBean;
import com.wenjing.yinfutong.view.LoadRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ${luoyingtao} on 2018\3\31 0031.
 */

public class CityClassFragment extends BaseFragment {

    @BindView(R.id.recycler_regionclass)
    LoadRecyclerView recyclerRegionclass;

    private CommonAdapter<ProvinceClassBean.ChildListBean> adapter;
    private List<ProvinceClassBean.ChildListBean> cityClassList;

    private int cur_cityPosition = 0;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_regionclass;
    }

    @Override
    protected void initView(View view) {
        initData();
        initRecycler();
    }

    private void initData() {
        Bundle bundle = getArguments();
        cityClassList =bundle .getParcelableArrayList(TabCodeConstant.KEY_CITYCLASSLIST);
        cur_cityPosition = bundle.getInt(TabCodeConstant.KEY_CITY_POSITION);

        if(cityClassList !=  null && cityClassList.size() > 0){
            //改造数据  记录上次点击
            ProvinceClassBean.ChildListBean bean = cityClassList.get(cur_cityPosition);
            bean.setActive(!bean.isActive());
            cityClassList.set(cur_cityPosition,bean);
        }

    }

    private void initRecycler() {
        recyclerRegionclass.init(new LinearLayoutManager(getContext()));
        recyclerRegionclass.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));

        initAdapter();
    }

    private void initAdapter() {
        adapter = new CommonAdapter<ProvinceClassBean.ChildListBean>(getContext(),
                R.layout.item_select,
                cityClassList
                ) {
            @Override
            protected void convert(ViewHolder holder, ProvinceClassBean.ChildListBean childListBean, int position) {
                holder.setText(R.id.item_tv_name,childListBean.getName());
                holder.setVisible(R.id.item_ic_agree,childListBean.isActive());

            }
        };

        recyclerRegionclass.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                updateData(position);

                Intent intent = new Intent();
                intent.putExtra(TabCodeConstant.KEY_CITY_POSITION,position);
                intent.putExtra(TabCodeConstant.KEY_CITYNAME,cityClassList.get(position).getName());
                getCurActivity().setResult(Activity.RESULT_OK,intent);
                getCurActivity().finish();
            }

            private void updateData(int position) {
                if(cur_cityPosition != position){
                    //1、更新过去一项
                    updateItem(cur_cityPosition);
                    //更新当前项
                    updateItem(position);
                    //更新记录 position
                    cur_cityPosition = position;
                }
            }

            private void updateItem(int position) {
                ProvinceClassBean.ChildListBean bean = cityClassList.get(position);
                bean.setActive(!bean.isActive());
                cityClassList.set(position,bean);
                adapter.notifyItemChanged(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

}
