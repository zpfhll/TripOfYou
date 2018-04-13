package ll.zhao.triptoyou;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ll.zhao.tripdatalibrary.PersonSqlDao;
import ll.zhao.tripdatalibrary.model.Person;
import ll.zhao.triptoyou.top.TopActivity;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PersonSqlDao personSqlDao = new PersonSqlDao(this);

        Person person = new Person();
        person.setTel("181111234567");
        person.setName("赵鹏飞");
        person.setIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));

        personSqlDao.insert(person);


        Log.i("--->main",person.getClass().getSimpleName());
        personSqlDao.getAllData();
    }

    public void click(View view){
        Intent intent = new Intent(MainActivity.this, TopActivity.class);
        startActivity(intent);
    }
}
