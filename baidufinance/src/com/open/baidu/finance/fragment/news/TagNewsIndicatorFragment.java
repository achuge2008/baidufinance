/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午5:31:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.fragment.news;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.open.android.adapter.CommonFragmentPagerAdapter;
import com.open.android.fragment.BaseV4Fragment;
import com.open.baidu.finance.R;
import com.open.baidu.finance.bean.MainTabBean;
import com.open.baidu.finance.json.MainTabJson;
import com.open.baidu.finance.utils.UrlUtils;
import com.open.indicator.TabPageIndicator;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-18下午5:31:00
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class TagNewsIndicatorFragment extends BaseV4Fragment<MainTabJson, TagNewsIndicatorFragment>{
	public ViewPager viewpager;
	public TabPageIndicator indicator;
	public List<String> titleList = new ArrayList<String>();
	public List<Fragment> listRankFragment = new ArrayList<Fragment>();// view数组
	public CommonFragmentPagerAdapter mRankPagerAdapter;
	public ArrayList<MainTabBean> list = new ArrayList<MainTabBean>();
	
	public static TagNewsIndicatorFragment newInstance(String url, boolean isVisibleToUser) {
		TagNewsIndicatorFragment fragment = new TagNewsIndicatorFragment();
		fragment.setFragment(fragment);
		fragment.setUserVisibleHint(isVisibleToUser);
		fragment.url = url;
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_common_indicator_viewpager, container, false);
		viewpager = (ViewPager) view.findViewById(R.id.viewpager);
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		return view;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.open.qianbailu.fragment.BaseV4Fragment#initValues()
	 */
	@Override
	public void initValues() {
		// TODO Auto-generated method stub
		super.initValues();
		mRankPagerAdapter = new CommonFragmentPagerAdapter(getChildFragmentManager(), listRankFragment, titleList);
		viewpager.setAdapter(mRankPagerAdapter);
		indicator.setViewPager(viewpager);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.open.enrz.fragment.BaseV4Fragment#handlerMessage(android.os.Message)
	 */
	@Override
	public void handlerMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
		case MESSAGE_HANDLER:
			doAsync(this, this, this);
			break;
		default:
			break;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#call()
	 */
	@Override
	public MainTabJson call() throws Exception {
		// TODO Auto-generated method stub
		MainTabJson mMainTabJson = new MainTabJson();
		ArrayList<MainTabBean> clist = new ArrayList<MainTabBean>();
		MainTabBean bean = new MainTabBean();
		bean.setHref(UrlUtils.GUPIAO_BAIDU);
		bean.setTitle("今日要闻");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.GETTAGNEWS_ECONOMY);
		bean.setTitle("宏观经济");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.GETTAGNEWS);
		bean.setTitle("行业解析");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.GETTAGNEWS_THEME_HOT);
		bean.setTitle("概念热点");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.GETTAGNEWS_REPORT);
		bean.setTitle("研究报告");
		clist.add(bean);
		
		bean = new MainTabBean();
		bean.setHref(UrlUtils.GUPIAO_BAIDU);
		bean.setTitle("投资顾问");
		clist.add(bean);
		
		mMainTabJson.setList(clist);
		return mMainTabJson;
	}
	
	/* (non-Javadoc)
	 * @see com.open.android.fragment.BaseV4Fragment#onCallback(java.lang.Object)
	 */
	@Override
	public void onCallback(MainTabJson result) {
		// TODO Auto-generated method stub
		if(result==null){
			return;
		}
		list.clear();
		list.addAll(result.getList());
		titleList.clear();

		Fragment fragment;
		for (int i=0;i< result.getList().size();i++) {
			MainTabBean bean = result.getList().get(i);
			titleList.add(bean.getTitle());
			if(i==0){
				fragment = TodayNewsPullListFragment.newInstance(bean.getHref(),true);
			}else{
				if("投资顾问".equals(bean.getTitle())){
					fragment = AdviserPersonPullListFragment.newInstance(bean.getHref(),false);
				}else{
					fragment = TagNewsPullListFragment.newInstance(bean.getHref(),false);
				}
			}
			listRankFragment.add(fragment);
		}
		mRankPagerAdapter.notifyDataSetChanged();
		indicator.notifyDataSetChanged();
	}
	
}
