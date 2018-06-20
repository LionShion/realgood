package com.wenjing.yinfutong.function.home.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.function.RequestCode;

/**
 * Created by wenjing on 2018/3/22.
 */

public class PersonalBillsScreenFragment extends DialogFragment implements View.OnClickListener {

    TextView tvFinish;
    //全部
    private int incomeTypeInt=3,
                payTypeInt=3;
    RadioButton rbIncomeAll, rbIncome, rbOut, rbTypeAll, rbPersonal, rbMerchant;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_personal_bills_screen);
        dialog.setCanceledOnTouchOutside(true);
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = (int) (getActivity().getWindowManager().getDefaultDisplay().getHeight() * 0.4);
        window.setAttributes(lp);

        initView(dialog);

        return dialog;
    }


    private void initView(Dialog dialog) {
        tvFinish = dialog.findViewById(R.id.tv_finish);
        rbIncomeAll = dialog.findViewById(R.id.rb_income_all);
        rbIncome = dialog.findViewById(R.id.rb_income);
        rbOut = dialog.findViewById(R.id.rb_out);
        rbTypeAll = dialog.findViewById(R.id.rb_type_all);
        rbPersonal = dialog.findViewById(R.id.rb_personal);
        rbMerchant = dialog.findViewById(R.id.rb_merchant);

        tvFinish.setOnClickListener(this);
        rbIncomeAll.setOnClickListener(this);
        rbIncome.setOnClickListener(this);
        rbOut.setOnClickListener(this);
        rbTypeAll.setOnClickListener(this);
        rbPersonal.setOnClickListener(this);
        rbMerchant.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_income_all:
                incomeTypeInt=3;
                break;
            case R.id.rb_income:
                incomeTypeInt=1;
                break;
            case R.id.rb_out:
                incomeTypeInt=2;
                break;
            case R.id.rb_type_all:
                payTypeInt=3;
                break;
            case R.id.rb_personal:
                payTypeInt=1;
                break;
            case R.id.rb_merchant:
                payTypeInt=2;
                break;
            case R.id.tv_finish:

                Intent resultIntent = new Intent();
                resultIntent.putExtra("incomeTypeInt", incomeTypeInt);
                resultIntent.putExtra("payTypeInt", payTypeInt);
                getTargetFragment().onActivityResult(RequestCode.PERSONAL_BILLS_SCREEN,
                        Activity.RESULT_OK,
                        resultIntent);

                getDialog().dismiss();
                break;
        }
    }
}
