package com.wenjing.yinfutong.function.home.adapter;

import android.content.Context;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.utils.BankCardHeadUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by wenjing on 2017/10/19.
 */

public class ChooseBankListAdapter extends CommonAdapter<BankCardBean> {
    public ChooseBankListAdapter(Context context, List<BankCardBean> datas) {
        super(context, R.layout.item_choose_bankcard_manager, datas);
    }

    @Override
    protected void convert(ViewHolder holder, BankCardBean bean, int position) {
        String bankName = bean.getBankName();
        String cardNo = bean.getCardNo();
        String bankNo = bean.getBankNo();
        String subCardNo = cardNo.substring(cardNo.length() - 4, cardNo.length());

        holder.setText(R.id.tv_bank_name, bankName)
                .setText(R.id.tv_bank_num,"("+subCardNo+")");

        holder.setImageDrawable(R.id.iv_bank_icon, BankCardHeadUtil.getBankDrawable(bankNo));

    }
}
