package com.shenyu.laikaword.model.bean.reponse;

import com.shenyu.laikaword.base.BaseReponse;

/**
 * Created by shenyu_zxjCode on 2017/10/12 0012.
 */

public class ImgSTSReponse extends BaseReponse {

    /**
     * payload : {"AccessKeySecret":"HETVJFUWvsc35igfuafDUSLnMYmLBybvnKGBmxUQRr2d","AccessKeyId":"STS.LbcGheBWffZjGHbk4oz4FiycF","Expiration":"2017-10-12T09:28:07Z","SecurityToken":"CAIS+QF1q6Ft5B2yfSjIraHWDNLRr4hH0ZiBRW7TjzQ6drtqhrzIpDz2IHtEeHNqAOsctPowmmhX5/YTlqBac6duQU3Ja9coW1aZZpviMeT7oMWQweEuqv/MQBq+aXPS2MvVfJ+KLrf0ceusbFbpjzJ6xaCAGxypQ12iN+/i6/clFKN1ODO1dj1bHtxbCxJ/ocsBTxvrOO2qLwThjxi7biMqmHIl1zImtfXnkpHEtUeP0Aeg8IJP+dSteKrDRtJ3IZJyX+2y2OFLbafb2EZSkUMRpPss1vUfo22Z4Y/FXAgBsg//NPHP78ZiNgZybaUhALRDqPXsAw3BZ2p3f+8agAGWlF0ldIRdpbM7kR6MMo5Mv24IfnAU9wK8DlqIPyqKBgeI5AsexxnKd9KA1lD/KcyPLfl0B37OrR5kWX4ioMWRwFl+saeFoddtqSIroYtXgKFGprfk5Vo3DYK4nKnHbMVxT6qIjKFrQxZvvKA7GicsX8uE28FvGzgb1opWAJjFQQ==","bucketName":"app-upload-img-test"}
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
         * AccessKeySecret : HETVJFUWvsc35igfuafDUSLnMYmLBybvnKGBmxUQRr2d
         * AccessKeyId : STS.LbcGheBWffZjGHbk4oz4FiycF
         * Expiration : 2017-10-12T09:28:07Z
         * SecurityToken : CAIS+QF1q6Ft5B2yfSjIraHWDNLRr4hH0ZiBRW7TjzQ6drtqhrzIpDz2IHtEeHNqAOsctPowmmhX5/YTlqBac6duQU3Ja9coW1aZZpviMeT7oMWQweEuqv/MQBq+aXPS2MvVfJ+KLrf0ceusbFbpjzJ6xaCAGxypQ12iN+/i6/clFKN1ODO1dj1bHtxbCxJ/ocsBTxvrOO2qLwThjxi7biMqmHIl1zImtfXnkpHEtUeP0Aeg8IJP+dSteKrDRtJ3IZJyX+2y2OFLbafb2EZSkUMRpPss1vUfo22Z4Y/FXAgBsg//NPHP78ZiNgZybaUhALRDqPXsAw3BZ2p3f+8agAGWlF0ldIRdpbM7kR6MMo5Mv24IfnAU9wK8DlqIPyqKBgeI5AsexxnKd9KA1lD/KcyPLfl0B37OrR5kWX4ioMWRwFl+saeFoddtqSIroYtXgKFGprfk5Vo3DYK4nKnHbMVxT6qIjKFrQxZvvKA7GicsX8uE28FvGzgb1opWAJjFQQ==
         * bucketName : app-upload-img-test
         */

        private String AccessKeySecret;
        private String AccessKeyId;
        private String Expiration;
        private String SecurityToken;
        private String bucketName;
        private String endPoint;

        public String getEndPoint() {
            return endPoint;
        }

        public void setEndPoint(String endPoint) {
            this.endPoint = endPoint;
        }

        public String getAccessKeySecret() {
            return AccessKeySecret;
        }

        public void setAccessKeySecret(String AccessKeySecret) {
            this.AccessKeySecret = AccessKeySecret;
        }

        public String getAccessKeyId() {
            return AccessKeyId;
        }

        public void setAccessKeyId(String AccessKeyId) {
            this.AccessKeyId = AccessKeyId;
        }

        public String getExpiration() {
            return Expiration;
        }

        public void setExpiration(String Expiration) {
            this.Expiration = Expiration;
        }

        public String getSecurityToken() {
            return SecurityToken;
        }

        public void setSecurityToken(String SecurityToken) {
            this.SecurityToken = SecurityToken;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }
    }
}
