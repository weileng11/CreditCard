package app.card.ui;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.EditText;
import android.widget.TextView;
import app.card.R;
import app.card.R2;
import app.card.base.BaseTitleActivity;
import butterknife.BindView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 空卡预约认证首页(打赏查询)
 * @date: 2018/11/20 0020  
 * @time: 上午 11:54
 */
public class ActEmptyCardYuAuthentication extends BaseTitleActivity
{
	@BindView(R2.id.txv_empty_price)
	TextView txvEmptyPrice;
	@BindView(R2.id.txv_empty_price_yuan)
	TextView txvEmptyPriceYuan;
	@BindView(R2.id.card_empty_select)
	CardView cardEmptySelect;
	@BindView(R2.id.card_ok_add)
	CardView cardOkAdd;
	@BindView(R2.id.ed_empty_name)
	EditText edEmptyName;
	@BindView(R2.id.ed_empty_phone)
	EditText edEmptyPhone;
	@BindView(R2.id.ed_empty_identity)
	EditText edEmptyIdentity;
	@BindView(R2.id.ed_empty_email)
	EditText edEmptyEmail;
	@BindView(R2.id.txv_empty_bottom_price)
	TextView txvEmptyBottomPrice;
	@BindView(R2.id.textView)
	TextView textView;
	@BindView(R2.id.card_empty_bottom_select)
	CardView cardEmptyBottomSelect;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_empty_card_authentication;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle("空卡预约认证");
	}
}
