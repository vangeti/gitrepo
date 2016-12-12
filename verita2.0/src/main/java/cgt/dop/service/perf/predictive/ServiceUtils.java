package cgt.dop.service.perf.predictive;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceUtils 
{
	public String getCPUPredictions(String calls,String cpuUsage)
	{
		RestTemplate restTemplate = new RestTemplate();
		final String uri = "https://ussouthcentral.services.azureml.net/workspaces/d731a5210cd84b7ba7f5f13673645c83/services/2d4298b5ce184529ac92c2fb791647f4/execute?api-version=2.0&details=true";
	
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		String postBody="{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"calls_per_minute\"],\"Values\": [[\""+calls+"\"]]}},\"GlobalParameters\": {}}";
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", "Bearer Ldq12cRFAX8K682HjX6i+w4suJbd1R6XzdvU7l0ylPpTkiRRjKMArGdTXysQPeg3Pgwe7UX2eqD0/06A36jXng==");
	    HttpEntity request = new HttpEntity(postBody, headers);
	    ResponseEntity<String> response = restTemplate
	    		  .exchange(uri, HttpMethod.POST, request, String.class);
	    System.out.println(response.getStatusCode());
	    if(response.getStatusCode().toString().equals("200"))
	    {
	    	System.out.println("**Response:"+response.getBody());
	    	return response.getBody();
	    }
	    else{
	    	return "";
	    }
	
	}


	public String getResponsePredictions(String calls,String cpuUsage)
	{
		RestTemplate restTemplate = new RestTemplate();
		final String uri = "https://ussouthcentral.services.azureml.net/workspaces/d731a5210cd84b7ba7f5f13673645c83/services/8174a37ba4474aad89160c5f61911b48/execute?api-version=2.0&details=true";
		System.out.println("*****"+calls);
		String postBody="{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"calls_per_minute\"],\"Values\": [[\""+calls+"\"]]}},\"GlobalParameters\": {}}";
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", "Bearer UFMVVadOylf8Y8xMvFnOrdsd8o/+o6fJ424xDa1UmohD4DYjeNcm6gJZcYs9yfaJ4VKCQxdJi5/mq/aNYvsnwA==");
	    HttpEntity request = new HttpEntity(postBody, headers);
	    
	    ResponseEntity<String> response = restTemplate
	    		  .exchange(uri, HttpMethod.POST, request, String.class);
	    System.out.println(response.getStatusCode());
	    if(response.getStatusCode().toString().equals("200"))
	    {
	    	System.out.println("**Response:"+response.getBody());
	    	return response.getBody();
	    }
	    else{
	    	return "";
	    }
	
	}


	public String getPercentile()
	{
		RestTemplate restTemplate = new RestTemplate();
		final String uri = "https://ussouthcentral.services.azureml.net/workspaces/d731a5210cd84b7ba7f5f13673645c83/services/81629930089c4176b30190e3570d630f/execute?api-version=2.0&details=true";
		//	System.out.println("*****"+calls);
		String postBody="{\"Inputs\": {\"input1\": {\"ColumnNames\": [\"calls_per_minute\"],\"Values\": [[\""+"00"+"\"]]}},\"GlobalParameters\": {}}";
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", "Bearer X4WPZbhYjLyNuu5oIAZPuOgKOObJJzTUIMdNtxICPrw6YvIdydUjHC3WxOsqf+N2lJ6HqQUNmBksq0NXH8oHug==");
	    HttpEntity request = new HttpEntity(postBody, headers);
	    
	    ResponseEntity<String> response = restTemplate
	    		  .exchange(uri, HttpMethod.POST, request, String.class);
	    System.out.println(response.getStatusCode());
	    if(response.getStatusCode().toString().equals("200"))
	    {
	    	System.out.println("**Response:"+response.getBody());
	    	return response.getBody();
	    }
	    else{
	    	return "";
	    }
	
	}
	
	
}
