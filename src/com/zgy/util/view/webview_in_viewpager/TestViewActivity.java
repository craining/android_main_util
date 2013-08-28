package com.zgy.util.view.webview_in_viewpager;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class TestViewActivity extends FragmentActivity {

	ExterViewPagerAdapter mAdapter = new ExterViewPagerAdapter(getSupportFragmentManager(), new ArrayList<String>());

	OnPageChangeListener listener = new OnPageChangeListener() {

		int mScrollState;
		boolean canOnEdge = true;

		@Override
		public void onPageSelected(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			if (mScrollState == 1 && arg2 == 0 && (arg0 == 0 || arg0 == mAdapter.getCount() - 1)) {
				if (canOnEdge) {
					canOnEdge = false;
					onNoMorePager();
				}
			} else {
				canOnEdge = true;
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			mScrollState = arg0;
			if (mScrollState == 0) {
				canOnEdge = true;
			}
		}

		private void onNoMorePager() {
			// 没有上一封或下一封
		}
	};

}
