package ll.zhao.triptoyou;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import ll.zhao.triptoyou.login.LoginActivity;
import ll.zhao.triptoyou.top.TopActivity;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        String loginFlg = Utils.getDateFromShare(this, Utils.LOGIN_FLAG);
        if(loginFlg.equals(Utils.LOGIN_DONE)){
            Intent intent = new Intent(MainActivity.this, TopActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
