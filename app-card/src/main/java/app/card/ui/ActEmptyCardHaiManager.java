package app.card.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import app.card.R;
import app.card.R2;
import app.card.base.BaseTitleActivity;
import app.card.ui.fragment.FmGuarantee;
import app.card.ui.fragment.FmGuaranteeIntention;
import butterknife.BindView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description:空卡代还管理
 * @date: 2018/11/20 0020  
 * @time: 下午 2:02
 */
public class ActEmptyCardHaiManager extends BaseTitleActivity
{
	@BindView(R2.id.card_ok_add)
	CardView cardOkAdd;
	@BindView(R2.id.empty_tablayout)
	SlidingTabLayout emptyTablayout;
	@BindView(R2.id.empty_viewpager)
	ViewPager emptyViewpager;
	
	private ArrayList<Fragment> mFragments = new ArrayList<>();
	private final String[] mTitles = {
			"意向担保人", "已担保人"
	};
	private MyPagerAdapter mAdapter;
	@Override
	protected int getLayoutId(){
		return R.layout.act_empty_card_hai_manager;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("空卡代还管理");
		
		mFragments.add(new FmGuaranteeIntention());
		mFragments.add(new FmGuarantee());
		
		mAdapter = new MyPagerAdapter(getSupportFragmentManager());
		emptyViewpager.setAdapter(mAdapter);
		emptyTablayout.setViewPager(emptyViewpager);
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
