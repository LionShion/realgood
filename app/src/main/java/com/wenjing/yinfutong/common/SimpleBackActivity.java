package com.wenjing.yinfutong.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;

import java.lang.ref.WeakReference;

public class SimpleBackActivity extends BaseActivity {
    private static final String TAG = SimpleBackActivity.class.getSimpleName();

    public final static String BUNDLE_KEY_PAGE = "BUNDLE_KEY_PAGE";
    public final static String BUNDLE_KEY_ARGS = "BUNDLE_KEY_ARGS";
    public final static String BUNDLE_KEY_CUSTOM_VIEW = "BUNDLE_KEY_CUSTOM_VIEW";

    private WeakReference<Fragment> mFragment;

    private Intent data;

    @Override
    protected void onBeforeSetContentLayout() {
        super.onBeforeSetContentLayout();
        data = getIntent();
        if (data == null) {
            throw new RuntimeException(
                    "you must provide a page info to display");
        }
        initLayoutRes();
    }

    @Override
    protected int getLayoutId() {

        Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);
        int layoutRes = -1;
        if (args != null) {
            layoutRes = args.getInt(BUNDLE_KEY_CUSTOM_VIEW, -1);
        }

        if (layoutRes == -1
                || layoutRes == SimpleBackPage.BASE_TOOLBAR_YES) {
            layoutRes = R.layout.activity_simple_fragment;
        }

        return layoutRes;
    }


    protected void initLayoutRes() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        int pageValue = data.getIntExtra(BUNDLE_KEY_PAGE, 0);
        SimpleBackPage page = SimpleBackPage.getPageByValue(pageValue);
        if (page == null) {
            throw new IllegalArgumentException("can not find page by value:"
                    + pageValue);
        }
        setActionBarTitle(page.getTitle());

        try {
            Fragment fragment = (Fragment) page.getClz().newInstance();

            Bundle args = data.getBundleExtra(BUNDLE_KEY_ARGS);

            if (args != null) {
                fragment.setArguments(args);
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, TAG)
                    .commit();

            mFragment = new WeakReference<>(fragment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(
                    "generate fragment error. by value:" + pageValue);
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null && mFragment.get() != null && mFragment.get() instanceof BaseFragment) {
            BaseFragment bf = (BaseFragment) mFragment.get();
            if (!bf.onBackPressed()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }
}
