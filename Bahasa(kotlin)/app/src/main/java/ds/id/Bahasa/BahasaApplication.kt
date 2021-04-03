package ds.id.Bahasa

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import ds.id.Bahasa.Controls.SharedPreferencesUtil

class BahasaApplication : Application() {

    private val tag: String = BahasaApplication::class.java.simpleName

    companion object {
        private lateinit var mContext: Context
        private lateinit var mInstance: BahasaApplication

        fun getContext(): Context {
            return mContext.applicationContext
        }

        fun getInstance(): BahasaApplication {
            return mInstance
        }

        private var toast: Toast? = null
    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this
        mContext = applicationContext

        try {
            SharedPreferencesUtil.getInstance().init(this)
        } catch (e: Exception) {
        }
    }

    fun Toast(context: Context, sMsg: String?, bLengthLong: Boolean) {

        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.toast_layout, null)
        when (BahasaApplication.getInstance().getSkinStyle()) {
            0 -> {
                view.setBackgroundResource(R.drawable.menu1_style)
            }
            1 -> {
                view.setBackgroundResource(R.drawable.menu2_style)
            }
            2 -> {
                view.setBackgroundResource(R.drawable.menu3_style)
            }
            3 -> {
                view.setBackgroundResource(R.drawable.menu4_style)
            }
            4 -> {
                view.setBackgroundResource(R.drawable.menu5_style)
            }
            5 -> {
                view.setBackgroundResource(R.drawable.menu6_style)
            }
            6 -> {
                view.setBackgroundResource(R.drawable.menu7_style)
            }
        }

        val tvtoast: TextView = view.findViewById(R.id.tvtoast)
        tvtoast.maxLines = 1 //최대 1줄까지
        tvtoast.setTextColor(Color.parseColor("#ffffff"))
        tvtoast.text = sMsg
        toast = Toast(context)
        toast!!.setGravity(Gravity.BOTTOM, 0, 0)

        if (bLengthLong) {
            toast!!.duration = Toast.LENGTH_LONG
        } else {
            toast!!.duration = Toast.LENGTH_SHORT
        }

        toast!!.view = view
        toast!!.show()
    }

    fun Toastcancel() {
        try {
            toast!!.cancel()
        } catch (e: java.lang.Exception) {
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    //현재 ORIENTATION_PORTRAIT 여부
    private var mbOrientation = false
    fun getOrientation(): Boolean {
        return mbOrientation
    }

    fun setOrientation(bOrientation: Boolean) {
        mbOrientation = bOrientation
    }

    //스킨 타입
    private var mSkinStyle = 0
    fun getSkinStyle(): Int {
        return mSkinStyle
    }

    fun setSkinStyle(nSkinStyle: Int) {
        mSkinStyle = nSkinStyle
    }

    //문장 자동 반복 시간
    private var mKataTime = 0
    fun getKataTime(): Int {
        return mKataTime
    }

    fun setKataTime(nKataTime: Int) {
        mKataTime = nKataTime
    }

    //단어장 자동 반복 시간
    private var mWordKataTime = 0
    fun getWordKataTime(): Int {
        return mWordKataTime
    }

    fun setWordKataTime(nWordKataTime: Int) {
        mWordKataTime = nWordKataTime
    }

    //문장 음성 지원
    private var mbKalimatSoundToggle = false
    fun getKalimatSound(): Boolean {
        return mbKalimatSoundToggle
    }

    fun setKalimatSound(bKalimatSoundToggle: Boolean) {
        mbKalimatSoundToggle = bKalimatSoundToggle
    }

    //단어장 녹음 지원
    private var mbSoundToggle = false
    fun getKataSound(): Boolean {
        return mbSoundToggle
    }

    fun setKataSound(bSoundToggle: Boolean) {
        mbSoundToggle = bSoundToggle
    }

    //스크린 화면 항상 켜기 지원
    private var mbScreenLockToggle = false
    fun getScreenLock(): Boolean {
        return mbScreenLockToggle
    }

    fun setScreenLock(bScreenLockToggle: Boolean) {
        mbScreenLockToggle = bScreenLockToggle
    }
}
