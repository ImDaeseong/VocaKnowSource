package ds.id.Bahasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import ds.id.Bahasa.Common.KataSetting;
import ds.id.Bahasa.Controls.RecycleUtil;
import ds.id.Bahasa.Database.CopyDBfile;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private int nSkinStyle;

    private Handler handler = null;
    private View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitTitleBar();
        setContentView(R.layout.activity_splash);

        init();

        InitSkinStyle();

        InitData();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //백버튼 기능 막음
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {

            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }

            RecycleUtil.recursiveRecycle(getWindow().getDecorView());
            System.gc();

        }catch (Exception e){
        }
    }

    private void init(){

        mainView = findViewById(android.R.id.content);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        SplashActivity.this.finish();
                        break;
                }
            }
        };
    }

    private void InitData(){

        try{

            boolean bCopy = new CopyDBTask().execute(SplashActivity.this).get();

        }catch (Exception e){
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                try{

                    //현재 미사용으로 주석 처리
                    /*
                    //스킨 타입
                    int nSkinStyle = KataSetting.getInstance().getSkinStyle();
                    BahasaApplication.getInstance().setSkinStyle(nSkinStyle);

                    //문장 자동 반복 시간
                    int nKataTime = KataSetting.getInstance().getKataTime();
                    BahasaApplication.getInstance().setKataTime(nKataTime);

                    //단어장 자동 반복 시간
                    int nWordKataTime = KataSetting.getInstance().getWordKataTime();
                    BahasaApplication.getInstance().setWordKataTime(nWordKataTime);

                    //문장 음성 지원
                    boolean bKalimatSoundToggle = KataSetting.getInstance().getKalimatSound();
                    BahasaApplication.getInstance().setKalimatSound(bKalimatSoundToggle);

                    //단어장 녹음 지원
                    boolean bSoundToggle = KataSetting.getInstance().getKataSound();
                    BahasaApplication.getInstance().setKataSound(bSoundToggle);

                    //스크린 화면 항상 켜기 지원
                    boolean bScreenLockToggle = KataSetting.getInstance().getScreenLock();
                    BahasaApplication.getInstance().setScreenLock(bScreenLockToggle);
                    */

                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    handler.sendMessage(msg);

                }catch (Exception e){
                }
            }
        }, 1000);
    }

    private void InitTitleBar(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void InitSkinStyle(){

        nSkinStyle = KataSetting.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {
            mainView.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1));
        } else if(nSkinStyle == 1) {
            mainView.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2));
        }  else if(nSkinStyle == 2) {
            mainView.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3));
        } else if(nSkinStyle == 3) {
            mainView.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4));
        } else if(nSkinStyle == 4) {
            mainView.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5));
        }  else if(nSkinStyle == 5) {
            mainView.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6));
        } else if(nSkinStyle == 6) {
            mainView.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7));
        }
    }

    private static class CopyDBTask extends AsyncTask<Context, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Context... params) {
            try {

                new CopyDBfile(params[0]);
                return true;

            } catch (Exception e) {
            }
            return false;
        }
    }
}
