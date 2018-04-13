package ll.zhao.tripdatalibrary;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2018/4/3.
 */

public class TripEnum {
    /**
     * 完成
     */
    public static final int TRIP_STATE_DONE = 0;
    /**
     * 未经算
     */
    public static final int TRIP_STATE_NOTDO = 1;
    /**
     *正在进行
     */
    public static final int TRIP_STATE_DOING = 2;

    @IntDef({TRIP_STATE_DONE,TRIP_STATE_NOTDO,TRIP_STATE_DOING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TRIP_STATE {}
}

