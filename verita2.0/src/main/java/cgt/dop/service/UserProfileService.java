package cgt.dop.service;

import cgt.dop.model.LennoxTestDetails;
import cgt.dop.model.LennoxUserProfile;

/**
 * @author Lakshmi
 *
 */
public interface UserProfileService {
	
	public String saveUserProfile(LennoxUserProfile lennoxUserProfile);
	
	//public String saveUserProfileTotalUsers(LennoxUserProfile lennoxUserProfile);
	public String saveUserProfileScenarios(LennoxUserProfile lennoxUserProfile);
	public String saveUserProfileTestUrl(LennoxUserProfile lennoxUserProfile);
	public String saveUserProfileTestDate(LennoxUserProfile lennoxUserProfile);
	public String saveGraphanaTestDetails(LennoxTestDetails lennoxUserProfile);
	
	
}

