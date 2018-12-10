package app.card.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import app.card.R;
import app.card.base.BaseTitleActivity;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 发起授信成功界面
 * @date: 2018/11/20 0020  
 * @time: 下午 3:46
 */
public class ActAwardSuccess extends BaseTitleActivity
{
	@Override
	protected int getLayoutId(){
		return R.layout.act_award_success;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("提交成功");
	}
}
