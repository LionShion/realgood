package com.wenjing.yinfutong.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.wenjing.yinfutong.AppManager;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.interf.VisiableControl;
import com.wenjing.yinfutong.utils.AndroidWorkaround;
import com.wenjing.yinfutong.utils.SharedPreferenceUtils;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.widget.DialogControl;
import com.wenjing.yinfutong.widget.dialog.DialogHelper;
import com.wenjing.yinfutong.widget.dialog.WaitDialog;

import java.lang.reflect.Field;
import java.util.Locale;


public abstract class BaseActivity extends AppCompatActivity implements DialogControl, VisiableControl {
    public static final String TAG = BaseActivity.class.getCanonicalName();

    private boolean isVisible;
    private WaitDialog waitDialog;

    private ActionBar mActionBar;
    protected LayoutInflater mInflater;
    private Toolbar mToolBar;
    private TextView mTvToolBarTitle;
    private QMUIStatusBarHelper mStatusBarHelper;
    private QMUITopBarLayout mQmuiTopBarlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //重启之后恢复到之前的语言   且不会为空
        String language = SharedPreferenceUtils.getLanguage(this);
        selectLanguage(language);

        //Android5.0全透明状态栏效果
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        onBeforeSetContentLayout();
        setContentView(getLayoutId());
        mInflater = getLayoutInflater();
        mToolBar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolBar);
        mActionBar = getSupportActionBar();
        if (hasActionBar()) {
            initActionBar(mToolBar, mActionBar);
        }
        init(savedInstanceState);

        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

        //手机虚拟键沉浸式
        AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
        //状态栏颜色
        mStatusBarHelper = new QMUIStatusBarHelper();

    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    protected void initActionBar(Toolbar toolbar, ActionBar actionBar) {
        if (toolbar == null)
            return;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //隐藏默认的标题
        actionBar.setDisplayShowTitleEnabled(false);

        mQmuiTopBarlayout = toolbar.findViewById(R.id.qmuiLayout);
        final QMUIAlphaImageButton imageButton = mQmuiTopBarlayout.addLeftBackImageButton();

        imageButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(imageButton.getWindowToken(), 0);
                    }
                });
        imageButton.setImageResource(R.mipmap.ic_back_titlebar);

        mTvToolBarTitle = toolbar.findViewById(R.id.tv_actionbar_title);
        if (mTvToolBarTitle == null) {
            throw new IllegalArgumentException(
                    "can not find R.id.tv_actionbar_title in customView");
        }

        int titleRes = getActionBarTitle();
        if (titleRes != 0) {
            mTvToolBarTitle.setText(titleRes);
        }

        mStatusBarHelper.setStatusBarLightMode(this);
    }

    private void initTextView() {
        ViewConfiguration configuration = ViewConfiguration.get(this);

        Class claz = configuration.getClass();

        try {

            Field field = claz.getDeclaredField("mFadingMarqueeEnabled");

            field.setAccessible(true);

            field.set(configuration, true);

        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void init(Bundle savedInstanceState) {
    }

    protected int getActionBarTitle() {
        return R.string.app_name;
    }

    protected void onBeforeSetContentLayout() {
        isVisible = true;
        initTextView();
    }

    protected boolean hasActionBar() {
        return true;
    }

    private int layoutId;

    protected int getLayoutId() {
        return layoutId;
    }

    protected void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void setActionBarTitle(int resId) {
        if (resId != 0) {
            setActionBarTitle(getString(resId));
        }
    }

    public void setActionBarTitle(String title) {
        if (hasActionBar()) {
            if (mTvToolBarTitle != null) {
                mTvToolBarTitle.setText(title);
            }
            mActionBar.setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        //        isVisible = false;
        hideWaitDialog();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    public Toolbar getmToolBar() {
        return mToolBar;
    }

    public QMUIStatusBarHelper getStatusBarHelper() {
        return mStatusBarHelper;
    }

    public QMUITopBarLayout getmQmuiTopBarlayout() {
        return mQmuiTopBarlayout;
    }

    public TextView getmTvToolBarTitle() {
        return mTvToolBarTitle;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void hideWaitDialog() {
        if (isVisible && waitDialog != null) {
            try {
                waitDialog.dismiss();
                waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public WaitDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public WaitDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public WaitDialog showWaitDialog(String message) {
        if (isVisible) {
            if (waitDialog == null) {
                waitDialog = DialogHelper.getWaitDialog(this, message);
            }
            if (waitDialog != null) {
                waitDialog.setMessage(message);
                waitDialog.show();
            }
            return waitDialog;
        }
        return null;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            hideWaitDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

      /*
    * toolbar的常规设置
    *
    * */

    public boolean isShowTitle() {
        return mToolBar.getVisibility() == View.VISIBLE;
    }

    public void showTitle(boolean isShow) {
        if (!isShow) {
            mToolBar.setVisibility(View.GONE);
        } else {
            mToolBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setTitleColor(int textColor) {
        TextView textView = (TextView) mToolBar.findViewById(R.id.tv_actionbar_title);
        textView.setTextColor(textColor);
    }

    public void startActivity(Activity activity, Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        startActivity(intent);
        activity.finish();
    }

    public void selectLanguage(String language) {
        TLog.log(TAG ,"language : " + language);

        //设置语言类型
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        switch (language) {
            case "en":
                configuration.locale = Locale.ENGLISH;
                break;
            case "zh":
                configuration.locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case "km":
                configuration.locale = new Locale("km");
                break;
            default:
                configuration.locale = Locale.getDefault();
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);

        //保存设置语言的类型
        SharedPreferenceUtils.putLanguage(this, language);
    }

}
