package ll.zhao.triptoyou.contacts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import ll.zhao.tripdatalibrary.model.PersonModel;
import ll.zhao.triptoyou.R;
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

    public ContactDetailFragment() {
        // Required empty public constructor
    }

    public static ContactDetailFragment newInstance(PersonModel userInfo) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        fragment.setUserInfo(userInfo);
        return fragment;
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
        contactIcon.setClipToOutline(true);
        contactIcon.setImageBitmap(userInfo.getIcon());
        contactName.setText(userInfo.getName());
        contactTel.setText(userInfo.getTel());
        closeButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        delteButton.setOnClickListener(this);
        return view;
    }

    public void setUserInfo(PersonModel userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contact_detail_delete:
                break;
            case R.id.contact_detail_close:
                break;
            case R.id.contact_detail_save:
                break;
        }
    }
}
