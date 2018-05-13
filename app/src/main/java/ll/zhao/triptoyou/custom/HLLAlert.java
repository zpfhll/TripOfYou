package ll.zhao.triptoyou.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.login.LoginActivity;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HLLAlert {

    public static void showAlert(Context context, int resId){
        showAlert(context,resId,null);
    }

    public static void showAlert(Context context, String message){
        showAlert(context,message,null);
    }

    public static void showAlert(Context context, int resId,DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        builder.setMessage(resId)
                .setPositiveButton(R.string.ok,listener)
                .setNegativeButton(R.string.undo,null).show();
    }

    public static void showAlert(Context context, String message,DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton(R.string.ok,listener)
                .setNegativeButton(R.string.undo,null).show();
    }
}
