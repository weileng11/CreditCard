package app.card.view.banner.mzbanner.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.view.banner.mzbanner.transformer
 * @description:
 * @date: 2018/11/29 0029  
 * @time: 下午 3:46
 */
public class ScaleYTransformer implements ViewPager.PageTransformer
{
	private static final float MIN_SCALE=0.9F;
	
	@Override
	public void transformPage(View page, float position){
		if(position<-1){
			page.setScaleY(MIN_SCALE);
		}else if(position<=1){
			//
			float scale=Math.max(MIN_SCALE, 1-Math.abs(position));
			page.setScaleY(scale);
	        /*page.setScaleX(scale);
            if(position<0){
                page.setTranslationX(width * (1 - scale) /2);
            }else{
                page.setTranslationX(-width * (1 - scale) /2);
            }*/
		}else{
			page.setScaleY(MIN_SCALE);
		}
	}
}
