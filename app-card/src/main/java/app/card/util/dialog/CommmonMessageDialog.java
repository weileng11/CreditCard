package app.card.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.card.R;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util.dialog
 * @description: 公用的对话框
 * @date: 2018/11/22 0022  
 * @time: 上午 11:42
 */
public class CommmonMessageDialog extends Dialog
{
	private Activity mActivity;
	public CommonMessageListenter selectListenter;
	private TextView txvTitle;
	private TextView txvcontent;
	private TextView txvcancel, txvsubmit;
	private CardView mCardViewCancel, mCardViewOk;
	private String title;
	private String content;
	private String positiveName, negativeName;
	//单个
	private String positiveOneName;
	private TextView txvOneOk;
	
	private LinearLayout llOne,llTwo;
	//isShowStatus false 单 true 双
	private boolean isShowStatus;
	private CardView cardCommitone;
	
	public interface CommonMessageListenter
	{
		void submitSuccess();
	}
	
	public CommmonMessageDialog(Activity activity,boolean isShowStatus, CommonMessageListenter selectListenter){
		super(activity);
		this.mActivity=activity;
		this.isShowStatus=isShowStatus;
		this.selectListenter=selectListenter;
		// 设置是否点击外围可解散
		setCanceledOnTouchOutside(true);
	}
	
	public CommmonMessageDialog setContent(String content){
		this.content=content;
		return this;
	}
	
	public CommmonMessageDialog setTitle(String title){
		this.title=title;
		return this;
	}
	
	public CommmonMessageDialog setPositiveButton(String name){
		this.positiveName=name;
		return this;
	}
	
	public CommmonMessageDialog setNegativeButton(String name){
		this.negativeName=name;
		return this;
	}
	
	//单个
	public CommmonMessageDialog setOneButton(String name){
		this.positiveOneName=name;
		return this;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		View view=getLayoutInflater().inflate(R.layout.dialog_message, null);
		setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		Window window=getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
		window.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams wl=window.getAttributes();
		
		// 将对话框的大小按屏幕大小的百分比设置
		WindowManager windowManager = mActivity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width=(int)(display.getWidth() * 0.9); //设置宽度
		wl.height=ViewGroup.LayoutParams.WRAP_CONTENT;
		window.setAttributes(wl);
		// 设置显示位置
		//onWindowAttributesChanged(wl);
		initViews();
	}
	
	//初始化布局
	private void initViews(){
		txvTitle=findViewById(R.id.tv_title);
		txvcontent=findViewById(R.id.content);
		txvcancel=findViewById(R.id.txv_cancel);
		txvsubmit=findViewById(R.id.txv_ok);
		
		llOne=findViewById(R.id.ll_one); //单个
		llTwo=findViewById(R.id.ll_two);//双个
		
		//双
		if(isShowStatus){
			llOne.setVisibility(View.GONE);
			llTwo.setVisibility(View.VISIBLE);
			mCardViewCancel=findViewById(R.id.cancel);
			mCardViewOk=findViewById(R.id.submit);
			mCardViewCancel.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					dismiss();
				}
			});
			mCardViewOk.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v){
					selectListenter.submitSuccess();
					dismiss();
				}
			});
		}else{//单
			llOne.setVisibility(View.VISIBLE);
			llTwo.setVisibility(View.GONE);
			cardCommitone=findViewById(R.id.card_commit_one);
			txvOneOk=findViewById(R.id.txv_one_ok);
			cardCommitone.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v){
					selectListenter.submitSuccess();
					dismiss();
				}
			});
			
			if(!TextUtils.isEmpty(positiveOneName)){
				txvOneOk.setText(positiveOneName);
			}
		}
		
		txvcontent.setText(content);
		if(!TextUtils.isEmpty(positiveName)){
			txvsubmit.setText(positiveName);
		}
		
		if(!TextUtils.isEmpty(negativeName)){
			txvcancel.setText(negativeName);
		}
		
		if(!TextUtils.isEmpty(title)){
			txvTitle.setText(title);
		}
		
	}
}

