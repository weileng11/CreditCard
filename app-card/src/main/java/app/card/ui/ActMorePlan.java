package app.card.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import app.card.R;
import app.card.R2;
import app.card.base.BaseTitleActivity;
import butterknife.BindView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 更多计划
 * @date: 2018/11/20 0020  
 * @time: 下午 5:11
 */
public class ActMorePlan extends BaseTitleActivity
{
	@BindView(R2.id.plan_tablayout)
	SlidingTabLayout planTablayout;
	@BindView(R2.id.plan_viewpager)
	ViewPager planViewpager;
	
	private ArrayList<Fragment> mFragments = new ArrayList<>();
	private final String[] mTitles = {
			"全部", "只能还款","空卡代还"
	};
	private MyPagerAdapter mAdapter;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_more_plan;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		
		setTitle("更多计划");
		
		//for (String title : mTitles) {
		//	mFragments.add(FmSimpleCard.getInstance(title));
		//}
		
		mAdapter = new MyPagerAdapter(getSupportFragmentManager());
		planViewpager.setAdapter(mAdapter);
		planTablayout.setViewPager(planViewpager);
	}
	
	private class MyPagerAdapter extends FragmentPagerAdapter
	{
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public int getCount() {
			return mFragments.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mTitles[position];
		}
		
		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}
	}
}
