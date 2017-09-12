package com.shenyu.laikaword.main.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenyu.laikaword.LaiKaApplication;
import com.shenyu.laikaword.R;
import com.shenyu.laikaword.base.LKWordBaseActivity;
import com.shenyu.laikaword.main.MainModule;
import com.shenyu.laikaword.widget.NoScrollViewPager;

import javax.inject.Inject;

import butterknife.BindView;

public class TestMainActivity  {
//    /**
//     * 相册调取
//     */
//    private static final int REQUEST_IMAGE_GET = 0;
//    /**
//     * 相机调取
//     */
//    private static final int REQUEST_IMAGE_CAPTURE = 1;
//    @BindView(R.id.id_img)
//    ImageView idImg;
//    @BindView(R.id.bt_update)
//    Button btUpdate;
//    @BindView(R.id.button2)
//    Button button2;
//    @BindView(R.id.tab_homepager_navigation)
//    TabLayout tabHomepagerNavigation;
//    @BindView(R.id.mian_fragment)
//    NoScrollViewPager mViewPager;
//    private String mCurrentPhotoPath;
//    @BindView(R.id.id_text)
//    TextView idText;
//    @BindView(R.id.bt_select)
//    Button btSelect;
//    @Inject
//    MainPageAdapter mainPageAdapter;
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_main_test;
//    }
//
//    @Override
//    public void doBusiness(Context context) {
//        mViewPager.setScroll(false);
//        mViewPager.setAdapter(mainPageAdapter);
//        tabHomepagerNavigation.setupWithViewPager(mViewPager);
//        //将TabLayout与ViewPager绑定在一起
//        tabHomepagerNavigation.setupWithViewPager(mViewPager);
//        setToolBarTitle("商城");
//        tabHomepagerNavigation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                int position = tab.getPosition();
//                switch (position){
//                    case 0:
//                        setToolBarTitle("商城");
//                        break;
//                    case 1:
//                        setToolBarTitle("夺宝");
//                        break;
//                    case 2:
//                        setToolBarTitle("我的");
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
////        mainPageAdapter.setiMainTabCallBack(new IMainTabCallBack() {
////            @Override
////            public void checkFragment(int position) {
////                LogUtil.i(TAG,position);
////                switch (position){
////                    case 0:
////                        setToolBarTitle("商城");
////                        break;
////                    case 1:
////                        setToolBarTitle("夺宝");
////                        break;
////                    case 2:
////                        setToolBarTitle("我的");
////                        break;
////                }
////            }
////        });
////        button2.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                //TODO 登陸
////                IntentLauncher.with(TestMainActivity.this).launch(LoginActivity.class);
////            }
////        });
////
////        btUpdate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                updateApp();
////            }
////        });
////        btSelect.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////                ViewUtils.takePhoto(TestMainActivity.this, new ViewUtils.TakePhotoListener() {
////                    @Override
////                    public void takeByPhoto() {
////                        dispatchTakePictureIntent();
////
//////                        ToastUtil.showToastLong("相册选择");
////                    }
////
////                    @Override
////                    public void takeByCamera() {
////                        selectImage();
//////                        ToastUtil.showToastLong("拍照选择");
////                    }
////                });
////            }
////        });
//
//
//    }

//    @Override
//    public void setupActivityComponent() {
////        LaiKaApplication.get(this).getAppComponent().plus(new MainModule(getSupportFragmentManager())).inject(this);
//    }

//    private void uploadImg() {
//
//        Map<String, RequestBody> bodyMapImg = new HashMap<>();
//        File file = new File(bitmap);
//        bodyMapImg.put("file" + 1 + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse("image/png"), file));
//        bodyMapImg.put("dataSource", RequestBody.create(MediaType.parse("text/plain"), "SHENZHEN"));
//        NetWorks.uploadMultipleTypeFile("android"
//                , "cfb76357b3695f222add958604779f26",
//                "886a23a15cd1d628b801da18729d3569", "7553839"
//                , "SHENZHEN", "v5_3", "2b75f514-c6b2-4232-b21a-2142457625c1"
//                , "1501828652822", "6.0.1", "886a23a15cd1d628b801da18729d3569"
//                , "ffffffff-fc56-8bbc-ffff-ffffe95cb2ba",
//                bodyMapImg, new SimpleCallback<HeadReponse>() {
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onNext(HeadReponse headReponse) {
//                        ToastUtil.showToastLong(headReponse.getUrl());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//    }


//
//    /**
//     * 从相册中获取
//     */
//    public void selectImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        //判断系统中是否有处理该Intent的Activity
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent, REQUEST_IMAGE_GET);
//        } else {
//            ToastUtil.showToastLong("未找到图片查看器");
//        }
//    }
//
//    /**
//     * 相机调取照片
//     */
//    private void dispatchTakePictureIntent() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // 判断系统中是否有处理该Intent的Activity
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // 创建文件来保存拍的照片
//            File photoFile = null;
//            try {
//                photoFile = FileStorageUtil.createImageFile();
//                mCurrentPhotoPath = photoFile.getAbsolutePath();
//            } catch (IOException ex) {
//                // 异常处理
//            }
//            if (photoFile != null) {
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//            }
//        } else {
//            ToastUtil.showToastLong("无法启动相机");
//        }
//    }

//    String bitmap;
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // 回调成功
//        if (resultCode == RESULT_OK) {
//            String filePath = null;
//            //判断是哪一个的回调
//            if (requestCode == REQUEST_IMAGE_GET) {
//                //返回的是content://的样式
//                filePath = getFilePathFromContentUri(data.getData(), this);
//            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
//                if (mCurrentPhotoPath != null) {
//                    filePath = mCurrentPhotoPath;
//                }
//            }
//            if (!TextUtils.isEmpty(filePath)) {
//                // 自定义大小，防止OOM
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                String newImageFileName = FileStorageUtil.getAppCacheDirPath() + timeStamp + ".JPG";
//                bitmap = ImageUtil.compressImage(filePath, newImageFileName);
//                idImg.setImageBitmap(ImageUtil.getBitmap(bitmap));
//                uploadImg();
//            }
//        }
//    }

//    /**
//     * @param uri     content:// 样式
//     * @param context
//     * @return real file path
//     */
//    public static String getFilePathFromContentUri(Uri uri, Context context) {
//        String filePath;
//        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
//        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
//        if (cursor == null) return null;
//        cursor.moveToFirst();
//        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//        filePath = cursor.getString(columnIndex);
//        cursor.close();
//        return filePath;
//    }
//
//    public void updateApp() {
//        ViewUtils.makeUpdate(TestMainActivity.this, "发现新版本", "xxxxxxx", "暂不升级", "马上升级", true, new ViewUtils.ButtonCallback() {
//            @Override
//            public void onPositive(Dialog d) {
////                if (true) {
////                    finish();
////                }
//                startService(new Intent(TestMainActivity.this, DownLoadService.class));
//            }
//
//            @Override
//            public void onNegative(Dialog d) {
//                //MainActivityPermissionsDispatcher.startUpdateWithCheck(TestMainActivity.this, jsonBean.getData());
//                //startUpdate(jsonBean.getData());
//            }
//        }).show();
//
//    }


//    /**
//     * 切换Tab
//     *
//     * @param container
//     * @param manager
//     * @param position
//     */
//    private void selectTab(int container, FragmentManager manager, int position) {
//        FragmentTransaction ft = manager.beginTransaction();
//        hideAll(ft);
//        switch (position) {
//            case 0:
//                if (shopFragment == null) {
//                    shopFragment = new ShopFragment();
//                    ft.add(container, shopFragment);
//                } else {
//                    ft.show(shopFragment);
//                }
//                break;
//            case 1:
//                if (snatchFragment == null) {
//                    snatchFragment = new SnatchFragment();
//                    ft.add(container, snatchFragment);
//                } else {
//                    ft.show(snatchFragment);
//                }
//                break;
//            case 2:
//                if (ownFragment == null) {
//                    ownFragment = new OwnFragment();
//                    ft.add(container, ownFragment);
//                } else {
//                    ft.show(ownFragment);
//                }
//                break;
//        }
//        ft.commit();
//    }

//    private void hideAll(FragmentTransaction ft) {
//        if (shopFragment != null) {
//            ft.hide(shopFragment);
//        }
//        if (snatchFragment != null) {
//            ft.hide(snatchFragment);
//        }
//        if (ownFragment != null) {
//            ft.hide(ownFragment);
//        }
//    }
}
