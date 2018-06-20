package com.wenjing.yinfutong.function.home.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.model.HomeDataBean;
import com.wenjing.yinfutong.utils.TextUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by wenjing on 2018/2/26.
 */

public class HomeActivityAdapter extends CommonAdapter<HomeDataBean.BannerBean> {
    public HomeActivityAdapter(Context context, List<HomeDataBean.BannerBean> datas) {
        super(context, R.layout.item_home_activity, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HomeDataBean.BannerBean bean, int position) {

        String url = bean.getImageUrl();

        if (!TextUtil.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            SimpleDraweeView draweeView = holder.getConvertView().findViewById(R.id.imageView);
            draweeView.setImageURI(uri);
        }

        if (position == getDatas().size() - 1) {
            holder.setVisible(R.id.tv_name, true);
        } else {
            holder.setVisible(R.id.tv_name, false);
        }


    }

}
