package cgt.dop.bean;
public class BuildInfoBean {

	private String number;
	private String result;
	private String timestamp;
	private String duration;
	private String executedtime;
	public String getExecutedtime() {
		return executedtime;
	}

	public void setExecutedtime(String executedtime) {
		this.executedtime = executedtime;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
}
