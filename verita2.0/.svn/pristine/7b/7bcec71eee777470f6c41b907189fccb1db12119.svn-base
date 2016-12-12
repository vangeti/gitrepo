package cgt.dop.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.dao.LennoxDetailsDao;
import cgt.dop.model.LennoxJmeterProject;

@Repository("lennoxDetailsDao")
public class LennoxDetailsDaoImpl implements LennoxDetailsDao{

	@Autowired
	private SessionFactory sessionFactory;

	public LennoxJmeterProject getLennoxLatestBuildDetails(){

		Session session = sessionFactory.openSession();

		//Query query = session.createQuery("FROM LennoxJmeterProject WHERE userName= :userName AND project= :project");
		Query query = session.createQuery("FROM LennoxJmeterProject where  endDate = (SELECT max(endDate) from LennoxJmeterProject)");
		
		LennoxJmeterProject LjmterDetails = (LennoxJmeterProject) query.list().get(0);
		return LjmterDetails;

	}
	
	
	public LennoxJmeterProject getStartDateAndEndDateForCurrentTest(String fileName){

		Session session = sessionFactory.openSession();

		Query query = session.createQuery("FROM LennoxJmeterProject where fileName like '%totalResults_5kUsers%'");
		
		LennoxJmeterProject LjmterDetails = (LennoxJmeterProject) query.list().get(0);
		return LjmterDetails;

	}

}
