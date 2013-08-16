package com.zgy.util.view.webview_in_viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.zgy.mainutil.R;

/**
 * 邮件详情的展示在此LinearLayout里，包括邮件体部分，以及邮件体上方的主题等信息的展示
 * 
 * 
 * 作用：解决双指缩放webview与页面上下滚动的冲突；此View用来判断第二个手指按下的情况，第二个手指按下时，页面不能上下滚动
 * 
 * @Description:
 * 
 *               If we have system zoom controls enabled, disable scrolling so the screen isn't wiggling
 *               around while trying to zoom.
 * 
 * @author:温楠
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-6-19
 */
public class ExterViewPagerItemContentView extends LinearLayout {

	private ExterViewPagerItemContentScrollView mContentScrollView;

	public ExterViewPagerItemContentView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ExterViewPagerItemContentView(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO
		// mContentScrollView = (MessageViewViewPagerItemContentScrollView)
		// findViewById(R.id.contentscrollview_msgview_body);

		if (ev.getAction() == MotionEvent.ACTION_UP) {
			// Text selection is finished. Allow scrolling again.
			mContentScrollView.setScrolling(true);
		}
		// If we have system zoom controls enabled, disable scrolling so the screen isn't wiggling around
		// while trying to zoom.
		if (ev.getAction() == MotionEvent.ACTION_POINTER_2_DOWN) {
			// 第二个手指按下，不控制屏幕滚动，因为此时是控制缩放的
			mContentScrollView.setScrolling(false);
		} else if (ev.getAction() == MotionEvent.ACTION_POINTER_2_UP) {
			mContentScrollView.setScrolling(true);
		}
		return super.dispatchTouchEvent(ev);
	}
}
