package app.card.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import app.card.R;
import app.card.R2;
import app.card.base.BaseTitleActivity;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickHolder;
import app.card.view.KmRecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 设置提交计划
 * @date: 2018/11/21 0021  
 * @time: 上午 11:47
 */
public class ActSetCommitPlan extends BaseTitleActivity
{
	@BindView(R2.id.txv_plan_time)
	TextView txvPlanTime;
	@BindView(R2.id.txv_plan_hai_money)
	TextView txvPlanHaiMoney;
	@BindView(R2.id.txv_plan_num)
	TextView txvPlanNum;
	@BindView(R2.id.txv_plan_sxf)
	TextView txvPlanSxf;
	@BindView(R2.id.rv_commit_plan)
	KmRecyclerView rvCommitPlan;
	@BindView(R2.id.card_commit)
	CardView cardCommit;
	
	public List<String> mStringList = new ArrayList<>();
	CommitPlanAdapter mAdapter;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_set_commit_plan;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("设置提交计划");
		for(int i=0;i<5;i++){
			mStringList.add("参数"+i);
		}
		
		mAdapter=new CommitPlanAdapter(R.layout.item_set_haimoney_plan);
		rvCommitPlan.setAdapter(mAdapter);
		mAdapter.setNewData(mStringList);
	}
	
	@OnClick(R2.id.card_commit)
	public void onViewClicked(){
		
	}
	
	class CommitPlanAdapter extends QuickAdapter<String>
	{
		public CommitPlanAdapter(int layout){
			super(layout);
		}
		
		@Override
		protected void convert(QuickHolder helper, final String item){
			if(item==null) return;
			if(helper.getAdapterPosition()==0)
				helper.getView(R.id.ll_com_head).setVisibility(View.VISIBLE);
			else helper.getView(R.id.ll_com_head).setVisibility(View.GONE);
			//账户状态
			helper.setText(R.id.txv_com_status, item);
			//交易时间
			helper.setText(R.id.txv_com_time, item);
			//交易金额
			helper.setText(R.id.txv_com_je, item);
			//行业类型
			helper.setText(R.id.txv_com_type, item);
			//手续费
			helper.setText(R.id.txv_com_fee, item);
		}
	}
}
