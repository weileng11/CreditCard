package app.card.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.card.R;

/**  Created by wuzhengu on 2018/10/31 0031 */
public class BaseTitleActivity extends BaseActivity
{
	
	//TextView tvLeft;
	TextView tvTitle;
	TextView tvRight;
	LinearLayout llBack;
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		initToolbar();
	}
	
	public void initToolbar(){
		//tvLeft=(TextView)findViewById(R.id.toolbar_left_text);
		llBack=(LinearLayout)findViewById(R.id.ll_back);
		tvTitle=(TextView)findViewById(R.id.toolbar_title_text);
		tvRight=(TextView)findViewById(R.id.toolbar_right_text);
		if(llBack!=null) llBack.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view){
				onClickLeft();
			}
		});
		//if(tvLeft!=null) tvLeft.setOnClickListener(new View.OnClickListener()
		//{
		//	@Override
		//	public void onClick(View view){
		//		onClickLeft();
		//	}
		//});
		if(tvTitle!=null) tvTitle.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view){
				onClickTitle();
			}
		});
		if(tvRight!=null) tvRight.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view){
				onClickRight();
			}
		});
	}
	
	public void setTitle(int resId){
		setTitle(resId>0? getString(resId): null);
	}
	
	public void setTitle(CharSequence text){
		if(tvTitle!=null) tvTitle.setText(text);
	}
	
	//public void setLeftText(int resId){
	//	setLeftText(resId>0? getString(resId): null);
	//}
	//
	//public void setLeftText(CharSequence text){
	//	if(tvLeft!=null) tvLeft.setText(text);
	//}
	
	public void setRightText(int resId){
		setRightText(resId>0? getString(resId): null);
	}
	
	public void setRightText(CharSequence text){
		if(tvRight!=null) tvRight.setText(text);
	}
	
	protected void onClickLeft(){
		onBackPressed();
	}
	
	protected void onClickRight(){
		
	}
	
	protected void onClickTitle(){
	}
	
}
