package com.shenyu.laikaword.model.web;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.provider.MediaStore;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.common.Constants;
import com.shenyu.laikaword.model.bean.reponse.ShopMainReponse;
import com.shenyu.laikaword.module.launch.LaiKaApplication;
import com.shenyu.laikaword.ui.view.widget.ProgressWebView;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zxj.utilslibrary.utils.FileStorageUtil;
import com.zxj.utilslibrary.utils.LogUtil;
import com.zxj.utilslibrary.utils.SPUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shenyu.laikaword.common.Constants.REQUEST_IMAGE_CAPTURE;

public class GuessActivity extends LKWordBaseActivity implements ReWebChomeClient.OpenFileChooserCallBack {
    @BindView(R.id.wb_load)
    ProgressWebView wbLoad;
    String webURL;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private ValueCallback<Uri> mUploadMsg;
    public ValueCallback<Uri[]> mUploadMsgForAndroid5;
    private Intent mSourceIntent;
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    private static final int P_CODE_PERMISSIONS = 101;
    @Override
    public int bindLayout() {
        return R.layout.activity_guess;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void initView() {
        initWebView();
        webURL = getIntent().getStringExtra("weburl");
        wbLoad.loadUrl(webURL);
        mToolbarSubTitle.setBackground(UIUtil.getDrawable(R.mipmap.refresh_icon));
        mToolbarSubTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wbLoad.reload();
            }
        });


    }


    @SuppressLint("NewApi")
    private void initWebView() {
        wbLoad.getSettings().setTextZoom(100);

        WebSettings webSettings = wbLoad.getSettings();
        webSettings.setUserAgentString(webSettings.getUserAgentString()+";laikashopapp");


        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLoadWithOverviewMode(true);
       webSettings.setUseWideViewPort(true); //????????????????????????webview?????????
//       webSettings.setLoadWithOverviewMode(true); // ????????????????????????
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        // enable navigator.geolocation????????
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        webSettings.setDomStorageEnabled(true);
        webSettings.setDisplayZoomControls(false); //???????????????????????????
        // ?????????Js???????????????
        webSettings.setJavaScriptEnabled(true);
        // ????????????JS??????
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wbLoad.setWebChromeClient(new ReWebChomeClient(this){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbarTitle.setText(title);
            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    public void doBusiness(Context context) {
        wbLoad.requestFocus();
        wbLoad.setScrollBarStyle(0);

        wbLoad.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                ShopMainReponse shopMainReponse= (ShopMainReponse) SPUtil.readObject(Constants.MAIN_SHOP_KEY);
                for (String ux:shopMainReponse.getPayload().getExternalURLs()){
                    if (url.contains(ux)) {
                        Intent mIntent =new Intent();
                        mIntent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(url);
                        mIntent.setData(content_url);
                        startActivity(mIntent);
                        return true;
                    }
                }





//                }
                return super.shouldOverrideUrlLoading(view, url);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });

    }

    @Override
    public void setupActivityComponent() {

    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        wbLoad.onResume();
//        wbLoad.pauseTimers();
//        MobclickAgent.onPageStart("GuessActivity");
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        super.onPause();
        wbLoad.onPause();
//        wbLoad.resumeTimers();
//        MobclickAgent.onPageEnd("GuessActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wbLoad != null) {
            wbLoad.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wbLoad.clearHistory();
            ((ViewGroup) wbLoad.getParent()).removeView(wbLoad);
            wbLoad.destroy();
        }
    }

    //????????????????????????????????????????????????
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wbLoad.canGoBack()) {
            wbLoad.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.toolbar_left)
    public void onViewClicked() {
        if(wbLoad.canGoBack()){
            wbLoad.goBack(); // goBack()????????????WebV
        }else {
            finish();
        }
    }


    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMsg = uploadMsg;
        showOptions();
    }

    @Override
    public boolean openFileChooserCallBackAndroid5(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        mUploadMsgForAndroid5 = filePathCallback;
        showOptions();
        return  true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            if (mUploadMsg != null) {
                mUploadMsg.onReceiveValue(null);
            }

            if (mUploadMsgForAndroid5 != null) {         // for android 5.0+
                mUploadMsgForAndroid5.onReceiveValue(null);
            }
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_IMAGE_CAPTURE:
                try {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        if (mUploadMsg == null) {
                            return;
                        }

                        String sourcePath = mCurrentPhotoPath;

                        if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {

                            break;
                        }
                        Uri uri = Uri.fromFile(new File(sourcePath));
                        mUploadMsg.onReceiveValue(uri);

                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (mUploadMsgForAndroid5 == null) {        // for android 5.0+
                            return;
                        }

                        String sourcePath = mCurrentPhotoPath;

                        if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {

                            break;
                        }
                        Uri uri = Uri.fromFile(new File(sourcePath));
                        mUploadMsgForAndroid5.onReceiveValue(new Uri[]{uri});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case REQUEST_CODE_PICK_IMAGE: {
                try {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        if (mUploadMsg == null) {
                            return;
                        }

                        String sourcePath = ImageUtil.retrievePath(this, mSourceIntent, data);

                        if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {

                            break;
                        }
                        Uri uri = Uri.fromFile(new File(sourcePath));
                        mUploadMsg.onReceiveValue(uri);

                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (mUploadMsgForAndroid5 == null) {        // for android 5.0+
                            return;
                        }

                        String sourcePath = ImageUtil.retrievePath(this, mSourceIntent, data);

                        if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {

                            break;
                        }
                        Uri uri = Uri.fromFile(new File(sourcePath));
                        mUploadMsgForAndroid5.onReceiveValue(new Uri[]{uri});
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void showOptions() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setOnCancelListener(new DialogOnCancelListener());
        alertDialog.setTitle("???????????????");
        String[] options = {"??????", "??????"};
        alertDialog.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if (PermissionUtil.isOverMarshmallow()) {
                                if (!PermissionUtil.isPermissionValid(GuessActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                    Toast.makeText(GuessActivity.this,
                                            "??????\"??????\"?????????????????????????????????????????????",
                                            Toast.LENGTH_SHORT).show();

                                    restoreUploadMsg();
                                    requestPermissionsAndroidM();
                                    return;
                                }

                            }

                            try {
                                mSourceIntent = ImageUtil.choosePicture();
                                startActivityForResult(mSourceIntent, REQUEST_CODE_PICK_IMAGE);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(GuessActivity.this,
                                        "??????\"??????\"?????????????????????????????????????????????",
                                        Toast.LENGTH_SHORT).show();
                                restoreUploadMsg();
                            }

                        } else {
                            if (PermissionUtil.isOverMarshmallow()) {
                                if (!PermissionUtil.isPermissionValid(GuessActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    Toast.makeText(GuessActivity.this,
                                            "??????\"??????\"?????????????????????????????????????????????",
                                            Toast.LENGTH_SHORT).show();

                                    restoreUploadMsg();
                                    requestPermissionsAndroidM();
                                    return;
                                }

                                if (!PermissionUtil.isPermissionValid(GuessActivity.this, Manifest.permission.CAMERA)) {
                                    Toast.makeText(GuessActivity.this,
                                            "??????\"??????\"?????????????????????????????????",
                                            Toast.LENGTH_SHORT).show();

                                    restoreUploadMsg();
                                    requestPermissionsAndroidM();
                                    return;
                                }
                            }
                            dispatchTakePictureIntent();
                        }
                    }
                }
        ).show();
    }

    private void restoreUploadMsg() {
        if (mUploadMsg != null) {
            mUploadMsg.onReceiveValue(null);
            mUploadMsg = null;

        } else if (mUploadMsgForAndroid5 != null) {
            mUploadMsgForAndroid5.onReceiveValue(null);
            mUploadMsgForAndroid5 = null;
        }
    }


    private void requestPermissionsAndroidM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> needPermissionList = new ArrayList<>();
            needPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            needPermissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            needPermissionList.add(Manifest.permission.CAMERA);
            PermissionUtil.requestPermissions(GuessActivity.this, P_CODE_PERMISSIONS, needPermissionList);

        } else {
            return;
        }
    }
    private class DialogOnCancelListener implements DialogInterface.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            restoreUploadMsg();
        }
    }
private  String mCurrentPhotoPath;
    /**
     * ??????????????????
     */
    public void dispatchTakePictureIntent() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //???????????????????????????????????????????????????Uri??????????????????
        }
        // ?????????????????????????????????Intent???Activity
        if (intentCamera.resolveActivity(getPackageManager()) != null) {
            // ?????????????????????????????????
            File photoFile = null;
            try {
                photoFile = FileStorageUtil.createImageFile();
                mCurrentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // ????????????
                ToastUtil.showToastShort(ex.getMessage());
            }
            if (photoFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                   Uri imageUri   = FileProvider.getUriForFile(this, "com.shenyu.laikaword.fileprovider", photoFile);//??????FileProvider????????????content?????????Uri
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                } else {
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                }
              startActivityForResult(intentCamera, REQUEST_CODE_IMAGE_CAPTURE);
            }
        } else {
            ToastUtil.showToastLong("??????????????????");
        }
    }
}
