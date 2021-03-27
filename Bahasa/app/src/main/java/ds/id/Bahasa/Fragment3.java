package ds.id.Bahasa;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import ds.id.Bahasa.Common.KataSetting;
import ds.id.Bahasa.Controls.AnimatorUtil;
import ds.id.Bahasa.Controls.MarqueeTask;
import ds.id.Bahasa.Controls.OnSingleClickListener;
import ds.id.Bahasa.Controls.RoundImageView;
import ds.id.Bahasa.Controls.SwipeConstraintLayout;
import ds.id.Bahasa.Controls.TextToSpeechUtil;
import ds.id.Bahasa.Controls.UnitUtils;
import ds.id.Bahasa.Database.KataManager;
import ds.id.Bahasa.Database.regkataItems;


public class Fragment3 extends Fragment {

    private static final String TAG = Fragment3.class.getSimpleName();

    private Context mContext;
    private View MainView;

    private KataManager kataManager;

    private String sMenu;


    private View cLChange;
    private ImageView ivChange;

    private View cL1;
    private TextView CounterTimer;

    private SwipeConstraintLayout Kataview;
    private TextView KataIndo, KataKor, lembutlidah;

    private View cL2;
    private TextView mView;

    private RoundImageView upPrePayImage, upNextplayImage;

    private int CurrentPlayIndex;
    private int nTotalPageCount;
    private regkataItems item;
    List<regkataItems> mCollection;

    private TextToSpeechUtil textToSpeechUtil;

    private MarqueeTask task;
    private Timer timer = null;

    private Timer playtimer = null;

    private int nRepeatCount;
    private int nRepeatTime = 0;
    private int nSkinStyle;
    private int nSkinStyleResource;
    private int nSwipeResource;
    private boolean bIsAutoPlay;
    private boolean bUseSound;

    private Handler handler = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Log.e(TAG, "onCreateView");

        mContext = container.getContext();
        MainView = inflater.inflate(R.layout.fragment_3, container, false);

        return MainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Log.e(TAG, "onViewCreated");

        init();

        changeDisplay();

        InitSkinStyle();

        DisplayMarquee();

        InitData();

