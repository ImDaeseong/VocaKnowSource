package ds.id.Bahasa

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
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
import ds.id.Bahasa.Controls.*
import ds.id.Bahasa.Controls.AnimatorUtil.AnimatoBottomToTop
import ds.id.Bahasa.Controls.AnimatorUtil.AnimatoTopToBottom
import ds.id.Bahasa.Controls.RecycleUtil.recursiveRecycle
import ds.id.Bahasa.Database.KataManager
import java.io.File

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

    private var audioRecorder: AudioRecorder? = null
    private var audioPlayer: AudioPlayer? = null
    private var length = 0

    private var playerTimer: PlayerTimer? = null

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

            if (audioPlayer != null) {
                audioPlayer!!.release()
            }

            stopplayerTimer()

            recursiveRecycle(window.decorView)
            System.gc()
        } catch (e: Exception) {
        }
    }

    private fun init() {

        audioRecorder = AudioRecorder.getInstance()
        audioRecorder!!.AudioRecorder(this)

        audioPlayer = AudioPlayer.getInstance(this)

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
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "외국어1 정보를 입력해주세요.",
                        false
                    )
                    return
                }

                val sKataIndoTambah = et2!!.text.toString()
                if (TextUtils.isEmpty(sKataIndoTambah)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "외국어2 정보를 입력해주세요.",
                        false
                    )
                    return
                }

                val sKataKor = et3!!.text.toString()
                if (TextUtils.isEmpty(sKataKor)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "한국어 정보를 입력해주세요.",
                        false
                    )
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

                BahasaApplication.getInstance().Toast(
                    this@EditKataActivity,
                    "새로운 단어가 등록 되었습니다.",
                    false
                )
            }
        })

        //수정
        btnEdit!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                hideKeyboard()

                val sKataIndo = et1!!.text.toString()
                if (TextUtils.isEmpty(sKataIndo)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "외국어1 정보를 입력해주세요.",
                        false
                    )
                    return
                }

                val sKataIndoTambah = et2!!.text.toString()
                if (TextUtils.isEmpty(sKataIndoTambah)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "외국어2 정보를 입력해주세요.",
                        false
                    )
                    return
                }

                val sKataKor = et3!!.text.toString()
                if (TextUtils.isEmpty(sKataKor)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "한국어 정보를 입력해주세요.",
                        false
                    )
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

                BahasaApplication.getInstance().Toast(
                    this@EditKataActivity,
                    "선택된 단어가 수정 되었습니다.",
                    false
                )
            }
        })

        //삭제
        btnDelete!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                hideKeyboard()

                val sKataIndo = et1!!.text.toString()
                if (TextUtils.isEmpty(sKataIndo)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "외국어1 정보를 입력해주세요.",
                        false
                    )
                    return
                }

                val sKataIndoTambah = et2!!.text.toString()
                if (TextUtils.isEmpty(sKataIndoTambah)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "외국어2 정보를 입력해주세요.",
                        false
                    )
                    return
                }

                val sKataKor = et3!!.text.toString()
                if (TextUtils.isEmpty(sKataKor)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "한국어 정보를 입력해주세요.",
                        false
                    )
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

                BahasaApplication.getInstance().Toast(
                    this@EditKataActivity,
                    "선택된 단어가 삭제 되었습니다.",
                    false
                )
            }
        })

        //음성 설정 버튼
        btnTextRecord!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                hideKeyboard()

                val sKataIndo = et1!!.text.toString()
                if (TextUtils.isEmpty(sKataIndo)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "녹음할 단어를 입력해주세요.",
                        false
                    )
                    return
                }

                val sKataIndoTambah = et2!!.text.toString()
                if (TextUtils.isEmpty(sKataIndoTambah)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "녹음할 단어를 입력해주세요.",
                        false
                    )
                    return
                }

                val sKataKor = et3!!.text.toString()
                if (TextUtils.isEmpty(sKataKor)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "녹음할 단어를 입력해주세요.",
                        false
                    )
                    return
                }

                if (cL5!!.visibility == View.VISIBLE) {

                    AnimatoTopToBottom(cL5)
                    AnimatoTopToBottom(cL6)
                    //cL5!!.visibility = View.GONE
                    //cL6!!.visibility = View.GONE
                } else {

                    cL5!!.visibility = View.VISIBLE
                    cL6!!.visibility = View.VISIBLE
                    AnimatoBottomToTop(cL5)
                    AnimatoBottomToTop(cL6)
                }

            }
        })

        //녹음 시작 버튼
        cL5!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                hideKeyboard()

                val sKataIndo = et1!!.text.toString()
                if (TextUtils.isEmpty(sKataIndo)) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "녹음할 단어를 입력해주세요.",
                        false
                    )
                    return
                }

                if (audioRecorder!!.isRecording()) {

                    lblRecord!!.text = "버튼을 클릭하면 녹음이 시작됩니다."
                    length = audioRecorder!!.stopRecord()
                    audioRecorder!!.release()
                } else {

                    lblRecord!!.text = "현재 녹음이 시작되었습니다."
                    audioRecorder!!.startRecord(sKataIndo)
                }
            }
        })

        //녹음 내용 듣기 버튼
        cL6!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                hideKeyboard()

                val sKataIndo = et1!!.text.toString()
                val sFile = "$sKataIndo.aac"
                val saveFolder = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
                    sFile
                )

                if (saveFolder == null) {
                    BahasaApplication.getInstance().Toast(
                        this@EditKataActivity,
                        "녹음된 파일이 존재하지 않습니다.",
                        false
                    )
                    return
                }

                if (audioRecorder != null) {
                    if (audioRecorder!!.isRecording()) {
                        return
                    }
                }

                if (audioPlayer != null) {
                    if (audioPlayer!!.isPlaying()) {
                        return
                    }
                }

                audioPlayer!!.init()
                audioPlayer!!.play(saveFolder.path, object : AudioPlayer.OnMediaPlayerListener {
                    override fun onCompletion(bComplete: Boolean) {

                        //Log.e(tag, "녹음된 내용을 전부 들었습니다.")

                        lblRecord!!.text = "버튼을 클릭하면 녹음이 시작됩니다."
                        stopplayerTimer()
                        initDisplaytime()

                        val duration = audioPlayer!!.getDuration()
                        pb1!!.progress = 0
                        pb1!!.max = duration
                    }

                    override fun onPrepared(mDuration: Int) {

                        //Log.e(tag, "녹음된 파일이 준비가 되었습니다.")

                        initDisplaytime()
                    }
                })

                lblRecord!!.text = "녹음내용 미리 듣기 중입니다."
                audioPlayer!!.start()
                setSeekBarProgress()
            }
        })
    }

    private fun setSeekBarProgress() {

        stopplayerTimer()

        playerTimer = PlayerTimer()
        playerTimer!!.setCallback(object : PlayerTimer.Callback {
            override fun onTick(timeMillis: Long) {

                if (audioPlayer == null) return

                val position = audioPlayer!!.getCurrentPosition()
                val duration = audioPlayer!!.getDuration()

                if (duration <= 0) return

                pb1!!.max = duration
                pb1!!.progress = position

                val nEndTime = duration / 1000
                val nEndMinutes = nEndTime / 60 % 60
                val nEndSeconds = nEndTime % 60

                val nCurrentTime = position / 1000
                val nCurrentMinutes = nCurrentTime / 60 % 60
                val nCurrentSeconds = nCurrentTime % 60

                currentTime!!.text = String.format("%02d:%02d", nCurrentMinutes, nCurrentSeconds)
                totalTime!!.text = String.format("%02d:%02d", nEndMinutes, nEndSeconds)
            }
        })
        playerTimer!!.start()
    }

    private fun stopplayerTimer() {

        if (playerTimer != null) {
            playerTimer!!.stop()
            playerTimer!!.removeMessages(0)
        }
    }

    private fun initDisplaytime() {

        if (audioPlayer == null)
            return

        val nEndTime = audioPlayer!!.getDuration() / 1000
        val nEndMinutes = nEndTime / 60 % 60
        val nEndSeconds = nEndTime % 60

        val nCurrentTime = audioPlayer!!.getCurrentPosition() / 1000
        val nCurrentMinutes = nCurrentTime / 60 % 60
        val nCurrentSeconds = nCurrentTime % 60

        //currentTime!!.text = String.format("%02d:%02d", nCurrentMinutes, nCurrentSeconds)
        currentTime!!.text = String.format("00:00", nCurrentMinutes, nCurrentSeconds)
        totalTime!!.text = String.format("%02d:%02d", nEndMinutes, nEndSeconds)
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
