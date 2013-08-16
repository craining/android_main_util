package com.zgy.util.view.webview_in_viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * 
 * 详情页的ViewPager
 * 
 * 作用：获得WebView是否滑动到左右边界，从而控制ViewPager是否左右滑动
 * 
 * @Description:
 * @author:温楠
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-7-8
 */
public class ExterViewPager extends ViewPager {

	public ExterViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		// 当ViewPager里的WebView滑动到边缘时，才触发ViewPager的滑动
		if (v instanceof ExterWebView) {
			return ((ExterWebView) v).canScrollHor(-dx);
		} else {
			return super.canScroll(v, checkV, dx, x, y);
		}
	}

}
