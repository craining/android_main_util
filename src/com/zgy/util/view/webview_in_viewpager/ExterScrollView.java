package com.zgy.util.view.webview_in_viewpager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 用于已发送列表，点击已读收件人时，收件人过多造成的弹窗过高，UI显示不美观，此处进行控制，不超过1/3个屏幕高度 另外，在进行滚动时，不允许触发手势
 * 
 * @Description:
 * @author: zhuanggy
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-8-15
 */
public class ExterScrollView extends ScrollView {

	private static final String TAG = "AddressLayout";

	private int screenHalfHeight;// 屏幕高度
	Context mcontext;
	private MessageViewAddressLayoutScrollListener mListener;
	private int scrollY = 0;
	private boolean onDrawScrolling = false;
	private boolean onTouchScrolling = false;

	public ExterScrollView(Context context) {
		super(context);
		mcontext = context;
		setViewHeight();
	}

	public ExterScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mcontext = context;
		setViewHeight();

	}

	public void setViewHeight() {
		DisplayMetrics dMetrics = new DisplayMetrics();
		((Activity) mcontext).getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
		screenHalfHeight = dMetrics.heightPixels / 3;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int nowScrollY = getScrollY();
		if (scrollY == nowScrollY) {
			onDrawScrolling = false;
		} else {
			scrollY = nowScrollY;
			onDrawScrolling = true;
		}

		callBack();
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			onTouchScrolling = true;
			// Log.e(TAG, "ACTION_DOWN");
			break;
		case MotionEvent.ACTION_MOVE:
			onTouchScrolling = true;
			// Log.e(TAG, "ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			// Log.e(TAG, "ACTION_UP");
			onTouchScrolling = false;
			break;
		case MotionEvent.ACTION_CANCEL:
			onTouchScrolling = false;
			break;
		default:
			break;
		}
		callBack();
		return super.onTouchEvent(ev);
	}

	private void callBack() {

		if (mListener != null) {
			if (!onTouchScrolling && !onDrawScrolling) {
				Log.d(TAG, "没有滚动");
				mListener.onScrollingEnd();
			} else {
				Log.d(TAG, "滚动中。。。。");
				mListener.onScrolling();
			}
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int height = getMeasuredHeight();
		if (height > screenHalfHeight) {
			this.setMeasuredDimension(widthMeasureSpec, screenHalfHeight);
		}
	}

	public void setScrollingListener(MessageViewAddressLayoutScrollListener listener) {
		this.mListener = listener;
	}

	/**
	 * 回调接口
	 * 
	 * @Description:
	 * @author: zhuanggy
	 * @see:
	 * @since:
	 * @copyright © 35.com
	 * @Date:2013-8-14
	 */
	public interface MessageViewAddressLayoutScrollListener {

		public void onScrolling();

		public void onScrollingEnd();
	}
}
