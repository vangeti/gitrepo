package cgt.dop.service.scrummaster.feature;

import java.util.List;

import cgt.dop.bean.FeatureBean;
import cgt.dop.bean.FeatureVelocityBean;
import cgt.dop.model.MonitorModel;

public class FeatureFactory {

	public FeatureBean getFeatureWidget(MonitorModel model) {
		Feature projmgmnt = null;
		String tool = model.getTool();
		switch (tool.toUpperCase()) {
		case "JIRA":
			projmgmnt = new JiraService();
			return projmgmnt.getManagementDetails(model);
		default:
			projmgmnt = new JiraService();
			return projmgmnt.getManagementDetails(model);
		}
	}

	
	public List<FeatureVelocityBean> getSprintVelocityDetails(MonitorModel model) {
		Feature projmgmnt = null;
		String tool = model.getTool();
		switch (tool.toUpperCase()) {
		case "JIRA_New":
			projmgmnt = new JiraService();
			return projmgmnt.getSprintVelocityDetails(model);
		default:
			projmgmnt = new JiraService();
			return projmgmnt.getSprintVelocityDetails(model);
		}
	}
	
	/*public FeatureBean getManagementDetails123(MonitorModel model) {
		Feature projmgmnt = null;
		String tool = model.getTool();
		switch (tool.toUpperCase()) {
		case "JIRA":
			projmgmnt = new JiraService();
			return projmgmnt.getManagementDetails(model);
		default:
			projmgmnt = new JiraService();
			return projmgmnt.getManagementDetails(model);
		}
	}*/

}
