package cgt.dop.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.model.JmeterAggrigateModel;
import cgt.dop.model.JmeterErrorsTimeModel;
import cgt.dop.model.JmeterProjectDetails;
import cgt.dop.model.JmeterResponsetimesModel;
import cgt.dop.model.JmeterTransactionDetailsModel;
import cgt.dop.model.LennoxProjectDetails;
import cgt.dop.model.NeoLoadStaticsGraphModel;
import cgt.dop.model.ProjectModel;

@Repository
public class JmeterDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionfactory() {
		return sessionFactory;
	}

	public void setSessionfactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public List getlastTwoDetails() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "select * from jmeter_aggrigate ORDER BY (buildNo) DESC  LIMIT 2";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterAggrigateModel.class);
		list = query.list();
		session.close();
		return list;
	}

	public List getTransactionDetailsForLastestBuild() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "SELECT * FROM jmeter_transactiondetails where buildNo=(select max(buildNo) from jmeter_transactiondetails)";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterTransactionDetailsModel.class);
		list = query.list();
		session.close();
		return list;
	}

	public List<LennoxProjectDetails> getTestIdInfoFromLennoxDB(String testId) {
		List<LennoxProjectDetails> list = new ArrayList<LennoxProjectDetails>();
		Session session = sessionFactory.openSession();
		String SQL_Query = "SELECT * FROM lennox_projectdetails where testname=?";
		Query query = session.createSQLQuery(SQL_Query);
		query.setParameter(0, testId);
		((SQLQuery) query).addEntity(LennoxProjectDetails.class);
		list = query.list();
		session.close();
		return list;
	}

	public List getProjectNameFromLennoxDB(String projectId) {
		List<ProjectModel> list = new ArrayList<ProjectModel>();
		Session session = sessionFactory.openSession();
		String SQL_Query = "SELECT * FROM project_master where project_id=?";
		Query query = session.createSQLQuery(SQL_Query);
		query.setParameter(0, projectId);
		((SQLQuery) query).addEntity(ProjectModel.class);
		list = query.list();
		session.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getTransactionPerSecondWithBuild() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		// String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		// o.user_name=? and o.user_pwd=?";
		String SQL_Query = "select * from jmeter_aggrigate ORDER BY buildNo DESC LIMIT 5";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterAggrigateModel.class);
		list = query.list();

		Collections.sort(list, new Comparator<JmeterAggrigateModel>() {

			public int compare(JmeterAggrigateModel o1, JmeterAggrigateModel o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getBuildNo(), o2.getBuildNo());
			}

		});
		session.close();
		System.out.println(list);
		return list;

	}

	public List getErrorTimeForLastestBuild() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "SELECT * FROM jmeter_errorstime where buildNo=(select max(buildNo) from jmeter_transactiondetails)";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterErrorsTimeModel.class);
		list = query.list();
		session.close();
		return list;
	}

	public List getResponseTimeLastestBuild() {

		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "SELECT * FROM jmeter_responsetimes where buildNo=(select max(buildNo) from jmeter_transactiondetails)";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterResponsetimesModel.class);
		list = query.list();
		session.close();
		return list;
	}

	public List getTransactionPerSecondWithBuildInflux() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		// String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		// o.user_name=? and o.user_pwd=?";
		String SQL_Query = "SELECT * FROM jmeter_projectdetails ORDER BY  buildNo Desc limit 5";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterProjectDetails.class);
		list = query.list();

		Collections.sort(list, new Comparator<JmeterProjectDetails>() {

			public int compare(JmeterProjectDetails o1, JmeterProjectDetails o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getBuildNo(), o2.getBuildNo());
			}

		});
		session.close();
		// System.out.println(list);
		return list;

	}
	
	
	public List getKPIDetailsFromInflux() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		// String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		// o.user_name=? and o.user_pwd=?";
		String SQL_Query = "SELECT * FROM jmeter_projectdetails ORDER BY  buildNo Desc limit 2";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterProjectDetails.class);
		list = query.list();

		Collections.sort(list, new Comparator<JmeterProjectDetails>() {

			public int compare(JmeterProjectDetails o1, JmeterProjectDetails o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getBuildNo(), o2.getBuildNo());
			}

		});
		session.close();
		//System.out.println(list);
		return list;

	}

	
	public List getLatestBuildFromInflux() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		// String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		// o.user_name=? and o.user_pwd=?";
		String SQL_Query = "SELECT * FROM jmeter_projectdetails ORDER BY  buildNo Desc limit 1";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterProjectDetails.class);
		list = query.list();

		Collections.sort(list, new Comparator<JmeterProjectDetails>() {

			public int compare(JmeterProjectDetails o1, JmeterProjectDetails o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getBuildNo(), o2.getBuildNo());
			}

		});
		session.close();
		// System.out.println(list);
		return list;

	}

	public List getTransactionDetailsForLastestBuildFromInflux() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "SELECT * FROM jmeter_projectdetails where buildNo=(select max(buildNo) from jmeter_projectdetails)";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterProjectDetails.class);
		list = query.list();
		session.close();
		return list;
	}

	public List getLastSixBuildsForInflux() {
		System.out.println("********************************");
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "SELECT * FROM lennox_projectdetails ORDER BY sno Desc limit 6";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(LennoxProjectDetails.class);
		list = query.list();
		session.close();
		return list;
	}

	public List getAllTestForInflux() {
		System.out.println("********************************");
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "SELECT * FROM lennox_projectdetails ORDER BY  sno Desc";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(LennoxProjectDetails.class);
		list = query.list();
		session.close();
		return list;
	}

}
