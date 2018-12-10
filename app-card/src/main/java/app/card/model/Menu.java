package app.card.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.model
 * @description:
 * @date: 2018/11/27 0027  
 * @time: 下午 1:17
 */
public class Menu
{
	///**
	// * code : 1
	// * msg :
	// * data : {"0":"全部","10":"等待执行","20":"执行中","30":"失败","40":"成功","50":"审核中","60":"审核失败"}
	// */
	//@SerializedName("data")
	//public DataBean data;
	//
	//public static class DataBean
	//{
		/**
		 * 0 : 全部
		 * 10 : 等待执行
		 * 20 : 执行中
		 * 30 : 失败
		 * 40 : 成功
		 * 50 : 审核中
		 * 60 : 审核失败
		 */
		@SerializedName("0")
		public String $0;
		@SerializedName("10")
		public String $10;
		@SerializedName("20")
		public String $20;
		@SerializedName("30")
		public String $30;
		@SerializedName("40")
		public String $40;
		@SerializedName("50")
		public String $50;
		@SerializedName("60")
		public String $60;
	
	//public Map<String, String> data;
	//
	//public static class TitleBean{
	//	public String id;
	//	public String title;
	//}
}
