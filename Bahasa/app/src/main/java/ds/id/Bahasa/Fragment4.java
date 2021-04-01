package ds.id.Bahasa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.ArrayList;
import ds.id.Bahasa.Controls.OnSingleClickListener;
import ds.id.Bahasa.Controls.RoundImageView;
import ds.id.Bahasa.Database.KataManager;
import ds.id.Bahasa.Database.regkataItems;

public class Fragment4 extends Fragment {

    private static final String TAG = Fragment4.class.getSimpleName();

    private Context mContext;
    private View MainView;

    private View divider_line;

    private int nSkinStyle;
    private int nSkinStyleResource;

    private KataManager kataManager;


    private EditText searchET;

    private RecyclerView rv1;
    private RegKataAdapter regKataAdapter;
    private ArrayList<regkataItems> items = new ArrayList<regkataItems>();

    private RoundImageView fab1, fab2;

    String sKataIndo, sKataIndoTambah, sKataKor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = container.getContext();
        MainView = inflater.inflate(R.layout.fragment_4, container, false);

        return MainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        InitSkinStyle();

        InitData();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{

            //데이터 수정 완료시 전체 화면을 초기화
            sKataIndo = "";
            sKataIndoTambah = "";
            sKataKor = "";

            //미선택 전체 데이터 제거
            items.clear();
            regKataAdapter = new RegKataAdapter(mContext, items);
            rv1.setAdapter(regKataAdapter);
            regKataAdapter.notifyDataSetChanged();

            fab2.setVisibility(View.GONE);
            fab1.setBackgroundColor(Color.parseColor("#66000000"));
            fab2.setBackgroundColor(Color.parseColor("#EDEDED"));

        }catch (Exception ex){
            ex.getMessage().toString();
        }
    }

    private void init(){

        //RecyclerView
        rv1 = (RecyclerView) MainView.findViewById(R.id.rv1);

        //EditText
        searchET = (EditText) MainView.findViewById(R.id.searchET);

        //line
        divider_line = (View)MainView.findViewById(R.id.divider_line);

        //단어 검색
        searchTextWatcher watcher = new searchTextWatcher();
        searchET.addTextChangedListener(watcher);

        //RecyclerView 클릭시 키보드 숨김
        rv1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try{
                    ((MainActivity)getActivity()).hideKeyboard();
                }catch (Exception e){
                }
                return false;
            }
        });

        //enter key 입력시 키보드 숨김
        searchET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == event.KEYCODE_ENTER){

                    try{

                        ((MainActivity)getActivity()).hideKeyboard();

                    }catch (Exception e){
                    }
                    return true;
                }
                return false;
            }
        });

        //제일 아래 버튼
        fab1 = (RoundImageView) MainView.findViewById(R.id.fab1);
        fab1.setBackgroundColor(Color.parseColor("#66000000"));
        fab1.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                ((MainActivity)getActivity()).hideKeyboard();
                searchET.setText("");

                if(fab2.getVisibility() == View.VISIBLE){

                    sKataIndo = "";
                    sKataIndoTambah = "";
                    sKataKor = "";

                    //미선택 전체 데이터 제거
                    items.clear();
                    regKataAdapter = new RegKataAdapter(mContext, items);
                    rv1.setAdapter(regKataAdapter);
                    regKataAdapter.notifyDataSetChanged();

                    fab2.setVisibility(View.GONE);
                    fab1.setBackgroundColor(Color.parseColor("#66000000"));
                    fab2.setBackgroundColor(Color.parseColor("#EDEDED"));
                }else {

                    //선택시 전체 데이터 로드
                    items.clear();
                    items = kataManager.GetRegItems();
                    regKataAdapter = new RegKataAdapter(mContext, items);
                    regKataAdapter.setOnItemClickListener(new RegKataAdapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(View v, int position) {

                            ((MainActivity)getActivity()).hideKeyboard();

                            //선택된 그리드의 데이터
                            regkataItems selectItem = items.get(position);
                            sKataIndo = selectItem.getKataIndo();
                            sKataIndoTambah = selectItem.getKataIndoTambah();
                            sKataKor = selectItem.getKataKor();

                            fab2.setBackgroundColor(Color.parseColor("#FFC10E"));
                        }
                    });
                    rv1.setAdapter(regKataAdapter);
                    regKataAdapter.notifyDataSetChanged();

                    fab2.setVisibility(View.VISIBLE);
                    fab1.setBackgroundColor(ContextCompat.getColor(mContext, nSkinStyleResource));
                }
            }
        });

        //두번째 추가 버튼
        fab2 = (RoundImageView) MainView.findViewById(R.id.fab2);
        fab2.setBackgroundColor(Color.parseColor("#EDEDED"));
        fab2.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                ((MainActivity)getActivity()).hideKeyboard();

                Intent intent = new Intent(mContext, EditKataActivity.class);
                intent.putExtra("KataIndo", sKataIndo);
                intent.putExtra("KataIndoTambah", sKataIndoTambah);
                intent.putExtra("KataKor", sKataKor);
                startActivityForResult(intent, 2);

                getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
            }
        });
    }

    private void InitData(){

        kataManager = KataManager.getInstance(mContext);

        //items = kataManager.GetRegItems();
        rv1.setLayoutManager(new LinearLayoutManager(mContext));
        regKataAdapter = new RegKataAdapter(mContext, items);
        rv1.setAdapter(regKataAdapter);
    }

    private void InitSkinStyle(){

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {

            nSkinStyleResource = R.color.skin1;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin1), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline1));
        } else if(nSkinStyle == 1) {

            nSkinStyleResource = R.color.skin2;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin2), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline2));
        }  else if(nSkinStyle == 2) {

            nSkinStyleResource = R.color.skin3;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin3), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline3));
        } else if(nSkinStyle == 3) {

            nSkinStyleResource = R.color.skin4;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin4), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline4));
        } else if(nSkinStyle == 4) {

            nSkinStyleResource = R.color.skin5;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin5), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline5));
        }  else if(nSkinStyle == 5) {

            nSkinStyleResource = R.color.skin6;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin6), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline6));
        } else if(nSkinStyle == 6) {

            nSkinStyleResource = R.color.skin7;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin7), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline7));
        }
    }

    private void ChangeSkinStyle(){

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle();
        if(nSkinStyle == 0) {

            nSkinStyleResource = R.color.skin1;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin1), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline1));
        } else if(nSkinStyle == 1) {

            nSkinStyleResource = R.color.skin2;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin2), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline2));
        }  else if(nSkinStyle == 2) {

            nSkinStyleResource = R.color.skin3;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin3), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline3));
        } else if(nSkinStyle == 3) {

            nSkinStyleResource = R.color.skin4;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin4), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline4));
        } else if(nSkinStyle == 4) {

            nSkinStyleResource = R.color.skin5;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin5), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline5));
        }  else if(nSkinStyle == 5) {

            nSkinStyleResource = R.color.skin6;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin6), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline6));
        } else if(nSkinStyle == 6) {

            nSkinStyleResource = R.color.skin7;

            Drawable drawable = getResources().getDrawable(R.drawable.search);
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.skin7), PorterDuff.Mode.SRC_ATOP);
            searchET.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);

            divider_line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.skin_divideline7));
        }
    }

    public void setChangeSetting(){

        ChangeSkinStyle();

        if(fab2.getVisibility() == View.VISIBLE){
            fab1.setBackgroundColor(Color.parseColor("#66000000"));
        }else {
            fab1.setBackgroundColor(ContextCompat.getColor(mContext, nSkinStyleResource));
        }
    }

    class searchTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            try {

                if (TextUtils.isEmpty(s.toString())) {

                    items.clear();
                    regKataAdapter = new RegKataAdapter(mContext, items);
                    rv1.setAdapter(regKataAdapter);
                    regKataAdapter.notifyDataSetChanged();
                } else {

                    items.clear();
                    items = kataManager.GetRegSearchKata(s.toString());
                    regKataAdapter = new RegKataAdapter(mContext, items);
                    regKataAdapter.setOnItemClickListener(new RegKataAdapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(View v, int position) {

                            //regkataItems item = items.get(position);
                            //Log.e(TAG, item.toString());
                        }
                    });
                    rv1.setAdapter(regKataAdapter);
                    regKataAdapter.notifyDataSetChanged();
                }

            } catch (Exception e){
                Log.e(TAG, e.getMessage().toString());
            }
        }
    }

}
