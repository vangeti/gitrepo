package cgt.dop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "neoload_statistics")
public class NeoLoadStaticsGraphModel {
	@Id
	@Column(name="buildNo")
	private int buildNo;
	@Column(name="avg_hits")
	private String avgHits;
	@Column(name="avg_pages")
	private String avgPages;
	@Column(name="avg_reqresponsetime")
	private String avgReqResponseTime;
	@Column(name="avg_pageresponsetime")
	private String avgPageResponseTime;
	@Column(name="avg_throughput")
	private String avgThroughput;
	@Column(name="total_pages")
	private String totalPages;
	@Column(name="total_hits")
	private String totalHits;
	@Column(name="total_users_launched")
	private String totalUserLaunched;
	@Column(name="total_throughput")
	private String totalThroughtput;
	@Column(name="total_errors")
	private String totalErros;
	@Column(name="error_percentile")
	private String errorPercentile;
	@Column(name="testdate")
	private String testdate;
	@Column(name="test_duration")
	private String testduration;
	
	public int getBuildNo() {
		return buildNo;
	}
	public void setBuildNo(int buildNo) {
		this.buildNo = buildNo;
	}
	public String getAvgHits() {
		return avgHits;
	}
	public void setAvgHits(String avgHits) {
		this.avgHits = avgHits;
	}
	public String getAvgPages() {
		return avgPages;
	}
	public void setAvgPages(String avgPages) {
		this.avgPages = avgPages;
	}
	public String getAvgReqResponseTime() {
		return avgReqResponseTime;
	}
	public void setAvgReqResponseTime(String avgReqResponseTime) {
		this.avgReqResponseTime = avgReqResponseTime;
	}
	public String getAvgPageResponseTime() {
		return avgPageResponseTime;
	}
	public void setAvgPageResponseTime(String avgPageResponseTime) {
		this.avgPageResponseTime = avgPageResponseTime;
	}
	public String getAvgThroughput() {
		return avgThroughput;
	}
	public void setAvgThroughput(String avgThroughput) {
		this.avgThroughput = avgThroughput;
	}
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}
	public String getTotalHits() {
		return totalHits;
	}
	public void setTotalHits(String totalHits) {
		this.totalHits = totalHits;
	}
	public String getTotalUserLaunched() {
		return totalUserLaunched;
	}
	public void setTotalUserLaunched(String totalUserLaunched) {
		this.totalUserLaunched = totalUserLaunched;
	}
	public String getTotalThroughtput() {
		return totalThroughtput;
	}
	public void setTotalThroughtput(String totalThroughtput) {
		this.totalThroughtput = totalThroughtput;
	}
	public String getTotalErros() {
		return totalErros;
	}
	public void setTotalErros(String totalErros) {
		this.totalErros = totalErros;
	}
	public String getErrorPercentile() {
		return errorPercentile;
	}
	public void setErrorPercentile(String errorPercentile) {
		this.errorPercentile = errorPercentile;
	}
	public String getTestdate() {
		return testdate;
	}
	public void setTestdate(String testdate) {
		this.testdate = testdate;
	}
	public String getTestduration() {
		return testduration;
	}
	public void setTestduration(String testduration) {
		this.testduration = testduration;
	}
	
	
	
}
