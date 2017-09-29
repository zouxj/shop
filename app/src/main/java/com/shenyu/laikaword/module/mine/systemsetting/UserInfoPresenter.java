package com.shenyu.laikaword.module.mine.systemsetting;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.leo618.mpermission.AfterPermissionGranted;
import com.leo618.mpermission.MPermission;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.common.Constants;
import com.zxj.utilslibrary.utils.FileStorageUtil;
import com.zxj.utilslibrary.utils.ImageUtil;
import com.zxj.utilslibrary.utils.ToastUtil;
import com.zxj.utilslibrary.utils.UIUtil;
import com.shenyu.laikaword.helper.DialogHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.shenyu.laikaword.common.Constants.REQUEST_IMAGE_CAPTURE;

/**
 * Created by shenyu_zxjCode on 2017/9/15 0015.
 */

public class UserInfoPresenter {
    //当前路径
    private String mCurrentPhotoPath;
    private UserInfoView mUserInfoView;
    private Activity activity;
    private  Uri imageUri;
    public UserInfoPresenter(Activity activity,UserInfoView userInfoView){
        this.activity=activity;
        this.mUserInfoView=userInfoView;
    }
    //点击头像
    public void updateImg(){
        DialogHelper.takePhoto(activity, new DialogHelper.TakePhotoListener() {
            @Override
            public void takeByPhoto() {
                        cameraTask();
                //TODO 调取摄像头
            }

            @Override
            public void takeByCamera() {
                //TODO 调取相册
                        photoTask();
            }
        });
    }
    /**
     * @param uri     content:// 样式

     * @return real file path
     */
    public  String getFilePathFromContentUri(Uri uri) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = UIUtil.getContext().getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
    //上传信息
    public void upateInfo(String filePath){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String newImageFileName = FileStorageUtil.getAppCacheDirPath() + timeStamp + ".JPG";
        String  imgUrl = ImageUtil.compressImage(filePath, newImageFileName);
        mUserInfoView.updateImg(imgUrl);

    }
    /**
     * 摄像头获取照片
     */
    @AfterPermissionGranted(Constants.RC_CAMERA_PERM)
    public void cameraTask() {
        if (MPermission.hasPermissions(activity, Manifest.permission.CAMERA)&&MPermission.hasPermissions(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!
            dispatchTakePictureIntent();
//          ToastUtil.showToastShort("TODO: Camera things");
        } else {
            // Ask for one permission
            MPermission.requestPermissions(activity, UIUtil.getString(R.string.rationale_camera), Constants.RC_CAMERA_PERM, Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    /**
     * 相册调取照片
     */
    public void dispatchTakePictureIntent() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        // 判断系统中是否有处理该Intent的Activity
        if (intentCamera.resolveActivity(activity.getPackageManager()) != null) {
            // 创建文件来保存拍的照片
            File photoFile = null;
            try {
                photoFile = FileStorageUtil.createImageFile();
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                mUserInfoView.setImg(mCurrentPhotoPath);
            } catch (IOException ex) {
                // 异常处理
                ToastUtil.showToastShort(ex.getMessage());
            }
            if (photoFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    imageUri   = FileProvider.getUriForFile(activity, "com.shenyu.laikaword.fileprovider", photoFile);//通过FileProvider创建一个content类型的Uri
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                } else {
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                }
                ;   activity.startActivityForResult(intentCamera, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            ToastUtil.showToastLong("无法启动相机");
        }
    }
    /**
     * 从相册中获取
     */
    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //判断系统中是否有处理该Intent的Activity
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, Constants.REQUEST_IMAGE_GET);
        } else {
            ToastUtil.showToastLong("未找到图片查看器");
        }
    }
    //相册获取
    @AfterPermissionGranted(Constants.RC_PHOTO_PERM)
    public void photoTask(){
        if (MPermission.hasPermissions(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!
            selectImage();
        } else {
            // Ask for one permission
            MPermission.requestPermissions(activity, UIUtil.getString(R.string.rationale_camera), Constants.RC_PHOTO_PERM, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

}
