package com.wenjing.yinfutong.function.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.QRContentBean;
import com.wenjing.yinfutong.model.body.QRContentRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.ZXingUtils;
import com.wenjing.yinfutong.view.QRCodeDecoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by wenjing on 2017/6/22.
 */

public class TwoDimensionalCodeFragment extends BaseFragment {

    @BindView(R.id.iv_two_code)
    SimpleDraweeView ivTwoCode;
    @BindView(R.id.tv_save_image)
    TextView tvSaveImage;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.rl_collection_record)
    RelativeLayout rlCollectionRecord;

    private String money;
    private int payChannel;
    private String content;

    //    private Bitmap  qrBitmap;
    private Bitmap logoBitmap;
    private Bitmap logoQRBitmap;

    private static final int SAVE_SUCCESS = 0;//保存图片成功
    private static final int SAVE_FAILURE = 1;//保存图片失败

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SAVE_SUCCESS:
                    AppContext.showToast("图片保存成功");
                    break;
                case SAVE_FAILURE:
                    AppContext.showToast("图片保存失败,请稍后再试...");
                    break;
            }
        }
    };

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_two_dimensional_code;
    }

    @Override
    protected void initView(View view) {

        Bundle bundle = getArguments();
        money = bundle.getString("money");
        payChannel = bundle.getInt(TabCodeConstant.KEY_PAYCHANNEL);

        tvMoney.setText(money);


        setToolbarLayout();

        //二维码  改成自己动态生成  二维码
        //        initData();
        //        getImageSize(url);
        getQRCodeContent();

        ivTwoCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //                Bitmap bitmap = returnBitMap(content);
                decode(logoQRBitmap, "解析二维码失败");

                return false;
            }
        });
    }


     /*   private void initData() {
            int accountId = AppContext.instance().getLoginUser().getAccountId();
            BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(), accountId);
            showDialog();
            sHomeApi.getQRcode(body)
                    .compose(RxSchedulers.<QRCodeBean>applyObservableAsync())
                    .subscribe(new Observer<QRCodeBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(QRCodeBean bean) {
                            getmTipDialog().dismiss();
                            if (bean.getData() != null && bean.getCode() == 0) {
                                data = bean.getData();
                                ivTwoCode.setImageURI(data);
                                return;
                            }
                            AppContext.showToast(bean.getMsg());

                        }

                        @Override
                        public void onError(Throwable e) {
                            getmTipDialog().dismiss();
                            AppContext.showToast(e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }

                    });
        }*/

    private void setToolbarLayout() {
        BaseActivity activity = (BaseActivity) getCurActivity();
        QMUITopBarLayout layout = activity.getmQmuiTopBarlayout();
        layout.setBackgroundColor(getResources().getColor(R.color.green_00bc9c));
        layout.removeAllLeftViews();
        layout.addLeftImageButton(R.mipmap.icon_back, -1)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCurActivity().finish();
                    }
                });
        activity.setTitleColor(getResources().getColor(R.color.white));
        activity.getStatusBarHelper().setStatusBarDarkMode(getCurActivity());

    }


    private void getImageSize(final String url) {
        // 向 ViewTreeObserver 注册方法，以获取控件尺寸
        final ViewTreeObserver vto = ivTwoCode.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                logoBitmap = ZXingUtils.createQRCodeWithLogo(url, bitmap);
                ivTwoCode.setImageBitmap(logoBitmap);

                // 成功调用一次后，移除 Hook 方法，防止被反复调用
                ivTwoCode.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    @OnClick({R.id.rl_collection_record, R.id.tv_save_image})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.tv_save_image:

                //保存图片必须在子线程中操作，是耗时操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                       /* if (content == null) {
                            return;
                        } else {
                            //保存图片
                            //                            Bitmap bp = returnBitMap(data);
                            //                            saveImageToPhotos(AppContext.context(), bp);
                        }*/

                    }
                }).start();


                break;
            case R.id.rl_collection_record:
                UIHelper.showCollectionRecord(getContext());
                break;
        }
    }

    /**
     * 保存二维码到本地相册
     */
    private void saveImageToPhotos(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mHandler.obtainMessage(SAVE_FAILURE).sendToTarget();
            return;
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
        mHandler.obtainMessage(SAVE_SUCCESS).sendToTarget();
    }

    /**
     * 将URL转化成bitmap形式
     *
     * @param url
     * @return bitmap type
     */
    public final static Bitmap returnBitMap(String url) {
        URL myFileUrl;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    private void decode(final Bitmap bitmap, final String errorTip) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return QRCodeDecoder.syncDecodeQRCode(bitmap);
            }

            @Override
            protected void onPostExecute(String result) {
                if (TextUtils.isEmpty(result)) {
                    AppContext.showToast(errorTip);
                } else {
                    AppContext.showToast("二维码识别成功：" + result);
                }
            }
        }.execute();
    }

    private void getQRCodeContent() {
        QRContentRequestBody body = new QRContentRequestBody();

        body.setAccountId(AppContext.instance().getLoginUser().getAccountId());
        body.setPayChannel(payChannel);
        body.setReceiveAmount(Double.parseDouble(money));
        body.setRegisterLang(AppContext.getLangulage());

        showWaitDialog();
        new MyObservable<BaseResponse<QRContentBean>>().observe(getContext(),
                sHomeApi.getQRContent(body),
                new OnResponseListener<BaseResponse<QRContentBean>>() {
                    @Override
                    public void onNext(BaseResponse<QRContentBean> data) {
                        hideWaitDialog();
                        if (data.getCode() == 0 && data.getData() != null) {
                            QRContentBean qrContentBean = data.getData();
                            content = qrContentBean.getResult();
                            //                            qrBitmap = ZXingUtils.createQRImage(content, (int) TDevice.dpToPixel(250), (int) TDevice.dpToPixel(250));

                            //默认
                            int logoResId = R.mipmap.logo_ourpay;
                            int payChannel = qrContentBean.getPayChannel();
                            switch (payChannel) {
                                case RequestConstant.PAYCHANNEL_TYPE_SYSTEM:
                                    logoResId = R.mipmap.logo_ourpay;
                                    break;
                                case RequestConstant.PAYCHANNEL_TYPE_WEPAY:
                                    logoResId = R.mipmap.logo_wepay;
                                    break;
                                case RequestConstant.PAYCHANNEL_TYPE_ALIPAY:
                                    logoResId = R.mipmap.logo_alipay;
                                    break;
                            }

                            logoBitmap = BitmapFactory.decodeResource(getResources(), logoResId);

                            if (content != null) {
                                logoQRBitmap = ZXingUtils.createQRCodeWithLogo(content, logoBitmap);
                                ivTwoCode.setImageBitmap(logoQRBitmap);
                            } else {
                                AppContext.showToast("金额过大");
                            }

                        } else {
                            AppContext.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                        AppContext.showToast(e.getMessage());
                    }
                });

    }
}
