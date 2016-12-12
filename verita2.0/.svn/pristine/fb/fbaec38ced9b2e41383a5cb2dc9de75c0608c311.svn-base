package cgt.dop.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.model.DomainModel;

@Repository
public class DomainDao {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionfactory() {
		return sessionFactory;
	}

	public void setSessionfactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Map<Integer, List<Object>> getDomains() {

		Session session = sessionFactory.openSession();

		Map<Integer, List<Object>> map = new HashMap<Integer, List<Object>>();
		List domainModels = session.createQuery("from DomainModel").list();
		for (Iterator iterator = domainModels.iterator(); iterator.hasNext();) {
			List<Object> list = new ArrayList<>();

			DomainModel domainModel = (DomainModel) iterator.next();

			list.add(domainModel.getDomain_id());
			list.add(domainModel.getDomain_name());
			list.add(domainModel.getDomain_lead());
			list.add(domainModel.getDomain_lead_email());
			list.add(domainModel.getDomain_description());

			map.put(domainModel.getDomain_id(), list);

		}

		session.close();

		System.out.println(map);
		return map;
	}

}
