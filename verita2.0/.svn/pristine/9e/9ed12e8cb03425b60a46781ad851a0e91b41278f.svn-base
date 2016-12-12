package cgt.dop.bean;

public class AggregateReportBean implements Comparable<AggregateReportBean> {

	String transactionName;
	
	long count;
	
	long avg;
	
	long aggregate_report_90_line;
	
	long aggregate_report_min;
	
	long aggregate_report_max;
	
	double aggregate_report_error;

	

	public AggregateReportBean() {

	}

	public AggregateReportBean(String transactionName, long count, long avg, long aggregate_report_90_line,
			long aggregate_report_min, long aggregate_report_max, double aggregate_report_error) {
		super();
		this.transactionName = transactionName;
		this.count = count;
		this.avg = avg;
		this.aggregate_report_90_line = aggregate_report_90_line;
		this.aggregate_report_min = aggregate_report_min;
		this.aggregate_report_max = aggregate_report_max;
		this.aggregate_report_error = aggregate_report_error;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public long getAvg() {
		return avg;
	}

	public void setAvg(long avg) {
		this.avg = avg;
	}

	public long getAggregate_report_90_line() {
		return aggregate_report_90_line;
	}

	public void setAggregate_report_90_line(long aggregate_report_90_line) {
		this.aggregate_report_90_line = aggregate_report_90_line;
	}

	public long getAggregate_report_min() {
		return aggregate_report_min;
	}

	public void setAggregate_report_min(long aggregate_report_min) {
		this.aggregate_report_min = aggregate_report_min;
	}

	public long getAggregate_report_max() {
		return aggregate_report_max;
	}

	public void setAggregate_report_max(long aggregate_report_max) {
		this.aggregate_report_max = aggregate_report_max;
	}

	public double getAggregate_report_error() {
		return aggregate_report_error;
	}

	public void setAggregate_report_error(double aggregate_report_error) {
		this.aggregate_report_error = aggregate_report_error;
	}
	
	@Override
	public String toString() {
		return "AggregateReportBean [transactionName=" + transactionName + ", count=" + count + ", avg=" + avg
				+ ", aggregate_report_90_line=" + aggregate_report_90_line + ", aggregate_report_min="
				+ aggregate_report_min + ", aggregate_report_max=" + aggregate_report_max + ", aggregate_report_error="
				+ aggregate_report_error + "]";
	}

	@Override
	public int compareTo(AggregateReportBean other) {
		int result = this.getTransactionName().compareTo(other.getTransactionName());
		return result;
	}

}
