package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "neoload_graphimages")
public class NeoLoadGraphsImages {
	
	@Id
	@Column(name="buildNo")
	private int buildNo;
	
	@Column(name="avgresponsetimes")
	private byte[] avgresponsetimes;
	
	@Column(name="error")
	private byte[] error;

	public int getBuildNo() {
		return buildNo;
	}

	public void setBuildNo(int buildNo) {
		this.buildNo = buildNo;
	}

	public byte[] getAvgresponsetimes() {
		return avgresponsetimes;
	}

	public void setAvgresponsetimes(byte[] avgresponsetimes) {
		this.avgresponsetimes = avgresponsetimes;
	}

	public byte[] getError() {
		return error;
	}

	public void setError(byte[] error) {
		this.error = error;
	}

	
}
