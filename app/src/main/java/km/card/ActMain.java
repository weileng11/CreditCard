package km.card;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import app.card.ui.ActCreditCardManager;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: km.card
 * @description:
 * @date: 2018/11/19 0019  
 * @time: 下午 3:18
 */
public class ActMain extends AppCompatActivity
{
	
	//8281a38263556b009b21bf593ef910fe506d1470f9fa0e477324640b819af381
	 long mInt =1543905102;
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.act_main);
		long today=System.currentTimeMillis();
		Log.i("info",String.valueOf(today));
		//if(1543905102424>1543464583000)
		/*1543905102424 1543464583000*/
		findViewById(R.id.btn_tz).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(ActMain.this,ActCreditCardManager.class);
				startActivity(intent);
				
				//Snackbar sb = Snackbar.make(v,"潇湘剑雨",Snackbar.LENGTH_LONG);
				//sb.show();
				//
				//Snackbar.make(v,"hahhaaa",Snackbar.LENGTH_LONG).setAction("haha", new View.OnClickListener(){
				//	@Override
				//	public void onClick(View v){
				//		ToastUtils.showToastLong(ActMain.this,"11111");
				//	}
				//}).show();
				
				//Intent intent = new Intent(ActMain.this,ActEmptyCardHaiManager.class);
				//startActivity(intent);
				
				//Intent intent = new Intent(ActMain.this,ActPlanList.class);
				//startActivity(intent);
				
				//Intent intent = new Intent(ActMain.this,ActAddCardInfo.class);
				//startActivity(intent);
				
				//Intent intent = new Intent(ActMain.this,ActSetCommitPlan.class);
				//startActivity(intent);
				
				////设置还款计划
				//Intent intent = new Intent(ActMain.this,ActSetHaimoneyPlan.class);
				//startActivity(intent);
			}
		});
	}
	
}
