package com.zgy.util.view.webview_in_viewpager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 详情页 ViewPager里的item view的容器；
 * 
 * @Description:
 * @see:
 * @since:
 * @Date:2013-8-7
 */
@SuppressLint("ValidFragment")
public class ExterViewPagerItemView extends Fragment {

	private static final String TAG = "MessageDetailView";

	private Context context;

	private ExterViewPagerAdapter mAdapter;

	private View mMainView;

	public ExterViewPagerItemView() {
	}

	public ExterViewPagerItemView(ExterViewPagerAdapter adapter) {
		this.mAdapter = adapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		context = inflater.getContext();
		// mMainView = inflater.inflate(R.layout., null);
		refreshItemView(true);

		return mMainView;
	}

	public void refreshItemView(boolean regetMessageInfo) {
		// 处理UI
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		// 移除缓存里的数据
	}
}