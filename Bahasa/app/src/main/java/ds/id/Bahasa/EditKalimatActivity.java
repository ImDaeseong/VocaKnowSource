package ds.id.Bahasa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ds.id.Bahasa.Common.KataSetting;
import ds.id.Bahasa.Controls.OnSingleClickListener;
import ds.id.Bahasa.Controls.RecycleUtil;
import ds.id.Bahasa.Database.KataManager;

public class EditKalimatActivity extends AppCompatActivity {

    private static final String TAG = EditKalimatActivity.class.getSimpleName();

    private KataManager kataManager;

    private int nSkinStyle;

    private View include;
    private TextView tvTitle;
    private View cLbtn_enter;

    private View cLEdit;
    private EditText et1, et2;
    private Button btnSet;
    private String sSaveKataIndo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitTitleBar();
        setContentView(R.layout.activity_edit_kalimat);

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
        tvTitle.setText("문장편집");

        cLEdit = findViewById(R.id.cLEdit);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        btnSet = findViewById(R.id.btnSet);

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

                String KataIndo = et1.getText().toString();
                if( TextUtils.isEmpty( KataIndo )){
                    return;
                }

                String lembutlidah = et2.getText().toString();
                if( TextUtils.isEmpty( lembutlidah )){
                    return;
                }

                //업데이트
                kataManager.UpdateKalimat(KataIndo, lembutlidah, "Terimakasih telah bekerja dengan baik.");

                kataManager.UpdateKalimat(KataIndo, lembutlidah, sSaveKataIndo);

                Intent intent = new Intent();
                intent.putExtra("KataIndo", KataIndo);
                intent.putExtra("lembutlidah", lembutlidah);
                setResult(1, intent);

                finish();
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);

                BahasaApplication.getInstance().Toast(EditKalimatActivity.this, "선택된 문장이 수정 되었습니다.", false);
            }
        });
    }

    private void InitData(){

        try {

            kataManager = KataManager.getInstance(this);

            Intent intent = new Intent(this.getIntent());
            String KataIndo = intent.getStringExtra("KataIndo");
            String lembutlidah = intent.getStringExtra("lembutlidah");

            sSaveKataIndo = KataIndo;

            et1.setText(KataIndo);
            et2.setText(lembutlidah);

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

        nSkinStyle = KataSetting.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin1));
            btnSet.setBackgroundResource(R.drawable.btn_border1);

        } else if(nSkinStyle == 1) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin2));
            btnSet.setBackgroundResource(R.drawable.btn_border2);

        }  else if(nSkinStyle == 2) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin3));
            btnSet.setBackgroundResource(R.drawable.btn_border3);

        } else if(nSkinStyle == 3) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin4));
            btnSet.setBackgroundResource(R.drawable.btn_border4);

        } else if(nSkinStyle == 4) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin5));
            btnSet.setBackgroundResource(R.drawable.btn_border5);

        }  else if(nSkinStyle == 5) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin6));
            btnSet.setBackgroundResource(R.drawable.btn_border6);

        } else if(nSkinStyle == 6) {

            include.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7));

            btnSet.setTextColor(ContextCompat.getColor(this, R.color.skin7));
            btnSet.setBackgroundResource(R.drawable.btn_border7);
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
