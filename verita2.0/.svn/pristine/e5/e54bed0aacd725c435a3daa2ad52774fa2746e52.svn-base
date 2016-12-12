package cgt.dop.scrummaster.monitor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cgt.dop.model.MonitorModel;

@Repository
public class MonitorService 
{
	MonitorServiceUtilities serviceUtilities = new MonitorServiceUtilities();

	private boolean getCurrentStatus(String URL) {
		int respCode = serviceUtilities.getUrlResposeCodes(URL);
		if (respCode == 200)
			return true;
		else
			return false;
	}

	public List<MonitorBean> getMonitorWidgetDetails(List<MonitorModel> monitormodel) {
		List<MonitorBean> mbean = new ArrayList<MonitorBean>();
		for (MonitorModel model : monitormodel) {
			MonitorBean bean = new MonitorBean();
			bean.setWidgetname(model.getWidgetname());
			bean.setTool(model.getTool());
			bean.setStatus(getCurrentStatus(model.getUrl()));
			mbean.add(bean);
		}
		return mbean;
	}

}
