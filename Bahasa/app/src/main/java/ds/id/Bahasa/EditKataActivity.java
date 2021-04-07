package ds.id.Bahasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import ds.id.Bahasa.Controls.AnimatorUtil;
import ds.id.Bahasa.Controls.AudioPlayer;
import ds.id.Bahasa.Controls.AudioRecorder;
import ds.id.Bahasa.Controls.FileUtils;
import ds.id.Bahasa.Controls.OnSingleClickListener;
import ds.id.Bahasa.Controls.PlayerTimer;
import ds.id.Bahasa.Controls.RecycleUtil;
import ds.id.Bahasa.Controls.RoundImageView;
import ds.id.Bahasa.Database.KataManager;

public class EditKataActivity extends AppCompatActivity {

    private static final String TAG = EditKataActivity.class.getSimpleName();

    private KataManager kataManager;

    private int nSkinStyle;

    private View include;
    private TextView tvTitle;
    private View cLbtn_enter;

    private View cLEdit;
    private EditText et1, et2, et3;
    private Button btnSet, btnEdit, btnDelete;

    private View cL5, cL6;
    private TextView lblRecord;

    private RoundImageView btnTextRecord;

    private String sSavedKataIndo;

    private TextView currentTime, totalTime;
    private ProgressBar pb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitTitleBar();
        setContentView(R.layout.activity_edit_kata);

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

        include = findViewById(R.id.bahasa_toolbar);

        tvTitle = (TextView)include.findViewById(R.id.tvTitle);
        tvTitle.setText("단어 추가");

