package cgt.dop.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.dao.UserProfileDao;
import cgt.dop.model.LennoxTestDetails;
import cgt.dop.model.LennoxUserProfile;


@Repository("UserProfileDao")	
public class UserProfileDaoImpl implements UserProfileDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	//method to get the data if already exist in the db
	
	public LennoxUserProfile getUserProfileData(long startTime,long endTime){

		Session session = sessionFactory.openSession();

		LennoxUserProfile returnBean = null;
		try{
			String hqlQuery = "FROM LennoxUserProfile up WHERE up.dbStartTime =:dbStartTime and  up.dbEndTime =:dbEndTime";
			Query query = session.createQuery(hqlQuery);
			query.setParameter("dbStartTime", startTime);
			query.setParameter("dbEndTime", endTime);
			//query.setParameter("testNo", testId);
			List<LennoxUserProfile> results = query.list();
			System.out.println("getUserProfileData----------= "+query.list().size());
			if (results.size()==1){
				returnBean = results.get(0);
			}

			else {

				LennoxUserProfile newBean = new LennoxUserProfile();
				newBean.setDbStartTime(startTime);
				newBean.setDbEndTime(endTime);
				session.save(newBean);
				returnBean =  newBean;
				
				String hqlQueryNew = "FROM LennoxUserProfile up WHERE up.dbStartTime =:dbStartTime and  up.dbEndTime =:dbEndTime";
				Query query2 = session.createQuery(hqlQueryNew);
				query.setParameter("dbStartTime", startTime);
				query.setParameter("dbEndTime", endTime);
				//query.setParameter("testNo", testId);
				List<LennoxUserProfile> results2 = query2.list();
				System.out.println("getUserProfileData----------"+query2.list().size());
				if (results.size()==1){
					returnBean = results2.get(0);
				}

			}
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

		return returnBean;
	}
	
	public String saveUserProfileDao(LennoxUserProfile lennoxUserProfile) {
		System.out.println("DAO IMPLIMENTATION");
		String result=null;
		try{
			Session session = sessionFactory.openSession();
			String hql = "FROM LennoxUserProfile up WHERE up.dbStartTime =:dbStartTime and  up.dbEndTime =:dbEndTime";
			Query query = session.createQuery(hql);
			query.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			query.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			System.out.println("Query result==------- "+query.list().size());
			System.out.println("Query result==------- "+query.list());
			List<LennoxUserProfile> results = query.list();
			
			if(results.size()==0){
					System.out.println("NEW");
					//lennoxUserProfile.setTestNo(1);
					session.save(lennoxUserProfile);
					result="{\"result\":\"success\"}";
				}
			else if( results.size()==1) {
				System.out.println("UPDATE");
				String updateHql = "UPDATE LennoxUserProfile up set up.testName = :testName WHERE  up.dbStartTime =:dbStartTime"+" and  up.dbEndTime =:dbEndTime";
			Query updateQuery = session.createQuery(updateHql);
			
			updateQuery.setParameter("testName", lennoxUserProfile.getTestName());
			updateQuery.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			updateQuery.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			int effectedRecords = updateQuery.executeUpdate();
			System.out.println("Rows affected: " + effectedRecords);
			result="{\"result\":\"success\"}";
				
			}
			
			
			
			//session.saveOrUpdate(lennoxUserProfile);
			
		}catch(Exception e){
			result="{\"result\":\"fail\"}";
		}
		return result;
	}
	
	
	
	public String saveUserGraphanaDao(LennoxTestDetails lennoxUserProfile) {
		System.out.println("DAO IMPLIMENTATION");
		String result=null;
		try{
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createSQLQuery("insert into lennox_projectdetails (testname,teststarttime,testendtime,testsuite,projectId,buildNo) values (?, ?, ?,?,?,?)");
			query.setParameter(0, lennoxUserProfile.getTestName());
			query.setParameter(1, lennoxUserProfile.getStartTime());
			query.setParameter(2, lennoxUserProfile.getEndTime());
			query.setParameter(3, lennoxUserProfile.getTestsuite());
			query.setParameter(4, lennoxUserProfile.getProjectId());
			query.setParameter(5, lennoxUserProfile.getBuildno());
			query.executeUpdate();
			
			
			
			//
			//String hql = "FROM lennox_projectdetails up WHERE up.dbStartTime =:dbStartTime and  up.dbEndTime =:dbEndTime and up.testName=:testName";
			//Query query = session.createQuery(hql);
			
			//session.saveOrUpdate(lennoxUserProfile);
			session.getTransaction().commit();
			session.close();
			System.out.println("Data saved successfully..............");
			result="{\"result\":\"success\"}";
//			query.setParameter("dbStartTime", lennoxUserProfile.getStartTime());
//			query.setParameter("dbEndTime", lennoxUserProfile.getEndTime());
//			query.setParameter("testName", lennoxUserProfile.getTestName());
//			System.out.println("Query result==------- "+query.list().size());
//			System.out.println("Query result==------- "+query.list());
//			List<LennoxTestDetails> results = query.list();
			
			/*if(results.size()==0){
					System.out.println("NEW");
					//lennoxUserProfile.setTestNo(1);
					session.saveOrUpdate(lennoxUserProfile);
					result="{\"result\":\"success\"}";
				}
			else if( results.size()==1) {
				System.out.println("UPDATE");
				String updateHql = "UPDATE lennox_projectdetails up set up.testName = :testName WHERE  up.dbStartTime =:dbStartTime"+" and  up.dbEndTime =:dbEndTime";
			Query updateQuery = session.createQuery(updateHql);
			
			updateQuery.setParameter("testName", lennoxUserProfile.getTestName());
			updateQuery.setParameter("dbStartTime", lennoxUserProfile.getStartTime());
			updateQuery.setParameter("dbEndTime", lennoxUserProfile.getEndTime());
			int effectedRecords = updateQuery.executeUpdate();
			System.out.println("Rows affected: " + effectedRecords);
			result="{\"result\":\"success\"}";
				
			}
			
			
			
			//session.saveOrUpdate(lennoxUserProfile);*/
			
		}catch(Exception e){
			result="{\"result\":\"fail\"}";
	}
			
		return result;
	
//		Session session = sessionFactory.openSession();
//		String hql = "INSERT INTO table_name ( testname, )VALUES(  );";
	}
	
/*	public String saveUserProfileTotalUsersDao(LennoxUserProfile lennoxUserProfile) {
		System.out.println("DAO IMPLIMENTATION saveUserProfileTotalUsersDao");
		String result=null;
		try{
			Session session = sessionFactory.openSession();
			String hql = "FROM LennoxUserProfile up WHERE up.dbStartTime =:dbStartTime and  up.dbEndTime =:dbEndTime";
			Query query = session.createQuery(hql);
			query.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			query.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			System.out.println("Query result==------- "+query.list().size());
			System.out.println("Query result==------- "+query.list());
			List<LennoxUserProfile> results = query.list();
			
			if(results.size()==0){
					System.out.println("NEW");
					//lennoxUserProfile.setTestNo(1);
					session.save(lennoxUserProfile);
					result="{\"result\":\"success\"}";
				}
			else if( results.size()==1) {
				System.out.println("UPDATE");
				String updateHql = "UPDATE LennoxUserProfile up set up.totalUsers =:totalUsers WHERE  up.dbStartTime =:dbStartTime"+" and  up.dbEndTime =:dbEndTime";
			Query updateQuery = session.createQuery(updateHql);
			
			updateQuery.setParameter("totalUsers", lennoxUserProfile.getTotalUsers());
			updateQuery.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			updateQuery.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			int effectedRecords = updateQuery.executeUpdate();
			System.out.println("Rows affected: " + effectedRecords);
			result="{\"result\":\"success\"}";
				
			}
			
			
			
			//session.saveOrUpdate(lennoxUserProfile);
			
		}catch(Exception e){
			result="{\"result\":\"fail\"}";
		}
		return result;
	}
	*/
	public String saveUserProfileScenariosDao(LennoxUserProfile lennoxUserProfile) {
		System.out.println("DAO IMPLIMENTATION saveUserProfileScenariosDao");
		String result=null;
		try{
			Session session = sessionFactory.openSession();
			String hql = "FROM LennoxUserProfile up WHERE up.dbStartTime =:dbStartTime and  up.dbEndTime =:dbEndTime";
			Query query = session.createQuery(hql);
			query.setParameter("testName", lennoxUserProfile.getTestName());
			query.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			query.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			System.out.println("Query result==------- "+query.list().size());
			System.out.println("Query result==------- "+query.list());
			List<LennoxUserProfile> results = query.list();
			
			if(results.size()==0){
					System.out.println("NEW");
					//lennoxUserProfile.setTestNo(1);
					session.save(lennoxUserProfile);
					result="{\"result\":\"success\"}";
				}
			else if( results.size()==1) {
				System.out.println("UPDATE");
				String updateHql = "UPDATE LennoxUserProfile up set up.scenarios =:scenarios WHERE  up.dbStartTime =:dbStartTime"+" and  up.dbEndTime =:dbEndTime";
			Query updateQuery = session.createQuery(updateHql);
			query.setParameter("testName", lennoxUserProfile.getTestName());
			updateQuery.setParameter("scenarios", lennoxUserProfile.getScenarios());
			updateQuery.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			updateQuery.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			int effectedRecords = updateQuery.executeUpdate();
			System.out.println("Rows affected: " + effectedRecords);
			result="{\"result\":\"success\"}";
				
			}
			
			
			
			//session.saveOrUpdate(lennoxUserProfile);
			
		}catch(Exception e){
			result="{\"result\":\"fail\"}";
		}
		return result;
	}
	
	public String saveUserProfileTestUrlDao(LennoxUserProfile lennoxUserProfile) {
		System.out.println("DAO IMPLIMENTATION saveUserProfileTestUrlDao");
		String result=null;
		try{
			Session session = sessionFactory.openSession();
			String hql = "FROM LennoxUserProfile up WHERE up.dbStartTime =:dbStartTime and  up.dbEndTime =:dbEndTime";
			Query query = session.createQuery(hql);
			query.setParameter("testName", lennoxUserProfile.getTestName());
			query.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			query.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			System.out.println("Query result==------- "+query.list().size());
			System.out.println("Query result==------- "+query.list());
			List<LennoxUserProfile> results = query.list();
			
			if(results.size()==0){
					System.out.println("NEW");
					//lennoxUserProfile.setTestNo(1);
					session.save(lennoxUserProfile);
					result="{\"result\":\"success\"}";
				}
			else if( results.size()==1) {
				System.out.println("UPDATE");
				String updateHql = "UPDATE LennoxUserProfile up set up.testEnvUrl = :testEnvUrl WHERE  up.dbStartTime =:dbStartTime"+" and  up.dbEndTime =:dbEndTime";
			Query updateQuery = session.createQuery(updateHql);
			query.setParameter("testName", lennoxUserProfile.getTestName());
			updateQuery.setParameter("testEnvUrl", lennoxUserProfile.getTestEnvUrl());
			updateQuery.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			updateQuery.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			int effectedRecords = updateQuery.executeUpdate();
			System.out.println("Rows affected: " + effectedRecords);
			result="{\"result\":\"success\"}";
				
			}
			
			
			
			//session.saveOrUpdate(lennoxUserProfile);
			
		}catch(Exception e){
			result="{\"result\":\"fail\"}";
		}
		return result;
	}
	
	public String saveUserProfileTestDateDao(LennoxUserProfile lennoxUserProfile) {
		System.out.println("DAO IMPLIMENTATION saveUserProfileTestDateDao");
		String result=null;
		try{
			Session session = sessionFactory.openSession();
			String hql = "FROM LennoxUserProfile up WHERE up.dbStartTime =:dbStartTime and  up.dbEndTime =:dbEndTime";
			Query query = session.createQuery(hql);
			query.setParameter("testName", lennoxUserProfile.getTestName());
			query.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			query.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			System.out.println("Query result==------- "+query.list().size());
			System.out.println("Query result==------- "+query.list());
			List<LennoxUserProfile> results = query.list();
			
			if(results.size()==0){
					System.out.println("NEW");
					//lennoxUserProfile.setTestNo(1);
					session.save(lennoxUserProfile);
					result="{\"result\":\"success\"}";
				}
			else if( results.size()==1) {
				System.out.println("UPDATE");
				String updateHql = "UPDATE LennoxUserProfile up set up.testDate = :testDate WHERE  up.dbStartTime =:dbStartTime"+" and  up.dbEndTime =:dbEndTime";
			Query updateQuery = session.createQuery(updateHql);
			
			updateQuery.setParameter("testName", lennoxUserProfile.getTestName());
			updateQuery.setParameter("testDate", lennoxUserProfile.getTestDate());
			updateQuery.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
			updateQuery.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
			int effectedRecords = updateQuery.executeUpdate();
			System.out.println("Rows affected: " + effectedRecords);
			result="{\"result\":\"success\"}";
				
			}
			
			
			
			//session.saveOrUpdate(lennoxUserProfile);
			
		}catch(Exception e){
			result="{\"result\":\"fail\"}";
		}
		return result;
	}
	
	
	/*public List<LennoxUserProfile> getDBStatus(){
		
		Query query = session.createQuery(hql);
		query.setParameter("dbStartTime", lennoxUserProfile.getDbStartTime());
		query.setParameter("dbEndTime", lennoxUserProfile.getDbEndTime());
		System.out.println("Query result==------- "+query.list().size());
		System.out.println("Query result==------- "+query.list());
	}*/

}