        CurrentKata();
    }

    @Override
    public void onPause() {
        super.onPause();

        //Log.e(TAG, "onPause");

        try {

            if (bIsAutoPlay) {
                RunAutoPlay();
                DisplayMarquee();
            }
        }catch (Exception e){
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //Log.e(TAG, "onResume");

        try {

            //다시 돌아왔을 경우 재시작을 자동으로
            /*
            if (bIsAutoPlay) {
                RunAutoPlay();
                DisplayMarquee();
            }
            */
        }catch (Exception e){
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //Log.e(TAG, "onDestroyView");

        try {
            handler.removeMessages(0);
            closeTimer();
            closePlayTimer();
        }catch (Exception e){
        }
    }

    private void init(){

        cLChange = (View) MainView.findViewById(R.id.cLChange);
        ivChange = (ImageView) cLChange.findViewById(R.id.ivChange);
        ivChange.setBackgroundColor(Color.parseColor("#66000000"));
        cLChange.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {

                    if (!bIsAutoPlay) {

                        bIsAutoPlay = true;
                        ivChange.setBackgroundResource(nSkinStyleResource);
                        RunAutoPlay();
                    } else {

                        bIsAutoPlay = false;
                        ivChange.setBackgroundColor(Color.parseColor("#66000000"));
                        RunAutoPlay();
                    }

                    IsVisibleBottomArrow();

                    DisplayMarquee();

                }catch (Exception e){
                }
            }
        });

        cL1 = (View) MainView.findViewById(R.id.cL1);
        CounterTimer = (TextView) cL1.findViewById(R.id.CounterTimer);

        Kataview = (SwipeConstraintLayout)MainView.findViewById(R.id.Kataview);
        KataIndo = (TextView) Kataview.findViewById(R.id.KataIndo);
        KataKor = (TextView) Kataview.findViewById(R.id.KataKor);
        lembutlidah = (TextView) Kataview.findViewById(R.id.lembutlidah);

        cL2 = (View) MainView.findViewById(R.id.cL2);
        mView = (TextView) cL2.findViewById(R.id.mView);

        upPrePayImage  = (RoundImageView) MainView.findViewById(R.id.upPrePayImage);
        upPrePayImage.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                try {
                    PreKata();
                }catch (Exception e){
                }
            }
        });

        upNextplayImage  = (RoundImageView) MainView.findViewById(R.id.upNextplayImage);
        upNextplayImage.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                try {
                    NextKata();
                }catch (Exception e){
                }
            }
        });

        Kataview.setOnSwipeListener(new SwipeConstraintLayout.OnSwipeConstraintListener() {
            @Override
            public void swipeLeft() {
                try {

                    PreKata();
                }catch (Exception e){
                }
            }

            @Override
            public void swipeRight() {
                try {

                    NextKata();
                }catch (Exception e){
                }
            }

            @Override
            public void swipeUp() {
            }

            @Override
            public void swipeDown() {
            }
        });

        handler = new Handler();

        textToSpeechUtil = new TextToSpeechUtil(mContext);

        //사운드 사용 여부
        bUseSound = KataSetting.getInstance().getKalimatSound();

        //가나다라... 자동 반복 시간
        nRepeatTime = KataSetting.getInstance().getKataTime();
    }

    private void InitData(){

        try {

            kataManager = KataManager.getInstance(mContext);
            mCollection = kataManager.GetRegItems();
            nTotalPageCount = mCollection.size();
            CurrentPlayIndex = 0;

            //Log.e(TAG, "mCollection:"+ mCollection);
            //Log.e(TAG, "nTotalPageCount:"+ nTotalPageCount);

        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }

    private void InitSkinStyle(){

        nSkinStyle = KataSetting.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {

            nSwipeResource  = R.drawable.skin1_style;
            nSkinStyleResource = R.color.skin1;
        } else if(nSkinStyle == 1) {

            nSwipeResource  = R.drawable.skin2_style;
            nSkinStyleResource = R.color.skin2;
        }  else if(nSkinStyle == 2) {

            nSwipeResource  = R.drawable.skin3_style;
            nSkinStyleResource = R.color.skin3;
        } else if(nSkinStyle == 3) {

            nSwipeResource  = R.drawable.skin4_style;
            nSkinStyleResource = R.color.skin4;
        } else if(nSkinStyle == 4) {

            nSwipeResource  = R.drawable.skin5_style;
            nSkinStyleResource = R.color.skin5;
        }  else if(nSkinStyle == 5) {

            nSwipeResource  = R.drawable.skin6_style;
            nSkinStyleResource = R.color.skin6;
        } else if(nSkinStyle == 6) {

            nSwipeResource  = R.drawable.skin7_style;
            nSkinStyleResource = R.color.skin7;
        }

        Kataview.setBackgroundResource(nSwipeResource);
        CounterTimer.setTextColor(getResources().getColor(nSkinStyleResource));
        mView.setTextColor(getResources().getColor(nSkinStyleResource));

        upPrePayImage.setBackgroundColor(getResources().getColor(nSkinStyleResource));
        upNextplayImage.setBackgroundColor(getResources().getColor(nSkinStyleResource));
    }

    private void CurrentKata()
    {
        try
        {
            //DB
            if (CurrentPlayIndex < 0 || nTotalPageCount == 0) return;

            //Log.e(TAG, "CurrentPlayIndex:" + CurrentPlayIndex);
            item = mCollection.get(CurrentPlayIndex);//kataManager.GetRegItems();
            KataIndo.setText(item.getKataIndo());
            lembutlidah.setText(item.getKataIndoTambah());
            KataKor.setText(item.getKataKor());

            CounterTimerLabel();

            //현재 프레그멘트 활성화시만 음성 출력
            PlaySuaraSound(item.getKataIndo());

        }catch (Exception e){
        }
    }

    private  void PreKata()
    {
        try
        {
            //DB
            if (nTotalPageCount == 0) return;

            AnimatorUtil.AnimatoSwipeLeft(Kataview);

            CurrentPlayIndex--;
            if (CurrentPlayIndex < 0)
                CurrentPlayIndex = nTotalPageCount - 1;

            //Log.e(TAG, "CurrentPlayIndex:" + CurrentPlayIndex);
            item = mCollection.get(CurrentPlayIndex);//kataManager.GetRegItems();
            KataIndo.setText(item.getKataIndo());
            lembutlidah.setText(item.getKataIndoTambah());
            KataKor.setText(item.getKataKor());

            if (!bIsAutoPlay)
                CounterTimerLabel();

            PlaySuaraSound(item.getKataIndo());

            if (bIsAutoPlay) nRepeatCount = 0;

        } catch(Exception e) {
        }
    }

    private void NextKata()
    {
        try
        {
            //DB
            if (nTotalPageCount == 0) return;

            AnimatorUtil.AnimatoSwipeRight(Kataview);

            CurrentPlayIndex++;
            if (CurrentPlayIndex > (nTotalPageCount - 1))
                CurrentPlayIndex = 0;

            //Log.e(TAG, "CurrentPlayIndex:" + CurrentPlayIndex);
            item = mCollection.get(CurrentPlayIndex);//kataManager.GetRegItems();
            KataIndo.setText(item.getKataIndo());
            lembutlidah.setText(item.getKataIndoTambah());
            KataKor.setText(item.getKataKor());

            if (!bIsAutoPlay)
                CounterTimerLabel();

            PlaySuaraSound(item.getKataIndo());

            if (bIsAutoPlay) nRepeatCount = 0;

        } catch(Exception e) {
        }
    }

    private void closeTimer(){
        try {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }catch (Exception e){
        }
    }

    private void startTimer(){
        try {
            closeTimer();
            task = new MarqueeTask(mView);
            timer = new Timer();
            timer.schedule(task, 0, 20000);
        }catch (Exception e){
        }
    }

    private void DisplayMarquee()
    {
        String ContentMarquee = "";

        if (bIsAutoPlay){

            if(bUseSound){
                ContentMarquee = String.format("지금은 사운드와 같이 실행중입니다.");
            } else {
                ContentMarquee = String.format("지금은 %d초마다 자동 실행중입니다.", nRepeatTime);
            }

        } else{

            ContentMarquee = String.format("Play 버튼을 클릭하시면 %d초마다 자동 실행합니다.", nRepeatTime);
        }
        mView.setText(ContentMarquee);

        startTimer();
    }

    private void CounterTimerLabel()
    {
        try {
            int nDisplayIndex = CurrentPlayIndex + 1;
            String sPage = String.format("현재 단어 (%d/%d)", nDisplayIndex, nTotalPageCount);
            CounterTimer.setTextColor(getResources().getColor(nSkinStyleResource));
            CounterTimer.setText(sPage);
        }catch (Exception e){
        }
    }

    private void PlaySuaraSound(final String sText)
    {
        try
        {
            if (bUseSound)
            {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (textToSpeechUtil.isLanguageAvailable()) {
                            textToSpeechUtil.Speak(sText);
                        } else {
                            BahasaApplication.getInstance().Toast(mContext, "음성지원이 되지 않습니다.", false);
                        }
                    }
                }, 1000);
            }
        } catch(Exception e) {
        }
    }

    private void closePlayTimer(){
        try {
            if (playtimer != null) {
                playtimer.cancel();
                playtimer = null;
            }
        }catch (Exception e){
        }
    }

    private void RunAutoPlay()
    {
        closePlayTimer();

        playtimer = new Timer();
        playtimer.schedule(new TimerTask() {
            @Override
            public void run() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            if (!bIsAutoPlay)
                            {
                                nRepeatCount = 0;
                                CounterTimerLabel();
                                return;
                            }

                            nRepeatCount++;

                            if (nRepeatCount % 2 == 0)
                            {
                                CounterTimer.setTextColor(getResources().getColor(nSkinStyleResource));
                            }
                            else
                            {
                                CounterTimer.setTextColor(Color.TRANSPARENT);
                            }

                            int nDisplayIndex = CurrentPlayIndex + 1;
                            String sPage = String.format("%d초 경과 (%d/%d)", nRepeatCount, nDisplayIndex, nTotalPageCount);
                            CounterTimer.setText(sPage);

                            //사운드 사용시
                            if (bUseSound)
                            {
                                if (textToSpeechUtil.IsSpeaking()) return;
                            }

                            if (nRepeatTime <= nRepeatCount)
                            {
                                nRepeatCount = 0;
                                NextKata();
                            }

                        }catch (Exception e){
                        }
                    }
                });
            }
        }, 0, 1000);

    }

    private void ChangeSkinStyle(){

        nSkinStyle = KataSetting.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {

            nSwipeResource  = R.drawable.skin1_style;
            nSkinStyleResource = R.color.skin1;
        } else if(nSkinStyle == 1) {

            nSwipeResource  = R.drawable.skin2_style;
            nSkinStyleResource = R.color.skin2;
        }  else if(nSkinStyle == 2) {

            nSwipeResource  = R.drawable.skin3_style;
            nSkinStyleResource = R.color.skin3;
        } else if(nSkinStyle == 3) {

            nSwipeResource  = R.drawable.skin4_style;
            nSkinStyleResource = R.color.skin4;
        } else if(nSkinStyle == 4) {

            nSwipeResource  = R.drawable.skin5_style;
            nSkinStyleResource = R.color.skin5;
        }  else if(nSkinStyle == 5) {

            nSwipeResource  = R.drawable.skin6_style;
            nSkinStyleResource = R.color.skin6;
        } else if(nSkinStyle == 6) {

            nSwipeResource  = R.drawable.skin7_style;
            nSkinStyleResource = R.color.skin7;
        }

        Kataview.setBackgroundResource(nSwipeResource);
        CounterTimer.setTextColor(getResources().getColor(nSkinStyleResource));
        mView.setTextColor(getResources().getColor(nSkinStyleResource));

        upPrePayImage.setBackgroundColor(getResources().getColor(nSkinStyleResource));
        upNextplayImage.setBackgroundColor(getResources().getColor(nSkinStyleResource));
    }

    public void changeDisplay(){

        try {

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            final Point size = new Point();
            display.getSize(size);
            int screenWidth = size.x;
            int screenHeight = size.y;

            if (BahasaApplication.getInstance().getOrientation()) {

                ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams) Kataview.getLayoutParams();
                lp1.height = (screenHeight / 2);
                lp1.leftMargin = UnitUtils.dip2px(mContext, 5f);
                lp1.rightMargin = UnitUtils.dip2px(mContext, 5f);
                Kataview.setLayoutParams(lp1);

                ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) cL1.getLayoutParams();
                lp2.leftMargin = UnitUtils.dip2px(mContext, 10f);
                lp2.rightMargin = UnitUtils.dip2px(mContext, 10f);
                cL1.setLayoutParams(lp2);

                ConstraintLayout.LayoutParams lp3 = (ConstraintLayout.LayoutParams) cL2.getLayoutParams();
                lp3.leftMargin = UnitUtils.dip2px(mContext, 10f);
                lp3.rightMargin = UnitUtils.dip2px(mContext, 10f);
                cL2.setLayoutParams(lp3);

            } else {

                ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) Kataview.getLayoutParams();
                lp.height = (screenHeight / 2);
                lp.leftMargin = UnitUtils.dip2px(mContext, 100f);
                lp.rightMargin = UnitUtils.dip2px(mContext, 100f);
                Kataview.setLayoutParams(lp);

                ConstraintLayout.LayoutParams lp2 = (ConstraintLayout.LayoutParams) cL1.getLayoutParams();
                lp2.leftMargin = UnitUtils.dip2px(mContext, 100f);
                lp2.rightMargin = UnitUtils.dip2px(mContext, 100f);
                cL1.setLayoutParams(lp2);

                ConstraintLayout.LayoutParams lp3 = (ConstraintLayout.LayoutParams) cL2.getLayoutParams();
                lp3.leftMargin = UnitUtils.dip2px(mContext, 10f);
                lp3.rightMargin = UnitUtils.dip2px(mContext, 10f);
                cL2.setLayoutParams(lp3);
            }

            IsVisibleBottomArrow();

        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }

    private void IsVisibleBottomArrow()
    {
        if (BahasaApplication.getInstance().getOrientation()){

            upPrePayImage.setVisibility(View.GONE);
            upNextplayImage.setVisibility(View.GONE);

        } else {

            if (bIsAutoPlay) {
                upPrePayImage.setVisibility(View.GONE);
                upNextplayImage.setVisibility(View.GONE);
            } else {
                upPrePayImage.setVisibility(View.VISIBLE);
                upNextplayImage.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setChangeSetting(){

        ChangeSkinStyle();

        //사운드 사용 여부
        bUseSound = KataSetting.getInstance().getKalimatSound();

        //가나다라... 자동 반복 시간
        nRepeatTime = KataSetting.getInstance().getKataTime();
    }
}