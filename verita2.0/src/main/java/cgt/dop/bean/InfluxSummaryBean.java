package cgt.dop.bean;


public class InfluxSummaryBean {
    
    private String allThreads;
    
    private String timeStamp;
    
    private String responseCode;
    
    private String latency;
    
    private String bytes;
    
    private String label;

    public String getAllThreads() {
        return allThreads;
    }

    public void setAllThreads(String allThreads) {
        this.allThreads = allThreads;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "InfluxSummaryBean [timeStamp=" + timeStamp + ", latency=" + latency + "]";
	}


}
