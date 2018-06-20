package com.wenjing.yinfutong.function.home.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.PhotoHelper;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.MerchantBean;
import com.wenjing.yinfutong.model.body.MerchantRegisterRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.TextUtil;
import com.wenjing.yinfutong.utils.Validator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by wenjing on 2018/3/21.
 */

public class OpenMerchantFragment extends BaseFragment {
    public static final String TAG = "OpenMerchantFragment";

    @BindView(R.id.et_proposer_name)
    MaterialEditText etProposerName;
    @BindView(R.id.et_contactway)
    MaterialEditText etPhone;
    @BindView(R.id.et_shopname)
    MaterialEditText etShopname;
    @BindView(R.id.tv_shop_proandcity)
    TextView tvShopProandcity;
    @BindView(R.id.et_detailaddress)
    MaterialEditText etDetailaddress;
    @BindView(R.id.tv_shop_photo)
    TextView tvShopPhoto;
    @BindView(R.id.tv_commit_application)
    TextView tvCommitApplication;

    // 每个EditText  是否为空的状态   默认为空   false
    private Map<Integer, Boolean> etFlagMap = new HashMap<Integer, Boolean>() {
        {
            put(R.id.et_proposer_name, false);
            put(R.id.et_contactway, false);
            put(R.id.et_shopname, false);
            put(R.id.et_detailaddress, false);
        }
    };

    private int down_cityposition = 0 ;
    private int down_proposition = 0 ;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_openmerchant;
    }

    @Override
    protected void initView(View view) {
        tvCommitApplication.setClickable(false);
        addETTxtChangeListen(view, R.id.et_proposer_name);
        addETTxtChangeListen(view, R.id.et_contactway);
        addETTxtChangeListen(view, R.id.et_shopname);
        addETTxtChangeListen(view, R.id.et_detailaddress);
        tvShopPhoto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                setTvCommitActive();
            }
        });

    }

    private void addETTxtChangeListen(View view, @IdRes final int resId) {
        EditText editText = view.findViewById(resId);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                etFlagMap.put(resId, !TextUtil.isEmpty(editable.toString()));
                setTvCommitActive();
            }
        });

    }

    private void setTvCommitActive() {
        if (etFlagMap.get(R.id.et_proposer_name) && etFlagMap.get(R.id.et_contactway)
                && etFlagMap.get(R.id.et_shopname) && etFlagMap.get(R.id.et_detailaddress)
                && !TextUtil.isTVEmpty(tvShopProandcity)
                && !TextUtil.isTVEmpty(tvShopPhoto)) {
            tvCommitApplication.setClickable(true);
            tvCommitApplication.setBackgroundResource(R.drawable.green_btn_pressed);
        } else {
            tvCommitApplication.setClickable(false);
            tvCommitApplication.setBackgroundResource(R.drawable.gray_btn_normal);
        }

    }


    @OnClick({R.id.shop_proandcity_line, R.id.shop_shopphoto_line, R.id.tv_commit_application})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shop_proandcity_line:
                Bundle bundle = new Bundle();
                bundle.putInt(TabCodeConstant.KEY_CITY_POSITION,down_cityposition);
                bundle.putInt(TabCodeConstant.KEY_PRO_POSITION,down_proposition);

                UIHelper.showSelectMerchantRegion(this,bundle);
                break;
            case R.id.shop_shopphoto_line:
                PhotoHelper.getPhoto(this);

                //更新 照片状态
                tvShopPhoto.setText("");
                tvShopPhoto.setHint(R.string.upload_shop_photo_hint);
                break;
            case R.id.tv_commit_application:
                commitApplication();
                break;
        }
    }

    private void commitApplication() {
        if(!isFormatOk()) return;

        MerchantRegisterRequestBody body = new MerchantRegisterRequestBody();

        body.setName(etProposerName.getText().toString().trim());
        body.setCellphone(etPhone.getText().toString().trim());
        body.setRegisterLang(AppContext.getLangulage());
        body.setTitle(etShopname.getText().toString().trim());
        body.setAddress(tvShopProandcity.getText().toString().trim() + etDetailaddress.getText().toString().trim());
        body.setAccountId(AppContext.instance().getLoginUser().getAccountId());

        File imgFile = new File(PhotoHelper.DEFAULT_TEMPPATH);

        RequestBody imgBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgFile);

        RequestBody requestbody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("businessPlacePhoto", imgFile.getName(), imgBody)
                .addFormDataPart("name", body.getName())
                .addFormDataPart("cellphone", body.getCellphone())
                .addFormDataPart("title", body.getTitle())
                .addFormDataPart("address", body.getAddress())
                .addFormDataPart("accountId", String.valueOf(body.getAccountId()))
                .build();


        showDialog();
        new MyObservable<BaseResponse<MerchantBean>>().observe(getContext(),
                sMineApi.registerMerchant(requestbody),
                new OnResponseListener<BaseResponse<MerchantBean>>() {
                    @Override
                    public void onNext(BaseResponse<MerchantBean> data) {
                        getmTipDialog().dismiss();
                        if (data.getCode() == 0) {
                            getCurActivity().finish();
                            UIHelper.showApplicationCommited(getContext());
                        } else {
                            AppContext.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getmTipDialog().dismiss();
                        AppContext.showToast(e.getMessage());
                    }
                });
    }

    private boolean isFormatOk() {
        String phone = etPhone.getText().toString().trim();
        if(Validator.isMobile(phone)) return true;
        AppContext.showToast(R.string.format_no_phone);
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "requestCode: " + requestCode + " , resultCode : " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PhotoHelper.REQUESTCODE_CAMERA) {
                //拍照回来进入裁剪 Activity
                PhotoHelper.getPhotoZoom(this,
                        Uri.fromFile(new File(PhotoHelper.DEFAULT_TEMPPATH)),//parseFile不行
                        1,
                        1);
                TLog.log("photo_cycle" , "requestCode == PhotoHelper.REQUESTCODE_CAMERA");
            } else if (requestCode == PhotoHelper.REQUESTCODE_ALBUM) {
                //相册回来进入裁剪 Activity
                Uri uri = data.getData();//获取选择图片 的Uri
                PhotoHelper.getPhotoZoom(this,
                        uri,
                        1,
                        1);
                TLog.log("photo_cycle" , "requestCode == PhotoHelper.REQUESTCODE_ALBUM");
            } else if (requestCode == PhotoHelper.REQUESTCODE_ZOOM) {
                Bitmap bitmap = PhotoHelper.convertToBitmap(PhotoHelper.DEFAULT_TEMPPATH,
                        500,
                        500);
                tvShopPhoto.setText(R.string.upload_already);
                TLog.log("photo_cycle" , "requestCode == PhotoHelper.REQUESTCODE_ZOOM");
            }else if(requestCode == TabCodeConstant.REQUESTCODE_MERCHANT_PROSELECT){
                down_cityposition = data.getIntExtra(TabCodeConstant.KEY_CITY_POSITION,0);
                down_proposition = data.getIntExtra(TabCodeConstant.KEY_PRO_POSITION,0);
                String proName = data.getStringExtra(TabCodeConstant.KEY_PRONAME);
                String cityName = data.getStringExtra(TabCodeConstant.KEY_CITYNAME);
                tvShopProandcity.setText(proName + " " + cityName);
            }
        }


    }



}
