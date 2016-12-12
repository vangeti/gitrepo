package cgt.dop.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.dao.TestUpdateDao;
import cgt.dop.model.TestUpdateModel;






/**
 * @author Dinesh Rajput
 *
 */
@Repository("TestUpdatesDao")
public class TestUpdateDaoImpl implements TestUpdateDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public String addTestUpdate(TestUpdateModel TestUpdate) {
		String result=null;
		try{
			Session session = sessionFactory.openSession();
			session.saveOrUpdate(TestUpdate);
			result="{\"result\":\"success\"}";
		}catch(Exception e){
			result="{\"result\":\"fail\"}";
		}
		return result;
	}
	
	
	public String addTestUpdateDetailsCompare(TestUpdateModel TestUpdate) {
		String result=null;
		try{
			Session session = sessionFactory.openSession();
			session.saveOrUpdate(TestUpdate);
			result="{\"result\":\"success\"}";
		}catch(Exception e){
			result="{\"result\":\"fail\"}";
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<TestUpdateModel> findTesterUpdatesByTestType(String test_type,String test_num) {
		List<TestUpdateModel> TestUpdateModels = new ArrayList<TestUpdateModel>();
		Session session = sessionFactory.openSession();
		String SQL_QUERY = "FROM TestUpdateModel tu WHERE tu.test_type = :test_type and tu.test_num=:test_num";
		Query query =  session.createQuery(SQL_QUERY);
		query.setParameter("test_type", test_type);
		query.setParameter("test_num", test_num);
		List list = query.list();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		    TestUpdateModel model = (TestUpdateModel) iterator.next();
		    TestUpdateModels.add(model);
		}
		session.close();
		return TestUpdateModels;
		//return (List<TestUpdateModel>) sessionFactory.getCurrentSession().createCriteria(TestUpdateModel.class).list();
    }
	
	
	
	@SuppressWarnings("unchecked")
	public List<TestUpdateModel> findTesterUpdatesCompareByTestType(String test_type,String testwith,String testto) {
		List<TestUpdateModel> TestUpdateModels = new ArrayList<TestUpdateModel>();
		Session session = sessionFactory.openSession();
		String SQL_QUERY = "FROM TestUpdateModel tu WHERE tu.test_type = :test_type and tu.testwith=:testwith and tu.testto=:testto";
		Query query =  session.createQuery(SQL_QUERY);
		query.setParameter("test_type", test_type);
		query.setParameter("testwith", testwith);
		query.setParameter("testto", testto);
		List list = query.list();
		if(list.size()!=0)
		{
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		    TestUpdateModel model = (TestUpdateModel) iterator.next();
		    TestUpdateModels.add(model);
		}
		}
		else{
			// query =  session.createQuery(SQL_QUERY);
			query.setParameter("test_type", test_type);
			query.setParameter("testwith", testto);
			query.setParameter("testto",  testwith);
			 list = query.list();
			 for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				    TestUpdateModel model = (TestUpdateModel) iterator.next();
				    TestUpdateModels.add(model);
				}
		}
		System.out.println("test details comparing...........................................");
		session.close();
		return TestUpdateModels;
		//return (List<TestUpdateModel>) sessionFactory.getCurrentSession().createCriteria(TestUpdateModel.class).list();
    }
	
	
	
	
	public TestUpdateModel getTestUpdate(int TestUpdateId) {
		Session session = sessionFactory.openSession();
		return (TestUpdateModel) session.get(TestUpdateModel.class, TestUpdateId);
	}

	public String deleteTestUpdate(TestUpdateModel TestUpdate) {
		String result = "success";
		try{
			Session session = sessionFactory.openSession();
			org.hibernate.Transaction tx=session.beginTransaction();
			String hql = "DELETE FROM TestUpdateModel "+
					"WHERE id = :id";
					Query query =  session.createQuery(hql);
					query.setParameter("id", TestUpdate.getId());
					query.executeUpdate();					
			tx.commit();
			session.close();
		}catch(Exception e){
			result = "Fail";
		}
		return result;
	}
	
	public List<TestUpdateModel> getTestUpdatesAndProjectByDomainModel(int domainModelId,int projectId) {
	    
	    System.out.println("domainModelId in getProjectByDomainModel == "+domainModelId);
		List<TestUpdateModel> TestUpdateListDetails = new ArrayList<TestUpdateModel>();
		Session session = sessionFactory.openSession();
		String SQL_QUERY = "from TestUpdateModel rm WHERE rm.projectModel.projectId =:projectId";
		Query query  = session.createQuery(SQL_QUERY);
		//query.setParameter("domainModelId", domainModelId);
		query.setParameter("projectId", projectId);
		List list =  (List) query.list();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TestUpdateModel model = (TestUpdateModel) iterator.next();
			TestUpdateListDetails.add(model);
		}
		session.close();
		
		System.out.println("TestUpdateListDetails == "+TestUpdateListDetails);
		
		return TestUpdateListDetails;
	}
	
	
	public boolean updateTestUpdateDetails(int test_update_id,String comment_name,String user_name,String comment_date)	{
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx=session.beginTransaction();
		Query qry = session.createQuery("update TestUpdateModel tu set tu.comment_name=?,tu.user_name=?,tu.comment_date=? where tu.id=?");
					        qry.setParameter(0,comment_name);
					        qry.setParameter(1,user_name);  
					        qry.setParameter(2,comment_date);
					        qry.setParameter(3,test_update_id);
					        int res = qry.executeUpdate();
		tx.commit();
		session.close();
		if(res>0) {
			System.out.println("data updated successfully");
			return true;
		} else {
			return false;
		}
	}

	public boolean updateTestNameDetails(String testName, String updatedTestName)	{
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx=session.beginTransaction();
		Query qry = session.createQuery("update LennoxProjectDetails lpd set lpd.testupdatedname=? where lpd.testname=?");
		qry.setParameter(0, updatedTestName);
		qry.setParameter(1, testName);
		int res = qry.executeUpdate();
		tx.commit();
		session.close();
		if(res>0) {
			System.out.println("data updated successfully");
			return true;
		} else {
			return false;
		}
	}
	
		public boolean updateDateTimeZone(String testName, String dateTimeZone)	{
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx=session.beginTransaction();
		Query qry = session.createQuery("update LennoxProjectDetails lpd set lpd.testdatetimezone=? where lpd.testname=?");
		qry.setParameter(0, dateTimeZone);
		qry.setParameter(1, testName);
		int res = qry.executeUpdate();
		tx.commit();
		session.close();
		if(res>0) {
			System.out.println("data updated successfully");
			return true;
		} else {
			return false;
		}
	}
		
		
		public boolean updateScenarioDetails(String testName, String scenarios)	{
			Session session = sessionFactory.openSession();
			org.hibernate.Transaction tx=session.beginTransaction();
			Query qry = session.createQuery("update LennoxProjectDetails lpd set lpd.testscenarios=? where lpd.testname=?");
			qry.setParameter(0, scenarios);
			qry.setParameter(1, testName);
			int res = qry.executeUpdate();
			tx.commit();
			session.close();
			if(res>0) {
				System.out.println("data updated successfully");
				return true;
			} else {
				return false;
			}
		}
		
		
		public boolean updateQaEnvUrlDetails(String testName, String envUrl)	{
			Session session = sessionFactory.openSession();
			org.hibernate.Transaction tx=session.beginTransaction();
			Query qry = session.createQuery("update LennoxProjectDetails lpd set lpd.testenvurl=? where lpd.testname=?");
			qry.setParameter(0, envUrl);
			qry.setParameter(1, testName);
			int res = qry.executeUpdate();
			tx.commit();
			session.close();
			if(res>0) {
				System.out.println("data updated successfully");
				return true;
			} else {
				return false;
			}
		}
	
	
	
}
