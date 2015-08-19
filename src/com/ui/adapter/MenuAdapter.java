package com.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.data.greendao.Channel;
import com.data.greendao.User;
import com.data.model.Data;
import com.example.weaworking.R;
import com.processor.utils.LogUitls;
import com.ui.view.stickylistheaders.StickyListHeadersAdapter;

public class MenuAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer{

	private final Context mContext;
	private LayoutInflater mInflater;
	
	//数据源
	private String[] menu_header_array;
	private int[] mSectionIndices;
	private List<Channel> channels = new ArrayList<Channel>();
	private List<User> groups = new ArrayList<User>();
	private List<Object> data_source = new ArrayList<Object>();
	
	
	public MenuAdapter(Context context){
		mContext = context;
		mInflater = LayoutInflater.from(context);
		menu_header_array = mContext.getResources().getStringArray(R.array.menu_header_array);
		
		getSectionIndices();
	}
	
	public void getSectionIndices() {
		
		channels.clear();
		channels.addAll(Data.getChannels());
		groups.clear();
		groups.addAll(Data.getUsers());
		
        data_source.clear();
        data_source.addAll(channels);
        data_source.addAll(groups);
        
        int[] sections = {0, channels.size()};
        mSectionIndices = sections;
    } 
	
	@Override
	public int getCount() {
		return data_source.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data_source.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.menu_list_item_layout, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        if (data_source.get(position) instanceof Channel) {
        	holder.text.setText(((Channel)data_source.get(position)).getName());
        } else if (data_source.get(position) instanceof User) {
        	holder.text.setText(((User)data_source.get(position)).getName());
        }

        return convertView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.menu_list_item_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        
        if (position < channels.size()){
        	holder.text.setText(menu_header_array[0]);
        } else if (position < channels.size() + groups.size()){
        	holder.text.setText(menu_header_array[1]);
        }  else {
        	holder.text.setText("-- nothing --");
        }
        
        return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		long id;
		if (position < channels.size()){
        	id = 0;
        } else if (position < channels.size() + groups.size()){
        	id = 1;
        } else {
        	id = -1;
        }
		return id;
	}
	
	@Override
	public int getPositionForSection(int section) {
		 if (mSectionIndices.length == 0) {
	            return 0;
	        }
	        
	        if (section >= mSectionIndices.length) {
	            section = mSectionIndices.length - 1;
	        } else if (section < 0) {
	            section = 0;
	        }
	        return mSectionIndices[section];
	}

	@Override
	public int getSectionForPosition(int position) {
		for (int i = 0; i < mSectionIndices.length; i++) {
            if (position < mSectionIndices[i]) {
                return i - 1;
            }
        }
        return mSectionIndices.length - 1;
	}

	@Override
	public Object[] getSections() {
		return menu_header_array;
	}
	
	class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }
    
    //更新数据
    public void updateSectionIndices() {
    	getSectionIndices();
    	LogUitls.LogE(mSectionIndices+","+channels.size()+","+groups.size());
    }

}
