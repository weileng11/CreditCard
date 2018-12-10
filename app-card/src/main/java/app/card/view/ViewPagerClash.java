package app.card.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import app.card.util.ScreenUtils;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.view
 * @description:
 * @date: 2018/11/27 0027  
 * @time: 下午 5:00
 */
public class ViewPagerClash extends android.support.v4.view.ViewPager
{
	
	private Context  context;
	public ViewPagerClash(@NonNull Context context){
		super(context);
		this.context=context;
	}
	
	public ViewPagerClash(@NonNull Context context, @Nullable AttributeSet attrs){
		super(context, attrs);
	}
	
	//@Override
	//public boolean dispatchTouchEvent(MotionEvent ev){
	//	switch(ev.getAction()){
	//	case MotionEvent.ACTION_DOWN:
	//		y=ev.getY();
	//		x=ev.getX();
	//		getParent().requestDisallowInterceptTouchEvent(true);
	//		break;
	//	case MotionEvent.ACTION_MOVE:
	//		if(Math.abs(ev.getX()-x)>Math.abs(ev.getY()-y))
	//			getParent().requestDisallowInterceptTouchEvent(true);
	//		else getParent().requestDisallowInterceptTouchEvent(false);
	//		break;
	//	case MotionEvent.ACTION_UP:
	//	case MotionEvent.ACTION_CANCEL:
	//		getParent().requestDisallowInterceptTouchEvent(false);
	//		break;
	//	default:
	//		break;
	//	}
	//	return super.dispatchTouchEvent(ev);
	//}
	
	/**
	 如果是上下滑动事件，拦截下来，自己处理
	 解决嵌套RecyclerView问题
	 */
	private float x=0;//初始化按下时坐标变量
	private float y=0;//初始化按下时坐标变量
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev){
		/**
		 * 如果是上下滑动事件，拦截下来，自己处理
		 * 解决嵌套RecyclerView问题
		 */
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			//记录按下时的坐标
			x=ev.getX();
			y=ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			float dX=Math.abs(x-ev.getX());
			float dY=Math.abs(y-ev.getY());
			//根据屏幕大小，调整能引发VerticalViewpager拦截竖直滑动的最小高度
			int screenHeight=ScreenUtils.getScreenHeight(context);
			//因为我的HonyWell的PDA屏幕高是800，拦截竖直滑动的最小高度设置为200比较合适，所以以此适配
			int scrollHeight=screenHeight*200/800;//引发VerticalViewpager拦截竖直滑动的最小高度
			//满足竖直滑动，并且滑动高度大于“引发VerticalViewpager拦截竖直滑动的最小高度”时，拦截此滑动事件，不再交给下面的RecyclerView处理
			if(dY>scrollHeight && dY>dX){
				Log.e("MyVerticalViewPager", "MyVerticalViewPager");
				return true;
			}
			break;
		}
		return true;
	}
}