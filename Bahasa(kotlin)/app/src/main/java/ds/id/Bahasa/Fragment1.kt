package ds.id.Bahasa

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ScrollView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import ds.id.Bahasa.Common.KataSetting
import ds.id.Bahasa.Common.gMenuItem
import ds.id.Bahasa.Controls.AnimatorUtil.AnimatoRotate
import ds.id.Bahasa.Controls.OnSingleClickListener

class Fragment1 : Fragment() {

    companion object {
        private val tag = Fragment1::class.java.simpleName
    }

    private var mContext: Context? = null
    private var MainView: View? = null
    private var scrollview: ScrollView? = null
    private var gridlayout: GridLayout? = null

    private var nSkinStyle = 0
    private var nSkinStyleResource = 0

    private val numRows = 15
    private val numCols = 2

    private val titlelist = arrayOf(
        arrayOf("일상생활/인사,소개", "일상생활/날씨,여가"),
        arrayOf("일상생활/시장,교통", "일상생활/문화,관습"),
        arrayOf("일상생활/건강,병원", "일상생활/음식,식생활"),
        arrayOf("일상생활/은행,송금", "일상생활/기타"),
        arrayOf("작업지시/근무시작,끝", "작업지시/근무태도"),
        arrayOf("작업지시/작업공구", "작업지시/안전규칙"),
        arrayOf("작업지시/작업규칙등기타", "기숙사및식당/기숙사안내"),
        arrayOf("기숙사및식당/기숙사규칙", "기숙사및식당/기숙사환경"),
        arrayOf("기숙사및식당/식당안내", "기숙사및식당/식당규칙"),
        arrayOf("근로관련/급여,수당", "근로관련/회사,근무규칙"),
        arrayOf("근로관련/채권채무", "근로관련/고용허가서등"),
        arrayOf("근로관련/각종보험", "근로관련/기타"),
        arrayOf("고용관련신고/입사", "고용관련신고/퇴사"),
        arrayOf("고용관련신고/사업장변경", "고용관련신고/출입국"),
        arrayOf("고용관련신고/고용지원센터", "고용관련신고/기타사항")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mContext = container!!.context
        MainView = inflater.inflate(R.layout.fragment_1, container, false)

        return MainView
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        changeDisplay()
        InitData()
    }

    private fun init() {

        scrollview = MainView!!.findViewById<View>(R.id.scrollview) as ScrollView
        gridlayout = MainView!!.findViewById<View>(R.id.gridlayout) as GridLayout

        scrollview!!.viewTreeObserver.addOnScrollChangedListener {
            try {

                if (scrollview != null) {
                    if (scrollview!!.getChildAt(0).bottom <= scrollview!!.height + scrollview!!.scrollY) {
                    } else {
                    }
                }
            } catch (e: Exception) {
            }
        }

        InitSkinStyle()
    }

    private fun InitSkinStyle() {

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle()
        when (nSkinStyle) {
            0 -> {
                nSkinStyleResource = R.drawable.menu1_style
            }
            1 -> {
                nSkinStyleResource = R.drawable.menu2_style
            }
            2 -> {
                nSkinStyleResource = R.drawable.menu3_style
            }
            3 -> {
                nSkinStyleResource = R.drawable.menu4_style
            }
            4 -> {
                nSkinStyleResource = R.drawable.menu5_style
            }
            5 -> {
                nSkinStyleResource = R.drawable.menu6_style
            }
            6 -> {
                nSkinStyleResource = R.drawable.menu7_style
            }
        }
    }

    private fun InitData() {

        val display = activity!!.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val screenWidth = size.x
        val screenHeight = size.y

        gridlayout!!.columnCount = numCols
        gridlayout!!.rowCount = numRows

        //val nMinheight = screenHeight / numRows
        //Log.e(tag, "nMinheight:$nMinheight")

        for (i in 0 until numRows) {
            for (j in 0 until numCols) {

                val gMenuItem = gMenuItem(mContext!!)
                gMenuItem.setText(titlelist[i][j])
                gMenuItem.setSkinResource(nSkinStyleResource)
                gMenuItem.setOnClickListener(object : OnSingleClickListener() {

                    override fun onSingleClick(v: View) {

                        val sText = (v as gMenuItem).getText()
                        if (!TextUtils.isEmpty(sText)) {
                            AnimatoRotate(v, sText)
                        }
                    }
                })

                gridlayout!!.addView(gMenuItem, (screenWidth /numCols), 135)//(screenHeight / numRows))
            }
        }
    }

    private fun ChangeSkinStyle() {

        InitSkinStyle()

        val count = gridlayout!!.childCount
        for (i in 0 until count) {
            val child = gridlayout!!.getChildAt(i) as gMenuItem
            child.setSkinResource(nSkinStyleResource)
        }
    }

    fun changeDisplay() {

        try {

            val display = activity!!.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val screenWidth = size.x
            val screenHeight = size.y
            val count = gridlayout!!.childCount

            for (i in 0 until count) {
                val child = gridlayout!!.getChildAt(i) as gMenuItem
                val lp = child.layoutParams as ViewGroup.LayoutParams
                lp.width = screenWidth / numCols
                lp.height = 135
                child.layoutParams = lp
            }
        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    fun setChangeSetting() {

        //Log.e(tag, "setChangeSetting()");

        ChangeSkinStyle()
    }
}