package app.card.nodata;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.card.R;

/**
 * @class describe
 * @anthor ${bruce} QQ:275762645
 * @time 2018/2/3 10:09
 * @change
 * @chang time
 * @class describe 没有数据自定义组件
 */
public class ProjectNoDataLayout extends LinearLayout
{
	private View view;
	private ImageView mIvNoData;
	private LinearLayout rlProjectNoData;
	//无数据内容
	private TextView txvNOData;
	
	public ProjectNoDataLayout(Context context){
		super(context);
	}
	
	public ProjectNoDataLayout(final Context context, @Nullable AttributeSet attrs){
		super(context, attrs);
		LayoutInflater inflater=
				(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view=inflater.inflate(R.layout.project_no_data, this);
		mIvNoData=view.findViewById(R.id.iv_project_no_data);
		rlProjectNoData=view.findViewById(R.id.rl_project_no_data);
		txvNOData=view.findViewById(R.id.txv_project_no_data);
	}
	
	/**
	 显示内容
	 */
	public void setProjectRelativeLayoutShow(int visibleStatus){
		rlProjectNoData.setVisibility(visibleStatus);
	}
	//    /**
	//     * 设置间距
	//     */
	//    public void setProjectRelativeLayoutDistance(Context context){
	//        LinearLayout.LayoutParams lp = (LayoutParams) rlProjectNoData.getLayoutParams();
	//        lp.setMargins(0, context.getResources().getDimensionPixelOffset(R.dimen.x45),context.getResources().getDimensionPixelOffset(R.dimen.x22), 0);
	//        rlProjectNoData.setLayoutParams(lp);
	//    }
	
	/**
	 根据模式类型设置图片
	 */
	public void setRigthViewTypeMode(IvNoDataTypeMode mode, String textStr){
		if(IvNoDataTypeMode.NOMESSAGE==mode){//无消息
			mIvNoData.setBackgroundResource(R.mipmap.card_plan_nodata);
			txvNOData.setText(textStr);
		}
	}
	
	/**
	 @author lt
	 */
	public enum IvNoDataTypeMode
	{
		/**
		 计划列表
		 */
		NOMESSAGE,
	}
}
