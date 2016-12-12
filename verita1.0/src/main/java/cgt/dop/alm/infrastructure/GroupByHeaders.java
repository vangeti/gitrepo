package cgt.dop.alm.infrastructure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
* 
* GroupByHeaders class
* 
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GroupByHeaders")
public class GroupByHeaders {

	@XmlElement(name = "GroupByHeader")
	protected List<GroupByHeader> groupByHeaders;

	public List<GroupByHeader> getGroupByHeaders() {
		return groupByHeaders;
	}

	public void setGroupByHeaders(List<GroupByHeader> groupByHeaders) {
		this.groupByHeaders = groupByHeaders;
	}

}
