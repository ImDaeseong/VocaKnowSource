package ds.id.Bahasa;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import ds.id.Bahasa.Controls.SharedPreferencesUtil;

public class BahasaApplication extends Application {

    private static final String TAG = BahasaApplication.class.getSimpleName();

    private static BahasaApplication mInstance;
    public static synchronized BahasaApplication getInstance() {
        return mInstance;
    }

    private static Context mContext;
    public static Context getAppContext(){
        return mContext;
    }

    private static Toast toast;

    //현재 ORIENTATION_PORTRAIT 여부
    private static boolean mbOrientation = false;
    public static boolean getOrientation(){ return  mbOrientation; }
    public static void setOrientation(boolean bOrientation) { mbOrientation = bOrientation;}



    //스킨 타입
    private static int mSkinStyle = 0;
    public static int getSkinStyle(){ return  mSkinStyle; }
    public static void setSkinStyle(int nSkinStyle) { mSkinStyle = nSkinStyle;}

    //문장 자동 반복 시간
    private static int mKataTime = 0;
    public static int getKataTime(){ return  mKataTime; }
    public static void setKataTime(int nKataTime) { mKataTime = nKataTime;}

    //단어장 자동 반복 시간
    private static int mWordKataTime = 0;
    public static int getWordKataTime(){ return  mWordKataTime; }
    public static void setWordKataTime(int nWordKataTime) { mWordKataTime = nWordKataTime;}

    //문장 음성 지원
    private static boolean mbKalimatSoundToggle = false;
    public static boolean getKalimatSound(){ return  mbKalimatSoundToggle; }
    public static void setKalimatSound(boolean bKalimatSoundToggle) { mbKalimatSoundToggle = bKalimatSoundToggle;}

    //단어장 녹음 지원
    private static boolean mbSoundToggle = false;
    public static boolean getKataSound(){ return  mbSoundToggle; }
    public static void setKataSound(boolean bSoundToggle) { mbSoundToggle = bSoundToggle;}

    //스크린 화면 항상 켜기 지원
    private static boolean mbScreenLockToggle = false;
    public static boolean getScreenLock(){ return  mbScreenLockToggle; }
    public static void setScreenLock(boolean bScreenLockToggle) { mbScreenLockToggle = bScreenLockToggle;}


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mContext = getApplicationContext();

        try {
            SharedPreferencesUtil.getInstance().init(this);
        }catch (Exception e){
        }
    }

    public static void Toast(Context context, String sMsg, boolean bLengthLong) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_layout, null);

        int nSkinStyle = BahasaApplication.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {
            view.setBackgroundResource(R.drawable.menu1_style);
        } else if(nSkinStyle == 1) {
            view.setBackgroundResource(R.drawable.menu2_style);
        }  else if(nSkinStyle == 2) {
            view.setBackgroundResource(R.drawable.menu3_style);
        } else if(nSkinStyle == 3) {
            view.setBackgroundResource(R.drawable.menu4_style);
        } else if(nSkinStyle == 4) {
            view.setBackgroundResource(R.drawable.menu5_style);
        }  else if(nSkinStyle == 5) {
            view.setBackgroundResource(R.drawable.menu6_style);
        } else if(nSkinStyle == 6) {
            view.setBackgroundResource(R.drawable.menu7_style);
        }

        TextView tvtoast = view.findViewById(R.id.tvtoast);
        tvtoast.setMaxLines(1);//최대 1줄까지
        tvtoast.setTextColor(Color.parseColor("#ffffff"));
        tvtoast.setText(sMsg);

        toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 0);

        if(bLengthLong) {
            toast.setDuration(Toast.LENGTH_LONG);
        }else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }

        toast.setView(view);
        toast.show();
    }

    public static void Toastcancel(){
        try {
            toast.cancel();
        }catch (Exception e){
        }
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
