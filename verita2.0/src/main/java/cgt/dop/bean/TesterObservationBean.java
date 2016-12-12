package cgt.dop.bean;

public class TesterObservationBean {
	 
	private String userName;
	
	private String buName;
	
	private String projectName;
	
	private String widget;
	
	private String comment;
	
	public TesterObservationBean()
	{
		
	}
	
	public TesterObservationBean(String userName, String buName, String projectName, String widget, String comment) {
		super();
		this.userName = userName;
		this.buName = buName;
		this.projectName = projectName;
		this.widget = widget;
		this.comment = comment;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBuName() {
		return buName;
	}
	public void setBuName(String buName) {
		this.buName = buName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getWidget() {
		return widget;
	}
	public void setWidget(String widget) {
		this.widget = widget;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "TesterObservationBean [userName=" + userName + ", buName=" + buName + ", projectName=" + projectName + ", widget=" + widget + ", comment="
				+ comment + "]";
	}
	
	
}
