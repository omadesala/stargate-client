package com.omade.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(DoTask.class);

	@Override
	public void run() {
		logger.info("current time: " + System.currentTimeMillis());
	}

}
