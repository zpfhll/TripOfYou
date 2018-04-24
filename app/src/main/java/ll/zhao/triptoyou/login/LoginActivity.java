package ll.zhao.triptoyou.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import ll.zhao.tripdatalibrary.PersonSqlDao;
import ll.zhao.tripdatalibrary.model.Person;
import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.HLLog;
import ll.zhao.triptoyou.MainActivity;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;
import ll.zhao.triptoyou.custom.HLLAlert;
import ll.zhao.triptoyou.custom.HLLButton;
import ll.zhao.triptoyou.top.TopActivity;

public class LoginActivity extends BaseActivity {

    private PatternLockView patternLockView ;
    private TextView patternGuide;
    private EditText nameEdit;
    private EditText telEdit;

    private String patternFirst = "";
    private boolean isFirstPattern = true;
    private HLLButton patternRedo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        telEdit = findViewById(R.id.tel);
        nameEdit = findViewById(R.id.name);

        patternLockView = findViewById(R.id.pattern_lock);
        patternGuide = findViewById(R.id.pattern_guide);
        patternRedo = findViewById(R.id.pattern_retry);
        patternRedo.setVisibility(View.GONE);
        patternRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isFirstPattern = true;
               patternFirst = "";
               patternLockView.clearPattern();
               patternGuide.setTextColor(getColor(R.color.white));
               patternGuide.setText(getString(R.string.login_pattern_guide_first));
                patternRedo.setVisibility(View.GONE);
            }
        });

        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                if(isFirstPattern){
                    patternFirst = "";
                }
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String patternStr = PatternLockUtils.patternToString(patternLockView, pattern);
                HLLog.showLog("LoginActivity", "PatternLockViewListener", isFirstPattern + "&" + patternStr);
                Editable name = nameEdit.getText();
                Editable tel = telEdit.getText();
                HLLog.showLog("LoginActivity", "PatternLockViewListener", name + "&" + tel);
                if (name == null || tel == null || name.toString().equals("") || tel.toString().equals("")) {
                    HLLAlert.showAlert(LoginActivity.this, R.string.login_error_message);
                    patternLockView.clearPattern();
                } else {
                    if (isFirstPattern) {
                        patternGuide.setTextColor(getColor(R.color.white));
                        patternFirst = patternStr;
                        isFirstPattern = false;
                        patternGuide.setText(getString(R.string.login_pattern_guide_final));
                        patternLockView.clearPattern();
                        patternRedo.setVisibility(View.VISIBLE);
                    } else {

                        if (patternFirst != "" && patternFirst.equals(patternStr)) {
                            //設定に成功するので、パターンを保存する
                            Utils.decryptString(Utils.PATTERN_ALISA, patternStr);
                            patternGuide.setTextColor(getColor(R.color.white));
                            patternGuide.setText(getString(R.string.login_pattern_guide_success));
                            //TODO:非同期需要做
                            saveInfo();
                        } else {
                            patternGuide.setTextColor(getColor(R.color.red));
                            if (patternFirst.equals("")) {
                                patternGuide.setText(getString(R.string.login_pattern_guide_retry));
                                isFirstPattern = true;
                            } else {
                                patternGuide.setText(getString(R.string.login_pattern_guide_nomacth));
                            }
                        }
                    }
                }
            }

            @Override
            public void onCleared() {

            }
        });
    }

    private void saveInfo(){
        String name = nameEdit.getText().toString();
        String tel = telEdit.getText().toString();
        PersonSqlDao personSqlDao = new PersonSqlDao(this);

        Person person = new Person();
        person.setTel(tel);
        person.setName(name);
        person.setType("1");
        //TODO:画像还未处理
        person.setIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        boolean result = personSqlDao.insert(person);
        if(!result){
            HLLAlert.showAlert(LoginActivity.this,R.string.login_error_message1);
            isFirstPattern = true;
            patternFirst = "";
            patternLockView.clearPattern();
            patternGuide.setTextColor(getColor(R.color.white));
            patternGuide.setText(getString(R.string.login_pattern_guide_first));
            patternRedo.setVisibility(View.GONE);
        }else{
            Intent intent = new Intent(LoginActivity.this, TopActivity.class);
            startActivity(intent);
        }
    }
}
