package app.card.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.*;
import app.card.R;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util.dialog
 * @description:
 * @date: 2018/11/23 0023  
 * @time: 上午 10:08
 */
public class PersonInsuranceDialog extends Dialog
{
	private Activity mActivity;
	
	public PersonInsuranceDialog(Activity activity){
		super(activity);
		this.mActivity=activity;
		// 设置是否点击外围可解散
		setCanceledOnTouchOutside(true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		View view=getLayoutInflater().inflate(R.layout.dialog_person_insurance, null);
		setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		Window window=getWindow();
		// 设置显示动画
		//window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
		window.setGravity(Gravity.CENTER);
		WindowManager.LayoutParams wl=window.getAttributes();
		// 将对话框的大小按屏幕大小的百分比设置
		WindowManager windowManager = mActivity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width=(int)(display.getWidth() * 0.9); //设置宽度
		wl.height=(int)(display.getHeight() * 0.7); //设置高度
		window.setAttributes(wl);
		// 设置显示位置
		//onWindowAttributesChanged(wl);
	}
	
}
