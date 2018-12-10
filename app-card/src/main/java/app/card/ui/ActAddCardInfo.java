package app.card.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import java.util.*;
import app.card.R;
import app.card.R2;
import app.card.api.*;
import app.card.base.BaseTitleActivity;
import app.card.model.Bank;
import app.card.model.M;
import app.card.util.CheckCodeTimer;
import app.card.util.KeybordS;
import app.card.util.pop.BankSetPop;
import app.card.util.pop.CommonSetPop;
import app.card.view.VerificationCodeView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 添加信用卡信息
 * @date: 2018/11/20 0020  
 * @time: 下午 6:04
 */
public class ActAddCardInfo extends BaseTitleActivity
{
	@BindView(R2.id.ed_cvn)
	EditText edCvn;
	@BindView(R2.id.ed_indate)
	EditText edIndate;
	@BindView(R2.id.txv_card_type)
	TextView txvCardType;
	@BindView(R2.id.ll_card_type)
	LinearLayout llCardType;
	@BindView(R2.id.txv_card_zdr)
	TextView txvCardZdr;
	@BindView(R2.id.ll_card_zdr)
	LinearLayout llCardZdr;
	@BindView(R2.id.txv_card_hai_money)
	TextView txvCardHaiMoney;
	@BindView(R2.id.ll_card_hai_money)
	LinearLayout llCardHaiMoney;
	@BindView(R2.id.ed_phone)
	EditText edPhone;
	@BindView(R2.id.ed_imgae_code)
	EditText edImgaeCode;
	@BindView(R2.id.iv_imgae_code)
	VerificationCodeView ivImgaeCode;
	@BindView(R2.id.iv_resush_code)
	ImageView ivResushCode;
	@BindView(R2.id.ed_card_code)
	EditText edCardCode;
	@BindView(R2.id.txv_get_code)
	TextView cardGetCode;
	@BindView(R2.id.card_save)
	CardView cardSave;
	private String rgPhone;
	private List<Bank> bankList=new ArrayList<>();
	//账单日
	private List<Item> list=new ArrayList<>();
	//还款日
	private List<Item> mHailist=new ArrayList<>();
	
	public class Item
	{
		public int numDate;
		public String type;
		public String name;
	}
	
