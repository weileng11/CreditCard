package app.card.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.Space;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import app.card.R;
import app.card.R2;
import app.card.api.*;
import app.card.base.BaseTitleActivity;
import app.card.model.*;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickClickAdapter;
import app.card.quick.QuickHolder;
import app.card.util.DateTimeUtil;
import app.card.util.SharedPreferencesUtils;
import app.card.util.dialog.CommmonMessageDialog;
import app.card.util.dialog.PersonInsuranceDialog;
import app.card.view.KmRecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description:信用卡管家
 * @date: 2018/11/21 0021  
 * @time: 下午 3:21
 */
public class ActCreditCardManager extends BaseTitleActivity
{
	@BindView(R2.id.rv_credit_card)
	KmRecyclerView rvCreditCard;
	final SparseArray<List> mDatas=new SparseArray<>();
	@BindView(R2.id.iv_card_person_bx)
	ImageView ivCardPersonBx;
	@BindView(R2.id.iv_card_service)
	ImageView ivCardService;
	int mRequestRun;
	List<CardManager> mCardList=new ArrayList<>();
	List<CardPlan> mCardPlanList=new ArrayList<>();
	private int page=1;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_credit_card_manager;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("信用卡管家");
		rvCreditCard.setAdapter(new Adapter());
		//加载数据
		//loadData();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		login();
	}
	
	//login
	private void login(){
		//13631427041   123456
		Servers.start(this, Server.get().doLogin("13631427041", "123456", "android"),
				new RxListener<M<User>>()
				{
					@Override
					public void onNext(M<User> m, String msg){
						getProxy().dismissLoading();
						if(m!=null){
							//保存token
							//TEST_TOKEN
							SharedPreferencesUtils.saveString(ActCreditCardManager.this, "token",
									m.data.token);
						}else{
							toast(msg);
						}
						////加载数据
						loadData();
					}
				});
	}
	
	private void loadData(){
		getProxy().showLoading("");
		if(mCardList.size()>0) mCardList.clear();
		new Request<CardManager.Page>(Server.get().creditManager())
		{
			@Override
			public void onNext(M<CardManager.Page> m){
				super.onNext(m);
				if(m==null) return;
				if(1==m.code){
					if(null!=m.data){
						CardManager.Page page=m.data;
						List<CardManager> list=page.data;
						if(list==null){
							mCardList=list;
						}else{
							mCardList.addAll(page.data);
						}
					}
				}else{
					toast(m.msg);
				}
				//还款计划
				loadCardPlan();
			}
		}.start();
	}
	
	private void loadCardPlan(){
		if(mCardPlanList.size()>0) mCardPlanList.clear();
		new Request<CardPlan.Page>(Server.get().cardHaiPlan(String.valueOf(page), "0"))
		{
			@Override
			public void onNext(M<CardPlan.Page> m){
				super.onNext(m);
				if(m==null) return;
				if(1==m.code){
					if(null!=m.data){
						CardPlan.Page page=m.data;
						List<CardPlan> list=page.data;
						if(list==null) mCardPlanList=list;
						else mCardPlanList.addAll(page.data);
						//if(mCardPlanList.size()>3){
						//	for(int i=mCardPlanList.size()-3; i>0; i--) mCardPlanList.remove(mCardPlanList.size()-1);
						//}
					}
				}else{
					toast(m.msg);
				}
			}
		}.start();
	}
	
	//点击图标保险
	@OnClick(R2.id.iv_card_person_bx)
	public void onViewClicked(){
		//弹窗显示保险图片
		new PersonInsuranceDialog(this).show();
	}
	
	//点击图标保险
	@OnClick(R2.id.iv_card_service)
	public void onViewClick(){
		Intent intent=new Intent(ActCreditCardManager.this, ActWeb.class);
		intent.putExtra(ActWeb.KEY_URL, Server.BASE_WEB+"chat/46861");
		startActivity(intent);
	}
	
	class Request<T> extends Subscriber<M<T>>
	{
		Observable<M<T>> observable;
		
		public Request(Observable<M<T>> observable){
			this.observable=observable;
		}
		
		public void start(){
			mRequestRun++;
			Servers.start(ActCreditCardManager.this, observable, this);
		}
		
		@Override
		public void onCompleted(){
			mRequestRun--;
			if(mRequestRun<=0){
				getProxy().dismissLoading();
				rvCreditCard.getAdapter().notifyDataSetChanged();
			}
		}
		
		@Override
		public void onError(Throwable ex){
			onCompleted();
		}
		
		@Override
		public void onNext(M<T> m){
		}
	}
	
	class Adapter extends QuickAdapter<Integer>
	{
		public Adapter(){
			super(0, Arrays.asList(1, 2, 3));
		}
		
		@Override
		public int getItemViewType(int p){
			Integer item=getItem(p);
			return item==null? EMPTY_VIEW: item;
		}
		
		@Override
		public QuickHolder onCreateViewHolder(ViewGroup vg, int t){
			try{
				return (H)Class.forName(H.class.getName()+t)
						.getConstructor(ActCreditCardManager.class)
						.newInstance(ActCreditCardManager.this);
			}catch(Exception e){
				e.printStackTrace();
			}
			return new QuickHolder(new Space(getContext()));
		}
		
		@Override
		protected void convert(QuickHolder h, Integer item){
			((H)h).update(item);
		}
	}
	
	class CardManagerAdapter<Item extends CardManager> extends QuickClickAdapter<Item>
	{
		public CardManagerAdapter(int layout){
			super(layout);
		}
		
		public CardManagerAdapter(int layout, List<Item> items){
			super(layout, items);
		}
		
		@Override
		protected void convert(QuickHolder h, final Item item){
			if(item==null) return;
			//用户名称
			if(item.name.length()==2){
				String cardName=item.name.substring(0, 1);
				h.setText(R.id.txv_card_name, cardName+"*");
			}else if(item.name.length()>2){
				String cardFirst=item.name.substring(0, 1);
				String cardLast=item.name.substring(item.name.length()-1, item.name.length());
				StringBuilder sp=new StringBuilder();
				sp.append(cardFirst);
				int postion=item.name.length()-2;
				for(int i=0; i<postion; i++){ sp.append("*"); }
				sp.append(cardLast);
				h.setText(R.id.txv_card_name, sp.toString());
			}
			//卡号
			String cardCode=item.cardCode.substring(0, 4);
			String cardCode1=
					item.cardCode.substring(item.cardCode.length()-4, item.cardCode.length());
			StringBuilder sp=new StringBuilder();
			sp.append("信用卡:");
			sp.append(cardCode);
			sp.append("*************");
			sp.append(cardCode1);
			h.setText(R.id.txv_card_num, sp.toString());
			//额度
			h.setText(R.id.txv_card_limit, String.valueOf(item.act));
			//账单日
			h.setText(R.id.txv_card_bill, String.valueOf(item.billDate)+"日");
			//还款日
			h.setText(R.id.txv_card_hai_ri, String.valueOf(item.repayDate)+"日");
			//设置背景
			ImageView ivCardBg=h.getView(R.id.iv_card_bg);
			ivCardBg.setVisibility(View.VISIBLE);
			h.setImage(R.id.iv_card_bg, item.bank.background);
			//设置状态
			if(null!=item.lastOrder){
				if(TextUtils.isEmpty(item.lastOrder.statusLang)){
					h.getView(R.id.crad_status_lang).setVisibility(View.GONE);
				}else{
					h.getView(R.id.crad_status_lang).setVisibility(View.VISIBLE);
					if(!TextUtils.isEmpty(item.lastOrder.statusLang))
						h.setText(R.id.txv_status_lang, item.lastOrder.statusLang);
				}
				//卡的模式
				if(item.lastOrder.type==2) h.setText(R.id.txv_card_model, "空卡周转");
				else h.setText(R.id.txv_card_model, "智能代还");
			}
		}
		
		@Override
		public void onClick(View v, int p){
			super.onClick(v, p);
		}
	}
	
	class CardPlanAdapter<Item extends CardPlan> extends QuickClickAdapter<Item>
	{
		public CardPlanAdapter(int layout){
			super(layout);
		}
		
		public CardPlanAdapter(int layout, List<Item> items){
			super(layout, items);
		}
		
		@Override
		protected void convert(final QuickHolder h, final Item item){
			if(item==null) return;
			////根据卡的id，查询对应的银行卡信息
			Servers.start(ActCreditCardManager.this,
					Server.get().getCardBankInfo(String.valueOf(item.cardId)),
					new RxListener2<CardInfo>()
					{
						@Override
						public void onNext(M<CardInfo> m, String msg){
							if(m==null) return;
							if(null!=m){
								if(1==m.code){
									//h.setImage(); 银行卡头像
									Log.i("info", m.data.bank.thumb);
									h.setImage(R.id.card_image_head, m.data.bank.thumb);
									//具体的银行
									h.setText(R.id.txv_plan_name, m.data.bankName);
								}else{
								}
							}
						}
					});
			//银行卡尾号
			h.setText(R.id.txv_plan_number, "尾号:"+item.cardCode);
			//计划的日期
			long time=item.addTime*1000L;
			h.setText(R.id.txv_plan_time, "计划日期:"+DateTimeUtil.formatDateTimeDay(time));
			//执行状态
			TextView tvStatus=h.getView(R.id.txv_plan_status);
			if(TextUtils.isEmpty(item.statusLang)) tvStatus.setText("未知");
			else{
				if(item.statusLang.equals("失败"))
					tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.empty_red));
				else tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.plan_more_tv));
				tvStatus.setText(item.statusLang);
			}
			//还款金额
			h.setText(R.id.txv_plan_money, item.moneyWithdraw);
			//智能待还
			if(item.type==2) h.setText(R.id.txv_plan_mode, "空卡周转");
			else h.setText(R.id.txv_plan_mode, "智能代还");
			//还款笔数
			h.setText(R.id.txv_plan_bishu, String.valueOf(item.payNum)+"笔");
			//手续费
			int num=Double.valueOf(item.fee).intValue();//转换为Int类型
			if(num==0){
				int dFeewith=Double.valueOf(item.moneyWithdraw).intValue();
				double feeFresult=dFeewith*0.012;
				DecimalFormat df=new DecimalFormat("#.##");
				double d=feeFresult;
				h.setText(R.id.txv_plan_procedure, "¥"+df.format(d));
			}else{
				h.setText(R.id.txv_plan_procedure, "¥"+String.valueOf(item.fee));
			}
			//h.setText(R.id.txv_plan_procedure, String.valueOf(item.orderWithdrawFee));
			//暂停还款计划
			h.getView(R.id.iv_plan_delete).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					CommmonMessageDialog commmonMessageDialog=
							new CommmonMessageDialog(ActCreditCardManager.this, true,
									new CommmonMessageDialog.CommonMessageListenter()
									{
										@Override
										public void submitSuccess(){
											//暂停计划
											Servers.start(ActCreditCardManager.this, Server.get()
															.pauseCardPlan(String.valueOf(item.id)),
													new RxListener2()
													{
														@Override
														public void onNext(M m, String msg){
															if(m==null) return;
															if(null!=m){
																if(1==m.code){
																	toast(m.msg);
																}else{
																}
															}else toast(msg);
														}
													});
										}
									});
					commmonMessageDialog.setTitle("温馨提示");
					commmonMessageDialog.setContent("是否将停止订单号为:"+item.orderSn+"计划的继续执行!");
					commmonMessageDialog.setNegativeButton("取消");
					commmonMessageDialog.setPositiveButton("确定");
					commmonMessageDialog.show();
				}
			});
		}
		
		@Override
		public void onClick(View v, int p){
			super.onClick(v, p);
		}
	}
	
	@Keep
	class H<T> extends QuickHolder
	{
		QuickAdapter<T> mAdapter;
		
		public H(int layout){
			super(inflate(rvCreditCard, layout));
		}
		
		void update(int type){
			if(mAdapter!=null) mAdapter.setNewData(getData(type));
		}
		
		List<T> getData(int type){
			List list=mDatas.get(type);
			if(list==null || list.isEmpty()){
				T[] data=getDftData();
				if(data!=null && data.length>0) list=Arrays.asList(data);
			}
			return list;
		}
		
		T[] getDftData(){
			return null;
		}
	}
	
	@Keep
	class H1 extends H
	{
		@BindView(R2.id.credit_h1_card_img)
		ImageView creditH1CardImg;
		
		public H1(){
			super(R.layout.item_credit_h1);
			ButterKnife.bind(this, itemView);
			creditH1CardImg.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					toast("敬请期待");
					//getProxy().startActivity(ActArea.class);
				}
			});
		}
		
		@Override
		void update(int type){
			super.update(type);
		}
	}
	
	@Keep
	class H2 extends H
	{
		@BindView(R2.id.txv_credit_card_manager)
		TextView txvCreditCardManager;
		@BindView(R2.id.iv_card_default)
		ImageView ivCardDefault;
		@BindView(R2.id.rv_add_card)
		KmRecyclerView rvAddCard;
		@BindView(R2.id.card_add)
		CardView cardAdd;
		
		public H2(){
			super(R.layout.item_credit_h2);
			ButterKnife.bind(this, itemView);
			rvAddCard.setAdapter(mAdapter=new CardManagerAdapter(R.layout.item_banner_card));
			txvCreditCardManager.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					//getActivity().startActivity(ActWeb.intent(Server.BASE_WEB+"steward/cardManagement"));
					//Intent intent=new Intent(ActCreditCardManager.this, ActWeb.class);
					//intent.putExtra(ActWeb.KEY_URL, Server.BASE_WEB+"steward/cardManagement");
					//startActivity(intent);
					getProxy().startActivity(ActCreditCardManagerBanner.class);
				}
			});
			cardAdd.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					//添加信用卡
					//selectCardDialog();
					getProxy().startActivity(ActAddCardFlow.class);
				}
			});
			mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
			{
				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position){
					getProxy().startActivity(ActCreditCardManagerBanner.class);
				}
			});
		}
		
		@Override
		void update(int type){
			super.update(type);
			if(mCardList.size()>0){
				rvAddCard.setVisibility(View.VISIBLE);
				ivCardDefault.setVisibility(View.GONE);
				mAdapter.setNewData(mCardList);
			}else{
				rvAddCard.setVisibility(View.GONE);
				ivCardDefault.setVisibility(View.VISIBLE);
			}
		}
	}
	
	@Keep
	class H3 extends H
	{
		@BindView(R2.id.ll_fen_l)
		LinearLayout llFenL;
		@BindView(R2.id.txv_more_plan)
		TextView txvMorePlan;
		@BindView(R2.id.ll_plan_no_data)
		ImageView llPlanNoData;
		@BindView(R2.id.rv_plan)
		KmRecyclerView rvPlan;
		@BindView(R2.id.ll_card_flow)
		LinearLayout llCardFlow;
		
		public H3(){
			super(R.layout.item_credit_h3);
			ButterKnife.bind(this, itemView);
			rvPlan.setAdapter(mAdapter=new CardPlanAdapter(R.layout.item_hai_plan));
			llFenL.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					//我的
					//startActivity(ActWeb.intent(Server.BASE_WEB+"intelligentincome"));
					Intent intent=new Intent(ActCreditCardManager.this, ActWeb.class);
					intent.putExtra(ActWeb.KEY_URL, Server.BASE_WEB+"intelligentincome");
					startActivity(intent);
				}
			});
			txvMorePlan.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					//Intent intent=new Intent(ActCreditCardManager.this, ActWeb.class);
					//intent.putExtra(ActWeb.KEY_URL, Server.BASE_WEB+"steward/planList");
					//startActivity(intent);
					Intent intent=new Intent(ActCreditCardManager.this, ActPlanList.class);
					intent.putExtra("dialog", "no");
					startActivity(intent);
					//getProxy().startActivity(ActPlanList.class);
					////更多计划
					//getActivity().startActivity(ActWeb.intent(Server.BASE_WEB+"steward/planList"));
				}
			});
			mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
			{
				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position){
					CardPlan cardPlan=(CardPlan)mAdapter.getItem(position);
					//详情页面
					Intent intent=new Intent(ActCreditCardManager.this, ActPlanDetail.class);
					intent.putExtra("id", String.valueOf(cardPlan.id));
					intent.putExtra("cardId", String.valueOf(cardPlan.cardId));
					intent.putExtra("remark", cardPlan.remark);
					startActivity(intent);
				}
			});
			//空卡流程
			llCardFlow.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					Intent intent=new Intent(ActCreditCardManager.this, ActWeb.class);
					intent.putExtra(ActWeb.KEY_URL,
							Server.BASE_WEB+"user/use_detail?id=8&name=空卡周转操作流程");
					startActivity(intent);
				}
			});
		}
		
		@Override
		void update(int type){
			super.update(type);
			if(null!=mCardPlanList){
				if(mCardPlanList.size()>0){
					llPlanNoData.setVisibility(View.GONE);
					rvPlan.setVisibility(View.VISIBLE);
					mAdapter.setNewData(mCardPlanList);
				}else{
					llPlanNoData.setVisibility(View.VISIBLE);
					rvPlan.setVisibility(View.GONE);
				}
			}
		}
	}
}
