package com.jain.ullas.binaryfeedback

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_animation.*

class FeedbackActivity : AppCompatActivity() {

    companion object {
        const val ANIMATION_DURATION = 500L
        const val GOOD_FEEDBACK = 1
        const val BAD_FEEDBACK = -1
        const val NO_FEEDBACK = 0
    }

    private var animatedState = NO_FEEDBACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        good_feedback_image.setOnClickListener {
            when (animatedState) {
                BAD_FEEDBACK -> {
                    goodFeedbackAnimationReverse()
                }
                GOOD_FEEDBACK -> {
                    Toast.makeText(this, "Open GOOD feedback notes", Toast.LENGTH_SHORT).show()
                }
                else -> goodFeedbackAnimation()
            }
        }

        bad_feedback_image.setOnClickListener {
            when (animatedState) {
                BAD_FEEDBACK -> {
                    Toast.makeText(this, "Open BAD feedback notes", Toast.LENGTH_SHORT).show()
                }
                GOOD_FEEDBACK -> {
                    badAnimationFeedbackReverse()
                }
                else -> badFeedbackAnimation()
            }
        }

        good_feedback_description.setOnClickListener {
            Toast.makeText(this, "Open GOOD feedback notes", Toast.LENGTH_SHORT).show()
        }

        bad_feedback_description.setOnClickListener {
            Toast.makeText(this, "Open BAD feedback notes", Toast.LENGTH_SHORT).show()
        }

//        refresh.setOnClickListener {
//            recreate()
//        }

    }


    private fun badFeedbackAnimation() {
        val goodFeedbackImageLeftAnimation = ObjectAnimator.ofFloat(good_feedback_image, View.TRANSLATION_X, (good_feedback_image.left - feedback_images_layout.left - dpToPx(resources.getDimension(R.dimen.dimen_16dp))) * -1f)
        val badFeedbackImageLeftAnimation = ObjectAnimator.ofFloat(bad_feedback_image, View.TRANSLATION_X, (bad_feedback_image.left - feedback_images_layout.left) * -1f + bad_feedback_image.width + dpToPx(resources.getDimension(R.dimen.dimen_24dp)) + dpToPx(resources.getDimension(R.dimen.dimen_16dp)))
        val badFeedbackDescriptionScaleAnimation = ObjectAnimator.ofFloat(bad_feedback_description, View.SCALE_X, 0f, 1f).apply {
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    bad_feedback_text.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                    bad_feedback_description.pivotX = feedback_images_layout.left.toFloat()
                    good_feedback_description.visibility = View.GONE
                    bad_feedback_description.visibility = View.VISIBLE
                }
            })
        }
        val badFeedbackDescriptionTextAlphaAnimation = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                bad_feedback_text.alpha = it.animatedValue as Float
            }
        }

        AnimatorSet().apply {
            playTogether(
                    goodFeedbackImageLeftAnimation,
                    badFeedbackImageLeftAnimation
            )
            duration = ANIMATION_DURATION
            interpolator = OvershootInterpolator(0.8f)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {
                    good_feedback_image.isEnabled = false
                    bad_feedback_image.isEnabled = false
                }
            })
            start()
        }

        AnimatorSet().apply {
            playTogether(badFeedbackDescriptionScaleAnimation, badFeedbackDescriptionTextAlphaAnimation)
            duration = ANIMATION_DURATION
            startDelay = ANIMATION_DURATION
            interpolator = OvershootInterpolator(0.8f)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    good_feedback_image.isEnabled = true
                    bad_feedback_image.isEnabled = true
                    animatedState = BAD_FEEDBACK
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }
            })
            start()
        }
    }

    private fun goodFeedbackAnimation() {
        val goodFeedbackImageLeftAnimation = ObjectAnimator.ofFloat(good_feedback_image, View.TRANSLATION_X, (good_feedback_image.left - feedback_images_layout.left - dpToPx(resources.getDimension(R.dimen.dimen_16dp))) * -1f)
        val badFeedbackImageLeftAnimation = ObjectAnimator.ofFloat(bad_feedback_image, View.TRANSLATION_X, (bad_feedback_image.left - feedback_images_layout.left) * -1f + good_feedback_image.width + dpToPx(resources.getDimension(R.dimen.dimen_24dp)))
        val goodFeedbackDescriptionScaleAnimation = ObjectAnimator.ofFloat(good_feedback_description, View.SCALE_X, 0f, 1f).apply {
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {

                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                    good_feedback_description.pivotX = feedback_images_layout.left.toFloat()
                    good_feedback_description.visibility = View.VISIBLE
                    bad_feedback_description.visibility = View.GONE
                }
            })
        }
        val badFeedbackImageRightAnimation = ObjectAnimator.ofFloat(bad_feedback_image, View.TRANSLATION_X, (feedback_images_layout.right - bad_feedback_image.right - bad_feedback_image.width + dpToPx(resources.getDimension(R.dimen.dimen_24dp))))

        AnimatorSet().apply {
            playTogether(goodFeedbackImageLeftAnimation,
                    badFeedbackImageLeftAnimation)
            duration = ANIMATION_DURATION
            interpolator = OvershootInterpolator(0.8f)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {
                    good_feedback_image.isEnabled = false
                    bad_feedback_image.isEnabled = false
                }
            })
            start()
        }

        AnimatorSet().apply {
            playTogether(goodFeedbackDescriptionScaleAnimation,
                    badFeedbackImageRightAnimation)
            startDelay = ANIMATION_DURATION
            duration = ANIMATION_DURATION
            interpolator = OvershootInterpolator(0.8f)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    good_feedback_image.isEnabled = true
                    bad_feedback_image.isEnabled = true
                    animatedState = GOOD_FEEDBACK
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            start()
        }
    }

    private fun badAnimationFeedbackReverse() {
        val badFeedbackImageLeftAnimation = ObjectAnimator.ofFloat(bad_feedback_image, View.TRANSLATION_X, (bad_feedback_image.left - feedback_images_layout.left) * -1f + bad_feedback_image.width + dpToPx(resources.getDimension(R.dimen.dimen_24dp)) + dpToPx(resources.getDimension(R.dimen.dimen_16dp)))
        val goodFeedbackDescriptionScaleAnimation = ObjectAnimator.ofFloat(good_feedback_description, View.SCALE_X, 1f, 0f).apply {
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    good_feedback_text.visibility = View.VISIBLE
                    good_feedback_description.visibility = View.GONE
                    bad_feedback_description.visibility = View.GONE
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                    good_feedback_description.pivotX = feedback_images_layout.left.toFloat()
                    good_feedback_description.visibility = View.VISIBLE
                }
            })
        }
        val badFeedbackDescriptionScaleAnimation = ObjectAnimator.ofFloat(bad_feedback_description, View.SCALE_X, 0f, 1f).apply {
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    bad_feedback_text.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                    bad_feedback_description.pivotX = feedback_images_layout.left.toFloat()
                    good_feedback_description.visibility = View.GONE
                    bad_feedback_description.visibility = View.VISIBLE
                }
            })
        }

        AnimatorSet().apply {
            playTogether(goodFeedbackDescriptionScaleAnimation,
                    badFeedbackImageLeftAnimation)
            duration = ANIMATION_DURATION
            interpolator = OvershootInterpolator(0f)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {
                    good_feedback_image.isEnabled = false
                    bad_feedback_image.isEnabled = false
                }
            })
            start()
        }

        AnimatorSet().apply {
            playTogether(badFeedbackDescriptionScaleAnimation)
            startDelay = ANIMATION_DURATION
            duration = ANIMATION_DURATION
            interpolator = OvershootInterpolator(0f)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    good_feedback_image.isEnabled = true
                    bad_feedback_image.isEnabled = true
                    animatedState = BAD_FEEDBACK
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }
            })
            start()
        }
    }

    private fun goodFeedbackAnimationReverse() {
        val badFeedbackDescriptionScaleAnimation = ObjectAnimator.ofFloat(bad_feedback_description, View.SCALE_X, 1f, 0f).apply {
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    bad_feedback_text.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                    bad_feedback_description.pivotX = feedback_images_layout.left.toFloat()
                    bad_feedback_description.visibility = View.VISIBLE
                }
            })
        }
        val goodFeedbackDescriptionScaleAnimation = ObjectAnimator.ofFloat(good_feedback_description, View.SCALE_X, 0f, 1f).apply {
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    good_feedback_text.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                    good_feedback_description.pivotX = feedback_images_layout.left.toFloat()
                    good_feedback_description.visibility = View.VISIBLE
                    bad_feedback_description.visibility = View.GONE
                }
            })
        }
        val badFeedbackImageRightAnimation = ObjectAnimator.ofFloat(bad_feedback_image, View.TRANSLATION_X, (feedback_images_layout.right - bad_feedback_image.right - bad_feedback_image.width + dpToPx(resources.getDimension(R.dimen.dimen_24dp))))

        AnimatorSet().apply {
            play(badFeedbackDescriptionScaleAnimation)
            duration = ANIMATION_DURATION
            interpolator = OvershootInterpolator(0f)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {

                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {
                    good_feedback_image.isEnabled = false
                    bad_feedback_image.isEnabled = false
                }
            })
            start()
        }

        AnimatorSet().apply {
            playTogether(goodFeedbackDescriptionScaleAnimation, badFeedbackImageRightAnimation)
            duration = ANIMATION_DURATION
            startDelay = ANIMATION_DURATION
            interpolator = OvershootInterpolator(0f)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    good_feedback_image.isEnabled = true
                    bad_feedback_image.isEnabled = true
                    animatedState = GOOD_FEEDBACK
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }
            })
            start()
        }
    }


    fun dpToPx(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, resources.displayMetrics)

}
