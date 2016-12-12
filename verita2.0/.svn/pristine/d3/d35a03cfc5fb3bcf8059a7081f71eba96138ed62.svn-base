package cgt.dop.service;

import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.model.QcDetails;

/**
* 
* QcDetailsService class
* 
*/


public interface QcDetailsService {

	/**
     * Gets QcDetails from the DAO
     * 
     * @param property (<code>String</code>) : the property userName
     * @return <code>QcDetails</code> : returns qcDetails.
     * @throws ConstraintViolatedException
     */
	QcDetails getQcDetailsByUserName(String userName);
	
	
	QcDetails getQcDetailsByProjectAndUserName(String userName,String project);
}
