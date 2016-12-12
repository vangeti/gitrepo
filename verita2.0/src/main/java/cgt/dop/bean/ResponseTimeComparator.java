package cgt.dop.bean;

import java.util.Comparator;

public class ResponseTimeComparator implements Comparator<ResponseTimeBean> {
	
	public int compare(ResponseTimeBean obj1, ResponseTimeBean obj2) {		
	    int result  = obj1.getElapsedTime().compareTo(obj2.getElapsedTime());			
	    return result;

	}

}
