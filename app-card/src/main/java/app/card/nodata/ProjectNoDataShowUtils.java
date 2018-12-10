package app.card.nodata;

import android.view.View;

/**
 * @author: ${bruce}
 * @project: ZhiJinInvest
 * @package: com.kameng.zhijininvest.common
 * @description:  没有数据公共类
 * @date: 2018/10/31 0031  
 * @time: 下午 2:24
 */
public class ProjectNoDataShowUtils
{
	/**
	 单例
	 */
	private static volatile ProjectNoDataShowUtils mProjectDataIsNull;
	
	/**
	 获取单例
	 */
	public static ProjectNoDataShowUtils getInstance(){
		if(mProjectDataIsNull==null){
			synchronized(ProjectNoDataShowUtils.class){
				if(mProjectDataIsNull==null) mProjectDataIsNull=new ProjectNoDataShowUtils();
			}
		}
		return mProjectDataIsNull;
	}
	
	public void projectGetDataStatus(ProjectNoDataLayout mProjectNoDataRelativeLayout){
		mProjectNoDataRelativeLayout.setProjectRelativeLayoutShow(View.GONE);
	}
	
	/**
	 默认显示
	 */
	public void projectDefaultNoData(ProjectNoDataLayout mProjectNoDataRelativeLayout,
			ProjectDataTypeMode projectDataTypeMode, String textStr){
		if(mProjectNoDataRelativeLayout!=null){
			//默认显示无内容图标
			mProjectNoDataRelativeLayout.setVisibility(View.VISIBLE);
			mProjectNoDataRelativeLayout.setProjectRelativeLayoutShow(View.VISIBLE);
			if(projectDataTypeMode==ProjectDataTypeMode.MESSAGE){
				mProjectNoDataRelativeLayout.setRigthViewTypeMode(
						ProjectNoDataLayout.IvNoDataTypeMode.NOMESSAGE, textStr);
			}
			//else if (ProjectDataTypeMode.MEMBER == projectDataTypeMode) {//无公司成员
			//    mProjectNoDataRelativeLayout.setRigthViewTypeMode(ProjectNoDataRelativeLayout.IvNoDataTypeMode.NOCOMPANYMEMBER);
			// }
		}
	}
	
	/**
	 @author lt
	 */
	public enum ProjectDataTypeMode
	{
		/**
		 无消息
		 */
		MESSAGE,
	}
}
