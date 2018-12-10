package app.card.util;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.util
 * @description:
 * @date: 2018/11/23 0023  
 * @time: 下午 5:55
 */
public class RegexUtil
{
	/**
	 比较真实完整的判断身份证号码的工具
	 @param IdCard 用户输入的身份证号码
	 @return [true符合规范, false不符合规范]
	 */
	public static boolean isRealIDCard(String IdCard){
		if(IdCard!=null){
			int correct=new IdCardUtil(IdCard).isCorrect();
			if(0==correct){// 符合规范
				return true;
			}
		}
		return false;
	}
	
	// 判断是否符合身份证号码的规范
	public static boolean isIDCard(String IDCard){
		if(IDCard!=null){
			String IDCardRegex="(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x|Y|y)$)";
			return IDCard.matches(IDCardRegex);
		}
		return false;
	}
	
	//信用卡校验
	
	/**
	 LUHN算法，主要用来计算信用卡等证件号码的合法性。
	 1、从卡号最后一位数字开始,偶数位乘以2,如果乘以2的结果是两位数，将两个位上数字相加保存。
	 2、把所有数字相加,得到总和。
	 3、如果信用卡号码是合法的，总和可以被10整除。
	 @param bankNumber
	 @return
	 */
	public static boolean isBankNumber(String bankNumber) {
		char[] cc = bankNumber.toCharArray();
		int[] n = new int[cc.length + 1];
		int j = 1;
		for (int i = cc.length - 1; i >= 0; i--) {
			n[j++] = cc[i] - '0';
		}
		int even = 0;
		int odd = 0;
		for (int i = 1; i < n.length; i++) {
			if (i % 2 == 0) {
				int temp = n[i] * 2;
				if (temp < 10) {
					even += temp;
				} else {
					temp = temp - 9;
					even += temp;
				}
			} else {
				odd += n[i];
			}
		}
		
		int total = even + odd;
		if (total % 10 == 0)
			return true;
		return false;
		
	}
}
