package app.card.util.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import app.card.R;
import app.card.model.CardManager;
import app.card.util.ToastUtils;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util.dialog
 * @description:
 * @date: 2018/11/29 0029  
 * @time: 下午 4:19
 */
public class CardDeleteDialog extends Dialog
{
	private Activity mActivity;
	private ImageView ivBankHead;
	private TextView txvBankName;
	private TextView txvBankContent;
	private EditText edBankCode;
	private TextView txvGetCode;
	//语音
	private TextView txvBankVideo;
	private TextView txvBankCancel, txvBankOk;
	private CardManager mCardManager;
	//0 短信 1.语音
	public static int type=0;
	public SelectCardDeleteListenter selectListenter;
	
	public interface SelectCardDeleteListenter
	{
		void getCode(String phone, TextView txvGetCode);
		void getVideoCode(String phone, TextView txvGetCode);
		void deleteCardInfo(String id, String code);
	}
	
	public CardDeleteDialog(Activity activity, CardManager cardManager,
			SelectCardDeleteListenter selectListenter){
		super(activity);
		this.mActivity=activity;
		this.selectListenter=selectListenter;
		this.mCardManager=cardManager;
		// 设置是否点击外围可解散
		setCanceledOnTouchOutside(true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		View view=getLayoutInflater().inflate(R.layout.dialog_card_delete, null);
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
		wl.width=(int)(display.getWidth()*0.9); //设置宽度
		wl.height=ViewGroup.LayoutParams.WRAP_CONTENT;
		window.setAttributes(wl);
		// 设置显示位置
		//onWindowAttributesChanged(wl);
		initViews();
	}
	
	//初始化布局
	private void initViews(){
		txvBankName=findViewById(R.id.txv_bank_name);
		ivBankHead=findViewById(R.id.iv_bank_head);
		txvBankContent=findViewById(R.id.txv_bank_content);
		edBankCode=findViewById(R.id.ed_bank_code);
		txvGetCode=findViewById(R.id.txv_get_code);
		txvBankVideo=findViewById(R.id.txv_bank_video);
		txvBankCancel=findViewById(R.id.txv_bank_cancel);
		txvBankOk=findViewById(R.id.txv_bank_ok);
		//设置参数
		txvBankName.setText(mCardManager.bank.bankName);
		Glide.with(mActivity).load(mCardManager.bank.thumb).into(ivBankHead);
		String subFourCard=mCardManager.cardCode.substring(mCardManager.cardCode.length()-4,
				mCardManager.cardCode.length());
		txvBankContent.setText("您正在解除尾号 "+subFourCard+" 的中国工商银行信用卡,解除后下次需要重新认证.");
		txvGetCode.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				//语音和正常短信
				if(type==0){
					txvBankVideo.setVisibility(View.VISIBLE);
					type=1;
					selectListenter.getCode(mCardManager.phone, txvGetCode);
				}else{
					txvBankVideo.setVisibility(View.VISIBLE);
					type=1;
					selectListenter.getVideoCode(mCardManager.phone, txvGetCode);
				}
			}
		});
		txvBankCancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				dismiss();
			}
		});
		txvBankOk.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				String code=edBankCode.getText().toString();
				if(TextUtils.isEmpty(code)) ToastUtils.showToastLong(mActivity, "请输入验证码");
				else{
					selectListenter.deleteCardInfo(String.valueOf(mCardManager.id), code);
					dismiss();
				}
			}
		});
	}
}
