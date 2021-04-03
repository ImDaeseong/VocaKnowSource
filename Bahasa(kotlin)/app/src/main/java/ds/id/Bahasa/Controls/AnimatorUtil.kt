package ds.id.Bahasa.Controls

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import ds.id.Bahasa.MainActivity

object AnimatorUtil {

    private val tag = AnimatorUtil::class.java.name

    //작아졌다 원래대로
    fun AnimatoScaleXY(view: View?) {
        val animatorSet = AnimatorSet()
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.8f, 1f)
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.8f, 1f)
        animatorSet.playTogether(scaleY, scaleX)
        animatorSet.duration = 1000
        animatorSet.start()
    }

    //왼쪽으로 swipe
    fun AnimatoSwipeLeft(view: View) {
        var mover = ObjectAnimator.ofFloat(view, "translationX", -view.width.toFloat(), 0f)
        mover.duration = 0
        mover.start()
        mover = ObjectAnimator.ofFloat(view, "translationX", view.width.toFloat(), 0f)
        mover.duration = 500
        mover.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //view.isClickable = true
            }
        })
        mover.start()

        //스프링 효과
        if (view != null) {
            val springForce = SpringForce(0f).setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(
                SpringForce.STIFFNESS_LOW
            )
            val springAnimation = SpringAnimation(view, DynamicAnimation.TRANSLATION_X).setSpring(springForce).setStartValue(view.width.toFloat())
            springAnimation.addEndListener { animation, canceled, value, velocity ->
                //view.isClickable = true
            }
            springAnimation.start()
        }
    }

    //오른쪽으로 swipe
    fun AnimatoSwipeRight(view: View) {
        var mover = ObjectAnimator.ofFloat(view, "translationX", view.width.toFloat(), 0f)
        mover.duration = 0
        mover.start()
        mover = ObjectAnimator.ofFloat(view, "translationX", -view.width.toFloat(), 0f)
        mover.duration = 500
        mover.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                //view.isClickable = true
            }
        })
        mover.start()

        //스프링 효과
        if (view != null) {
            val springForce = SpringForce(0f).setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_LOW)
            val springAnimation = SpringAnimation(view, DynamicAnimation.TRANSLATION_X).setSpring(springForce).setStartValue(-view.width.toFloat())
            springAnimation.addEndListener { animation, canceled, value, velocity ->
                //view.isClickable = true
            }
            springAnimation.start()
        }
    }

    //360 도 회전후 원래대로
    fun AnimatoRotate(v: View, sText: String?) {
        val objectAnimator = ObjectAnimator.ofFloat(v, "rotation", 0f, 360f)
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.duration = 200
        objectAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator) {
                v.animate().duration = 100
            }

            override fun onAnimationEnd(animation: Animator) {
                try {
                    //1번째 탭으로 이동
                    MainActivity.mActivity?.SelectTab(1, sText)
                } catch (ex: java.lang.Exception) {
                    Log.d(tag, ex.message.toString())
                }
            }
        })
        objectAnimator.start()
    }

    //아래에서 위로
    fun AnimatoBottomToTop(view: View?) {
        val objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 1000f, 500f, 200f, 50f, 0f)
        objectAnimator.duration = 1000
        objectAnimator.start()

        //스프링 효과
        if (view != null) {
            val springForce = SpringForce(0f).setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_LOW)
            val springAnimation = SpringAnimation(view, DynamicAnimation.TRANSLATION_Y).setSpring(springForce).setStartValue(1000f)
            springAnimation.addEndListener { animation, canceled, value, velocity ->
                view.isClickable = true
            }
            springAnimation.start()
        }
    }

    //위에서 아래로
    fun AnimatoTopToBottom(view: View?) {
        val objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0f, 50f, 200f, 500f, 1000f)
        objectAnimator.duration = 1000
        objectAnimator.start()

        //스프링 효과
        if (view != null) {
            val springForce = SpringForce(1000f).setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_LOW)
            val springAnimation = SpringAnimation(view, DynamicAnimation.TRANSLATION_Y).setSpring(springForce).setStartValue(0f)
            springAnimation.addEndListener { animation, canceled, value, velocity ->
                //view.isClickable = true
                view.visibility = View.GONE
            }
            springAnimation.start()
        }
    }
}