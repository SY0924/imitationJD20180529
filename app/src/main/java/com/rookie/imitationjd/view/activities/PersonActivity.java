package com.rookie.imitationjd.view.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rookie.imitationjd.R;
import com.rookie.imitationjd.bean.UpdateNameBean;
import com.rookie.imitationjd.bean.UploadBean;
import com.rookie.imitationjd.bean.UserInfoBean;
import com.rookie.imitationjd.presenter.UploadPresenter;
import com.rookie.imitationjd.presenter.UserPresenter;
import com.rookie.imitationjd.utils.ImageUtils;
import com.rookie.imitationjd.view.interfaces.IUploadView;
import com.rookie.imitationjd.view.interfaces.IUserView;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import android.view.ViewGroup.LayoutParams;

public class PersonActivity extends AppCompatActivity implements IUploadView, IUserView {

    private static final String TAG ="PersonActivity---" ;
    @BindView(R.id.person_back)
    ImageView mPersonBack;
    @BindView(R.id.person_logo)
    ImageView mPersonLogo;
    @BindView(R.id.nickname_logo)
    ImageView mNicknameLogo;
    @BindView(R.id.person_nickname)
    TextView mPersonNickname;
    @BindView(R.id.preson_camera)
    Button mPresonCamera;
    @BindView(R.id.preson_album)
    Button mPresonAlbum;
    @BindView(R.id.preson_cancel)
    Button mPresonCancel;
    @BindView(R.id.preson_lin)
    LinearLayout mPresonLin;
    @BindView(R.id.person_icon)
    SimpleDraweeView mPersonIcon;

    private File tempFile;
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private String uid;
    private UserPresenter userPresenter;
    private UploadPresenter uploadPresenter;
    private Button update_cancel,update_success;
    private EditText update_name;


    private View parent;
    private View contentView;
    private PopupWindow popupWindow;

    private static final int MY_PERMISSION_REQUEST_CODE = 9999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

        contentView = View.inflate(PersonActivity.this, R.layout.layout_wd_update_nickname, null);
        //父窗体
        parent = View.inflate(PersonActivity.this, R.layout.activity_person, null);

        popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        popupWindow.setTouchable(true);//设置窗体可以触摸,,,默认就是true
        popupWindow.setFocusable(true);//让窗体获取到焦点...一般情况下窗体里面的控件都能获取到焦点,但是editText特殊

        popupWindow.setOutsideTouchable(true);//设置窗体外部可以触摸
        popupWindow.setBackgroundDrawable(new BitmapDrawable(String.valueOf(R.drawable.a1)));//设置背景

        update_cancel =  contentView.findViewById(R.id.update_cancel);
        update_success =  contentView.findViewById(R.id.update_success);
        update_name =  contentView.findViewById(R.id.update_name);

        update_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(update_name.getText().toString().trim())){
                    Toast.makeText(PersonActivity.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                }else {
                    //否则，走修改昵称的方法
                    uploadPresenter.updateParams(uid,update_name.getText().toString());
                    onResume();
                    //弹出窗体消失
                    popupWindow.dismiss();

                }


            }
        });

        update_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出窗体消失
                popupWindow.dismiss();
            }
        });

        //用户信息持有的presenter
        userPresenter= new UserPresenter(this);

        //上传头像，修改昵称持有的presenter
        uploadPresenter = new UploadPresenter(this);




    }

    @OnClick({R.id.person_back, R.id.person_logo, R.id.nickname_logo, R.id.preson_camera, R.id.preson_album, R.id.preson_cancel,R.id.person_icon})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.person_back:
                finish();
                break;
            case R.id.person_logo:
                mPresonLin.setVisibility(View.VISIBLE);
                break;
            case R.id.person_icon:
                mPresonLin.setVisibility(View.VISIBLE);
                break;
            case R.id.nickname_logo:
                popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
                break;
            case R.id.preson_camera:
                checkPermission();
                break;
            case R.id.preson_album:
                // 激活系统图库，选择一张图片
                Intent intent1 = new Intent(Intent.ACTION_PICK);
                intent1.setType("image/*");
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(intent1, PHOTO_REQUEST_GALLERY);
                break;
            case R.id.preson_cancel:
                mPresonLin.setVisibility(View.INVISIBLE);
                break;

        }
    }

    private void initCamera() {
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
            // 从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
    }

    /*
    * 判断sdcard是否被挂载
    */
    private boolean hasSdcard() {
        //判断ＳＤ卡手否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    /*
    * 剪切图片
    */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(PersonActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                /**
                 * 获得图片
                 */
                mPersonIcon.setImageBitmap(bitmap);
                setImgByStr( bitmap);
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 上传头像
     */
    public  void setImgByStr(Bitmap bitmap) {
        if(bitmap != null){
            // 拿着imagePath上传了
        }
        String imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.d("zxz","imagePath:"+imagePath);
        if(imagePath!=null){
            File file=new File(imagePath);//将要保存图片的路径

            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("file", file.getName(), photoRequestBody);
            uploadPresenter.uploads(uid,photo);
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences data = getSharedPreferences("user", Context.MODE_PRIVATE);
        uid = data.getString("uid", "");
        userPresenter.userParams(uid);

        Log.d(TAG, "onResume: "+uid);
    }

    //上传头像的接口
    @Override
    public void showUploadData(UploadBean uploadBean) {
        if(uploadBean!=null){
            if(uploadBean.getCode().equals("0")){
                Toast.makeText(PersonActivity.this,uploadBean.getMsg().toString(),Toast.LENGTH_SHORT).show();
                mPresonLin.setVisibility(View.INVISIBLE);
                //成功后重新走一次获取用户信息的方法
                onResume();
            }else{
                Toast.makeText(PersonActivity.this,uploadBean.getMsg().toString(),Toast.LENGTH_SHORT).show();
                mPresonLin.setVisibility(View.INVISIBLE);
            }

        }

    }
        //获取用户信息
    @Override
    public void getUserSuccess(UserInfoBean userInfoBean) {

        if (userInfoBean != null) {
            String icon = userInfoBean.getData().getIcon();
            mPersonIcon.setImageURI(icon);
            Object nickname = userInfoBean.getData().getNickname();
            mPersonNickname.setText((String)nickname);
        }

    }

    //修改昵称
    @Override
    public void showUpdateNameData(UpdateNameBean updateNameBean) {
        if (updateNameBean != null) {
            if (updateNameBean.getCode().equals("0")) {
                Toast.makeText(PersonActivity.this, updateNameBean.getMsg().toString(), Toast.LENGTH_SHORT).show();
                //成功后重新走一次获取用户信息的方法
                onResume();

            } else {
                Toast.makeText(PersonActivity.this, updateNameBean.getMsg().toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    //解绑
    @Override
    protected void onDestroy() {
        super.onDestroy();
        uploadPresenter.onDestory();
        userPresenter.onDestory();


    }

    //动态申请权限
    public void checkPermission() {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {

                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                }
        );
        // 如果这3个权限全都拥有, 则直接执行读取短信代码
        if (isAllGranted) {
            initCamera();
            toast("所有权限已经授权！");
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(PersonActivity.this,
                new String[] {

                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }
    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(PersonActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                toast("检查权限");
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行读取短信代码
                initCamera();


            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
//                openAppDetails();
                toast("需要授权！");

            }
        }
    }
    public void toast(String content){
        Toast.makeText(PersonActivity.this,content,Toast.LENGTH_SHORT).show();
    }
}

