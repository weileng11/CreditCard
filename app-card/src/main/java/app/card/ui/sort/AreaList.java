package app.card.ui.sort;

import java.util.List;
import app.card.model.Area;

/**
 * @author: ${bruce}
 * @project: CreditCard
 * @package: app.card.ui.sort
 * @description:
 * @date: 2018/12/3 0003  
 * @time: 下午 3:34
 */
public class AreaList
{
	public static  List<SortModel> setAreaList(List<SortModel> sourceDateList,Area area){
		for(int i=0; i<area.A.size(); i++){
			Area.ABean listBean=area.A.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sortModel);
		}
		
		for(int i=0; i<area.B.size(); i++){
			Area.BBean listBean=area.B.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.C.size(); i++){
			Area.CBean listBean=area.C.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.D.size(); i++){
			Area.DBean listBean=area.D.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.E.size(); i++){
			Area.EBean listBean=area.E.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.F.size(); i++){
			Area.FBean listBean=area.F.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.G.size(); i++){
			Area.GBean listBean=area.G.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.H.size(); i++){
			Area.HBean listBean=area.H.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.J.size(); i++){
			Area.JBean listBean=area.J.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		
		for(int i=0; i<area.K.size(); i++){
			Area.KBean listBean=area.K.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.L.size(); i++){
			Area.LBean listBean=area.L.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.M.size(); i++){
			Area.MBean listBean=area.M.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.N.size(); i++){
			Area.NBean listBean=area.N.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.P.size(); i++){
			Area.PBean listBean=area.P.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.Q.size(); i++){
			Area.QBean listBean=area.Q.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.R.size(); i++){
			Area.RBean listBean=area.R.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.S.size(); i++){
			Area.SBean listBean=area.S.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.T.size(); i++){
			Area.TBean listBean=area.T.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.W.size(); i++){
			Area.WBean listBean=area.W.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.X.size(); i++){
			Area.XBean listBean=area.X.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.Y.size(); i++){
			Area.YBean listBean=area.Y.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		for(int i=0; i<area.Z.size(); i++){
			Area.ZBean listBean=area.Z.get(i);
			SortModel sortModel=new SortModel();
			sortModel.name=listBean.name;
			sortModel.cityId=listBean.code;
			sortModel.letters=listBean.initial;
			sortModel.type="0";
			sourceDateList.add(sourceDateList.size(),sortModel);
		}
		
		return sourceDateList;
	}
	
}
