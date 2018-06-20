package com.wenjing.yinfutong.model;

/**
 * Created by wenjing on 2018/3/24.
 */

public class IfMerchantBean {

    /**
     * msg : success
     * code : 0
     * data : {"id":8,"name":"吴祖新","cellphone":"15264781384","title":"华润万家"}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 8
         * name : 吴祖新
         * cellphone : 15264781384
         * title : 华润万家
         */

        private int id;
        private String name;
        private String cellphone;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCellphone() {
            return cellphone;
        }

        public void setCellphone(String cellphone) {
            this.cellphone = cellphone;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
