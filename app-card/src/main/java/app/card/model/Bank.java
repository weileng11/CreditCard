package app.card.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.model
 * @description:
 * @date: 2018/11/23 0023  
 * @time: 下午 6:36
 */
public class Bank
{
	/**
	 id : 12
	 bank_name : 中国光大银行
	 bank_code : 303
	 thumb : http://res.kamengjinfu.com/admin/20180319/160c106dc9b9398376a3fb0a985854f8.png
	 color : #d9d900
	 background : http://res.manshenghuoa.cn/admin/20181121/4974bdb0bc0c24a374e23a893f1f2b60.png
	 sort : 123
	 bank_code_y : 303100000006
	 bank_name_y : 光大银行
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
	
	public String selectType;
	
	public static class Page extends DataPage<List<Bank>>
	{}
}
