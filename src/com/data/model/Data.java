package com.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.data.greendao.Activity;
import com.data.greendao.Channel;
import com.data.greendao.Note;
import com.data.greendao.Task;
import com.data.greendao.User;
import com.data.greendao.utils.ActivityUtils;
import com.data.greendao.utils.ChannelUtils;
import com.data.greendao.utils.GreenDaoFactory;
import com.data.greendao.utils.NoteUtils;
import com.data.greendao.utils.TaskUtils;
import com.data.greendao.utils.UserUtils;
import com.processor.LoadManager;
import com.processor.utils.GsonUtils;
import com.processor.utils.LogUitls;

public class Data{//所有数据都是源于IData模型
	
	private static Context 			context;
	private static User 			current_user  = null;
	private static List<User> 	 	users 		  = new ArrayList<User>();
	private static List<Channel>  	channels	  = new ArrayList<Channel>();
	private static List<Activity> 	activites	  = new ArrayList<Activity>();
	private static List<Task> 	 	tasks		  = new ArrayList<Task>();
	private static List<Note>		notes  		  = new ArrayList<Note>();
	private static List<User>		alluser		  = new ArrayList<User>();//目前用户只属于一个团
	private static List<ChannelUser> channelusers = new ArrayList<ChannelUser>();
	public  static boolean 			isFirstLoad	  = true;
	
	public 	static List<Activity>  	task_activity_notes = new ArrayList<Activity>();
	public static void init(Context mContext){
		context = mContext;
	}
	
	
	/**
	 * 从本地数据库获取全部数据，保存在缓存中
	 * @param context
	 */
	public static void getDataFromDB(){
		List<User> userlist = ((UserUtils)GreenDaoFactory.builder(context, GreenDaoFactory.USER)).loadAll();
		List<Channel> channellist = ((ChannelUtils)GreenDaoFactory.builder(context, GreenDaoFactory.CHANNEL)).loadAll();
		List<Activity> activitylist = ((ActivityUtils)GreenDaoFactory.builder(context, GreenDaoFactory.ACTIVITY)).loadAll();
		List<Task> tasklist = ((TaskUtils)GreenDaoFactory.builder(context, GreenDaoFactory.TASK)).loadAll();
		List<Note> notelist = ((NoteUtils)GreenDaoFactory.builder(context, GreenDaoFactory.NOTE)).loadAll();
		
		if (null != userlist && !userlist.isEmpty()){
			users.clear();
			users.addAll(userlist);
		}
		
		if (null != channellist && !channellist.isEmpty()){
			channels.clear();
			channels.addAll(channellist);
		}
		
		if (null != activitylist && !activitylist.isEmpty()){
			activites.clear();
			activites.addAll(activitylist);
		}
		
		if (null != tasklist && !tasklist.isEmpty()){
			tasks.clear();
			tasks.addAll(tasklist);
		}
		
		if (null != notelist && !notelist.isEmpty()){
			notes.clear();
			notes.addAll(notelist);
		}
	}
	
