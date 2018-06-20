package com.wenjing.yinfutong.function.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.joker.api.Permissions4M;
import com.joker.api.wrapper.Wrapper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.activity.CaptureActivity;
import com.wenjing.yinfutong.base.BaseActivity;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.function.home.adapter.HomeActivityAdapter;
import com.wenjing.yinfutong.function.home.adapter.HomeListAdapter;
import com.wenjing.yinfutong.model.HomeDataBean;
import com.wenjing.yinfutong.model.IfMechantBean;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;
import com.wenjing.yinfutong.utils.TLog;
import com.wenjing.yinfutong.view.CustomDialog;
import com.wenjing.yinfutong.view.MarqueeView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomeFragment extends BaseFragment implements Wrapper.PermissionRequestListener, Wrapper.PermissionPageListener, SwipeRefreshLayout.OnRefreshListener {

    public static final int CALL_PHONE_CODE = 100;

    public static final int NO_REGISTER = 0;
    public static final int UNDER_REVIEW = 1;
    public static final int IS_MECHANT = 2;
    public static final int REFUSE = 3;

    @BindView(R.id.ECoder_scaning)
    LinearLayout ECoderScaning;
    @BindView(R.id.ll_payment_code)
    LinearLayout llPaymentCode;
    @BindView(R.id.ll_balance)
    LinearLayout llBalance;
    @BindView(R.id.rv_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.marquee_view)
    MarqueeView marqueeView;
    @BindView(R.id.recyclerview)
    RecyclerView mActivityRecyclerView;
    @BindView(R.id.header)
    RecyclerViewHeader mHeader;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<HomeDataBean.MenuBean> infoList = new ArrayList<>();
    private List<HomeDataBean.BannerBean> infoListActivity = new ArrayList<>();
    private List<HomeDataBean.NoticeBean> noticeList = new ArrayList<>();

    private HomeListAdapter mAdapter;
    private HomeActivityAdapter mActivityAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    private void initListener() {

    }

    @Override
    protected void initView(View view) {
        setToolbarRight();
        initListener();
        initRecyclerView();
        getBanner();//加载   广告  页

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.red, R.color.black);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setToolbarRight();
        }
    }

    private void getBanner() {
        int accountId;
        if (!AppContext.instance().isLogin()) {
            accountId = -1;
        } else {
            accountId = AppContext.instance().getLoginUser().getAccountId();
        }

        BaseRequestBody requestHomeDataBody = new BaseRequestBody(AppContext.getLangulage(), accountId);
        showDialog();
        sHomeApi.getHomeData(requestHomeDataBody)
                .compose(RxResultHelper.<HomeDataBean>handleRespose())
                .compose(RxSchedulers.<HomeDataBean>applyObservableAsync())
                .subscribe(new Observer<HomeDataBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull HomeDataBean bean) {
                        getmTipDialog().dismiss();

                        List<HomeDataBean.BannerBean> banner = bean.getBanner();
                        List<HomeDataBean.MenuBean> menu = bean.getMenu();
                        List<HomeDataBean.NoticeBean> notice = bean.getNotice();

                        for (HomeDataBean.MenuBean menuBean : menu) {
                            infoList.add(menuBean);
                        }
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();


                        for (HomeDataBean.BannerBean bannerBean : banner) {
                            infoListActivity.add(bannerBean);
                        }

                        mActivityRecyclerView.setAdapter(mActivityAdapter);
                        mActivityAdapter.notifyDataSetChanged();

                        for (HomeDataBean.NoticeBean noticeBean : notice) {
                            noticeList.add(noticeBean);
                        }
                        initMarqueeView();


                        return;
                    }

                    @Override
                    public void onError(Throwable e) {
                        getmTipDialog().dismiss();
                        AppContext.showToast(e.toString());
                        // 关闭加载进度条
                        mSwipeRefreshLayout.setRefreshing(false);

                        TLog.e("HomeFragment", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }


    private void setToolbarRight() {
        BaseActivity activity = (BaseActivity) getCurActivity();
        activity.setActionBarTitle(getResources().getString(R.string.app_name));
        QMUITopBarLayout qmuiTopBarLayout = activity.getmQmuiTopBarlayout();

        activity.setTitleColor(getResources().getColor(R.color.white));
        activity.getStatusBarHelper().setStatusBarDarkMode(getCurActivity());

        qmuiTopBarLayout.removeAllLeftViews();
        qmuiTopBarLayout.setBackgroundColor(getResources().getColor(R.color.green_00bc9c));
        qmuiTopBarLayout.addRightImageButton(R.mipmap.bell, R.id.imageView).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AppContext.instance().isLogin()) {
                            UIHelper.showNewsCenter(getContext());
                            return;
                        }
                        UIHelper.showLogin(getContext());
                    }
                });
    }


    @OnClick({R.id.ECoder_scaning, R.id.ll_payment_code, R.id.ll_balance})
    public void onViewClicked(View view) {
        int traderPasswordFlag;
        switch (view.getId()) {
            case R.id.ECoder_scaning:
                if (AppContext.instance().isLogin()) {
                    traderPasswordFlag = AppContext.instance().getLoginUser().getTraderPasswordFlag();
                    switch (traderPasswordFlag) {
                        case 0://无密码
                            UIHelper.showSetPaymentPsd(getContext());
                            break;
                        case 1://有密码
                            Permissions4M.get(getCurActivity())
                                    .requestForce(true)
                                    .requestPermission(Manifest.permission.CAMERA)
                                    .requestCode(CALL_PHONE_CODE)
                                    .requestCallback(this)
                                    .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                                    .requestPage(this)
                                    .request();
                            break;
                    }
                    return;
                }
                UIHelper.showLogin(getContext());

                break;

            case R.id.ll_payment_code:
                if (AppContext.instance().isLogin()) {
                    verifyMerchants(RequestCode.QR_CODE);
                    return;
                }
                UIHelper.showLogin(getContext());
                break;
            case R.id.ll_balance:
                if (AppContext.instance().isLogin()) {
                    UIHelper.showAccountBanlace(getContext());
                    return;
                }
                UIHelper.showLogin(getContext());
                break;
        }
    }

    private void verifyMerchants(final int type) {
        int accountId = AppContext.instance().getLoginUser().getAccountId();

        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(), accountId);
        sHomeApi.getIfMerchant(body)
                .compose(RxResultHelper.<IfMechantBean>handleRespose())
                .compose(RxSchedulers.<IfMechantBean>applyObservableAsync())
                .subscribe(new Observer<IfMechantBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IfMechantBean bean) {

                        int status = bean.getStatus();
                        int merchantId = bean.getMerchantId();
                        AppContext.instance().getLoginUser().setMerchantId(merchantId);
                        switch (status) {
                            case NO_REGISTER:
                                showCustomDialog(getResources().getString(R.string.merchant_services_tips)
                                        , getResources().getString(R.string.cancel)
                                        , getResources().getString(R.string.confirm));
                                break;
                            case UNDER_REVIEW:
                                UIHelper.showApplicationCommited(getContext());
                                break;
                            case IS_MECHANT:
                                switch (type) {
                                    case RequestCode.QR_CODE:
                                        //                                        UIHelper.showTwoDimensionalCode(getContext());

                                        UIHelper.showGathering(getContext());
                                        break;
                                    case RequestCode.MY_MERCHANT:
                                        UIHelper.showMerchantService(getContext());
                                        break;
                                    case RequestCode.RECONCILIATION:
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("listBean", null);
                                        bundle.putInt("type", RequestCode.DAY_TYPE);
                                        bundle.putInt("buttonType", 1);
                                        UIHelper.showMerchantBillsList(getContext(), bundle);

                                        break;
                                }
                                break;
                            case REFUSE:
                                UIHelper.showOpenMerchantRefuse(getContext());
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }

                });

    }


    @Override
    public void permissionGranted() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(getCurActivity(), CaptureActivity.class);
        startActivity(intent);
    }

    @Override
    public void permissionDenied() {
        //        AppContext.showToast("失败");
    }

    @Override
    public void permissionRationale() {
        //        AppContext.showToast("请打开读取通讯录权限 in activity with listener");
    }

    @Override
    public void pageIntent(final Intent intent) {
        new AlertDialog.Builder(getContext())
                .setMessage(getResources().getString(R.string.read_camera_permission))
                .setPositiveButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }


    private void initRecyclerView() {
        //首页列表
        GridLayoutManager gridLayoutManager = new GridLayoutManager(curActivity, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new HomeListAdapter(curActivity, infoList);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //使用sort  变量  当数据   顺序变化时候就不能  准确响应
                int eNum = infoList.get(position).getInterfaceEnum();
                switch (eNum) {
                    case 0:
                        //一元购
                        if (AppContext.instance().isLogin()) {
                            AppContext.showToast(infoList.get(position).getName());
                            return;
                        }
                        UIHelper.showLogin(getContext());
                        break;
                    case 1:
                        //账单
                        if (AppContext.instance().isLogin()) {
                            UIHelper.showPersonalBills(getContext());
                        } else {
                            UIHelper.showLogin(getContext());
                        }
                        break;
                    case 2:
                        //我的商家
                        if (AppContext.instance().isLogin()) {
                            verifyMerchants(RequestCode.MY_MERCHANT);
                            return;

                        }
                        UIHelper.showLogin(getContext());
                        break;
                    case 3:
                        //对账
                        if (AppContext.instance().isLogin()) {
                            verifyMerchants(RequestCode.RECONCILIATION);
                            return;

                        }
                        UIHelper.showLogin(getContext());
                        break;
                    case 4:
                        //佣金
                        if (AppContext.instance().isLogin()) {
                            AppContext.showToast(infoList.get(position).getName());
                            return;
                        }
                        UIHelper.showLogin(getContext());
                        break;
                    case 5:
                        //更多期待
                        AppContext.showToast(R.string.tip_expect_more);
                        break;
                    default:
                        String name = mAdapter.getDatas().get(position).getName();
                        AppContext.showToast(name);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


        // 首页活动
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mActivityRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 将顶部视图与RecyclerView关联即可
        mHeader.attachTo(mActivityRecyclerView);

        mActivityAdapter = new HomeActivityAdapter(getContext(), infoListActivity);
        mActivityAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                HomeDataBean.BannerBean bannerBean = infoListActivity.get(position);
                String url = bannerBean.getImageUrl();
                String name = bannerBean.getName();

                UIHelper.showSingleWebView(getContext(), url, name);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void initMarqueeView() {
        List<CharSequence> list = new ArrayList<>();

        for (int i = 0; i < noticeList.size(); i++) {
            HomeDataBean.NoticeBean noticeBean = noticeList.get(i);
            SpannableString ss = new SpannableString(noticeBean.getTitle());
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.grey_666)), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            list.add(ss);

        }

        marqueeView.startWithList(list);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                HomeDataBean.NoticeBean noticeBean = noticeList.get(position);
                String linkUrl = noticeBean.getLinkUrl();
                String title = noticeBean.getTitle();
                UIHelper.showSingleWebView(getContext(), linkUrl, title);
            }
        });
    }

    private void showCustomDialog(String message, String positiveButton, final String negativeButton) {

        //创建dialog
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setMessage(message);

        builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (negativeButton.equals(getResources().getString(R.string.confirm))) {
                    UIHelper.showOpenMerchantServices(getContext());
                }
                dialog.dismiss();

            }
        });
        builder.create(R.layout.dialog_layout).show();
    }

    @Override
    public void onRefresh() {
        //设置2秒的时间来执行以下事件
        new Handler().postDelayed(new Runnable() {
            public void run() {
                infoList.clear();
                infoListActivity.clear();
                noticeList.clear();
                getBanner();//加载   广告  页
                // 关闭加载进度条
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Permissions4M.onRequestPermissionsResult(this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
