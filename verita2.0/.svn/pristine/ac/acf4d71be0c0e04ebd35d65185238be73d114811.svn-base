package cgt.dop.bean;

import java.util.Date;

public class HitsBean implements Comparable<HitsBean>{

    private Long hits;

    private long elapsedTime;
    
    
   
    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long l) {
        this.elapsedTime = l;
    }

	public Long getHits() {
		return hits;
	}

	public void setHits(Long userHits) {
		this.hits = userHits;
	}
	

	@Override
	public int compareTo(HitsBean other) {
			return this.getElapsedTime() < other.getElapsedTime() ? -1 : this.getElapsedTime()  > other.getElapsedTime() ? 1 : 0;
		}

	@Override
	public String toString() {
		return "HitsBean [hits=" + hits + ", elapsedTime=" + elapsedTime + "]";
	}

  

}
