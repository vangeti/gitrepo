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

import cgt.dop.model.DashboardUserWidgetMapping;
import cgt.dop.model.JmeterAggrigateModel;
import cgt.dop.model.JmeterErrorsTimeModel;
import cgt.dop.model.JmeterProjectDetails;
import cgt.dop.model.JmeterResponsetimesModel;
import cgt.dop.model.JmeterTransactionDetailsModel;
import cgt.dop.model.NeoLoadStaticsGraphModel;

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

	public List getTransactionDetailsForLastestBuildFromInflux(){
		List list=new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "SELECT * FROM jmeter_projectdetails where buildNo=(select max(buildNo) from jmeter_projectdetails)";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterProjectDetails.class);
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

	public List getTransactionPerSecondWithBuildInflux() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		// String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		// o.user_name=? and o.user_pwd=?";
		String SQL_Query = "SELECT * FROM jmeter_projectdetails ORDER BY  sno Desc limit 5";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterProjectDetails.class);
		list = query.list();
		
		Collections.sort(list, new Comparator<JmeterProjectDetails>() {

			public int compare(JmeterProjectDetails o1, JmeterProjectDetails o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getSno(), o2.getSno());
			}

		});
		session.close();
		//System.out.println(list);
		return list;

	}
	
	/*public List getLatestBuildFromInflux() {
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
		//System.out.println(list);
		return list;

	}*/
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

/*	public List getTransactionPerSecondWithBuildInflux() {
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

	}*/
	
	
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
	
	public List getKPIDetailsFromInflux(String testto,String testwith)
	{
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		// String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		// o.user_name=? and o.user_pwd=?";
		String SQL_Query = "SELECT * FROM jmeter_projectdetails where testname='"+testto+"' or testname='"+testwith+"'";
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
	
	public List getKPICompare(String testto,String testWith)
	{

		List list = new ArrayList();
		List all=new ArrayList();
		Session session = sessionFactory.openSession();
		// String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		// o.user_name=? and o.user_pwd=?";
		String SQL_Query = "SELECT * FROM jmeter_projectdetails where testname='"+testto+"'";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterProjectDetails.class);
		list = query.list();

		Collections.sort(list, new Comparator<JmeterProjectDetails>() {

			public int compare(JmeterProjectDetails o1, JmeterProjectDetails o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getBuildNo(), o2.getBuildNo());
			}

		});
		all.addAll(list);
		
		SQL_Query = "SELECT * FROM jmeter_projectdetails where testname='"+testWith+"'";
		query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(JmeterProjectDetails.class);
		list = query.list();

		Collections.sort(list, new Comparator<JmeterProjectDetails>() {

			public int compare(JmeterProjectDetails o1, JmeterProjectDetails o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getBuildNo(), o2.getBuildNo());
			}

		});
		all.addAll(list);
		session.close();
		
		//System.out.println(list);
		return all;

	
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
		  //System.out.println(list);
		  return list;

		 }
	
/*	public List getTransactionDetailsForLastestBuildFromInflux(){
		  List list=new ArrayList();
		  Session session = sessionFactory.openSession();
		  String SQL_Query = "SELECT * FROM jmeter_projectdetails where buildNo=(select max(buildNo) from jmeter_projectdetails)";
		  Query query = session.createSQLQuery(SQL_Query);
		  ((SQLQuery) query).addEntity(JmeterProjectDetails.class);
		  list = query.list();
		  session.close();
		  return list;
		 }*/
	
	public void setTransactionTrendvaluetoMysql(JmeterProjectDetails jmeterProjectDetails)
	{
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		//Query qry = session.createQuery("update jmeter_projectdetails p set p.transactionTrend=? where p.buildNo=? and p.projectId=?" );
		//qry.setParameter(0,jmeterProjectDetails.getTransactionTrend());
		//qry.setParameter(0,jmeterProjectDetails.getBuildNo());
		//qry.setParameter(0,jmeterProjectDetails.getProjectId());
		//int res = qry.executeUpdate();
		session.update(jmeterProjectDetails);
		//System.out.println("Updating the object in DB"+jmeterProjectDetails.getSno());
		 //session.save(dashboardUserWidgetMapping);
		 tx.commit();
		  session.close();
	}
	public void setResponseTrendvaluetoMysql(JmeterProjectDetails jmeterProjectDetails)
	{
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		//Query qry = session.createQuery("update jmeter_projectdetails p set p.transactionTrend=? where p.buildNo=? and p.projectId=?" );
		//qry.setParameter(0,jmeterProjectDetails.getTransactionTrend());
		//qry.setParameter(0,jmeterProjectDetails.getBuildNo());
		//qry.setParameter(0,jmeterProjectDetails.getProjectId());
		//int res = qry.executeUpdate();
		session.update(jmeterProjectDetails);
		//System.out.println("Updating the object in DB"+jmeterProjectDetails.getSno());
		 //session.save(dashboardUserWidgetMapping);
		 tx.commit();
		  session.close();
	}
	public void setErrorTrendvaluetoMysql(JmeterProjectDetails jmeterProjectDetails)
	{
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		//Query qry = session.createQuery("update jmeter_projectdetails p set p.transactionTrend=? where p.buildNo=? and p.projectId=?" );
		//qry.setParameter(0,jmeterProjectDetails.getTransactionTrend());
		//qry.setParameter(0,jmeterProjectDetails.getBuildNo());
		//qry.setParameter(0,jmeterProjectDetails.getProjectId());
		//int res = qry.executeUpdate();
		session.update(jmeterProjectDetails);
	//	System.out.println("Updating the object in DB"+jmeterProjectDetails.getSno());
		 //session.save(dashboardUserWidgetMapping);
		 tx.commit();
		  session.close();
	}
	public List getListOfTests()
	{
		 List list = new ArrayList();
		  Session session = sessionFactory.openSession();
		  // String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		  // o.user_name=? and o.user_pwd=?";
		  String SQL_Query = "SELECT * FROM jmeter_projectdetails ORDER BY  buildNo Desc limit 30";
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
	public List getTestsCompareResults(String testno)
	{
		 List list = new ArrayList();
		 Session session = sessionFactory.openSession();
		  // String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		  // o.user_name=? and o.user_pwd=?";
		  String SQL_Query = "SELECT * FROM jmeter_projectdetails where testname='"+testno+"'";
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
}
