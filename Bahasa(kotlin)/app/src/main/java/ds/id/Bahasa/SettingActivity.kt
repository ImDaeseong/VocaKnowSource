package ds.id.Bahasa

import android.content.pm.ActivityInfo
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ds.id.Bahasa.Common.KataSetting
import ds.id.Bahasa.Controls.OnSingleClickListener
import ds.id.Bahasa.Controls.RecycleUtil.recursiveRecycle
import ds.id.Bahasa.Controls.RoundImageView

class SettingActivity : AppCompatActivity() {

    private val tag = SettingActivity::class.java.simpleName

    private var nSkinStyle = 0

    private var main: View? = null

    private var include: View? = null
    private var tvTitle: TextView? = null
    private var cLbtn_enter: View? = null

    private var cLInfo: View? = null
    private var ivInfo: RoundImageView? = null

    private var et1: EditText? = null
    private var et2: EditText? = null

    private var sw1: Switch? = null
    private var sw2: Switch? = null
    private var sw3: Switch? = null

    private var tvskin: TextView? = null

    private var color_item1: View? = null
    private var color_item2: View? = null
    private var color_item3: View? = null
    private var color_item4: View? = null
    private var color_item5: View? = null
    private var color_item6: View? = null
    private var color_item7: View? = null
    
    private var vColor1: View? = null
    private var vColor2: View? = null
    private var vColor3: View? = null
    private var vColor4: View? = null
    private var vColor5: View? = null
    private var vColor6: View? = null
    private var vColor7: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InitTitleBar()
        setContentView(R.layout.activity_setting)
        init()

        InitSkinStyle()

