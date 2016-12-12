package cgt.dop.alm.infrastructure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
* 
* GroupByHeader class
* 
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GroupByHeader")
public class GroupByHeader {

	@XmlAttribute(name = "Name")
	protected String name;

	@XmlAttribute(name = "Value")
	protected String value;
	
	@XmlAttribute(name = "size")
	protected int size;
	

	@XmlElement(name = "GroupByHeader")
	protected List<GroupByHeader> groupByHeaders;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<GroupByHeader> getGroupByHeaders() {
		return groupByHeaders;
	}

	public void setGroupByHeaders(List<GroupByHeader> groupByHeaders) {
		this.groupByHeaders = groupByHeaders;
	}

}
