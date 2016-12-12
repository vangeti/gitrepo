package cgt.dop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cgt.dop.dao.TestUpdateDao;
import cgt.dop.model.TestUpdateModel;
import cgt.dop.service.TestUpdateService;


/**
 *
 */
@Service("TestUpdateService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TestUpdateServiceImpl implements TestUpdateService {

	@Autowired
	private TestUpdateDao TestUpdateDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String addTestUpdate(TestUpdateModel TestUpdate) {
		return TestUpdateDao.addTestUpdate(TestUpdate);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String addTestUpdateDetailsCompare(TestUpdateModel TestUpdate) {
		return TestUpdateDao.addTestUpdateDetailsCompare(TestUpdate);
	}
	
	public List<TestUpdateModel> findTesterUpdatesByTestType(String test_type,String test_num) {
		return TestUpdateDao.findTesterUpdatesByTestType(test_type,test_num);
	}
	
	public List<TestUpdateModel> findTesterUpdatesCompareByTestType(String test_type,String testwith,String testto) {
		return TestUpdateDao.findTesterUpdatesCompareByTestType(test_type,testwith,testto);
	}

	public TestUpdateModel getTestUpdate(int TestUpdateid) {
		return TestUpdateDao.getTestUpdate(TestUpdateid);
	}

	public String deleteTestUpdate(TestUpdateModel TestUpdate) {
		return TestUpdateDao.deleteTestUpdate(TestUpdate);
	}

	@Override
	public boolean updateTestUpdateDetails(int test_update_id, String comment_name, String user_name,
			String comment_date) {
		return TestUpdateDao.updateTestUpdateDetails(test_update_id, comment_name, user_name, comment_date);
	}
	
	@Override
	public boolean updateTestNameDetails(String testName, String updatedTestName) {
		return TestUpdateDao.updateTestNameDetails(testName, updatedTestName);
	}
	
	@Override
	public boolean updateDateTimeZone(String testName, String dateTimeZone) {
		return TestUpdateDao.updateDateTimeZone(testName, dateTimeZone);
	}
	
	@Override
	public boolean updateScenarioDetails(String testName, String scenarios) {
		return TestUpdateDao.updateScenarioDetails(testName, scenarios);
	}
	
	@Override
	public boolean updateQaEnvUrlDetails(String testName, String envUrl) {
		return TestUpdateDao.updateQaEnvUrlDetails(testName, envUrl);
	}

}
