package ll.zhao.triptoyou;

import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/4/19.
 */

public class BaseActivity extends FragmentActivity implements View.OnClickListener{

    //端末の戻るボタンの有効かどうか判断するフラグ
    protected  boolean deviceBackIsEnable = true;
    protected  static boolean clickIsDone = true;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && deviceBackIsEnable){
            backOnClick(keyCode,event);
            return super.onKeyDown(keyCode, event);
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        baseOnClick(v);
    }

    protected void  baseOnClick(View v){}

    protected void  backOnClick(int keyCode, KeyEvent event){}

    protected void closeActivity(){
        finish();
    }
}
