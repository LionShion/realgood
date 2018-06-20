package com.wenjing.yinfutong.function.mine.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.DepositBankBean;
import com.wenjing.yinfutong.model.RegionBean;
import com.wenjing.yinfutong.model.body.BindBankCardRequestBody;
import com.wenjing.yinfutong.model.body.SMSSendRequestBody;
import com.wenjing.yinfutong.utils.BaseTimerTextHandler;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.TextUtil;
import com.wenjing.yinfutong.utils.VerifyCodeUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\23 0023.
 */

public class BindBankCardFragment extends BaseFragment {
    public static final String TAG = "BindBankCardFragment";

    public static final int REQUESTCODE_DEPOSITBANK = 100;

    @BindView(R.id.et_tb_name)
    MaterialEditText etTbName;
    @BindView(R.id.et_tb_idnum)
    MaterialEditText etTbIdnum;
    @BindView(R.id.tv_tb_depositbank)
    TextView tvTbDepositbank;
    @BindView(R.id.et_tb_cardnum)
    MaterialEditText etTbCardnum;
    @BindView(R.id.et_tb_reservedphone)
    MaterialEditText etTbReservedphone;

    @BindView(R.id.et_tb_verifycode)
    MaterialEditText etTbVerifycode;
    @BindView(R.id.tv_tb_regioncode)
    TextView tvTbRegioncode;
    @BindView(R.id.tv_tb_regionname)
    TextView tvTbRegionname;
    @BindView(R.id.tv_tb_commit)
    TextView tvTbCommit;
    @BindView(R.id.tv_tb_obtain_verifycode)
    TextView tvTbObtainVerifycode;

    // 每个EditText  是否为空的状态   默认为空   false
    private Map<Integer, Boolean> etFlagMap = new HashMap<Integer, Boolean>() {
        {
            put(R.id.et_tb_name, false);
            put(R.id.et_tb_idnum, false);
            put(R.id.et_tb_cardnum, false);
            put(R.id.et_tb_reservedphone, false);
            put(R.id.et_tb_verifycode, false);
        }
    };


    private TxtTimer txtTimer = new TxtTimer(this);

    private int down_regionposition = 0;

    private RegionBean regionBean = RegionBean.getDefault();

