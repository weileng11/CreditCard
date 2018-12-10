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
import android.widget.TextView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import app.card.R;
import app.card.R2;
import app.card.api.RxListener2;
import app.card.api.Server;
import app.card.api.Servers;
import app.card.base.BaseTitleActivity;
import app.card.model.CardInfo;
import app.card.model.M;
import app.card.model.PlanDetail;
import app.card.nodata.ProjectNoDataLayout;
import app.card.nodata.ProjectNoDataShowUtils;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickHolder;
import app.card.util.DateTimeUtil;
import app.card.view.KmRecyclerView;
import app.card.view.KmRefreshLayout;
import app.card.view.KmRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 计划详情
 * @date: 2018/11/20 0020  
 * @time: 上午 10:47
 */
public class ActPlanDetail extends BaseTitleActivity
{
	@BindView(R2.id.rv_plan)
	KmRecyclerView rvPlan;
	@BindView(R2.id.refresh)
	KmRefreshLayout vRefresh;
	@BindView(R2.id.card_plan_reset)
	CardView cardPlanReset;
	private int page=1;
	private int lastPage=0;
	private String id;
	private String cardId;
	private String remark;
	PlanListDlAdapter mAdapter;
	List<PlanDetail.DataBean> mPlanDetails=new ArrayList<>();;
	final SparseArray<List> mDatas=new SparseArray<>();
	PlanDetail.ExtendBean mExtendBean;
	//默认不执行没有数据
	private String mType="0";
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_plan_detail;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("计划详情");
		id=getIntent().getStringExtra("id");
		remark=getIntent().getStringExtra("remark");
		cardId=getIntent().getStringExtra("cardId");
		initParams();
	}
	
	private void initParams(){
		cardPlanReset.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				againSetPlan();
			}
		});
		mType="0";
		rvPlan.setAdapter(new Adapter());
		//vRefresh.setEnableLoadMore(true);
		//vRefresh.setEnableAutoLoadMore(true);
		vRefresh.setOnMultiPurposeListener(new KmRefreshListener()
		{
			@Override
			public void onRefresh(RefreshLayout layout){
				page=1;
				//if(mPlanLists.size()>0) mPlanLists.clear();
				loadData();
			}
			
		});
		getProxy().showLoading("");
		loadData();
	}
	
	private void loadData(){
		Servers.start(this, Server.get().getPlanDetail(id, String.valueOf(page)),
				new RxListener2<PlanDetail>()
				{
					@Override
					public void onNext(M<PlanDetail> m, String msg){
						getProxy().dismissLoading();
						if(m==null) return;
						if(null!=m){
							if(1==m.code){
								if(m.data!=null){
									lastPage=m.data.lastPage;
									mExtendBean=m.data.extend;
									List<PlanDetail.DataBean> list=m.data.data;
									if(list==null){
										mPlanDetails=list;
									}else{
										mPlanDetails.addAll(list);
									}
									updateData();
								}
							}else{
								toast(m.msg);
							}
						}
					}
				});
	}
	
	private void updateData(){
		mType="1";
		rvPlan.getAdapter().notifyDataSetChanged();
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
						.getConstructor(ActPlanDetail.class)
						.newInstance(ActPlanDetail.this);
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
	
	class PlanListDlAdapter extends QuickAdapter<PlanDetail.DataBean>
	{
		public PlanListDlAdapter(int layout){
			super(layout);
		}
		
		@Override
		protected void convert(final QuickHolder helper, final PlanDetail.DataBean item){
			if(item==null) return;
			//订单号
			helper.setText(R.id.txv_plan_dl_order, "订单号:"+item.orderSn);
			//前3个显示试刷金题
			if(helper.getAdapterPosition()<3)
				helper.getView(R.id.txv_plan_dl_shua).setVisibility(View.VISIBLE);
			else helper.getView(R.id.txv_plan_dl_shua).setVisibility(View.GONE);
			//加或- //类型
			TextView txvPlanNum=helper.getView(R.id.txv_plan_dl_num);
			if(item.orderType==1 || item.orderType==4 || item.orderType==5 ||
			   item.orderType==6){ //-
				txvPlanNum.setText("-");
				txvPlanNum.setTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
				helper.setText(R.id.txv_plan_dl_type, "消费账单");
			}else{//+
				txvPlanNum.setText("+");
				txvPlanNum.setTextColor(ContextCompat.getColor(mContext, R.color.grayColor_main));
				helper.setText(R.id.txv_plan_dl_type, "还款账单");
			}
			//金额
			helper.setText(R.id.txv_plan_dl_money, "¥:"+item.totalFee);
			//根据cardId查询银行信息
			Servers.start(ActPlanDetail.this,
					Server.get().getCardBankInfo(String.valueOf(item.cardId)),
					new RxListener2<CardInfo>()
					{
						@Override
						public void onNext(M<CardInfo> m, String msg){
							if(m==null) return;
							if(null!=m){
								if(1==m.code){
									//银行头像
									//银行名称和银行卡号
									Log.i("info", m.data.bank.thumb);
									helper.setImage(R.id.iv_plan_head, m.data.bank.thumb);
									String cardCode=
											m.data.cardCode.substring(m.data.cardCode.length()-4,
													m.data.cardCode.length());
									//具体的银行
									helper.setText(R.id.txv_plan_dl_bank,
											m.data.bankName+":"+cardCode);
									//持卡者
									if(m.data.name.length()==2){
										String cardName=m.data.name.substring(0, 1);
										helper.setText(R.id.txv_plan_dl_name, cardName+"*");
									}else if(m.data.name.length()>2){
										String cardFirst=m.data.name.substring(0, 1);
										String cardLast=
												m.data.name.substring(m.data.name.length()-1,
														m.data.name.length());
										StringBuilder sp=new StringBuilder();
										sp.append(cardFirst);
										int postion=m.data.name.length()-2;
										for(int i=0; i<postion; i++){ sp.append("*"); }
										sp.append(cardLast);
										helper.setText(R.id.txv_plan_dl_name, sp.toString());
									}
								}else{
								}
							}
						}
					});
			//时间
			long addTime=item.addTime*1000L;
			helper.setText(R.id.txv_plan_dl_time, DateTimeUtil.formatDateTimeDay(addTime));
			//是否显示图标
			//执行状态
			TextView txvPlanStatus=helper.getView(R.id.txv_plan_dl_status);
			ImageView ivPlanDl=helper.getView(R.id.iv_plan_dl);
			if(item.status==10){
				//今天时间
				int today=(int)System.currentTimeMillis();
				int newTody=(int)(today/1000L);
				//服务器返回的时间
				//如果服务器返回的时间小于今天时间
				int planTime=item.planPayTime;
				if(planTime<newTody){
					ivPlanDl.setVisibility(View.GONE);
					//消费
					if(item.orderType==1 || item.orderType==4 || item.orderType==5 ||
					   item.orderType==6){
						txvPlanStatus.setText("即将消费"+"¥:"+item.totalFee);
						txvPlanStatus.setTextColor(
								ContextCompat.getColor(mContext, R.color.color_card_b1));
					}else{//还款
						txvPlanStatus.setText("即将还款"+"¥:"+item.totalFee);
						txvPlanStatus.setTextColor(
								ContextCompat.getColor(mContext, R.color.color_card_b1));
					}
				}else{
					ivPlanDl.setVisibility(View.VISIBLE);
					ivPlanDl.setBackground(
							ContextCompat.getDrawable(mContext, R.mipmap.card_plan_zhixingzhong));
					txvPlanStatus.setText("等待执行");
					txvPlanStatus.setTextColor(
							ContextCompat.getColor(mContext, R.color.color_card_b1));
				}
			}else if(item.status==20){
				ivPlanDl.setVisibility(View.VISIBLE);
				ivPlanDl.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.iv_plan_check));
				txvPlanStatus.setText("执行中...");
				txvPlanStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_card_b1));
			}else if(item.status==30){
				if(!TextUtils.isEmpty(item.failureMsg)){
					ivPlanDl.setVisibility(View.VISIBLE);
					ivPlanDl.setBackground(
							ContextCompat.getDrawable(mContext, R.mipmap.card_plan_fail));
					txvPlanStatus.setText("失败");
					txvPlanStatus.setTextColor(ContextCompat.getColor(mContext, R.color.empty_red));
				}else{
					ivPlanDl.setVisibility(View.GONE);
					if(item.orderType==1 || item.orderType==4 || item.orderType==5 ||
					   item.orderType==6){
						txvPlanStatus.setText("正在执行中");
						txvPlanStatus.setTextColor(
								ContextCompat.getColor(mContext, R.color.color_card_b1));
					}else{
						txvPlanStatus.setText("已在还款中");
						txvPlanStatus.setTextColor(
								ContextCompat.getColor(mContext, R.color.color_card_b1));
					}
				}
			}else if(item.status==40){
				ivPlanDl.setVisibility(View.VISIBLE);
				ivPlanDl.setBackground(
						ContextCompat.getDrawable(mContext, R.mipmap.card_plan_success));
				txvPlanStatus.setText("执行成功");
				txvPlanStatus.setTextColor(
						ContextCompat.getColor(mContext, R.color.empty_hai_success_tv));
			}
		}
	}
	
	@Keep
	class H<T> extends QuickHolder
	{
		QuickAdapter<T> mAdapter;
		
		public H(int layout){
			super(inflate(rvPlan, layout));
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
		@BindView(R2.id.txv_plan_xf_money)
		TextView txvPlanXfMoney;
		@BindView(R2.id.txv_plan_hai_money)
		TextView txvPlanHaiMoney;
		@BindView(R2.id.txv_plan_sx_money)
		TextView txvPlanSxMoney;
		@BindView(R2.id.txv_hai_money)
		TextView txvHaiMoney;
		@BindView(R2.id.txv_height_money)
		TextView txvHeightMoney;
		@BindView(R2.id.txv_plan_fail)
		TextView txvPlanFail;
		
		public H1(){
			super(R.layout.item_plan_dl_h1);
			ButterKnife.bind(this, itemView);
		}
		
		@Override
		void update(int type){
			super.update(type);
			if(!TextUtils.isEmpty(remark)){
				txvPlanFail.setVisibility(View.VISIBLE);
				txvPlanFail.setText("失败原因:"+remark);
			}else txvPlanFail.setVisibility(View.GONE);
			if(mExtendBean!=null){
				if(mExtendBean.pay==0)
					txvPlanXfMoney.setText("¥"+String.valueOf(mExtendBean.pay)+".00");
				else txvPlanXfMoney.setText("¥"+String.valueOf(mExtendBean.pay));
				if(mExtendBean.o==0)
					txvPlanHaiMoney.setText("¥"+String.valueOf(mExtendBean.o)+".00");
				else txvPlanHaiMoney.setText("¥"+String.valueOf(mExtendBean.o));
				if(mExtendBean.repay==0)
					txvPlanSxMoney.setText("¥"+String.valueOf(mExtendBean.repay)+".00");
				else txvPlanSxMoney.setText("¥"+String.valueOf(mExtendBean.repay));
				if(mExtendBean.weekPay==0)
					txvHaiMoney.setText("¥"+String.valueOf(mExtendBean.weekPay)+".00");
				else txvHaiMoney.setText("¥"+String.valueOf(mExtendBean.weekPay));
				txvHeightMoney.setText("¥"+String.valueOf(mExtendBean.withdrawFee));
			}
		}
	}
	
	@Keep
	class H2 extends H
	{
		@BindView(R2.id.rv_plan_dl)
		KmRecyclerView rvPlanDl;
		@BindView(R2.id.ll_no_data)
		ProjectNoDataLayout llNoData;
		
		public H2(){
			super(R.layout.item_plan_dl_h2);
			ButterKnife.bind(this, itemView);
			mAdapter=new PlanListDlAdapter(R.layout.item_plan_detail);
			rvPlanDl.setAdapter(mAdapter);
		}
		
		@Override
		void update(int type){
			super.update(type);
			if(mType.equals("1")){
				if(mPlanDetails.size()>0){
					//隐藏布局
					ProjectNoDataShowUtils.getInstance().projectGetDataStatus(llNoData);
					mAdapter.setNewData(mPlanDetails);
				}else ProjectNoDataShowUtils.getInstance()
						.projectDefaultNoData(llNoData,
								ProjectNoDataShowUtils.ProjectDataTypeMode.MESSAGE, "暂无数据~");
			}
				
		}
	}
	
	private void againSetPlan(){
		//设置还款计划
		Intent intent=new Intent(this,ActSetHaimoneyPlan.class);
		intent.putExtra("id",cardId);
		startActivity(intent);
	}
}