	/**
	 * 通过网络获取数据
	 */
	//------------------- 解析json数据 -------------------------
	public static void initParseJson(String str, int status){
		Map<Integer, List<Object>> map = GsonUtils.parseJsons(str);

		if (null != map && !map.isEmpty()){
			for (Map.Entry<Integer, List<Object>> entry : map.entrySet()){
				switch (entry.getKey()){
				case GreenDaoFactory.ACTIVITY:
					List<Activity> list1 = new ArrayList<Activity>();
					for(Object item : entry.getValue()){
						list1.add((Activity)item);
					}
					updateActivitesCacheData(list1, status);
					break;
				case GreenDaoFactory.CHANNEL:
					List<Channel> list2 = new ArrayList<Channel>();
					for(Object item : entry.getValue()){
						list2.add((Channel)item);
					}
					updateChannelsCacheData(list2, status);
					break;
				case GreenDaoFactory.TASK:
					List<Task> list3 = new ArrayList<Task>();
					for(Object item : entry.getValue()){
						list3.add((Task)item);
					}
					updateTasksCacheData(list3, status);
					break;
				case GreenDaoFactory.USER:
					List<User> list4 = new ArrayList<User>();
					for(Object item : entry.getValue()){
						list4.add((User)item);
					}
					updateUsersCacheData(list4, status);
					break;
				case GreenDaoFactory.NOTE:
					List<Note> list5 = new ArrayList<Note>();
					for(Object item : entry.getValue()){
						list5.add((Note)item);
					}
					updateNotesCacheData(list5, status);
					break;
				case GreenDaoFactory.ALLUSER:
					List<User> list6 = new ArrayList<User>();
					for(Object item : entry.getValue()){
						list6.add((User)item);
					}
					updateGourpCacheData(list6, status);
					break;
				case GreenDaoFactory.CHANNEL_GROUP:
					List<ChannelUser> list7 = new ArrayList<ChannelUser>();
					for(Object item : entry.getValue()){
						list7.add((ChannelUser)item);
					}
					updateChannelUserCacheData(list7, status);
					break;
				}
			}
		}
	}
	public static void initParseJson(String str){
		initParseJson(str, LoadManager.LOAD_INIT);
	}
	
