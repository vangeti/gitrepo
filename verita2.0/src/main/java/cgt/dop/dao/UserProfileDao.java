package cgt.dop.dao;
/**
 * @author Lakshmi
 *
 */

import cgt.dop.model.LennoxTestDetails;
import cgt.dop.model.LennoxUserProfile;


public interface UserProfileDao {
	
	public LennoxUserProfile getUserProfileData(long startTime,long endTime); 
	public String saveUserProfileDao(LennoxUserProfile lennoxUserProfile);
	//public String saveUserProfileTotalUsersDao(LennoxUserProfile lennoxUserProfile);
	public String saveUserProfileScenariosDao(LennoxUserProfile lennoxUserProfile);
	public String saveUserProfileTestUrlDao(LennoxUserProfile lennoxUserProfile);
	public String saveUserProfileTestDateDao(LennoxUserProfile lennoxUserProfile);
	public String saveUserGraphanaDao(LennoxTestDetails lennoxUserProfile);

	
	
	

}
