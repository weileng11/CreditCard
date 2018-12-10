package app.card.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import app.card.R;
import app.card.R2;
import app.card.base.NameFragment;
import app.card.model.PlanList;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickHolder;
import app.card.ui.ActAwardSuccess;
import app.card.util.dialog.ExtensionDialog;
import app.card.view.KmRecyclerView;
import butterknife.BindView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui.fragment
 * @description:已担保
 * @date: 2018/11/20 0020  
 * @time: 下午 4:27
 */
public class FmGuarantee extends NameFragment
{
	GuaranteeIntentionAdapter mAdapter;
	ArrayList<PlanList> mPlanLists=new ArrayList<>();
	@BindView(R2.id.rv_guarantee)
	KmRecyclerView rvGuarantee;
	
	@Override
	public int getLayout(){
		return R.layout.fm_guarantee;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle bundle){
		super.onViewCreated(view, bundle);
		mAdapter=new GuaranteeIntentionAdapter(R.layout.item_guarantee_head);
		rvGuarantee.setAdapter(mAdapter);
		
		for(int i=0;i<5;i++){
			PlanList planList=new PlanList();
			planList.orderSn="信用卡:"+i;
			mPlanLists.add(planList);
		}
		mAdapter.setNewData(mPlanLists);
	}
	
	class GuaranteeIntentionAdapter extends QuickAdapter<PlanList>
	{
		public GuaranteeIntentionAdapter(int layout){
			super(layout);
		}
		
		@Override
		protected void convert(QuickHolder helper, PlanList item){
			if(item==null) return;
			int position=helper.getAdapterPosition();
			LinearLayout llhead=helper.getView(R.id.ll_head);
			if(position==0){
				llhead.setVisibility(View.VISIBLE);
			}else{
				llhead.setVisibility(View.GONE);
			}
			//名称
			helper.setText(R.id.txv_user_name, item.orderSn);
			//等级
			helper.getView(R.id.iv_user_dj);
			//费率
			helper.setText(R.id.txv_user_fl, item.orderSn);
			//服务费
			helper.setText(R.id.txv_user_fw, item.orderSn);
			//发起修改
			helper.getView(R.id.card_user_sq).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					ExtensionDialog extensionDialog=new ExtensionDialog(getActivity(),
							new ExtensionDialog.ExtensionListenter()
							{
								@Override
								public void extensionSuccessOk(){
									getProxy().startActivity(ActAwardSuccess.class);
								}
							});
					extensionDialog.setSxTitle("修改费用")
							.setSxMoney("111")
							.setSxFl("222")
							.setSxFw("3333")
							.setSxCancel("取消")
							.setSxCommit("确定")
							.show();
				}
			});
		}
	}
}
