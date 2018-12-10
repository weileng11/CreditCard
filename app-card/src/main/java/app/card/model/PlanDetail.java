package app.card.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.model
 * @description:
 * @date: 2018/11/30 0030  
 * @time: 下午 4:28
 */
public class PlanDetail
{
		@SerializedName("total")
		public int total;
		@SerializedName("per_page")
		public int perPage;
		@SerializedName("current_page")
		public int currentPage;
		@SerializedName("last_page")
		public int lastPage;
		@SerializedName("extend")
		public ExtendBean extend;
		@SerializedName("data")
		public List<DataBean> data;
		
		public static class ExtendBean
		{
			/**
			 * pay : 43.2
			 * repay : 0
			 * o : 0
			 * week_pay : 0
			 * withdraw_fee : 3.84
			 * last_error_msg : null
			 */
			@SerializedName("pay")
			public double pay;
			@SerializedName("repay")
			public int repay;
			@SerializedName("o")
			public int o;
			@SerializedName("week_pay")
			public int weekPay;
			@SerializedName("withdraw_fee")
			public String withdrawFee;
			@SerializedName("last_error_msg")
			public Object lastErrorMsg;
		}
		
		public static class DataBean
		{
			/**
			 * id : 5774
			 * user_id : 10036
			 * card_id : 11
			 * order_sn : 20181128223632529930
			 * main_id : 305484
			 * terminal_time : null
			 * total_fee : 14.40
			 * order_feerate : 0.0120
			 * order_withdraw_fee : 0.0000
			 * money_withdraw : 14.40
			 * add_time : 1543464473
			 * status : 40
			 * out_trade_no : null
			 * channel_trade_no : null
			 * finish_time : 1543464939
			 * plan_pay_time : 1543464583
			 * is_closed : 0
			 * reduce_integral : 0
			 * is_reduce : null
			 * channel : 13
			 * is_delete : 0
			 * is_paid : 0
			 * is_back : 0
			 * back_time : 1543464939
			 * failure_msg : null
			 * try_time : 0
			 * mcc : null
			 * city_code : null
			 * order_type : 6
			 * order_group : 0
			 * voluntary : 0
			 * is_stop : 0
			 * fee : 0.00
			 * real : 0
			 * step : 1
			 * money_withdraw_real : 0.00
			 * query_time : 0
			 * is_settled : 0
			 * open_user_id : 0
			 * income : 0.0000
			 * status_lang : 成功
			 */
			@SerializedName("id")
			public int id;
			@SerializedName("user_id")
			public int userId;
			@SerializedName("card_id")
			public int cardId;
			@SerializedName("order_sn")
			public String orderSn;
			@SerializedName("main_id")
			public int mainId;
			@SerializedName("terminal_time")
			public Object terminalTime;
			@SerializedName("total_fee")
			public String totalFee;
			@SerializedName("order_feerate")
			public String orderFeerate;
			@SerializedName("order_withdraw_fee")
			public String orderWithdrawFee;
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
			public int finishTime;
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
			public String failureMsg;
			@SerializedName("try_time")
			public int tryTime;
			@SerializedName("mcc")
			public Object mcc;
			@SerializedName("city_code")
			public Object cityCode;
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
			@SerializedName("real")
			public int real;
			@SerializedName("step")
			public int step;
			@SerializedName("money_withdraw_real")
			public String moneyWithdrawReal;
			@SerializedName("query_time")
			public int queryTime;
			@SerializedName("is_settled")
			public int isSettled;
			@SerializedName("open_user_id")
			public int openUserId;
			@SerializedName("income")
			public String income;
			@SerializedName("status_lang")
			public String statusLang;
		}
}
