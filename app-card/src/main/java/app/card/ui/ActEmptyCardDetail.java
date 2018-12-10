package app.card.ui;

import android.os.Bundle;
import app.card.R;
import app.card.base.BaseTitleActivity;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 空卡预约认证
 * @date: 2018/11/20 0020  
 * @time: 上午 11:05
 */
public class ActEmptyCardDetail extends BaseTitleActivity
{
	@Override
	protected int getLayoutId(){
		return R.layout.act_empty_card_detail;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle("空卡预约认证");
	}
}
