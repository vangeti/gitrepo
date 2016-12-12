package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tstr_obsvtn")
public class TesterObservationModel {
	
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "bu_name")
	private String buName;
	
	@Column(name = "proj_name")
	private String projectName;
	
	@Column(name = "widget_flag")
	private String widget;
	
	@Column(name = "comment")
	private String comment;
	
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
		return "TesterObservationModel [id=" + id + ", userName=" + userName + ", buName=" + buName + ", projectName=" + projectName + ", widget=" + widget
				+ ", comment=" + comment + "]";
	}
	
	
	
}
