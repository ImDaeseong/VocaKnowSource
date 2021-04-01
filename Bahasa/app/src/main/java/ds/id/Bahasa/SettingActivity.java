package ds.id.Bahasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import ds.id.Bahasa.Common.KataSetting;
import ds.id.Bahasa.Controls.OnSingleClickListener;
import ds.id.Bahasa.Controls.RecycleUtil;
import ds.id.Bahasa.Controls.RoundImageView;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = SettingActivity.class.getSimpleName();

    private int nSkinStyle;

    private View main;

    private View include;
    private TextView tvTitle;
    private View cLbtn_enter;

    private View cLInfo;
    private RoundImageView ivInfo;

    private EditText et1, et2;
    private Switch sw1, sw2, sw3;

    private TextView tvskin;

    private View color_item1, color_item2, color_item3, color_item4, color_item5, color_item6, color_item7;
    private View vColor1,vColor2, vColor3, vColor4, vColor5, vColor6, vColor7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitTitleBar();
        setContentView(R.layout.activity_setting);

        init();

        InitSkinStyle();

        InitData();
    }

    @Override
    public void onBackPressed() {

        hideKeyboard();

        finish();
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {

            RecycleUtil.recursiveRecycle(getWindow().getDecorView());
            System.gc();

        }catch (Exception e){
        }
    }

    private void init(){

        main = findViewById(R.id.main);

        include = findViewById(R.id.bahasa_toolbar);

        tvTitle = (TextView)include.findViewById(R.id.tvTitle);
        tvTitle.setText("문장편집");

        cLInfo = findViewById(R.id.cLInfo);
        ivInfo = findViewById(R.id.ivInfo);

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);

        tvskin = findViewById(R.id.tvskin);

        sw1 = (Switch)findViewById(R.id.sw1);
        sw2 = (Switch)findViewById(R.id.sw2);
        sw3 = (Switch)findViewById(R.id.sw3);

        //화면 클릭시
        main.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                hideKeyboard();
            }
        });

        //종료
        cLbtn_enter = (View) include.findViewById(R.id.cLbtn_enter);
        cLbtn_enter.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                hideKeyboard();

                finish();
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
            }
        });

        //사용법 호출
        cLInfo.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                hideKeyboard();
            }
        });

        //문장 자동 반복
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    if (!TextUtils.isEmpty(s.toString())) {

                        int nKataTime = Integer.parseInt(s.toString());
                        KataSetting.getInstance().setKataTime(nKataTime);
                        BahasaApplication.getInstance().setKataTime(nKataTime);
                        MainActivity.getMainActivity().setChangeSetting();
                    }

                } catch (Exception e){
                }
            }
        });

        //단어장 자동 반복
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    if (!TextUtils.isEmpty(s.toString())) {

                        int nWordKataTime = Integer.parseInt(s.toString());
                        KataSetting.getInstance().setWordKataTime(nWordKataTime);
                        BahasaApplication.getInstance().setWordKataTime(nWordKataTime);
                        MainActivity.getMainActivity().setChangeSetting();
                    }

                } catch (Exception e){
                }
            }
        });

        //문장 음성 켜기
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try {

                    KataSetting.getInstance().setKalimatSound(isChecked);
                    BahasaApplication.getInstance().setKalimatSound(isChecked);
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

        //단어장 녹음 음성 켜기
        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try {

                    KataSetting.getInstance().setKataSound(isChecked);
                    BahasaApplication.getInstance().setKataSound(isChecked);
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

        //스크린 화면 항상 켜기
        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try {

                    KataSetting.getInstance().setScreenLock(isChecked);
                    BahasaApplication.getInstance().setScreenLock(isChecked);
                    MainActivity.getMainActivity().getScreenLock();
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

        //스킨 선택
        color_item1 = (View)findViewById(R.id.color_skin_item1);
        vColor1 = (View) color_item1.findViewById(R.id.vColor);
        vColor1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {

                    KataSetting.getInstance().setSkinStyle(0);
                    BahasaApplication.getInstance().setSkinStyle(0);
                    InitSkinStyle();
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

        color_item2 = (View)findViewById(R.id.color_skin_item2);
        vColor2 = (View) color_item2.findViewById(R.id.vColor);
        vColor2.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {

                    KataSetting.getInstance().setSkinStyle(1);
                    BahasaApplication.getInstance().setSkinStyle(1);
                    InitSkinStyle();
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

        color_item3 = (View)findViewById(R.id.color_skin_item3);
        vColor3 = (View) color_item3.findViewById(R.id.vColor);
        vColor3.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {

                    KataSetting.getInstance().setSkinStyle(2);
                    BahasaApplication.getInstance().setSkinStyle(2);
                    InitSkinStyle();
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

        color_item4 = (View)findViewById(R.id.color_skin_item4);
        vColor4 = (View) color_item4.findViewById(R.id.vColor);
        vColor4.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {

                    KataSetting.getInstance().setSkinStyle(3);
                    BahasaApplication.getInstance().setSkinStyle(3);
                    InitSkinStyle();
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

        color_item5 = (View)findViewById(R.id.color_skin_item5);
        vColor5 = (View) color_item5.findViewById(R.id.vColor);
        vColor5.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {

                    KataSetting.getInstance().setSkinStyle(4);
                    BahasaApplication.getInstance().setSkinStyle(4);
                    InitSkinStyle();
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

        color_item6 = (View)findViewById(R.id.color_skin_item6);
        vColor6 = (View) color_item6.findViewById(R.id.vColor);
        vColor6.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {

                    KataSetting.getInstance().setSkinStyle(5);
                    BahasaApplication.getInstance().setSkinStyle(5);
                    InitSkinStyle();
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

        color_item7 = (View)findViewById(R.id.color_skin_item7);
        vColor7 = (View) color_item7.findViewById(R.id.vColor);
        vColor7.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {

                    KataSetting.getInstance().setSkinStyle(6);
                    BahasaApplication.getInstance().setSkinStyle(6);
                    InitSkinStyle();
                    MainActivity.getMainActivity().setChangeSetting();

                }catch (Exception e){
                }

            }
        });

    }

    private void InitData(){

        try {

            String sVersion = KataSetting.getInstance().getKataVersion();
            String sPlayNumber = KataSetting.getInstance().getKataPlayNumber();
            int nKataTime = KataSetting.getInstance().getKataTime();
            int nWordKataTime = KataSetting.getInstance().getWordKataTime();
            //int nKataTimeRepeat = KataSetting.getInstance().setKataTimeRepeat();
            //int nWordKataTimeRepeat = KataSetting.getInstance().getWordKataTimeRepeat();

            boolean bKalimatSoundToggle = KataSetting.getInstance().getKalimatSound();
            boolean bSoundToggle = KataSetting.getInstance().getKataSound();
            boolean bScreenLockToggle = KataSetting.getInstance().getScreenLock();

            String sKataTime = String.format("%d", nKataTime);
            String sWordKataTime = String.format("%d", nWordKataTime);

            //문장 자동 반복 시간
            et1.setText(sKataTime);

            //단어장 자동 반복 시간
            et2.setText(sWordKataTime);

            //문장 음성 지원
            sw1.setChecked(bKalimatSoundToggle);

            //단어장 녹음 지원
            sw2.setChecked(bSoundToggle);

            //스크린 화면 항상 켜기 지원
            sw3.setChecked(bScreenLockToggle);

        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }

    private void InitTitleBar(){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void InitSkinStyle(){

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1));

            ivInfo.setColorFilter(ContextCompat.getColor(this, R.color.skin1), PorterDuff.Mode.SRC_ATOP);

            Drawable drawable = getResources().getDrawable(R.drawable.switch_thumb_selector);
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.skin1), PorterDuff.Mode.SRC_ATOP);
            sw1.setThumbDrawable(drawable);
            sw2.setThumbDrawable(drawable);
            sw3.setThumbDrawable(drawable);

            tvskin.setTextColor(ContextCompat.getColor(this, R.color.skin1));

        } else if(nSkinStyle == 1) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2));

            ivInfo.setColorFilter(ContextCompat.getColor(this, R.color.skin2), PorterDuff.Mode.SRC_ATOP);

            Drawable drawable = getResources().getDrawable(R.drawable.switch_thumb_selector);
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.skin2), PorterDuff.Mode.SRC_ATOP);
            sw1.setThumbDrawable(drawable);
            sw2.setThumbDrawable(drawable);
            sw3.setThumbDrawable(drawable);

            tvskin.setTextColor(ContextCompat.getColor(this, R.color.skin2));

        }  else if(nSkinStyle == 2) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3));

            ivInfo.setColorFilter(ContextCompat.getColor(this, R.color.skin3), PorterDuff.Mode.SRC_ATOP);

            Drawable drawable = getResources().getDrawable(R.drawable.switch_thumb_selector);
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.skin3), PorterDuff.Mode.SRC_ATOP);
            sw1.setThumbDrawable(drawable);
            sw2.setThumbDrawable(drawable);
            sw3.setThumbDrawable(drawable);

            tvskin.setTextColor(ContextCompat.getColor(this, R.color.skin3));

        } else if(nSkinStyle == 3) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4));

            ivInfo.setColorFilter(ContextCompat.getColor(this, R.color.skin4), PorterDuff.Mode.SRC_ATOP);

            Drawable drawable = getResources().getDrawable(R.drawable.switch_thumb_selector);
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.skin4), PorterDuff.Mode.SRC_ATOP);
            sw1.setThumbDrawable(drawable);
            sw2.setThumbDrawable(drawable);
            sw3.setThumbDrawable(drawable);

            tvskin.setTextColor(ContextCompat.getColor(this, R.color.skin4));

        } else if(nSkinStyle == 4) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5));

            ivInfo.setColorFilter(ContextCompat.getColor(this, R.color.skin5), PorterDuff.Mode.SRC_ATOP);

            Drawable drawable = getResources().getDrawable(R.drawable.switch_thumb_selector);
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.skin5), PorterDuff.Mode.SRC_ATOP);
            sw1.setThumbDrawable(drawable);
            sw2.setThumbDrawable(drawable);
            sw3.setThumbDrawable(drawable);

            tvskin.setTextColor(ContextCompat.getColor(this, R.color.skin5));

        }  else if(nSkinStyle == 5) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6));

            ivInfo.setColorFilter(ContextCompat.getColor(this, R.color.skin6), PorterDuff.Mode.SRC_ATOP);

            Drawable drawable = getResources().getDrawable(R.drawable.switch_thumb_selector);
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.skin6), PorterDuff.Mode.SRC_ATOP);
            sw1.setThumbDrawable(drawable);
            sw2.setThumbDrawable(drawable);
            sw3.setThumbDrawable(drawable);

            tvskin.setTextColor(ContextCompat.getColor(this, R.color.skin6));

        } else if(nSkinStyle == 6) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7));

            ivInfo.setColorFilter(ContextCompat.getColor(this, R.color.skin7), PorterDuff.Mode.SRC_ATOP);

            Drawable drawable = getResources().getDrawable(R.drawable.switch_thumb_selector);
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.skin7), PorterDuff.Mode.SRC_ATOP);
            sw1.setThumbDrawable(drawable);
            sw2.setThumbDrawable(drawable);
            sw3.setThumbDrawable(drawable);

            tvskin.setTextColor(ContextCompat.getColor(this, R.color.skin7));
        }
    }

    private void hideKeyboard() {

        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }catch (Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }
}
