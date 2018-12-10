package app.card.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import java.util.List;
import app.card.R;
import app.card.R2;
import app.card.api.RxListener2;
import app.card.api.Server;
import app.card.api.Servers;
import app.card.base.BaseTitleActivity;
import app.card.model.M;
import app.card.model.Menu;
import app.card.ui.fragment.FmSimpleCard;
import butterknife.BindView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 计划列表
 * @date: 2018/11/19 0019  
 * @time: 下午 6:12
 */
public class ActPlanList extends BaseTitleActivity
{
	@BindView(R2.id.tab_layout)
	SlidingTabLayout tabLayout;
	@BindView(R2.id.viewpager_pain)
	ViewPager viewpagerPain;
	private Context mContext=this;
	private ArrayList<Fragment> mFragments=new ArrayList<>();
	//private final String[] mTitles = {
	//		"全部", "等待执行", "执行中"
	//		, "失败", "成功","审核中","审核失败"
	//};
	private MyPagerAdapter mAdapter;
	private List<String> mTitles=new ArrayList<>();
	private String dialohShow;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_plan_list;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle("计划列表");
		dialohShow=getIntent().getStringExtra("dialog");
		//加载数据
		loadData();
		
		
		///** 默认 */
		//SlidingTabLayout tabLayout_1 = ViewFindUtils.find(decorView, R.id.tl_1);
		///**自定义部分属性*/
		//SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_2);
		///** 字体加粗,大写 */
		//SlidingTabLayout tabLayout_3 = ViewFindUtils.find(decorView, R.id.tl_3);
		///** tab固定宽度 */
		//SlidingTabLayout tabLayout_4 = ViewFindUtils.find(decorView, R.id.tl_4);
		///** indicator固定宽度 */
		//SlidingTabLayout tabLayout_5 = ViewFindUtils.find(decorView, R.id.tl_5);
		///** indicator圆 */
		//SlidingTabLayout tabLayout_6 = ViewFindUtils.find(decorView, R.id.tl_6);
		///** indicator矩形圆角 */
		//final SlidingTabLayout tabLayout_7 = ViewFindUtils.find(decorView, R.id.tl_7);
		///** indicator三角形 */
		//SlidingTabLayout tabLayout_8 = ViewFindUtils.find(decorView, R.id.tl_8);
		///** indicator圆角色块 */
		//SlidingTabLayout tabLayout_9 = ViewFindUtils.find(decorView, R.id.tl_9);
		///** indicator圆角色块 */
		//SlidingTabLayout tabLayout_10 = ViewFindUtils.find(decorView, R.id.tl_10);
		//
		//tabLayout_1.setViewPager(vp);
		//tabLayout_2.setViewPager(vp);
		//tabLayout_2.setOnTabSelectListener(this);
		//tabLayout_3.setViewPager(vp);
		//tabLayout_4.setViewPager(vp);
		//tabLayout_5.setViewPager(vp);
		//tabLayout_6.setViewPager(vp);
		//tabLayout_7.setViewPager(vp, mTitles);
		//tabLayout_8.setViewPager(vp, mTitles, this, mFragments);
		//tabLayout_9.setViewPager(vp);
		//tabLayout_10.setViewPager(vp);
		//
		//vp.setCurrentItem(4);
		//
		//tabLayout_1.showDot(4);
		//tabLayout_3.showDot(4);
		//tabLayout_2.showDot(4);
		//
		//tabLayout_2.showMsg(3, 5);
		//tabLayout_2.setMsgMargin(3, 0, 10);
		//MsgView rtv_2_3 = tabLayout_2.getMsgView(3);
		//if (rtv_2_3 != null) {
		//	rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
		//}
		//
		//tabLayout_2.showMsg(5, 5);
		//tabLayout_2.setMsgMargin(5, 0, 10);
	}
	//@Override
	//public void onTabSelect(int position) {
	//	Toast.makeText(mContext, "onTabSelect&position--->"+position, Toast.LENGTH_SHORT).show();
	//}
	//
	//@Override
	//public void onTabReselect(int position) {
	//	Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
	//}
	
	private void loadData(){
		Servers.start(this, Server.get().getPlanListTitle(), new RxListener2<Menu>()
		{
			@Override
			public void onNext(M<Menu> model, String msg){
				super.onNext(model, msg);
				if(model==null) return;
				if(model.code==1) if(model.data!=null){
					if(!TextUtils.isEmpty(model.data.$0))
					mTitles.add(model.data.$0);
					if(!TextUtils.isEmpty(model.data.$10))
					mTitles.add(model.data.$10);
					if(!TextUtils.isEmpty(model.data.$20))
					mTitles.add(model.data.$20);
					if(!TextUtils.isEmpty(model.data.$30))
					mTitles.add(model.data.$30);
					if(!TextUtils.isEmpty(model.data.$40))
					mTitles.add(model.data.$40);
					if(!TextUtils.isEmpty(model.data.$50))
					mTitles.add(model.data.$50);
					if(!TextUtils.isEmpty(model.data.$60))
					mTitles.add(model.data.$60);
					//初始化viewpager
					setViewpagerT();
				}else{
					//toast("");
				}
			}
		});
	}
	
	private void setViewpagerT(){
		for(String title : mTitles){
			mFragments.add(FmSimpleCard.getInstance(title,dialohShow));
		}
		mAdapter=new MyPagerAdapter(getSupportFragmentManager());
		viewpagerPain.setAdapter(mAdapter);
		tabLayout.setViewPager(viewpagerPain);
	}
	
	private class MyPagerAdapter extends FragmentPagerAdapter
	{
		public MyPagerAdapter(FragmentManager fm){
			super(fm);
		}
		
		@Override
		public int getCount(){
			return mFragments.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position){
			return mTitles.get(position);
		}
		
		@Override
		public Fragment getItem(int position){
			return mFragments.get(position);
		}
	}
}
