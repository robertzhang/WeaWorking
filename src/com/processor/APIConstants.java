package com.processor;

public class APIConstants {
	public static final String SERVER_IP 							= "http://172.16.0.250:3001/";
//	public static final String SERVER_IP 							= "http://192.168.0.105:3000/";
	
	public static final String AUTHORIZER_PATH 						= "/oauth/authorize";//认证地址
	public static final String TOKEN_PATH 							= "/oauth/token";//获取token的地址
	public static final String AUTHORIZER_SERVER_IP 				= "localhost:3001";
	public static final String RESOURCE_SERVER_IP 					= "localhost:3000";
	public static final String USER_PATH 							= "/api/v1/me";
	
	public static final String INIT_URL								= "api/v1/dashboard";
	public static final String USE_ID_FIND_TASKS_URL 				= "api/v1/tasks";
	public static final String TASK_NOTE_URL						= SERVER_IP+USE_ID_FIND_TASKS_URL+"/feeds?task_id=";
	public static final String TASK_URL								= SERVER_IP+USE_ID_FIND_TASKS_URL;
	public static final String ADD_NOTE								= TASK_URL +"/add_note";
	public static final String CHANGE_TASK_STATE					= TASK_URL +"/change_state";
	public static final String DELETE_TASK							= TASK_URL +"?task_id=";
	
}
