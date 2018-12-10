package app.card.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.*;
import android.widget.CheckBox;
import android.widget.TextView;
import app.card.R;
import app.card.util.ToastUtils;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util.dialog
 * @description: 卡条款提示
 * @date: 2018/12/3 0003  
 * @time: 上午 11:14
 */
public class CardClauseDialog extends Dialog
{
	private Activity mActivity;
	public CardClauseListenter selectListenter;
	private CheckBox mCheckBox;
	private TextView txvcontent;
	private TextView txvcancel, txvsubmit;
	
	public interface CardClauseListenter
	{
		void submitSuccess();
	}
	
	public CardClauseDialog(Activity activity, CardClauseListenter selectListenter){
		super(activity);
		this.mActivity=activity;
		this.selectListenter=selectListenter;
		// 设置是否点击外围可解散
		setCanceledOnTouchOutside(false);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		View view=getLayoutInflater().inflate(R.layout.dialog_clause_message, null);
		setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		Window window=getWindow();
		// 设置显示动画
		//window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
		window.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams wl=window.getAttributes();
		// 将对话框的大小按屏幕大小的百分比设置
		WindowManager windowManager=mActivity.getWindowManager();
		Display display=windowManager.getDefaultDisplay();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width=(int)(display.getWidth()*0.95); //设置宽度
		wl.height=ViewGroup.LayoutParams.WRAP_CONTENT;
		window.setAttributes(wl);
		// 设置显示位置
		//onWindowAttributesChanged(wl);
		initViews();
	}
	
	//初始化布局
	private void initViews(){
		txvcontent=findViewById(R.id.content);
		txvcancel=findViewById(R.id.txv_cancel);
		txvsubmit=findViewById(R.id.txv_ok);
		mCheckBox=findViewById(R.id.cb_card);
		txvcancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				dismiss();
			}
		});
		txvsubmit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				if(mCheckBox.isChecked()){
					selectListenter.submitSuccess();
					dismiss();
				}else ToastUtils.showToastLong(mActivity, "请认真阅读以上条款");
			}
		});
	}
}
