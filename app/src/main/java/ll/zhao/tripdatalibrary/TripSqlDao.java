package ll.zhao.tripdatalibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.tripdatalibrary.model.TripModel;

public class TripSqlDao {

    private BaseDB baseDB;

    private Context context;

    private static final String insertSql = "insert into  " +  BaseDB.TRIP_TABLE + " (" +
            BaseDB.ID_TRIP_COLUMN + ",  " +
            BaseDB.TRIP_NAME_TRIP_COLUMN  + ",  " +
            BaseDB.MEMO_TRIP_COLUMN  + ",  " +
            BaseDB.LOCATION_TRIP_COLUMN + ",  " +
            BaseDB.COUNT_TRIP_COLUMN  + ",  " +
            BaseDB.START_TIME_TRIP_COLUMN  + ",  " +
            BaseDB.END_TIME_TRIP_COLUMN + ",  " +
            BaseDB.STATE_TRIP_COLUMN  + ",  " +
            BaseDB.ADMIN_TRIP_COLUMN  + ",  " +
            BaseDB.LOCATION_ID_TRIP_COLUMN + ",  " +
            BaseDB.IMAGE1_TRIP_COLUMN  + ",  " +
            BaseDB.IMAGE2_TRIP_COLUMN  + ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String selectSql = "select * from " + BaseDB.TRIP_TABLE;

    public TripSqlDao(Context context) {
        this.context =context;
        baseDB = new BaseDB(context);
    }

    public boolean insert(TripModel tripModel) {
        ZLog.showLog("TripSqlDao","insert", tripModel.toString());
        if(tripModel == null || tripModel.getKey() == null){
            return false;
        }
        SQLiteDatabase sqlDatabase = null;
        boolean isSuccess = true;
        try {
            sqlDatabase = baseDB.getWritableDatabase();
            sqlDatabase.beginTransaction();
            SQLiteStatement stat = null;
            stat = sqlDatabase.compileStatement(insertSql);
            stat.bindString(1, tripModel.getId());
            stat.bindString(2, tripModel.getTrip_name());
            stat.bindString(3, tripModel.getMemo());
            stat.bindString(4, tripModel.getLocation());
            stat.bindDouble(5, tripModel.getCount());
            stat.bindString(6, tripModel.getStart_time());
            stat.bindString(7, tripModel.getEnd_time());
            stat.bindLong(8, tripModel.getState());
            stat.bindString(9, tripModel.getAdministrator());
            stat.bindString(10, tripModel.getLoction_id());
            if (tripModel.getImage1() != null) {
                stat.bindBlob(11, Utils.bitmabToBytes(context, tripModel.getImage1()));
            }
            if (tripModel.getImage2() != null) {
                stat.bindBlob(12, Utils.bitmabToBytes(context, tripModel.getImage2()));
            }
            stat.executeInsert();
            sqlDatabase.setTransactionSuccessful();
        }catch (SQLException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            // 结束
            sqlDatabase.endTransaction();
            sqlDatabase.close();
        }
        ZLog.showLog("TripSqlDao","insert", isSuccess+"");
        return isSuccess;
    }

    public List<TripModel> getAllData() {
        SQLiteDatabase sqlDatabase = baseDB.getWritableDatabase();
        Cursor cursor = sqlDatabase.rawQuery(selectSql,null);
        List<TripModel> baseModels = new ArrayList<>();
        while (cursor.moveToNext()){
            TripModel tripModel = new TripModel();
            tripModel.setId(cursor.getString(cursor.getColumnIndex(BaseDB.ID_TRIP_COLUMN)));
            tripModel.setTrip_name(cursor.getString(cursor.getColumnIndex(BaseDB.TRIP_NAME_TRIP_COLUMN)));
            tripModel.setMemo(cursor.getString(cursor.getColumnIndex(BaseDB.MEMO_TRIP_COLUMN)));
            tripModel.setLocation(cursor.getString(cursor.getColumnIndex(BaseDB.LOCATION_TRIP_COLUMN)));
            tripModel.setCount(cursor.getDouble(cursor.getColumnIndex(BaseDB.COUNT_TRIP_COLUMN)));
            tripModel.setStart_time(cursor.getString(cursor.getColumnIndex(BaseDB.START_TIME_TRIP_COLUMN)));
            tripModel.setEnd_time(cursor.getString(cursor.getColumnIndex(BaseDB.END_TIME_TRIP_COLUMN)));
            tripModel.setState(cursor.getInt(cursor.getColumnIndex(BaseDB.STATE_TRIP_COLUMN)));
            tripModel.setAdministrator(cursor.getString(cursor.getColumnIndex(BaseDB.ADMIN_TRIP_COLUMN)));
            tripModel.setLoction_id(cursor.getString(cursor.getColumnIndex(BaseDB.LOCATION_ID_TRIP_COLUMN)));
            tripModel.setId(cursor.getString(cursor.getColumnIndex(BaseDB.IMAGE1_TRIP_COLUMN)));

            byte[] image1 = cursor.getBlob(cursor.getColumnIndex(BaseDB.IMAGE1_TRIP_COLUMN));
            if(image1 != null && image1.length > 0){
                tripModel.setImage1(image1);
            }
            byte[] image2 = cursor.getBlob(cursor.getColumnIndex(BaseDB.IMAGE2_TRIP_COLUMN));
            if(image2 != null && image2.length > 0){
                tripModel.setImage2(image2);
            }
            ZLog.showLog("TripSqlDao","getAllData", tripModel.toString());

            baseModels.add(tripModel);
    }
        ZLog.showLog("TripSqlDao","getAllData",baseModels.size() + "");
        sqlDatabase.close();
        return baseModels;
    }

