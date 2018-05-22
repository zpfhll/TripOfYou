package ll.zhao.triptoyou.userinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import ll.zhao.tripdatalibrary.PersonSqlDao;
import ll.zhao.tripdatalibrary.model.PersonModel;
import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;
import ll.zhao.triptoyou.custom.HLLAlert;
import ll.zhao.triptoyou.top.TopActivity;

public class UserInfoActivity extends BaseActivity {
    public static final int IMAGE_CODE = 1;
    public static final int CROP_CODE = 2;
    public static final int MODIFY_RESPONSE = 2;

    private PersonModel userInfo;
    private ImageView userImage;
    private EditText userName;
    private EditText userTel;
    private Button closeBtn;
    private Button doneBtn;
    private Bitmap chanedImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_info_modify);

        userInfo = getIntent().getParcelableExtra("userInfo");

        userImage = findViewById(R.id.user_image);
        userName =findViewById(R.id.user_name);
        userTel  =findViewById(R.id.user_tel);
        closeBtn  =findViewById(R.id.close_modify);
        doneBtn =findViewById(R.id.done_button);
        userImage.setImageBitmap(userInfo.getIcon());
        userImage.setClipToOutline(true);
        userName.setHint(userInfo.getName());
        userName.setText(userInfo.getName());
        userTel.setHint(userInfo.getTel());
        userTel.setText(userInfo.getTel());



        closeBtn.setOnClickListener(this);
        doneBtn.setOnClickListener(this);
        userImage.setOnClickListener(this);
    }


    @Override
    public void baseOnClick(View v) {
        switch (v.getId()){
            case R.id.close_modify:
                finish();
                break;
            case R.id.done_button:
                String userNmaeStr  = userName.getText().toString();
                String userTelStr = userTel.getText().toString();

                if(userTelStr.equals("") || userNmaeStr.equals("")
                        || (userNmaeStr.equals(userInfo.getName())
                        && userTelStr.equals(userInfo.getTel()) && chanedImage == null)){
                    HLLAlert.showAlert(this,R.string.top_modify_info_error_message);
                }else {
                    userInfo.setName(userNmaeStr);
                    userInfo.setTel(userTelStr);
                    if(chanedImage != null){
                        userInfo.setIcon(chanedImage);
                    }
                    PersonSqlDao personSqlDao = new PersonSqlDao(this);
                    personSqlDao.updateSelfInfo(userInfo);
                    setResult(MODIFY_RESPONSE);
                    finish();
                }
                break;
            case R.id.user_image:
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,IMAGE_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case IMAGE_CODE:
                if(data == null || data.getData() == null){
                    return;
                }
                Uri selectedImageUri = data.getData();
                File file = new File(getExternalCacheDir(), "crop_image.png");
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri outputUri = Uri.fromFile(file);
                Intent intent = new Intent("com.android.camera.action.CROP");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                intent.setDataAndType(selectedImageUri, "image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", Utils.dp2pxS(this,70));
                intent.putExtra("outputY",  Utils.dp2pxS(this,70));
                intent.putExtra("scale", true);
                //将剪切的图片保存到目标Uri中
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
                intent.putExtra("return-data", false);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
                intent.putExtra("noFaceDetection", true);
                startActivityForResult(intent, CROP_CODE);
                break;
            case CROP_CODE:
                chanedImage = null;
                File scropfile = new File(this.getExternalCacheDir(), "crop_image.png");
                chanedImage = BitmapFactory.decodeFile(scropfile.getPath());

                if(chanedImage != null){
                    userImage.setImageBitmap(chanedImage);
                    File deletefile = new File(this.getExternalCacheDir(), "crop_image.png");
                    if (deletefile.exists()) {
                        deletefile.delete();
                    }
                }else{
                    Uri imageUri = data.getData();
                    chanedImage = BitmapFactory.decodeFile(imageUri.getPath());
                    if(chanedImage != null) {
                        userImage.setImageBitmap(chanedImage);
                    }
                }
                break;
        }
    }
}
