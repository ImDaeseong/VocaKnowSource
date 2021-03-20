package ds.id.Bahasa.Controls

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator
import ds.id.Bahasa.MainActivity

object AnimatorUtil {

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
        var mover = ObjectAnimator.ofFloat(view, "translationX", view.width.toFloat(), 0f)
        mover.duration = 0
        mover.start()
        mover = ObjectAnimator.ofFloat(view, "translationX", -view.width.toFloat(), 0f)
        mover.duration = 500
        mover.start()
    }

    //오른쪽으로 swipe
    fun AnimatoSwipeRight(view: View) {
        var mover = ObjectAnimator.ofFloat(view, "translationX", -view.width.toFloat(), 0f)
        mover.duration = 0
        mover.start()
        mover = ObjectAnimator.ofFloat(view, "translationX", view.width.toFloat(), 0f)
        mover.duration = 500
        mover.start()
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

                //1번째 탭으로 이동
                //MainActivity.getMainActivity().SelectTab(1, sText)
            }
        })
        objectAnimator.start()
    }
}