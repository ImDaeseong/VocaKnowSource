package ds.id.Bahasa

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import ds.id.Bahasa.Controls.*
import ds.id.Bahasa.Controls.AnimatorUtil.AnimatoSwipeLeft
import ds.id.Bahasa.Controls.AnimatorUtil.AnimatoSwipeRight
import ds.id.Bahasa.Controls.SwipeConstraintLayout.OnSwipeConstraintListener
import ds.id.Bahasa.Controls.UnitUtils.dip2px
import ds.id.Bahasa.Database.KataItems
import ds.id.Bahasa.Database.KataManager
import java.util.*

class Fragment2 : Fragment() {

    companion object {
        private val tag = Fragment2::class.java.simpleName
    }

    private var mContext: Context? = null
    private var MainView: View? = null

    private var kataManager: KataManager? = null

    private var sMenu: String? = null

    private var cLChange: View? = null
    private var ivChange: RoundImageView? = null
    private var Editsentence: RoundImageView? = null

    private var cL1: View? = null
    private var CounterTimer: TextView? = null

    private var Kataview: SwipeConstraintLayout? = null
    private var KataIndo: TextView? = null
    private var KataKor: TextView? = null
    private var lembutlidah: TextView? = null

    private var cL2: View? = null
    private var mView: TextView? = null

    private var upPrePayImage: RoundImageView? = null
    private var upNextplayImage: RoundImageView? = null

    private var CurrentPlayIndex = 0
    private var nTotalPageCount = 0
    private var item: KataItems? = null
    var mCollection: List<KataItems>? = null

    private var textToSpeechUtil: TextToSpeechUtil? = null

    private var task: MarqueeTask? = null
    private var timer: Timer? = null
    private var playtimer: Timer? = null
    private var nRepeatCount = 0
    private var nRepeatTime = 0
    private var nSkinStyle = 0
    private var nSkinStyleResource = 0
    private var nSwipeResource = 0
    private var bIsAutoPlay = false
    private var bUseSound = false

    private var handler: Handler? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Log.e(tag, "onCreateView")

        try {

            val bundle = arguments
            sMenu = bundle!!.getString("MenuItem", "")
            bundle.clear()
        } catch (ex: Exception) {
            ex.message.toString()
        }

        mContext = container!!.context
        MainView = inflater.inflate(R.layout.fragment_2, container, false)

        return MainView
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Log.e(tag, "onViewCreated")

        init()

        changeDisplay()

        InitSkinStyle()

        DisplayMarquee()

        InitData()

