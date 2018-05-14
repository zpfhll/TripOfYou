package ll.zhao.triptoyou.contacts;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import ll.zhao.tripdatalibrary.PersonSqlDao;
import ll.zhao.tripdatalibrary.model.PersonModel;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;
import ll.zhao.triptoyou.custom.HLLAlert;
import ll.zhao.triptoyou.custom.HLLButton;
import ll.zhao.triptoyou.top.TopActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDetailFragment extends Fragment implements View.OnClickListener {

    private HLLButton closeButton;
    private ImageView contactIcon;
    private EditText contactName;
    private EditText contactTel;
    private HLLButton delteButton;
    private HLLButton saveButton;
    private PersonModel userInfo;
    private int position;
    private Bitmap chanedImage;

    public static final int IMAGE_CODE = 1;
    public static final int CROP_CODE = 2;

    private ContactsActivity activity;

    private static ContactDetailFragment contactDetailFragment;

    public ContactDetailFragment() {
        // Required empty public constructor
    }

    public static  ContactDetailFragment newInstance(PersonModel userInfo,int position) {
        if(contactDetailFragment == null){
            synchronized (ContactDetailFragment.class) {
                if(contactDetailFragment == null){
                    contactDetailFragment = new ContactDetailFragment();
                    contactDetailFragment.setUserInfo(userInfo);
                    contactDetailFragment.setPosition(position);
                }
            }
        }
        return contactDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        closeButton = view.findViewById(R.id.contact_detail_close);
        contactIcon = view.findViewById(R.id.contact_detail_icon);
        contactName = view.findViewById(R.id.contact_detail_name);
        contactTel = view.findViewById(R.id.contact_detail_tel);
        delteButton = view.findViewById(R.id.contact_detail_delete);
        saveButton = view.findViewById(R.id.contact_detail_save);
        if(userInfo != null && !userInfo.getName().equals("") && !userInfo.getTel().equals("")) {
            if(userInfo.getIcon() != null) {
                contactIcon.setImageBitmap(userInfo.getIcon());
            }else{
                contactIcon.setImageResource(R.mipmap.ic_launcher);
            }
            contactName.setText(userInfo.getName());
            contactTel.setText(userInfo.getTel());
        }else{
            contactName.setText("");
            contactTel.setText("");
            this.userInfo = new PersonModel();
            delteButton.setVisibility(View.GONE);
            contactIcon.setImageResource(R.mipmap.ic_launcher);
        }
        contactIcon.setClipToOutline(true);
        contactIcon.setOnClickListener(this);
        closeButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        delteButton.setOnClickListener(this);
        activity  = (ContactsActivity) getActivity();
        return view;
    }

    public void setUserInfo(PersonModel userInfo) {
        this.userInfo = userInfo;
    }

    public void refreshView(PersonModel userInfo,int position){
        this.userInfo = userInfo;
        delteButton.setVisibility(View.VISIBLE);
        if(userInfo != null && !userInfo.getName().equals("") && !userInfo.getTel().equals("")) {
            if(userInfo.getIcon() != null) {
                contactIcon.setImageBitmap(userInfo.getIcon());
            }else{
                contactIcon.setImageResource(R.mipmap.ic_launcher);
            }
            contactName.setText(userInfo.getName());
            contactTel.setText(userInfo.getTel());
        }else{
            this.userInfo = new PersonModel();
            contactName.setText("");
            contactTel.setText("");
            delteButton.setVisibility(View.GONE);
            contactIcon.setImageResource(R.mipmap.ic_launcher);
        }
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contact_detail_delete:
                String messge = String.format(getString(R.string.contacts_delete_notice),userInfo.getName());
                HLLAlert.showAlert(getContext(), messge, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.onBackPressed();
                        activity.deleteContact(position);
                    }
                });
                break;
            case R.id.contact_detail_close:
                activity.onBackPressed();
                break;
            case R.id.contact_detail_save:
                String userNmaeStr  = contactName.getText().toString();
                String userTelStr = contactTel.getText().toString();

                if(userTelStr.equals("") || userNmaeStr.equals("")
                        || (userNmaeStr.equals(userInfo.getName())
                        && userTelStr.equals(userInfo.getTel()) && chanedImage == null)){
                    HLLAlert.showAlert(getContext(),R.string.top_modify_info_error_message);
                }else {
                    String tel = userInfo.getTel();
                    userInfo.setName(userNmaeStr);
                    userInfo.setTel(userTelStr);
                    if(chanedImage != null){
                        userInfo.setIcon(chanedImage);
                    }
                    PersonSqlDao personSqlDao = new PersonSqlDao(getContext());
                    if(delteButton.getVisibility() == View.VISIBLE){
                        personSqlDao.updateInfo(userInfo,tel);
                    }else{
                        userInfo.setType("0");
                        personSqlDao.insert(userInfo);
                    }
                    activity.onBackPressed();
                    activity.refreshContacts();
                }
                break;
            case R.id.contact_detail_icon:
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
                File file = new File(getActivity().getExternalCacheDir(), "crop_image.png");
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
                intent.putExtra("outputX", Utils.dp2pxS(getContext(),70));
                intent.putExtra("outputY",  Utils.dp2pxS(getContext(),70));
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
                File scropfile = new File(getActivity().getExternalCacheDir(), "crop_image.png");
                chanedImage = BitmapFactory.decodeFile(scropfile.getPath());

                if(chanedImage != null){
                    contactIcon.setImageBitmap(chanedImage);
                    File deletefile = new File(getActivity().getExternalCacheDir(), "crop_image.png");
                    if (deletefile.exists()) {
                        deletefile.delete();
                    }
                }else{
                    Uri imageUri = data.getData();
                    chanedImage = BitmapFactory.decodeFile(imageUri.getPath());
                    if(chanedImage != null) {
                        contactIcon.setImageBitmap(chanedImage);
                    }
                }
                break;
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
