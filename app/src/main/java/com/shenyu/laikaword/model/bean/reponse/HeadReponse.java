package com.shenyu.laikaword.model.bean.reponse;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class HeadReponse extends BaseReponse {
    private String message;
    private int status;
    private String url;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
