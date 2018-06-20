package com.wenjing.yinfutong.function.mine.fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.PersonalModifyRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.TextUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\15 0015.
 */

public class NicknameFragment extends BaseFragment {
    public static final String TAG = "NicknameFragment";

    @BindView(R.id.et_nickname)
    MaterialEditText etNickname;
    @BindView(R.id.tv_nickname_commit)
    TextView tvNicknameCommit;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_nickname;
    }

    @Override
    protected void initView(View view) {
        etNickname.setText(AppContext.instance().getLoginUser().getNickName());

        tvNicknameCommit.setClickable(false);
        etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                TLog.log(TAG, "etNickname.getText(): " + etNickname.getText().toString().trim());

                String content = editable.toString();
                if (TextUtils.isEmpty(content) || content.length() < 4) {
                    tvNicknameCommit.setBackgroundResource(R.drawable.gray_btn_normal);
                    tvNicknameCommit.setClickable(false);
                }else {
                    tvNicknameCommit.setBackgroundResource(R.drawable.green_btn_pressed);
                    tvNicknameCommit.setClickable(true);
                }

            }
        });
    }

    @OnClick(R.id.tv_nickname_commit)
    public void onViewClicked() {
        final String nickname = etNickname.getText().toString().trim();
//        if (TextUtil.isEmpty(nickname)) {
//            AppContext.showToast(R.string.nickname_empty);
//            return;
//        }

        //网络操作 保存昵称
        PersonalModifyRequestBody body = new PersonalModifyRequestBody();
        Customer customer = AppContext.instance().getLoginUser();
        body.setAccountId(customer.getAccountId());
        body.setCellphone(customer.getCellphone());
        body.setNickName(nickname);
        body.setRegisterLang(AppContext.getLangulage());

        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.modifyPersonInfo(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if (data.getCode() == 0) {
                            //先 本地静态保存  昵称
                            AppContext.instance().getLoginUser().setNickName(nickname);//本地修改  昵称

                            getCurActivity().finish();
                            AppContext.showToast(R.string.success_modify_nickname);
                        } else {
                            AppContext.showToast(data.getMsg());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.getMessage());
                    }
                });

    }

}
