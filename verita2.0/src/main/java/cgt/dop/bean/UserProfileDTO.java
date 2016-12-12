package cgt.dop.bean;

public class UserProfileDTO {

	private long maxAllThreads;
	
	private long startDateTimeStamp;

	private long endDateTimeStamp;

	private long rampUpTimeStamp;

	private long rampDownTimeStamp;

	public UserProfileDTO(long maxAllThreads, long startDateTimeStamp, long endDateTimeStamp, long rampUpTimeStamp, long rampDownTimeStamp) {
		super();
		this.maxAllThreads = maxAllThreads;
		this.startDateTimeStamp = startDateTimeStamp;
		this.endDateTimeStamp = endDateTimeStamp;
		this.rampUpTimeStamp = rampUpTimeStamp;
		this.rampDownTimeStamp = rampDownTimeStamp;
	}

	public long getMaxAllThreads() {
		return maxAllThreads;
	}

	public void setMaxAllThreads(long maxAllThreads) {
		this.maxAllThreads = maxAllThreads;
	}

	public long getStartDateTimeStamp() {
		return startDateTimeStamp;
	}

	public void setStartDateTimeStamp(long startDateTimeStamp) {
		this.startDateTimeStamp = startDateTimeStamp;
	}

	public long getEndDateTimeStamp() {
		return endDateTimeStamp;
	}

	public void setEndDateTimeStamp(long endDateTimeStamp) {
		this.endDateTimeStamp = endDateTimeStamp;
	}

	public long getRampUpTimeStamp() {
		return rampUpTimeStamp;
	}

	public void setRampUpTimeStamp(long rampUpTimeStamp) {
		this.rampUpTimeStamp = rampUpTimeStamp;
	}

	public long getRampDownTimeStamp() {
		return rampDownTimeStamp;
	}

	public void setRampDownTimeStamp(long rampDownTimeStamp) {
		this.rampDownTimeStamp = rampDownTimeStamp;
	}
}
