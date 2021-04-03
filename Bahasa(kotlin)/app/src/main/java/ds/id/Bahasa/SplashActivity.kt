package ds.id.Bahasa

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import ds.id.Bahasa.Common.KataSetting
import ds.id.Bahasa.Controls.RecycleUtil.recursiveRecycle
import ds.id.Bahasa.Database.CopyDBfile

class SplashActivity : AppCompatActivity() {

    private val tag = SplashActivity::class.java.simpleName

    private var nSkinStyle = 0

    private var handler: Handler? = null
    private var mainView: View? = null

    private val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INSTALL_SHORTCUT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        InitTitleBar()
        setContentView(R.layout.activity_splash)
        init()

        InitSkinStyle()

        InitData()
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        //백버튼 기능 막음
        return
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        try {

            if (handler != null) {
                handler!!.removeCallbacksAndMessages(null)
                handler = null
            }

            recursiveRecycle(window.decorView)
            System.gc()
        } catch (e: Exception) {
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1 -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.e(tag, "PERMISSION_GRANTED")
                } else {
                    Log.e(tag, "PERMISSION_GRANTED NOT")
                }

                callMainActivity()
            }
        }
    }

    private fun init() {

        mainView = findViewById(android.R.id.content)

        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    0 -> {

                        //권한 체크
                        if(hasPermissions(permissions)){
                            callMainActivity()
                        }else{
                            ActivityCompat.requestPermissions(this@SplashActivity, permissions, 1)
                        }
                    }
                }
            }
        }
    }

    private fun hasPermissions(permissions: Array<String>): Boolean {
        permissions.forEach { permission ->
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    private fun callMainActivity(){

        //바탕화면 아이콘 추가
        //addShortcut()

        Handler().postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 100)
    }

    private fun addShortcut(){

        if (ShortcutManagerCompat.isRequestPinShortcutSupported(this)) {
            val shortcutInfo = ShortcutInfoCompat.Builder(this, "#1")
                .setIntent(Intent(this, SplashActivity::class.java).setAction(Intent.ACTION_MAIN))
                .setShortLabel(getString(R.string.app_name))
                .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher))
                .build()
            ShortcutManagerCompat.requestPinShortcut(this, shortcutInfo, null)
        }
    }

    private fun InitData() {

        try {
            val bCopy = CopyDBTask().execute(this@SplashActivity).get()
        } catch (e: Exception) {
        }

        handler!!.postDelayed({
            try {

                //현재 미사용으로 주석 처리

                //스킨 타입
                val nSkinStyle: Int = KataSetting.getInstance().skinStyle
                BahasaApplication.getInstance().setSkinStyle(nSkinStyle)
                //Log.e(tag, "InitData nSkinStyle:$nSkinStyle")

                //문장 자동 반복 시간
                val nKataTime: Int = KataSetting.getInstance().kataTime
                BahasaApplication.getInstance().setKataTime(nKataTime)
                //Log.e(tag, "InitData nKataTime:$nKataTime")

                //단어장 자동 반복 시간
                val nWordKataTime: Int = KataSetting.getInstance().wordKataTime
                BahasaApplication.getInstance().setWordKataTime(nWordKataTime)
                //Log.e(tag, "InitData nWordKataTime:$nWordKataTime")

                //문장 음성 지원
                val bKalimatSoundToggle: Boolean? = KataSetting.getInstance().kalimatSound
                if (bKalimatSoundToggle != null) {
                    BahasaApplication.getInstance().setKalimatSound(bKalimatSoundToggle)
                }
                //Log.e(tag, "InitData bKalimatSoundToggle:$bKalimatSoundToggle")

                //단어장 녹음 지원
                val bSoundToggle: Boolean? = KataSetting.getInstance().kataSound
                if (bSoundToggle != null) {
                    BahasaApplication.getInstance().setKataSound(bSoundToggle)
                }
                //Log.e(tag, "InitData bSoundToggle:$bSoundToggle")

                //스크린 화면 항상 켜기 지원
                val bScreenLockToggle: Boolean? = KataSetting.getInstance().screenLock
                if (bScreenLockToggle != null) {
                    BahasaApplication.getInstance().setScreenLock(bScreenLockToggle)
                }
                //Log.e(tag, "InitData bScreenLockToggle:$bScreenLockToggle")

                val msg = handler!!.obtainMessage()
                msg.what = 0
                handler!!.sendMessage(msg)
            } catch (e: Exception) {
            }
        }, 1000)
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

        nSkinStyle = KataSetting.getInstance().skinStyle
        when (nSkinStyle) {
            0 -> {
                mainView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin1))
            }
            1 -> {
                mainView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin2))
            }
            2 -> {
                mainView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin3))
            }
            3 -> {
                mainView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin4))
            }
            4 -> {
                mainView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin5))
            }
            5 -> {
                mainView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin6))
            }
            6 -> {
                mainView!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin7))
            }
        }
    }

    inner class CopyDBTask : AsyncTask<Context?, Void?, Boolean>() {
        override fun doInBackground(vararg params: Context?): Boolean? {

            try {
                params[0]?.let { CopyDBfile(it) }
                return true
            } catch (e: Exception) {
            }

            return false
        }
    }
}