    private DepositBankBean.PaybankBean bank = null;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_tie_bankcard;
    }

    @Override
    protected void initView(View view) {
        initDatas();

        tvTbCommit.setClickable(false);
        addETTxtChangeListen(view, R.id.et_tb_name);
        addETTxtChangeListen(view, R.id.et_tb_idnum);
        addETTxtChangeListen(view, R.id.et_tb_cardnum);
        addETTxtChangeListen(view, R.id.et_tb_reservedphone);
        addETTxtChangeListen(view, R.id.et_tb_verifycode);
    }

    private void initDatas() {
        tvTbRegionname.setText(regionBean.getSupportArea());
        tvTbRegioncode.setText("+" + regionBean.getSupportPre());
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
        if (etFlagMap.get(R.id.et_tb_name)
                && etFlagMap.get(R.id.et_tb_idnum)
                && etFlagMap.get(R.id.et_tb_cardnum)
                && etFlagMap.get(R.id.et_tb_reservedphone)
                && etFlagMap.get(R.id.et_tb_verifycode)
                && !TextUtil.isTVEmpty(tvTbDepositbank)
                && !TextUtil.isTVEmpty(tvTbRegionname)
                && !TextUtil.isTVEmpty(tvTbRegioncode)
                && isFormatAllOk()
                ) {
            tvTbCommit.setClickable(true);
            tvTbCommit.setBackgroundResource(R.drawable.green_btn_pressed);
        } else {
            tvTbCommit.setClickable(false);
            tvTbCommit.setBackgroundResource(R.drawable.gray_btn_normal);
        }

    }

    @OnClick({R.id.tb_depositbank_line, R.id.tb_region_line, R.id.tv_tb_obtain_verifycode, R.id.tv_tb_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tb_depositbank_line:
                UIHelper.showDepositBank(this, 100);

                break;
            case R.id.tb_region_line:
                regionSelect();
                break;
            case R.id.tv_tb_obtain_verifycode:
                obtainVerifyCode();
                break;
            case R.id.tv_tb_commit:
                commitToTiebankCard();
                break;
        }
    }

    private boolean isPhoneEmpty() {
        String phone = etTbReservedphone.getText().toString().trim();
        if (TextUtil.isEmpty(phone)) {
            AppContext.showToast(R.string.reserved_phone_empty);
            return true;
        }

        return false;
    }

    private void obtainVerifyCode() {
        if (isPhoneEmpty()) return;
        if(!LocalUtils.isPhoneFormatOk(etTbReservedphone)) return;

        String phone = etTbReservedphone.getText().toString().trim();

        SMSSendRequestBody body = new SMSSendRequestBody();
        body.setAccount(phone);
        body.setAccountType(RequestConstant.getAccountType(phone));
        body.setSmsType(RequestConstant.SMS_TYPE_BINDCARD);
        body.setRegisterLang(AppContext.getLangulage());
        body.setAttributionId(regionBean == null ? -1 : regionBean.getId());


        //暂时不需要  回调
        VerifyCodeUtils.obtainSmsVerifyCode(getContext(),sMineApi, body, new OnResponseListener<BaseResponse>() {
            @Override
            public void onNext(BaseResponse data) {
                if(data.getCode() == 0){
                    AppContext.showToast(R.string.verifycode_sent);
                    txtTimer.start();// 开始计时
                }else {
                    AppContext.showToast(data.getMsg());
                    tvTbObtainVerifycode.setClickable(true);
                }

            }

            @Override
            public void onError(Throwable e) {
                tvTbObtainVerifycode.setClickable(true);
            }
        });

        tvTbObtainVerifycode.setClickable(false);
    }

    private void regionSelect() {
        Bundle bundle = new Bundle();
        bundle.putInt(TabCodeConstant.KEY_TABCODE, TabCodeConstant.TAB_TIEBANKCARD);
        bundle.putInt(TabCodeConstant.KEY_REGION_POSITION,down_regionposition);
        UIHelper.showRegionForResult(this, bundle);
    }

    private void commitToTiebankCard() {
//        if(!isFormatAllOk()) return;

        Customer customer = AppContext.instance().getLoginUser();

        BindBankCardRequestBody body = new BindBankCardRequestBody();
        body.setAccountId(customer.getAccountId());
        body.setAccountName(etTbName.getText().toString().trim());
        body.setCardNo(etTbCardnum.getText().toString().trim());
        body.setCellphone(etTbReservedphone.getText().toString().trim());
        body.setIdcard(etTbIdnum.getText().toString().trim());
        body.setRegisterLang(AppContext.getLangulage());
        body.setValiCode(etTbVerifycode.getText().toString().trim());
        body.setBankNo(bank == null ? "" : bank.getBankNo());
        body.setBankName(bank == null ? "" : bank.getBankName());
        body.setAttributionId(regionBean == null ? -1 : regionBean.getId());

        TLog.log(TAG, body.toString());

        showWaitDialog();
        new MyObservable<BaseResponse>().observe( getContext(),
                sMineApi.bindBankCard(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        hideWaitDialog();
                        if (data.getCode() == 0) {
                            AppContext.showToast(R.string.succ_bind_bankcard);
                            getCurActivity().finish();
                        } else {
                            AppContext.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                    }
                });

    }

    private boolean isFormatAllOk() {
        if(!LocalUtils.isIdNoFormatOk(etTbIdnum)) return false;
        if(!LocalUtils.isBankNoFormatOk(etTbCardnum)) return false;
        if(!LocalUtils.isPhoneFormatOk(etTbReservedphone)) return false;
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUESTCODE_DEPOSITBANK) {
                bank = data.getParcelableExtra("bank");
                tvTbDepositbank.setText(bank.getBankName());
            } else if (requestCode == TabCodeConstant.REQUESTCODE_REGIONSELECT) {
                RegionBean bean = data.getParcelableExtra(TabCodeConstant.KEY_REGION_BEAN);
                down_regionposition = data.getIntExtra(TabCodeConstant.KEY_REGION_POSITION,0);
                tvTbRegionname.setText(bean.getSupportArea());
                tvTbRegioncode.setText("+" + bean.getSupportPre());

                regionBean = bean;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(txtTimer != null){
            txtTimer.stop();
            txtTimer = null;
        }
    }


    private static class TxtTimer extends BaseTimerTextHandler{

        private WeakReference<BindBankCardFragment> reference;

        public TxtTimer(BindBankCardFragment fragment){
            reference = new WeakReference<BindBankCardFragment>(fragment);
        }

        @Override
        public void waitingRun(int leaveTime) {
            reference.get().tvTbObtainVerifycode.setText(String.valueOf(leaveTime) + getUnit());
        }

        @Override
        public void waitingComplete() {
            reference.get().tvTbObtainVerifycode.setText(R.string.resend);
            reference.get().tvTbObtainVerifycode.setClickable(true);
        }
    }


}
