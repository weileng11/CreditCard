package app.card.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import app.card.R;
import app.card.R2;
import app.card.api.RxListener2;
import app.card.api.Server;
import app.card.api.Servers;
import app.card.base.NameFragment;
import app.card.model.CardInfo;
import app.card.model.M;
import app.card.model.PlanList;
import app.card.nodata.ProjectNoDataLayout;
import app.card.nodata.ProjectNoDataShowUtils;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickHolder;
import app.card.ui.ActPlanDetail;
import app.card.util.DateTimeUtil;
import app.card.util.dialog.CommmonMessageDialog;
import app.card.view.KmRecyclerView;
import app.card.view.KmRefreshLayout;
import app.card.view.KmRefreshListener;
import butterknife.BindView;

public class FmSimpleCard extends NameFragment
{
	@BindView(R2.id.rv_plan_list)
	KmRecyclerView rvPlanList;
	@BindView(R2.id.rl_nodata)
	ProjectNoDataLayout rlNodata;
	@BindView(R2.id.refresh)
	KmRefreshLayout vRefresh;
	private String mTitle;
	private String dialogShow;
	PlanListAdapter mAdapter;
	List<PlanList> mPlanLists=new ArrayList<>();
	private int page=1;
	private int status=0;
	private int lastPage=0;
	
	@Override
	public int getLayout(){
		return R.layout.plan_list;
	}
	
