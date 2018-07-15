package ll.zhao.triptoyou;

import android.util.Log;

/**
 * Created by Administrator on 2018/3/24.
 */

public class HLLog {
    private static final String TAG = "TripToYou";

    public static void showLog(String className,String methodName,String log){
        Log.i(TAG,"--->" + className+"." + methodName + ":"+log);
    }

    public static void showLog(Object object,String log){
        Log.i(TAG,"--->" + object.getClass().toString()+ ":"+log);
    }

}
