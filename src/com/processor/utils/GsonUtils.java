package com.processor.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.data.greendao.Activity;
import com.data.greendao.Channel;
import com.data.greendao.Note;
import com.data.greendao.Task;
import com.data.greendao.User;
import com.data.greendao.utils.GreenDaoFactory;
import com.data.model.ChannelUser;
import com.data.model.Data;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;


public class GsonUtils {

	/**
	 * 使用模板类型来构建方法，解析json数据
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> parseJsonToList(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonArray Jarray = parser.parse(jsonString).getAsJsonArray();
			for(JsonElement obj : Jarray ){
				T cse = gson.fromJson(obj , cls);
				list.add(cse);
			}

		} catch (Exception e) {
		}
		return list;
	}
	

	public static Map<Integer, List<Object>> parseJsons(String jsonString) {//加载全部数据的时候
		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		try {
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonObject Jobject = parser.parse(jsonString).getAsJsonObject();
			JsonObject currentuserJsonArray = Jobject.getAsJsonObject("current_user");
			JsonArray groupuserJsonArray = Jobject.getAsJsonArray("team_users");
			JsonArray usersJsonArray = Jobject.getAsJsonArray("users");
			JsonArray channelsJsonArray = Jobject.getAsJsonArray("channels");
			JsonArray channelusersJsonArray = Jobject.getAsJsonArray("channel_user");
			JsonArray activitiesJsonArray = Jobject.getAsJsonArray("feeds");
			JsonArray tasksJsonArray = Jobject.getAsJsonArray("tasks");
			JsonArray notesJsonArray = Jobject.getAsJsonArray("notes");
			
			if (currentuserJsonArray != null) {
//				map.put(GreenDaoFactory.CURRENT_USER, 
//					((List<Object>) gson.fromJson(currentuserJsonArray, new TypeToken<List<User>>(){}.getType())));
				Data.setCurrentUser(parseJson(currentuserJsonArray.toString(),User.class));
			}
			if (usersJsonArray != null) {
				map.put(GreenDaoFactory.ALLUSER, 
					((List<Object>) gson.fromJson(usersJsonArray, new TypeToken<List<User>>(){}.getType())));
			}
			if (groupuserJsonArray != null) {
			map.put(GreenDaoFactory.USER, 
				((List<Object>) gson.fromJson(groupuserJsonArray, new TypeToken<List<User>>(){}.getType())));
			}
			if (channelusersJsonArray != null) {
			map.put(GreenDaoFactory.CHANNEL_GROUP, 
				((List<Object>) gson.fromJson(channelusersJsonArray, new TypeToken<List<ChannelUser>>(){}.getType())));
			}
			if (channelsJsonArray != null) {
			map.put(GreenDaoFactory.CHANNEL, 
				((List<Object>) gson.fromJson(channelsJsonArray, new TypeToken<List<Channel>>(){}.getType())));
			}
			if (tasksJsonArray != null) {
			map.put(GreenDaoFactory.TASK,
				((List<Object>) gson.fromJson(tasksJsonArray, new TypeToken<List<Task>>(){}.getType())));
			}
			if (activitiesJsonArray != null) {
			map.put(GreenDaoFactory.ACTIVITY,
				((List<Object>) gson.fromJson(activitiesJsonArray, new TypeToken<List<Activity>>(){}.getType())));
			}
			if (notesJsonArray != null) {
			map.put(GreenDaoFactory.NOTE,
				((List<Object>) gson.fromJson(notesJsonArray, new TypeToken<List<Note>>(){}.getType())));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}


	public static <T> T parseJson(String jsonString, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, cls);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}
	
	public static <T> List<T> parseJsonToList2(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
		}
		return list;
	}
	public static List<Map<String, Object>> listKeyMaps(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString,
					new TypeToken<List<Map<String, Object>>>() {
			}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

}
