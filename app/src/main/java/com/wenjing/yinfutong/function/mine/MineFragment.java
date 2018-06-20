package com.wenjing.yinfutong.function.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.PhotoHelper;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.TextUtil;
import com.wenjing.yinfutong.utils.Validator;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wenjing.yinfutong.utils.LocalUtils.isNotLoginTowards;

public class MineFragment extends BaseFragment {
    private static final String TAG = "MineFragment";

    @BindView(R.id.bill_line)
    RelativeLayout billLine;
    @BindView(R.id.merchant_service_line)
    RelativeLayout merchantServiceLine;
    @BindView(R.id.bank_card_line)
    RelativeLayout bankCardLine;
    @BindView(R.id.safety_management_line)
    RelativeLayout safetyManagementLine;
    @BindView(R.id.customer_service_line)
    RelativeLayout customerServiceLine;
    @BindView(R.id.switch_language_line)
    RelativeLayout switchLanguageLine;
    @BindView(R.id.verify_name)
    TextView verifyName;
    @BindView(R.id.verify_id)
    TextView verifyId;
    @BindView(R.id.balance_num)
    TextView balanceNum;
    @BindView(R.id.iv_portrait)
    ImageView ivPortrait;
    @BindView(R.id.tv_cur_language)
    TextView tvCurLanguage;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        initDatas();
        setToolbarRight();
    }

    private void initDatas() {
        //语言记录
        int langulage = AppContext.getLangulage();
        String[] languages = getResources().getStringArray(R.array.languages);
        tvCurLanguage.setText(languages[langulage - 1]);

    }

    private void setToolbarRight() {
        BaseActivity activity = (BaseActivity) getCurActivity();
        QMUITopBarLayout qmuiTopBarLayout = activity.getmQmuiTopBarlayout();
        qmuiTopBarLayout.removeAllRightViews();

        activity.setActionBarTitle(getResources().getString(R.string.mine));

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        TLog.log(TAG,"onHiddenChanged hidden : " + hidden);

        if (!hidden) {
            setToolbarRight();
            refreshData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        if (!AppContext.instance().isLogin()) {
            verifyName.setText(R.string.not_verify_name);
            verifyId.setText(R.string.not_verify_id);
            ivPortrait.setImageResource(R.mipmap.no_portrait);
            balanceNum.setText(TextUtil.getSpannableBalance(getResources().getString(R.string.default_balance_num)));
            return;
        } else {
            Customer customer = AppContext.instance().getLoginUser();
            updateUserUI(customer);
        }
        getUserInfo();
    }

    private void getUserInfo() {
        int accountId;
        if (!AppContext.instance().isLogin()) {
            accountId = -1;
        } else {
            accountId = AppContext.instance().getLoginUser().getAccountId();
        }

        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(), accountId);
        new MyObservable<BaseResponse<Customer>>().observe(getContext(),
                sHomeApi.getUserInfo(body),
                new OnResponseListener<BaseResponse<Customer>>() {
                    @Override
                    public void onNext(BaseResponse<Customer> data) {

                        if (data.getData() != null && data.getCode() == 0) {
                            Customer customer = data.getData();
                            updateUserUI(customer);

                            //覆盖缓存
                            AppContext.instance().saveLoginInfo(getContext(), customer);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.toString());
                    }
                });

    }

    private void updateUserUI(Customer customer) {
        String nickName = customer.getNickName();
        String cellphone = customer.getCellphone();
        verifyName.setText(nickName);
        verifyId.setText(Validator.getStarPhone(cellphone));

        //余额
        double usableAmount = customer.getUsableAmount();
        String format = DecimalFormatUtil.defFormat(usableAmount);
        String formatAmount = Validator.getPerThreeCommaAmount(format);

        balanceNum.setText(TextUtil.getSpannableBalance(formatAmount));

        TLog.log(TAG, "verifyName : " + nickName + " , verifyId : " + cellphone + " , usableAmount : " + usableAmount);
    }

    @OnClick({R.id.balance_line, R.id.iv_portrait, R.id.verifyinfo_line, R.id.bill_line, R.id.merchant_service_line, R.id.bank_card_line, R.id.safety_management_line, R.id.customer_service_line, R.id.switch_language_line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.balance_line:
                showBalance();
                break;
            case R.id.verifyinfo_line:
                verifyUserInfo();

                break;
            case R.id.bill_line:
                showBill();
                break;
            case R.id.merchant_service_line:
                merchantservice();
                break;
            case R.id.bank_card_line:
                bankCard();
                break;
            case R.id.safety_management_line:
                safetyManager();
                break;
            case R.id.customer_service_line:
                customerservice();
                break;
            case R.id.switch_language_line:

                UIHelper.showSwitchLanguage(getContext());

                break;
            case R.id.iv_portrait:
//                selectPhotoAsPortrait();
                break;
        }
    }

    private void customerservice() {
        if (isNotLoginTowards(getContext())) return;

        UIHelper.showCustomerService(getContext());
    }

    private void showBalance() {
        if (isNotLoginTowards(getContext())) return;
        UIHelper.showAccountBanlace(getContext());
    }

    private void showBill() {
        if (isNotLoginTowards(getContext())) return;

        UIHelper.showPersonalBills(getContext());
    }

    private void bankCard() {
        if (isNotLoginTowards(getContext())) return;

        UIHelper.showBankCard(getContext());
    }

    private void selectPhotoAsPortrait() {
        if (isNotLoginTowards(getContext())) return;

        PhotoHelper.getPhoto(this);
    }

    private void safetyManager() {
        if (isNotLoginTowards(getContext())) return;

        UIHelper.showSafetyManager(getContext());
    }

    private void merchantservice() {
        if (isNotLoginTowards(getContext())) return;

        LocalUtils.rootToMerchant(this,sHomeApi,null);
    }

    private void switchLanguage() {
        UIHelper.showSwitchLanguage(getContext());
    }

    private void verifyUserInfo() {
        if (isNotLoginTowards(getContext())) return;

        Bundle bundle = new Bundle();
        UIHelper.showPersonalInfo(getContext(), bundle);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("onActivityResult", "requestCode: " + requestCode + " , resultCode : " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PhotoHelper.REQUESTCODE_CAMERA) {
                //拍照回来进入裁剪 Activity
                PhotoHelper.getPhotoZoom(this,
                        Uri.fromFile(new File(PhotoHelper.DEFAULT_TEMPPATH)),//parseFile不行
                        1,
                        1);
            } else if (requestCode == PhotoHelper.REQUESTCODE_ALBUM) {
                //拍照回来进入裁剪 Activity
                Uri uri = data.getData();//获取选择图片 的Uri
                PhotoHelper.getPhotoZoom(this,
                        uri,
                        1,
                        1);
                Log.i(TAG, "getPath: " + uri);

            } else if (requestCode == PhotoHelper.REQUESTCODE_ZOOM) {
                Bitmap bitmap = PhotoHelper.convertToBitmap(PhotoHelper.DEFAULT_TEMPPATH,
                        500,
                        500);
                ivPortrait.setImageBitmap(bitmap);

                PhotoHelper.getImgFileByte();
            }
        }
    }
}
