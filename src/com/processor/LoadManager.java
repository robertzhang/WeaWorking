package com.processor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.data.greendao.Activity;
import com.data.greendao.Channel;
import com.data.greendao.Note;
import com.data.greendao.Task;
import com.data.greendao.User;
import com.data.greendao.utils.GreenDaoFactory;
import com.data.model.ChannelUser;
import com.data.model.Data;
import com.data.model.LoadDataFinishedListenser;
import com.processor.itf.ILoadData;
import com.processor.utils.LogUitls;
import com.processor.utils.NetworkUtils;

public class LoadManager implements ILoadData{
	
	public static final int NONE			= 0;
	public static final int LOAD_INIT		= 1;
	public static final int LOAD_NEW 		= 2; 
	public static final int LOAD_MORE 		= 3;
	
	public static int dashboard_page = 1;

	public LoadDataFinishedListenser mLoadDataFinishedListenser;
	public Context mContext;
	public RequestQueue mRequestQueue;
	
	public LoadManager(Context context){
		mContext = context;
		mRequestQueue = Volley.newRequestQueue(mContext);
	}
	public static int getDashBoardNextPage(){
		return ++dashboard_page;
	}
//Begin ------------------------ 实现ILoadData  ---------------------	
	public void loadData(int loadtype, int datatype, String url) {
		if (NetworkUtils.hasNetwork(mContext)){ //加载网络数据
			getNetData(loadtype, datatype, url);//Volley处理数据
		} else {
			getLoaclData();
		}
	}

	public void loadAsyncTask(int loadtype, int datatype, String url){
		new LoadAsyncTask(url).execute(loadtype, datatype);//启动一个异步线程，加载数据
	}
	
	//Volley处理get请求
	public JSONArray getNetData(final int loadtype, final int datatype, String url) {
		JSONArray json = null;
		//通过URL获取对应的数据
		StringRequest strRequest = new StringRequest(url,new Response.Listener<String>() {  
            @Override  
            public void onResponse(String response) {  
            	new ParseJsonTask(loadtype, datatype, response).execute();
            }  
        }, new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
                LogUitls.LogE("message:"+error.getMessage() +", error: "+ error);  
            }  
        }); 
		mRequestQueue.add(strRequest);
		return json;
	}
	//Volley处理post请求
	public void postNetData(final int loadtype, final int datatype, String url, final Map<String,String> map,final String succ){
		StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
				new Response.Listener<String>() {
			public void onResponse(String response) {
				new ParseJsonTask(loadtype, datatype, response).execute();
				Toast.makeText(mContext, succ+"成功", Toast.LENGTH_SHORT).show();
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				LogUitls.LogE("postNetData errormessage:"+ error.getMessage()+";error:"+error);
				Toast.makeText(mContext, succ+"失败", Toast.LENGTH_SHORT).show();
			}
		}) {
			protected Map<String, String> getParams() {
				return map;
			}
		};        
		mRequestQueue.add(stringRequest);
	}
	//Volley处理delete请求
	public void deleteNetData(final int loadtype, final int datatype, String url,  final long task_id,final String succ){
		StringRequest stringRequest = new StringRequest(Request.Method.DELETE,url,
				new Response.Listener<String>() {
			public void onResponse(String response) {
				String str = deleteTaskFromCacheData(task_id) ? "成功" : "失败";
				Toast.makeText(mContext, succ+str, Toast.LENGTH_SHORT).show();
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				LogUitls.LogE("postNetData errormessage:"+ error.getMessage()+";error:"+error);
				Toast.makeText(mContext, succ+"失败", Toast.LENGTH_SHORT).show();
			}
		});        
		mRequestQueue.add(stringRequest);
	} 
	
	@Override
	public void parseNetData(int loadtype, int datatype, String json) {
		switch (datatype){
		case GreenDaoFactory.NONE://重新加载全部数据
			Data.initParseJson(json);
			Data.saveData();
			break;
		case GreenDaoFactory.ACTIVITY:
			Data.activityParseJson(json, loadtype);
//			Data.saveActivites();
			break;
		case GreenDaoFactory.CHANNEL:
			Data.channelParseJson(json, loadtype);
			Data.saveChannels();
			break;
		case GreenDaoFactory.TASK:
			Data.taskParseJson(json, loadtype);
			Data.saveTasks();
			break;
		case GreenDaoFactory.USER:
			Data.userParseJson(json, loadtype);//解析并保存数据在缓存
			Data.saveUsers();//永久化数据
			break;
		}
	}
	
	@Override
	public void getLoaclData() {
		if (Data.isFirstLoad) {
			Data.getDataFromDB();
			Data.isFirstLoad = false;
		}
	}
