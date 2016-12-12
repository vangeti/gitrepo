package cgt.dop.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.model.MonitorModel;


@Repository
public class MonitorDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionfactory() {
		return sessionFactory;
	}

	public void setSessionfactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List<MonitorModel> getWidgetToolMapping() {
		Session session = sessionFactory.openSession();
		List<MonitorModel> list = new ArrayList<MonitorModel>();
		List results = session.createQuery("from MonitorModel").list();
		for (Iterator iterator = results.iterator(); iterator.hasNext();) {
			MonitorModel model = (MonitorModel) iterator.next();
			list.add(model);
		}
		session.close();
		return list;
	}

	public MonitorModel getWidgetDetail(String widgetname) {
		Session session = sessionFactory.openSession();
		return (MonitorModel) session
				.createQuery("from MonitorModel model where model.widgetname=" + "\'" + widgetname + "\'")
				.uniqueResult();
	}
}
