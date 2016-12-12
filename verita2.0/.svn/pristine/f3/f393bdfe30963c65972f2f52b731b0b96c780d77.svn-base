package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "neoload_transactionsdetails")
public class NeoLoadTransactionDetails {
	//@OneToMany(mappedBy = "buildNo",cascade = CascadeType.ALL)
	@Id
	@Column(name="sno")
	private int sno;
	@Column(name="buildNo")
	private int buildNo;
	@Column(name="name")
	private  String transactionName;
	@Column(name="max")
	private String max;
	@Column(name="min")
	private String min;
	@Column(name="avg")
	private String avg;
	@Column(name="hits")
	private String hits;
	
	
	
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public int getBuildNo() {
		return buildNo;
	}
	public void setBuildNo(int buildNo) {
		this.buildNo = buildNo;
	}
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getAvg() {
		return avg;
	}
	public void setAvg(String avg) {
		this.avg = avg;
	}
	public String getHits() {
		return hits;
	}
	public void setHits(String hits) {
		this.hits = hits;
	}
	
	
	
	
	
	
}