        InitData()
    }

    override fun onBackPressed() {
        super.onBackPressed()

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

        main = findViewById(R.id.main)

        include = findViewById(R.id.bahasa_toolbar)

        tvTitle = include!!.findViewById(R.id.tvTitle)
        tvTitle!!.text = "문장편집"

        cLInfo = findViewById(R.id.cLInfo)
        ivInfo = findViewById(R.id.ivInfo)

        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)

        tvskin = findViewById(R.id.tvskin)

        sw1 = findViewById<View>(R.id.sw1) as Switch
        sw2 = findViewById<View>(R.id.sw2) as Switch
        sw3 = findViewById<View>(R.id.sw3) as Switch

        //화면 클릭시
        main!!.setOnClickListener(object : OnSingleClickListener() {
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

        //사용법 호출
        cLInfo!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                hideKeyboard()
            }
        })

        //문장 자동 반복
        et1!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                try {

                    if (!TextUtils.isEmpty(s.toString())) {
                        val nKataTime = s.toString().toInt()
                        KataSetting.getInstance().kataTime = nKataTime
                        BahasaApplication.getInstance().setKataTime(nKataTime)

                        val mainActivity = MainActivity()
                        mainActivity.setChangeSetting()
                    }
                } catch (e: Exception) {
                }
            }
        })

        //단어장 자동 반복
        et2!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                try {

                    if (!TextUtils.isEmpty(s.toString())) {
                        val nWordKataTime = s.toString().toInt()
                        KataSetting.getInstance().wordKataTime = nWordKataTime
                        BahasaApplication.getInstance().setWordKataTime(nWordKataTime)

                        val mainActivity = MainActivity()
                        mainActivity.setChangeSetting()
                    }
                } catch (e: Exception) {
                }
            }
        })

        //문장 음성 켜기
        sw1!!.setOnCheckedChangeListener { _, isChecked ->
            try {

                KataSetting.getInstance().kalimatSound = isChecked
                BahasaApplication.getInstance().setKalimatSound(isChecked)

                val mainActivity = MainActivity()
                mainActivity.setChangeSetting()
            } catch (e: Exception) {
            }
        }

        //단어장 녹음 음성 켜기
        sw2!!.setOnCheckedChangeListener { _, isChecked ->
            try {

                KataSetting.getInstance().kataSound = isChecked
                BahasaApplication.getInstance().setKataSound(isChecked)

                val mainActivity = MainActivity()
                mainActivity.setChangeSetting()
            } catch (e: Exception) {
            }
        }

        //스크린 화면 항상 켜기
        sw3!!.setOnCheckedChangeListener { _, isChecked ->
            try {

                KataSetting.getInstance().screenLock = isChecked
                BahasaApplication.getInstance().setScreenLock(isChecked)

                val mainActivity = MainActivity()
                mainActivity.getScreenLock()
                mainActivity.setChangeSetting()
            } catch (e: Exception) {
            }
        }

        //스킨 선택
        color_item1 = findViewById(R.id.color_skin_item1)
        vColor1 = color_item1!!.findViewById(R.id.vColor) as View
        vColor1!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {

                    KataSetting.getInstance().skinStyle = 0
                    BahasaApplication.getInstance().setSkinStyle(0)
                    InitSkinStyle()

                    val mainActivity = MainActivity()
                    mainActivity.setChangeSetting()
                } catch (e: Exception) {
                }
            }
        })

        color_item2 = findViewById(R.id.color_skin_item2)
        vColor2 = color_item2!!.findViewById(R.id.vColor) as View
        vColor2!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {

                    KataSetting.getInstance().skinStyle = 1
                    BahasaApplication.getInstance().setSkinStyle(1)
                    InitSkinStyle()

                    val mainActivity = MainActivity()
                    mainActivity.setChangeSetting()
                } catch (e: Exception) {
                }
            }
        })

        color_item3 = findViewById(R.id.color_skin_item3)
        vColor3 = color_item3!!.findViewById(R.id.vColor) as View
        vColor3!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {

                    KataSetting.getInstance().skinStyle = 2
                    BahasaApplication.getInstance().setSkinStyle(2)
                    InitSkinStyle()

                    val mainActivity = MainActivity()
                    mainActivity.setChangeSetting()
                } catch (e: Exception) {
                }
            }
        })

        color_item4 = findViewById(R.id.color_skin_item4)
        vColor4 = color_item4!!.findViewById(R.id.vColor) as View
        vColor4!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {

                    KataSetting.getInstance().skinStyle = 3
                    BahasaApplication.getInstance().setSkinStyle(3)
                    InitSkinStyle()

                    val mainActivity = MainActivity()
                    mainActivity.setChangeSetting()
                } catch (e: Exception) {
                }
            }
        })

        color_item5 = findViewById(R.id.color_skin_item5)
        vColor5 = color_item5!!.findViewById(R.id.vColor) as View
        vColor5!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {

                    KataSetting.getInstance().skinStyle = 4
                    BahasaApplication.getInstance().setSkinStyle(4)
                    InitSkinStyle()

                    val mainActivity = MainActivity()
                    mainActivity.setChangeSetting()
                } catch (e: Exception) {
                }
            }
        })

        color_item6 = findViewById(R.id.color_skin_item6)
        vColor6 = color_item6!!.findViewById(R.id.vColor) as View
        vColor6!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {

                    KataSetting.getInstance().skinStyle = 5
                    BahasaApplication.getInstance().setSkinStyle(5)
                    InitSkinStyle()

                    val mainActivity = MainActivity()
                    mainActivity.setChangeSetting()
                } catch (e: Exception) {
                }
            }
        })

        color_item7 = findViewById(R.id.color_skin_item7)
        vColor7 = color_item7!!.findViewById(R.id.vColor) as View
        vColor7!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {

                    KataSetting.getInstance().skinStyle = 6
                    BahasaApplication.getInstance().setSkinStyle(6)
                    InitSkinStyle()

                    val mainActivity = MainActivity()
                    mainActivity.setChangeSetting()
                } catch (e: Exception) {
                }
            }
        })
    }

    private fun InitData() {

        try {

            val sVersion: String? = KataSetting.getInstance().kataVersion
            val sPlayNumber: String? = KataSetting.getInstance().kataPlayNumber
            val nKataTime: Int = KataSetting.getInstance().kataTime
            val nWordKataTime: Int = KataSetting.getInstance().wordKataTime
            //val nKataTimeRepeat : Int = KataSetting.getInstance().kataTimeRepeat
            //val nWordKataTimeRepeat: Int = KataSetting.getInstance().wordKataTimeRepeat
            val bKalimatSoundToggle: Boolean? = KataSetting.getInstance().kalimatSound
            val bSoundToggle: Boolean? = KataSetting.getInstance().kataSound
            val bScreenLockToggle: Boolean? = KataSetting.getInstance().screenLock
            val sKataTime = String.format("%d", nKataTime)
            val sWordKataTime = String.format("%d", nWordKataTime)

            //문장 자동 반복 시간
            et1!!.setText(sKataTime)

            //단어장 자동 반복 시간
            et2!!.setText(sWordKataTime)

            //문장 음성 지원
            if (bKalimatSoundToggle != null) {
                sw1!!.isChecked = bKalimatSoundToggle
            }

            //단어장 녹음 지원
            if (bSoundToggle != null) {
                sw2!!.isChecked = bSoundToggle
            }

            //스크린 화면 항상 켜기 지원
            if (bScreenLockToggle != null) {
                sw3!!.isChecked = bScreenLockToggle
            }

        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    private fun InitTitleBar() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun InitSkinStyle() {

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle()
        when (nSkinStyle) {
            0 -> {
                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1))
                ivInfo!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin1),
                    PorterDuff.Mode.SRC_ATOP
                )
                val drawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_selector)
                drawable!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin1),
                    PorterDuff.Mode.SRC_ATOP
                )
                sw1!!.thumbDrawable = drawable
                sw2!!.thumbDrawable = drawable
                sw3!!.thumbDrawable = drawable
                tvskin!!.setTextColor(ContextCompat.getColor(this, R.color.skin1))
            }
            1 -> {
                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2))
                ivInfo!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin2),
                    PorterDuff.Mode.SRC_ATOP
                )
                val drawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_selector)
                drawable!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin2),
                    PorterDuff.Mode.SRC_ATOP
                )
                sw1!!.thumbDrawable = drawable
                sw2!!.thumbDrawable = drawable
                sw3!!.thumbDrawable = drawable
                tvskin!!.setTextColor(ContextCompat.getColor(this, R.color.skin2))
            }
            2 -> {
                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3))
                ivInfo!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin3),
                    PorterDuff.Mode.SRC_ATOP
                )
                val drawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_selector)
                drawable!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin3),
                    PorterDuff.Mode.SRC_ATOP
                )
                sw1!!.thumbDrawable = drawable
                sw2!!.thumbDrawable = drawable
                sw3!!.thumbDrawable = drawable
                tvskin!!.setTextColor(ContextCompat.getColor(this, R.color.skin3))
            }
            3 -> {
                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4))
                ivInfo!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin4),
                    PorterDuff.Mode.SRC_ATOP
                )
                val drawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_selector)
                drawable!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin4),
                    PorterDuff.Mode.SRC_ATOP
                )
                sw1!!.thumbDrawable = drawable
                sw2!!.thumbDrawable = drawable
                sw3!!.thumbDrawable = drawable
                tvskin!!.setTextColor(ContextCompat.getColor(this, R.color.skin4))
            }
            4 -> {
                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5))
                ivInfo!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin5),
                    PorterDuff.Mode.SRC_ATOP
                )
                val drawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_selector)
                drawable!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin5),
                    PorterDuff.Mode.SRC_ATOP
                )
                sw1!!.thumbDrawable = drawable
                sw2!!.thumbDrawable = drawable
                sw3!!.thumbDrawable = drawable
                tvskin!!.setTextColor(ContextCompat.getColor(this, R.color.skin5))
            }
            5 -> {
                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6))
                ivInfo!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin6),
                    PorterDuff.Mode.SRC_ATOP
                )
                val drawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_selector)
                drawable!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin6),
                    PorterDuff.Mode.SRC_ATOP
                )
                sw1!!.thumbDrawable = drawable
                sw2!!.thumbDrawable = drawable
                sw3!!.thumbDrawable = drawable
                tvskin!!.setTextColor(ContextCompat.getColor(this, R.color.skin6))
            }
            6 -> {
                include!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7))
                ivInfo!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin7),
                    PorterDuff.Mode.SRC_ATOP
                )
                val drawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_selector)
                drawable!!.setColorFilter(
                    ContextCompat.getColor(this, R.color.skin7),
                    PorterDuff.Mode.SRC_ATOP
                )
                sw1!!.thumbDrawable = drawable
                sw2!!.thumbDrawable = drawable
                sw3!!.thumbDrawable = drawable
                tvskin!!.setTextColor(ContextCompat.getColor(this, R.color.skin7))
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
