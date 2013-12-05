package com.zgy.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

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
	
	
	/**
	 * 图标的旋转飞入飞出动画
	 * @param 
	 * @author zhuanggy
	 * @date 2013-12-5
	 */
	public static void startAnimationsRotate(ViewGroup viewgroup, int durationMillis, boolean in, AnimationListener listener) {
		Animation animation = null;
		if(in) {
			animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		} else {
			animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		}
		animation.setFillAfter(true);
		animation.setDuration(durationMillis);
		if(listener != null) {
			animation.setAnimationListener(listener);
		}
		viewgroup.startAnimation(animation);
	}
}
