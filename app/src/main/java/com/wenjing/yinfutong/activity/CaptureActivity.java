package com.wenjing.yinfutong.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.android.BeepManager;
import com.wenjing.yinfutong.android.CaptureActivityHandler;
import com.wenjing.yinfutong.android.FinishListener;
import com.wenjing.yinfutong.android.InactivityTimer;
import com.wenjing.yinfutong.android.IntentSource;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.camera.CameraManager;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.QRCodeResultBean;
import com.wenjing.yinfutong.model.QRCodeResultRequestBean;
import com.wenjing.yinfutong.model.UsQRBean;
import com.wenjing.yinfutong.model.body.QRCodeRequestBody;
import com.wenjing.yinfutong.model.body.UsQRRequestBody;
import com.wenjing.yinfutong.retrofit.YinFuTongFactory;
import com.wenjing.yinfutong.retrofit.api.HomeApi;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.view.ViewfinderView;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();
    private ViewfinderView viewfinderView;
    private ImageView ivFlashlight;

    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private IntentSource source;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    // 电量控制
    private InactivityTimer inactivityTimer;
    // 声音、震动控制
    private BeepManager beepManager;


    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.capture;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 去掉锁屏界面
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        // 保持屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // 强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);


        getStatusBarHelper().setStatusBarLightMode(this);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        ivFlashlight = (ImageView) findViewById(R.id.iv_flashlight);


        ivFlashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraFlashControl();
            }
        });

        Toolbar toolbar = getmToolBar();
        TextView textView = toolbar.findViewById(R.id.tv_actionbar_title);
        textView.setText(getResources().getString(R.string.richScan));

        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        toolbar.measure(w, h);
        int height = toolbar.getMeasuredHeight();
        int width = toolbar.getMeasuredWidth();

        TLog.e("CaptureActivity", width + "--" + height);
        viewfinderView.setToolBarWidth(height);


    }

    @Override
    protected void onResume() {
        super.onResume();
        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getApplication());

        viewfinderView.setCameraManager(cameraManager);

        handler = null;

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * 扫描成功，处理反馈信息
     *
     * @param rawResult
     * @param barcode
     * @param scaleFactor
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();

        boolean fromLiveScan = barcode != null;

        if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();

            String result = rawResult.getText();
            //判断扫描出来 的 字符串是不是特定的json格式
            TLog.log(TAG, "result : " + result);
            //https://qr.alipay.com/bax001832r7m3fjh8vji4098
            //weixin://wxpay/bizpayurl?pr=iiHvFHN
            if (result.startsWith("https://qr.alipay.com/") || result.startsWith("weixin://wxpay/")) {
                startpayActivity(result);
                return;
            }
            if (result.contains("id") && result.contains("roleType") && isGoodJson(result)) {
                TLog.log(TAG, "result : " + result + "是特定json格式对象");

                Gson gson = new Gson();
                QRCodeResultRequestBean bean = gson.fromJson(result, QRCodeResultRequestBean.class);
                int id = bean.getId();
                int roleType = bean.getRoleType();
                getStaticUsQrcodeOrder(id, roleType);
                return;
            }
            TLog.log(TAG, "result : " + result + "是订单号");
            getUsQrcodeOrder(result);

        }
    }

    private void startpayActivity(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private boolean isGoodJson(String result) {
        JsonElement parse = new JsonParser().parse(result);
        return parse.isJsonObject();
    }

    /**
     * 系统支付   动态 二维码  订单
     *
     * @param orderId
     */
    private void getUsQrcodeOrder(final String orderId) {
        UsQRRequestBody body = new UsQRRequestBody();
        body.setOrderNo(orderId);
        body.setRegisterLang(AppContext.getLangulage());

        HomeApi sHomeApi = YinFuTongFactory.getHomeApiSingleton();

        new MyObservable<BaseResponse<UsQRBean>>().observe(this,
                sHomeApi.getUsQROrder(body),
                new OnResponseListener<BaseResponse<UsQRBean>>() {
                    @Override
                    public void onNext(BaseResponse<UsQRBean> data) {
                        if (data.getCode() == 0 && data.getData() != null) {
                            UsQRBean usQRBean = data.getData();
                            if (usQRBean.getId() == AppContext.instance().getLoginUser().getAccountId()) {//自己生成 的二维码
                                UIHelper.showMerchantService(CaptureActivity.this);
                                finish();
                                return;
                            }

                            Bundle bundle = new Bundle();
                            bundle.putParcelable("DataBean", data.getData());
                            bundle.putString("orderNo", orderId);
                            UIHelper.showPayment(CaptureActivity.this, bundle);
                            finish();
                        } else {
                            AppContext.showToast(data.getMsg());

                            //失败重新扫码
                            handler = new CaptureActivityHandler(CaptureActivity.this, decodeFormats,
                                    decodeHints, characterSet, cameraManager);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.log(TAG, e.getMessage());
                    }
                });
    }

    /**
     * 系统支付   静态 二维码  订单(扫  分销商二维码)
     *
     * @param id
     * @param roleType
     */
    private void getStaticUsQrcodeOrder(int id, final int roleType) {
        QRCodeRequestBody body = new QRCodeRequestBody(id, roleType, AppContext.getLangulage());
        HomeApi sHomeApi = YinFuTongFactory.getHomeApiSingleton();

        TLog.log("getStaticUsQrcodeOrder", body.toString());
        sHomeApi.getQRCodeResult(body)
                .compose(RxResultHelper.<QRCodeResultBean>handleRespose())
                .compose(RxSchedulers.<QRCodeResultBean>applyObservableAsync())
                .subscribe(new Observer<QRCodeResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(QRCodeResultBean bean) {
                        TLog.log("getStaticUsQrcodeOrder", bean.toString());

                        String status = bean.getStatus();
                        if (status != null && status.equals(RequestConstant.STATUS_OFFLINE_RECHARGE_QUEUE)) {//等待页面
                            Bundle bundle = new Bundle();
                            bundle.putInt("distributorId", bean.getDistributorId());
                            UIHelper.showToRecharge(CaptureActivity.this, bundle);
                            finish();
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("DataBean", bean);
                            UIHelper.showRecharge(CaptureActivity.this, bundle);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.toString());

                        //失败重新扫码
                        handler = new CaptureActivityHandler(CaptureActivity.this, decodeFormats,
                                decodeHints, characterSet, cameraManager);

                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }

    /**
     * 初始化Camera
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    /**
     * 显示底层错误信息并退出应用
     */
    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    //闪光灯控制
    private void cameraFlashControl() {
        if (cameraManager.flashControlHandler()) {
            ivFlashlight.setImageResource(R.mipmap.rich_scan_on);

        } else {
            ivFlashlight.setImageResource(R.mipmap.rich_scan_down);

        }
    }
}
