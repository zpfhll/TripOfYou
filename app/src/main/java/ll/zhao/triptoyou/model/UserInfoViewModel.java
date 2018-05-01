package ll.zhao.triptoyou.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import ll.zhao.tripdatalibrary.model.PersonModel;

/**
 * Created by ssnbuser on 2018/04/27.
 */

public class UserInfoViewModel extends ViewModel {
    private MutableLiveData<PersonModel> userInfo;

    public MutableLiveData<PersonModel> getUserInfo() {
        if(userInfo == null){
            userInfo = new MutableLiveData();
        }
        return userInfo;
    }
}
