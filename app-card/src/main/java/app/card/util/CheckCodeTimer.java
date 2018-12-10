package app.card.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util
 * @description:
 * @date: 2018/11/29 0029  
 * @time: 上午 9:39
 */
public class CheckCodeTimer extends CountDownTimer
{
	TextView tipText;
	int type;
	public CheckCodeTimer(TextView tipText,int type) {
		super(60000, 1000);
		this.tipText = tipText;
		this.type=type;
	}
	
	@Override
	public void onTick(long millisUntilFinished) {
		tipText.setEnabled(false);
		tipText.setText( (millisUntilFinished/1000)+"秒再获取");
	}
	
	@Override
	public void onFinish() {
		tipText.setEnabled(true);
		if(type==0)
		tipText.setText("获取验证码");
		else tipText.setText("语音验证码");
	}
	
}
