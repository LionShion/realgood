package com.wenjing.yinfutong.function.mine.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.model.DepositBankBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by ${luoyingtao} on 2018\4\17 0017.
 */

public class DepositBankAdapter extends CommonAdapter<DepositBankBean.PaybankBean> {

    public DepositBankAdapter(Context context,List<DepositBankBean.PaybankBean> datas) {
        super(context, R.layout.item_depositbank, datas);
    }

    @Override
    protected void convert(ViewHolder holder, DepositBankBean.PaybankBean paybankBean, int position) {
        SimpleDraweeView simpleDraweeView=  holder.getConvertView().findViewById(R.id.item_logo_depositbank);
        simpleDraweeView.setImageURI(Uri.parse(paybankBean.getImgUrl()));

        holder.setText(R.id.item_name_depositbank,paybankBean.getBankName());
    }

}
