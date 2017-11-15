package com.shenyu.laikaword.model.bean.reponse;

import java.util.List;

/**
 * Created by shenyu_zxjCode on 2017/10/17 0017.
 * 消息
 */

public class MessageReponse extends BaseReponse{

    private List<PayloadBean> payload;

    public List<MessageReponse.PayloadBean> getPayload() {
        return payload;
    }

    public void setPayload(List<PayloadBean> payload) {
        this.payload = payload;
    }

    public static class PayloadBean {
        /**
         * messageId : 12345667890
         * content : 消息内容消息内容
         * createTime : 1508219443
         */

        private String messageId;
        private String content;
        private int createTime;

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }
    }
}
