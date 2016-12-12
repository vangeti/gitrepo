package cgt.dop.dao;

import cgt.dop.model.LennoxJmeterProject;

public interface LennoxDetailsDao {
	
	LennoxJmeterProject getLennoxLatestBuildDetails();
	
	LennoxJmeterProject getStartDateAndEndDateForCurrentTest(String fileName);

}
