package com.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.data.greendao.Task;
import com.data.greendao.utils.GreenDaoFactory;
import com.example.weaworking.R;
import com.processor.APIConstants;
import com.processor.LoadManager;
import com.processor.ProcessorImpl;
import com.processor.itf.IProcessor.RefreshUIListener;
import com.processor.itf.IView;
import com.processor.utils.LogUitls;
import com.ui.adapter.TaskAdapter;
import com.ui.view.sweetalertdialog.SweetAlertDialog;

public class TaskActivity extends Activity implements IView, OnClickListener{
	ImageButton task_content_title_bar_left_ib;
	TextView task_content_title_bar_tv;
	
	TextView task_content_top_channel_name_tv;
	TextView task_content_top_task_name_tv;
	TextView task_content_top_assigned_user_name_tv;
	TextView task_content_top_state;
	TextView task_content_top_delete;
	
	EditText task_content_middle_et;
	ImageButton task_content_middle_ib;
	ProgressBar task_pb;
	
	ListView task_notes_lv;
	IView mLastView = null;
	private List<com.data.greendao.Activity> mList = new ArrayList<com.data.greendao.Activity>();
	private TaskAdapter mTaskAdapter;
	public Task task = null;
	public Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.task_content_layout);
		Long taskId = (Long) getIntent().getExtras().get("task_id");
		task = ProcessorImpl.getInstance(this).mLoadManager.getTaskAsId(taskId);
		ProcessorImpl.getInstance(this).mLoadManager.loadData(LoadManager.NONE, 
				GreenDaoFactory.ACTIVITY, APIConstants.TASK_NOTE_URL+taskId.intValue());
		mLastView = ProcessorImpl.getInstance(this).getView();
		ProcessorImpl.getInstance(this).setView(this);
		initView();
		
	}

	public void initView() {
		task_content_title_bar_left_ib = (ImageButton)findViewById(R.id.task_content_title_bar_left_ib);
		task_content_title_bar_tv = (TextView)findViewById(R.id.task_content_title_bar_tv);
		
		task_content_top_channel_name_tv = (TextView)findViewById(R.id.task_content_top_channel_name_tv);
		task_content_top_task_name_tv = (TextView)findViewById(R.id.task_content_top_task_name_tv);
		task_content_top_assigned_user_name_tv = (TextView)findViewById(R.id.task_content_top_assigned_user_name_tv);
		task_content_top_state = (TextView)findViewById(R.id.task_content_top_state);
		task_content_top_delete = (TextView)findViewById(R.id.task_content_top_delete);
		
		task_content_middle_ib = (ImageButton)findViewById(R.id.task_content_middle_ib);
		task_content_middle_et = (EditText)findViewById(R.id.task_content_middle_et);
//		task_pb = (ProgressBar)findViewById(R.id.task_pb);
		
		task_notes_lv = (ListView)findViewById(R.id.task_notes_lv);
		
		String state = null;
		if (task.getState().equals("todo")) {
			state = "待处理";
		} else if (task.getState().equals("doing")) {
			state = "处理中";
		} else if (task.getState().equals("done")) {
			state = "已完成";
		}
		task_content_top_state.setText(state);
		task_content_top_state.setOnClickListener(this);
		task_content_top_delete.setOnClickListener(this);
		task_content_top_channel_name_tv.setText(String.
				format(getResources().getString(R.string.task_content_channel_name), 
						ProcessorImpl.getInstance(this).mLoadManager.getChannelName(task.getChannel_id())));
		task_content_top_task_name_tv.setText(task.getTitle());
		task_content_top_assigned_user_name_tv.setText(String.
				format(getResources().getString(R.string.task_content_assigned_user_name), 
						ProcessorImpl.getInstance(this).mLoadManager.getUserName(task.getAssigned_user_id())));
		task_content_title_bar_left_ib.setOnClickListener(this);
		task_content_middle_ib.setOnClickListener(this);
		
		mTaskAdapter = new TaskAdapter(this,mList);
		task_notes_lv.setAdapter(mTaskAdapter);
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ProcessorImpl.getInstance(this).setView(mLastView);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()) {
		case R.id.task_content_title_bar_left_ib:
			finish();
			break;
		case R.id.task_content_middle_ib:
			//发送编辑的note
			String content = task_content_middle_et.getText().toString();
			if (content !=null && !content.isEmpty()) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("task_id", task.getId().toString());
				map.put("channel_id", task.getChannel_id().toString());
				map.put("content", content);
				ProcessorImpl.getInstance(context).mLoadManager.postNetData(LoadManager.NONE,
						GreenDaoFactory.ACTIVITY, APIConstants.ADD_NOTE, map, "添加进展");
				task_content_middle_et.setText("");
			} else {
				Toast.makeText(this, "不能发送空的内容！", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.task_content_top_state:
			dialog(task.getState());
			break;
		case R.id.task_content_top_delete:
//			Map<String,String> map = new HashMap<String,String>();
//			map.put("task_id", task.getId().toString());
			sweetAlertDialog();
			break;
		}
	}

	
	private void dialog(String str){ 
		//{"移到待处理", "移到处理中", "移到已完成"};
		final String[] items = {"移到待处理", "移到处理中", "移到已完成"};
		final String[] state = {"todo","doing","done"};
		int position = 0;
		if (str.equals("todo")) {
			position = 0;
		} else if (str.equals("doing")) {
			position = 1;
		} else if (str.equals("done")) {
			position = 2;
		}
		AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器  
		builder.setTitle("设置任务状态"); //设置标题  
		builder.setSingleChoiceItems(items,position,new DialogInterface.OnClickListener() {  
			@Override  
			public void onClick(DialogInterface dialog, int which) {
				task_content_top_state.setText(items[which].substring(2));
				task.setState(state[which]);
				Map<String,String> map = new HashMap<String,String>();
				map.put("task_id", task.getId().toString());
				map.put("state", task.getState());
				ProcessorImpl.getInstance(context).mLoadManager.postNetData(LoadManager.NONE,
						GreenDaoFactory.ACTIVITY, APIConstants.CHANGE_TASK_STATE, map, "修改任务状态");
				dialog.dismiss();
			}  
		});  
		builder.create().show();  
	}  

	
	public void sweetAlertDialog(){
		new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
		.setTitleText("你确定要删除?")
		.setContentText("删除后将无法恢复！")
		.setCancelText("取消")
		.setConfirmText("确定")
		.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
			public void onClick(SweetAlertDialog sweetAlertDialog) {
				long task_id = task.getId();
				ProcessorImpl.getInstance(context).mLoadManager.deleteNetData(LoadManager.NONE,
						GreenDaoFactory.ACTIVITY, APIConstants.DELETE_TASK+task_id, task_id, "修改任务状态");
				sweetAlertDialog.cancel();
				finish();
				/*sweetAlertDialog
                .setTitleText("已删除!")
                .setContentText("已成功移除记录！")
                .setConfirmText("确定")
                .setConfirmClickListener(null)
                .showCancelButton(false)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);*/
			}
		})
		.showCancelButton(true)
		.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
			@Override
			public void onClick(SweetAlertDialog sDialog) {
				sDialog.cancel();
			}
		}).show();
	}
	
	@Override
	public void reFreshMenuUI() {
	}

	@Override
	public void reFreshContentUI() {
		mList.clear();
		mList.addAll(ProcessorImpl.getInstance(this).mLoadManager.getTaskNotes());
		mTaskAdapter.notifyDataSetChanged();
	}

	@Override
	public void reFreshDashBoardChange(int loadtype) {
	}

	@Override
	public void setRefreshUIListener(RefreshUIListener listener) {
	}
}
