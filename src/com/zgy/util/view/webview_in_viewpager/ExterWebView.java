package com.zgy.util.view.webview_in_viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * pushmail专用webView子类，拦截左右滑到边界事件
 * 
 * MessaveViewViewPagerItemBodyView里的WebView
 * 
 * 作用：判断WebView是否滑动到左右边界
 * 
 * @Description:
 * @author: cuiwei
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-5-13
 */
public class ExterWebView extends WebView {

	private SlideListener slideListener;

	public ExterWebView(Context context, AttributeSet atts) {
		super(context, atts);
		// setFocusable(false);
		setFocusableInTouchMode(false);
	}

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	//
	// if (getScrollX() <= 0) {
	// if (GlobalVariable.isWEBVIEW_CANMOVE_LEFTEND()) {
	// if (slideListener != null) {
	// slideListener.onToLeftEdge();
	//
	// }
	// GlobalVariable.setLeftEdge(true);
	// MailToast.makeText(EmailApplication.getInstance(), "左边滑到头", Toast.LENGTH_SHORT).show();
	// } else if (getScrollX() >= computeHorizontalScrollRange() - getWidth()) {
	// if (GlobalVariable.isWEBVIEW_CANMOVE_RIGHTEND()) {
	// if (slideListener != null) {
	// slideListener.onToRightEdge();
	//
	// }
	// GlobalVariable.setRightEdge(true);
	// MailToast.makeText(EmailApplication.getInstance(), "右边滑到头", Toast.LENGTH_SHORT).show();
	// }
	// }
	// } else if (getScrollX() >= computeHorizontalScrollRange() - getWidth()) {
	// if (GlobalVariable.isWEBVIEW_CANMOVE_RIGHTEND()) {
	// if (slideListener != null) {
	// slideListener.onToRightEdge();
	//
	// }
	// GlobalVariable.setRightEdge(true);
	// MailToast.makeText(EmailApplication.getInstance(), "右边滑到头", Toast.LENGTH_SHORT).show();
	// }
	// }
	// return super.onInterceptTouchEvent(ev);
	// }

	public boolean canScrollHor(float direction) {
		final int offset = computeHorizontalScrollOffset();
		final int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
		if (range == 0) {
			return false;
		}
		if (direction < 0) {
			return offset > 0;
		} else {
			return offset < range - 1;
		}
	}

	public interface SlideListener {

		/**
		 * 
		 * @Description:
		 * @param id
		 * @see:
		 * @since:
		 * @author: 温楠
		 * @date:2013-5-18
		 */
		public void onToLeftEdge();

		/**
		 * 
		 * @Description:
		 * @param id
		 * @see:
		 * @since:
		 * @author: 温楠
		 * @date:2013-5-18
		 */
		public void onToRightEdge();

	}

	public void setSlideListener(SlideListener mListener) {
		this.slideListener = mListener;
	}
}
