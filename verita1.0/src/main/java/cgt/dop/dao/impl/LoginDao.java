package cgt.dop.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionfactory() {
		return sessionFactory;
	}

	public void setSessionfactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Object getUserRole(String userName, String password) {
		boolean userFound = false;
		Object userRole = 0;
		
		Session session = sessionFactory.openSession();
		String SQL_QUERY = "select o.user_role from UserLoginModel as o where o.user_name=? and o.user_pwd=?";
		Query query = session.createQuery(SQL_QUERY);
		query.setParameter(0, userName);
		query.setParameter(1, password);
		List list = query.list();
		userRole =  list.get(0);
	
		if ((list != null) && (list.size() > 0)) {
			userFound = true;
		}
		session.close();
		return userRole;
	}
	
	public Object getUserid(String userName, String password) {
		boolean userFound = false;
	
		Object user_id=0;
		Session session = sessionFactory.openSession();
		String SQL_QUERY = "select o.user_id from UserLoginModel as o where o.user_name=? and o.user_pwd=?";
		Query query = session.createQuery(SQL_QUERY);
		query.setParameter(0, userName);
		query.setParameter(1, password);
		List list = query.list();

		user_id=  list.get(0);
		if ((list != null) && (list.size() > 0)) {
			userFound = true;
		}
		session.close();
		return user_id;
	}

	public String isValidUser(String userName, String password) {
		String userFound = null;
		
		Session session = sessionFactory.openSession();
		String VALIDATE_USER_QUERY = "select o.user_id from UserLoginModel as o where o.user_name=? and o.user_pwd=?";
		Query query = session.createQuery(VALIDATE_USER_QUERY);
		query.setParameter(0, userName);
		query.setParameter(1, password);
		
		List list = query.list();
		if ((list != null) && (list.size() > 0)) {
			userFound = "true";
		} else {
			userFound = "false";
		}
		session.close();
		return userFound;
	}

}
