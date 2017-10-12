package com.shenyu.laikaword.retrofit.callback;

import com.shenyu.laikaword.http.downloadmanager.FileLoadingBean;
import com.shenyu.laikaword.rxbus.RxBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Created by shenyu_zxjCode on 2017/10/11 0011.
 * 文件接收类
 */

public class FileResponseBody extends ResponseBody {
    Response originalResponse;

    public FileResponseBody(Response originalResponse) {
        this.originalResponse = originalResponse;
    }
    @Override

    public MediaType contentType() {
        return originalResponse.body().contentType();

    }



    @Override

    public long contentLength() {
        return originalResponse.body().contentLength();

    }



    @Override

    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
            long bytesReaded = 0;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                RxBus.getDefault().post(new FileLoadingBean(contentLength(), bytesReaded));
                return bytesRead;

            }

        });

    }
}
