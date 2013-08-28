package com.zgy.util.view.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 监听软键盘弹起
 * 
 * @Description:
 * @see:
 * @since:
 * @Date:2013-8-28
 */
@SuppressLint("WrongCall")
public class KeyboardListenRelativeLayout extends RelativeLayout {

	public static final byte KEYBOARD_STATE_SHOW = -3;
	public static final byte KEYBOARD_STATE_HIDE = -2;
	public static final byte KEYBOARD_STATE_INIT = -1;

	private boolean mHasInit = false;
	private boolean mHasKeyboard = false;
	private int mHeight;

	private IOnKeyboardStateChangedListener onKeyboardStateChangedListener;

	public KeyboardListenRelativeLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public KeyboardListenRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public KeyboardListenRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnKeyboardStateChangedListener(IOnKeyboardStateChangedListener onKeyboardStateChangedListener) {
		this.onKeyboardStateChangedListener = onKeyboardStateChangedListener;
	}

	// 以下3个变量用于临时测量，以防搜狗输入法等过大的问题
	boolean bOnMeasure = false;
	int tempwidthMeasureSpec = 0;
	int tempheightMeasureSpec = 0;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!bOnMeasure) {
			bOnMeasure = true;
			tempwidthMeasureSpec = widthMeasureSpec;
			tempheightMeasureSpec = heightMeasureSpec;
		}

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		if (!mHasInit) {
			mHasInit = true;
			mHeight = b;
			if (onKeyboardStateChangedListener != null) {
				onKeyboardStateChangedListener.onKeyboardStateChanged(KEYBOARD_STATE_INIT);
			}
		} else {
			mHeight = mHeight < b ? b : mHeight;
		}
		if (mHasInit && mHeight > b + 100) { // 软键盘显示
			mHasKeyboard = true;
			if (onKeyboardStateChangedListener != null) {
				onKeyboardStateChangedListener.onKeyboardStateChanged(KEYBOARD_STATE_SHOW);
				onMeasure(tempwidthMeasureSpec, tempheightMeasureSpec);
			}
		}
		if (mHasInit && mHasKeyboard && (mHeight >= b && mHeight <= b + 100)) {// 软键盘隐藏
			mHasKeyboard = false;
			if (onKeyboardStateChangedListener != null) {
				onKeyboardStateChangedListener.onKeyboardStateChanged(KEYBOARD_STATE_HIDE);
			}
		}

		super.onLayout(changed, l, t, r, b);

	}

	public interface IOnKeyboardStateChangedListener {

		public void onKeyboardStateChanged(int state);
	}
}
