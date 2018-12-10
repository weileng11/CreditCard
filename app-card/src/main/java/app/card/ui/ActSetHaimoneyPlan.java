package app.card.ui;

import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import java.util.Arrays;
import app.card.R;
import app.card.R2;
import app.card.api.RxListener2;
import app.card.api.Server;
import app.card.api.Servers;
import app.card.base.BaseTitleActivity;
import app.card.model.CardInfo;
import app.card.model.M;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickHolder;
import app.card.ui.fragment.FmCustomPlan;
import app.card.ui.fragment.FmSysPlan;
import app.card.util.dialog.CommmonMessageDialog;
import app.card.view.CircleHeadImageView;
import app.card.view.ForbidViewPager;
import app.card.view.KmRecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 设置还款计划总页面
 * @date: 2018/11/21 0021  
 * @time: 下午 1:29
 */
public class ActSetHaimoneyPlan extends BaseTitleActivity
{
	@BindView(R2.id.rv_plan_set)
	KmRecyclerView rvPlanSet;
	@BindView(R2.id.txv_enact_plan)
	TextView txvEnactPlan;
	private ArrayList<Fragment> mFragments=new ArrayList<>();
	private final String[] mTitles={
			"系统计划", "自定义计划"
	};
	//viewpager 适配
	private MyPagerAdapter mMyPagerAdapter;
	//fm
	FmCustomPlan mFmCustomPlan;
	private String cardId;
	private CardInfo mCardInfo;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_set_haimoney_plan;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("设置还款计划");
		txvEnactPlan.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				if(txvEnactPlan.getText().toString().equals("制定计划")){
					getProxy().startActivity(ActSetCommitPlan.class);
				}else{
					//对话框
					CommmonMessageDialog commmonMessageDialog=
							new CommmonMessageDialog(ActSetHaimoneyPlan.this, false,
									new CommmonMessageDialog.CommonMessageListenter()
									{
										@Override
										public void submitSuccess(){
										}
									});
					commmonMessageDialog.setTitle("温馨提示")
							.setContent("请制定至少两天计划，可重复点击消费还款计划!")
							.setOneButton("知道了");
					commmonMessageDialog.show();
				}
			}
		});
		cardId=getIntent().getStringExtra("id");
		//查询卡信息
		loadCardInfo();
	}
	
	private void loadCardInfo(){
		////根据卡的id，查询对应的银行卡信息
		Servers.start(ActSetHaimoneyPlan.this, Server.get().getCardBankInfo(cardId),
				new RxListener2<CardInfo>()
				{
					@Override
					public void onNext(M<CardInfo> m, String msg){
						if(m==null) return;
						if(null!=m){
							if(1==m.code){
								mCardInfo=m.data;
								rvPlanSet.setAdapter(new Adapter());
								//rvPlanSet.getAdapter().notifyDataSetChanged();
							}else{
								toast(m.msg);
							}
						}
					}
				});
	}
	
	public CardInfo getCardInfo(){
		return mCardInfo;
	}
	
	class Adapter extends QuickAdapter<Integer>
	{
		public Adapter(){
			super(0, Arrays.asList(1, 2));
		}
		
		@Override
		public int getItemViewType(int p){
			Integer item=getItem(p);
			return item==null? EMPTY_VIEW: item;
		}
		
		@Override
		public QuickHolder onCreateViewHolder(ViewGroup vg, int t){
			try{
				return (ActSetHaimoneyPlan.H)Class.forName(ActSetHaimoneyPlan.H.class.getName()+t)
						.getConstructor(ActSetHaimoneyPlan.class)
						.newInstance(ActSetHaimoneyPlan.this);
			}catch(Exception e){
				e.printStackTrace();
			}
			return new QuickHolder(new Space(getContext()));
		}
		
		@Override
		protected void convert(QuickHolder h, Integer item){
			((ActSetHaimoneyPlan.H)h).update(item);
		}
	}
	
	@Keep
	class H<T> extends QuickHolder
	{
		public H(int layout){
			super(inflate(rvPlanSet, layout));
		}
		
		void update(int type){
		}
	}
	
	@Keep
	class H1 extends H
	{
		@BindView(R2.id.iv_setplan_head)
		CircleHeadImageView ivSetplanHead; //头像
		@BindView(R2.id.txv_setplan_name)
		TextView txvSetplanName; //银行名称
		@BindView(R2.id.txv_setplan_num)
		TextView txvSetplanNum; //银行卡尾号
		@BindView(R2.id.txv_setplan_username)
		TextView txvSetplanUsername; //用户名
		@BindView(R2.id.txv_setplan_limit)
		TextView txvSetplanLimit;  //额度
		@BindView(R2.id.txv_setplan_bill)
		TextView txvSetplanBill;  //账单日
		@BindView(R2.id.txv_setplan_refund)
		TextView txvSetplanRefund; //还款日
		
		public H1(){
			super(R.layout.item_set_plan_h1);
			ButterKnife.bind(this, itemView);
		}
		
		@Override
		void update(int type){
			super.update(type);
			if(null!=mCardInfo){
				Glide.with(ActSetHaimoneyPlan.this).load(mCardInfo.bank.thumb).into(ivSetplanHead);
				txvSetplanName.setText(mCardInfo.bankName);
				String cardCode=mCardInfo.cardCode.substring(mCardInfo.cardCode.length()-4,
						mCardInfo.cardCode.length());
				txvSetplanNum.setText("尾号"+cardCode);
				//用户名称
				if(mCardInfo.name.length()==2){
					String cardName=mCardInfo.name.substring(0, 1);
					txvSetplanUsername.setText(mCardInfo.name+"*");
				}else if(mCardInfo.name.length()>2){
					String cardFirst=mCardInfo.name.substring(0, 1);
					String cardLast=mCardInfo.name.substring(mCardInfo.name.length()-1,
							mCardInfo.name.length());
					StringBuilder sp=new StringBuilder();
					sp.append(cardFirst);
					int postion=mCardInfo.name.length()-2;
					for(int i=0; i<postion; i++){ sp.append("*"); }
					sp.append(cardLast);
					txvSetplanUsername.setText(sp.toString());
				}
				txvSetplanLimit.setText(String.valueOf(mCardInfo.act));
				//账单
				txvSetplanBill.setText(String.valueOf(mCardInfo.billDate)+"日");
				//还款
				txvSetplanRefund.setText(String.valueOf(mCardInfo.repayDate)+"日");
			}
		}
	}
	
	@Keep
	class H2 extends H
	{
		@BindView(R2.id.tablayout)
		SlidingTabLayout tablayout;
		@BindView(R2.id.set_viewpager)
		ForbidViewPager setViewpager;
		
		public H2(){
			super(R.layout.item_set_plan_h2);
			ButterKnife.bind(this, itemView);
			mFmCustomPlan=new FmCustomPlan();
			mFragments.add(new FmSysPlan());
			mFragments.add(mFmCustomPlan);
			//禁止滑动
			setViewpager.setScanScroll(false);
			mMyPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
			setViewpager.setAdapter(mMyPagerAdapter);
			tablayout.setViewPager(setViewpager);
			setViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
			{
				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels){
				}
				
				@Override
				public void onPageSelected(int position){
					if(mTitles[position].equals("系统计划")){
						txvEnactPlan.setText("制定计划");
					}else{
						txvEnactPlan.setText("确认添加");
					}
				}
				
				@Override
				public void onPageScrollStateChanged(int state){
				}
			});
			//禁止点击
			LinearLayout tabStrip = (LinearLayout) tablayout.getChildAt(0);
			for (int i = 0; i < tabStrip.getChildCount(); i++) {
				View tabView = tabStrip.getChildAt(i);
				if (tabView != null) {
					tabView.setClickable(false);
				}
			}
			
			//tablayout.setOnTabSelectListener(new OnTabSelectListener()
			//{
			//	@Override
			//	public void onTabSelect(int position){
			//		if(position==1) toast("暂未开放");
			//	}
			//
			//	@Override
			//	public void onTabReselect(int position){
			//	}
			//});
		}
		
		@Override
		void update(int type){
			super.update(type);
		}
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
			return mTitles[position];
		}
		
		@Override
		public Fragment getItem(int position){
			return mFragments.get(position);
		}
	}
}
