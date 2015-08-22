package com.processor;

import android.content.Context;

import com.data.greendao.utils.GreenDaoFactory;
import com.data.model.Data;
import com.data.model.LoadDataFinishedListenser;
import com.processor.itf.IProcessor;
import com.processor.itf.IView;
import com.processor.utils.LogUitls;


public class ProcessorImpl implements IProcessor , IProcessor.RefreshUIListener, LoadDataFinishedListenser{

	public IView mView = null;
	public LoadManager mLoadManager = null;
	public RefreshUIListener mRefreshUIListener;
	
	private static ProcessorImpl mPresenterImpl = null;
	
	public static ProcessorImpl getInstance(Context context){
		if (null == mPresenterImpl) {
			mPresenterImpl = new ProcessorImpl(context);
		}
		return mPresenterImpl;
	}
	
	public ProcessorImpl(Context context){
		Data.init(context);
		mLoadManager = new LoadManager(context);
		mLoadManager.setLoadDataFinishedListener(this);
	}
	
	public ProcessorImpl(IView view, Context context){
		Data.init(context);
		mView = view;
		mLoadManager = new LoadManager(context);
		mLoadManager.setLoadDataFinishedListener(this);
	}
	
	public ProcessorImpl(IView view, Context context,LoadManager loadmanager){
		Data.init(context);
		mView = view;
		mLoadManager = loadmanager;
		mLoadManager.setLoadDataFinishedListener(this);
	}

	public void setView(IView view) {
		mView = view;
	}

	@Override
	public IView getView() {
		return mView;
	}
	
	
	//-----------RefreshUIListener  UI调用Presenter申请load数据 -------------
	@Override
	public void onLoadData(int loadtype, int datatype, String url) {
		mLoadManager.loadData(loadtype, datatype, url);
	}
	@Override
	public void saveData(int status) {
		switch (status){
		case GreenDaoFactory.NONE://保存全部数据
			Data.saveData();
			break;
		case GreenDaoFactory.ACTIVITY:
			Data.saveActivites();
			break;
		case GreenDaoFactory.CHANNEL:
			Data.saveChannels();
			break;
		case GreenDaoFactory.TASK:
			Data.saveTasks();
			break;
		case GreenDaoFactory.USER:
			Data.saveUsers();
			break;
		}
		
	}

	//------- LoadDataFinishedListenser 加载完成后通知Presenter做相应的动作 ----------
	@Override
	public void LoadDataFinished(int loadtype, int dataStatus) {
		switch (dataStatus){
		case GreenDaoFactory.NONE:
			LogUitls.LogE("LoadDataFinish--- >init");
			mView.reFreshMenuUI();
			break;
		case GreenDaoFactory.ACTIVITY:
			LogUitls.LogE("LoadDataFinish--- >ACTIVITY");
			if (loadtype == LoadManager.LOAD_MORE || loadtype == LoadManager.LOAD_NEW) {
				mView.reFreshDashBoardChange(loadtype);
			} else {
				mView.reFreshContentUI();
			}
			break;
		/*case GreenDaoFactory.CHANNEL:
			mView.updateChannelsView();
			break;*/
		case GreenDaoFactory.TASK:
			LogUitls.LogE("LoadDataFinish--- >TASK");
			mView.reFreshContentUI();
			break;
		/*case GreenDaoFactory.USER:
			LogUitls.LogE("LoadDataFinish--- >User");
			mView.updateUsersView();
			break;*/
		}
	}

}
