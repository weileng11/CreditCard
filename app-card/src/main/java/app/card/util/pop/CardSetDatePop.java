package app.card.util.pop;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.*;
import android.widget.*;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.List;
import app.card.R;
import app.card.model.CardRepayOrbill;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickHolder;
import app.card.view.KmRecyclerView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util.pop
 * @description:
 * @date: 2018/11/29 0029  
 * @time: 下午 1:22
 */
public class CardSetDatePop
{
	private PopupWindow popupWindow;
	private Activity mActivity;
	private TextView txvCancel, txvOk;
	private TextView txvTitle;
	private KmRecyclerView mRecyclerView;
	private KmRecyclerView mRecyclerView1;
	private CommPopAdapter mCommPopAdapter;
	private CommPopAdapter1 mCommPopAdapter1;
	public CommonSetListenter mHeadFmListenter;
	//还款
	public List<CardRepayOrbill> mRepayList;
	//账单
	public List<CardRepayOrbill> mBillList;
	private int repayDate;
	private int billDate;
	//选中的item
	public CardRepayOrbill selectRepay;
	public CardRepayOrbill selectBill;
	
	public interface CommonSetListenter
	{
		void selectDataOk(int repay,int bill);
	}
	//String repayDate,String billDate,
	public CardSetDatePop(Activity activity, List<CardRepayOrbill> rlist,
			List<CardRepayOrbill> blist,int repayDate,int billDate,
			final CommonSetListenter listener){
		this.mActivity=activity;
		this.mRepayList=rlist;
		this.mBillList=blist;
		this.repayDate=repayDate;
		this.billDate=billDate;
		this.mHeadFmListenter=listener;
		initView();
	}
	
	private void initView(){
		//防止重复按按钮
		if(popupWindow!=null && popupWindow.isShowing()){
			return;
		}
		//设置PopupWindow的View
		View view=LayoutInflater.from(mActivity).inflate(R.layout.pop_repay_and_bill, null);
		popupWindow=new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		//设置背景,这个没什么效果，不添加会报错
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//设置点击弹窗外隐藏自身
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		//设置动画
		popupWindow.setAnimationStyle(R.style.PopupWindow);
		//设置位置
		popupWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
		//Gravity.BOTTOM底部  NO_GRAVITY左上  LEFT高的中间
		//设置消失监听
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
		{
			@Override
			public void onDismiss(){
				setBackgroundAlpha(1f); //消失后设置回透明度
			}
		});
		//设置PopupWindow的View点击事件
		setOnPopupViewClick(view);
		//设置背景色
		setBackgroundAlpha(0.5f);
	}
	
	private void setOnPopupViewClick(View view){
		txvTitle=view.findViewById(R.id.txv_set_title);
		txvCancel=view.findViewById(R.id.txv_cancel);
		txvOk=view.findViewById(R.id.txv_ok);
		mRecyclerView=view.findViewById(R.id.rv_common);
		mRecyclerView1=view.findViewById(R.id.rv_common1);
		//txvTitle.setText(mTitle);
		mCommPopAdapter=new CommPopAdapter(R.layout.pop_item_textview);
		mRecyclerView.setAdapter(mCommPopAdapter);
		mCommPopAdapter.setNewData(mBillList);
		mCommPopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
		{
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position){
				selectBill=mCommPopAdapter.getItem(position);
				for(int i=0; i<mBillList.size(); i++){
					if(position==i) mBillList.get(i).type="1";
					else mBillList.get(i).type="0";
				}
				mCommPopAdapter.notifyDataSetChanged();
			}
		});
		mCommPopAdapter1=new CommPopAdapter1(R.layout.pop_item_textview);
		mRecyclerView1.setAdapter(mCommPopAdapter1);
		mCommPopAdapter1.setNewData(mRepayList);
		mCommPopAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
		{
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position){
				selectRepay=mCommPopAdapter1.getItem(position);
				for(int i=0; i<mRepayList.size(); i++){
					if(position==i) mRepayList.get(i).type="1";
					else mRepayList.get(i).type="0";
				}
				mCommPopAdapter1.notifyDataSetChanged();
			}
		});
		//完成
		txvOk.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				popupWindow.dismiss();
				if(selectRepay==null&&selectBill==null)
					mHeadFmListenter.selectDataOk(repayDate,billDate);
				else if(selectRepay==null&&selectBill!=null)
					mHeadFmListenter.selectDataOk(repayDate,selectBill.numDate);
				else if(selectRepay!=null&&selectBill==null)
				mHeadFmListenter.selectDataOk(selectRepay.numDate,billDate);
				else
				mHeadFmListenter.selectDataOk(selectRepay.numDate,selectBill.numDate);
			}
		});
		//取消
		txvCancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				popupWindow.dismiss();
			}
		});
	}
	
	public void setBackgroundAlpha(float alpha){
		Activity context=this.mActivity;
		WindowManager.LayoutParams lp=context.getWindow().getAttributes();
		lp.alpha=alpha;
		context.getWindow().setAttributes(lp);
	}
	
	class CommPopAdapter extends QuickAdapter<CardRepayOrbill>
	{
		public CommPopAdapter(int layout){
			super(layout);
		}
		
		@Override
		protected void convert(QuickHolder helper, CardRepayOrbill item){
			if(item==null) return;
			helper.setText(R.id.txv_pop_content, item.name);
			LinearLayout llBg=helper.getView(R.id.ll_bg);
			if(item.type.equals("0"))
				llBg.setBackground(ContextCompat.getDrawable(mActivity, R.color.colorWhite));
			else llBg.setBackground(ContextCompat.getDrawable(mActivity, R.color.dialog_bg3));
		}
	}
	
	class CommPopAdapter1 extends QuickAdapter<CardRepayOrbill>
	{
		public CommPopAdapter1(int layout){
			super(layout);
		}
		
		@Override
		protected void convert(QuickHolder helper, CardRepayOrbill item){
			if(item==null) return;
			helper.setText(R.id.txv_pop_content, item.name);
			LinearLayout llBg=helper.getView(R.id.ll_bg);
			if(item.type.equals("0"))
				llBg.setBackground(ContextCompat.getDrawable(mActivity, R.color.colorWhite));
			else llBg.setBackground(ContextCompat.getDrawable(mActivity, R.color.dialog_bg3));
		}
	}
}
