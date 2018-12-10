package app.card.api;

import java.util.Map;
import app.card.model.*;
import retrofit2.http.*;
import rx.Observable;

/**
 Created by wuzhengu on 2018/10/30 0019 */
public class Server
{
	public static final String TEST_TOKEN=
			"b06159a8b75517de4fd6de3f162f12b6649b073c2e81217e078df1da69fc9ac5";
	public static final String DEVICE_TYPE="android";
	public static final String BASE_WEB="https://m.manshenghuoa.cn/";
	public static final String BASE_URL="https://api.manshenghuoa.cn/";
	//public static final String BASE_WEB="https://m.51lbc.cn/";
	
	private static Api api=null;
	
	public static synchronized Api get(){
		if(api==null) api=Servers.getRetrofit(BASE_URL).create(Api.class);
		return api;
	}
	
	public interface Api
	{
		/**
		 登录
		 */
		@FormUrlEncoded
		@POST("api/user/login")
		Observable<M<User>> doLogin(@Field("username") String username,
				@Field("password") String password, @Field("device_type") String type);
		/**
		 获取个人信息
		 */
		@GET("api/profile/get")
		Observable<M<PersonInfo>> getPersonInfo();
		
		/**
		 信用卡管理
		 */
		@GET("api/intelligent_repayment/card/lists")
		Observable<M<CardManager.Page>> creditManager();
		/**
		 还款计划
		 */
		@GET("api/intelligent_repayment/lists")
		Observable<M<CardPlan.Page>> cardHaiPlan(@Query("page") String page,
				@Query("status") String status);
		/**
		 计划列表
		 */
		@GET("api/intelligent_repayment/lists")
		Observable<M<PlanList.Page>> getPlanList(@Query("page") String page,
				@Query("status") String status);
		/**
		 计划列表暂停订单
		 */
		@FormUrlEncoded
		@POST("/api/intelligent_repayment/cancel")
		Observable<M> planOrderPause(@Field("id") String id);
		/**
		 银行集合
		 */
		@GET("api/intelligent_repayment/bank_list")
		Observable<M<Bank.Page>> getBankList();
		/**
		 卡对应的信息(待定post或者get)
		 */
		@GET("api/intelligent_repayment/card/info")
		Observable<M<CardInfo>> getCardBankInfo(@Query("id") String id);
		/**
		 计划列表头部
		 */
		@GET("/api/intelligent_repayment/menu")
		Observable<M<Menu>> getPlanListTitle();
		/**
		 计划列表详情
		 */
		@GET("api/intelligent_repayment/c_lists")
		Observable<M<PlanDetail>> getPlanDetail(@Query("id") String id,
				@Query("page") String page);
		
		/**
		 添加信用卡获取验证码
		 */
		@FormUrlEncoded
		@POST("api/user/sms_code/send")
		Observable<M> getCardCode(@Field("phone") String phone);
		/**
		 信用卡语音验证码
		 */
		@FormUrlEncoded
		@POST("api/user/sms_code/video_send")
		Observable<M> getCardVideoCode(@Field("phone") String phone);
		/**
		 添加信用卡
		 */
		@FormUrlEncoded
		@POST("api/intelligent_repayment/card/open")
		Observable<M> saveCardInfo(@FieldMap Map<String, String> params);
		
		/**
		 删除信用卡
		 */
		@FormUrlEncoded
		@POST("api/intelligent_repayment/card/del")
		Observable<M> deleteCardInfo(@Field("id") String id,@Field("verification_code") String code);
		/**
		 修改还款日和账单日
		 */
		@GET("api/intelligent_repayment/card_update")
		Observable<M> updateDateInfo(@Query("id") String id, @Query("key") String key,
		@Query("val") String val);
		
