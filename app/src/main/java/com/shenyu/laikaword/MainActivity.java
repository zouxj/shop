package com.shenyu.laikaword;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.bean.BaseReponse;
import com.shenyu.laikaword.bean.reponse.DidiFuResponse;
import com.shenyu.laikaword.bean.reponse.HeadReponse;
import com.shenyu.laikaword.http.NetWorks;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import rx.Observer;

public class MainActivity extends LKWordBaseActivity {

    @BindView(R.id.id_text)
    TextView idText;


    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void doBusiness(Context context) {
//        NetWorks.Getcache(new Observer<DidiFuResponse>() {
//            @Override
//            public void onNext(DidiFuResponse value) {
//                idText.setText("来卡世界..."+value.getMessage());
//            }
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                idText.setText("来卡世界..."+e.toString());
//            }
//
//
//        });
//        String path1 = Environment.getExternalStorageDirectory() + File.separator + "test.png";
//        String path2 = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
//        ArrayList<String> pathList = new ArrayList<>();
//        pathList.add(path1);
//        pathList.add(path2);
//
//        Map<String, RequestBody> bodyMap = new HashMap<>();
//        if(pathList.size() > 0) {
//            for (int i = 0; i < pathList.size(); i++) {
//                File file = new File(pathList.get(i));
//                bodyMap.put("file"+i+"\"; filename=\""+file.getName(), RequestBody.create(MediaType.parse("image/png"),file));
//            }
//        }
        Map<String, RequestBody> bodyMapImg = new HashMap<>();
        Map<String, String> bodyMapText = new HashMap<>();
        bodyMapText.put("","");
        bodyMapText.put("","");
        bodyMapText.put("","");
        bodyMapText.put("","");
        bodyMapText.put("","");

        NetWorks.uploadMultipleTypeFile(bodyMapText, bodyMapImg, new Observer<HeadReponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HeadReponse headReponse) {

            }
        });

    }

}
