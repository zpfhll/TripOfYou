package ll.zhao.triptoyou;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import ll.zhao.triptoyou.database.DaoMaster;
import ll.zhao.triptoyou.database.DaoSession;

public class BaseApplication extends Application {
    private static DaoSession daoSession;

    private static final String DB_PASSWORD = "trip_of_you";

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"trip-db");
        Database database = helper.getEncryptedWritableDb("DB_PASSWORD");
        daoSession = new DaoMaster(database).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
