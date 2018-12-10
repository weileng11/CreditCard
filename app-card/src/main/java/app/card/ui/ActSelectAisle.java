package app.card.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import app.card.R;
import app.card.base.BaseTitleActivity;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description:选择通道
 * @date: 2018/11/20 0020  
 * @time: 下午 6:06
 */
public class ActSelectAisle extends BaseTitleActivity
{
	//@BindView(R2.id.rv_aisle)
	//KmRecyclerView rvAisle;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_select_aisle;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("选择通道");
	}
}