    public List<TripModel> getDataByState(@TripEnum.TRIP_STATE int state) {
        SQLiteDatabase sqlDatabase = baseDB.getWritableDatabase();
        ZLog.showLog("TripSqlDao","getDataByState", "state : "+state);

        String sql = selectSql + " " + BaseDB.STATE_TRIP_COLUMN + " = " + state;

        Cursor cursor = sqlDatabase.rawQuery(sql,null);
        List<TripModel> baseModels = new ArrayList<>();
        while (cursor.moveToNext()){
            TripModel tripModel = new TripModel();
            tripModel.setId(cursor.getString(cursor.getColumnIndex(BaseDB.ID_TRIP_COLUMN)));
            tripModel.setTrip_name(cursor.getString(cursor.getColumnIndex(BaseDB.TRIP_NAME_TRIP_COLUMN)));
            tripModel.setMemo(cursor.getString(cursor.getColumnIndex(BaseDB.MEMO_TRIP_COLUMN)));
            tripModel.setLocation(cursor.getString(cursor.getColumnIndex(BaseDB.LOCATION_TRIP_COLUMN)));
            tripModel.setCount(cursor.getDouble(cursor.getColumnIndex(BaseDB.COUNT_TRIP_COLUMN)));
            tripModel.setStart_time(cursor.getString(cursor.getColumnIndex(BaseDB.START_TIME_TRIP_COLUMN)));
            tripModel.setEnd_time(cursor.getString(cursor.getColumnIndex(BaseDB.END_TIME_TRIP_COLUMN)));
            tripModel.setState(cursor.getInt(cursor.getColumnIndex(BaseDB.STATE_TRIP_COLUMN)));
            tripModel.setAdministrator(cursor.getString(cursor.getColumnIndex(BaseDB.ADMIN_TRIP_COLUMN)));
            tripModel.setLoction_id(cursor.getString(cursor.getColumnIndex(BaseDB.LOCATION_ID_TRIP_COLUMN)));
            tripModel.setId(cursor.getString(cursor.getColumnIndex(BaseDB.IMAGE1_TRIP_COLUMN)));

            byte[] image1 = cursor.getBlob(cursor.getColumnIndex(BaseDB.IMAGE1_TRIP_COLUMN));
            if(image1 != null && image1.length > 0){
                tripModel.setImage1(image1);
            }
            byte[] image2 = cursor.getBlob(cursor.getColumnIndex(BaseDB.IMAGE2_TRIP_COLUMN));
            if(image2 != null && image2.length > 0){
                tripModel.setImage2(image2);
            }
            ZLog.showLog("TripSqlDao","getDataByState", tripModel.toString());

            baseModels.add(tripModel);
        }
        ZLog.showLog("TripSqlDao","getDataByState",baseModels.size() + "");
        sqlDatabase.close();
        return baseModels;
    }

    public boolean setTripState(@TripEnum.TRIP_STATE int state,String endTime,double count,String tripId){
        ZLog.showLog("TripSqlDao","setTripState", state+":"+endTime+":"+tripId);
        if(state == TripEnum.TRIP_STATE_NOTDO && (endTime == null || endTime.equals(""))) {
                    return false;
        }
        SQLiteDatabase sqlDatabase = null;
        boolean isSuccess = true;
        try {
            sqlDatabase = baseDB.getWritableDatabase();
            sqlDatabase.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(BaseDB.STATE_TRIP_COLUMN,state);

            if(state == TripEnum.TRIP_STATE_NOTDO){
                cv.put(BaseDB.END_TIME_TRIP_COLUMN,endTime);
            }else if(state == TripEnum.TRIP_STATE_DONE){
                cv.put(BaseDB.COUNT_TRIP_COLUMN,count);
            }
            sqlDatabase.update(BaseDB.TRIP_TABLE,cv,BaseDB.ID_TRIP_COLUMN + " = ?",new String[]{tripId});
            sqlDatabase.setTransactionSuccessful();
        }catch (SQLException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            // 结束
            sqlDatabase.endTransaction();
            sqlDatabase.close();
        }
        ZLog.showLog("TripSqlDao","setTripState", isSuccess+"");

        return  isSuccess;
    }

    public boolean updateTripImage(Bitmap image1, Bitmap image2,String tripId){
        ZLog.showLog("TripSqlDao","updateTripImage", image1+":"+image2+":"+tripId);
        if(image1 == null && image2 == null){
            return true;
        }
        SQLiteDatabase sqlDatabase = null;
        boolean isSuccess = true;
        try {
            sqlDatabase = baseDB.getWritableDatabase();
            sqlDatabase.beginTransaction();
            ContentValues cv = new ContentValues();

            if(image1 != null){
                cv.put(BaseDB.IMAGE1_TRIP_COLUMN,Utils.bitmabToBytes(context, image1));
            }
            if(image2 != null){
                cv.put(BaseDB.IMAGE2_TRIP_COLUMN,Utils.bitmabToBytes(context, image2));
            }
            sqlDatabase.update(BaseDB.TRIP_TABLE,cv,BaseDB.ID_TRIP_COLUMN + " = ?",new String[]{tripId});
            sqlDatabase.setTransactionSuccessful();
        }catch (SQLException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            // 结束
            sqlDatabase.endTransaction();
            sqlDatabase.close();
        }
        ZLog.showLog("TripSqlDao","setTripState", isSuccess+"");

        return  isSuccess;
    }



}
