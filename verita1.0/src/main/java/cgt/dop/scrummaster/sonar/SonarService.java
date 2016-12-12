package cgt.dop.scrummaster.sonar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

@Repository
public class SonarService 
{
	String sonarUrl = "http://172.16.3.40:9000/api/resources?resource=konatest&metrics=ncloc,violations,files,functions,directories,lines,classes,statements,accessors,blocker_violations,critical_violations,major_violations,minor_violations,info_violations,sqale_index,sqale_debt_ratio,duplicated_files,duplicated_blocks,duplicated_lines,duplicated_lines_density,class_complexity,file_complexity,function_complexity,complexity&format=json";

	// String sonarUrl="http://172.16.3.193:9000/api/webservices/list";

	public Map<String, String> getSonarData() {
		StringBuffer response = null;
		Map<String, String> sonarMap = null;
		try {
			URL oracle = new URL(sonarUrl);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			String inputLine = null;

			while ((inputLine = in.readLine()) != null) {
				response = new StringBuffer();
				response.append(inputLine);
			}
			in.close();
			sonarMap = new HashMap<String, String>();
			JSONArray jsonArray = new JSONArray(response.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject explrObject = jsonArray.getJSONObject(i);
				JSONArray explrArray = explrObject.getJSONArray("msr");
				for (int j = 0; j < explrArray.length(); j++) {
					sonarMap.put(explrArray.getJSONObject(j).getString("key"),
							explrArray.getJSONObject(j).getString("frmt_val"));
				}
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sonarMap;

	}

	public static void main(String[] args) {

		Map<String, String> res = new SonarService().getSonarData();

		System.out.println(res.toString());
		System.out.println(res.get("sqale_index"));
		System.out.println(res.get("blocker_violations"));
	}
}