	public static void userParseJson(String str, int status){
		List<User> list = GsonUtils.parseJsonToList(str, User.class);
		updateUsersCacheData(list, status);
	}
	public static void channelParseJson(String str, int status){
		List<Channel> list = GsonUtils.parseJsonToList(str, Channel.class);
		updateChannelsCacheData(list, status);
	}
	public static void activityParseJson(String str , int status){
		initParseJson(str, status);
	}
	public static void taskParseJson(String str, int status){
		List<Task> list = null;
		list = GsonUtils.parseJsonToList(str, Task.class);
//		list = new ArrayList<Task>();
//		list.add(GsonUtils.parseJson(str, Task.class));
		updateTasksCacheData(list, status);
	}
	
	
	//-------------------- 保存数据到缓存 ----------------------
	public static void updateUsersCacheData(List<User> list, int status){
		if (null != list && !list.isEmpty()){
			if (status == LoadManager.LOAD_INIT){
				users.clear();
				users.addAll(list);
			} else if (status == LoadManager.LOAD_NEW){
				users.addAll(0, list);
			} else if (status == LoadManager.LOAD_MORE){
				users.addAll(list);
			}
		}
	}
	public static void updateActivitesCacheData(List<Activity> list, int status){
		if (null != list && !list.isEmpty()){
			if (status == LoadManager.LOAD_INIT){
				activites.clear();
				activites.addAll(list);
			} else {
				takeOutRepeatAcitivityItem(list, status);
			}
		}
	}
	public static void takeOutRepeatAcitivityItem(List<Activity> listload, int status) {//重复数据用来跟新，新数据用来添加
		boolean isNoExist = true;
		if (status == LoadManager.NONE) {
			task_activity_notes.clear();
			task_activity_notes.addAll(listload);
			return;
		}
		for (Activity loaditem : listload) {
			for (int i=0; i < activites.size(); i++) {
				if (activites.get(i).getId() == loaditem.getId()){
					activites.set(i, loaditem);
					isNoExist = false;
					break;
				}
			}
			if (isNoExist) {
				if (status == LoadManager.LOAD_NEW) {
					activites.add(0, loaditem);
				} else if (status == LoadManager.LOAD_MORE) {
					activites.add(loaditem);
				} 
			}
			isNoExist = true;
		}
		
	}
	public static void updateChannelsCacheData(List<Channel> list, int status){
		if (null != list && !list.isEmpty()){
			if (status == LoadManager.LOAD_INIT){
				channels.clear();
				channels.addAll(list);
			} else if (status == LoadManager.LOAD_NEW){
				channels.addAll(0, list);
			} else if (status == LoadManager.LOAD_MORE){
				channels.addAll(list);
			}
		}
	}
	public static void updateTasksCacheData(List<Task> list, int status){
		if (null != list && !list.isEmpty()){
			if (status == LoadManager.LOAD_INIT){
				tasks.clear();
				tasks.addAll(list);
			} else if (status == LoadManager.LOAD_NEW || LoadManager.NONE == status){//其他原因加载过来的数据也按新数据处理
				if (list != null)
					takeOutRepeatTaskItem(list);
//				tasks.addAll(0, list);
			} else if (status == LoadManager.LOAD_MORE){
				tasks.addAll(list);
			}
		}
	}
	public static boolean deleteTaskFromCacheData(long task_id) {
		boolean succ = false;
		for (Task item : tasks) {
			if (item.getId() == task_id) {
				tasks.remove(item);
				succ = true;
				break;
			}
		}
		return succ;
	}
	public static void takeOutRepeatTaskItem(List<Task> listload) {//重复数据用来跟新，新数据用来添加
		boolean isNoExist = true;
		for (Task loaditem : listload) {
			for (int i=0; i < tasks.size(); i++) {
				if (tasks.get(i).getId() == loaditem.getId()){
					tasks.set(i, loaditem);
					isNoExist = false;
					break;
				}
			}
			if (isNoExist) {
				tasks.add(0, loaditem);
			}
			isNoExist = true;
		}
	}
	public static void updateNotesCacheData(List<Note> list, int status) {
		if (null != list && !list.isEmpty()){
			if (status == LoadManager.LOAD_INIT){
				notes.clear();
				notes.addAll(list);
			} else if (status == LoadManager.LOAD_NEW || LoadManager.NONE == status){
				takeOutRepeatNoteItem(list);
//				notes.addAll(0, list);
			} else if (status == LoadManager.LOAD_MORE){
				notes.addAll(list);
			}
		}
	}
	public static void takeOutRepeatNoteItem(List<Note> listload) { //重复数据用来跟新，新数据用来添加
		boolean isNoExist = true;
		for (Note loaditem : listload) {
			for (int i=0; i < notes.size(); i++) {
				if (notes.get(i).getId() == loaditem.getId()){
					notes.set(i, loaditem);
					isNoExist = false;
					break;
				}
			}
			if (isNoExist) {
				notes.add(0, loaditem);
			}
			isNoExist = true;
		}
	}
	public static void updateGourpCacheData(List<User> list, int status) {
		if (null != list && !list.isEmpty()){
			if (status == LoadManager.LOAD_INIT){
				alluser.clear();
				alluser.addAll(list);
			} else if (status == LoadManager.LOAD_NEW || LoadManager.NONE == status){
				alluser.addAll(0, list);
			} else if (status == LoadManager.LOAD_MORE){
				alluser.addAll(list);
			}
		}
	}
	public static void updateChannelUserCacheData(List<ChannelUser> list, int status) {
		if (null != list && !list.isEmpty()){
			if (status == LoadManager.LOAD_INIT){
				channelusers.clear();
				channelusers.addAll(list);
			} else if (status == LoadManager.LOAD_NEW || LoadManager.NONE == status){
				channelusers.addAll(0, list);
			} else if (status == LoadManager.LOAD_MORE){
				channelusers.addAll(list);
			}
		}
	}
	public static boolean isUserInAllUserList(User user) {
		if (!alluser.isEmpty()) {
			for (User item : alluser) {
				if (user.getId() == item.getId()) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	//------------------ 保存数据到本地数据库 -------------------
	public static void saveData(){//保存全部数据
		saveUsers(users);
		saveChannels(channels);
		saveActivites(activites);
		saveTasks(tasks);
		saveNotes(notes);
	}
	public static void saveUsers(){
		saveUsers(users);
	}
	public static void saveUsers(List<User> list){
		((UserUtils)GreenDaoFactory.builder(context, GreenDaoFactory.USER)).deleteAll();
		((UserUtils)GreenDaoFactory.builder(context, GreenDaoFactory.USER)).saveByLists(list);
	}
	public static void saveChannels(){
		saveChannels(channels);
	}
	public static void saveChannels(List<Channel> list){
		((ChannelUtils)GreenDaoFactory.builder(context, GreenDaoFactory.CHANNEL)).deleteAll();
		((ChannelUtils)GreenDaoFactory.builder(context, GreenDaoFactory.CHANNEL)).saveByLists(list);
	}
	public static void saveActivites(){
		saveActivites(activites);
	}
	public static void saveActivites(List<Activity> list){
		((ActivityUtils)GreenDaoFactory.builder(context, GreenDaoFactory.ACTIVITY)).deleteAll();
		((ActivityUtils)GreenDaoFactory.builder(context, GreenDaoFactory.ACTIVITY)).saveByLists(list);
	}
	public static void saveTasks(){
		saveTasks(tasks);
	}
	public static void saveTasks(List<Task> list){
		((TaskUtils)GreenDaoFactory.builder(context, GreenDaoFactory.TASK)).deleteAll();
		((TaskUtils)GreenDaoFactory.builder(context, GreenDaoFactory.TASK)).saveByLists(list);
	}
	public static void saveNotes(){
		saveNotes(notes);
	}
	public static void saveNotes(List<Note> list){
		((NoteUtils)GreenDaoFactory.builder(context, GreenDaoFactory.NOTE)).deleteAll();
		((NoteUtils)GreenDaoFactory.builder(context, GreenDaoFactory.NOTE)).saveByLists(list);
	}
	
	//-------------------- get方法获取数据 ---------------------
	public static void setCurrentUser(User user){
		current_user = user;
	}
	public static User getCurrentUser(){
		return current_user;
	}
	public static List<User> getUsers(){
		return users;
	}
	public static List<Channel> getChannels(){
		return channels;
	}
	public static List<Activity> getActivites(){
		return activites;
	}
	public static List<Task> getTasks(){
		return tasks;
	}
	public static List<User> getAllUsers(){
		return alluser;
	}
	public static List<Note> getNotes(){
		return notes;
	}
	public static List<Activity> getTaskNotes(){
		return task_activity_notes;
	}
	public static List<ChannelUser> getChannelUsers(){
		return channelusers;
	}
	
	//----------------- get指定状态或的数据 ---------------------
	/**
	 * 通过List<Task>获取按状态分离开的Task list
	 * @param tasks
	 * @return
	 */
	public static List<ArrayList<Task>> getTaskListAsState(List<Task> tasks){
		List<ArrayList<Task>> list = new ArrayList<ArrayList<Task>>();
		list.add(new ArrayList<Task>());
		list.add(new ArrayList<Task>());
		list.add(new ArrayList<Task>());
		for(Task item : tasks){
			if (item.getState().equals("todo")){
				list.get(0).add(item);
			} else if (item.getState().equals("doing")) {
				list.get(1).add(item);
			} else if (item.getState().equals("done")) {
				list.get(2).add(item);
			}
		}
		return list;
	}
	/**
	 * 通过assigned_user_id 或者 channel_id查找List<Task>
	 * @param datatype
	 * @param id
	 * @return
	 */
	public static List<Task> getTaskListAsSomething(int datatype, int id){
		List<Task> list = new ArrayList<Task>();
		for (Task item : tasks) {
			if ((GreenDaoFactory.USER == datatype && item.getAssigned_user_id() == id) 
					|| (GreenDaoFactory.CHANNEL == datatype && item.getChannel_id() == id)) {
				list.add(item);
			}
		}
		return list;
	}
	public static List<ArrayList<Task>> getTaskList(int datatype, int id){
		return getTaskListAsState(getTaskListAsSomething(datatype, id));
	}
	
}
