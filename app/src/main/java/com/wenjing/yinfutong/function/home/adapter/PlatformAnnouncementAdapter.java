package com.wenjing.yinfutong.function.home.adapter;

import android.content.Context;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.model.NoticePageBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by wenjing on 2018/4/13.
 */

public class PlatformAnnouncementAdapter extends CommonAdapter<NoticePageBean.ListBean> {
    public PlatformAnnouncementAdapter(Context context,  List<NoticePageBean.ListBean> datas) {
        super(context, R.layout.item_platform_announcement, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NoticePageBean.ListBean noticeBean, int position) {
        String title = noticeBean.getTitle();
        String content = noticeBean.getContent();
        String linkUrl = noticeBean.getLinkUrl();

        holder.setText(R.id.tv_title,title)
                .setText(R.id.tv_time,content);

    }
}
