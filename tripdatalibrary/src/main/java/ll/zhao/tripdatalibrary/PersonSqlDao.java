package ll.zhao.tripdatalibrary;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ll.zhao.tripdatalibrary.model.BaseModel;
import ll.zhao.tripdatalibrary.model.Person;

/**
 * Created by Administrator on 2018/3/21.
 */
public class PersonSqlDao implements BaseSqlDao {

    private BaseDB baseDB;

    private static final String insertSql = "insert into  " +  BaseDB.PERSON_TABLE + " (" +
            BaseDB.TEL_PERSON_COLUMN + ",  " +
            BaseDB.NAME_PERSON_COLUMN  + ",  " +
            BaseDB.TYPE_PERSON_COLUMN  + ",  " +
            BaseDB.ICON_PERSON_COLUMN + ") values (?,?,?,?)";

    private static final String selectSql = "select * from " + BaseDB.PERSON_TABLE;

    private Context context;

     public PersonSqlDao(Context context) {
        this.context =context;
         baseDB = new BaseDB(context);
     }

    @Override
    public boolean insert(List<BaseModel> list) {
        if(list == null || list.size() < 1){
            return true;
        }
        SQLiteDatabase sqlDatabase = null;
         boolean isSuccess = true;
        try {
            sqlDatabase = baseDB.getWritableDatabase();
            sqlDatabase.beginTransaction();
            SQLiteStatement stat = null;
            Person person = null;
            for (BaseModel model : list) {
                person = (Person) model;
                ZLog.showLog("PersonSqlDao","insert", person.toString());
                stat = sqlDatabase.compileStatement(insertSql);
                stat.bindString(1, person.getTel());
                stat.bindString(2, person.getName());
                stat.bindString(3, person.getType());
                if (person.getIcon() != null) {
                    stat.bindBlob(4, Utils.bitmabToBytes(context, person.getIcon()));
                }
                stat.executeInsert();
            }
            sqlDatabase.setTransactionSuccessful();
        }catch (SQLException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            // 结束
            sqlDatabase.endTransaction();
            sqlDatabase.close();
        }
        ZLog.showLog("PersonSqlDao","insert", isSuccess+"");
        return isSuccess;
    }

    @Override
    public boolean insert(BaseModel model) {
        ZLog.showLog("PersonSqlDao","insert", model.toString());
        if(model == null || model.getKey() == null){
            return false;
        }
        SQLiteDatabase sqlDatabase = null;
        boolean isSuccess = true;
        try {
            sqlDatabase = baseDB.getWritableDatabase();
            sqlDatabase.beginTransaction();
            SQLiteStatement stat = null;
            Person person = (Person) model;
                stat = sqlDatabase.compileStatement(insertSql);
                stat.bindString(1, person.getTel());
                stat.bindString(2, person.getName());
                stat.bindString(3, person.getType());
                if (person.getIcon() != null) {
                    stat.bindBlob(4, Utils.bitmabToBytes(context, person.getIcon()));
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
        ZLog.showLog("PersonSqlDao","insert", isSuccess+"");
        return isSuccess;
    }

    @Override
    public List<BaseModel> getAllData() {
        SQLiteDatabase sqlDatabase = baseDB.getWritableDatabase();
        Cursor cursor = sqlDatabase.rawQuery(selectSql,null);
        List<BaseModel> baseModels = new ArrayList<>();
        while (cursor.moveToNext()){
            Person person = new Person();
            person.setTel(cursor.getString(cursor.getColumnIndex(BaseDB.TEL_PERSON_COLUMN)));
            person.setName(cursor.getString(cursor.getColumnIndex(BaseDB.NAME_PERSON_COLUMN)));
            person.setType(cursor.getString(cursor.getColumnIndex(BaseDB.TYPE_PERSON_COLUMN)));
            byte[] icon = cursor.getBlob(cursor.getColumnIndex(BaseDB.ICON_PERSON_COLUMN));
            if(icon != null && icon.length > 0){
                person.setIcon(icon);
            }
            ZLog.showLog("PersonSqlDao","getAllData", person.toString());
            baseModels.add(person);
        }
        ZLog.showLog("PersonSqlDao","getAllData",baseModels.size() + "");
        sqlDatabase.close();
        return baseModels;
    }

    @Override
    public List<BaseModel> getData(String[] conditions) {
         if(conditions == null || conditions.length < 1){
             return getAllData();
         }
        List<BaseModel> baseModels = new ArrayList<>();
         StringBuffer selectStr = new StringBuffer(selectSql);
        selectStr.append(" ");
        for (int i = 0; i < conditions.length;i++) {
            selectStr.append(conditions[i]);
            if ( i < conditions.length - 1) {
                selectStr.append(" and ");
            }
        }
        ZLog.showLog("PersonSqlDao","getData", selectStr.toString());
        SQLiteDatabase sqlDatabase = baseDB.getWritableDatabase();
        Cursor cursor = sqlDatabase.rawQuery(selectSql,null);
        while (cursor.moveToNext()){
            Person person = new Person();
            person.setTel(cursor.getString(cursor.getColumnIndex(BaseDB.TEL_PERSON_COLUMN)));
            person.setName(cursor.getString(cursor.getColumnIndex(BaseDB.NAME_PERSON_COLUMN)));
            person.setType(cursor.getString(cursor.getColumnIndex(BaseDB.TYPE_PERSON_COLUMN)));
            byte[] icon = cursor.getBlob(cursor.getColumnIndex(BaseDB.ICON_PERSON_COLUMN));
            if(icon != null && icon.length > 0){
                person.setIcon(icon);
            }
            ZLog.showLog("PersonSqlDao","getData", person.toString());
            baseModels.add(person);
        }
        return baseModels;
    }
}
