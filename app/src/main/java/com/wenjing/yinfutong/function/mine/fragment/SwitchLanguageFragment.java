package com.wenjing.yinfutong.function.mine.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.AppManager;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.activity.MainActivity;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.LanguageBean;
import com.wenjing.yinfutong.model.body.PersonalModifyRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.SharedPreferenceUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ${luoyingtao} on 2018\3\19 0019.
 */

public class SwitchLanguageFragment extends BaseFragment {

    @BindView(R.id.recycler_language)
    RecyclerView recyclerLanguage;

    private CommonAdapter<LanguageBean> adapter;
    private List<LanguageBean> languageList = new ArrayList<>();
    ;

    private int cur_position;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_switchlanguage;
    }

    @Override
    protected void initView(View view) {
        addRightTitleButton();
        initRecycler();
        initAdapter();
        loadData();
    }

    private void addRightTitleButton() {
        BaseActivity activity = (BaseActivity) getCurActivity();
        QMUITopBarLayout qmuiTopBarLayout = activity.getmQmuiTopBarlayout();
        Button button = qmuiTopBarLayout.addRightTextButton(R.string.confirm, R.id.right_titlebtn);
        button.setTextColor(ContextCompat.getColor(getContext() , R.color.right_titlebtn_color));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppContext.instance().isLogin()) {
                    //更新  后台个人信息
                    modifyLangInfo(cur_position);
                }else{
                    switchAppLang();
                }
            }
        });
    }

    private void loadData() {
        //添加中文
        LanguageBean zh_bean = new LanguageBean(RequestConstant.REGISTER_LANGUAGE_CHINESE, getResources().getString(R.string.Chinese));
        LanguageBean en_bean = new LanguageBean(RequestConstant.REGISTER_LANGUAGE_ENGLISH, getResources().getString(R.string.English));
        LanguageBean km_bean = new LanguageBean(RequestConstant.REGISTER_LANGUAGE_CAMBODIA, getResources().getString(R.string.Cambodia));

        languageList.add(zh_bean);
        languageList.add(en_bean);
        languageList.add(km_bean);

        //缓存  记录
        cur_position = AppContext.getLangulage() - 1;

        LanguageBean bean = languageList.get(cur_position);
        bean.setActive(!bean.isActive());
        languageList.set(cur_position, bean);


        adapter.notifyDataSetChanged();
    }

    private void initAdapter() {
        adapter = new CommonAdapter<LanguageBean>(getContext(),
                R.layout.item_select,
                languageList) {
            @Override
            protected void convert(ViewHolder holder, LanguageBean languageBean, int position) {
                holder.setText(R.id.item_tv_name, languageBean.getLangName());

                holder.setTextColorRes(R.id.item_tv_name, languageBean.isActive() ? R.color.item_selected_txtcolor : R.color.item_normal_txtcolor);
                holder.setVisible(R.id.item_ic_agree, languageBean.isActive());

            }
        };

        recyclerLanguage.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //  第一步   列表跟新
                updateData(position);
            }

            private void updateData(int position) {
                if (cur_position != position) {
                    //1、更新过去一项
                    updateItem(cur_position);
                    //更新当前项
                    updateItem(position);
                    //更新记录 position
                    cur_position = position;
                }
            }

            private void updateItem(int position) {
                LanguageBean bean = languageList.get(position);
                bean.setActive(!bean.isActive());
                languageList.set(position, bean);
                adapter.notifyItemChanged(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void modifyLangInfo(int position) {
        PersonalModifyRequestBody body = new PersonalModifyRequestBody();
        Customer customer = AppContext.instance().getLoginUser();

        body.setAccountId(customer.getAccountId());
        body.setCellphone(customer.getCellphone());
        body.setRegisterLang(languageList.get(position).getLangFlag());

        showWaitDialog();
        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.modifyPersonInfo(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        hideWaitDialog();
                        if (data.getCode() == 0) {
                            //切换语言
                            switchAppLang();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                    }
                });


    }

    private void switchAppLang() {
        BaseActivity activity = (BaseActivity) getCurActivity();

        //获取语言   简称
        String abbr = SharedPreferenceUtils.getLangStrMap.get(languageList.get(cur_position).getLangFlag());
        activity.selectLanguage(abbr);

        Intent intent = new Intent();
        intent.setClass(getContext(), MainActivity.class);
        startActivity(intent);

        AppManager.getAppManager().finishAllActivity();

    }

    private void initRecycler() {
        LinearLayoutManager LManager = new LinearLayoutManager(getContext());
        recyclerLanguage.setLayoutManager(LManager);
        recyclerLanguage.addItemDecoration(new DividerItemDecoration(getContext(), OrientationHelper.VERTICAL));

    }

}
