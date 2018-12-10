package app.card.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.model
 * @description:
 * @date: 2018/11/23 0023  
 * @time: 上午 11:48
 */
public class User
{
	/**
	 * code : 1
	 * msg : 登录成功!
	 * data : {"token":"a45c4777dfec0fe7a6ad65e4f788b6943bffdd663128271e1b0871cabb1b8338"}
	 */
	/**
	 token : a45c4777dfec0fe7a6ad65e4f788b6943bffdd663128271e1b0871cabb1b8338
	 */
	@SerializedName("token")
	public String token;
}
