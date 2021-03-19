package ds.id.Bahasa;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.ScrollView;
import ds.id.Bahasa.Common.KataSetting;
import ds.id.Bahasa.Common.gMenuItem;
import ds.id.Bahasa.Controls.AnimatorUtil;
import ds.id.Bahasa.Controls.OnSingleClickListener;

public class Fragment1 extends Fragment {

    private static final String TAG = Fragment1.class.getSimpleName();

    private Context mContext;
    private View MainView;
    private ScrollView scrollview;
    private GridLayout gridlayout;

    private int nSkinStyle;
    private int nSkinStyleResource;

    private final int numRows = 15;
    private final int numCols = 2;

    private String[][] titlelist =
            {       {"일상생활/인사,소개", "일상생활/날씨,여가"},
                    {"일상생활/시장,교통", "일상생활/문화,관습"},
                    {"일상생활/건강,병원", "일상생활/음식,식생활"},
                    {"일상생활/은행,송금", "일상생활/기타"},
                    {"작업지시/근무시작,끝", "작업지시/근무태도"},
                    {"작업지시/작업공구", "작업지시/안전규칙"},
                    {"작업지시/작업규칙등기타", "기숙사및식당/기숙사안내"},
                    {"기숙사및식당/기숙사규칙", "기숙사및식당/기숙사환경"},
                    {"기숙사및식당/식당안내", "기숙사및식당/식당규칙"},
                    {"근로관련/급여,수당", "근로관련/회사,근무규칙"},
                    {"근로관련/채권채무", "근로관련/고용허가서등"},
                    {"근로관련/각종보험", "근로관련/기타"},
                    {"고용관련신고/입사", "고용관련신고/퇴사"},
                    {"고용관련신고/사업장변경", "고용관련신고/출입국"},
                    {"고용관련신고/고용지원센터", "고용관련신고/기타사항"}
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = container.getContext();
        MainView = inflater.inflate(R.layout.fragment_1, container, false);

        return MainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        changeDisplay();

        InitData();
    }

    private void init(){

        scrollview = (ScrollView)MainView.findViewById(R.id.scrollview);
        gridlayout = (GridLayout)MainView.findViewById(R.id.gridlayout);

        scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                try {

                    if(scrollview != null){
                        if (scrollview.getChildAt(0).getBottom() <= (scrollview.getHeight() + scrollview.getScrollY())) {

                        } else {

                        }
                    }
                }catch (Exception e){
                }
            }
        });

        InitSkinStyle();
    }

    private void InitSkinStyle(){

        nSkinStyle = KataSetting.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {
            nSkinStyleResource = R.drawable.menu1_style;
        } else if(nSkinStyle == 1) {
            nSkinStyleResource = R.drawable.menu2_style;
        }  else if(nSkinStyle == 2) {
            nSkinStyleResource = R.drawable.menu3_style;
        } else if(nSkinStyle == 3) {
            nSkinStyleResource = R.drawable.menu4_style;
        } else if(nSkinStyle == 4) {
            nSkinStyleResource = R.drawable.menu5_style;
        }  else if(nSkinStyle == 5) {
            nSkinStyleResource = R.drawable.menu6_style;
        } else if(nSkinStyle == 6) {
            nSkinStyleResource = R.drawable.menu7_style;
        }
    }

    private void InitData(){

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        gridlayout.setColumnCount(numCols);
        gridlayout.setRowCount(numRows);

        //int nMinheight = (screenHeight / numRows);
        //Log.e(TAG, "nMinheight:" + nMinheight);

        for (int i = 0; i < numRows; i++) {
            for(int j=0; j < numCols; j++){

                gMenuItem gMenuItem = new gMenuItem(mContext);
                gMenuItem.setText(titlelist[i][j]);
                gMenuItem.setSkinResource(nSkinStyleResource);
                gMenuItem.setOnClickListener(new OnSingleClickListener() {

                    @Override
                    public void onSingleClick(View v) {

                        String sText = ((gMenuItem)v).getText();
                        if(!TextUtils.isEmpty(sText)){

                            AnimatorUtil.AnimatoRotate(v, sText);
                        }
                    }
                });

                gridlayout.addView(gMenuItem, (screenWidth /numCols), 135);//(screenHeight / numRows));
            }
        }
    }

    private void ChangeSkinStyle(){

        InitSkinStyle();

        int count = gridlayout.getChildCount();
        for(int i = 0 ; i <count ; i++){
            gMenuItem child = (gMenuItem)gridlayout.getChildAt(i);
            child.setSkinResource(nSkinStyleResource);
        }
    }

    public void changeDisplay(){

        try {

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            final Point size = new Point();
            display.getSize(size);
            int screenWidth = size.x;
            int screenHeight = size.y;

            int count = gridlayout.getChildCount();
            for (int i = 0; i < count; i++) {
                gMenuItem child = (gMenuItem) gridlayout.getChildAt(i);
                ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) child.getLayoutParams();
                lp.width = (screenWidth / numCols);
                lp.height = 135;
                child.setLayoutParams(lp);
            }
        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }

    public void setChangeSetting(){

        ChangeSkinStyle();

    }
}