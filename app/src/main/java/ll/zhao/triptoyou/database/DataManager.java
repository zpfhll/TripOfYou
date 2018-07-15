package ll.zhao.triptoyou.database;

import java.util.ArrayList;
import java.util.List;

import ll.zhao.triptoyou.BaseApplication;
import ll.zhao.triptoyou.Constants;

public class DataManager {

    public static long insertPerson(Person person){
        long id = BaseApplication.getDaoSession().getPersonDao().insertOrReplace(person);
        return id;
    }
    public static Person getSelfInfo(){
        List<Person> self = BaseApplication.getDaoSession().getPersonDao().queryBuilder().where(PersonDao.Properties.Type.eq(Constants.PERSON_TYPE_S)).list();
        if(self == null || self.size() != 1){
            return null;
        }else{
            return  self.get(0);
        }
    }
    public static void updatePerson(Person person){
        BaseApplication.getDaoSession().getPersonDao().update(person);
    }

    public static List<Person> getAllFriend(){
        List<Person>  persons = BaseApplication.getDaoSession().getPersonDao().queryBuilder().where(PersonDao.Properties.Type.eq(Constants.PERSON_TYPE_P)).list();
        if(persons == null){
            persons = new ArrayList<>();
        }
        return persons;
    }

    public static void deletePerson(Person person){
        BaseApplication.getDaoSession().getPersonDao().delete(person);
    }

}
