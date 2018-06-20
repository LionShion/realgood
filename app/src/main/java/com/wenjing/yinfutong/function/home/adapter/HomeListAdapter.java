package com.wenjing.yinfutong.function.home.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.model.HomeDataBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by wenjing on 2018/3/16.
 */

public class HomeListAdapter extends CommonAdapter<HomeDataBean.MenuBean> {
    public HomeListAdapter(Context context, List<HomeDataBean.MenuBean> datas) {
        super(context, R.layout.item_home_list, datas);
    }


    @Override
    protected void convert(ViewHolder holder, HomeDataBean.MenuBean bean, int position) {
        String imageUrl = bean.getImageUrl();
        String linkUrl = bean.getLinkUrl();
        String name = bean.getName();
        int sort = bean.getSort();

        holder.setText(R.id.tv_name, name);

        Uri uri=Uri.parse(imageUrl);

        SimpleDraweeView simpleDraweeView= holder.getConvertView().findViewById(R.id.imageView);
        simpleDraweeView.setImageURI(uri);

    }
}
