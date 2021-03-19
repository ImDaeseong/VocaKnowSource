package ds.id.Bahasa.Controls;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import ds.id.Bahasa.MainActivity;

public class AnimatorUtil {

    //작아졌다 원래대로
    public static void AnimatoScaleXY(View view){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.8f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.8f, 1f);
        animatorSet.playTogether(scaleY, scaleX);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    //왼쪽으로 swipe
    public static void AnimatoSwipeLeft(View view){

        ObjectAnimator mover = ObjectAnimator.ofFloat(view,"translationX", view.getWidth(), 0f);
        mover.setDuration(0);
        mover.start();
        mover = ObjectAnimator.ofFloat(view, "translationX", -view.getWidth(), 0f);
        mover.setDuration(500);
        mover.start();
    }

    //오른쪽으로 swipe
    public static void AnimatoSwipeRight(View view){

        ObjectAnimator mover = ObjectAnimator.ofFloat(view,"translationX", -view.getWidth(), 0f);
        mover.setDuration(0);
        mover.start();
        mover = ObjectAnimator.ofFloat(view, "translationX",view.getWidth(), 0f);
        mover.setDuration(500);
        mover.start();
    }

    //360 도 회전후 원래대로
    public static void AnimatoRotate(final View v, final String sText){

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "rotation", 0, 360);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(200);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                v.animate().setDuration(100);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                //1번째 탭으로 이동
                MainActivity.getMainActivity().SelectTab(1, sText);
            }
        });
        objectAnimator.start();
    }
}