package com.shenyu.laikaword.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class ReRequest{
    /**
     * success : false
     * error : {"code":505,"message":"sign error & real sign:013fc524f40d823ff66d2789adb47f92"}
     */

    @SerializedName("success")
    private boolean successX;
    private ErrorBean error;

    public boolean isSuccessX() {
        return successX;
    }

    public void setSuccessX(boolean successX) {
        this.successX = successX;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class ErrorBean {
        /**
         * code : 505
         * message : sign error & real sign:013fc524f40d823ff66d2789adb47f92
         */

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
//    public String[] payload;\

}
