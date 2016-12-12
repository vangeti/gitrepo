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

import cgt.dop.bean.ApdexBean;
import cgt.dop.model.NeoLoadGraphsImages;
import cgt.dop.model.NeoLoadProject;
import cgt.dop.model.NeoLoadStaticsGraphModel;
import cgt.dop.model.NeoLoadTransactionDetails;
import cgt.dop.model.TestDetails;

@Repository
public class NeoLoadDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionfactory() {
		return sessionFactory;
	}

	public void setSessionfactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public List getgraph1Details() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		// String SQL_QUERY ="from NeoLoadStaticsGraphModel as graph where
		// o.user_name=? and o.user_pwd=?";
		String SQL_Query = "select * from neoload_statistics ORDER BY buildNo DESC LIMIT 5";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(NeoLoadStaticsGraphModel.class);
		list = query.list();
		
		Collections.sort(list, new Comparator<NeoLoadStaticsGraphModel>() {

			public int compare(NeoLoadStaticsGraphModel o1, NeoLoadStaticsGraphModel o2) {
				// TODO Auto-generated method stub
				return Integer.compare(o1.getBuildNo(), o2.getBuildNo());
			}

		});
		session.close();
		return list;

	}

	public List getImagesGraphs() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "select * from neoload_graphimages where buildNo in (select max(buildNo) from neoload_project);";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(NeoLoadGraphsImages.class);
		list = query.list();
		System.out.println("**********lenght of rows:" + list.size());
		session.close();
		return list;
	}

	public List getGraph2Details() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "select * from neoload_statistics ORDER BY buildNo DESC LIMIT 1";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(NeoLoadStaticsGraphModel.class);
		list = query.list();
		Collections.sort(list, new Comparator<NeoLoadStaticsGraphModel>() {

			public int compare(NeoLoadStaticsGraphModel o1, NeoLoadStaticsGraphModel o2) {
				// TODO Auto-generated method stub
				// return 0;
				return Integer.compare(o2.getBuildNo(), o1.getBuildNo());
			}

		});
		session.close();
		return list;

	}

	public List getSingleTransaction() {

		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		// String SQL_Query="select * from neoload_transactionsdetails where
		// buildNo in(select max(buildNo) from neoload_project )";
		String SQL_Query = "select *  from neoload_transactionsdetails where buildNo in(select max(buildNo) from neoload_project ) ";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(NeoLoadTransactionDetails.class);
		// query.add
		list = query.list();
		/*
		 * for(int i=0;i<list.size();i++) { NeoLoadTransactionDetails
		 * obj=(NeoLoadTransactionDetails) list.get(i);
		 * System.out.println("********"+obj.getTransactionName()); //
		 * System.out.println("Transaction Name;"
		 * +(NeoLoadTransactionDetails)list.get(0).); }
		 */
		/*
		 * Collections.sort(list, new Comparator<NeoLoadTransactionDetails>(){
		 * 
		 * @Override public int compare(NeoLoadStaticsGraphModel o1,
		 * NeoLoadStaticsGraphModel o2) { // TODO Auto-generated method stub
		 * //return 0; return Integer.compare(o2.getBuildNo(), o1.getBuildNo());
		 * }
		 * 
		 * });
		 */
		session.close();
		return list;

	}

	public List getErrorpercentile() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "select * from neoload_transactionsdetails where buildNo in(select max(buildNo) from neoload_project )";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(NeoLoadTransactionDetails.class);
		// query.add
		list = query.list();
		for (int i = 0; i < list.size(); i++) {
			NeoLoadTransactionDetails obj = (NeoLoadTransactionDetails) list.get(i);
			System.out.println("********" + obj.getTransactionName());
			// System.out.println("Transaction
			// Name;"+(NeoLoadTransactionDetails)list.get(0).);
		}
		/*
		 * Collections.sort(list, new Comparator<NeoLoadTransactionDetails>(){
		 * 
		 * @Override public int compare(NeoLoadStaticsGraphModel o1,
		 * NeoLoadStaticsGraphModel o2) { // TODO Auto-generated method stub
		 * //return 0; return Integer.compare(o2.getBuildNo(), o1.getBuildNo());
		 * }
		 * 
		 * });
		 */
		session.close();

		return list;
	}

	/*
	 * public boolean neoloadJsontodb() { NeoLoadXMLtoJSON json=new
	 * NeoLoadXMLtoJSON(); try { JSONObject jsonObject=json.getXMltoJson(
	 * "C:\\Users\\IN01071\\Downloads\\June2016\\report-Oct-21-12_50.xml");
	 * JSONArray response=json.getAllResponseTimes(jsonObject);
	 * 
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return true; }
	 */
	public boolean listtoDb(List list) {

		if (list != null && list.size() > 0) {
			try {
				String colNames[] = (String[]) list.get(0);

				StringBuffer buffer = new StringBuffer();
				buffer.append("insert into neoloadresults (");
				for (int i = 0; i < colNames.length; i++) {
					String colname = colNames[i].toLowerCase().replaceAll(" ", "");
					buffer.append(colname);
					if (i + 1 < colNames.length) {
						buffer.append(",");
					}
				}
				buffer.append(") values(");
				for (int i = 0; i < colNames.length; i++) {
					buffer.append("?");
					if (i + 1 < colNames.length) {
						buffer.append(",");
					}
				}
				buffer.append(");");
				System.out.println("My sql Query:" + buffer.toString());

				Session session = sessionFactory.openSession();
				org.hibernate.Transaction tx = session.beginTransaction();

				Query qry = session.createSQLQuery(buffer.toString());
				colNames = (String[]) list.get(1);
				System.out.println("changing values");
				for (int i = 0; i < colNames.length; i++) {
					qry.setParameter(i, colNames[i]);
					System.out.println("values " + i + ":" + colNames[i]);
				}
				// System.out.println("Complete:"+qry.getQueryString());
				int res = qry.executeUpdate();

				tx.commit();
				session.close();
				if (res > 0) {
					System.out.println("data updated successfully");
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		} else {
			return false;
		}

		return false;
	}

	public List getlastTwoDetails() {
		List list = new ArrayList();
		Session session = sessionFactory.openSession();
		String SQL_Query = "select * from neoload_statistics ORDER BY (buildNo) DESC  LIMIT 3";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(NeoLoadStaticsGraphModel.class);
		list = query.list();
		session.close();
		return list;
	}

	public void insertApdex(ApdexBean bean) {
		Session session = sessionFactory.openSession();

		String SQL_Query = "select * from test_details  where projectName='" + bean.getProjectName() + "'";
		Query query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(TestDetails.class);
		List list = query.list();
		TestDetails projectDetails = (TestDetails) list.get(0);
		SQL_Query = "UPDATE neoload_project SET apdex=" + bean.getApdexValue() + " where projectId="
				+ projectDetails.getProjectId() + " and buildNo=" + bean.getBuilNo();
		query = session.createSQLQuery(SQL_Query);
		((SQLQuery) query).addEntity(NeoLoadProject.class);
		int i = query.executeUpdate();
		session.close();
	}
}
