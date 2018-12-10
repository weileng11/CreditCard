package app.card.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import app.card.R;
import app.card.R2;
import app.card.api.RxListener2;
import app.card.api.Server;
import app.card.api.Servers;
import app.card.app.App;
import app.card.base.ActivityProxy;
import app.card.base.NameFragment;
import app.card.model.CardInfo;
import app.card.model.M;
import app.card.ui.ActArea;
import app.card.ui.ActPlanList;
import app.card.ui.ActSetHaimoneyPlan;
import app.card.ui.bean.PlanTime;
import app.card.ui.sort.SortModel;
import app.card.util.DateTimeUtil;
import app.card.util.pop.PlanDateSetPop;
import butterknife.BindView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui.fragment
 * @description: 系统计划
 * @date: 2018/11/21 0021  
 * @time: 下午 2:16
 */
public class FmSysPlan extends NameFragment
{
	@BindView(R2.id.txv_card_area)
	TextView txvCardArea;
	@BindView(R2.id.ll_area)
	LinearLayout llArea;
	@BindView(R2.id.ed_hai_money_count)
	EditText edHaiMoneyCount;
	@BindView(R2.id.ll_date)
	LinearLayout llDate;
	@BindView(R2.id.ed_user_balance)
	EditText edUserBalance;
	@BindView(R2.id.ed_hai_num)
	EditText edHaiNum;
	@BindView(R2.id.ed_hai_money)
	EditText edHaiMoney;
	@BindView(R2.id.txv_hai_date)
	TextView txvHaiDate;
	@BindView(R2.id.ll_hai_date)
	LinearLayout llHaiDate;
	@BindView(R2.id.iv_hai_jian)
	ImageView ivHaiJian;
	@BindView(R2.id.txv_hai_num)
	TextView txvHaiNum;
	@BindView(R2.id.iv_hai_jia)
	ImageView ivHaiJia;
	@BindView(R2.id.txv_enact_plan)
	TextView txvEnactPlan;
	@BindView(R2.id.txv_card_date)
	TextView txvCardDate;
	private int defaultInt=1;
	//城市id
	private String cityId;
	private String area;
	private String money;
	private String date;
	private CardInfo mCardInfo;
	private String cardId;
	private String repayDate;
	
	@Override
	public int getLayout(){
		return R.layout.fm_sys_plan;
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle bundle){
		super.onViewCreated(view, bundle);
		initListenter();
		mCardInfo=((ActSetHaimoneyPlan)getActivity()).getCardInfo();
		//显示月份和日期
		showMonthDay();
	}
	
	//显示月份和日期
	private void showMonthDay(){
		//获取当前年月日
		String today=DateTimeUtil.formatDateTimeDay(System.currentTimeMillis());
		String day=
				today.toString().substring(today.toString().length()-2, today.toString().length());
		//如果还款日==今天，显示下个月的今天
		if(mCardInfo.repayDate==Integer.parseInt(day)){
			//获取下一个月
			String endNextTime=DateTimeUtil.monthAddFrist(today);
			String nextMonth=endNextTime.toString()
					.substring(endNextTime.toString().length()-5,
							endNextTime.toString().length()-3);
			repayDate=nextMonth+"月"+mCardInfo.repayDate+"日";
		}else{
			repayDate=
					today.substring(today.length()-5, today.length()-3)+"月"+mCardInfo.repayDate+"日";
		}
		txvCardDate.setText(repayDate);
	}
	
	public static Intent intent(){
		Intent intent=new Intent(App.get(), ActArea.class);
		return intent;
	}
	
	private void initListenter(){
		llArea.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				getProxy().startActivity(new ActivityProxy.Starter()
				{
					@Override
					public Intent getIntent(){
						return intent();
					}
					
					@Override
					protected void onResult(boolean ok, Intent data){
						SortModel sortModel=ActArea.parseResult(data);
						if(sortModel!=null){
							String city=sortModel.name;
							cityId=sortModel.cityId;
							txvCardArea.setText(city);
						}
					}
				});
			}
		});
		////还款日期
		//llHaiDate.setOnClickListener(new View.OnClickListener()
		//{
		//	@Override
		//	public void onClick(View v){
		//
		//	}
		//});
		llDate.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				//获取固定的月份，根据当前月份天数，显示当前月+下一个月
				String startTime=DateTimeUtil.formatDateTimeDay(System.currentTimeMillis());
				String endTime=DateTimeUtil.monthAddFrist(startTime);
				List<String> timeList=DateTimeUtil.getDiffDay(startTime, endTime);
				List<PlanTime> mPlanTimes=new ArrayList<>();
				for(String s : timeList){
					Log.i("info", s.toString());
					PlanTime plan=new PlanTime();
					StringBuilder sp=new StringBuilder();
					String month=s.toString()
							.substring(s.toString().length()-5, s.toString().length()-3);
					String day=
							s.toString().substring(s.toString().length()-2, s.toString().length());
					sp.append(month+"月");
					sp.append(day+"日");
					//plan.monthDay=s.toString().substring(s.toString().length()-5, s.toString().length());
					plan.monthDay=sp.toString();
					plan.type="0";
					mPlanTimes.add(plan);
				}
				//弹窗
				new PlanDateSetPop(getProxy().getActivity(), mPlanTimes,
						new PlanDateSetPop.CommonSetListenter()
						{
							@Override
							public void selectDataOk(PlanTime content){
								txvCardDate.setText(content.monthDay);
							}
						});
			}
		});
		ivHaiJian.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				if(defaultInt>0) defaultInt--;
				else defaultInt=0;
				txvHaiNum.setText(String.valueOf(defaultInt));
				//减
			}
		});
		ivHaiJia.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				//加
				defaultInt++;
				txvHaiNum.setText(String.valueOf(defaultInt));
			}
		});
		//制定计划
		txvEnactPlan.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v){
				if(checkParams()) commitHaiPlan();
			}
		});
	}
	
	private void commitHaiPlan(){
		cardId=String.valueOf(mCardInfo.id);
		HashMap<String, String> map=new HashMap<>();
		map.put("card_id", cardId);
		map.put("city_code", cityId);
		map.put("money", money);
		map.put("pay_date", date);
		Servers.start(this, Server.get().formulatePlan(map), new RxListener2()
		{
			@Override
			public void onNext(M m, String msg){
				if(m==null) return;
				if(null!=m){
					if(1==m.code){
						//操作成功跳转到计划列表界面
						Intent intent=new Intent(getActivity(), ActPlanList.class);
						intent.putExtra("dialog", "yes");
						startActivity(intent);
					}else{
						toast(m.msg);
					}
				}
			}
		});
	}
	
	private boolean checkParams(){
		area=txvCardArea.getText().toString();
		money=edHaiMoneyCount.getText().toString();
		date=txvCardDate.getText().toString();
		if(TextUtils.isEmpty(area)){
			toast("请选择地区");
			return false;
		}else if(TextUtils.isEmpty(money)){
			toast("请输入金额");
			return false;
		}else if(TextUtils.isEmpty(date)){
			toast("请选择日期");
			return false;
		}
		return true;
	}
}
