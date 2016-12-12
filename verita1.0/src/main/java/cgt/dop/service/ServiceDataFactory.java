package cgt.dop.service;

public class ServiceDataFactory {

	public String getServiceURL() {
		// TODO Auto-generated method stub

		String ip = "172.16.31.104";
		String port = "8083";
		String domain = "http://";
		

		String finalURL = domain + ip + ":" + port;

		return finalURL;
	}

}