        CurrentKata()
    }

    override fun onPause() {
        super.onPause()

        try {
            if (bIsAutoPlay) {
                RunAutoPlay()
                DisplayMarquee()
            }
        } catch (e: Exception) {
        }
    }

    override fun onResume() {
        super.onResume()

        try {

            //다시 돌아왔을 경우 재시작을 자동으로
            /*
            if (bIsAutoPlay) {
                RunAutoPlay()
                DisplayMarquee()
            }
            */
        } catch (e: Exception) {
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        //Log.e(tag, "onDestroyView")

        try {
            handler!!.removeMessages(0)
            closeTimer()
            closePlayTimer()
        } catch (e: Exception) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {

            val val1 = data!!.getStringExtra("KataIndo")
            val val2 = data.getStringExtra("lembutlidah")
            KataIndo!!.text = val1
            lembutlidah!!.text = val2
        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    private fun init() {

        cLChange = MainView!!.findViewById(R.id.cLChange) as View
        ivChange = cLChange!!.findViewById<View>(R.id.ivChange) as RoundImageView
        ivChange!!.setBackgroundColor(Color.parseColor("#66000000"))
        cLChange!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {

                try {

                    if (!bIsAutoPlay) {

                        bIsAutoPlay = true
                        ivChange!!.setBackgroundResource(nSkinStyleResource)

                        Editsentence!!.visibility = View.INVISIBLE

                        RunAutoPlay()
                    } else {

                        bIsAutoPlay = false
                        ivChange!!.setBackgroundColor(Color.parseColor("#66000000"))

                        Editsentence!!.visibility = View.VISIBLE

                        RunAutoPlay()
                    }

                    IsVisibleBottomArrow()
                    DisplayMarquee()
                } catch (e: Exception) {
                }
            }
        })

        Editsentence = MainView!!.findViewById<View>(R.id.Editsentence) as RoundImageView
        Editsentence!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {

                    val intent = Intent(mContext, EditKalimatActivity::class.java)
                    intent.putExtra("KataIndo", KataIndo!!.text.toString())
                    intent.putExtra("lembutlidah", lembutlidah!!.text.toString())
                    //startActivity(intent)
                    startActivityForResult(intent, 1)

                    activity!!.overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
                } catch (e: Exception) {
                }
            }
        })

        cL1 = MainView!!.findViewById(R.id.cL1) as View
        CounterTimer = cL1!!.findViewById<View>(R.id.CounterTimer) as TextView

        Kataview = MainView!!.findViewById<View>(R.id.Kataview) as SwipeConstraintLayout
        KataIndo = Kataview!!.findViewById<View>(R.id.KataIndo) as TextView
        KataKor = Kataview!!.findViewById<View>(R.id.KataKor) as TextView
        lembutlidah = Kataview!!.findViewById<View>(R.id.lembutlidah) as TextView

        cL2 = MainView!!.findViewById(R.id.cL2) as View
        mView = cL2!!.findViewById<View>(R.id.mView) as TextView

        upPrePayImage = MainView!!.findViewById<View>(R.id.upPrePayImage) as RoundImageView
        upPrePayImage!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {
                    PreKata()
                } catch (e: Exception) {
                }
            }
        })

        upNextplayImage = MainView!!.findViewById<View>(R.id.upNextplayImage) as RoundImageView
        upNextplayImage!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {
                    NextKata()
                } catch (e: Exception) {
                }
            }
        })

        Kataview!!.setOnSwipeListener(object : OnSwipeConstraintListener {
            override fun swipeLeft() {
                try {
                    PreKata()
                } catch (e: Exception) {
                }
            }

            override fun swipeRight() {
                try {
                    NextKata()
                } catch (e: Exception) {
                }
            }

            override fun swipeUp() {}
            override fun swipeDown() {}
        })

        handler = Handler()

        textToSpeechUtil = TextToSpeechUtil(mContext)

        //사운드 사용 여부
        bUseSound = BahasaApplication.getInstance().getKalimatSound()

        //가나다라... 자동 반복 시간
        nRepeatTime = BahasaApplication.getInstance().getKataTime()
    }

    private fun InitData() {

        try {

            kataManager = KataManager.getInstance(mContext!!)

            mCollection = if (TextUtils.isEmpty(sMenu)) {
                kataManager!!.GetItems()
            } else {
                val sArray = sMenu!!.split("/").toTypedArray()
                kataManager!!.GetItemsPart(sArray[0], sArray[1])
            }

            nTotalPageCount = mCollection!!.size
            CurrentPlayIndex = 0

            //Log.e(tag, "mCollection:$mCollection")
            //Log.e(tag, "nTotalPageCount:$nTotalPageCount")
        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    private fun InitSkinStyle() {

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle()
        when (nSkinStyle) {
            0 -> {
                nSwipeResource = R.drawable.skin1_style
                nSkinStyleResource = R.color.skin1
            }
            1 -> {
                nSwipeResource = R.drawable.skin2_style
                nSkinStyleResource = R.color.skin2
            }
            2 -> {
                nSwipeResource = R.drawable.skin3_style
                nSkinStyleResource = R.color.skin3
            }
            3 -> {
                nSwipeResource = R.drawable.skin4_style
                nSkinStyleResource = R.color.skin4
            }
            4 -> {
                nSwipeResource = R.drawable.skin5_style
                nSkinStyleResource = R.color.skin5
            }
            5 -> {
                nSwipeResource = R.drawable.skin6_style
                nSkinStyleResource = R.color.skin6
            }
            6 -> {
                nSwipeResource = R.drawable.skin7_style
                nSkinStyleResource = R.color.skin7
            }
        }

        Kataview!!.setBackgroundResource(nSwipeResource)
        CounterTimer!!.setTextColor(resources.getColor(nSkinStyleResource))
        mView!!.setTextColor(resources.getColor(nSkinStyleResource))

        upPrePayImage!!.setBackgroundColor(resources.getColor(nSkinStyleResource))
        upNextplayImage!!.setBackgroundColor(resources.getColor(nSkinStyleResource))
    }

    private fun CurrentKata() {

        try {

            //DB
            if (CurrentPlayIndex < 0 || nTotalPageCount == 0) return

            //Log.e(tag, "CurrentPlayIndex:$CurrentPlayIndex")
            item = mCollection!![CurrentPlayIndex]
            KataIndo!!.text = item!!.kataIndo
            KataKor!!.text = item!!.kataKor
            lembutlidah!!.text = item!!.lembutlidah

            CounterTimerLabel()

            //현재 프레그멘트 활성화시만 음성 출력
            PlaySuaraSound(item!!.kataIndo)
        } catch (e: Exception) {
        }
    }

    private fun PreKata() {
        try {

            //DB
            if (nTotalPageCount == 0) return

            AnimatoSwipeLeft(Kataview!!)

            CurrentPlayIndex--
            if (CurrentPlayIndex < 0) CurrentPlayIndex = nTotalPageCount - 1

            //Log.e(tag, "CurrentPlayIndex:$CurrentPlayIndex")
            item = mCollection!![CurrentPlayIndex]
            KataIndo!!.text = item!!.kataIndo
            KataKor!!.text = item!!.kataKor
            lembutlidah!!.text = item!!.lembutlidah

            if (!bIsAutoPlay) CounterTimerLabel()

            PlaySuaraSound(item!!.kataIndo)

            if (bIsAutoPlay) nRepeatCount = 0
        } catch (e: Exception) {
        }
    }

    private fun NextKata() {
        try {

            //DB
            if (nTotalPageCount == 0) return

            AnimatoSwipeRight(Kataview!!)

            CurrentPlayIndex++
            if (CurrentPlayIndex > nTotalPageCount - 1) CurrentPlayIndex = 0

            //Log.e(tag, "CurrentPlayIndex:$CurrentPlayIndex")
            item = mCollection!![CurrentPlayIndex]
            KataIndo!!.text = item!!.kataIndo
            KataKor!!.text = item!!.kataKor
            lembutlidah!!.text = item!!.lembutlidah

            if (!bIsAutoPlay) CounterTimerLabel()

            PlaySuaraSound(item!!.kataIndo)

            if (bIsAutoPlay) nRepeatCount = 0

        } catch (e: Exception) {
        }
    }

    private fun closeTimer() {
        try {
            if (timer != null) {
                timer!!.cancel()
                timer = null
            }
        } catch (e: Exception) {
        }
    }

    private fun startTimer() {
        try {
            closeTimer()
            task = MarqueeTask(mView!!)
            timer = Timer()
            timer!!.schedule(task, 0, 20000)
        } catch (e: Exception) {
        }
    }

    private fun DisplayMarquee() {

        if (bIsAutoPlay) {
            val ContentMarquee = String.format("지금은 %d초마다 자동 실행중입니다.", nRepeatTime)
            mView!!.text = ContentMarquee
        } else {
            val ContentMarquee = String.format("Play 버튼을 클릭하시면 %d초마다 자동 실행합니다.", nRepeatTime)
            mView!!.text = ContentMarquee
        }

        startTimer()
    }

    private fun CounterTimerLabel() {

        try {
            val nDisplayIndex = CurrentPlayIndex + 1
            val sPage = String.format("현재 페이지 (%d/%d)", nDisplayIndex, nTotalPageCount)
            CounterTimer!!.setTextColor(resources.getColor(nSkinStyleResource))
            CounterTimer!!.text = sPage
        } catch (e: Exception) {
        }
    }

    private fun PlaySuaraSound(sText: String) {

        try {
            if (bUseSound) {
                handler!!.postDelayed({
                    if (textToSpeechUtil!!.isLanguageAvailable()) {
                        textToSpeechUtil!!.Speak(sText)
                    } else {
                        BahasaApplication.getInstance().Toast(mContext!!, "음성지원이 되지 않습니다.", false)
                    }
                }, 1000)
            }
        } catch (e: Exception) {
        }
    }

    private fun closePlayTimer() {
        try {
            if (playtimer != null) {
                playtimer!!.cancel()
                playtimer = null
            }
        } catch (e: Exception) {
        }
    }

    private fun RunAutoPlay() {

        closePlayTimer()

        playtimer = Timer()
        playtimer!!.schedule(object : TimerTask() {
            override fun run() {

                activity!!.runOnUiThread(Runnable {
                    try {

                        if (!bIsAutoPlay) {
                            nRepeatCount = 0
                            CounterTimerLabel()
                            return@Runnable
                        }

                        nRepeatCount++

                        if (nRepeatCount % 2 == 0) {
                            CounterTimer!!.setTextColor(resources.getColor(nSkinStyleResource))
                        } else {
                            CounterTimer!!.setTextColor(Color.TRANSPARENT)
                        }

                        val nDisplayIndex = CurrentPlayIndex + 1
                        val sPage = String.format("%d초 경과 (%d/%d)", nRepeatCount, nDisplayIndex, nTotalPageCount)
                        CounterTimer!!.text = sPage

                        //사운드 사용시
                        if (bUseSound) {
                            if (textToSpeechUtil!!.IsSpeaking()) return@Runnable
                        }

                        if (nRepeatTime <= nRepeatCount) {
                            nRepeatCount = 0
                            NextKata()
                        }

                    } catch (e: Exception) {
                    }
                })
            }
        }, 0, 1000)
    }

    private fun ChangeSkinStyle() {

        nSkinStyle = BahasaApplication.getInstance().getSkinStyle()
        when (nSkinStyle) {
            0 -> {
                nSwipeResource = R.drawable.skin1_style
                nSkinStyleResource = R.color.skin1
            }
            1 -> {
                nSwipeResource = R.drawable.skin2_style
                nSkinStyleResource = R.color.skin2
            }
            2 -> {
                nSwipeResource = R.drawable.skin3_style
                nSkinStyleResource = R.color.skin3
            }
            3 -> {
                nSwipeResource = R.drawable.skin4_style
                nSkinStyleResource = R.color.skin4
            }
            4 -> {
                nSwipeResource = R.drawable.skin5_style
                nSkinStyleResource = R.color.skin5
            }
            5 -> {
                nSwipeResource = R.drawable.skin6_style
                nSkinStyleResource = R.color.skin6
            }
            6 -> {
                nSwipeResource = R.drawable.skin7_style
                nSkinStyleResource = R.color.skin7
            }
        }

        Kataview!!.setBackgroundResource(nSwipeResource)
        CounterTimer!!.setTextColor(resources.getColor(nSkinStyleResource))
        mView!!.setTextColor(resources.getColor(nSkinStyleResource))

        upPrePayImage!!.setBackgroundColor(resources.getColor(nSkinStyleResource))
        upNextplayImage!!.setBackgroundColor(resources.getColor(nSkinStyleResource))
    }

    fun changeDisplay() {

        try {

            val display = activity!!.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            val screenWidth = size.x
            val screenHeight = size.y

            if (BahasaApplication.getInstance().getOrientation()) {

                val lp1 = Kataview!!.layoutParams as ConstraintLayout.LayoutParams
                lp1.height = screenHeight / 2
                lp1.leftMargin = dip2px(mContext!!, 5f)
                lp1.rightMargin = dip2px(mContext!!, 5f)
                Kataview!!.layoutParams = lp1

                val lp2 = cL1!!.layoutParams as ConstraintLayout.LayoutParams
                lp2.leftMargin = dip2px(mContext!!, 10f)
                lp2.rightMargin = dip2px(mContext!!, 10f)
                cL1!!.layoutParams = lp2

                val lp3 = cL2!!.layoutParams as ConstraintLayout.LayoutParams
                lp3.leftMargin = dip2px(mContext!!, 10f)
                lp3.rightMargin = dip2px(mContext!!, 10f)
                cL2!!.layoutParams = lp3

            } else {

                val lp = Kataview!!.layoutParams as ConstraintLayout.LayoutParams
                lp.height = screenHeight / 2
                lp.leftMargin = dip2px(mContext!!, 100f)
                lp.rightMargin = dip2px(mContext!!, 100f)
                Kataview!!.layoutParams = lp

                val lp2 = cL1!!.layoutParams as ConstraintLayout.LayoutParams
                lp2.leftMargin = dip2px(mContext!!, 100f)
                lp2.rightMargin = dip2px(mContext!!, 100f)
                cL1!!.layoutParams = lp2

                val lp3 = cL2!!.layoutParams as ConstraintLayout.LayoutParams
                lp3.leftMargin = dip2px(mContext!!, 10f)
                lp3.rightMargin = dip2px(mContext!!, 10f)
                cL2!!.layoutParams = lp3

            }

            IsVisibleBottomArrow()

        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    private fun IsVisibleBottomArrow() {

        if (BahasaApplication.getInstance().getOrientation()) {
            upPrePayImage!!.visibility = View.GONE
            upNextplayImage!!.visibility = View.GONE
        } else {
            if (bIsAutoPlay) {
                upPrePayImage!!.visibility = View.GONE
                upNextplayImage!!.visibility = View.GONE
            } else {
                upPrePayImage!!.visibility = View.VISIBLE
                upNextplayImage!!.visibility = View.VISIBLE
            }
        }
    }

    fun setChangeSetting() {

        ChangeSkinStyle()

        //사운드 사용 여부
        bUseSound = BahasaApplication.getInstance().getKalimatSound()

        //가나다라... 자동 반복 시간
        nRepeatTime = BahasaApplication.getInstance().getKataTime()
    }
}