package app.card.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.view
 * @description:
 * @date: 2018/12/10 0010  
 * @time: 上午 10:12
 */
public class ForbidViewPager extends ViewPager
{
	
	private boolean isCanScroll = true;
	
	public ForbidViewPager(Context context) {
		super(context);
	}
	
	public ForbidViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 设置其是否能滑动换页
	 * @param isCanScroll false 不能换页， true 可以滑动换页
	 */
	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return isCanScroll && super.onInterceptTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return isCanScroll && super.onTouchEvent(ev);
		
	}
}
