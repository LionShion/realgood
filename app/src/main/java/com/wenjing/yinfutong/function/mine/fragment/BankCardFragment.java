package com.wenjing.yinfutong.function.mine.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.model.body.UnbindBankCardRequestBody;
import com.wenjing.yinfutong.utils.MyObservable;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.utils.Validator;
import com.wenjing.yinfutong.view.CustomDialog;
import com.wenjing.yinfutong.view.LinearItemDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ${luoyingtao} on 2018\3\22 0022.
 */

public class BankCardFragment extends BaseFragment {
    public static final String TAG = "BankCardFragment";

    @BindView(R.id.recycler_bankcard)
    SwipeMenuRecyclerView recyclerBankcard;
    @BindView(R.id.page_bankcard)
    LinearLayout pageBankcard;
    @BindView(R.id.page_no_bankcard)
    LinearLayout pageNoBankcard;
    @BindView(R.id.page_error)
    LinearLayout pageError;

    public static final Map<Integer, String> bankcard_typeMap = new HashMap<Integer, String>() {
        {
            put(RequestConstant.BANKCARD_TYPE_SAVINGSCARD, "储蓄卡");
            put(RequestConstant.BANKCARD_TYPE_CREDITCARD, "信用卡");
        }
    };

    private CommonAdapter<BankCardBean> adapter;

    private List<BankCardBean> bankCardBeanList = new ArrayList<>();

