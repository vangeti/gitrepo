package cgt.dop.service;

import java.util.List;

import cgt.dop.model.TestUpdateModel;



/**
 *
 */
public interface TestUpdateService {
	
	
	public String addTestUpdate(TestUpdateModel TestUpdate);
	
	public String addTestUpdateDetailsCompare(TestUpdateModel TestUpdate);

	public List<TestUpdateModel> findTesterUpdatesByTestType(String test_type,String test_num);
	
	public List<TestUpdateModel> findTesterUpdatesCompareByTestType(String test_type,String testwith,String testto);
	
	public TestUpdateModel getTestUpdate(int TestUpdateid);
	
	public String deleteTestUpdate(TestUpdateModel TestUpdate);
	
	public boolean updateTestUpdateDetails(int test_update_id,String comment_name,String user_name,String comment_date);

	boolean updateTestNameDetails(String testName, String scenarioName);
	boolean updateDateTimeZone(String testName, String dateTimeZone);
	boolean updateScenarioDetails(String testName, String scenarios);
	
	boolean updateQaEnvUrlDetails(String testName, String envUrl);
	
}
