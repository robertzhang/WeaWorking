package com.ui.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.data.greendao.utils.GreenDaoFactory;
import com.example.weaworking.R;
import com.processor.APIConstants;
import com.processor.LoadManager;
import com.processor.ProcessorImpl;

public class AddTaskActivity extends Activity implements OnClickListener {
	
	private ImageButton add_task_title_bar_left_ib;
	private ImageButton add_task_title_bar_right_iv;
	private TextView add_task_name_et;
	private Spinner add_task_channel_sp;
	private Spinner add_task_assigned_user_sp;
	
	private String[] channelNameArray = null;
	private String[] userNameArray = null;
	private ArrayAdapter<String> channelAdapter;
	private ArrayAdapter<String> userAdapter;
	private Long channel_id;
	private Long user_id;
	private Context context;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_task_layout);
		context = this;
		add_task_title_bar_left_ib = (ImageButton)findViewById(R.id.add_task_title_bar_left_ib);
		add_task_title_bar_right_iv = (ImageButton)findViewById(R.id.add_task_title_bar_right_iv);
		add_task_title_bar_left_ib.setOnClickListener(this);
		add_task_title_bar_right_iv.setOnClickListener(this);
		
		add_task_name_et = (TextView)findViewById(R.id.add_task_name_et);
		add_task_channel_sp = (Spinner)findViewById(R.id.add_task_channel_sp);
		add_task_assigned_user_sp = (Spinner)findViewById(R.id.add_task_assigned_user_sp);
		
		add_task_channel_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				channel_id = ProcessorImpl.getInstance(getApplicationContext()).
						mLoadManager.getChannelsList().get(position).getId();
				//设置频道对应的成员spinner
				userNameArray = ProcessorImpl.getInstance(context).mLoadManager.getChannelUsersName(channel_id);
				userAdapter = new ArrayAdapter<String>(context,
						android.R.layout.simple_spinner_item, userNameArray);
				userAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
				add_task_assigned_user_sp.setAdapter(userAdapter);
			}
			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		add_task_assigned_user_sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				user_id = ProcessorImpl.getInstance(getApplicationContext()).
						mLoadManager.getChannelUserId(channel_id, position);
			}
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		//设置频道名称spinner
		channelNameArray = ProcessorImpl.getInstance(this).mLoadManager.getChannelNameArray();
		channelAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, channelNameArray);
		channelAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		add_task_channel_sp.setAdapter(channelAdapter);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add_task_title_bar_left_ib:
			finish();
			break;
		case R.id.add_task_title_bar_right_iv:
			// channel_id,user_id已有,上传操作
			String content = add_task_name_et.getText().toString();
			if (content != null && !content.isEmpty()){
				Map<String,String> map = new HashMap<String,String>();
				map.put("channel_id", channel_id.toString());
				map.put("assigned_user_id", user_id.toString());
				map.put("title", content);
				ProcessorImpl.getInstance(context).mLoadManager.postNetData(LoadManager.LOAD_NEW, 
						GreenDaoFactory.TASK, APIConstants.TASK_URL, map,"添加新任务");
				add_task_name_et.setText("");
			} else {
				Toast.makeText(context, "任务内容不能为空！", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
}
