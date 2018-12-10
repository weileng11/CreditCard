package app.card.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import app.card.R;
import app.card.base.BaseTitleActivity;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: km.card
 * @description: 预约还款
 * @date: 2018/11/19 0019  
 * @time: 下午 2:54
 */
public class ActAppointmentRefund extends BaseTitleActivity
{
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.act_appointment_refund);
	}
}
