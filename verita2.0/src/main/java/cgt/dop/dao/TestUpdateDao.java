package cgt.dop.dao;

import java.util.List;

import cgt.dop.model.TestUpdateModel;




/**
 * @author Dinesh Rajput
 *
 */
public interface TestUpdateDao {
	
	
	public String addTestUpdate(TestUpdateModel TestUpdateModel);
	
	public String addTestUpdateDetailsCompare(TestUpdateModel TestUpdateModel);

	public List<TestUpdateModel> findTesterUpdatesByTestType(String test_type,String test_num);
	
	public List<TestUpdateModel> findTesterUpdatesCompareByTestType(String test_type,String testwith,String testto);
	
	public TestUpdateModel getTestUpdate(int TestUpdateid);
	
	public String deleteTestUpdate(TestUpdateModel TestUpdate);
	
	public boolean updateTestUpdateDetails(int test_update_id,String comment_name,String user_name,String comment_date);

	public boolean updateTestNameDetails(String testName, String scenarioName);
	public boolean updateDateTimeZone(String testName, String dateTimeZone);
	public boolean updateScenarioDetails(String testName, String scenarios);
	public boolean updateQaEnvUrlDetails(String testName, String envUrl);
}