//End ------------------------ 实现ILoadData  ---------------------		

	
	/*
	 * 异步任务用来加载数据
	 * @author robert.zhang
	 *
	 */
	public class LoadAsyncTask extends AsyncTask<Integer, Void, Integer>{
		String mUrl;
		
		public LoadAsyncTask(String url){
			mUrl = url;
		}
		
		@Override
		protected Integer doInBackground(Integer... arg0) {
			if (NetworkUtils.hasNetwork(mContext)){ //加载网络数据
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
				byte[] data = new byte[1024];  
				int len = 0;  
				URL url;
				try {
					url = new URL(mUrl);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
					InputStream inStream = conn.getInputStream();  
					while ((len = inStream.read(data)) != -1) {  
						outStream.write(data, 0, len);  
					}  
					inStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				} 
				String json = new String(outStream.toByteArray());//通过out.Stream.toByteArray获取到写的数据
				parseNetData(arg0[0], arg0[1], json);//解析json数据并保存；arg0[0]-loadtype,arg0[1]-datatype
			} else {//加载本地数据
				getLoaclData();
			}
			return arg0[1];
		}

		@Override
		protected void onPostExecute(Integer datatype) {
			super.onPostExecute(datatype);
			LogUitls.LogE("onPostExecute");
			mLoadDataFinishedListenser.LoadDataFinished(0,datatype);
		}
	}
	
	//解析json任务，开启了一个异步任务
	public class ParseJsonTask extends AsyncTask<Void, Void, Integer> {
		private int loadtype, datatype;
		private String response;
		
		public ParseJsonTask(int loadtype, int datatype, String response){
			this.loadtype = loadtype;
			this.datatype = datatype;
			this.response = response;
		}
		@Override
		protected Integer doInBackground(Void... arg0) {
			parseNetData(loadtype, datatype, response);
			return datatype;
		}
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			mLoadDataFinishedListenser.LoadDataFinished(loadtype, result);
		}
	}
	
	//---------------------- 从cache中获取数据 ------------------
	/**
	 * 通过user id获取task list
	 */
	public List<ArrayList<Task>> getTaskListAsUserID(int userId){
		List<ArrayList<Task>> list = null;
		if (NetworkUtils.hasNetwork(mContext)) {
			getNetData(NONE, GreenDaoFactory.TASK, APIConstants.TASK_URL+"?user_id="+userId);
		} else {
			list = Data.getTaskList(GreenDaoFactory.USER, userId);
		}
		return list;
	}
	/**
	 * 通过channel id获取task list
	 * @param channelId
	 * @return
	 */
	public List<ArrayList<Task>> getTaskListAsChannelID(int channelId){
		List<ArrayList<Task>> list = null;
		if (NetworkUtils.hasNetwork(mContext)) {
			getNetData(NONE, GreenDaoFactory.TASK, APIConstants.TASK_URL+"?channel_id="+channelId);
		} else {
			list = Data.getTaskList(GreenDaoFactory.CHANNEL, channelId);
		}
		return list;
	}
	
	public List<ArrayList<Task>> getTaskListAsID(int datatype, Object obj){
		List<ArrayList<Task>> list = null;
		if (obj instanceof User) {
			list = Data.getTaskList(datatype, ((User)obj).getId().intValue());
		} else {
			list = Data.getTaskList(datatype, ((Channel)obj).getId().intValue());
		}
		return list;
	}
	
	/**
	 * 通过user id 获取user name
	 * @param userId
	 * @return
	 */
	public String getUserName(int userId){
		String name = null;
		for (User item:Data.getAllUsers()){
			if (item.getId().intValue() == userId) {
				if(null != item.getName() && !item.getName().isEmpty()){
					name = item.getName();
				} else {
					name = item.getEmail();
				}
				break;
			}
		}
		return name;
	}
	/**
	 * 通过channel id获取channel name
	 * @param id
	 * @return
	 */
	public String getChannelName(int id){
		String name = null;
		for (Channel item:Data.getChannels()){
			if (item.getId().intValue() == id) {
				if(null != item.getName() && !item.getName().isEmpty()){
					name = item.getName();
				}
				break;
			}
		}
		return name;
	}
	public Task getTaskAsId(Long id){//通过任务ID获取任务
		Task task = null;
		for (Task item : Data.getTasks()){
			if (item.getId() == id) {
				task = item;
				break;
			}
		}
		return task;
	}
	public Note getNoteAsId(Long id){//通过note ID获取note
		Note note = null;
		for (Note item : Data.getNotes()) {
			if (item.getId() == id){
				note = item;
				break;
			}
		}
		return note;
	}
	public List<User> getUsersList() {
		return Data.getAllUsers();
	}
	/**
	 * 获取channel list
	 * @return
	 */
	public List<Channel> getChannelsList(){
		return Data.getChannels();
	}
	
	/**
	 * 返回频道名称数组
	 * @return
	 */
	public String[] getChannelNameArray(){
		List<Channel> list = Data.getChannels();
		String[] str = new String[list.size()];
		for(int i=0;i<list.size();i++){
			str[i] = list.get(i).getName();
		}
		return str;
	}
	/**
	 * 获取channel id对应的user list，返回值为Map<Long,List<User>>
	 */
	/*public Map<Long,List<User>> getMapWithChannelIDToFindUsersList(){
		Map<Long, List<User>> map = new  HashMap<Long, List<User>>();
		return map;
	}*/
	
	@Override
	public void setLoadDataFinishedListener(LoadDataFinishedListenser listener) {
		mLoadDataFinishedListenser = listener;
	}
	
	public List<Activity> getTaskNotes(){//返回activity的list
		return Data.getTaskNotes();
	}
	
	public List<Activity> getActivityList(){
		return Data.getActivites();
	}
	public String[] getChannelUsersName(Long id){//通过频道ID获取频道对应的用户组的用户名称数组
		List<User> list = null;
		String [] name ;
		for (ChannelUser item : Data.getChannelUsers()){
			if (item.getChannel_Id() == id.intValue()){
				list = item.getGroup();
				break;
			}
		}
		name = new String[list.size()];
		for(int i=0;i<list.size();i++) {
			name[i] = list.get(i).getName();
		}
		return name;
	}
	public Long getChannelUserId(Long channel_id , int position){//通过频道ID和用户组的索引获取用户ID
		Long id = null;
		for (ChannelUser item : Data.getChannelUsers()) {
			if(item.getChannel_Id() == channel_id){
				id = item.getGroup().get(position).getId();
			}
		}
		return id;
	}
	public User getCurrentUser(){//获取当前用户
		return Data.getCurrentUser();
	}
	
	public boolean deleteTaskFromCacheData(long task_id) {//通过任务ID删除缓存中的任务
		return Data.deleteTaskFromCacheData(task_id);
	}
}
