package app.card.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.model
 * @description: 信用卡管理还款计划
 * @date: 2018/11/23 0023  
 * @time: 下午 3:56
 */
public class CardPlan
{
	public static class Page extends DataPage<List<CardPlan>>
	{}
	
	/**
	 id : 305484
	 user_id : 10036
	 card_id : 11
	 order_sn : 2018112822363252993
	 terminal_time : null
	 total_fee : 1200.00
	 order_feerate : null
	 order_withdraw_fee : null
	 money_withdraw : 1200.00
	 add_time : 1543415792
	 status : 30
	 out_trade_no : null
	 channel_trade_no : null
	 finish_time : null
	 plan_pay_time : 0
	 is_closed : 0
	 reduce_integral : 0
	 is_reduce : null
	 channel : 1
	 is_delete : 0
	 is_paid : 0
	 is_back : 0
	 back_time : 0
	 failure_msg : null
	 try_time : 0
	 mcc : null
	 city_code : 440100
	 order_type : 1
	 order_group : 0
	 voluntary : 0
	 is_stop : 0
	 fee : 0.00
	 total_fee_done : 0.00
	 money_withdraw_done : 0.00
	 step : 0
	 type : 2
	 check_status : 3
	 remark : 贷款失败
	 pay_date : 2018-12-14 00:00:00
	 loan : 110
	 sponsor : 0
	 pay_rate : 0.00
	 sponsor_pay_rate : 0.00
	 status_lang : 失败
	 num : 2
	 card_code : 5127
	 pay_fail_num : 0
	 repay_fail_num : 0
	 pay_num : 34
	 */
	@SerializedName("id")
	public int id;
	@SerializedName("user_id")
	public int userId;
	@SerializedName("card_id")
	public int cardId;
	@SerializedName("order_sn")
	public String orderSn;
	@SerializedName("terminal_time")
	public Object terminalTime;
	@SerializedName("total_fee")
	public String totalFee;
	@SerializedName("order_feerate")
	public Object orderFeerate;
	@SerializedName("order_withdraw_fee")
	public Object orderWithdrawFee;
	@SerializedName("money_withdraw")
	public String moneyWithdraw;
	@SerializedName("add_time")
	public int addTime;
	@SerializedName("status")
	public int status;
	@SerializedName("out_trade_no")
	public Object outTradeNo;
	@SerializedName("channel_trade_no")
	public Object channelTradeNo;
	@SerializedName("finish_time")
	public Object finishTime;
	@SerializedName("plan_pay_time")
	public int planPayTime;
	@SerializedName("is_closed")
	public int isClosed;
	@SerializedName("reduce_integral")
	public int reduceIntegral;
	@SerializedName("is_reduce")
	public Object isReduce;
	@SerializedName("channel")
	public int channel;
	@SerializedName("is_delete")
	public int isDelete;
	@SerializedName("is_paid")
	public int isPaid;
	@SerializedName("is_back")
	public int isBack;
	@SerializedName("back_time")
	public int backTime;
	@SerializedName("failure_msg")
	public Object failureMsg;
	@SerializedName("try_time")
	public int tryTime;
	@SerializedName("mcc")
	public Object mcc;
	@SerializedName("city_code")
	public String cityCode;
	@SerializedName("order_type")
	public int orderType;
	@SerializedName("order_group")
	public String orderGroup;
	@SerializedName("voluntary")
	public int voluntary;
	@SerializedName("is_stop")
	public int isStop;
	@SerializedName("fee")
	public String fee;
	@SerializedName("total_fee_done")
	public String totalFeeDone;
	@SerializedName("money_withdraw_done")
	public String moneyWithdrawDone;
	@SerializedName("step")
	public int step;
	@SerializedName("type")
	public int type;
	@SerializedName("check_status")
	public int checkStatus;
	@SerializedName("remark")
	public String remark;
	@SerializedName("pay_date")
	public String payDate;
	@SerializedName("loan")
	public int loan;
	@SerializedName("sponsor")
	public int sponsor;
	@SerializedName("pay_rate")
	public String payRate;
	@SerializedName("sponsor_pay_rate")
	public String sponsorPayRate;
	@SerializedName("status_lang")
	public String statusLang;
	@SerializedName("num")
	public int num;
	@SerializedName("card_code")
	public String cardCode;
	@SerializedName("pay_fail_num")
	public int payFailNum;
	@SerializedName("repay_fail_num")
	public int repayFailNum;
	@SerializedName("pay_num")
	public int payNum;
}
