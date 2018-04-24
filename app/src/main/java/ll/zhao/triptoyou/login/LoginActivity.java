package ll.zhao.triptoyou.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import ll.zhao.triptoyou.BaseActivity;
import ll.zhao.triptoyou.HLLog;
import ll.zhao.triptoyou.MainActivity;
import ll.zhao.triptoyou.R;
import ll.zhao.triptoyou.Utils;
import ll.zhao.triptoyou.top.TopActivity;

public class LoginActivity extends BaseActivity {

    private PatternLockView patternLockView ;
    private TextView patternGuide;

    private String patternFirst = "";
    private boolean isFirstPattern = true;
    private Button patternRedo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                String patternStr = PatternLockUtils.patternToString(patternLockView,pattern);
                HLLog.showLog("LoginActivity","PatternLockViewListener", isFirstPattern + "&" + patternStr);
                if(isFirstPattern){
                    patternGuide.setTextColor(getColor(R.color.white));
                    patternFirst = patternStr;
                    isFirstPattern = false;
                    patternGuide.setText(getString(R.string.login_pattern_guide_final));
                    patternLockView.clearPattern();
                    patternRedo.setVisibility(View.VISIBLE);
                }else{

                    if(patternFirst != "" && patternFirst.equals(patternStr)){
                        //設定に成功するので、パターンを保存する
                        Utils.decryptString(Utils.PATTERN_ALISA,patternStr);
                        patternGuide.setTextColor(getColor(R.color.white));
                        patternGuide.setText(getString(R.string.login_pattern_guide_success));
                        Intent intent = new Intent(LoginActivity.this, TopActivity.class);
                        startActivity(intent);
                    }else{
                        patternGuide.setTextColor(getColor(R.color.red));
                        if(patternFirst.equals("")){
                            patternGuide.setText(getString(R.string.login_pattern_guide_retry));
                            isFirstPattern = true;
                        }else{
                            patternGuide.setText(getString(R.string.login_pattern_guide_nomacth));
                        }
                    }
                }
            }

            @Override
            public void onCleared() {

            }
        });


    }
}
