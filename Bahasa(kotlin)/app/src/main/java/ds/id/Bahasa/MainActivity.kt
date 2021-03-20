package ds.id.Bahasa

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import ds.id.Bahasa.Common.KataSetting
import ds.id.Bahasa.Controls.FloatingTextView
import ds.id.Bahasa.Controls.OnSingleClickListener
import ds.id.Bahasa.Controls.PermissionUtil.checkSelfPermission
import ds.id.Bahasa.Controls.PermissionUtil.requestPermissions
import ds.id.Bahasa.Controls.ScreenLockutil
import ds.id.Bahasa.Controls.UnitUtils.dip2px


class MainActivity : AppCompatActivity() {

    private val tag = MainActivity::class.java.simpleName

    private var mainActivity: MainActivity? = null
    fun getMainActivity(): MainActivity? {
        return mainActivity
    }

    private fun setMainActivity(mainActivity: MainActivity) {
        this.mainActivity = mainActivity
    }

    private var nSkinStyle = 0
    private var nBookResource = 0

    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    private var fragment1: Fragment1? = null
    private var fragment2: Fragment2? = null
    private var fragment3: Fragment3? = null
    private var fragment4: Fragment4? = null

    private var tabLayout: TabLayout? = null
    private var divider_line: View? = null

    private var floatingTextView: FloatingTextView? = null

    private var btnsetting: FloatingActionButton? = null

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setMainActivity(this)

        InitTitleBar()

        setContentView(R.layout.activity_main)

        initFragment()

        divider_line = findViewById<View>(R.id.divider_line)

