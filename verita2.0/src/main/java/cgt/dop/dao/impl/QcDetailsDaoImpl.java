package cgt.dop.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.dao.QcDetailsDao;
import cgt.dop.model.DomainModel;
import cgt.dop.model.QcDetails;

/**
* 
* QcDetailsDaoImpl class
* 
*/
@Repository("qcDetailsDao")
public class QcDetailsDaoImpl implements QcDetailsDao {

	@Autowired
	private SessionFactory sessionFactory;

	/**
     * Gets QcDetails from the DB
     * 
     * @param property (<code>String</code>) : the property userName
     * @return <code>QcDetails</code> : returns qcDetails.
     * @throws ConstraintViolatedException
     */
	public QcDetails getQcDetailsByUserName(String userName) {

		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(QcDetails.class);
		criteria.add(Restrictions.or(Restrictions.like("userName", "%"
				+ userName + "%")));

		QcDetails qcDetails = (QcDetails) criteria.list().get(0);
		return qcDetails;
	}
	
	
	public QcDetails getQcDetailsByProjectAndUserName(String userName,String project) {

		Session session = sessionFactory.openSession();
		
		Query query = session.createQuery("FROM QcDetails WHERE userName= :userName AND project= :project");
		query.setParameter("userName", userName);
		query.setParameter("project", project);
		QcDetails qcDetails = (QcDetails) query.list().get(0);
		return qcDetails;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
