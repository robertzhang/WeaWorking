package com.processor.itf;

public interface IProcessor {
	
	public void setView(IView view);
	
	public IView getView();
	
	//UI申请presenter加载数据, 添加数据
	public interface RefreshUIListener{
		/**
		 * UI要求Presenter加载数据
		 * @param loadtype
		 * @param datatype
		 * @param url
		 */
		public void onLoadData(int loadtype, int datatype, String url);
		
		public void saveData(int status);
		
		/*public void addChannel(Channel channel);//添加频道,包括修改频道信息，主要是name
		
		public void addChannelMember(List<User> userlist);//添加频道成员
		
		public void addTask(Task task);//添加任务
		 */		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
