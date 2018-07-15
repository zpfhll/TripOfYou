package ll.zhao.tripdatalibrary;

import android.util.Log;

/**
 * Created by Administrator on 2018/3/24.
 */

public class ZLog {
    private static final String TAG = "tripdatalibrary";

    public static void showLog(String className,String methodName,String log){
        Log.i(TAG,"--->" + className+"." + methodName + ":"+log);
    }

}
