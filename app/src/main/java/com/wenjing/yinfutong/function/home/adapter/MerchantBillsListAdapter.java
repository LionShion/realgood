package com.wenjing.yinfutong.function.home.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.model.MerchantBillsListBean;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by wenjing on 2018/3/21.
 */

public class MerchantBillsListAdapter extends CommonAdapter<MerchantBillsListBean.ListBean> {
    public MerchantBillsListAdapter(Context context, List<MerchantBillsListBean.ListBean> datas) {
        super(context, R.layout.item_bills_list, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MerchantBillsListBean.ListBean listBean, int position) {
        String orderNo = listBean.getOrderNo();
        String payerIcon = listBean.getPayerIcon();
        String payerName = listBean.getPayerName();
        int receiveAmount = listBean.getReceiveAmount();
        String receiveTime = listBean.getReceiveTime();

        String money = DecimalFormatUtil.defFormat(receiveAmount);

        holder.setText(R.id.tv_name,payerName)
                .setText(R.id.tv_money,"+"+money)
                .setText(R.id.tv_brief,receiveTime);

        Uri uri = Uri.parse(payerIcon);
        SimpleDraweeView draweeView = holder.getConvertView().findViewById(R.id.iv_icon);
        draweeView.setImageURI(uri);




    }
}
