package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="user_role_master")
public class RoleModel {
	
	@Id
	@Column

	private int user_role_id;
	
	@Column(name="user_role_name")
	private String user_role_name;
	@Column
	private String user_role_description;
	


	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	public String getUser_role_name() {
		return user_role_name;
	}
	public void setUser_role_name(String user_role_name) {
		this.user_role_name = user_role_name;
	}
	public String getUser_role_description() {
		return user_role_description;
	}
	public void setUser_role_description(String user_role_description) {
		this.user_role_description = user_role_description;
	}
	
	


}