		/**
		 空卡周转
		 */
		@GET("api/intelligent_repayment/order/check_e_source")
		Observable<M> getCheckSourceStatus(@Query("source") String source ,@Query("card_id") String cardId);
		/**
		 还款计划暂停
		 */
		@FormUrlEncoded
		@POST("api/intelligent_repayment/cancel")
		Observable<M> pauseCardPlan(@Field("id") String id);
		/**
		 获取地区
		 */
		@GET("api/intelligent_repayment/kk/area")
		Observable<M<Area>> getCardArea();
		/**
		 制定计划
		 */
		@FormUrlEncoded
		@POST("api/intelligent_repayment/kk_create")
		Observable<M> formulatePlan(@FieldMap Map<String, String> params);
		
		//
		///** 城市列表 */
		//@GET("api/User/get_region")
		//Observable<M<Region.Result>> getCityList(@Query("id") int id);
		//
		//@GET("api/Goods/hot_tips")
		//Observable<M<List<BaseGood>>> getTags();
		//
		//@GET("api/ad/index_ad")
		//Observable<M<List<Good>>> getBanners();
		//
		//@GET("api/Goods/categoryList")
		//Observable<M<List<GoodsType>>> getGoodsType();
		//
		//@GET("api/Goods/indexZero")
		//Observable<M<Good.Page>> getVipGo();
		//
		//@GET("api/Goods/collage")
		//Observable<M<GoodsType>> getCollage();
		//
		//@GET("api/Goods/cateHot")
		//Observable<M<List<GoodsType>>> getHots();
		//
		//@GET("api/Goods/index")
		//Observable<M<Good.Page>> getGoods(@Query("page") int page, @Query("cat_id") String id,
		//		@Query("keyword") String keyword);
		//
		//@GET("api/business_card/index_data")
		//Observable<M<CityService>> getCityService();
		//
		//@GET("api/business_card/massage_list")
		//Observable<M<News.Page>> getNews(@Query("page") int page, @Query("type") String type,
		//		@Query("location_x") String longitude, @Query("location_y") String latitude,
		//		@Query("cate_id") String cateId);
		//
		//@GET("api/business_card/card_list")
		//Observable<M<Card.Page>> getCards(@Query("page") int page, @Query("is_hot") int hot,
		//		@Query("is_nearby") int nearby, @Query("location_x") String longitude,
		//		@Query("location_y") String latitude, @Query("cate_id") String cateId);
		//
		//@GET("api/business_card/get_classify")
		//Observable<M<List<Card.Category>>> getCardCategorys();
		//
		//@FormUrlEncoded
		//@POST("api/Friend/apply")
		//Observable<M> doFriendApply(@Field("friend_uid") String uid, @Field("msg") String msg);
		//
		//@FormUrlEncoded
		//@POST("api/User/send_validate_code")
		//Observable<M> doSendCode(@Field("mobile") String mobile);
		//
		//@FormUrlEncoded
		//@POST("api/UserCentre/binding_mobile")
		//Observable<M<PhoneBind>> doBindPhone(@Field("mobile") String phone,
		//		@Field("code") String code, @Field("pwd") String pwd, @Field("pwd_2") String pwd2);
		///**
		//  个人中心
		// */
		//@GET("api/UserCentre/index")
		//Observable<M<UserCenter>> getUserCenter();
		///**
		// 城主信息
		// */
		//@GET("api/Territory/info")
		//Observable<M<CityMaster>> getCityMaster();
		//
		///**
		// 我的名片
		// */
		//@GET("api/business_card/card_detail")
		//Observable<M<CardDetail>> getCardDetail(@Query("user_id") String uid,
		//		@Query("type") String type);
		//
		///**
		//  收藏
		// */
		//@GET("api/business_card/collection_card")
		//Observable<M> doCardCollect(@Query("card_id") String cardId);
		///**
		// 点赞
		// */
		//@GET("api/business_card/add_praise")
		//Observable<M> doCardPraise(@Query("card_id") String cardId);
		//
		///**
		// 设置邀请码
		// */
		//@GET("api/UserCentre/binding")
		//Observable<M> doInvite(@Query("invite_code") String code);
	}
}
