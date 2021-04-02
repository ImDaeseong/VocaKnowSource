package ds.id.Bahasa

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ds.id.Bahasa.Controls.OnSingleClickListener
import ds.id.Bahasa.Controls.RoundImageView
import ds.id.Bahasa.Database.KataManager
import ds.id.Bahasa.Database.regkataItems
import java.util.*

class Fragment4 : Fragment() {

    companion object {
        private val tag = Fragment4::class.java.simpleName
    }

    private var mContext: Context? = null
    private var MainView: View? = null

    private var divider_line: View? = null

    private var nSkinStyle = 0
    private var nSkinStyleResource = 0

    private var kataManager: KataManager? = null

    private var searchET: EditText? = null

    private var rv1: RecyclerView? = null
    private var regKataAdapter: RegKataAdapter? = null
    private var items: ArrayList<regkataItems>? = ArrayList()

    private var fab1: RoundImageView? = null
    private var fab2: RoundImageView? = null

    var sKataIndo: String? = null
    var sKataIndoTambah: String? = null
    var sKataKor: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mContext = container!!.context
        MainView = inflater.inflate(R.layout.fragment_4, container, false)
        return MainView
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        InitSkinStyle()
        InitData()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {

            //데이터 수정 완료시 전체 화면을 초기화
            sKataIndo = ""
            sKataIndoTambah = ""
            sKataKor = ""

            //미선택 전체 데이터 제거
            items!!.clear()
            regKataAdapter = RegKataAdapter(mContext!!, items)
            rv1!!.adapter = regKataAdapter
            regKataAdapter!!.notifyDataSetChanged()

            fab2!!.visibility = View.GONE
            fab1!!.setBackgroundColor(Color.parseColor("#66000000"))
            fab2!!.setBackgroundColor(Color.parseColor("#EDEDED"))

        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    private fun init() {

        //RecyclerView
        rv1 = MainView!!.findViewById<View>(R.id.rv1) as RecyclerView

        //EditText
        searchET = MainView!!.findViewById<View>(R.id.searchET) as EditText

        //line
        divider_line = MainView!!.findViewById(R.id.divider_line) as View

        //단어 검색
        val watcher: searchTextWatcher = searchTextWatcher()
        searchET!!.addTextChangedListener(watcher)

        //RecyclerView 클릭시 키보드 숨김
        rv1!!.setOnTouchListener { v, event ->

            try {
                MainActivity.mActivity?.hideKeyboard()
            } catch (e: Exception) {
            }
            false
        }

        //enter key 입력시 키보드 숨김
        searchET!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->

            if (keyCode == KeyEvent.KEYCODE_ENTER) {

                try {
                    MainActivity.mActivity?.hideKeyboard()
                } catch (e: Exception) {
                }
                return@OnKeyListener true
            }
            false
        })

        //제일 아래 버튼
        fab1 = MainView!!.findViewById<View>(R.id.fab1) as RoundImageView
        fab1!!.setBackgroundColor(Color.parseColor("#66000000"))
        fab1!!.setOnClickListener(object : OnSingleClickListener() {

            override fun onSingleClick(v: View) {

                MainActivity.mActivity?.hideKeyboard()

                searchET!!.setText("")

                if (fab2!!.visibility == View.VISIBLE) {

                    sKataIndo = ""
                    sKataIndoTambah = ""
                    sKataKor = ""

                    //미선택 전체 데이터 제거
                    items!!.clear()
                    regKataAdapter = RegKataAdapter(mContext!!, items)
                    rv1!!.adapter = regKataAdapter
                    regKataAdapter!!.notifyDataSetChanged()

                    fab2!!.visibility = View.GONE
                    fab1!!.setBackgroundColor(Color.parseColor("#66000000"))
                    fab2!!.setBackgroundColor(Color.parseColor("#EDEDED"))
                } else {

                    //선택시 전체 데이터 로드
                    items!!.clear()
                    items = kataManager!!.GetRegItems()
                    regKataAdapter = RegKataAdapter(mContext!!, items)
                    regKataAdapter!!.setOnItemClickListener(object :
                        RegKataAdapter.OnItemClickListener {

                        override fun onItemClick(v: View?, position: Int) {

                            MainActivity.mActivity?.hideKeyboard()

                            //선택된 그리드의 데이터
                            val (rIndex, kataKor, kataIndo, kataIndoTambah) = items!![position]
                            sKataIndo = kataIndo
                            sKataIndoTambah = kataIndoTambah
                            sKataKor = kataKor

                            fab2!!.setBackgroundColor(Color.parseColor("#FFC10E"))
                        }
                    })
                    rv1!!.adapter = regKataAdapter
                    regKataAdapter!!.notifyDataSetChanged()

                    fab2!!.visibility = View.VISIBLE
                    fab1!!.setBackgroundColor(
                        ContextCompat.getColor(
                            mContext!!,
                            nSkinStyleResource
                        )
                    )
                }
            }
        })

