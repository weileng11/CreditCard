package app.card.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import java.text.SimpleDateFormat;
import java.util.*;
import app.card.R;
import app.card.R2;
import app.card.base.NameFragment;
import app.card.quick.QuickAdapter;
import app.card.quick.QuickHolder;
import app.card.view.KmRecyclerView;
import butterknife.BindView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui.fragment
 * @description: 自定义计划
 * @date: 2018/11/21 0021  
 * @time: 下午 2:16
 */
public class FmCustomPlan extends NameFragment
{
	@BindView(R2.id.card_add_consumption)
	CardView cardAddConsumption;
	@BindView(R2.id.card_add_hk)
	CardView cardAddHk;
	@BindView(R2.id.rv_card_plan)
	KmRecyclerView rvCardPlan;
	public List<String> mStringList=new ArrayList<>();
	CustomPlanAdapter mAdapter;
	
	@Override
	public int getLayout(){
		return R.layout.fm_custom_plan;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle bundle){
		super.onViewCreated(view, bundle);
		initListenter();
		for(int i=0; i<10; i++){
			mStringList.add("测试数据"+i);
		}
		mAdapter=new CustomPlanAdapter(R.layout.item_add_bill);
		rvCardPlan.setAdapter(mAdapter);
		mAdapter.setNewData(mStringList);
		//showPlanCalendar();
	}
	
	@Override
	public void onInitLate(){
		super.onInitLate();
	}
	
	private void initListenter(){
		cardAddConsumption.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				//添加消费单
			}
		});
		cardAddHk.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				//添加还款单
			}
		});
	}
	
	class CustomPlanAdapter extends QuickAdapter<String>
	{
		public CustomPlanAdapter(int layout){
			super(layout);
		}
		
		@Override
		protected void convert(QuickHolder helper, final String item){
			if(item==null) return;
		}
	}
	
	//日历
	TimePickerView pvTime;
	
	private void showPlanCalendar(){
		Calendar selectedDate=Calendar.getInstance();
		Calendar startDate=Calendar.getInstance();
		//startDate.set(2013,1,1);
		Calendar endDate=Calendar.getInstance();
		//endDate.set(2020,1,1);
		//正确设置方式 原因：注意事项有说明
		startDate.set(2018, 11, 1);
		endDate.set(2028, 11, 31);
		pvTime=new TimePickerBuilder(getActivity(), new OnTimeSelectListener()
		{
			@Override
			public void onTimeSelect(Date date, View v){//选中事件回调
				Log.i("info", getTime(date));
			}
		}).setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
				.setCancelText("取消")//取消按钮文字
				.setSubmitText("确定")//确认按钮文字
				.setSubCalSize(getResources().getDimensionPixelSize(R.dimen.sp5))
				.setTitleSize(getResources().getDimensionPixelSize(R.dimen.sp6))//标题文字大小
				.setContentTextSize(getResources().getDimensionPixelSize(R.dimen.sp5))
				.setTitleText("设置日期")//标题文字
				.setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
				.isCyclic(true)//是否循环滚动
				.setTitleColor(ContextCompat.getColor(getActivity(),R.color.grayColor_main))//标题文字颜色
				.setSubmitColor(ContextCompat.getColor(getActivity(),R.color.color_bus_hui))//确定按钮文字颜色
				.setCancelColor(ContextCompat.getColor(getActivity(),R.color.color_card_b1))//取消按钮文字颜色
				.setTitleBgColor(ContextCompat.getColor(getActivity(),R.color.colorWhite))//标题背景颜色 Night mode
				.setBgColor(ContextCompat.getColor(getActivity(),R.color.colorWhite))//滚轮背景颜色 Night mode
				.setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
				.setRangDate(startDate, endDate)//起始终止年月日设定
				.setLineSpacingMultiplier(2.0f)
				.setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
				.isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
				.isDialog(false)//是否显示为对话框样式
				.build();
		pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
		pvTime.show();
	}
	
	private String getTime(Date date){//可根据需要自行截取数据显示
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
}
