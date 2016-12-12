package cgt.dop.service.scrummaster.feature;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cgt.dop.bean.FeatureBean;
import cgt.dop.bean.FeatureVelocityBean;
import cgt.dop.model.MonitorModel;

@Repository
public class JiraService implements Feature {

	/*String jiraUrl = "http://172.16.3.38:8888/rest/api/2/search?jql=sprint=1";
	String userId = "mdayan";
	String pwd = "mdayan";
	String sprintName;
	String sprintStartDate;
	String sprintEndDate;
	Map<String, String> currentSprint = null;*/

	@Override
	public FeatureBean getManagementDetails(MonitorModel model) {
		FeatureBean bean = null;
		String sprintName = null;
		String sprintStartDate = null;
		String sprintEndDate = null;
		int todo = 0;
		int wip = 0;
		int done = 0;
		try {
			bean = new FeatureBean();
			byte[] message = (model.getUsername() + ":" + model.getPassword()).getBytes("UTF-8");
			String auth = DatatypeConverter.printBase64Binary(message);

			Client client = Client.create();
			WebResource webResource = client.resource(model.getUrl() + "/rest/api/2/search?jql=sprint=1");

			ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
					.accept("application/json").get(ClientResponse.class);
			String response1 = response.getEntity(String.class);
			JSONObject jsnobject = new JSONObject(response1);
			JSONArray jsonArray = jsnobject.getJSONArray("issues");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject explrObject = jsonArray.getJSONObject(i);
				String form = explrObject.getJSONObject("fields").getJSONArray("customfield_10004").toString();
				sprintName = form.substring(form.indexOf("name=") + 5, form.indexOf(",startDate"));
				sprintStartDate = form.substring(form.indexOf("startDate=") + 10, form.indexOf("startDate=") + 20);
				sprintEndDate = form.substring(form.indexOf("endDate=") + 8, form.indexOf("endDate=") + 18);
				if (explrObject.getJSONObject("fields").getJSONObject("status").getString("name").equals("To Do"))
					todo++;
				else if (explrObject.getJSONObject("fields").getJSONObject("status").getString("name")
						.equals("In Progress"))
					wip++;
				else
					done++;
			}
			bean.setToolname("JIRA");
			bean.setSprintname(sprintName.trim());
			bean.setSprintstartdate(sprintStartDate.trim());
			bean.setSprintenddate(sprintEndDate.trim());
			bean.setTotal(String.valueOf(todo + wip + done).trim());
			bean.setTodo(String.valueOf(todo).trim());
			bean.setWip(String.valueOf(wip).trim());
			bean.setDone(String.valueOf(done).trim());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public List<FeatureVelocityBean> getSprintVelocityDetails(MonitorModel model) {
		
		List<FeatureVelocityBean> list=new ArrayList<FeatureVelocityBean>();
		
		try {
			FeatureVelocityBean featureVelocityBean=null;
			
			byte[] message = (model.getUsername() + ":" + model.getPassword()).getBytes("UTF-8");
			String auth = DatatypeConverter.printBase64Binary(message);

			Client client = Client.create();
			System.out.println("Web Link : "+model.getUrl() + "/rest/greenhopper/1.0/rapid/charts/velocity?rapidViewId=5");
			WebResource webResource = client.resource(model.getUrl() + "/rest/greenhopper/1.0/rapid/charts/velocity?rapidViewId=5");
			ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
					.accept("application/json").get(ClientResponse.class);
			String response1 = response.getEntity(String.class);
			
			System.out.println("================");
			System.out.println(response1);
			System.out.println("================");
			
			JSONObject jsnobject = new JSONObject(response1);
			
			JSONArray sprintsArr= jsnobject.getJSONArray("sprints");
			JSONObject velocityStatEntries=jsnobject.getJSONObject("velocityStatEntries");
			
			for (int i = 0; i < sprintsArr.length(); i++) {
				
				featureVelocityBean=new FeatureVelocityBean();
				
				//System.out.println(sprintsArr.getJSONObject(i).get("name"));
				String sprintName=(String) sprintsArr.getJSONObject(i).get("name");
				String commitment=velocityStatEntries.getJSONObject(sprintsArr.getJSONObject(i).getString("id")).getJSONObject("estimated").getString("value");
				String completed=velocityStatEntries.getJSONObject(sprintsArr.getJSONObject(i).getString("id")).getJSONObject("completed").getString("value");
				
				featureVelocityBean.setSprintName(sprintName);
				featureVelocityBean.setCommitment(commitment);
				featureVelocityBean.setCompleted(completed);

				list.add(featureVelocityBean);
				
				/*System.out.println(commitment);
				System.out.println(completed);*/
			}
			//System.out.println(sprintsArr);
			//System.out.println(velocityStatEntries);
			//System.out.println(jsnobject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		MonitorModel model=new MonitorModel();
		model.setUrl("http://172.16.3.141:8080");
		model.setUsername("moshe");
		model.setPassword("Dayan");
		new JiraService().getSprintVelocityDetails(model);
	}
	
}