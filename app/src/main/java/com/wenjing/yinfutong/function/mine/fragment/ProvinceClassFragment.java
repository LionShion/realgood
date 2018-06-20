package com.wenjing.yinfutong.function.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.ProvinceClassBean;
import com.wenjing.yinfutong.model.body.LeastRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.view.LoadRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${luoyingtao} on 2018\3\31 0031.
 */

public class ProvinceClassFragment extends BaseFragment {

    @BindView(R.id.recycler_regionclass)
    LoadRecyclerView recyclerRegionclass;

    private CommonAdapter<ProvinceClassBean> adapter;
    private List<ProvinceClassBean> proInfoList = new ArrayList<>();

    //记录下一级 的选择
    private int down_cityposition = 0;
    private String down_cityName;

    //当前选择
    private int cur_proPosition   = 0;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_regionclass;
    }

    @Override
    protected void initView(View view) {
        Bundle bundle = getArguments();
        down_cityposition = bundle.getInt(TabCodeConstant.KEY_CITY_POSITION);
        cur_proPosition   = bundle.getInt(TabCodeConstant.KEY_PRO_POSITION);

        initRecycler();
        loadData();
    }

    private void loadData() {

        LeastRequestBody body = new LeastRequestBody();
        body.setRegisterLang(AppContext.getLangulage());

        showWaitDialog();


        new MyObservable<BaseResponse<List<ProvinceClassBean>>>().observe(getContext(),
                sMineApi.getMerchantProAndCity(body),
                new OnResponseListener<BaseResponse<List<ProvinceClassBean>>>() {
                    @Override
                    public void onNext(BaseResponse<List<ProvinceClassBean>> data) {
                        hideWaitDialog();

                        if(data.getCode() == 0 && data.getData() != null){
                            //改造数据    记录上次点击
                            List<ProvinceClassBean> proList = data.getData();
                            ProvinceClassBean bean = proList.get(cur_proPosition);
                            bean.setActive(!bean.isActive());
                            proList.set(cur_proPosition,bean);

                            proInfoList.addAll(proList);
                            adapter.notifyDataSetChanged();

                        }else {
                            AppContext.showToast(data.getMsg());
                            recyclerRegionclass.showEmpty(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                        AppContext.showToast(e.getMessage());
                        recyclerRegionclass.showError(null);
                    }
                });

    }

    private void initRecycler() {
        recyclerRegionclass.init(new LinearLayoutManager(getContext()));
        recyclerRegionclass.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));

        initAdapter();
    }

    private void initAdapter() {
        adapter = new CommonAdapter<ProvinceClassBean>(getContext(),
                R.layout.item_select,
                proInfoList) {
            @Override
            protected void convert(ViewHolder holder, ProvinceClassBean provinceClassBean, int position) {
                holder.setText(R.id.item_tv_name,provinceClassBean.getName());
                holder.setVisible(R.id.item_ic_agree,provinceClassBean.isActive());

            }
        };

        recyclerRegionclass.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //列表选择   更新
                updateData(position);

                //跳转  到市级  选择
                List<ProvinceClassBean.ChildListBean> childList = proInfoList.get(position).getChildList();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(TabCodeConstant.KEY_CITYCLASSLIST, (ArrayList<? extends Parcelable>) childList);
                bundle.putInt(TabCodeConstant.KEY_CITY_POSITION,down_cityposition);
                UIHelper.showSelectMerchantCity(ProvinceClassFragment.this,bundle);
            }

            private void updateData(int position) {
                if(cur_proPosition != position){
                    //1、更新过去一项
                    updateItem(cur_proPosition);
                    //更新当前项
                    updateItem(position);
                    //更新记录 position
                    cur_proPosition = position;
                }
            }

            private void updateItem(int position) {
                ProvinceClassBean bean = proInfoList.get(position);
                bean.setActive(!bean.isActive());
                proInfoList.set(position,bean);
                adapter.notifyItemChanged(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == TabCodeConstant.REQUESTCODE_MERCHANT_CITYSELECT){
                down_cityposition = data.getIntExtra(TabCodeConstant.KEY_CITY_POSITION,0);
                down_cityName = data.getStringExtra(TabCodeConstant.KEY_CITYNAME);

                //下一级返回过来   直接跳过 当前页
                Intent intent = new Intent();
                intent.putExtra(TabCodeConstant.KEY_PRO_POSITION,cur_proPosition);
                intent.putExtra(TabCodeConstant.KEY_CITY_POSITION,down_cityposition);
                intent.putExtra(TabCodeConstant.KEY_CITYNAME,down_cityName);
                intent.putExtra(TabCodeConstant.KEY_PRONAME,proInfoList.get(cur_proPosition).getName());

                getCurActivity().setResult(Activity.RESULT_OK,intent);
                getCurActivity().finish();
            }
        }
    }
}
