package app.card.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import app.card.R;
import app.card.R2;
import app.card.api.RxListener2;
import app.card.api.Server;
import app.card.api.Servers;
import app.card.base.BaseTitleActivity;
import app.card.model.M;
import app.card.model.PersonInfo;
import app.card.util.RegexUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 添加信用卡流程
 * @date: 2018/11/20 0020  
 * @time: 下午 6:05
 */
public class ActAddCardFlow extends BaseTitleActivity
{
	@BindView(R2.id.ed_username)
	EditText edUsername;
	@BindView(R2.id.ed_identity)
	TextView edIdentity;
	@BindView(R2.id.ed_card_hao)
	EditText edCardHao;
	@BindView(R2.id.ed_card_content)
	TextView edCardContent;
	@BindView(R2.id.card_next_commit)
	CardView cardNextCommit;
	@BindView(R2.id.ed_validate)
	EditText edValidate;
	private String userName;
	private String validateStr;
	private String cardNum;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_add_card_flow;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("添加信用卡");
		//加载个人信息
		load();
	}
	
	private void load(){
		Servers.start(this,
				Server.get().getPersonInfo(),
				new RxListener2<PersonInfo>()
				{
					@Override
					public void onNext(M<PersonInfo> m, String msg){
						if(m==null) return;
						if(null!=m){
							if(1==m.code){
								edUsername.setText(m.data.realName);
								edValidate.setText(m.data.idCard);
							}else{
								toast(m.msg);
							}
						}
					}
				});
	}
	
	@OnClick(R2.id.card_next_commit)
	public void onViewClicked(){
		//下一步
		if(checkCardStatus()){
			Intent intent=new Intent(this,ActAddCardInfo.class);
			intent.putExtra("name",userName);
			intent.putExtra("validate",validateStr);
			intent.putExtra("cardnum",cardNum);
			startActivity(intent);
			finish();
			//getProxy().startActivity(ActAddCardInfo.class);
		}
	}
	
	private boolean checkCardStatus(){
		userName=edUsername.getText().toString();
		validateStr=edValidate.getText().toString();
		cardNum=edCardHao.getText().toString();
		if(TextUtils.isEmpty(userName)){
			toast("姓名不能为空");
			return false;
		}else if(RegexUtil.isRealIDCard(validateStr)==false){
			toast("身份证不符合规范");
			return false;
		}else{
			if(TextUtils.isEmpty(cardNum)){
				toast("信用卡不能为空");
				return false;
			}else{
				if(cardNum.length()==16){
					//if(RegexUtil.isBankNumber(cardNum)==false){
					//	toast("信用卡不符合规范");
					//	return false;
					//}
				}else{
					toast("信用卡长度必须为16位");
					return false;
				}
			}
		}
		return true;
	}
}
