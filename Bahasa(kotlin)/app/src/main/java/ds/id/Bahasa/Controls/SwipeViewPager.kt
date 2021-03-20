package ds.id.Bahasa.Controls

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class SwipeViewPager : ViewPager {

    private var enabled: Boolean? = false

    constructor(context: Context) : super(context) {
        enabled = true
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        enabled = true
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (enabled!!) {
            super.onInterceptTouchEvent(ev)
        } else false
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (enabled!!) {
            super.onTouchEvent(ev)
        } else false
    }

    fun setPagingEnabled(enabled: Boolean) {
        this.enabled = enabled
    }
}