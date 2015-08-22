package com.processor.itf;

import com.processor.itf.IProcessor.RefreshUIListener;

/*
 * Presenter调用View的刷新接口
 */
public interface IView {
	
	public void reFreshMenuUI();
	
	public void reFreshContentUI();
	
	public void reFreshDashBoardChange(int loadtype); //用于更新DashBoard动态页面的数据
	
	/*public void updateChannelsView();
	
	public void updateUsersView();
	
	public void updateTasksView();
	
	public void updateActivitesView();*/
	
	public void setRefreshUIListener(RefreshUIListener listener);
	
}
