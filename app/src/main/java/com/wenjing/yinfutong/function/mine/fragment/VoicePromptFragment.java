package com.wenjing.yinfutong.function.mine.fragment;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by ${luoyingtao} on 2018\3\16 0016.
 */

public class VoicePromptFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.cb_voiceprompt)
    CheckBox cbVoiceprompt;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_voiceprompt;
    }

    @Override
    protected void initView(View view) {
        cbVoiceprompt.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        AppContext.saveVoicePrompt(isChecked);
    }

}
