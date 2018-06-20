package com.wenjing.yinfutong.function.home.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.function.home.adapter.MyFragmentPagerAdapter;
import com.wenjing.yinfutong.utils.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wenjing on 2018/3/21.
 */

public class HistoryBillFragment extends BaseFragment implements ViewPager.OnTouchListener{
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragemt_hisory_bill;
    }

    @Override
    protected void initView(View view) {
        //构造适配器
        List<Fragment> fragments = new ArrayList<>();

        DailyBillsFragment dailyBillsFragment = new DailyBillsFragment();
        MonthlyBillsFragment monthlyBillsFragment = new MonthlyBillsFragment();

        fragments.add(dailyBillsFragment);
        fragments.add(monthlyBillsFragment);

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getCurActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        viewPager.setOnTouchListener(this);
        tabs.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //返回true  禁止滑动
        return true;
    }

}
