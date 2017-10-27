package com.shenyu.laikaword.model.bean.reponse;

import com.shenyu.laikaword.base.BaseReponse;

import java.io.Serializable;
import java.util.List;

import rx.exceptions.OnErrorNotImplementedException;

/**
 * Created by shenyu_zxjCode on 2017/9/29 0029.
 */

public class ShopMainReponse extends BaseReponse implements Serializable {


    /**
     * payload : {"banner":[{"imageUrl":"https://comingcard.com/resource/images/guess_banner1.jpg","link":"http://comingcard.com"},{"imageUrl":"https://comingcard.com/resource/images/guess_time.jpg","link":"http://comingcard.com"}],"notice":[{"text":"通知111111111","link":"http://comingcard.com"},{"text":"通知222222222","link":"http://comingcard.com"}],"goods":[{"type":"dx","name":"电信卡","weight":"1","list":[{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"}]}]}
     */

    private PayloadBean payload;


    public PayloadBean getPayload() {
        return payload;
    }

    public void setPayload(PayloadBean payload) {
        this.payload = payload;
    }


    public static class EntranceListBean implements Serializable {
        public EntranceListBean(String title,int imgUrl, String iconURL, String url, boolean dot) {
            this.title = title;
            this.iconURL = iconURL;
            this.url = url;
            this.dot = dot;
            this.imgUrl=imgUrl;
        }

        /**
         * title : 来卡竞猜
         * iconURL
         * url : https://www.baidu.com
         * dot : true
         */

        private String title;
        private String iconURL;
        private String url;
        private boolean dot;
        private int imgUrl;

        public int getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(int imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIconURL() {
            return iconURL;
        }

        public void setIconURL(String iconURL) {
            this.iconURL = iconURL;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isDot() {
            return dot;
        }

        public void setDot(boolean dot) {
            this.dot = dot;
        }
    }

    public  class PayloadBean implements Serializable{
        private List<BannerBean> banner;
        private List<NoticeBean> notice;
        private List<GoodsBean> goods;
        private List<EntranceListBean> entranceList;

        public List<EntranceListBean> getEntranceList() {
            return entranceList;
        }

        public void setEntranceList(List<EntranceListBean> entranceList) {
            this.entranceList = entranceList;
        }
        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<NoticeBean> getNotice() {
            return notice;
        }

        public void setNotice(List<NoticeBean> notice) {
            this.notice = notice;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public  class BannerBean implements Serializable{
            /**
             * imageUrl : https://comingcard.com/resource/images/guess_banner1.jpg
             * link : http://comingcard.com
             */

            private String imageUrl;
            private String link;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public  class NoticeBean implements Serializable {
            /**
             * text : 通知111111111
             * link : http://comingcard.com
             */

            private String text;
            private String link;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public  class GoodsBean extends OnErrorNotImplementedException implements Serializable {
            /**
             * type : dx
             * name : 电信卡
             * weight : 1
             * list : [{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"},{"goodsId":"1","goodsName":"测试商品","goodsImage":"https://t.comingcard.com/resource/images/yd-100.jpg","discount":"9.5","nickName":"卖家昵称","originPrice":"100","discountPrice":"95","stock":"100"}]
             */

            private String type;
            private String name;
            private String weight;
            private List<GoodBean> list;

            public GoodsBean(String message, Throwable e) {
                super(message, e);
            }

            public GoodsBean(Throwable e) {
                super(e);
            }


            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public List<GoodBean> getList() {
                return list;
            }

            public void setList(List<GoodBean> list) {
                this.list = list;
            }


        }}
}
