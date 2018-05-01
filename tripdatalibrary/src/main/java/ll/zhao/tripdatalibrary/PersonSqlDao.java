package ll.zhao.tripdatalibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.tripdatalibrary.model.BaseModel;
import ll.zhao.tripdatalibrary.model.PersonModel;

/**
 * Created by Administrator on 2018/3/21.
 */
public class PersonSqlDao{

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

    public boolean insert(List<PersonModel> list) {
        if(list == null || list.size() < 1){
            return true;
        }
        SQLiteDatabase sqlDatabase = null;
         boolean isSuccess = true;
        try {
            sqlDatabase = baseDB.getWritableDatabase();
            sqlDatabase.beginTransaction();
            SQLiteStatement stat = null;
            for (PersonModel personModel : list) {
                ZLog.showLog("PersonSqlDao","insert", personModel.toString());
                stat = sqlDatabase.compileStatement(insertSql);
                stat.bindString(1, personModel.getTel());
                stat.bindString(2, personModel.getName());
                stat.bindString(3, personModel.getType());
                if (personModel.getIcon() != null) {
                    stat.bindBlob(4, Utils.bitmabToBytes(context, personModel.getIcon()));
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

    public boolean insert(PersonModel personModel) {
        ZLog.showLog("PersonSqlDao","insert", personModel.toString());
        if(personModel == null || personModel.getKey() == null){
            return false;
        }
        SQLiteDatabase sqlDatabase = null;
        boolean isSuccess = true;
        try {
            sqlDatabase = baseDB.getWritableDatabase();
            sqlDatabase.beginTransaction();
            SQLiteStatement stat = null;
                stat = sqlDatabase.compileStatement(insertSql);
                stat.bindString(1, personModel.getTel());
                stat.bindString(2, personModel.getName());
                stat.bindString(3, personModel.getType());
                if (personModel.getIcon() != null) {
                    stat.bindBlob(4, Utils.bitmabToBytes(context, personModel.getIcon()));
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

    public List<PersonModel> getAllData() {
        SQLiteDatabase sqlDatabase = baseDB.getWritableDatabase();
        Cursor cursor = sqlDatabase.rawQuery(selectSql,null);
        List<PersonModel> baseModels = new ArrayList<>();
        while (cursor.moveToNext()){
            PersonModel personModel = new PersonModel();
            personModel.setTel(cursor.getString(cursor.getColumnIndex(BaseDB.TEL_PERSON_COLUMN)));
            personModel.setName(cursor.getString(cursor.getColumnIndex(BaseDB.NAME_PERSON_COLUMN)));
            personModel.setType(cursor.getString(cursor.getColumnIndex(BaseDB.TYPE_PERSON_COLUMN)));
            byte[] icon = cursor.getBlob(cursor.getColumnIndex(BaseDB.ICON_PERSON_COLUMN));
            if(icon != null && icon.length > 0){
                personModel.setIcon(icon);
            }
            ZLog.showLog("PersonSqlDao","getAllData", personModel.toString());
            baseModels.add(personModel);
        }
        ZLog.showLog("PersonSqlDao","getAllData",baseModels.size() + "");
        sqlDatabase.close();
        return baseModels;
    }

    public List<PersonModel> getData(String[] conditions) {
         if(conditions == null || conditions.length < 1){
             return getAllData();
         }
        List<PersonModel> baseModels = new ArrayList<>();
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
            PersonModel personModel = new PersonModel();
            personModel.setTel(cursor.getString(cursor.getColumnIndex(BaseDB.TEL_PERSON_COLUMN)));
            personModel.setName(cursor.getString(cursor.getColumnIndex(BaseDB.NAME_PERSON_COLUMN)));
            personModel.setType(cursor.getString(cursor.getColumnIndex(BaseDB.TYPE_PERSON_COLUMN)));
            byte[] icon = cursor.getBlob(cursor.getColumnIndex(BaseDB.ICON_PERSON_COLUMN));
            if(icon != null && icon.length > 0){
                personModel.setIcon(icon);
            }
            ZLog.showLog("PersonSqlDao","getData", personModel.toString());
            baseModels.add(personModel);
        }
        return baseModels;
    }

    public PersonModel getSelfData() {

        StringBuffer selectStr = new StringBuffer(selectSql);
        selectStr.append(" ");
                selectStr.append(BaseDB.TYPE_PERSON_COLUMN);
                selectStr.append("='1'");
        ZLog.showLog("PersonSqlDao","getData", selectStr.toString());
        SQLiteDatabase sqlDatabase = baseDB.getWritableDatabase();
        Cursor cursor = sqlDatabase.rawQuery(selectSql,null);
        PersonModel personModel = new PersonModel();
        while (cursor.moveToNext()){
            personModel.setTel(cursor.getString(cursor.getColumnIndex(BaseDB.TEL_PERSON_COLUMN)));
            personModel.setName(cursor.getString(cursor.getColumnIndex(BaseDB.NAME_PERSON_COLUMN)));
            personModel.setType(cursor.getString(cursor.getColumnIndex(BaseDB.TYPE_PERSON_COLUMN)));
            byte[] icon = cursor.getBlob(cursor.getColumnIndex(BaseDB.ICON_PERSON_COLUMN));
            if(icon != null && icon.length > 0){
                personModel.setIcon(icon);
            }
            ZLog.showLog("PersonSqlDao","getData", personModel.toString());
        }
        return personModel;
    }

    public boolean updateSelfInfo(PersonModel personModel){
        ZLog.showLog("PersonSqlDao","updateSelfInfo", personModel.toString());
        if(personModel == null || personModel.getKey() == null){
            return false;
        }
        SQLiteDatabase sqlDatabase = null;
        boolean isSuccess = true;
        try {
            sqlDatabase = baseDB.getWritableDatabase();
            sqlDatabase.beginTransaction();

            ContentValues cv = new ContentValues();
            cv.put(BaseDB.TEL_PERSON_COLUMN,personModel.getTel());
            cv.put(BaseDB.NAME_PERSON_COLUMN,personModel.getName());
            if (personModel.getIcon() != null) {
                cv.put(BaseDB.ICON_PERSON_COLUMN,Utils.bitmabToBytes(context, personModel.getIcon()));
            }
            sqlDatabase.update(BaseDB.PERSON_TABLE,cv,BaseDB.TYPE_PERSON_COLUMN + " = ?",new String[]{"1"});
            sqlDatabase.setTransactionSuccessful();
        }catch (SQLException e) {
            isSuccess = false;
            e.printStackTrace();
        } finally {
            // 结束
            sqlDatabase.endTransaction();
            sqlDatabase.close();
        }
        ZLog.showLog("PersonSqlDao","updateSelfInfo", isSuccess+"");
        return isSuccess;
    }

}
