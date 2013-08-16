package com.zgy.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.Animation.AnimationListener;

public class ViewUtil {

	/**
	 * 防止动画执行过程中背景空白，屏幕跳
	 * 
	 * @Description:
	 * @param v
	 * @param al
	 * @param measureHeight
	 * @param show
	 * @param ainmTime
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-8-14
	 */
	public static void animHideShowView(final View v, AnimationListener al, int measureHeight, final boolean show, int ainmTime) {

		if (measureHeight == 0) {
			measureHeight = v.getMeasuredHeight();
		}
		final int heightMeasure = measureHeight;
		Animation anim = new Animation() {

			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {

				if (interpolatedTime == 1) {
					v.setVisibility(show ? View.VISIBLE : View.GONE);
				} else {
					int height;
					if (show) {
						height = (int) (heightMeasure * interpolatedTime);
					} else {
						height = heightMeasure - (int) (heightMeasure * interpolatedTime);
					}
					v.getLayoutParams().height = height;
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		if (al != null) {
			anim.setAnimationListener(al);
		}
		anim.setDuration(ainmTime);
		v.startAnimation(anim);
	}
}
