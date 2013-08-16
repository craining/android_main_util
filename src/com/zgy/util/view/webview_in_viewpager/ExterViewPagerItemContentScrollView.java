package com.zgy.util.view.webview_in_viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 邮件详情展示时，可以上下滚动的ScrollView，(MessaveViewViewPagerItemContentView里的)
 * 
 * 作用：解决双指缩放webview与页面上下滚动的冲突；此View用来控制页面上下滑动与否
 * 
 * @Description:
 * @author:gongfacun
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2012-11-2
 */
public class ExterViewPagerItemContentScrollView extends ScrollView {

	private GestureDetector mDetector;
	private boolean mScrolling = true;

	public ExterViewPagerItemContentScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDetector = new GestureDetector(new YScrollDetector());
	}

	public void setScrolling(boolean enable) {
		mScrolling = enable;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return (mScrolling) ? super.onTouchEvent(ev) : true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!mScrolling) {
			return false;
		}

		// This doesn't quite get us to diagonal scrolling, but it's somewhat better than what we've
		// currently got. This is based on
		// http://stackoverflow.com/questions/2646028/android-horizontalscrollview-within-scrollview-touch-handling
		boolean result = super.onInterceptTouchEvent(ev);
		// Let the original ScrollView handle ACTION_DOWN so we can stop the scroll when someone touches the
		// screen.
		if (ev.getAction() == MotionEvent.ACTION_DOWN || mDetector.onTouchEvent(ev)) {
			return result;
		}

		return false;
	}

	// Return false if we're scrolling in the x direction. That is, decline to consume the event and
	// let the parent class take a stab at it.
	class YScrollDetector extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			try {
				if (Math.abs(distanceY) - Math.abs(distanceX) > 0) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}
}
