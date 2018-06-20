package com.wenjing.yinfutong.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.permissions.PermissionsActivity;
import com.wenjing.yinfutong.permissions.PermissionsChecker;
import com.wenjing.yinfutong.utils.HandlerUtils;


public class SplashActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_image;
    private String subtitle;
    private String imageUrl;

    private static boolean isAdClicked = false;
    private static final int REQUEST_CODE = 0; // 请求码
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);


        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar);
        toolbar.setVisibility(View.GONE);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_image.setOnClickListener(this);

        mPermissionsChecker = new PermissionsChecker(this);
        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            //打开权限设置界面
            startPermissionsActivity();
        } else {
            initData();
        }
    }

    private void initData() {

        loadViewPagerData();
        HandlerUtils.getMainZooerHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //延迟三秒再启动 app
                goNextActivity();
            }

        }, 2 * 1000);


    }


    private void loadViewPagerData() {

    }


    private void goNextActivity() {

        //如果点击了广告那么停止启动应用，没点广告的话就按正常情况启动
        if (isAdClicked) {
            return;
        } else {
            startActivity(SplashActivity.this, MainActivity.class);
        }

        finish();

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_image) {
            if (imageUrl != null) {//跳转H5
                //点击广告图片设置标记并跳转页面
                isAdClicked = true;
                UIHelper.showSingleWebView(this, imageUrl, subtitle);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //从广告页回退时再延迟一点执行启动程序，并改变标记状态
        if (isAdClicked) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goNextActivity();
                }
            }, 800);
        }

        isAdClicked = false;
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {
            initData();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

}
