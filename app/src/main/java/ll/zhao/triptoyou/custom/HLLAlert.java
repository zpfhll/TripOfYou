package ll.zhao.triptoyou.custom;

import android.app.AlertDialog;
import android.content.Context;

import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.login.LoginActivity;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HLLAlert {

    public static void showAlert(Context context, int resId){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setMessage(resId)
                .setPositiveButton(R.string.close,null).show();
    }

}
