package app.card.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.model
 * @description:
 * @date: 2018/11/26 0026  
 * @time: 下午 7:38
 */
public class CardInfo
{
		/**
		 * id : 11
		 * user_id : 10032
		 * name : 施秋良
		 * phone : 13826212710
		 * address : 商业区创意园
		 * prvince : null
		 * city : 5810
		 * category : 4816
		 * merchant_id : 00087034
		 * add_time : 1525316485
		 * cvn2 : 668
		 * bank_name : 招商银行
		 * feerate : null
		 * repay_date : 20
		 * bill_date : 1
		 * is_delete : 0
		 * bank_code : 308
		 * card_code : 4392260036375127
		 * bind_id : 15253165152Hsx
		 * is_bind : 1
		 * remarks : null
		 * id_number : 330682198208182836
		 * shop_id : 8
		 * bank_id : 17
		 * card_date : 0722
		 * hq_large : 0
		 * hq_double : null
		 * bank_card_id : null
		 * hq_union_bind : null
		 * bind_card_order : null
		 * old_bind_bls : 0
		 * old_customer_number : null
		 * leshua_bind_time : 0
		 * leshua_bind_status : 0
		 * leshua_bind_id :
		 * open_user_id : 0
		 * customer_number :
		 * bind_bls : 0
		 * e_bind_id : 2018111418195700100104958
		 * f_hqhj_card_id :
		 * act : 1
		 * failure_count : 0
		 * bank : {"id":17,"bank_name":"招商银行","bank_code":"308","thumb":"http://res.kamengjinfu.com/admin/20171220/d5cc53871721d0b76544b98c1582c261.jpg","color":"#a20000","background":"http://res.manshenghuoa.cn/admin/20181121/764475b2ee5a81f3a15c88947f3c589d.png","sort":108,"bank_code_y":"308584000013","bank_name_y":"招商银行"}
		 */
		@SerializedName("id")
		public int id;
		@SerializedName("user_id")
		public int userId;
		@SerializedName("name")
		public String name;
		@SerializedName("phone")
		public String phone;
		@SerializedName("address")
		public String address;
		@SerializedName("prvince")
		public Object prvince;
		@SerializedName("city")
		public int city;
		@SerializedName("category")
		public String category;
		@SerializedName("merchant_id")
		public String merchantId;
		@SerializedName("add_time")
		public int addTime;
		@SerializedName("cvn2")
		public String cvn2;
		@SerializedName("bank_name")
		public String bankName;
		@SerializedName("feerate")
		public String feerate;
		@SerializedName("repay_date")
		public int repayDate;
		@SerializedName("bill_date")
		public int billDate;
		@SerializedName("is_delete")
		public int isDelete;
		@SerializedName("bank_code")
		public String bankCode;
		@SerializedName("card_code")
		public String cardCode;
		@SerializedName("bind_id")
		public String bindId;
		@SerializedName("is_bind")
		public int isBind;
		@SerializedName("remarks")
		public Object remarks;
		@SerializedName("id_number")
		public String idNumber;
		@SerializedName("shop_id")
		public int shopId;
		@SerializedName("bank_id")
		public int bankId;
		@SerializedName("card_date")
		public String cardDate;
		@SerializedName("hq_large")
		public int hqLarge;
		@SerializedName("hq_double")
		public Object hqDouble;
		@SerializedName("bank_card_id")
		public Object bankCardId;
		@SerializedName("hq_union_bind")
		public Object hqUnionBind;
		@SerializedName("bind_card_order")
		public Object bindCardOrder;
		@SerializedName("old_bind_bls")
		public int oldBindBls;
		@SerializedName("old_customer_number")
		public Object oldCustomerNumber;
		@SerializedName("leshua_bind_time")
		public int leshuaBindTime;
		@SerializedName("leshua_bind_status")
		public int leshuaBindStatus;
		@SerializedName("leshua_bind_id")
		public String leshuaBindId;
		@SerializedName("open_user_id")
		public int openUserId;
		@SerializedName("customer_number")
		public String customerNumber;
		@SerializedName("bind_bls")
		public int bindBls;
		@SerializedName("e_bind_id")
		public String eBindId;
		@SerializedName("f_hqhj_card_id")
		public String fHqhjCardId;
		@SerializedName("act")
		public int act;
		@SerializedName("failure_count")
		public int failureCount;
		@SerializedName("bank")
		public BankBean bank;
		
		public static class BankBean
		{
			/**
			 * id : 17
			 * bank_name : 招商银行
			 * bank_code : 308
			 * thumb : http://res.kamengjinfu.com/admin/20171220/d5cc53871721d0b76544b98c1582c261.jpg
			 * color : #a20000
			 * background : http://res.manshenghuoa.cn/admin/20181121/764475b2ee5a81f3a15c88947f3c589d.png
			 * sort : 108
			 * bank_code_y : 308584000013
			 * bank_name_y : 招商银行
			 */
			@SerializedName("id")
			public int id;
			@SerializedName("bank_name")
			public String bankName;
			@SerializedName("bank_code")
			public String bankCode;
			@SerializedName("thumb")
			public String thumb;
			@SerializedName("color")
			public String color;
			@SerializedName("background")
			public String background;
			@SerializedName("sort")
			public int sort;
			@SerializedName("bank_code_y")
			public String bankCodeY;
			@SerializedName("bank_name_y")
			public String bankNameY;
		}
}
