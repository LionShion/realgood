package com.wenjing.yinfutong.function.home.adapter;

import android.content.Context;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.model.HistoryBillsBean;
import com.wenjing.yinfutong.utils.DecimalFormatUtil;
import com.wenjing.yinfutong.utils.TextUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by wenjing on 2018/3/26.
 */

public class HistoryBillsAdapter extends CommonAdapter<HistoryBillsBean.ListBean> {
    public HistoryBillsAdapter(Context context, List<HistoryBillsBean.ListBean> datas) {
        super(context, R.layout.item_history_bills, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HistoryBillsBean.ListBean listBean, int position) {
        int count = listBean.getCount();
        String date = listBean.getDate();
        String month = listBean.getMonth();
        double receiveAmount = listBean.getReceiveAmount();

        if (!TextUtil.isEmpty(date)) {
            holder.setText(R.id.tv_name, date);
        } else {
            holder.setText(R.id.tv_name, month);
        }

        String money = DecimalFormatUtil.defFormat(receiveAmount);

        holder.setText(R.id.tv_money, money)
                .setText(R.id.tv_count, String.valueOf(count));

    }
}
