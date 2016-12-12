package cgt.dop.alm.log;

import org.apache.log4j.Logger;


public enum Error {

	DEFAULT;

	static Logger logger = Logger.getLogger(Error.class);

	public static boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public void log(String message, Throwable e) {
		Error.logger.debug(message, e);
	}

	public void log(String message) {
		Error.logger.debug(message);
	}

}
