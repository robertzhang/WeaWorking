package com.processor.itf;

import org.json.JSONArray;
import com.data.model.LoadDataFinishedListenser;
/*
 * ILoadData用来加载数据的接口，主要包括：Net和Local的数据。
 * 
 * 1、NetData的处理包括：从Server获取json数据；解析json数据；将json数据存储；通知presenter刷新ui
 * 
 * 2、LoadData的处理：就是从数据库获取数据存入缓存
 * 
 */

public interface ILoadData {

	/**
	 * 加载数据
	 * @param loadtype加载类型。init初始化；load_new跟新数据；load_more加载更多
	 * @param datatype数据类型。activity，channel，task，user等，定义在GreenDaoFactory
	 * @param url访问数据的API地址
	 */
	public void loadData(int loadtype, int datatype, String url);
	
	
	/**
	 * 从网络获取json数据
	 */
//	public String getNetData(String url);
	public JSONArray getNetData(int loadtype, int datatype, String url);
	
	/**
	 * 处理网络数据，json数据的解析
	 */
	public void parseNetData(int loadtype, int datatype, String json);
	
	
	/**
	 * 从数据库获取数据
	 */
	public void getLoaclData();
	
	/**
	 * 设置数据加载完成的监听事件
	 * @param listener
	 */
	public void setLoadDataFinishedListener(LoadDataFinishedListenser listener);
	
	
}