        tabLayout = findViewById<View>(R.id.tablayout) as TabLayout
        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                SelectIndex(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        //설정
        btnsetting = findViewById<View>(R.id.btnsetting) as FloatingActionButton
        btnsetting!!.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                try {
                    Setting()

                    //설정 후 숨김
                    btnsetting!!.hide()
                } catch (e: Exception) {
                }
            }
        })

        Initdivider()
        setTabItemImage()
        SelectIndex(0)

        //가로/세로 모드 시작 체크
        val configuration = Resources.getSystem().configuration
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            //Log.e(tag, "portrait")
            BahasaApplication.getInstance().setOrientation(true)
        } else {

            //Log.e(tag, "landscape")
            BahasaApplication.getInstance().setOrientation(false)
        }

        //1o초 후 자동으로 설정 버튼을 숨김다.
        checkbtnSetting()

        //스크린 화면 락상태 설정
        getScreenLock()

        //initfloatingTextView()

        //권한 체크
        checkPermission()
    }

    private fun checkPermission() {

        if (checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) &&
            checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) &&
            checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
            checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ) {
            Log.e(tag, "권한 있음")
        } else {
            Log.e(tag, "권한이 없으면 권한 요청")
            requestPermissions(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            if (countDownTimer != null) countDownTimer!!.cancel()
        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        //Log.e(tag, "onConfigurationChanged")

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            BahasaApplication.getInstance().setOrientation(true)
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            BahasaApplication.getInstance().setOrientation(false)
        }

        if (fragment1!!.activity != null) {
            fragment1!!.changeDisplay()
        }

        if (fragment2!!.activity != null) {
            fragment2!!.changeDisplay()
        }

        if (fragment3!!.activity != null) {
            fragment3!!.changeDisplay()
        }
    }

    private fun checkbtnSetting() {

        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                try {

                    //Log.e(tag, "현재 경과:" + (millisUntilFinished / 1000) + "초")
                } catch (ex: Exception) {
                    ex.message.toString()
                }
            }

            override fun onFinish() {
                try {
                    //설정 후 숨김
                    btnsetting!!.hide()
                } catch (ex: Exception) {
                    ex.message.toString()
                }
            }
        }
        countDownTimer!!.start()
    }

    private fun InitTitleBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            nSkinStyle = KataSetting.getInstance().skinStyle

            when (nSkinStyle) {
                0 -> {
                    window.statusBarColor = resources.getColor(R.color.skin_statusbar1)
                }
                1 -> {
                    window.statusBarColor = resources.getColor(R.color.skin_statusbar2)
                }
                2 -> {
                    window.statusBarColor = resources.getColor(R.color.skin_statusbar3)
                }
                3 -> {
                    window.statusBarColor = resources.getColor(R.color.skin_statusbar4)
                }
                4 -> {
                    window.statusBarColor = resources.getColor(R.color.skin_statusbar5)
                }
                5 -> {
                    window.statusBarColor = resources.getColor(R.color.skin_statusbar6)
                }
                6 -> {
                    window.statusBarColor = resources.getColor(R.color.skin_statusbar7)
                }
            }
        }
    }

    private fun Initdivider() {

        nSkinStyle = KataSetting.getInstance().skinStyle
        when (nSkinStyle) {
            0 -> {
                divider_line!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline1))
            }
            1 -> {
                divider_line!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline2))
            }
            2 -> {
                divider_line!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline3))
            }
            3 -> {
                divider_line!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline4))
            }
            4 -> {
                divider_line!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline5))
            }
            5 -> {
                divider_line!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline6))
            }
            6 -> {
                divider_line!!.setBackgroundColor(ContextCompat.getColor(this, R.color.skin_divideline7))
            }
        }
    }

    private fun setTabItemImage() {

        nSkinStyle = KataSetting.getInstance().skinStyle
        when (nSkinStyle) {
            0 -> {
                nBookResource = R.drawable.book1
            }
            1 -> {
                nBookResource = R.drawable.book2
            }
            2 -> {
                nBookResource = R.drawable.book3
            }
            3 -> {
                nBookResource = R.drawable.book4
            }
            4 -> {
                nBookResource = R.drawable.book5
            }
            5 -> {
                nBookResource = R.drawable.book6
            }
            6 -> {
                nBookResource = R.drawable.book7
            }
        }

        tabLayout!!.getTabAt(0)!!.setIcon(nBookResource)
        tabLayout!!.getTabAt(1)!!.setIcon(nBookResource)
        tabLayout!!.getTabAt(2)!!.setIcon(nBookResource)
        tabLayout!!.getTabAt(3)!!.setIcon(nBookResource)
    }

    private fun initFragment() {

        fragment1 = Fragment1()
        fragment2 = Fragment2()
        fragment3 = Fragment3()
        fragment4 = Fragment4()
    }

    private fun SelectIndex(nIndex: Int) {

        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager!!.beginTransaction()
        when (nIndex) {
            0 -> {
                fragmentTransaction!!.replace(R.id.frameLayout, fragment1!!)
                fragmentTransaction!!.commit()
            }
            1 -> {
                fragmentTransaction!!.replace(R.id.frameLayout, fragment2!!)
                fragmentTransaction!!.commit()
            }
            2 -> {
                fragmentTransaction!!.replace(R.id.frameLayout, fragment3!!)
                fragmentTransaction!!.commit()
            }
            3 -> {
                fragmentTransaction!!.replace(R.id.frameLayout, fragment4!!)
                fragmentTransaction!!.commit()
            }
        }
    }

    fun SelectTab(nIndex: Int, SelectMenuItem: String?) {

        try {

            val bundle = Bundle()
            bundle.putString("MenuItem", SelectMenuItem)
            fragment2!!.arguments = bundle
            tabLayout!!.getTabAt(nIndex)!!.select()
        } catch (ex: Exception) {
            ex.message.toString()
        }
    }

    fun Setting() {

        startActivity(Intent(this, SettingActivity::class.java))
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    }

    private fun ChangeSkinStyle() {

        InitTitleBar()
        setTabItemImage()
        Initdivider()
    }

    public fun hideKeyboard() {

        try {
            if (currentFocus != null) {
                val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            }
        } catch (e: java.lang.Exception) {
        }
    }

    private fun initfloatingTextView() {

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val screenLength = size.y

        val rootView = findViewById<View>(R.id.clMain) as ViewGroup
        floatingTextView = FloatingTextView(rootView)
        floatingTextView!!.getFloatingview()!!.visibility = View.VISIBLE

        val layoutParams = floatingTextView!!.getFloatingview()!!.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.setMargins(0, screenLength - dip2px(this, 160f), 0, 0)
        floatingTextView!!.getFloatingview()!!.layoutParams = layoutParams
        rootView.addView(floatingTextView!!.getFloatingview())
    }

    fun setChangeSetting() {

        ChangeSkinStyle()

        if (fragment1!!.activity != null) {
            fragment1!!.setChangeSetting()
        }

        if (fragment2!!.activity != null) {
            fragment2!!.setChangeSetting()
        }

        if (fragment3!!.activity != null) {
            fragment3!!.setChangeSetting()
        }

        if (fragment4!!.activity != null) {
            fragment4!!.setChangeSetting()
        }
    }

    fun getScreenLock() {

        try {
            val bLock: Boolean? = KataSetting.getInstance().screenLock
            if (bLock != null) {
                ScreenLockutil.Lock_Flag(window, bLock)
            }
        } catch (e: java.lang.Exception) {
        }
    }
}
