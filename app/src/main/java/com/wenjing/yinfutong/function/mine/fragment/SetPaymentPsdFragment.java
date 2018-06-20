package com.wenjing.yinfutong.function.mine.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.PersonalModifyRequestBody;
import com.wenjing.yinfutong.utils.LocalUtils;
import com.wenjing.yinfutong.utils.MD5Util;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\28 0028.
 */

public class SetPaymentPsdFragment extends BaseFragment {

    @BindView(R.id.et_spayment_psd)
    MaterialEditText etSpaymentPsd;
    @BindView(R.id.et_spayment_confirm)
    MaterialEditText etSpaymentConfirm;
    @BindView(R.id.tv_spayment_commit)
    TextView tvSpaymentCommit;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_setpaymentpsd;
    }

    @Override
    protected void initView(View view) {
        tvSpaymentCommit.setClickable(false);
        addTextListener(etSpaymentPsd);
        addTextListener(etSpaymentConfirm);
    }

    private void addTextListener(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isPsdEmpty()
                        && !isPsdConfirmEmpty()
                        && LocalUtils.isPayPsdFormatOk(etSpaymentPsd)
                        && isPsdEqual()){
                    tvSpaymentCommit.setBackgroundResource(R.drawable.green_btn_pressed);
                    tvSpaymentCommit.setClickable(true);
                }else {
                    tvSpaymentCommit.setBackgroundResource(R.drawable.gray_btn_normal);
                    tvSpaymentCommit.setClickable(false);
                }
            }
        });
    }

    @OnClick(R.id.tv_spayment_commit)
    public void onViewClicked() {
//        if (isPsdEmpty()) return;
//        if(!LocalUtils.isPayPsdFormatOk(etSpaymentPsd)) return;
//        if (isPsdConfirmEmpty()) return;
//        if (!isPsdEqual()) return;

        String psd = etSpaymentConfirm.getText().toString().trim();

        PersonalModifyRequestBody body = new PersonalModifyRequestBody();

        Customer customer = AppContext.instance().getLoginUser();

        body.setNewTradePwd(MD5Util.encrypt(psd));
        body.setAccountId(customer.getAccountId());
        body.setRegisterLang(AppContext.getLangulage());
        body.setCellphone(customer.getCellphone());

        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.modifyPersonInfo(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if(data.getCode() == 0){
                            AppContext.showToast(R.string.succ_set_paymentpsd);
                            AppContext.instance().getLoginUser().setTraderPasswordFlag(RequestConstant.TRADEPSD_FLAG_YES);
                            getCurActivity().finish();
                        }else {
                            AppContext.showToast(data.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.getMessage());
                    }
                });

    }

    private boolean isPsdEmpty() {
        String psd = etSpaymentPsd.getText().toString().trim();
        if (TextUtil.isEmpty(psd)) {
//            AppContext.showToast(R.string.psd_empty);
            return true;
        }
        return false;
    }

    private boolean isPsdConfirmEmpty() {
        String psdConfirm = etSpaymentConfirm.getText().toString().trim();
        if (TextUtil.isEmpty(psdConfirm)) {
//            AppContext.showToast(R.string.psd_confirm_empty);
            return true;
        }
        return false;
    }

    private boolean isPsdEqual() {
        String psd = etSpaymentPsd.getText().toString().trim();
        String psdConfirm = etSpaymentConfirm.getText().toString().trim();

        if (psd.equals(psdConfirm)) {
            return true;
        }
//        AppContext.showToast(R.string.psd_not_equal);
        return false;
    }

}
