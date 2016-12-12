package cgt.dop.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.model.DomainModel;
import cgt.dop.model.ProjectModel;



@Repository
public class ProjectDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionfactory() {
		return sessionFactory;
	}

	public void setSessionfactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	

	

	public Map<Integer, List<Object>> getProjects() {

		Session session = sessionFactory.openSession();

		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		List RoleModels = session.createQuery("from ProjectModel").list();
		for (Iterator iterator = RoleModels.iterator(); iterator.hasNext();) {
			List<Object> list = new ArrayList<>();

			ProjectModel roleModel = (ProjectModel) iterator.next();
			DomainModel domain = (DomainModel) session.createQuery("from DomainModel where domain_id="+roleModel.getDomain_id()).uniqueResult();
			list.add(roleModel.getProject_id());
			list.add(roleModel.getProject_Name());
			list.add(domain.getDomain_name());
			list.add(roleModel.getProject_Client());
			list.add(roleModel.getProject_startDate());
			list.add(roleModel.getProject_endDate());
			list.add(roleModel.getProject_Description());
			

			map.put(roleModel.getProject_id(), list);

		}

		session.close();

		System.out.println(map);
		return map;
	}

}
