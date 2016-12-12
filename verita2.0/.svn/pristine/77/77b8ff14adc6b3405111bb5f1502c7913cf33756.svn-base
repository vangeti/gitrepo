package cgt.dop.bean;

import java.util.Date;

public class ActiveThreadsBean implements Comparable<ActiveThreadsBean>{

    private long activeThreads;

    private Date elapsedTime;

    public long getActiveThreads() {
        return activeThreads;
    }

    public void setActiveThreads(long activeThreads) {
        this.activeThreads = activeThreads;
    }

    public Date getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Date elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
    

	@Override
	public int compareTo(ActiveThreadsBean other) {
    	return this.getElapsedTime().compareTo(other.getElapsedTime());		
	}

	@Override
	public String toString() {
		return "ActiveThreadsBean [activeThreads=" + activeThreads
				+ ", elapsedTime=" + elapsedTime + "]";
	}

	


}
