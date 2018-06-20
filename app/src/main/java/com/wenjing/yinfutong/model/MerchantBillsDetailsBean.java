package com.wenjing.yinfutong.model;

/**
 * Created by wenjing on 2018/3/24.
 */

public class MerchantBillsDetailsBean {

    /**
     * code : 0
     * msg : success
     * data : {"payerName":"付款人姓名","receiveTime":"付款时间","receiveAmount":"付款金额","orderNo":"订单号","payChannel":"支付渠道：1系统余额;2微信;3支付宝","status":"订单状态","bonusAmount":"折扣金额"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * payerName : 付款人姓名
         * receiveTime : 付款时间
         * receiveAmount : 付款金额
         * orderNo : 订单号
         * payChannel : 支付渠道：1系统余额;2微信;3支付宝
         * status : 订单状态
         * bonusAmount : 折扣金额
         */

        private String payerName;
        private String receiveTime;
        private String receiveAmount;
        private String orderNo;
        private String payChannel;
        private String status;
        private String bonusAmount;

        public String getPayerName() {
            return payerName;
        }

        public void setPayerName(String payerName) {
            this.payerName = payerName;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getReceiveAmount() {
            return receiveAmount;
        }

        public void setReceiveAmount(String receiveAmount) {
            this.receiveAmount = receiveAmount;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPayChannel() {
            return payChannel;
        }

        public void setPayChannel(String payChannel) {
            this.payChannel = payChannel;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBonusAmount() {
            return bonusAmount;
        }

        public void setBonusAmount(String bonusAmount) {
            this.bonusAmount = bonusAmount;
        }
    }
}
