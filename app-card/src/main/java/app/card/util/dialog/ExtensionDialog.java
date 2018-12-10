package app.card.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.TextView;
import app.card.R;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util.dialog
 * @description:
 * @date: 2018/11/22 0022  
 * @time: 下午 5:04
 */
public class ExtensionDialog extends Dialog
{
	private Activity mActivity;
	private TextView txvTitle;
	private TextView txvSxMoney, txvSxfl, txvSxfw;
	private TextView txvSxCancel, txvSxCommit;
	public ExtensionListenter selectListenter;
	private String sxMoney, sxFl, sxFW;
	private String cancelStr,commitStr;
	private String title;
	
	public interface ExtensionListenter
	{
		void extensionSuccessOk();
	}
	
	public ExtensionDialog(Activity activity, ExtensionListenter selectListenter){
		super(activity);
		this.mActivity=activity;
		this.selectListenter=selectListenter;
		// 设置是否点击外围可解散
		setCanceledOnTouchOutside(false);
	}
	
	
	public ExtensionDialog setSxMoney(String sxMoney){
		this.sxMoney=sxMoney;
		return this;
	}
	
	public ExtensionDialog setSxFw(String fw){
		this.sxFl=fw;
		return this;
	}
	
	public ExtensionDialog setSxFl(String fl){
		this.sxFW=fl;
		return this;
	}
	
	public ExtensionDialog setSxTitle(String title){
		this.title=title;
		return this;
	}
	
	public ExtensionDialog setSxCancel(String fl){
		this.cancelStr=fl;
		return this;
	}
	
	public ExtensionDialog setSxCommit(String fl){
		this.commitStr=fl;
		return this;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		View view=getLayoutInflater().inflate(R.layout.dialog_empty_card_sx, null);
		//setContentView(view);
		setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		Window window=getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
		window.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams wl=window.getAttributes();
		//wl.x=0;
		//wl.y=mActivity.getWindowManager().getDefaultDisplay().getHeight();
		// 将对话框的大小按屏幕大小的百分比设置
		WindowManager windowManager=mActivity.getWindowManager();
		Display display=windowManager.getDefaultDisplay();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width=(int)(display.getWidth()*0.9); //设置宽度
		wl.height=ViewGroup.LayoutParams.WRAP_CONTENT;
		window.setAttributes(wl);
		// 设置显示位置
		//onWindowAttributesChanged(wl);
		initViews();
	}
	//private  void setDialogWidth(){
	//	// 将对话框的大小按屏幕大小的百分比设置
	//	WindowManager windowManager = mActivity.getWindowManager();
	//	Display display = windowManager.getDefaultDisplay();
	//	WindowManager.LayoutParams lp = this.getWindow().getAttributes();
	//	lp.width = (int)(display.getWidth() * 0.8); //设置宽度
	//	this.getWindow().setAttributes(lp);
	//}
	
	//初始化布局
	private void initViews(){
		txvTitle=findViewById(R.id.txv_sx_title);
		txvSxMoney=findViewById(R.id.txv_sx_money);
		txvSxfl=findViewById(R.id.txv_sx_fl);
		txvSxfw=findViewById(R.id.txv_sx_fw);
		txvSxCancel=findViewById(R.id.txv_sx_cancel);
		txvSxCommit=findViewById(R.id.txv_sx_commit);
		if(!TextUtils.isEmpty(sxMoney)){
			txvSxMoney.setText(sxMoney);
		}
		if(!TextUtils.isEmpty(sxFl)){
			txvSxfl.setText(sxFl);
		}
		if(!TextUtils.isEmpty(sxFW)){
			txvSxfw.setText(sxFW);
		}
		
		if(!TextUtils.isEmpty(title)){
			txvTitle.setText(title);
		}
		if(!TextUtils.isEmpty(cancelStr)){
			txvSxCancel.setText(cancelStr);
		}
		if(!TextUtils.isEmpty(commitStr)){
			txvSxCommit.setText(commitStr);
		}
		txvSxCancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				dismiss();
			}
		});
		txvSxCommit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				selectListenter.extensionSuccessOk();
				dismiss();
			}
		});
	}
}
