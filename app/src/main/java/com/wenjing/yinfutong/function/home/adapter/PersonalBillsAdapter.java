package com.wenjing.yinfutong.function.home.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.model.PersonalBillsBean;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by wenjing on 2018/3/21.
 */

public class PersonalBillsAdapter extends CommonAdapter<PersonalBillsBean.ListBean> {
    public PersonalBillsAdapter(Context context, List<PersonalBillsBean.ListBean> datas) {
        super(context, R.layout.item_bills_list, datas);
    }

    @Override
    protected void convert(ViewHolder holder, PersonalBillsBean.ListBean listBean, int position) {
        SimpleDraweeView simpleDraweeView = holder.getConvertView().findViewById(R.id.iv_icon);
        String payerIcon = listBean.getPayerIcon();
        simpleDraweeView.setImageURI(Uri.parse(payerIcon));
        holder.setText(R.id.tv_name, listBean.getOrderTypeName())
                .setText(R.id.tv_brief, listBean.getAccountTime());


        int orderType = listBean.getOrderType();
        String money = DecimalFormatUtil.defFormat(listBean.getAccountAmount());
        switch (orderType) {
            case 1://付款
                holder.setText(R.id.tv_money, "-" + money)
                        .setTextColorRes(R.id.tv_money, R.color.black_333);
                break;

            case 2://充值
                holder.setText(R.id.tv_money, "+" + money)
                        .setTextColorRes(R.id.tv_money, R.color.orange_ff9933);
                break;
            case 3://提现
                holder.setText(R.id.tv_money, "-" + money)
                        .setTextColorRes(R.id.tv_money, R.color.black_333);
                break;

            case 4://商家
                holder.setText(R.id.tv_money, "+" + money)
                        .setTextColorRes(R.id.tv_money, R.color.orange_ff9933);
                break;


        }

    }
}
