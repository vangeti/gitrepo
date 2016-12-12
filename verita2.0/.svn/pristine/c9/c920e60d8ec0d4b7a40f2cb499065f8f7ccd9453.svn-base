package cgt.dop.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.bean.TesterObservationBean;
import cgt.dop.dao.TesterObservationsDao;
import cgt.dop.model.TesterObservationModel;

@Repository
public class TesterObservationsDaoImpl implements TesterObservationsDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public String addTesterObservations(TesterObservationModel testerObservationModel) {
		System.out.println("testerObservationModel addTesterObservations ======================="+testerObservationModel);
		String result = null;
		try {
			Session session = sessionFactory.openSession();
			session.save(testerObservationModel);
			result = "{\"result\":\"success\"}";
		} catch (Exception e) {
			result = "{\"result\":\"fail\"}";
		}
		return result;
	}
	

	@Override
	public String updateTesterObservations(TesterObservationModel testerObservationModel) {
		System.out.println("testerObservationModel updateTesterObservations ======================="+testerObservationModel);
		String result = null;
		try {
			Session session = sessionFactory.openSession();
			org.hibernate.Transaction tx=session.beginTransaction();
			session.merge(testerObservationModel);
			tx.commit();
			session.close();
			result = "{\"result\":\"success\"}";
		} catch (Exception e) {
			result = "{\"result\":\"fail\"}";
		}
		return result;
	}

	public TesterObservationModel getTesterObservationsByWidgetFlag(String widgetFlag) {

		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("FROM TesterObservationModel WHERE widget= :widgetFlag ");
			query.setParameter("widgetFlag", widgetFlag);
			TesterObservationModel testerObservationModel = (TesterObservationModel) query.list().get(0);
			return testerObservationModel;
		} catch (Exception e) {
			return null;
		}

	}
	
	public List<TesterObservationModel> getTesterObservations() {

		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("FROM TesterObservationModel");
			List<TesterObservationModel> testerObservations =  (List<TesterObservationModel>)query.list();
			System.out.println("testerObservations >>>> getTesterObservations"+testerObservations);
			return testerObservations;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<TesterObservationModel>();
		}

	}
	
	

}
