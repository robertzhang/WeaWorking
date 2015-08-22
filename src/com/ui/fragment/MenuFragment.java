package com.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.data.greendao.Channel;
import com.data.greendao.Task;
import com.data.greendao.User;
import com.data.greendao.utils.GreenDaoFactory;
import com.example.weaworking.R;

import com.processor.LoadManager;
import com.processor.ProcessorImpl;
import com.processor.itf.IView;
import com.processor.itf.IProcessor.RefreshUIListener;
import com.processor.utils.ConstantUtils;
import com.processor.utils.LogUitls;
import com.ui.activity.MainActivity;
import com.ui.adapter.MenuAdapter;
import com.ui.view.stickylistheaders.ExpandableStickyListHeadersListView;
import com.ui.view.stickylistheaders.StickyListHeadersListView;
import com.ui.view.stickylistheaders.anim.ELAnimExecutor;
import com.ui.view.textdrawable.ColorGenerator;
import com.ui.view.textdrawable.TextDrawable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuFragment extends Fragment implements IView{

	public ExpandableStickyListHeadersListView navdrawer;
	private ImageView menu_user_iv;
	private TextView menu_user_name_tv;
	private TextView menu_user_email_tv;
	private TextView menu_out_sys;
	private TextDrawable td;

	public MenuAdapter menuAdapter;
	public ViewPagerFragment f = null;

	private Fragment mContent;

	public MenuFragment(){}
	public MenuFragment(Fragment content){
		mContent = content;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ProcessorImpl.getInstance(getActivity()).setView(this);
	}

	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_left_layout, null);

		initMenuLayout(view);
		setRefreshUIListener(ProcessorImpl.getInstance(getActivity()));
		return view;
	}
	
	public void onStart() {
		super.onStart();
		if (f != null)
			reFreshContentUI();
	}
	public void initMenuLayout(View view){
		menu_user_iv = (ImageView) view.findViewById(R.id.menu_user_iv);
		menu_user_name_tv = (TextView) view.findViewById(R.id.menu_user_name_tv);
		menu_user_email_tv = (TextView) view.findViewById(R.id.menu_user_email_tv);
		menu_out_sys = (TextView) view.findViewById(R.id.menu_out_sys);
		
		
		td = TextDrawable.builder().round().build("EM", ColorGenerator.DEFAULT.getColor("zhangchao"));
		menu_user_iv.setImageDrawable(td);

		menu_out_sys.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				System.exit(0);
			}
		});

		initESLHListView(view);//初始化ExpandableStickyListHeadersListView
	}
	/**
	 *初始化ExpandableStickyListHeadersListView
	 */
	public void initESLHListView(View view){
		navdrawer = (ExpandableStickyListHeadersListView) view.findViewById(R.id.navdrawer);
		navdrawer.setAnimExecutor(new ELAnimExecutor());//设置滑动动画
		navdrawer.setOnHeaderClickListener(new StickyListHeadersListView.OnHeaderClickListener() {
			@Override
			public void onHeaderClick(StickyListHeadersListView l, View header, int itemPosition, long headerId, boolean currentlySticky) {
				if(navdrawer.isHeaderCollapsed(headerId)){
					navdrawer.expand(headerId);
				}else {
					navdrawer.collapse(headerId);
				}
			}
		});

		menuAdapter = new MenuAdapter(getActivity());
		navdrawer.setAdapter(menuAdapter);

		navdrawer.setOnItemClickListener(
				new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Long id = 1L;
						List<ArrayList<Task>> list = null;
						if (menuAdapter.getItem(position) instanceof Channel){
							id = ((Channel)menuAdapter.getItem(position)).getId();
							list = ProcessorImpl.getInstance(getActivity()).mLoadManager
									.getTaskListAsChannelID(id.intValue());
						} else if (menuAdapter.getItem(position) instanceof User){
							id = ((User)menuAdapter.getItem(position)).getId();
							list = ProcessorImpl.getInstance(getActivity()).mLoadManager
									.getTaskListAsUserID(id.intValue());
						}
						
						f = new ViewPagerFragment(list, menuAdapter.getItem(position));
						switchFragment(f);
					}
				});
	}

	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment);
		}
	}

	public void setCurrentUserInfo(){
		User user = ProcessorImpl.getInstance(getActivity()).mLoadManager.getCurrentUser();
		menu_user_name_tv.setText(user.getName());
		menu_user_email_tv.setText(user.getEmail());
		String iconStr;
		if (ConstantUtils.isLetterFirst(user.getName())){
			iconStr = user.getName().toUpperCase().charAt(0)+"";
		} else {
			iconStr = user.getName().charAt(0)+"";
		}
		
		td = TextDrawable.builder().round().build(iconStr, ColorGenerator.DEFAULT.getColor(user.getName()));
		menu_user_iv.setImageDrawable(td);
	}
	
	//----------------------- IView接口的实现 ---------------------	
	@Override
	public void reFreshMenuUI() {
		LogUitls.LogE("menuFragment ----"+"reFreshMenuUI");
		menuAdapter.updateSectionIndices();
		menuAdapter.notifyDataSetChanged();
		setCurrentUserInfo();
		
		reFreshDashBoardChange(LoadManager.LOAD_INIT);//更新动态数据列表
	}
	
	@Override
	public void reFreshDashBoardChange(int loadtype) {
		((MainFragment)mContent).notifyChangeData(loadtype);
	}

	public void reFreshContentUI(){
		LogUitls.LogE("menuFragment ----"+"reFreshContentUI");
		int datatype = 0;
		if (f.mObject instanceof User) {
			datatype = GreenDaoFactory.USER;
		} else if (f.mObject instanceof Channel) {
			datatype = GreenDaoFactory.CHANNEL;
		}
		upadtaVPFragmentUI(datatype);
	}

	public void upadtaVPFragmentUI(int datatype) {
		f.upadtaVPFragmentUI(ProcessorImpl.
				getInstance(getActivity()).mLoadManager.getTaskListAsID(datatype, f.mObject));
	}

	@Override
	public void setRefreshUIListener(RefreshUIListener listener) {
		LogUitls.LogE(" menuFragment ----"+"refreshUI");
	}

}
