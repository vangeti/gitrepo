package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
* 
* QcDetails class
* 
*/
@Entity
@Table(name = "QC_Details")
public class QcDetails {

	@Column(name = "qc_id")
	@Id
	private String id;
	
	@Column(name = "qc_service_url")
	private String serviceURL;

	@Column(name = "qc_domain")
	private String domain;

	@Column(name = "qc_project")
	private String project;

	@Column(name = "qc_name")
	private String userName;

	@Column(name = "qc_pwd")
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getServiceURL() {
		return serviceURL;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("QcDetails [id=");
	    builder.append(id);
	    builder.append(", serviceURL=");
	    builder.append(serviceURL);
	    builder.append(", domain=");
	    builder.append(domain);
	    builder.append(", project=");
	    builder.append(project);
	    builder.append(", userName=");
	    builder.append(userName);
	    builder.append(", password=");
	    builder.append(password);
	    builder.append("]");
	    return builder.toString();
	}

}
