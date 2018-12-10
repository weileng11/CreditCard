package app.card.util.pop;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.*;
import android.widget.*;
import com.chad.library.adapter.base.BaseQuickAdapter;
import java.util.List;
import app.card.R;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickHolder;
import app.card.ui.ActAddCardInfo;
import app.card.util.ToastUtils;
import app.card.view.KmRecyclerView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util.pop
 * @description: 底部pop
 * @date: 2018/11/22 0022  
 * @time: 下午 8:27
 */
public class CommonSetPop
{
	private PopupWindow popupWindow;
	private Activity mActivity;
	private TextView txvCancel, txvOk;
	private TextView txvTitle;
	private KmRecyclerView mRecyclerView;
	private CommPopAdapter mCommPopAdapter;
	public CommonSetListenter mHeadFmListenter;
	public List<ActAddCardInfo.Item> mlist;
	public String mTitle;
	//选中的item
	public ActAddCardInfo.Item selectStr;
	
	public interface CommonSetListenter
	{
		void selectDataOk(ActAddCardInfo.Item content);
	}
	
	public CommonSetPop(Activity activity, List<ActAddCardInfo.Item> list, String title,
			final CommonSetListenter listener){
		this.mActivity=activity;
		this.mlist=list;
		this.mTitle=title;
		this.mHeadFmListenter=listener;
		initView();
	}
	
	private void initView(){
		//防止重复按按钮
		if(popupWindow!=null && popupWindow.isShowing()){
			return;
		}
		//设置PopupWindow的View
		View view=LayoutInflater.from(mActivity).inflate(R.layout.pop_repay_select_address, null);
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
		txvTitle.setText(mTitle);
		mCommPopAdapter=new CommPopAdapter(R.layout.pop_item_textview);
		mRecyclerView.setAdapter(mCommPopAdapter);
		mCommPopAdapter.setNewData(mlist);
		mCommPopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
		{
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position){
				selectStr=mCommPopAdapter.getItem(position);
				for(int i=0; i<mlist.size(); i++){
					if(position==i) mlist.get(i).type="1";
					else mlist.get(i).type="0";
				}
				mCommPopAdapter.notifyDataSetChanged();
			}
		});
		//完成
		txvOk.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				if(null==selectStr){
					if(mTitle.equals("账单日")) ToastUtils.showToastLong(mActivity, "请选择账单日");
					else ToastUtils.showToastLong(mActivity, "请选择还款日");
				}else{
					popupWindow.dismiss();
					mHeadFmListenter.selectDataOk(selectStr);
				}
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
	
	class CommPopAdapter extends QuickAdapter<ActAddCardInfo.Item>
	{
		public CommPopAdapter(int layout){
			super(layout);
		}
		
		@Override
		protected void convert(QuickHolder helper, ActAddCardInfo.Item item){
			if(item==null) return;
			helper.setText(R.id.txv_pop_content, item.name);
			LinearLayout llBg=helper.getView(R.id.ll_bg);
			if(item.type.equals("0"))
				llBg.setBackground(ContextCompat.getDrawable(mActivity, R.color.colorWhite));
			else llBg.setBackground(ContextCompat.getDrawable(mActivity, R.color.dialog_bg3));
			//LinearLayout llBg=helper.getView(R.id.ll_bg);
			//llBg.setOnClickListener(null);
		}
	}
}
