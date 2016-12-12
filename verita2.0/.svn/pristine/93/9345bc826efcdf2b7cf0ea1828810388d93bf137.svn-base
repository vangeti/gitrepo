package cgt.dop.dao;

import cgt.dop.alm.exception.ConstraintViolatedException;
import cgt.dop.model.QcDetails;

/**
* 
* QcDetailsDao class
* 
*/

public interface QcDetailsDao {
	
	/**
     * Gets QcDetails from the DB
     * 
     * @param property (<code>String</code>) : the property userName
     * @return <code>QcDetails</code> : returns qcDetails.
     * @throws ConstraintViolatedException
     */
	QcDetails getQcDetailsByUserName(String userName);
	
	QcDetails getQcDetailsByProjectAndUserName(String userName,String project);

}
