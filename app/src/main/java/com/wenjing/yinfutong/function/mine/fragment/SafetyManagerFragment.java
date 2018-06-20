package com.wenjing.yinfutong.function.mine.fragment;

import android.content.DialogInterface;
import android.view.View;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.SimpleBackPage;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.view.CustomDialog;

import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ${luoyingtao} on 2018\3\20 0020.
 */

public class SafetyManagerFragment extends BaseFragment {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_safetymanager;
    }

    @Override
    protected void initView(View view) {

    }

    @OnClick({R.id.reset_login_psd_line, R.id.reset_payment_psd_line, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reset_login_psd_line:
                UIHelper.showResetLoginPsd(getContext());

                break;
            case R.id.reset_payment_psd_line:
                UIHelper.showSimpleBack(getContext(),SimpleBackPage.RESET_PAYMENT_PSD);

                break;
            case R.id.btn_exit:
                exit();
                break;
        }
    }

    private void exit() {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setMessage(R.string.tip_confirm_exit);

        builder.setNegativeButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                accountExit();
            }
        });

        builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create(R.layout.dialog_layout).show();
    }

    private void accountExit() {
        sMineApi.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse value) {
                        if(value.getCode() == 0){
                            AppContext.showToast(R.string.success_logout);

                            //清除缓存客户信息
                            AppContext.instance().clearLoginUser(getContext());
                            getCurActivity().finish();
                        }else {
                            AppContext.showToast(value.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
