package app.card.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.*;
import android.widget.CheckBox;
import app.card.R;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util.dialog
 * @description: 信用卡选择框
 * @date: 2018/11/22 0022  
 * @time: 上午 10:25
 */
public class CardSelectDialog extends Dialog
{
	private Activity mActivity;
	private CheckBox ivEmptyHai, ivZhinengHai;
	private CardView cardSelectOk;
	//1.空卡 2.智能
	private String type="1";
	public SelectCardListenter selectListenter;
	
	public interface SelectCardListenter
	{
		void selectCardOk(String type);
	}
	
	public CardSelectDialog(Activity activity, SelectCardListenter selectListenter){
		super(activity);
		this.mActivity=activity;
		this.selectListenter=selectListenter;
		// 设置是否点击外围可解散
		setCanceledOnTouchOutside(true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		View view=getLayoutInflater().inflate(R.layout.dialog_select_mode, null);
		setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		Window window=getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
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
		ivEmptyHai=findViewById(R.id.iv_empty_hai);
		ivZhinengHai=findViewById(R.id.iv_zhineng_hai);
		cardSelectOk=findViewById(R.id.card_select_ok);
		//默认选中空卡
		type="1";
		ivEmptyHai.setChecked(true);
		ivZhinengHai.setChecked(false);
		ivEmptyHai.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				type="1";
				ivEmptyHai.setChecked(true);
				ivZhinengHai.setChecked(false);
			}
		});
		ivZhinengHai.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				type="2";
				ivEmptyHai.setChecked(false);
				ivZhinengHai.setChecked(true);
			}
		});
		cardSelectOk.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				selectListenter.selectCardOk(type);
				dismiss();
			}
		});
		//ivEmptyHai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
		//	@Override
		//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
		//	}
		//});
		//
		//ivZhinengHai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
		//	@Override
		//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
		//
		//	}
		//});
	}
}
