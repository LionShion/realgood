package com.wenjing.yinfutong.common;


import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.function.home.fragment.AccountBanalceFragment;
import com.wenjing.yinfutong.function.home.fragment.CashOutFragment;
import com.wenjing.yinfutong.function.home.fragment.CollectionRecordFragment;
import com.wenjing.yinfutong.function.home.fragment.GatheringFragment;
import com.wenjing.yinfutong.function.home.fragment.HistoryBillFragment;
import com.wenjing.yinfutong.function.home.fragment.MerchantBillsListFragment;
import com.wenjing.yinfutong.function.home.fragment.MerchantBillsDetailsFragment;
import com.wenjing.yinfutong.function.home.fragment.NewsCenterFragment;
import com.wenjing.yinfutong.function.home.fragment.OpenMerchantFragment;
import com.wenjing.yinfutong.function.home.fragment.PaymentFragment;
import com.wenjing.yinfutong.function.home.fragment.PersonalBillsFragment;
import com.wenjing.yinfutong.function.home.fragment.PersonalBillsPayDetailsFragment;
import com.wenjing.yinfutong.function.home.fragment.PersonalBillsRechargeDetailsFragment;
import com.wenjing.yinfutong.function.home.fragment.PersonalBillsWithdrawDetailsFragment;
import com.wenjing.yinfutong.function.home.fragment.PlatformAnnouncementFragment;
import com.wenjing.yinfutong.function.home.fragment.RechargeFragment;
import com.wenjing.yinfutong.function.home.fragment.RechargeOnlineFragment;
import com.wenjing.yinfutong.function.home.fragment.RechargeSuccFragment;
import com.wenjing.yinfutong.function.home.fragment.TradeInformationFragment;
import com.wenjing.yinfutong.function.home.fragment.TwoDimensionalCodeFragment;
import com.wenjing.yinfutong.function.mine.fragment.ApplicationCommitedFragment;
import com.wenjing.yinfutong.function.mine.fragment.BankCardFragment;
import com.wenjing.yinfutong.function.mine.fragment.BindBankCardFragment;
import com.wenjing.yinfutong.function.mine.fragment.CityClassFragment;
import com.wenjing.yinfutong.function.mine.fragment.CommonpbDetailsFragment;
import com.wenjing.yinfutong.function.mine.fragment.CustomerServiceFragment;
import com.wenjing.yinfutong.function.mine.fragment.DepositBankFragment;
import com.wenjing.yinfutong.function.mine.fragment.LoginFragment;
import com.wenjing.yinfutong.function.mine.fragment.MerchantServiceFragment;
import com.wenjing.yinfutong.function.mine.fragment.NicknameFragment;
import com.wenjing.yinfutong.function.mine.fragment.OpenMerchantRefuseFragment;
import com.wenjing.yinfutong.function.mine.fragment.PaySuccFragment;
import com.wenjing.yinfutong.function.mine.fragment.PersonalInfoFragment;
import com.wenjing.yinfutong.function.mine.fragment.ProvinceClassFragment;
import com.wenjing.yinfutong.function.mine.fragment.RegionFragment;
import com.wenjing.yinfutong.function.mine.fragment.RegisterFragment;
import com.wenjing.yinfutong.function.mine.fragment.ResetLoginPsdFragment;
import com.wenjing.yinfutong.function.mine.fragment.ResetPaymentPsdFragment;
import com.wenjing.yinfutong.function.mine.fragment.RetrievePsdFragment;
import com.wenjing.yinfutong.function.mine.fragment.RetrievePsdSecondFragment;
import com.wenjing.yinfutong.function.mine.fragment.RetrievepaymentPsdFragment;
import com.wenjing.yinfutong.function.mine.fragment.SafetyManagerFragment;
import com.wenjing.yinfutong.function.mine.fragment.SetPaymentPsdFragment;
import com.wenjing.yinfutong.function.mine.fragment.SetPsdFragment;
import com.wenjing.yinfutong.function.mine.fragment.SwitchLanguageFragment;
import com.wenjing.yinfutong.function.mine.fragment.TieEmailFragment;
import com.wenjing.yinfutong.function.mine.fragment.TiePhoneFragment;
import com.wenjing.yinfutong.function.mine.fragment.ToRechargeFragment;
import com.wenjing.yinfutong.function.mine.fragment.VoicePromptFragment;
import com.wenjing.yinfutong.function.mine.fragment.WithdrawWaitFragment;

public enum SimpleBackPage {

