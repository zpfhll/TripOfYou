package ll.zhao.triptoyou.top;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.database.Person;
import ll.zhao.triptoyou.model.UserInfoViewModel;


public class UserInfoFragment extends Fragment {

    private UserInfoViewModel userInfoViewModel;
    private Person userInfo;
    private ImageView userImage;
    private TextView userName;

    public UserInfoFragment() {
    }


    public static UserInfoFragment newInstance(Person userInfo) {
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setUserInfo(userInfo);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);
        userImage = rootView.findViewById(R.id.user_image);
        userImage.setClipToOutline(true);
        userName =rootView.findViewById(R.id.user_name);
        userInfoViewModel = ViewModelProviders.of(getActivity()).get(UserInfoViewModel.class);
        userInfoViewModel.getUserInfo().observe(this, new Observer<Person>() {
            @Override
            public void onChanged(@Nullable Person person) {
                userImage.setImageBitmap(person.getBitmapIcon());
                userName.setText(person.getName());
            }
        });

        userInfoViewModel.getUserInfo().setValue(userInfo);

        return rootView;
    }


    public Person getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Person userInfo) {
        this.userInfo = userInfo;
    }

}
