package app.card.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.model
 * @description:
 * @date: 2018/11/30 0030  
 * @time: 下午 3:26
 */
public class PersonInfo
{
	/**
	 wechat_account : 1234
	 has_card : 0
	 has_car : 0
	 id : 10036
	 user_type : 2
	 user_login : 陈陈-1-8
	 mobile : 13631427041
	 user_email :
	 user_nickname : 陈陈-1-8
	 avatar : admin/20181123/15429509345595057.jpg
	 signature :
	 app_msg_push : 0
	 user_url :
	 sex : 0
	 birthday : 0
	 score : 0
	 coin : 0
	 user_status : 1
	 user_activation_key :
	 create_time : 1542950934
	 last_login_time : 0
	 last_login_ip :
	 province : 6
	 city : 59
	 district : 736
	 id_card : 330682199311112814
	 real_name : 杨勇标
	 wx_qrcode : http://res.manshenghuoa.cn/FnBB5X3NCKBstMIWEyGC_e1gB6Uq
	 invite_code : 90010036
	 balance : 0.0000
	 im_id : J10036
	 im_pwd : ###eafe0dc044a926b2b79a4a5b3e94b800
	 work :
	 set_pass : 1
	 integral : 0.00
	 province_name : 内蒙古自治区
	 city_name : 呼和浩特市
	 district_name : 武川县
	 level : 30
	 buy_level : 20
	 vip_number : 0
	 insurance_level : 0.0
	 level_name : 黄金会员
	 income : 0
	 settled_income : 0
	 avatar_full : http://res.manshenghuoa.cn/admin/20181123/15429509345595057.jpg
	 parent_id : 10000
	 is_lender_manager : 0
	 job_no : 610036
	 is_service : false
	 need_bind_mobile : false
	 web_domain : https://m.51lbc.cn/
	 */
	@SerializedName("wechat_account")
	public String wechatAccount;
	@SerializedName("has_card")
	public int hasCard;
	@SerializedName("has_car")
	public int hasCar;
	@SerializedName("id")
	public int id;
	@SerializedName("user_type")
	public int userType;
	@SerializedName("user_login")
	public String userLogin;
	@SerializedName("mobile")
	public String mobile;
	@SerializedName("user_email")
	public String userEmail;
	@SerializedName("user_nickname")
	public String userNickname;
	@SerializedName("avatar")
	public String avatar;
	@SerializedName("signature")
	public String signature;
	@SerializedName("app_msg_push")
	public int appMsgPush;
	@SerializedName("user_url")
	public String userUrl;
	@SerializedName("sex")
	public int sex;
	@SerializedName("birthday")
	public int birthday;
	@SerializedName("score")
	public int score;
	@SerializedName("coin")
	public int coin;
	@SerializedName("user_status")
	public int userStatus;
	@SerializedName("user_activation_key")
	public String userActivationKey;
	@SerializedName("create_time")
	public int createTime;
	@SerializedName("last_login_time")
	public int lastLoginTime;
	@SerializedName("last_login_ip")
	public String lastLoginIp;
	@SerializedName("province")
	public int province;
	@SerializedName("city")
	public int city;
	@SerializedName("district")
	public int district;
	@SerializedName("id_card")
	public String idCard;
	@SerializedName("real_name")
	public String realName;
	@SerializedName("wx_qrcode")
	public String wxQrcode;
	@SerializedName("invite_code")
	public String inviteCode;
	@SerializedName("balance")
	public String balance;
	@SerializedName("im_id")
	public String imId;
	@SerializedName("im_pwd")
	public String imPwd;
	@SerializedName("work")
	public String work;
	@SerializedName("set_pass")
	public int setPass;
	@SerializedName("integral")
	public String integral;
	@SerializedName("province_name")
	public String provinceName;
	@SerializedName("city_name")
	public String cityName;
	@SerializedName("district_name")
	public String districtName;
	@SerializedName("level")
	public int level;
	@SerializedName("buy_level")
	public int buyLevel;
	@SerializedName("vip_number")
	public int vipNumber;
	@SerializedName("insurance_level")
	public String insuranceLevel;
	@SerializedName("level_name")
	public String levelName;
	@SerializedName("income")
	public int income;
	@SerializedName("settled_income")
	public int settledIncome;
	@SerializedName("avatar_full")
	public String avatarFull;
	@SerializedName("parent_id")
	public int parentId;
	@SerializedName("is_lender_manager")
	public int isLenderManager;
	@SerializedName("job_no")
	public String jobNo;
	@SerializedName("is_service")
	public boolean isService;
	@SerializedName("need_bind_mobile")
	public boolean needBindMobile;
	@SerializedName("web_domain")
	public String webDomain;
}