    TWODIMENSIONALCODE(0, R.string.payment_code, TwoDimensionalCodeFragment.class),
    PAYMENT(1, R.string.payment, PaymentFragment.class),
    REGISTER(2, R.string.register, RegisterFragment.class),
    RETRIEVE_PSD(3, R.string.retrieve_psd, RetrievePsdFragment.class),
    LOGIN(4, R.string.login, LoginFragment.class),
    PERSONAL_INFO(5, R.string.personal_info, PersonalInfoFragment.class),
    NICKNAME(6, R.string.nickname, NicknameFragment.class),
    TIED_PHONENUM(7, R.string.tied_phonenum, TiePhoneFragment.class),
    TIED_EMAIL(8, R.string.tied_email, TieEmailFragment.class),
    MERCHANT_SERVICE(9, R.string.merchant_services, MerchantServiceFragment.class),
    VOICE_PROMPT(10, R.string.voice_prompt, VoicePromptFragment.class),
    RETRIEVE_PSD_SECOND(11, R.string.retrieve_psd, RetrievePsdSecondFragment.class),
    SETPSD(12, R.string.set_psd, SetPsdFragment.class),
    REGION(13, R.string.region, RegionFragment.class),
    SWITCH_LANGUAGE(14, R.string.language_selection, SwitchLanguageFragment.class),
    SAFETY_MANAGER(15, R.string.safety_management, SafetyManagerFragment.class),
    RESET_LOGIN_PSD(16, R.string.reset_login_psd, ResetLoginPsdFragment.class),
    RESET_PAYMENT_PSD(17, R.string.reset_payment_psd, ResetPaymentPsdFragment.class),
    APPLICATION_COMMITED(18, R.string.commited, ApplicationCommitedFragment.class),
    BANKCARD(19, R.string.bank_card, BankCardFragment.class),
    TIE_BANKCARD(20, R.string.tie_bankcard, BindBankCardFragment.class),
    DEPOSITBANK(21, R.string.select_depositbank, DepositBankFragment.class),
    RETRIEVE_PAYMENT_PSD(22,R.string.retrieve_psd, RetrievepaymentPsdFragment.class),
    SELECT_MERCHANT_REGION(23,R.string.select_region, ProvinceClassFragment.class),
    SELECT_MERCHANT_CITY(24,R.string.select_region, CityClassFragment.class),
    SET_PAYMENTPSD(25,R.string.set_payment_psd, SetPaymentPsdFragment.class),
    CUTOMER_SERVICE(26,R.string.customer_service, CustomerServiceFragment.class),
    COMMONPB_DETAILS(27,R.string.common_problem, CommonpbDetailsFragment.class),






    ACCOUNT_BALANCE(100, R.string.account_balance, AccountBanalceFragment.class),
    RECHARGE(101, R.string.recharge, RechargeFragment.class),
    COLLECTION_RECORD(102, R.string.collection_record, CollectionRecordFragment.class),
    NEWS_CENTER(103, R.string.news_center, NewsCenterFragment.class),
    PLATFORM_ANNOUNCEMENT(104, R.string.platform_announcement, PlatformAnnouncementFragment.class),
    TRADE_INFORMATION(105, R.string.trade_information, TradeInformationFragment.class),
    OPEN_MERCHANTSERVICE(106, R.string.merchant_services, OpenMerchantFragment.class),
    HISTORY_BILL(107, R.string.history_bill, HistoryBillFragment.class),
    PERSONAL_BILLS(108, R.string.bill, PersonalBillsFragment.class),
    PERSONAL_BILLS_RECHARGE_DETAILS(109, R.string.bill_detalis, PersonalBillsRechargeDetailsFragment.class),
    MERCHANTBILLSLIST(110, R.string.home, MerchantBillsListFragment.class),
    RECHARGE_ONLINE(111, R.string.recharge, RechargeOnlineFragment.class),
    CASH_OUT(112, R.string.cash_out, CashOutFragment.class),
    RECHARGE_SUCC(113, R.string.order_result, RechargeSuccFragment.class),
    PERSONAL_BILLS_PAY_DETAILS(114, R.string.bill_detalis, PersonalBillsPayDetailsFragment.class),
    PERSONAL_BILLS_WITHDRAW_DETAILS(115, R.string.bill_detalis, PersonalBillsWithdrawDetailsFragment.class),
    WITHDRAW_WAIT(116, R.string.commit, WithdrawWaitFragment.class),
    PAY_SUCC(117, R.string.pay_succ, PaySuccFragment.class),
    DEFEATED(118, R.string.fail, OpenMerchantRefuseFragment.class),
    GATHERING(119, R.string.gathering, GatheringFragment.class),
    TO_RECHARGE(120 , R.string.to_recharge , ToRechargeFragment.class),
    MERCHANT_BILL_DETAILS(121 , R.string.bill_detalis , MerchantBillsDetailsFragment.class),





    SINGLEWEBVIEW(200, R.string.safety_ensure, SingleWebViewFragment.class);


    public static int BASE_TOOLBAR_YES = 0;//使用
    public static int BASE_TOOLBAR_NO = 1;
    private int title;
    private Class<?> clz;
    private int value;

    //枚举类型构造方法只能是私有的
    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static SimpleBackPage getPageByValue(int val) {
        //values()表示得到全部的枚举内容，然后用对象数组的形式用forEach输出
        for (SimpleBackPage p : values()) {
            if (p.getValue() == val)
                return p;
        }
        return null;
    }

}
