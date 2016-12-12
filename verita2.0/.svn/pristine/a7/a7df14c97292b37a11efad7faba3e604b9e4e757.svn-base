package cgt.dop.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import cgt.dop.dao.UserProfileDao;
import cgt.dop.model.LennoxTestDetails;
import cgt.dop.model.LennoxUserProfile;
import cgt.dop.service.UserProfileService;

/**
 * @author Lakshmi
 *
 */
@Service("UserProfileService")
/*@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)*/
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private UserProfileDao userProfileDao;
	
	//@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String saveUserProfile(LennoxUserProfile lennoxUserProfile) {
		System.out.println("SERVICE IMPLIMENTATION saveUserProfile");
		return userProfileDao.saveUserProfileDao(lennoxUserProfile);
	}
	
	/*public String saveUserProfileTotalUsers(LennoxUserProfile lennoxUserProfile) {
		System.out.println("SERVICE IMPLIMENTATION saveUserProfileTotalUsers");
		return userProfileDao.saveUserProfileTotalUsersDao(lennoxUserProfile);
	}*/
	
	public String saveUserProfileScenarios(LennoxUserProfile lennoxUserProfile) {
		System.out.println("SERVICE IMPLIMENTATION saveUserProfileScenarios ");
		return userProfileDao.saveUserProfileScenariosDao(lennoxUserProfile);
	}
	
	public String saveUserProfileTestUrl(LennoxUserProfile lennoxUserProfile) {
		System.out.println("SERVICE IMPLIMENTATION saveUserProfileTestUrl ");
		return userProfileDao.saveUserProfileTestUrlDao(lennoxUserProfile);
	}
	
//	public String saveUserProfileTestDate(LennoxUserProfile lennoxUserProfile) {
//		System.out.println("SERVICE IMPLIMENTATION saveUserProfileTestDate ");
//		return userProfileDao.saveUserProfileTestDateDao(lennoxUserProfile);
//	}
	
	public String saveUserProfileTestDate(LennoxUserProfile lennoxUserProfile) {
		System.out.println("SERVICE IMPLIMENTATION saveUserProfileTestDate ");
		return userProfileDao.saveUserProfileTestDateDao(lennoxUserProfile);
	}

	@Override
	public String saveGraphanaTestDetails(LennoxTestDetails lennoxUserProfile) {
		//System.out.println("SERVICE IMPLIMENTATION saveUserProfile");
		return userProfileDao.saveUserGraphanaDao(lennoxUserProfile);
	}
	
	

}
