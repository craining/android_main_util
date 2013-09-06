package com.zgy.util.view.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zgy.mainutil.R;

/**
 * 重写TextView，用于支持3.0以下版本两行以上的TextView末尾显示省略号
 * 
 * @Description:
 * @author: zhuanggy
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2012-12-6
 */

public class EllipsizeTextView extends TextView {

	private static final String ELLIPSIS = "...";
	private static final String LINE = " - ";

	private boolean isStale;// 是否需要对文字进行处理，目前仅在3行时进行处理
	private boolean programmaticChange;
	private boolean isEllipsized;
	private String tempFullText;
	private String mTitle;
	private String mContent;
	private int maxLines = 3;
	private float lineSpacingMultiplier = 1.0f;
	private float lineAdditionalVerticalPadding = 0.0f;
	private boolean isRead; // 邮件是否已读，true为已读，不加粗；false为未读，加粗；

	// 以下是预览行数
	private static final int PREVIEW_LINE_ONE = 1;// 预览1行
	private static final int PREVIEW_LINE_TWO = 2;// 预览2行
	private static final int PREVIEW_LINE_THREE = 3;// 预览3行
	private static final int PREVIEW_LINE_FOUR = 4;// 预览4行

	public EllipsizeTextView(Context context) {
		super(context);
	}

	public EllipsizeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EllipsizeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isEllipsized() {
		return isEllipsized;
	}

	public void setTextShow(String title, String content, boolean read, int maxLines) {
		this.isRead = read;
		this.mTitle = title;
		this.mContent = LINE + content;
		tempFullText = this.mTitle + this.mContent;

		this.maxLines = maxLines;
		// 两行、或一行时，显示效果肯能会有问题，在此优化，仅在超过两行预览时处理省略号。
		switch (maxLines) {
		case PREVIEW_LINE_ONE:
			super.setSingleLine(true);
			super.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
			isStale = false;
			setTextOnShow(tempFullText);
			break;
		case PREVIEW_LINE_TWO:
			super.setSingleLine(false);
			super.setMaxLines(2);
			super.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
			isStale = false;
			setTextOnShow(tempFullText);
			break;
		case PREVIEW_LINE_THREE:
			isStale = true;
			super.setSingleLine(false);
			super.setMaxLines(maxLines);
			super.setEllipsize(null);// 不设置末尾省略号，用代码实现
			this.setText(tempFullText);
			break;
		case PREVIEW_LINE_FOUR:
			isStale = true;
			super.setSingleLine(false);
			super.setMaxLines(maxLines);
			super.setEllipsize(null);// 不设置末尾省略号，用代码实现
			this.setText(tempFullText);
			break;
		default:
			break;
		}
		// super.setMaxLines(maxLines);
		// this.setTextSize(getResources().getDimensionPixelSize(R.dimen.messagelist_preview_textsize));
	}

	@Override
	public void setLineSpacing(float add, float mult) {
		this.lineAdditionalVerticalPadding = add;
		this.lineSpacingMultiplier = mult;
		super.setLineSpacing(add, mult);
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int before, int after) {
		super.onTextChanged(text, start, before, after);
		if (!programmaticChange && isStale) {
			tempFullText = text.toString();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (isStale) {
			// Debug.v("EllipsizeText", "EllipsizeText  onDrow............................");
			resetText();// 如果设成3行预览，则进行处理
		}
		super.onDraw(canvas);
	}

	/**
	 * 超过两行时对文字进行处理
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2012-12-6
	 */
	private void resetText() {
		// Debug.v("EllipsizeText", "EllipsizeText  resetText............................");

		String workingText = tempFullText;
		// Debug.v("EllipsizeText", "pre fullText =" + fullText);
		boolean ellipsized = false;
		Layout layout = createWorkingLayout(workingText);
		if (layout.getLineCount() > maxLines) {

			workingText = tempFullText.substring(0, (layout.getLineEnd(maxLines - 1) - 1)).trim();
			Layout layout2 = createWorkingLayout(workingText + ELLIPSIS);
			while (layout2.getLineCount() > maxLines && (workingText.length() - 1) > 0) {
				workingText = workingText.substring(0, workingText.length() - 1);
				layout2 = createWorkingLayout(workingText + ELLIPSIS);
			}
			ellipsized = true;
		} else {
			setLines(maxLines);// 不够预设行数，仍设置该行数，便于控制itemview后层已删除-撤销条的高度.
		}

		int lastLength = workingText.length();
		// if (lastLength > tempFullText.length()) {
		if (lastLength < getText().length()) {
			// 超过的话加“...”
			workingText = workingText + ELLIPSIS;
		}
		programmaticChange = true;
		try {
			setTextOnShow(workingText);
		} finally {
			programmaticChange = false;
		}
		// }

		isStale = false;
		if (ellipsized != isEllipsized) {
			isEllipsized = ellipsized;
		}
	}

	private Layout createWorkingLayout(String workingText) {
		return new StaticLayout(workingText, getPaint(), getWidth() - getPaddingLeft() - getPaddingRight(), Alignment.ALIGN_NORMAL, lineSpacingMultiplier, lineAdditionalVerticalPadding, false);
	}

	/**
	 * 设置文字风格
	 * 
	 * @Description:
	 * @param workingText
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-5-31
	 */
	private void setTextOnShow(String workingText) {
		// 对处理后的text设置显示样式，此处为from样式不变，预览内容变。
		// 即：发件人 + 预览内容，开头 fromStrLength 个字符是发件人的名字长度，忽略这段文字，改变之后的文字样式（加粗与否）。若 isread
		// 为true则不加粗。若预览文字不包含发件人，则fromStrLength需传值为零
		SpannableStringBuilder spannable = new SpannableStringBuilder(workingText);
		// ColorStateList previewColor =
		// getResources().getColorStateList(R.color.message_preview_color);
//		ColorStateList contentColor = getResources().getColorStateList(R.color.grey);
//		ColorStateList titleColor;
//		TextAppearanceSpan titleSpan;
//		if (!isRead) {
//			titleColor = getResources().getColorStateList(R.color.black);// 未读标题为黑色
//			titleSpan = new TextAppearanceSpan(null, Typeface.NORMAL, getResources().getDimensionPixelSize(R.dimen.messagelist_preview_textsize), titleColor, titleColor);
//		} else {
//			titleColor = getResources().getColorStateList(R.color.grey);
//			titleSpan = new TextAppearanceSpan(null, Typeface.NORMAL, getResources().getDimensionPixelSize(R.dimen.messagelist_preview_textsize), titleColor, titleColor);
//		}

//		if (mTitle.length() >= workingText.length()) {
//			// 如果标题超过所有显示文字的长度
//			spannable.setSpan(titleSpan, 0, workingText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		} else {
//			// Debug.e("", "设置标题和预览的颜色");
//			// 标题显示，内容也有部分
//			spannable.setSpan(titleSpan, 0, mTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//			spannable.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, getResources().getDimensionPixelSize(R.dimen.messagelist_preview_textsize), contentColor, contentColor), mTitle.length(), workingText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}

		setText(spannable);
	}
}
