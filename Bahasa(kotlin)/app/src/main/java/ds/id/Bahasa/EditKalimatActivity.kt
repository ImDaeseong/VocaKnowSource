package ds.id.Bahasa

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ds.id.Bahasa.Controls.OnSingleClickListener
import ds.id.Bahasa.Controls.RecycleUtil.recursiveRecycle
import ds.id.Bahasa.Database.KataManager

class EditKalimatActivity : AppCompatActivity() {

    private val tag = EditKalimatActivity::class.java.simpleName

    private var kataManager: KataManager? = null

    private var nSkinStyle = 0

    private var include: View? = null
    private var tvTitle: TextView? = null
    private var cLbtn_enter: View? = null

    private var cLEdit: View? = null
    private var et1: EditText? = null
    private var et2: EditText? = null
    private var btnSet: Button? = null

    private var sSaveKataIndo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InitTitleBar()
        setContentView(R.layout.activity_edit_kalimat)

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
        tvTitle!!.text = "문장편집"

        cLEdit = findViewById(R.id.cLEdit)
        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        btnSet = findViewById(R.id.btnSet)

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

                val KataIndo = et1!!.text.toString()
                if (TextUtils.isEmpty(KataIndo)) {
                    return
                }

                val lembutlidah = et2!!.text.toString()
                if (TextUtils.isEmpty(lembutlidah)) {
                    return
                }

                //업데이트
                kataManager!!.UpdateKalimat(KataIndo, lembutlidah, sSaveKataIndo!!)

                val intent = Intent()
                intent.putExtra("KataIndo", KataIndo)
                intent.putExtra("lembutlidah", lembutlidah)
                setResult(1, intent)

                finish()
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)

                BahasaApplication.getInstance().Toast(this@EditKalimatActivity, "선택된 문장이 수정 되었습니다.", false)
            }
        })
    }

    private fun InitData() {

        try {

            kataManager = KataManager.getInstance(this)

            val intent = Intent(this.intent)
            val KataIndo = intent.getStringExtra("KataIndo")
            val lembutlidah = intent.getStringExtra("lembutlidah")
            sSaveKataIndo = KataIndo

            et1!!.setText(KataIndo)
            et2!!.setText(lembutlidah)

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

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle()
        when (nSkinStyle) {
            0 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin1))
                btnSet!!.setBackgroundResource(R.drawable.btn_border1)
            }
            1 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin2))
                btnSet!!.setBackgroundResource(R.drawable.btn_border2)
            }
            2 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin3))
                btnSet!!.setBackgroundResource(R.drawable.btn_border3)
            }
            3 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin4))
                btnSet!!.setBackgroundResource(R.drawable.btn_border4)
            }
            4 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin5))
                btnSet!!.setBackgroundResource(R.drawable.btn_border5)
            }
            5 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin6))
                btnSet!!.setBackgroundResource(R.drawable.btn_border6)
            }
            6 -> {

                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7))

                btnSet!!.setTextColor(ContextCompat.getColor(this, R.color.skin7))
                btnSet!!.setBackgroundResource(R.drawable.btn_border7)
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
}

