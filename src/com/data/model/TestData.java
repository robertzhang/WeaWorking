package com.data.model;

public class TestData {

	public static String Users(){
		return 
//				"{\"user\":" +
				"[" +
				"{\"id\":\"1\",\"email\":\"1@net263.com\",\"name\":\"张超-1\"},"+
				"{\"id\":\"2\",\"email\":\"2@net263.com\",\"name\":\"李超-2\"}," +
				"{\"id\":\"3\",\"email\":\"3@net263.com\",\"name\":\"王超-3\"}," +
				"{\"id\":\"4\",\"email\":\"4@net263.com\",\"name\":\"柳超-4\"}," +
				"{\"id\":\"5\",\"email\":\"5@net263.com\",\"name\":\"刘超-5\"}," +
				"{\"id\":\"6\",\"email\":\"6@net263.com\",\"name\":\"宜超-6\"}," +
				"{\"id\":\"7\",\"email\":\"7@net263.com\",\"name\":\"牛超-7\"}," +
				"{\"id\":\"8\",\"email\":\"8@net263.com\",\"name\":\"沈超-8\"}" +
				"]" 
//				+"}"
				;
	}
	public static String Channels(){
		return 
//				"{\"channel\":" +
				"[" +
				"{\"id\":\"1\",\"privacy\":\"private\",\"name\":\"channel-1\"},"+
				"{\"id\":\"2\",\"privacy\":\"private\",\"name\":\"channel-2\"}," +
				"{\"id\":\"3\",\"privacy\":\"private\",\"name\":\"channel-3\"}," +
				"{\"id\":\"4\",\"privacy\":\"private\",\"name\":\"channel-4\"}," +
				"{\"id\":\"5\",\"privacy\":\"public\",\"name\":\"channel-5\"}," +
				"{\"id\":\"6\",\"privacy\":\"public\",\"name\":\"channel-6\"}," +
				"{\"id\":\"7\",\"privacy\":\"public\",\"name\":\"channel-7\"}," +
				"{\"id\":\"8\",\"privacy\":\"private\",\"name\":\"channel-8\"}" +
				"]" 
//				+"}"
				;
	}
	public static String Activites(){
		return "";
	}
	public static String Tasks(){
		return 
//				"{\"task\":" +
				"[" +
				"{\"id\":\"1\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-1\",\"state\":\"todo\",\"user_id\":\"1\",\"assigned_user_id\":\"3\",\"channel_id\":\"3\"},"+
				"{\"id\":\"2\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-2\",\"state\":\"todo\",\"user_id\":\"2\",\"assigned_user_id\":\"3\",\"channel_id\":\"1\"}," +
				"{\"id\":\"3\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-3\",\"state\":\"doing\",\"user_id\":\"1\",\"assigned_user_id\":\"1\",\"channel_id\":\"1\"}," +
				"{\"id\":\"4\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-4\",\"state\":\"doing\",\"user_id\":\"1\",\"assigned_user_id\":\"1\",\"channel_id\":\"1\"}," +
				"{\"id\":\"5\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-5\",\"state\":\"done\",\"user_id\":\"4\",\"assigned_user_id\":\"2\",\"channel_id\":\"3\"}," +
				"{\"id\":\"6\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-6\",\"state\":\"doing\",\"user_id\":\"2\",\"assigned_user_id\":\"4\",\"channel_id\":\"4\"}," +
				"{\"id\":\"7\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-7\",\"state\":\"todo\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"3\"}," +
				"{\"id\":\"8\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-8\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"9\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-9\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"10\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-10\",\"state\":\"todo\",\"user_id\":\"1\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"11\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-11\",\"state\":\"done\",\"user_id\":\"1\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"12\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-12\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"1\"}," +
				"{\"id\":\"13\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-13\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"5\"}," +
				"{\"id\":\"14\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-14\",\"state\":\"doing\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"6\"}," +
				"{\"id\":\"15\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-15\",\"state\":\"todo\",\"user_id\":\"5\",\"assigned_user_id\":\"1\",\"channel_id\":\"7\"}," +
				"{\"id\":\"16\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-16\",\"state\":\"doing\",\"user_id\":\"1\",\"assigned_user_id\":\"1\",\"channel_id\":\"8\"}," +
				"{\"id\":\"17\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-17\",\"state\":\"todo\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"18\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-18\",\"state\":\"doing\",\"user_id\":\"2\",\"assigned_user_id\":\"2\",\"channel_id\":\"3\"}," +
				"{\"id\":\"19\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-19\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"20\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-20\",\"state\":\"doing\",\"user_id\":\"1\",\"assigned_user_id\":\"4\",\"channel_id\":\"1\"}," +
				"{\"id\":\"21\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-21\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"3\"}," +
				"{\"id\":\"22\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-22\",\"state\":\"doing\",\"user_id\":\"6\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"23\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-23\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"2\",\"channel_id\":\"2\"}," +
				"{\"id\":\"24\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-24\",\"state\":\"todo\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}" +
				"]" 
//				+"}"
				;
	}
	
	
	public static String init(){
		return "{" +
				"\"user\":[" +
				"{\"id\":\"1\",\"email\":\"1@net263.com\",\"name\":\"张超-1\"},"+
				"{\"id\":\"2\",\"email\":\"2@net263.com\",\"name\":\"李超-2\"}," +
				"{\"id\":\"3\",\"email\":\"3@net263.com\",\"name\":\"王超-3\"}," +
				"{\"id\":\"4\",\"email\":\"4@net263.com\",\"name\":\"柳超-4\"}," +
				"{\"id\":\"5\",\"email\":\"5@net263.com\",\"name\":\"刘超-5\"}," +
				"{\"id\":\"6\",\"email\":\"6@net263.com\",\"name\":\"宜超-6\"}," +
				"{\"id\":\"7\",\"email\":\"7@net263.com\",\"name\":\"牛超-7\"}," +
				"{\"id\":\"8\",\"email\":\"8@net263.com\",\"name\":\"沈超-8\"}]" +
				",\"channel\":[" +
				"{\"id\":\"1\",\"privacy\":\"private\",\"name\":\"channel-1\"},"+
				"{\"id\":\"2\",\"privacy\":\"private\",\"name\":\"channel-2\"}," +
				"{\"id\":\"3\",\"privacy\":\"private\",\"name\":\"channel-3\"}," +
				"{\"id\":\"4\",\"privacy\":\"private\",\"name\":\"channel-4\"}," +
				"{\"id\":\"5\",\"privacy\":\"public\",\"name\":\"channel-5\"}," +
				"{\"id\":\"6\",\"privacy\":\"public\",\"name\":\"channel-6\"}," +
				"{\"id\":\"7\",\"privacy\":\"public\",\"name\":\"channel-7\"}," +
				"{\"id\":\"8\",\"privacy\":\"private\",\"name\":\"channel-8\"}]" +
				",\"task\":[" +
				"{\"id\":\"1\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-1\",\"state\":\"todo\",\"user_id\":\"1\",\"assigned_user_id\":\"3\",\"channel_id\":\"3\"},"+
				"{\"id\":\"2\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-2\",\"state\":\"todo\",\"user_id\":\"2\",\"assigned_user_id\":\"3\",\"channel_id\":\"1\"}," +
				"{\"id\":\"3\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-3\",\"state\":\"doing\",\"user_id\":\"1\",\"assigned_user_id\":\"1\",\"channel_id\":\"1\"}," +
				"{\"id\":\"4\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-4\",\"state\":\"doing\",\"user_id\":\"1\",\"assigned_user_id\":\"1\",\"channel_id\":\"1\"}," +
				"{\"id\":\"5\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-5\",\"state\":\"done\",\"user_id\":\"4\",\"assigned_user_id\":\"2\",\"channel_id\":\"3\"}," +
				"{\"id\":\"6\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-6\",\"state\":\"doing\",\"user_id\":\"2\",\"assigned_user_id\":\"4\",\"channel_id\":\"4\"}," +
				"{\"id\":\"7\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-7\",\"state\":\"todo\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"3\"}," +
				"{\"id\":\"8\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-8\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"9\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-9\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"10\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-10\",\"state\":\"todo\",\"user_id\":\"1\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"11\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-11\",\"state\":\"done\",\"user_id\":\"1\",\"assigned_user_id\":\"1\",\"channel_id\":\"2\"}," +
				"{\"id\":\"12\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-12\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"1\"}," +
				"{\"id\":\"13\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-13\",\"state\":\"done\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"5\"}," +
				"{\"id\":\"14\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-14\",\"state\":\"doing\",\"user_id\":\"2\",\"assigned_user_id\":\"1\",\"channel_id\":\"6\"}," +
				"{\"id\":\"15\",\"due_at\":\"2015-07-22 01:05:03\",\"title\":\"T-15\",\"state\":\"todo\",\"user_id\":\"5\",\"assigned_user_id\":\"1\",\"channel_id\":\"7\"}]" +
				"}";
	}
}
