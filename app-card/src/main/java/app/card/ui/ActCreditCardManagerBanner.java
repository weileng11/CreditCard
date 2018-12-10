package app.card.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import app.card.R;
import app.card.R2;
import app.card.api.RxListener;
import app.card.api.Server;
import app.card.api.Servers;
import app.card.base.BaseTitleActivity;
import app.card.model.CardManager;
import app.card.model.CardRepayOrbill;
import app.card.model.M;
import app.card.nodata.ProjectNoDataLayout;
import app.card.nodata.ProjectNoDataShowUtils;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickHolder;
import app.card.util.CheckCodeTimer;
import app.card.util.dialog.CardClauseDialog;
import app.card.util.dialog.CardDeleteDialog;
import app.card.util.dialog.CardSelectDialog;
import app.card.util.pop.CardSetDatePop;
import app.card.view.KmRecyclerView;
import app.card.view.banner.mzbanner.MZBannerView;
import app.card.view.banner.mzbanner.holder.MZHolderCreator;
import app.card.view.banner.mzbanner.holder.MZViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 信用卡管家banber
 * @date: 2018/11/21 0021  
 * @time: 下午 6:03
 */
public class ActCreditCardManagerBanner extends BaseTitleActivity
{
	@BindView(R2.id.rv_banner)
	KmRecyclerView rvBanner;
	final SparseArray<List> mDatas=new SparseArray<>();
	int mRequestRun;
	List<CardManager> mCardList=new ArrayList<>();
	List<CardRepayOrbill> mCardRePayList=new ArrayList<>();
	List<CardRepayOrbill> mCardBillList=new ArrayList<>();
	private int mPosition=0;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_credit_card_manager_banner;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("信用卡管理");
		rvBanner.setAdapter(new Adapter());
		loadData();
	}
	
	private void loadData(){
		getProxy().showLoading("");
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
			}
		}.start();
	}
	
	class Request<T> extends Subscriber<M<T>>
	{
		Observable<M<T>> observable;
		
		public Request(Observable<M<T>> observable){
			this.observable=observable;
		}
		
		public void start(){
			mRequestRun++;
			Servers.start(ActCreditCardManagerBanner.this, observable, this);
		}
		
		@Override
		public void onCompleted(){
			mRequestRun--;
			if(mRequestRun<=0){
				getProxy().dismissLoading();
				rvBanner.getAdapter().notifyDataSetChanged();
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
				return (H)Class.forName(H.class.getName()+t)
						.getConstructor(ActCreditCardManagerBanner.class)
						.newInstance(ActCreditCardManagerBanner.this);
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
	
	@Keep
	class H<T> extends QuickHolder
	{
		QuickAdapter<T> mAdapter;
		
		public H(int layout){
			super(inflate(rvBanner, layout));
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
		@BindView(R2.id.banner)
		MZBannerView banner;
		@BindView(R2.id.ll_no_data)
		ProjectNoDataLayout llNoData;
		@BindView(R2.id.card_ok_add)
		CardView cardOkAdd;
		@BindView(R2.id.iv_card_bg)
		ImageView ivCardBg;
		@BindView(R2.id.txv_card_num)
		TextView txvCardNum;
		@BindView(R2.id.txv_card_limit)
		TextView txvCardLimit;
		@BindView(R2.id.txv_card_bill)
		TextView txvCardBill;
		@BindView(R2.id.txv_card_hai_ri)
		TextView txvCardHaiRi;
		@BindView(R2.id.ll_card_banner)
		LinearLayout cardBanner;
		@BindView(R2.id.txv_card_edit)
		TextView txvCardEdit;
		@BindView(R2.id.txv_last_order_repay)
		TextView txvLastOrderRepay;
		@BindView(R2.id.txv_last_order_repay_y)
		TextView txvLastOrderRepayY;
		@BindView(R2.id.txv_last_model)
		TextView txvLastModel;
		
		public H1(){
			super(R.layout.item_credit_banner_h1);
			ButterKnife.bind(this, itemView);
			txvCardEdit.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					//编辑
					if(mCardList.size()>0){
						CardManager cardManager=mCardList.get(mPosition);
						Log.i("info", String.valueOf(mPosition));
						Log.i("info=====", String.valueOf(cardManager.repayDate)+
						                   String.valueOf(cardManager.billDate));
						if(mCardRePayList.size()>0) mCardRePayList.clear();
						if(mCardBillList.size()>0) mCardBillList.clear();
						//初始化参数
						initDateParams(cardManager.id, cardManager.repayDate, cardManager.billDate);
					}
				}
			});
			//预约还款
			cardOkAdd.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					selectCardDialog();
				}
			});
		}
		
		@Override
		void update(int type){
			super.update(type);
			if(mCardList.size()>0){
				CardManager cardManager=mCardList.get(mPosition);
				//剩余还款
				txvLastOrderRepay.setText(String.valueOf(cardManager.lastOrderRepay));
				//剩余还款
				txvLastOrderRepayY.setText(String.valueOf(cardManager.lastOrderRepayY));
				//剩余还款
				txvLastModel.setText("空卡周转");
				//隐藏
				llNoData.setVisibility(View.GONE);
				banner.setVisibility(View.VISIBLE);
				cardBanner.setVisibility(View.GONE);
				banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener()
				{
					@Override
					public void onPageClick(View view, int position){
						CardManager cardManager=mCardList.get(mPosition);
						//详情页面
						Intent intent=new Intent(ActCreditCardManagerBanner.this, ActPlanDetail.class);
						intent.putExtra("id", String.valueOf(cardManager.lastOrder.id));
						intent.putExtra("cardId", String.valueOf(cardManager.id));
						intent.putExtra("remark",cardManager.lastOrder.remark);
						startActivity(intent);
					}
				});
				banner.addPageChangeListener(new ViewPager.OnPageChangeListener()
				{
					@Override
					public void onPageScrolled(int position, float positionOffset,
							int positionOffsetPixels){
					}
					
					@Override
					public void onPageSelected(int position){
						mPosition=position;
						if(mCardList.size()>0){
							CardManager cardManager=mCardList.get(mPosition);
							//剩余还款
							txvLastOrderRepay.setText(String.valueOf(cardManager.lastOrderRepay));
							//剩余还款
							txvLastOrderRepayY.setText(String.valueOf(cardManager.lastOrderRepayY));
							//剩余还款
							txvLastModel.setText("空卡周转");
						}
					}
					
					@Override
					public void onPageScrollStateChanged(int state){
					}
				});
				if(mCardList.size()==1){
					banner.setIndicatorVisible(false);
					banner.setCanLoop(false);
				}else{
					banner.setIndicatorVisible(true);
					banner.setCanLoop(true);
				}
				//banner.setIndicatorAlign(MZBannerView.IndicatorAlign.LEFT);
				banner.setPages(mCardList, mPosition, new MZHolderCreator<BannerViewHolder>()
				{
					@Override
					public BannerViewHolder createViewHolder(){
						return new BannerViewHolder();
					}
				});
				if(mCardList.size()==1){
					banner.pause();
				}else{
					banner.start();
				}
				//banner.pause();
			}else{
				banner.setVisibility(View.GONE);
				cardBanner.setVisibility(View.GONE);
				ProjectNoDataShowUtils.getInstance()
						.projectDefaultNoData(llNoData,
								ProjectNoDataShowUtils.ProjectDataTypeMode.MESSAGE, "暂未添加信用卡~");
			}
		}
	}
	
	@Keep
	class H2 extends H
	{
		@BindView(R2.id.rv_consumption)
		KmRecyclerView rvConsumption;
		@BindView(R2.id.ll_no_data)
		ProjectNoDataLayout llNoData;
		
		public H2(){
			super(R.layout.item_credit_banner_h2);
			ButterKnife.bind(this, itemView);
			//暂时先显示无数据情况
			ProjectNoDataShowUtils.getInstance()
					.projectDefaultNoData(llNoData,
							ProjectNoDataShowUtils.ProjectDataTypeMode.MESSAGE, "暂无消费记录~");
		}
		
		@Override
		void update(int type){
			super.update(type);
		}
	}
	
	public class BannerViewHolder implements MZViewHolder<CardManager>
	{
		private TextView txvCardNum;
		private TextView txvCardLimit;
		private TextView txvCardBill;
		private TextView txvCardHaiRi;
		private ImageView ivCardBg;
		private ImageView ivBankDelete;
		//名称
		private TextView txvCardName;
		//卡的模式
		private TextView txvCardModel;
		private TextView txvStatusLang;
		private CardView mCardView;
		
		@Override
		public View createView(Context context){
			// 返回页面布局文件
			View view=LayoutInflater.from(context).inflate(R.layout.item_banner_layout, null);
			txvCardNum=(TextView)view.findViewById(R.id.txv_card_num);
			txvCardLimit=(TextView)view.findViewById(R.id.txv_card_limit);
			txvCardBill=(TextView)view.findViewById(R.id.txv_card_bill);
			txvCardHaiRi=(TextView)view.findViewById(R.id.txv_card_hai_ri);
			ivCardBg=(ImageView)view.findViewById(R.id.iv_card_bg);
			ivBankDelete=(ImageView)view.findViewById(R.id.iv_bank_delete);
			txvCardName=view.findViewById(R.id.txv_card_name);
			txvCardModel=view.findViewById(R.id.txv_card_model);
			txvStatusLang=view.findViewById(R.id.txv_status_lang);
			mCardView=view.findViewById(R.id.crad_status_lang);
			return view;
		}
		
		@Override
		public void onBind(final Context context, int position, final CardManager item){
			//用户名称
			if(item.name.length()==2){
				String cardName=item.name.substring(0, 1);
				txvCardName.setText(cardName+"*");
			}else if(item.name.length()>2){
				String cardFirst=item.name.substring(0, 1);
				String cardLast=item.name.substring(item.name.length()-1, item.name.length());
				StringBuilder sp=new StringBuilder();
				sp.append(cardFirst);
				int postion=item.name.length()-2;
				for(int i=0; i<postion; i++){ sp.append("*"); }
				sp.append(cardLast);
				txvCardName.setText(sp.toString());
			}
			//设置状态
			if(null!=item.lastOrder){
				if(TextUtils.isEmpty(item.lastOrder.statusLang)){
					mCardView.setVisibility(View.GONE);
				}else{
					mCardView.setVisibility(View.VISIBLE);
					if(!TextUtils.isEmpty(item.lastOrder.statusLang))
					txvStatusLang.setText(item.lastOrder.statusLang);
				}
				//卡的模式
				if(item.lastOrder.type==2)
					txvCardModel.setText("空卡周转");
				else txvCardModel.setText("智能代还");
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
			txvCardNum.setText(sp.toString());
			//额度
			txvCardLimit.setText(String.valueOf(item.act));
			//账单日
			txvCardBill.setText(String.valueOf(item.billDate)+"日");
			//还款日
			txvCardHaiRi.setText(String.valueOf(item.repayDate)+"日");
			//设置背景
			ivCardBg.setVisibility(View.VISIBLE);
			if(null!=item.bank) if(!TextUtils.isEmpty(item.bank.background))
				Glide.with(context).load(item.bank.background).into(ivCardBg);
			ivBankDelete.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					//弹窗对话框
					CardDeleteDialog dialog=
							new CardDeleteDialog(ActCreditCardManagerBanner.this, item,
									new CardDeleteDialog.SelectCardDeleteListenter()
									{
										@Override
										public void getCode(String phone, TextView txvGetCode){
											getNewCode(phone, txvGetCode, 1);
										}
										
										@Override
										public void getVideoCode(String phone, TextView txvGetCode){
											getNewVideoCode(phone, txvGetCode, 1);
										}
										
										@Override
										public void deleteCardInfo(String id, String code){
											deleteNewCardInfo(id, code);
										}
									});
					dialog.show();
				}
			});
		}
	}
	
	private void initDateParams(final int id, final int repay, final int bill){
		for(int i=0; i<31; i++){
			int num=i+1;
			CardRepayOrbill item=new CardRepayOrbill();
			item.name="每月"+num+"日";
			if(i==repay-1) item.type="1";
			else item.type="0";
			item.numDate=num;
			mCardRePayList.add(item);
		}
		for(int i=0; i<31; i++){
			int num=i+1;
			CardRepayOrbill item=new CardRepayOrbill();
			item.name="每月"+num+"日";
			if(i==bill-1) item.type="1";
			else item.type="0";
			item.numDate=num;
			mCardBillList.add(item);
		}
		//弹窗
		new CardSetDatePop(this, mCardRePayList, mCardBillList, repay, bill,
				new CardSetDatePop.CommonSetListenter()
				{
					@Override
					public void selectDataOk(int mrepay, int mbill){
						//数据没有改变
						if(repay!=mrepay)
							//执行请求
							setUpdateCardDate(String.valueOf(id), "repay_date", mrepay);
						if(bill!=mbill)
							//执行请求
							setUpdateCardDate(String.valueOf(id), "bill_date", mbill);
					}
				});
	}
	
	private void setUpdateCardDate(String id, String type, int vall){
		Servers.start(this, Server.get().updateDateInfo(id, type, String.valueOf(vall)),
				new RxListener<M>()
				{
					@Override
					public void onNext(M m, String msg){
						getProxy().dismissLoading();
						if(m==null) return;
						if(m!=null) if(m.code==1){
							if(mCardList.size()>0){
								mCardList.clear();
							}
							loadData();
						}else toast(m.msg);
					}
				});
	}
	
	//正常短信
	private CheckCodeTimer countDownTimer;
	
	public void getNewCode(String phone, final TextView txvGetCode, int type){
		//重新获取验证码倒计时
		countDownTimer=(CheckCodeTimer)new CheckCodeTimer(txvGetCode, type).start();
		Servers.start(this, Server.get().getCardCode(phone), new RxListener<M>()
		{
			@Override
			public void onNext(M m, String msg){
				if(m==null) return;
				if(m!=null){
					if(m.code==1){
						toast("发送成功");
					}else toast(m.msg);
				}
			}
		});
	}
	
	//语音
	public void getNewVideoCode(String phone, final TextView txvGetCode, int type){
		//重新获取验证码倒计时
		countDownTimer=(CheckCodeTimer)new CheckCodeTimer(txvGetCode, type).start();
		Servers.start(this, Server.get().getCardVideoCode(phone), new RxListener<M>()
		{
			@Override
			public void onNext(M m, String msg){
				if(m==null) return;
				if(m!=null){
					if(m.code==1){
						//toast("发送成功");
						txvGetCode.setText("语音验证码");
					}else toast(m.msg);
				}
			}
		});
	}
	
	//删除
	public void deleteNewCardInfo(String id, String code){
		Servers.start(this, Server.get().deleteCardInfo(id, code), new RxListener<M>()
		{
			@Override
			public void onNext(M m, String msg){
				if(m==null) return;
				if(m!=null){
					if(m.code==1){
						toast("删除成功");
						//mCardList
						//loadData();
						mCardList.remove(mCardList.get(mPosition));
						rvBanner.getAdapter().notifyDataSetChanged();
					}else toast(m.msg);
				}
			}
		});
	}
	
	private void selectCardDialog(){
		CardSelectDialog cardSelectDialog=
				new CardSelectDialog(this, new CardSelectDialog.SelectCardListenter()
				{
					@Override
					public void selectCardOk(String type){
						if(type.equals("1")){
							final CardManager cardManager=mCardList.get(mPosition);
							//toast("空卡代还暂未开放");
							Servers.start(ActCreditCardManagerBanner.this, Server.get()
											.getCheckSourceStatus("E", String.valueOf(cardManager.id)),
									new RxListener<M>()
									{
										@Override
										public void onNext(M m, String msg){
											if(m==null) return;
											if(m!=null){
												if(m.code==1){
													//已绑定
													new CardClauseDialog(
															ActCreditCardManagerBanner.this, new CardClauseDialog.CardClauseListenter(){
														@Override
														public void submitSuccess(){
															//设置还款计划
															Intent intent=new Intent(ActCreditCardManagerBanner.this,ActSetHaimoneyPlan.class);
															intent.putExtra("id",String.valueOf(cardManager.id));
															startActivity(intent);
														}
													}).show();
												}else toast(m.msg);
											}
										}
									});
						}else{
							toast("即将开放");
							//Intent intent=new Intent(ActCreditCardManager.this, ActWeb.class);
							//intent.putExtra(ActWeb.KEY_URL,
							//		Server.BASE_WEB+"steward/addCreditCard?type=intelligent");
							//startActivity(intent);
							//getProxy().startActivity(ActAddCardFlow.class);
						}
					}
				});
		cardSelectDialog.show();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(null!=countDownTimer){
			countDownTimer.cancel();
		}
	}
}
