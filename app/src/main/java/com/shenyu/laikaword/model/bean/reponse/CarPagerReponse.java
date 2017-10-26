package com.shenyu.laikaword.model.bean.reponse;

import com.shenyu.laikaword.base.BaseReponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/20 0020.
 */

public class CarPagerReponse extends BaseReponse {

    /**
     * payload : {"yd":{"name":"移动卡","list":[]},"dx":{"name":"电信卡","list":[]},"lt":{"name":"联通卡","list":[]},"jd":{"name":"京东卡","list":[{"packageId":"8","userId":"43","goodsName":"goods1","goodsImage":"http://image.comingcard.com/goods/yd100.png","goodsType":"jd","goodsValue":"100.00","quantity":"28","createTime":"1508478500","imageUrl":null}]}}
     */

    private PayloadBean payload;

    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * yd : {"name":"移动卡","list":[]}
         * dx : {"name":"电信卡","list":[]}
         * lt : {"name":"联通卡","list":[]}
         * jd : {"name":"京东卡","list":[{"packageId":"8","userId":"43","goodsName":"goods1","goodsImage":"http://image.comingcard.com/goods/yd100.png","goodsType":"jd","goodsValue":"100.00","quantity":"28","createTime":"1508478500","imageUrl":null}]}
         */

        private YdBean yd;
        private DxBean dx;
        private LtBean lt;
        private JdBean jd;

        public YdBean getYd() {
            return yd;
        }

        public void setYd(YdBean yd) {
            this.yd = yd;
        }

        public DxBean getDx() {
            return dx;
        }

        public void setDx(DxBean dx) {
            this.dx = dx;
        }

        public LtBean getLt() {
            return lt;
        }

        public void setLt(LtBean lt) {
            this.lt = lt;
        }

        public JdBean getJd() {
            return jd;
        }

        public void setJd(JdBean jd) {
            this.jd = jd;
        }
    }

    public static class YdBean {
        /**
         * name : 移动卡
         * list : []
         */

        private String name;
        private List<Bean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Bean> getList() {
            return list;
        }

        public void setList(List<Bean> list) {
            this.list = list;
        }
    }

    public static class DxBean {
        /**
         * name : 电信卡
         * list : []
         */

        private String name;
        private List<Bean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Bean> getList() {
            return list;
        }

        public void setList(List<Bean> list) {
            this.list = list;
        }
    }

    public static class LtBean {
        /**
         * name : 联通卡
         * list : []
         */

        private String name;
        private List<Bean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Bean> getList() {
            return list;
        }

        public void setList(List<Bean> list) {
            this.list = list;
        }
    }

    public static class JdBean {
        /**
         * name : 京东卡
         * list : [{"packageId":"8","userId":"43","goodsName":"goods1","goodsImage":"http://image.comingcard.com/goods/yd100.png","goodsType":"jd","goodsValue":"100.00","quantity":"28","createTime":"1508478500","imageUrl":null}]
         */

        private String name;
        private List<Bean> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Bean> getList() {
            return list;
        }

        public void setList(List<Bean> list) {
            this.list = list;
        }
    }
    public  class Bean implements Serializable {

        /**
         * packageId : 8
         * userId : 43
         * goodsName : goods1
         * goodsImage : http://image.comingcard.com/goods/yd100.png
         * goodsType : jd
         * goodsValue : 100.00
         * quantity : 28
         * createTime : 1508478500
         * imageUrl : null
         */

        private String packageId;
        private String userId;
        private String goodsName;
        private String goodsImage;
        private String goodsType;
        private String goodsValue;
        private String quantity;
        private String createTime;
        private Object imageUrl;

        public String getPackageId() {
            return packageId;
        }

        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public String getGoodsValue() {
            return goodsValue;
        }

        public void setGoodsValue(String goodsValue) {
            this.goodsValue = goodsValue;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(Object imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
