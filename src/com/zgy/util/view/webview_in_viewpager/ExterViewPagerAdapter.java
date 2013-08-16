package com.zgy.util.view.webview_in_viewpager;

import java.util.ArrayList;
import java.util.HashMap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * 邮件详情页ViewPager适配器
 * 
 * @Description:
 * @author: zhuanggy
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-8-8
 */
public class ExterViewPagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<String> mItems;

	private HashMap<String, ExterViewPagerItemView> mTempItemView;// 缓存当前选中的以及前后的ItemView

	public ExterViewPagerAdapter(FragmentManager fm, ArrayList<String> items) {
		super(fm);
		mTempItemView = new HashMap<String, ExterViewPagerItemView>();
		this.mItems = items;
	}

	@Override
	public Fragment getItem(int arg0) {
		ExterViewPagerItemView view = new ExterViewPagerItemView(this);
		mTempItemView.put(mItems.get(arg0), view);
		return view;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	 
	/**
	 * 从Adapter中删除一个item
	 * 
	 * @Description:
	 * @param uids
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-8-12
	 */
	public void removeByUid(String key) {
		mItems.remove(key);
		mTempItemView.remove(key);
		notifyDataSetChanged();
	}

	/** 对缓存三个view的Hasmap操作 **/
	public ExterViewPagerItemView getTempItemView(String key) {
		return mTempItemView.get(key);
	}

	public void removeTempItemView(String key) {
		mTempItemView.remove(key);
	}
}