        cLEdit = findViewById(R.id.cLEdit);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);

        //저장 버튼
        btnSet = findViewById(R.id.btnSet);

        //수정 버튼
        btnEdit = findViewById(R.id.btnEdit);

        //삭제 버튼
        btnDelete = findViewById(R.id.btnDelete);

        //녹음 시작 버튼
        cL5 = (View) findViewById(R.id.cL5);

        //녹음 내용 듣기 버튼
        cL6 = (View)findViewById(R.id.cL6);

        //녹음 버튼 텍스트
        lblRecord = findViewById(R.id.lblRecord);

        //녹음 내용 시간 설정
        currentTime = (TextView)findViewById(R.id.currentTime);
        totalTime = (TextView)findViewById(R.id.totalTime);

        //프로그래스바
        pb1 = findViewById(R.id.pb1);

        //음성 설정 버튼
        btnTextRecord = findViewById(R.id.btnTextRecord);

        //키보드 숨김
        cLEdit.setOnClickListener(new OnSingleClickListener() {
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

        //저장
        btnSet.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                hideKeyboard();

                String sKataIndo = et1.getText().toString();
                if( TextUtils.isEmpty( sKataIndo )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "외국어1 정보를 입력해주세요.", false);
                    return;
                }

                String sKataIndoTambah = et2.getText().toString();
                if( TextUtils.isEmpty( sKataIndoTambah )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "외국어2 정보를 입력해주세요.", false);
                    return;
                }

                String sKataKor = et3.getText().toString();
                if( TextUtils.isEmpty( sKataKor )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "한국어 정보를 입력해주세요.", false);
                    return;
                }

                //추가
                kataManager.InsertRegKata(sKataIndo, sKataIndoTambah, sKataKor);

                Intent intent = new Intent();
                intent.putExtra("KataIndo", sKataIndo);
                intent.putExtra("KataIndoTambah", sKataIndoTambah);
                intent.putExtra("KataKor", sKataKor);
                setResult(2, intent);

                finish();
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

                BahasaApplication.getInstance().Toast(EditKataActivity.this, "새로운 단어가 등록 되었습니다.", false);

            }
        });

        //수정
        btnEdit.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                hideKeyboard();

                String sKataIndo = et1.getText().toString();
                if( TextUtils.isEmpty( sKataIndo )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "외국어1 정보를 입력해주세요.", false);
                    return;
                }

                String sKataIndoTambah = et2.getText().toString();
                if( TextUtils.isEmpty( sKataIndoTambah )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "외국어2 정보를 입력해주세요.", false);
                    return;
                }

                String sKataKor = et3.getText().toString();
                if( TextUtils.isEmpty( sKataKor )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "한국어 정보를 입력해주세요.", false);
                    return;
                }

                //수정
                kataManager.UpdateRegKata(sSavedKataIndo, sKataIndo, sKataIndoTambah, sKataKor);

                Intent intent = new Intent();
                intent.putExtra("KataIndo", sKataIndo);
                intent.putExtra("KataIndoTambah", sKataIndoTambah);
                intent.putExtra("KataKor", sKataKor);
                setResult(2, intent);

                finish();
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

                BahasaApplication.getInstance().Toast(EditKataActivity.this, "선택된 단어가 수정 되었습니다.", false);

            }
        });

        //삭제
        btnDelete.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                hideKeyboard();

                String sKataIndo = et1.getText().toString();
                if( TextUtils.isEmpty( sKataIndo )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "외국어1 정보를 입력해주세요.", false);
                    return;
                }

                String sKataIndoTambah = et2.getText().toString();
                if( TextUtils.isEmpty( sKataIndoTambah )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "외국어2 정보를 입력해주세요.", false);
                    return;
                }

                String sKataKor = et3.getText().toString();
                if( TextUtils.isEmpty( sKataKor )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "한국어 정보를 입력해주세요.", false);
                    return;
                }

                //삭제
                kataManager.DeleteRegKata(sSavedKataIndo);

                Intent intent = new Intent();
                intent.putExtra("KataIndo", sKataIndo);
                intent.putExtra("KataIndoTambah", sKataIndoTambah);
                intent.putExtra("KataKor", sKataKor);
                setResult(2, intent);

                finish();
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

                BahasaApplication.getInstance().Toast(EditKataActivity.this, "선택된 단어가 삭제 되었습니다.", false);

            }
        });

        //음성 설정 버튼
        btnTextRecord.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                hideKeyboard();

                String sKataIndo = et1.getText().toString();
                if( TextUtils.isEmpty( sKataIndo )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "녹음할 단어를 입력해주세요.", false);
                    return;
                }

                String sKataIndoTambah = et2.getText().toString();
                if( TextUtils.isEmpty( sKataIndoTambah )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "녹음할 단어를 입력해주세요.", false);
                    return;
                }

                String sKataKor = et3.getText().toString();
                if( TextUtils.isEmpty( sKataKor )){
                    BahasaApplication.getInstance().Toast(EditKataActivity.this, "녹음할 단어를 입력해주세요.", false);
                    return;
                }

                if(cL5.getVisibility() == View.VISIBLE){

                    AnimatorUtil.AnimatoTopToBottom(cL5);
                    AnimatorUtil.AnimatoTopToBottom(cL6);
                    //cL5.setVisibility(View.GONE);
                    //cL6.setVisibility(View.GONE);
                }else {

                    cL5.setVisibility(View.VISIBLE);
                    cL6.setVisibility(View.VISIBLE);
                    AnimatorUtil.AnimatoBottomToTop(cL5);
                    AnimatorUtil.AnimatoBottomToTop(cL6);
                }
            }
        });

        //녹음 시작 버튼
        cL5.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                hideKeyboard();

            }
        });

        //녹음 내용 듣기 버튼
        cL6.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                hideKeyboard();
            }
        });

    }

    private void InitData(){

        try {

            kataManager = KataManager.getInstance(this);

            Intent intent = new Intent(this.getIntent());
            String sKataIndo = intent.getStringExtra("KataIndo");
            String sKataIndoTambah = intent.getStringExtra("KataIndoTambah");
            String sKataKor = intent.getStringExtra("KataKor");
            sSavedKataIndo = sKataIndo;

            if(TextUtils.isEmpty(sKataIndo)){

                tvTitle.setText("단어 추가");

                btnSet.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);

            }else {

                tvTitle.setText("단어 편집");

                btnSet.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
            }

            et1.setText(sKataIndo);
            et2.setText(sKataIndoTambah);
            et3.setText(sKataKor);

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

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin1));
            btnSet.setBackgroundResource(R.drawable.btn_border1);

            btnEdit.setTextColor(ContextCompat.getColor(this, R.color.skin1));
            btnEdit.setBackgroundResource(R.drawable.btn_border1);

            btnDelete.setTextColor(ContextCompat.getColor(this, R.color.skin1));
            btnDelete.setBackgroundResource(R.drawable.btn_border1);

            cL5.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1));
            cL6.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1));

        } else if(nSkinStyle == 1) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin2));
            btnSet.setBackgroundResource(R.drawable.btn_border2);

            btnEdit.setTextColor(ContextCompat.getColor(this, R.color.skin2));
            btnEdit.setBackgroundResource(R.drawable.btn_border2);

            btnDelete.setTextColor(ContextCompat.getColor(this, R.color.skin2));
            btnDelete.setBackgroundResource(R.drawable.btn_border2);

            cL5.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2));
            cL6.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2));

        }  else if(nSkinStyle == 2) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin3));
            btnSet.setBackgroundResource(R.drawable.btn_border3);

            btnEdit.setTextColor(ContextCompat.getColor(this, R.color.skin3));
            btnEdit.setBackgroundResource(R.drawable.btn_border3);

            btnDelete.setTextColor(ContextCompat.getColor(this, R.color.skin3));
            btnDelete.setBackgroundResource(R.drawable.btn_border3);

            cL5.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3));
            cL6.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3));

        } else if(nSkinStyle == 3) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin4));
            btnSet.setBackgroundResource(R.drawable.btn_border4);

            btnEdit.setTextColor(ContextCompat.getColor(this, R.color.skin4));
            btnEdit.setBackgroundResource(R.drawable.btn_border4);

            btnDelete.setTextColor(ContextCompat.getColor(this, R.color.skin4));
            btnDelete.setBackgroundResource(R.drawable.btn_border4);

            cL5.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4));
            cL6.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4));

        } else if(nSkinStyle == 4) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin5));
            btnSet.setBackgroundResource(R.drawable.btn_border5);

            btnEdit.setTextColor(ContextCompat.getColor(this, R.color.skin5));
            btnEdit.setBackgroundResource(R.drawable.btn_border5);

            btnDelete.setTextColor(ContextCompat.getColor(this, R.color.skin5));
            btnDelete.setBackgroundResource(R.drawable.btn_border5);

            cL5.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5));
            cL6.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5));

        }  else if(nSkinStyle == 5) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin6));
            btnSet.setBackgroundResource(R.drawable.btn_border6);

            btnEdit.setTextColor(ContextCompat.getColor(this, R.color.skin6));
            btnEdit.setBackgroundResource(R.drawable.btn_border6);

            btnDelete.setTextColor(ContextCompat.getColor(this, R.color.skin6));
            btnDelete.setBackgroundResource(R.drawable.btn_border6);

            cL5.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6));
            cL6.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6));

        } else if(nSkinStyle == 6) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin7));
            btnSet.setBackgroundResource(R.drawable.btn_border7);

            btnEdit.setTextColor(ContextCompat.getColor(this, R.color.skin7));
            btnEdit.setBackgroundResource(R.drawable.btn_border7);

            btnDelete.setTextColor(ContextCompat.getColor(this, R.color.skin7));
            btnDelete.setBackgroundResource(R.drawable.btn_border7);

            cL5.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7));
            cL6.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7));
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

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //Log.e(TAG, "onConfigurationChanged");
    }
}