        //두번째 추가 버튼
        fab2 = MainView!!.findViewById<View>(R.id.fab2) as RoundImageView
        fab2!!.setBackgroundColor(Color.parseColor("#EDEDED"))
        fab2!!.setOnClickListener(object : OnSingleClickListener() {

            override fun onSingleClick(v: View) {

                MainActivity.mActivity?.hideKeyboard()

                val intent = Intent(mContext, EditKataActivity::class.java)
                intent.putExtra("KataIndo", sKataIndo)
                intent.putExtra("KataIndoTambah", sKataIndoTambah)
                intent.putExtra("KataKor", sKataKor)
                startActivityForResult(intent, 2)

                activity!!.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
            }
        })
    }

    private fun InitData() {

        kataManager = KataManager.getInstance(mContext!!)

        //items = kataManager!!.GetRegItems()
        rv1!!.layoutManager = LinearLayoutManager(mContext)
        regKataAdapter = RegKataAdapter(mContext!!, items)
        rv1!!.adapter = regKataAdapter
    }

    private fun InitSkinStyle() {

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle()
        when (nSkinStyle) {
            0 -> {
                nSkinStyleResource = R.color.skin1

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin1), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline1))
            }
            1 -> {
                nSkinStyleResource = R.color.skin2

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin2), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline2))
            }
            2 -> {
                nSkinStyleResource = R.color.skin3

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin3), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline3))
            }
            3 -> {
                nSkinStyleResource = R.color.skin4

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin4), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline4))
            }
            4 -> {
                nSkinStyleResource = R.color.skin5

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin5), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline5))
            }
            5 -> {
                nSkinStyleResource = R.color.skin6

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin6), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline6))
            }
            6 -> {
                nSkinStyleResource = R.color.skin7

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin7), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline7))
            }
        }
    }

    private fun ChangeSkinStyle() {

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle()
        when (nSkinStyle) {
            0 -> {

                nSkinStyleResource = R.color.skin1

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin1), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline1))
            }
            1 -> {
                nSkinStyleResource = R.color.skin2

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin2), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline2))
            }
            2 -> {
                nSkinStyleResource = R.color.skin3

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin3), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline3))
            }
            3 -> {
                nSkinStyleResource = R.color.skin4

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin4), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline4))
            }
            4 -> {
                nSkinStyleResource = R.color.skin5

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin5), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline5))
            }
            5 -> {
                nSkinStyleResource = R.color.skin6

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin6), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline6))
            }
            6 -> {
                nSkinStyleResource = R.color.skin7

                val drawable = resources.getDrawable(R.drawable.search)
                drawable.setColorFilter(ContextCompat.getColor(mContext!!, R.color.skin7), PorterDuff.Mode.SRC_ATOP)
                searchET!!.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                divider_line!!.setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.skin_divideline7))
            }
        }
    }

    fun setChangeSetting() {

        ChangeSkinStyle()

        if (fab2!!.visibility == View.VISIBLE) {
            fab1!!.setBackgroundColor(Color.parseColor("#66000000"))
        } else {
            fab1!!.setBackgroundColor(ContextCompat.getColor(mContext!!, nSkinStyleResource))
        }
    }

    internal inner class searchTextWatcher : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            try {

                if (TextUtils.isEmpty(s.toString())) {

                    items!!.clear()
                    regKataAdapter = RegKataAdapter(mContext!!, items)
                    rv1!!.adapter = regKataAdapter
                    regKataAdapter!!.notifyDataSetChanged()
                } else {

                    items!!.clear()
                    items = kataManager!!.GetRegSearchKata(s.toString())
                    regKataAdapter = RegKataAdapter(mContext!!, items)
                    regKataAdapter!!.setOnItemClickListener(object : RegKataAdapter.OnItemClickListener {

                        override fun onItemClick(v: View?, position: Int) {
                            //val (rIndex, kataKor, kataIndo, kataIndoTambah) = items!![position]
                            //Log.e(tag, items.toString())
                        }
                    })
                    rv1!!.adapter = regKataAdapter
                    regKataAdapter!!.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Log.e(tag, e.message.toString())
            }
        }
    }

}
