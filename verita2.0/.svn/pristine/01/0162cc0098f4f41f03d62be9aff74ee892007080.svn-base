package cgt.dop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cgt.dop.bean.TesterObservationBean;
import cgt.dop.dao.TesterObservationsDao;
import cgt.dop.model.TesterObservationModel;
import cgt.dop.service.TesterObservationsService;

@Service
@Transactional 
public class TesterObservationsServiceImpl implements TesterObservationsService {

	@Autowired
	TesterObservationsDao testerObservationsDao;
	
	@Override
	public String addTesterObservations(TesterObservationBean testerObservationBean) {
		
		TesterObservationModel testerObservationModel = testerObservationsDao.getTesterObservationsByWidgetFlag(testerObservationBean.getWidget());

		if(null == testerObservationModel){
			testerObservationModel = new TesterObservationModel();
			testerObservationModel.setUserName(testerObservationBean.getUserName());
			testerObservationModel.setBuName(testerObservationBean.getBuName());
			testerObservationModel.setProjectName(testerObservationBean.getProjectName());
			testerObservationModel.setWidget(testerObservationBean.getWidget());
			testerObservationModel.setComment(testerObservationBean.getComment());
			return testerObservationsDao.addTesterObservations(testerObservationModel);
		}
		else{
			testerObservationModel.setComment(testerObservationBean.getComment());
			return testerObservationsDao.updateTesterObservations(testerObservationModel);
		}
		
		
		
	}
	
	@Override
	public String getTesterObservationsByWidgetFlag(String widgetFlag){
		
		String comment = "";
		TesterObservationModel testerObservationModel = testerObservationsDao.getTesterObservationsByWidgetFlag(widgetFlag);	
		if(null == testerObservationModel)
		{
			return comment;
		}
		return testerObservationModel.getComment();
		
	}
	
	
	@Override
	public List<TesterObservationBean> getTesterObservations(){	
		
		List<TesterObservationBean> testerObservationBeans = new ArrayList<TesterObservationBean>();
		
		List<TesterObservationModel> testerObservations = testerObservationsDao.getTesterObservations();	
		System.out.println("getTesterObservations >>> testerObservations =="+testerObservations);
		
		for(TesterObservationModel testerObservationModel :  testerObservations){
			TesterObservationBean testerObservationBean = new TesterObservationBean();
			testerObservationBean.setWidget(testerObservationModel.getWidget());
			testerObservationBean.setComment(testerObservationModel.getComment());
			testerObservationBeans.add(testerObservationBean);
		}
		return testerObservationBeans;
		
	}
	


}
