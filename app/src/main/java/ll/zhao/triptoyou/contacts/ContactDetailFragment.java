package ll.zhao.triptoyou.contacts;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import ll.zhao.tripdatalibrary.model.PersonModel;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.custom.HLLAlert;
import ll.zhao.triptoyou.custom.HLLButton;

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
        contactIcon.setImageBitmap(userInfo.getIcon());
        contactName.setText(userInfo.getName());
        contactTel.setText(userInfo.getTel());
        contactIcon.setClipToOutline(true);
        closeButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        delteButton.setOnClickListener(this);
        return view;
    }

    public void setUserInfo(PersonModel userInfo) {
        this.userInfo = userInfo;
    }

    public void refreshView(PersonModel userInfo,int position){
        this.userInfo = userInfo;
        contactIcon.setImageBitmap(userInfo.getIcon());
        contactName.setText(userInfo.getName());
        contactTel.setText(userInfo.getTel());
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
                        ContactsActivity activity  = (ContactsActivity) getActivity();
                        activity.onBackPressed();
                        activity.deleteContact(position);
                    }
                });
                break;
            case R.id.contact_detail_close:
                ContactsActivity activity  = (ContactsActivity) getActivity();
                activity.onBackPressed();
                break;
            case R.id.contact_detail_save:
                break;
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
