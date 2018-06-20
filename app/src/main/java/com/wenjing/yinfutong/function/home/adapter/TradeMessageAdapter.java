package com.wenjing.yinfutong.function.home.adapter;

import android.content.Context;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.model.TradeMessageBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by wenjing on 2018/4/13.
 */

public class TradeMessageAdapter extends CommonAdapter<TradeMessageBean.MessageBean> {
    public TradeMessageAdapter(Context context, List<TradeMessageBean.MessageBean> datas) {
        super(context, R.layout.item_platform_announcement, datas);
    }

    @Override
    protected void convert(ViewHolder holder, TradeMessageBean.MessageBean bean, int position) {
        String createTime = bean.getCreateTime();
        int category = bean.getCategory();
        String content = bean.getContent();

        holder.setText(R.id.tv_time,createTime)
                .setText(R.id.tv_title,content);



    }
}
