package app.card.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import app.card.R;
import app.card.R2;
import app.card.api.RxListener2;
import app.card.api.Server;
import app.card.api.Servers;
import app.card.base.BaseTitleActivity;
import app.card.model.Area;
import app.card.model.M;
import app.card.ui.sort.*;
import app.card.view.KmRecyclerView;
import butterknife.BindView;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui
 * @description: 地区选择
 * @date: 2018/12/3 0003  
 * @time: 下午 2:42
 */
public class ActArea extends BaseTitleActivity
{
	@BindView(R2.id.rv_area)
	KmRecyclerView rvArea;
	@BindView(R2.id.sideBar)
	SideBar sideBar;
	public static final String KEY_ADDRESS_NAME="city_address";
	public static final String KEY_ADDRESS_ID="city_id";
	private SortAdapter adapter;
	List<SortModel> sourceDateList=new ArrayList<>();
	LinearLayoutManager manager;
	private SortModel sortModel;
	
	@Override
	protected int getLayoutId(){
		return R.layout.act_area;
	}
	
	@Override
	protected void onCreate(@Nullable Bundle bundle){
		super.onCreate(bundle);
		setTitle("地区选择");
		setRightText("完成");
		initSortData();
		//加载地区
		loadArea();
	}
	
	//完成
	@Override
	protected void onClickRight(){
		super.onClickRight();
		if(null!=sortModel){
			setResult(RESULT_OK, new Intent().putExtra(KEY_ADDRESS_NAME, sortModel.name)
					.putExtra(KEY_ADDRESS_ID, sortModel.cityId));
		}
		finish();
	}
	//public static Intent intent(){
	//	Intent intent=new Intent();
	//	intent.setClassName(BuildConfig.APPLICATION_ID, ActArea.class.getName());
	//	return intent;
	//}
	
	public static SortModel parseResult(Intent data){
		String addressId=null;
		String addressName=null;
		if(data!=null){
			addressId=data.getStringExtra(KEY_ADDRESS_ID);
			addressName=data.getStringExtra(KEY_ADDRESS_NAME);
		}
		SortModel sortModel=new SortModel();
		sortModel.name=addressName;
		sortModel.cityId=addressId;
		return sortModel;
	}
	
	private void initSortData(){
		//设置右侧SideBar触摸监听
		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener()
		{
			@Override
			public void onTouchingLetterChanged(String s){
				//该字母首次出现的位置
				int position=adapter.getPositionForSection(s.charAt(0));
				if(position!=-1) manager.scrollToPositionWithOffset(position, 0);
			}
		});
		// 根据a-z进行排序源数据
		//Collections.sort(SourceDateList, pinyinComparator);
		//RecyclerView
		manager=new LinearLayoutManager(this);
		manager.setOrientation(LinearLayoutManager.VERTICAL);
		rvArea.setLayoutManager(manager);
		adapter=new SortAdapter(this, sourceDateList);
		rvArea.setAdapter(adapter);
		adapter.setOnItemClickListener(new SortAdapter.OnItemClickListener()
		{
			@Override
			public void onItemClick(View view, int position){
				sortModel=(SortModel)adapter.getItem(position);
				for(int i=0; i<sourceDateList.size(); i++){
					if(position==i) sourceDateList.get(i).type="1";
					else sourceDateList.get(i).type="0";
				}
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	private void loadArea(){
		Servers.start(ActArea.this, Server.get().getCardArea(), new RxListener2<Area>()
		{
			@Override
			public void onNext(M<Area> m, String msg){
				if(m==null) return;
				if(null!=m){
					if(1==m.code){
						if(m.data!=null) AreaList.setAreaList(sourceDateList, m.data);
						adapter.notifyDataSetChanged();
					}else{
						toast(msg);
					}
				}
			}
		});
	}
}
