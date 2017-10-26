package com.shenyu.laikaword.model.net.api;

/**
 * Created by shenyu_zxjCode on 2017/10/19 0019.
 */

public class ApiModel<T> {


    private boolean success;
    private ErrorBean error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }


    public static class ErrorBean {
        /**
         * code : 701
         * message : 验证码错误
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

    public T data;

}
