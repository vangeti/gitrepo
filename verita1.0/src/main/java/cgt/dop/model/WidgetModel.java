package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "widget_master")
public class WidgetModel {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column
	private String widget_name;

	@Column
	private String widget_description;

	@Column
	private String widget_div_id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWidget_name() {
		return widget_name;
	}

	public void setWidget_name(String widget_name) {
		this.widget_name = widget_name;
	}

	public String getWidget_description() {
		return widget_description;
	}

	public void setWidget_description(String widget_description) {
		this.widget_description = widget_description;
	}

	public String getWidget_div_id() {
		return widget_div_id;
	}

	public void setWidget_div_id(String widget_div_id) {
		this.widget_div_id = widget_div_id;
	}

	
}
