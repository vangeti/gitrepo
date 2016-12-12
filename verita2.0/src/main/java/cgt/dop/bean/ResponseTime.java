package cgt.dop.bean;

import java.util.List;
import java.util.Map;

public class ResponseTime {
	
	private Object[] trnNames;
	
	private Object[] elapseTimes;
	
	private Map<String, List<ResponseTimeDTO>> resultMap;
	
	public Object[] getTrnNames() {
		return trnNames;
	}

	public void setTrnNames(Object[] trnNames) {
		this.trnNames = trnNames;
	}

	public Map<String, List<ResponseTimeDTO>> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, List<ResponseTimeDTO>> resultMap) {
		this.resultMap = resultMap;
	}

	public Object[] getElapseTimes() {
		return elapseTimes;
	}

	public void setElapseTimes(Object[] elapseTimes) {
		this.elapseTimes = elapseTimes;
	}

	

}
