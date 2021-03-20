package ds.id.Bahasa

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ds.id.Bahasa.Common.KataSetting
import ds.id.Bahasa.Controls.OnSingleClickListener
import ds.id.Bahasa.Controls.RecycleUtil.recursiveRecycle
import ds.id.Bahasa.Controls.RoundImageView
import ds.id.Bahasa.Database.KataManager

class EditKataActivity : AppCompatActivity() {

    private val tag = EditKataActivity::class.java.simpleName

    private var kataManager: KataManager? = null

    private var nSkinStyle = 0

    private var include: View? = null
    private var tvTitle: TextView? = null
    private var cLbtn_enter: View? = null

    private var cLEdit: View? = null
    private var et1: EditText? = null
    private var et2: EditText? = null
    private var et3: EditText? = null
    private var btnSet: Button? = null
    private var btnEdit: Button? = null
    private var btnDelete: Button? = null

    private var cL5: View? = null
    private var cL6: View? = null
    private var lblRecord: TextView? = null

    private var btnTextRecord: RoundImageView? = null

    private var sSavedKataIndo: String? = null

    private var currentTime: TextView? = null
    private var totalTime: TextView? = null

    private var pb1: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InitTitleBar()
        setContentView(R.layout.activity_edit_kata)

        init()
        InitSkinStyle()
        InitData()
    }

    override fun onBackPressed() {
        hideKeyboard()
        finish()
        overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            recursiveRecycle(window.decorView)
            System.gc()
        } catch (e: Exception) {
        }
    }

    private fun init() {

        include = findViewById(R.id.bahasa_toolbar)

        tvTitle = include!!.findViewById<View>(R.id.tvTitle) as TextView
        tvTitle!!.text = "단어 추가"

        cLEdit = findViewById(R.id.cLEdit)
        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        et3 = findViewById(R.id.et3)

        //저장 버튼
        btnSet = findViewById(R.id.btnSet)

        //수정 버튼
        btnEdit = findViewById(R.id.btnEdit)

        //삭제 버튼
        btnDelete = findViewById(R.id.btnDelete)

        //녹음 시작 버튼
        cL5 = findViewById<View>(R.id.cL5)

        //녹음 내용 듣기 버튼
        cL6 = findViewById<View>(R.id.cL6)

        //녹음 버튼 텍스트
        lblRecord = findViewById(R.id.lblRecord)

        //녹음 내용 시간 설정
        currentTime = findViewById<View>(R.id.currentTime) as TextView
        totalTime = findViewById<View>(R.id.totalTime) as TextView

        //프로그래스바
        pb1 = findViewById(R.id.pb1)

        //음성 설정 버튼
        btnTextRecord = findViewById(R.id.btnTextRecord)

        //키보드 숨김
        cLEdit!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                hideKeyboard()
            }
        })

        //종료
        cLbtn_enter = include!!.findViewById(R.id.cLbtn_enter) as View
        cLbtn_enter!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                hideKeyboard()

                finish()
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
            }
        })

        //저장
        btnSet!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                hideKeyboard()

                val sKataIndo = et1!!.text.toString()
                if (TextUtils.isEmpty(sKataIndo)) {
                    BahasaApplication.getInstance().Toast(this@EditKataActivity, "외국어1 정보를 입력해주세요.", false)
                    return
                }

                val sKataIndoTambah = et2!!.text.toString()
                if (TextUtils.isEmpty(sKataIndoTambah)) {
                    BahasaApplication.getInstance().Toast(this@EditKataActivity, "외국어2 정보를 입력해주세요.", false)
                    return
                }

                val sKataKor = et3!!.text.toString()
                if (TextUtils.isEmpty(sKataKor)) {
                    BahasaApplication.getInstance().Toast(this@EditKataActivity, "한국어 정보를 입력해주세요.", false)
                    return
                }

                //추가
                kataManager!!.InsertRegKata(sKataIndo, sKataIndoTambah, sKataKor)

                val intent = Intent()
                intent.putExtra("KataIndo", sKataIndo)
                intent.putExtra("KataIndoTambah", sKataIndoTambah)
                intent.putExtra("KataKor", sKataKor)
                setResult(2, intent)

                finish()
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)

                BahasaApplication.getInstance().Toast(this@EditKataActivity, "새로운 단어가 등록 되었습니다.", false)
            }
        })

        //수정
        btnEdit!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                hideKeyboard()

                val sKataIndo = et1!!.text.toString()
                if (TextUtils.isEmpty(sKataIndo)) {
                    BahasaApplication.getInstance()
                        .Toast(this@EditKataActivity, "외국어1 정보를 입력해주세요.", false)
                    return
                }

                val sKataIndoTambah = et2!!.text.toString()
                if (TextUtils.isEmpty(sKataIndoTambah)) {
                    BahasaApplication.getInstance()
                        .Toast(this@EditKataActivity, "외국어2 정보를 입력해주세요.", false)
                    return
                }

                val sKataKor = et3!!.text.toString()
                if (TextUtils.isEmpty(sKataKor)) {
                    BahasaApplication.getInstance()
                        .Toast(this@EditKataActivity, "한국어 정보를 입력해주세요.", false)
                    return
                }

                //수정
                kataManager!!.UpdateRegKata(sSavedKataIndo!!, sKataIndo, sKataIndoTambah, sKataKor)

                val intent = Intent()
                intent.putExtra("KataIndo", sKataIndo)
                intent.putExtra("KataIndoTambah", sKataIndoTambah)
                intent.putExtra("KataKor", sKataKor)
                setResult(2, intent)

                finish()
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)

                BahasaApplication.getInstance().Toast(this@EditKataActivity, "선택된 단어가 수정 되었습니다.", false)
            }
        })

        //삭제
        btnDelete!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                hideKeyboard()

                val sKataIndo = et1!!.text.toString()
                if (TextUtils.isEmpty(sKataIndo)) {
                    BahasaApplication.getInstance().Toast(this@EditKataActivity, "외국어1 정보를 입력해주세요.", false)
                    return
                }

                val sKataIndoTambah = et2!!.text.toString()
                if (TextUtils.isEmpty(sKataIndoTambah)) {
                    BahasaApplication.getInstance().Toast(this@EditKataActivity, "외국어2 정보를 입력해주세요.", false)
                    return
                }

                val sKataKor = et3!!.text.toString()
                if (TextUtils.isEmpty(sKataKor)) {
                    BahasaApplication.getInstance().Toast(this@EditKataActivity, "한국어 정보를 입력해주세요.", false)
                    return
                }

                //삭제
                kataManager!!.DeleteRegKata(sSavedKataIndo!!)

                val intent = Intent()
                intent.putExtra("KataIndo", sKataIndo)
                intent.putExtra("KataIndoTambah", sKataIndoTambah)
                intent.putExtra("KataKor", sKataKor)
                setResult(2, intent)

                finish()
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)

                BahasaApplication.getInstance().Toast(this@EditKataActivity, "선택된 단어가 삭제 되었습니다.", false)
            }
        })

        //음성 설정 버튼
        btnTextRecord!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                hideKeyboard()

                val sKataIndo = et1!!.text.toString()
                if (TextUtils.isEmpty(sKataIndo)) {
                    BahasaApplication.getInstance().Toast(this@EditKataActivity, "녹음할 단어를 입력해주세요.", false)
                    return
                }

                val sKataIndoTambah = et2!!.text.toString()
                if (TextUtils.isEmpty(sKataIndoTambah)) {
                    BahasaApplication.getInstance().Toast(this@EditKataActivity, "녹음할 단어를 입력해주세요.", false)
                    return
                }

                val sKataKor = et3!!.text.toString()
                if (TextUtils.isEmpty(sKataKor)) {
                    BahasaApplication.getInstance().Toast(this@EditKataActivity, "녹음할 단어를 입력해주세요.", false)
                    return
                }

                if (cL5!!.visibility == View.VISIBLE) {

                    cL5!!.visibility = View.GONE
                    cL6!!.visibility = View.GONE
                } else {

                    cL5!!.visibility = View.VISIBLE
                    cL6!!.visibility = View.VISIBLE
                }

            }
        })

        //녹음 시작 버튼
        cL5!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                hideKeyboard()
            }
        })

        //녹음 내용 듣기 버튼
        cL6!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                hideKeyboard()
            }
        })
    }

    private fun InitData() {

        try {

            kataManager = KataManager.getInstance(this)

            val intent = Intent(this.intent)
            val sKataIndo = intent.getStringExtra("KataIndo")
            val sKataIndoTambah = intent.getStringExtra("KataIndoTambah")
            val sKataKor = intent.getStringExtra("KataKor")
            sSavedKataIndo = sKataIndo

            if (TextUtils.isEmpty(sKataIndo)) {

                tvTitle!!.text = "단어 추가"
                btnSet!!.visibility = View.VISIBLE
                btnEdit!!.visibility = View.GONE
                btnDelete!!.visibility = View.GONE
            } else {

                tvTitle!!.text = "단어 편집"
                btnSet!!.visibility = View.GONE
                btnEdit!!.visibility = View.VISIBLE
                btnDelete!!.visibility = View.VISIBLE
            }

            et1!!.setText(sKataIndo)
            et2!!.setText(sKataIndoTambah)
            et3!!.setText(sKataKor)

        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    private fun InitTitleBar() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun InitSkinStyle() {

        nSkinStyle = KataSetting.getInstance().skinStyle
        when (nSkinStyle) {
            0 -> {
                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin1))
                btnSet!!.setBackgroundResource(R.drawable.btn_border1)

                btnEdit!!.setTextColor(ContextCompat.getColor(this, R.color.skin1))
                btnEdit!!.setBackgroundResource(R.drawable.btn_border1)

                btnDelete!!.setTextColor(ContextCompat.getColor(this, R.color.skin1))
                btnDelete!!.setBackgroundResource(R.drawable.btn_border1)

                cL5!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1))
                cL6!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1))
            }
            1 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin2))
                btnSet!!.setBackgroundResource(R.drawable.btn_border2)

                btnEdit!!.setTextColor(ContextCompat.getColor(this, R.color.skin2))
                btnEdit!!.setBackgroundResource(R.drawable.btn_border2)

                btnDelete!!.setTextColor(ContextCompat.getColor(this, R.color.skin2))
                btnDelete!!.setBackgroundResource(R.drawable.btn_border2)

                cL5!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2))
                cL6!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2))
            }
            2 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin3))
                btnSet!!.setBackgroundResource(R.drawable.btn_border3)

                btnEdit!!.setTextColor(ContextCompat.getColor(this, R.color.skin3))
                btnEdit!!.setBackgroundResource(R.drawable.btn_border3)

                btnDelete!!.setTextColor(ContextCompat.getColor(this, R.color.skin3))
                btnDelete!!.setBackgroundResource(R.drawable.btn_border3)

                cL5!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3))
                cL6!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3))
            }
            3 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin4))
                btnSet!!.setBackgroundResource(R.drawable.btn_border4)

                btnEdit!!.setTextColor(ContextCompat.getColor(this, R.color.skin4))
                btnEdit!!.setBackgroundResource(R.drawable.btn_border4)

                btnDelete!!.setTextColor(ContextCompat.getColor(this, R.color.skin4))
                btnDelete!!.setBackgroundResource(R.drawable.btn_border4)

                cL5!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4))
                cL6!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4))
            }
            4 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin5))
                btnSet!!.setBackgroundResource(R.drawable.btn_border5)

                btnEdit!!.setTextColor(ContextCompat.getColor(this, R.color.skin5))
                btnEdit!!.setBackgroundResource(R.drawable.btn_border5)

                btnDelete!!.setTextColor(ContextCompat.getColor(this, R.color.skin5))
                btnDelete!!.setBackgroundResource(R.drawable.btn_border5)

                cL5!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5))
                cL6!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5))
            }
            5 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin6))
                btnSet!!.setBackgroundResource(R.drawable.btn_border6)

                btnEdit!!.setTextColor(ContextCompat.getColor(this, R.color.skin6))
                btnEdit!!.setBackgroundResource(R.drawable.btn_border6)

                btnDelete!!.setTextColor(ContextCompat.getColor(this, R.color.skin6))
                btnDelete!!.setBackgroundResource(R.drawable.btn_border6)

                cL5!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6))
                cL6!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6))
            }
            6 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin7))
                btnSet!!.setBackgroundResource(R.drawable.btn_border7)

                btnEdit!!.setTextColor(ContextCompat.getColor(this, R.color.skin7))
                btnEdit!!.setBackgroundResource(R.drawable.btn_border7)

                btnDelete!!.setTextColor(ContextCompat.getColor(this, R.color.skin7))
                btnDelete!!.setBackgroundResource(R.drawable.btn_border7)

                cL5!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7))
                cL6!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7))
            }
        }
    }

    private fun hideKeyboard() {

        try {
            if (currentFocus != null) {
                val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            }
        } catch (e: java.lang.Exception) {
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        //Log.e(tag, "onConfigurationChanged")
    }
}