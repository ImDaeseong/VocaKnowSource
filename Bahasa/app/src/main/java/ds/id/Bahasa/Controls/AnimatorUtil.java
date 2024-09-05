package ds.id.Bahasa.Controls;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
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
    public static void AnimatoSwipeLeft(final View view){

        ObjectAnimator mover = ObjectAnimator.ofFloat(view,"translationX", -view.getWidth(), 0f);
        mover.setDuration(0);
        mover.start();
        mover = ObjectAnimator.ofFloat(view, "translationX",view.getWidth(), 0f);
        mover.setDuration(500);
        mover.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //view.setClickable(true);
            }
        });
        mover.start();

        //스프링 효과
        if (view != null) {

            SpringForce springForce = new SpringForce(0).setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_LOW);
            SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X).setSpring(springForce).setStartValue(view.getWidth());
            springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {

                    //view.setClickable(true);
                }
            });
            springAnimation.start();
        }
    }

    //오른쪽으로 swipe
    public static void AnimatoSwipeRight(final View view){

        ObjectAnimator mover = ObjectAnimator.ofFloat(view,"translationX", view.getWidth(), 0f);
        mover.setDuration(0);
        mover.start();
        mover = ObjectAnimator.ofFloat(view, "translationX", -view.getWidth(), 0f);
        mover.setDuration(500);
        mover.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //view.setClickable(true);
            }
        });
        mover.start();

        //스프링 효과
        if (view != null) {

            SpringForce springForce = new SpringForce(0).setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_LOW);
            SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_X).setSpring(springForce).setStartValue(-view.getWidth());
            springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {

                    //view.setClickable(true);
                }
            });
            springAnimation.start();
        }
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

    //아래에서 위로
    public static void AnimatoBottomToTop(final View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 1000, 500, 200, 50, 0);
        objectAnimator.setDuration(1000);
        objectAnimator.start();

        //스프링 효과
        if (view != null) {

            SpringForce springForce = new SpringForce(0).setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_LOW);
            SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y).setSpring(springForce).setStartValue(1000);
            springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {

                    //view.setClickable(true);
                }
            });
            springAnimation.start();
        }
    }

    //위에서 아래로
    public static void AnimatoTopToBottom(final View view){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, view.TRANSLATION_Y, 0, 50, 200, 500, 1000);
        objectAnimator.setDuration(1000);
        objectAnimator.start();

        //스프링 효과
        if (view != null) {

            SpringForce springForce = new SpringForce(1000).setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY).setStiffness(SpringForce.STIFFNESS_LOW);
            SpringAnimation springAnimation = new SpringAnimation(view, DynamicAnimation.TRANSLATION_Y).setSpring(springForce).setStartValue(0);
            springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
                @Override
                public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {

                    //view.setClickable(true);
                    view.setVisibility(View.GONE);
                }
            });
            springAnimation.start();
        }
    }
}