	public static FmSimpleCard getInstance(String title, String dialog){
		FmSimpleCard sf=new FmSimpleCard();
		sf.mTitle=title;
		sf.dialogShow=dialog;
		return sf;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle bundle){
		super.onViewCreated(view, bundle);
		mAdapter=new PlanListAdapter(R.layout.item_plan_list);
		rvPlanList.setAdapter(mAdapter);
		vRefresh.setEnableLoadMore(true);
		vRefresh.setEnableAutoLoadMore(true);
		vRefresh.setOnMultiPurposeListener(new KmRefreshListener()
		{
			@Override
			public void onRefresh(RefreshLayout layout){
				page=1;
				loadData();
			}
			
			@Override
			public void onLoadMore(RefreshLayout layout){
				super.onLoadMore(layout);
				//loadMore(mPage+1);
				page++;
				if(page<=lastPage) loadData();
				else vRefresh.setNoMoreData(true);
				vRefresh.setEnableLoadMoreWhenContentNotFull(false);
			}
		});
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
		{
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position){
				PlanList cardPlan=(PlanList)mAdapter.getItem(position);
				//详情页面
				Intent intent=new Intent(getActivity(), ActPlanDetail.class);
				intent.putExtra("id", String.valueOf(cardPlan.id));
				intent.putExtra("remark", cardPlan.remark);
				startActivity(intent);
			}
		});
		getProxy().showLoading("");
		loadData();
	}
	
	//加载数据
	private void loadData(){
		if(mPlanLists.size()>0) mPlanLists.clear();
		if(!TextUtils.isEmpty(mTitle)){
			//根据标题类型请求
			if(mTitle.equals("全部")) status=0;
			else if(mTitle.equals("等待执行")) status=10;
			else if(mTitle.equals("执行中")) status=20;
			else if(mTitle.equals("失败")) status=30;
			else if(mTitle.equals("成功")) status=40;
			else if(mTitle.equals("审核中")) status=50;
			else if(mTitle.equals("审核失败")) status=60;
		}else{
			return;
		}
		Servers.start(this, Server.get().getPlanList(String.valueOf(page), String.valueOf(status)),
				new RxListener2<PlanList.Page>()
				{
					@Override
					public void onNext(M<PlanList.Page> m){
						super.onNext(m);
						getProxy().dismissLoading();
						if(m==null) return;
						if(1==m.code){
							if(m.data!=null){
								lastPage=m.data.lastPage;
								PlanList.Page page=m.data;
								List<PlanList> list=page.data;
								if(list==null){
									mPlanLists=list;
								}else{
									mPlanLists.addAll(page.data);
								}
								mAdapter.setNewData(mPlanLists);
								//显示对话框
								showPlanMsgDialog();
							}
						}else toast(m.msg);
						if(mPlanLists.size()>0)
							ProjectNoDataShowUtils.getInstance().projectGetDataStatus(rlNodata);
						else ProjectNoDataShowUtils.getInstance()
								.projectDefaultNoData(rlNodata,
										ProjectNoDataShowUtils.ProjectDataTypeMode.MESSAGE,
										"暂无计划~");
					}
					
					@Override
					public void onCompleted(){
						super.onCompleted();
						//getProxy().dismissLoading();
						vRefresh.finishRefresh();
					}
				});
	}
	
	class PlanListAdapter extends QuickAdapter<PlanList>
	{
		public PlanListAdapter(int layout){
			super(layout);
		}
		
		@Override
		protected void convert(final QuickHolder helper, final PlanList item){
			if(item==null) return;
			reqSetBankInfo(String.valueOf(item.cardId), helper);
			//状态1
			TextView txvContentStatus=helper.getView(R.id.txv_content_status);
			TextView txvPlanStatus=helper.getView(R.id.txv_plan_status);
			ImageView ivPlanStatus=helper.getView(R.id.iv_plan_status);
			////原因
			//if(!TextUtils.isEmpty(item.remark)) txvContentStatus.setVisibility(View.VISIBLE);
			//else txvContentStatus.setVisibility(View.GONE);
			CardView cardPause=helper.getView(R.id.card_pause);
			cardPause.setVisibility(View.VISIBLE);
			//状态
			txvPlanStatus.setText(item.statusLang);
			if(item.status==10){//等待
				txvContentStatus.setTextColor(
						ContextCompat.getColor(mContext, R.color.color_pub_tv));
				txvContentStatus.setText("等待原因:"+item.remark);
				txvPlanStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_pub_tv));
				ivPlanStatus.setBackground(
						ContextCompat.getDrawable(mContext, R.mipmap.card_plan_dengdai));
				//是否显示原因
				txvContentStatus.setVisibility(View.GONE);
			}else if(item.status==20){ //执行
				txvContentStatus.setTextColor(
						ContextCompat.getColor(mContext, R.color.color_card_ok));
				txvContentStatus.setText("执行原因:"+item.remark);
				txvPlanStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_pub_tv));
				ivPlanStatus.setBackground(
						ContextCompat.getDrawable(mContext, R.mipmap.card_plan_zhixingzhong));
				//是否显示原因
				txvContentStatus.setVisibility(View.GONE);
			}else if(item.status==30){ //失败
				txvContentStatus.setTextColor(ContextCompat.getColor(mContext, R.color.empty_red));
				txvContentStatus.setText("失败原因:"+item.remark);
				txvPlanStatus.setTextColor(ContextCompat.getColor(mContext, R.color.empty_red));
				ivPlanStatus.setBackground(
						ContextCompat.getDrawable(mContext, R.mipmap.card_plan_fail));
				//隐藏取消
				cardPause.setVisibility(View.GONE);
				//是否显示原因
				txvContentStatus.setVisibility(View.VISIBLE);
			}else if(item.status==40){ //成功
				txvContentStatus.setTextColor(
						ContextCompat.getColor(mContext, R.color.empty_hai_success_tv));
				txvContentStatus.setText("成功原因:"+item.remark);
				txvPlanStatus.setTextColor(
						ContextCompat.getColor(mContext, R.color.empty_hai_success_tv));
				ivPlanStatus.setBackground(
						ContextCompat.getDrawable(mContext, R.mipmap.card_plan_success));
				//是否显示原因
				txvContentStatus.setVisibility(View.GONE);
			}else if(item.status==50){ //检查
				txvContentStatus.setTextColor(
						ContextCompat.getColor(mContext, R.color.color_pub_tv1));
				txvPlanStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_pub_tv1));
				ivPlanStatus.setBackground(
						ContextCompat.getDrawable(mContext, R.mipmap.iv_plan_check));
				//是否显示原因
				txvContentStatus.setVisibility(View.GONE);
			}
			//订单号
			helper.setText(R.id.txv_plan_order, "订单号:"+item.orderSn);
			//卡号
			helper.setText(R.id.txv_plan_card, "卡号:"+item.cardCode);
			//消费金额
			helper.setText(R.id.txv_plan_xfmoney, "¥"+item.totalFee);
			//还款金额
			helper.setText(R.id.txv_plan_haimoney, "¥"+item.moneyWithdraw);
			//手续费
			int num=Double.valueOf(item.fee).intValue();//转换为Int类型
			if(num==0){
				int dFeewith=Double.valueOf(item.moneyWithdraw).intValue();
				double feeFresult=dFeewith*0.012;
				DecimalFormat df=new DecimalFormat("#.##");
				double d=feeFresult;
				helper.setText(R.id.txv_plan_sxmoney, "¥"+df.format(d));
			}else{
				helper.setText(R.id.txv_plan_sxmoney, "¥"+String.valueOf(item.fee));
			}
			//helper.setText(R.id.txv_plan_sxmoney, "¥"+item.fee);
			//消费笔数
			helper.setText(R.id.txv_plan_xfbishu, String.valueOf(item.payNum)+"笔");
			//暂停
			helper.getView(R.id.card_pause).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					selectCardDialog(String.valueOf(item.id), item.orderSn);
				}
			});
			//时间
			long time=item.addTime*1000L;
			helper.setText(R.id.txv_plan_time, DateTimeUtil.formatDateTime(time));
		}
	}
	
	private void selectCardDialog(final String orderId, String orderSn){
		final CommmonMessageDialog commmonMessageDialog=
				new CommmonMessageDialog(getActivity(), true,
						new CommmonMessageDialog.CommonMessageListenter()
						{
							@Override
							public void submitSuccess(){
								//暂停订单
								Servers.start(FmSimpleCard.this,
										Server.get().planOrderPause(orderId), new RxListener2()
										{
											@Override
											public void onNext(M m, String msg){
												super.onNext(m, msg);
												if(m==null) return;
												if(m!=null){
													if(m.code==1){
														toast("操作成功");
														//重新加载数据
														loadData();
													}else toast(m.msg);
												}else toast(msg);
											}
										});
							}
						});
		commmonMessageDialog.setTitle("暂停提示")
				.setContent("是否确定暂停订单号,"+orderSn+"的还款计划继续执行")
				.setNegativeButton("取消")
				.setPositiveButton("确定")
				.show();
	}
	
	public void reqSetBankInfo(String cardId, final QuickHolder helper){
		////根据卡的id，查询对应的银行卡信息
		Servers.start(this, Server.get().getCardBankInfo(cardId), new RxListener2<CardInfo>()
		{
			@Override
			public void onNext(M<CardInfo> m, String msg){
				if(m==null) return;
				if(null!=m){
					if(1==m.code){
						//h.setImage(); 银行卡头像
						Log.i("info", m.data.bank.thumb);
						helper.setImage(R.id.iv_plan_head, m.data.bank.thumb);
					}else{
						toast(m.msg);
					}
				}
			}
		});
	}
	
	private void showPlanMsgDialog(){
		if(dialogShow.equals("yes")){
			PlanList item=mPlanLists.get(0);
			//手续费
			int num=Double.valueOf(item.fee).intValue();//转换为Int类型
			String withdraw;
			if(num==0){
				int dFeewith=Double.valueOf(item.moneyWithdraw).intValue();
				double feeFresult=dFeewith*0.012;
				DecimalFormat df=new DecimalFormat("#.##");
				double d=feeFresult;
				withdraw=df.format(d);
			}else{
				withdraw=item.fee;
			}
			new CommmonMessageDialog(getProxy().getActivity(), false,
					new CommmonMessageDialog.CommonMessageListenter()
					{
						@Override
						public void submitSuccess(){
						}
					}).setTitle("温馨提示")
					.setContent(
							"尊敬的用户，为了空卡周转计划能顺利的进行，请您确保您尾号为"+item.cardCode+
							"的招商银行信用卡里有"+withdraw+"元手续费，\n"+
							"          否则您的计划将无法正常运行！")
					.setOneButton("确定").show();
			//弹出后修改状态
			dialogShow="no";
		}
	}
}