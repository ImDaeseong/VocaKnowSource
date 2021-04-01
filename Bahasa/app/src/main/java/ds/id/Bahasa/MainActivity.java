package ds.id.Bahasa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import ds.id.Bahasa.Common.KataSetting;
import ds.id.Bahasa.Controls.FloatingTextView;
import ds.id.Bahasa.Controls.OnSingleClickListener;
import ds.id.Bahasa.Controls.PermissionUtil;
import ds.id.Bahasa.Controls.ScreenLockutil;
import ds.id.Bahasa.Controls.UnitUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static MainActivity mainActivity;
    public static MainActivity getMainActivity(){
        return  mainActivity;
    }
    public static void setMainActivity(MainActivity mainActivity){
        MainActivity.mainActivity = mainActivity;
    }

    private int nSkinStyle;
    private int nBookResource;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;

    private TabLayout tabLayout;
    private View divider_line;

    private FloatingTextView floatingTextView;

    private FloatingActionButton btnsetting;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.setMainActivity(this);

        InitTitleBar();

        setContentView(R.layout.activity_main);

        initFragment();

        divider_line = (View) findViewById(R.id.divider_line);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                SelectIndex(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //설정
        btnsetting = (FloatingActionButton)findViewById(R.id.btnsetting);
        btnsetting.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {

                    Setting();

                    //설정 후 숨김
                    btnsetting.hide();

                }catch (Exception e){
                }
            }
        });

        Initdivider();

        setTabItemImage();

        SelectIndex(0);

        //가로/세로 모드 시작 체크
        Configuration configuration = Resources.getSystem().getConfiguration();
        if(configuration.orientation == Configuration.ORIENTATION_PORTRAIT){

            //Log.e(TAG, "portrait");

            BahasaApplication.getInstance().setOrientation(true);
        }else {

            //Log.e(TAG, "landscape");

            BahasaApplication.getInstance().setOrientation(false);
        }

        //1o초 후 자동으로 설정 버튼을 숨김다.
        checkbtnSetting();

        //스크린 화면 락상태 설정
        getScreenLock();

        //initfloatingTextView();

        //권한 체크
        checkPermission();
    }

    private void checkPermission(){

        if ( PermissionUtil.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) &&
                PermissionUtil.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) &&
                PermissionUtil.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                PermissionUtil.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ) {

            //Log.e(TAG, "권한 있음");
        } else  {

            //Log.e(TAG, "권한이 없으면 권한 요청");
            PermissionUtil.requestPermissions(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try{

            if (countDownTimer != null)
                countDownTimer.cancel();

        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //Log.e(TAG, "onConfigurationChanged");

        if ( newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            BahasaApplication.getInstance().setOrientation(true);

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            BahasaApplication.getInstance().setOrientation(false);
        }

        if(fragment1.getActivity() != null){
            fragment1.changeDisplay();
        }

        if(fragment2.getActivity() != null){
            fragment2.changeDisplay();
        }

        if(fragment3.getActivity() != null){
            fragment3.changeDisplay();
        }
    }

    private void checkbtnSetting(){

        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                try {

                    //Log.e(TAG, "현재 경과:" + (millisUntilFinished / 1000) + "초");
                }catch (Exception ex){
                    ex.getMessage().toString();
                }
            }

            @Override
            public void onFinish() {

                try{
                    //설정 후 숨김
                    btnsetting.hide();
                }catch (Exception ex){
                    ex.getMessage().toString();
                }
            }
        };
        countDownTimer.start();
    }

    private void InitTitleBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            nSkinStyle = BahasaApplication.getInstance().getSkinStyle();
            if(nSkinStyle == 0) {
                window.setStatusBarColor(getResources().getColor(R.color.skin_statusbar1));
            } else if(nSkinStyle == 1) {
                window.setStatusBarColor(getResources().getColor(R.color.skin_statusbar2));
            }  else if(nSkinStyle == 2) {
                window.setStatusBarColor(getResources().getColor(R.color.skin_statusbar3));
            } else if(nSkinStyle == 3) {
                window.setStatusBarColor(getResources().getColor(R.color.skin_statusbar4));
            } else if(nSkinStyle == 4) {
                window.setStatusBarColor(getResources().getColor(R.color.skin_statusbar5));
            }  else if(nSkinStyle == 5) {
                window.setStatusBarColor(getResources().getColor(R.color.skin_statusbar6));
            } else if(nSkinStyle == 6) {
                window.setStatusBarColor(getResources().getColor(R.color.skin_statusbar7));
            }
        }
    }

    private void Initdivider(){

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {
            divider_line.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline1));
        } else if(nSkinStyle == 1) {
            divider_line.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline2));
        }  else if(nSkinStyle == 2) {
            divider_line.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline3));
        } else if(nSkinStyle == 3) {
            divider_line.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline4));
        } else if(nSkinStyle == 4) {
            divider_line.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline5));
        }  else if(nSkinStyle == 5) {
            divider_line.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline6));
        } else if(nSkinStyle == 6) {
            divider_line.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline7));
        }
    }

    private void setTabItemImage(){

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {
            nBookResource  = R.drawable.book1;
        } else if(nSkinStyle == 1) {
            nBookResource  = R.drawable.book2;
        }  else if(nSkinStyle == 2) {
            nBookResource  = R.drawable.book3;
        } else if(nSkinStyle == 3) {
            nBookResource  = R.drawable.book4;
        } else if(nSkinStyle == 4) {
            nBookResource  = R.drawable.book5;
        }  else if(nSkinStyle == 5) {
            nBookResource  = R.drawable.book6;
        } else if(nSkinStyle == 6) {
            nBookResource  = R.drawable.book7;
        }

        tabLayout.getTabAt(0).setIcon(nBookResource);
        tabLayout.getTabAt(1).setIcon(nBookResource);
        tabLayout.getTabAt(2).setIcon(nBookResource);
        tabLayout.getTabAt(3).setIcon(nBookResource);
    }

    private void initFragment(){
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
    }

    private void SelectIndex(int nIndex){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        switch (nIndex){
            case 0:
                fragmentTransaction.replace(R.id.frameLayout, fragment1);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.setCustomAnimations(R.anim.slide_bounce1, R.anim.slide_bounce2);
                fragmentTransaction.replace(R.id.frameLayout, fragment2);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.setCustomAnimations(R.anim.slide_bounce1, R.anim.slide_bounce2);
                fragmentTransaction.replace(R.id.frameLayout, fragment3);
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.frameLayout, fragment4);
                fragmentTransaction.commit();
                break;
        }
    }

    public void SelectTab(int nIndex, String SelectMenuItem){

        try {

            Bundle bundle = new Bundle();
            bundle.putString("MenuItem", SelectMenuItem);
            fragment2.setArguments(bundle);
            tabLayout.getTabAt(nIndex).select();
        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }

    public void Setting(){

        startActivity(new Intent(this, SettingActivity.class));

        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }

    private void ChangeSkinStyle(){

        InitTitleBar();

        setTabItemImage();

        Initdivider();
    }

    public void hideKeyboard() {

        try {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }catch (Exception ex){
            Log.e(TAG, ex.getMessage());
        }
    }

    private void initfloatingTextView(){

        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        int screenLength = size.y;

        ViewGroup rootView = (ViewGroup)findViewById(R.id.clMain);
        floatingTextView = new FloatingTextView(rootView);
        floatingTextView.getFloatingview().setVisibility(View.VISIBLE);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) floatingTextView.getFloatingview().getLayoutParams();
        layoutParams.setMargins(0, (screenLength - UnitUtils.dip2px(this, 160)),0,0);
        floatingTextView.getFloatingview().setLayoutParams(layoutParams);
        rootView.addView(floatingTextView.getFloatingview());
    }

    public void setChangeSetting(){

        ChangeSkinStyle();

        if(fragment1.getActivity() != null){
            fragment1.setChangeSetting();
        }

        if(fragment2.getActivity() != null){
            fragment2.setChangeSetting();
        }

        if(fragment3.getActivity() != null){
            fragment3.setChangeSetting();
        }

        if(fragment4.getActivity() != null){
            fragment4.setChangeSetting();
        }
    }

    public void getScreenLock(){

        try {
            boolean bLock = KataSetting.getInstance().getScreenLock();
            ScreenLockutil.Lock_Flag(getWindow(), bLock);
        }catch (Exception e){
        }
    }
}
