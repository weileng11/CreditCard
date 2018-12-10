package app.card.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import app.card.base.BaseTitleActivity;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 上传图片证明
 * @date: 2018/11/21 0021  
 * @time: 下午 4:51
 */
public class ActUploadImageProve extends BaseTitleActivity
{
	@Override
	protected int getLayoutId(){
		return super.getLayoutId();
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("上传证明");
	}
}
