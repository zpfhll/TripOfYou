package ll.zhao.triptoyou.login;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import ll.zhao.tripdatalibrary.PersonSqlDao;
import ll.zhao.tripdatalibrary.model.PersonModel;
import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.HLLog;
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
        patternRedo.setOnClickListener(this);

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

        PersonModel personModel = new PersonModel();
        personModel.setTel(tel);
        personModel.setName(name);
        personModel.setType("1");
        //TODO:画像还未处理
        personModel.setIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
        boolean result = personSqlDao.insert(personModel);
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
            Utils.saveDateToShare(this,Utils.LOGIN_FLAG,"1");
        }
    }

    @Override
    protected void baseOnClick(View v) {
        if(clickIsDone) {
            super.baseOnClick(v);
            clickIsDone = false;
            switch (v.getId()) {
                case R.id.pattern_retry:
                    isFirstPattern = true;
                    patternFirst = "";
                    patternLockView.clearPattern();
                    patternGuide.setTextColor(getColor(R.color.white));
                    patternGuide.setText(getString(R.string.login_pattern_guide_first));
                    patternRedo.setVisibility(View.GONE);
                    break;
            }
            clickIsDone = true;
        }
    }
}
