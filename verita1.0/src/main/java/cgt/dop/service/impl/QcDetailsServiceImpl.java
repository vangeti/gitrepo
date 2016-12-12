package cgt.dop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.dao.QcDetailsDao;
import cgt.dop.model.QcDetails;
import cgt.dop.service.QcDetailsService;

/**
* 
* QcDetailsServiceImpl class
* 
*/
@Service("qcDetailsService")
@Transactional 
public class QcDetailsServiceImpl implements QcDetailsService{

	
	@Autowired
	private QcDetailsDao qcDetailsDao;
	
	/**
     * Gets QcDetails from the DAO
     * 
     * @param property (<code>String</code>) : the property userName
     * @return <code>QcDetails</code> : returns qcDetails.
     * @throws ConstraintViolatedException
     */
	public QcDetails getQcDetailsByUserName(String userName) {
		return qcDetailsDao.getQcDetailsByUserName(userName);
	}
	
	
	public QcDetails getQcDetailsByProjectAndUserName(String userName,String project) {
		return qcDetailsDao.getQcDetailsByProjectAndUserName(userName,project);
	}

}
