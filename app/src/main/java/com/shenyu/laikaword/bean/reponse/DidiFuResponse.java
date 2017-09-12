package com.shenyu.laikaword.bean.reponse;

import com.shenyu.laikaword.bean.BaseResponse;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class DidiFuResponse extends BaseResponse {

    private int code;
    private String message;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public  class ResultBean {
        private int id;
        private String bindName;
        private String jumpurl;
        private int deviceType;
        private int bind;
        private List<ListBean> list;
        private List<ImgListBean> imgList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBindName() {
            return bindName;
        }

        public void setBindName(String bindName) {
            this.bindName = bindName;
        }

        public String getJumpurl() {
            return jumpurl;
        }

        public void setJumpurl(String jumpurl) {
            this.jumpurl = jumpurl;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public int getBind() {
            return bind;
        }

        public void setBind(int bind) {
            this.bind = bind;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<ImgListBean> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImgListBean> imgList) {
            this.imgList = imgList;
        }

        public  class ListBean {
            private String function;
            private int functionId;
            private int status;
            private int useStatus;
            private String icon;
            private String unuseIcon;
            private String url;

            public String getFunction() {
                return function;
            }

            public void setFunction(String function) {
                this.function = function;
            }

            public int getFunctionId() {
                return functionId;
            }

            public void setFunctionId(int functionId) {
                this.functionId = functionId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getUseStatus() {
                return useStatus;
            }

            public void setUseStatus(int useStatus) {
                this.useStatus = useStatus;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getUnuseIcon() {
                return unuseIcon;
            }

            public void setUnuseIcon(String unuseIcon) {
                this.unuseIcon = unuseIcon;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public  class ImgListBean {
            private String url;
            private String linkUrl;
            private String proportion;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getLinkUrl() {
                return linkUrl;
            }

            public void setLinkUrl(String linkUrl) {
                this.linkUrl = linkUrl;
            }

            public String getProportion() {
                return proportion;
            }

            public void setProportion(String proportion) {
                this.proportion = proportion;
            }
        }
    }
}
