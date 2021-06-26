package com.shenyu.laikaword.model.web;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.shenyu.laikaword.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MIActivity extends AppCompatActivity implements ReWebChomeClient.OpenFileChooserCallBack {

    private WebView webView;
    private static final int REQUEST_CODE_PICK_IMAGE = 0;
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    private Intent mSourceIntent;
    public ValueCallback<Uri[]> mUploadMsgForAndroid5;
    private ValueCallback<Uri> mUploadMsg;
    private static final int P_CODE_PERMISSIONS = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi);
        webView= (WebView) findViewById(R.id.wb_load);
        webView.loadUrl(getIntent().getStringExtra("weburl"));
        fixDirPath();
        webView.setWebViewClient(new ReWebViewClient());
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new ReWebChomeClient(this));
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        alertDialog.setTitle("请选择操作");
        String[] options = {"相册", "拍照"};
        alertDialog.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if (PermissionUtil.isOverMarshmallow()) {
                                if (!PermissionUtil.isPermissionValid(MIActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                                    Toast.makeText(MIActivity.this,
                                            "请去\"设置\"中开启本应用的图片媒体访问权限",
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
                                Toast.makeText(MIActivity.this,
                                        "请去\"设置\"中开启本应用的图片媒体访问权限",
                                        Toast.LENGTH_SHORT).show();
                                restoreUploadMsg();
                            }

                        } else {
                            if (PermissionUtil.isOverMarshmallow()) {
                                if (!PermissionUtil.isPermissionValid(MIActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    Toast.makeText(MIActivity.this,
                                            "请去\"设置\"中开启本应用的图片媒体访问权限",
                                            Toast.LENGTH_SHORT).show();

                                    restoreUploadMsg();
                                    requestPermissionsAndroidM();
                                    return;
                                }

                                if (!PermissionUtil.isPermissionValid(MIActivity.this, Manifest.permission.CAMERA)) {
                                    Toast.makeText(MIActivity.this,
                                            "请去\"设置\"中开启本应用的相机权限",
                                            Toast.LENGTH_SHORT).show();

                                    restoreUploadMsg();
                                    requestPermissionsAndroidM();
                                    return;
                                }
                            }

                            try {
                                mSourceIntent = ImageUtil.takeBigPicture();
                                startActivityForResult(mSourceIntent, REQUEST_CODE_IMAGE_CAPTURE);

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MIActivity.this,
                                        "请去\"设置\"中开启本应用的相机和图片媒体访问权限",
                                        Toast.LENGTH_SHORT).show();

                                restoreUploadMsg();
                            }
                        }
                    }
                }
        ).show();
    }

    private void fixDirPath() {
        String path = ImageUtil.getDirPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
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


    private class DialogOnCancelListener implements DialogInterface.OnCancelListener {
        @Override
        public void onCancel(DialogInterface dialogInterface) {
            restoreUploadMsg();
        }
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



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case P_CODE_PERMISSIONS:
                requestResult(permissions, grantResults);
                restoreUploadMsg();
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestPermissionsAndroidM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> needPermissionList = new ArrayList<>();
            needPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            needPermissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            needPermissionList.add(Manifest.permission.CAMERA);

            PermissionUtil.requestPermissions(MIActivity.this, P_CODE_PERMISSIONS, needPermissionList);

        } else {
            return;
        }
    }

    public void requestResult(String[] permissions, int[] grantResults) {
        ArrayList<String> needPermissions = new ArrayList<String>();

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (PermissionUtil.isOverMarshmallow()) {

                    needPermissions.add(permissions[i]);
                }
            }
        }

        if (needPermissions.size() > 0) {
            StringBuilder permissionsMsg = new StringBuilder();

            for (int i = 0; i < needPermissions.size(); i++) {
                String strPermissons = needPermissions.get(i);

                if (Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(strPermissons)) {
                    permissionsMsg.append("," + getString(R.string.permission_storage));

                } else if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(strPermissons)) {
                    permissionsMsg.append("," + getString(R.string.permission_storage));

                } else if (Manifest.permission.CAMERA.equals(strPermissons)) {
                    permissionsMsg.append("," + getString(R.string.permission_camera));

                }
            }

            String strMessage = "请允许使用\"" + permissionsMsg.substring(1).toString() + "\"权限, 以正常使用APP的所有功能.";

            Toast.makeText(this, strMessage, Toast.LENGTH_SHORT).show();

        } else {
            return;
        }
    }
}
