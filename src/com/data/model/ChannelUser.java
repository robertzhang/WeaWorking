package com.data.model;

import java.util.List;
import com.data.greendao.User;
/*
 * 用来存放频道对应的用户组
 */
public class ChannelUser {
	int channel_id;
	List<User> group;
	
	public ChannelUser() {
    }
	
	public void setChannel_Id(Integer channel_id) {
		this.channel_id = channel_id;
	}
	public int getChannel_Id() {
		return channel_id;
	}
	public void setGroup(List<User> group) {
		this.group = group;
	}
	public List<User> getGroup() {
		return group;
	}
	public String toString(){
		return "channel_id:"+channel_id+"; group:"+ group;
	}
}
