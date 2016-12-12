package cgt.dop.alm.log;

import org.apache.log4j.Logger;


public enum Debug {

	DEFAULT;

	static Logger logger = Logger.getLogger(Debug.class);

	public static boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void log(String message, Throwable e) {
		Debug.logger.debug(message, e);
	}

	public void log(String message) {
		Debug.logger.debug(message);
	}

}
