package com.shenyu.laikaword.model.net.downloadmanager;

import com.shenyu.laikaword.model.rxjava.rxbus.RxBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Administrator on 2017/8/6 0006.
 */

public class FileResponseBody extends ResponseBody {
    private BufferedSource bufferedSource;
    ResponseBody  originalResponse;

    public FileResponseBody(ResponseBody originalResponse) {
        this.originalResponse = originalResponse;
    }

    @Override
    public MediaType contentType() {
        return originalResponse.contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.contentLength();
    }
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(originalResponse.source()));
        }
        return bufferedSource;
    }
    private Source source(Source source) {
        return new ForwardingSource(source) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                //实时发送当前已读取的字节和总字节
                RxBus.getDefault().post(new FileLoadingBean(contentLength(), bytesReaded));
                return bytesRead;
            }
        };

    }
}