    private BankCardObserver observer = new BankCardObserver();

    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.btn_unbind_width);

            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                    .setBackground(R.drawable.unbind_bg)
                    .setText(R.string.unbind)
                    .setTextColor(ContextCompat.getColor(getContext(), R.color.btn_unbind_txtcolor))
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(deleteItem); // 添加菜单到右侧。

        }
    };


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_bankcard;
    }

    @Override
    protected void initView(View view) {
        initBankRecycler();
    }

    @Override
    public void onResume() {
        super.onResume();
        //聚焦以后  加载数据  以便更新

        //初始化页面
        initPage();
        //清除旧数据
        bankCardBeanList.clear();

        loadData();
    }

    private void initPage() {
        pageNoBankcard.setVisibility(View.GONE);
        pageBankcard.setVisibility(View.GONE);
        pageError.setVisibility(View.GONE);
    }

    private void initBankRecycler() {
        recyclerBankcard.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerBankcard.addItemDecoration(new LinearItemDecoration(getContext(), OrientationHelper.VERTICAL, R.drawable.bankcard_divider));

        adapter = new CommonAdapter<BankCardBean>(getContext(),
                R.layout.item_bankcard,
                bankCardBeanList) {
            @Override
            protected void convert(ViewHolder holder, BankCardBean bankCardBean, final int position) {
                holder.setText(R.id.name_bankcard, bankCardBean.getBankName());
                holder.setText(R.id.type_bankcard, bankcard_typeMap.get(bankCardBean.getBankType()));
                holder.setText(R.id.num_bankcard, Validator.getPerFourRegex(bankCardBean.getCardNo()));

                int color = Color.parseColor("#" + bankCardBean.getColor());

                /**
                 * 如果insetDistance与insideRadian设为null亦可
                 */
                /**
                 * 外部矩形弧度
                 */
                float[] outerRadian = new float[]{20, 20, 20, 20, 20, 20, 20, 20};
                RoundRectShape roundRectShape = new RoundRectShape(outerRadian, null, null);
                ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
                /**
                 * 指定填充颜色
                 */
                drawable.getPaint().setColor(color);
                /**
                 * 指定填充模式
                 */
                drawable.getPaint().setStyle(Paint.Style.FILL);

                holder.getView(R.id.item_bankcard_left).setBackground(drawable);

                SimpleDraweeView simpleDraweeView=holder.getConvertView().findViewById(R.id.logo_bankcard);
                String imgUrl = bankCardBean.getImgUrl();
                simpleDraweeView.setImageURI(Uri.parse(imgUrl));

            }
        };

        //设置侧滑

        recyclerBankcard.setSwipeMenuCreator(mSwipeMenuCreator);
        recyclerBankcard.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {

                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();

                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                TLog.log(TAG, "direction: " + direction + ", adapterPosition : " + adapterPosition + ", menuPosition" + menuPosition);
                switch (menuPosition) {
                    case 0:
                        //  删除
                        showUnbindTipDialog(adapterPosition);
                        break;
                }

            }
        });

        recyclerBankcard.setAdapter(adapter);

        adapter.registerAdapterDataObserver(observer);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BankCardBean bean = bankCardBeanList.get(position);
                bean.setActive(!bean.isActive());
                bankCardBeanList.set(position, bean);
                adapter.notifyItemChanged(position);//通知单项数据  改变
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void showUnbindTipDialog(final int position) {
        new CustomDialog.Builder(getContext())
                .setMessage(R.string.tip_confirm_unbind_bankcard)
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {//左边
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(R.string.unbind, new DialogInterface.OnClickListener() {//右边
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        unBindItemBankCard(position);
                        dialogInterface.dismiss();
                    }
                })
                .create(R.layout.dialog_layout)
                .show();
    }

    private void unBindItemBankCard(final int position) {
        //调  解绑银行卡接口   修改后台数据
        BankCardBean bean = bankCardBeanList.get(position);
        UnbindBankCardRequestBody body = new UnbindBankCardRequestBody();
        body.setAccountId(bean.getAccountId());
        body.setBankId(bean.getBankId());
        body.setRegisterLang(AppContext.getLangulage());
        new MyObservable<BaseResponse>().observe(getContext(),
                sMineApi.unbindBankCard(body),
                new OnResponseListener<BaseResponse>() {
                    @Override
                    public void onNext(BaseResponse data) {
                        if (data.getCode() == 0) {
                            //adapter   remove条目
                            AppContext.showToast(R.string.succ_remove_bankcard);
                            bankCardBeanList.remove(position);
                            adapter.notifyItemRemoved(position);
                            adapter.notifyItemRangeChanged(position, bankCardBeanList.size() - position);

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(R.string.fail_remove);
                    }
                });

    }

    private void loadData() {
        Customer customer = AppContext.instance().getLoginUser();
        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(),customer.getAccountId());

        showWaitDialog();
        new MyObservable<BaseResponse<List<BankCardBean>>>().observe(getContext(),
                sMineApi.getCustomerBankList(body),
                new OnResponseListener<BaseResponse<List<BankCardBean>>>() {
                    @Override
                    public void onNext(BaseResponse<List<BankCardBean>> data) {
                        hideWaitDialog();
                        if (data.getCode() == 0 && data.getData() != null && data.getData().size() > 0) {
                            showBankRecycler();
                            bankCardBeanList.addAll(data.getData());
                            adapter.notifyDataSetChanged();
                        } else {
                            showNoTiedBankPage();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideWaitDialog();
                        AppContext.showToast(e.getMessage());
                        showErrorPage();
                    }
                });

    }

    private void showBankRecycler() {
        pageNoBankcard.setVisibility(View.GONE);
        pageError.setVisibility(View.GONE);
        pageBankcard.setVisibility(View.VISIBLE);

    }

    private void showNoTiedBankPage() {
        pageNoBankcard.setVisibility(View.VISIBLE);
        pageError.setVisibility(View.GONE);
        pageBankcard.setVisibility(View.GONE);
    }

    private void showErrorPage(){
        pageNoBankcard.setVisibility(View.GONE);
        pageError.setVisibility(View.VISIBLE);
        pageBankcard.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.unregisterAdapterDataObserver(observer);
    }

    @OnClick({R.id.tv_add_bankcard, R.id.banklist_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_bankcard:
                addBankCard();
                break;
            case R.id.banklist_add:
               addBankCard();
                break;
        }
    }

    private void addBankCard() {
        if(isNotSetPaymentPsd()) return;

        UIHelper.showBindBankCard(getContext());
    }

    private boolean isNotSetPaymentPsd() {
        Customer customer = AppContext.instance().getLoginUser();
        int traderPasswordFlag = customer.getTraderPasswordFlag();
        if(traderPasswordFlag == RequestConstant.TRADEPSD_FLAG_NO){//还没有设置支付密码
            UIHelper.showSetPaymentPsd(getContext());
            return true;
        }
        return false;
    }

    private class BankCardObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            super.onChanged();

        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            if (bankCardBeanList.size() == 0) {
                Log.i(TAG, "onItemRangeRemoved");
                showNoTiedBankPage();
            }

        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            Log.i(TAG, "onItemRangeInserted");
        }
    }

}