	private String userName;
	private String validateStr;
	private String cardNum;
	private String bankId;//银行卡id
	private int billDate=0;
	private int repayDate=0;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_add_card_info;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("添加信用卡");
		initParams();
		load();
	}
	
	private void initParams(){
		userName=getIntent().getStringExtra("name");
		validateStr=getIntent().getStringExtra("validate");
		cardNum=getIntent().getStringExtra("cardnum");
		for(int i=0; i<31; i++){
			int num=i+1;
			Item item=new Item();
			item.name="每月"+num+"日";
			item.type="0";
			item.numDate=num;
			list.add(item);
		}
		for(int i=0; i<31; i++){
			int num=i+1;
			Item item=new Item();
			item.name="每月"+num+"日";
			item.type="0";
			item.numDate=num;
			mHailist.add(item);
		}
	}
	
	void load(){
		getProxy().showLoading("");
		Servers.start(this, Server.get().getBankList(), new RxListener2<Bank.Page>()
		{
			@Override
			public void onNext(M<Bank.Page> m, String msg){
				getProxy().dismissLoading();
				if(m==null) return;
				if(1==m.code){
					if(null!=m.data){
						bankList.addAll(m.data.data);
						if(bankList.size()>0)
							for(int i=0; i<bankList.size(); i++){ bankList.get(i).selectType="0"; }
					}
				}else{
					Bank bank=new Bank();
					bank.bankName="暂无数据";
					bankList.add(bank);
					toast(m.msg);
				}
			}
		});
	}
	
	@OnClick({
			         R2.id.ll_card_type, R2.id.ll_card_zdr, R2.id.ll_card_hai_money,
			         R2.id.iv_imgae_code, R2.id.txv_get_code, R2.id.card_save, R2.id.iv_resush_code
	         })
	public void onViewClicked(View view){
		switch(view.getId()){
		case R2.id.ll_card_type://发卡银行
			if(KeybordS.isSoftInputShow(this)) showPopBank();
			break;
		case R2.id.ll_card_zdr://设置账单日
			if(KeybordS.isSoftInputShow(this)) showPopBill();
			break;
		case R2.id.ll_card_hai_money://还款日
			if(KeybordS.isSoftInputShow(this)) showPopHaiDate();
			break;
		case R2.id.iv_imgae_code://图片code
			//edImgaeCode.setText("");
			//ivImgaeCode.refreshCode();
			break;
		case R2.id.iv_resush_code://刷新
			//edImgaeCode.setText("");
			//ivImgaeCode.refreshCode();
			break;
		case R2.id.txv_get_code://验证码
			if(checkPhoneCode()){
				getCode();
			}
			break;
		case R2.id.card_save://保存
			if(isCheckParams()) saveCardInfo();
			break;
		}
	}
	
	private CheckCodeTimer countDownTimer;
	
	private void getCode(){
		getProxy().showLoading("");
		Servers.start(this, Server.get().getCardCode(rgPhone), new RxListener<M>()
		{
			@Override
			public void onNext(M m, String msg){
				getProxy().dismissLoading();
				if(m==null) return;
				if(m!=null){
					toast("发送成功");
					if(m.code==1)
						//重新获取验证码倒计时
						countDownTimer=(CheckCodeTimer)new CheckCodeTimer(cardGetCode,0).start();
					else toast(m.msg);
				}
			}
		});
	}
	
	//发卡银行
	private void showPopBank(){
		new BankSetPop(this, bankList, "银行类型", new BankSetPop.CommonSetListenter()
		{
			@Override
			public void selectDataOk(Bank content){
				bankId=String.valueOf(content.id);
				txvCardType.setText(content.bankName);
			}
		});
	}
	
	//账单日
	private void showPopBill(){
		new CommonSetPop(this, list, "账单日", new CommonSetPop.CommonSetListenter()
		{
			@Override
			public void selectDataOk(Item content){
				billDate=content.numDate;
				txvCardZdr.setText(content.name);
			}
		});
	}
	
	//还款日
	private void showPopHaiDate(){
		new CommonSetPop(this, mHailist, "还款日", new CommonSetPop.CommonSetListenter()
		{
			@Override
			public void selectDataOk(Item content){
				repayDate=content.numDate;
				txvCardHaiMoney.setText(content.name);
			}
		});
	}
	
	/**
	 校验账号
	 */
	private boolean checkPhoneCode(){
		rgPhone=edPhone.getText().toString();
		if(TextUtils.isEmpty(rgPhone)){
			toast("手机号码不能为空");
			return false;
		}
		return true;
	}
	
	private String indate;
	private String cvn;
	private String cardCode;
	
	private boolean isCheckParams(){
		indate=edIndate.getText().toString();
		cvn=edCvn.getText().toString();
		String cardType=txvCardType.getText().toString();
		String cardBill=txvCardZdr.getText().toString();
		String cardRepay=txvCardHaiMoney.getText().toString();
		rgPhone=edPhone.getText().toString();
		cardCode=edCardCode.getText().toString();
		if(TextUtils.isEmpty(cvn)){
			toast("请输入信用卡背后CVN2后3位数");
			return false;
		}else if(TextUtils.isEmpty(indate)){
			toast("请输入有效期月年");
			return false;
		}else if(TextUtils.isEmpty(cardType)){
			toast("请选择银行类型");
			return false;
		}else if(TextUtils.isEmpty(cardBill)){
			toast("请选择账单日");
			return false;
		}else if(TextUtils.isEmpty(cardRepay)){
			toast("请选择还款日");
			return false;
		}else if(TextUtils.isEmpty(rgPhone)){
			toast("请输入手机号码");
			return false;
		}else if(TextUtils.isEmpty(cardCode)){
			toast("请输入验证码");
			return false;
		}
		return true;
	}
	
	private void saveCardInfo(){
		Map<String, String> map=new HashMap<>();
		map.put("bank_id", bankId); //银行id
		map.put("bill_date", String.valueOf(billDate));//账单日
		map.put("card_code", cardNum);//卡号
		map.put("card_date", indate);//卡的日期
		map.put("cvn2", cvn);//cvn
		map.put("id_number", validateStr);//身份证号
		map.put("name", userName);//用户名称
		map.put("phone", rgPhone); //手机号码
		map.put("repay_date", String.valueOf(repayDate)); //还款日
		map.put("verification_code", cardCode);//验证码
		Servers.start(this, Server.get().saveCardInfo(map), new RxListener<M>()
		{
			@Override
			public void onNext(M m, String msg){
				getProxy().dismissLoading();
				if(m==null) return;
				if(m!=null){
					if(m.code==1){
						toast("添加成功");
						getProxy().startActivity(ActCreditCardManagerBanner.class);
						finish();
					}else toast(m.msg);
				}
			}
		});
		//CommmonMessageDialog dialog=new CommmonMessageDialog(this, true,
		//		new CommmonMessageDialog.CommonMessageListenter()
		//		{
		//			@Override
		//			public void submitSuccess(){
		//			}
		//		});
		//dialog.setContent("恭喜您,您已经成功绑定银行卡，您可以去设置您的信用卡代还")
		//		.setTitle("绑定成功")
		//		.setNegativeButton("取消")
		//		.setPositiveButton("立即前往")
		//		.show();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		if(null!=countDownTimer){
			countDownTimer.cancel();
		}
	}
}
