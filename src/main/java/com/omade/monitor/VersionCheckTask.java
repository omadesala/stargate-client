package com.omade.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omade.monitor.utils.HttpRequestUtil;

public class VersionCheckTask implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(VersionCheckTask.class);

	private String url;
	private String port;

	VersionCheckTask(String url, String port) {
		this.url = url;
		this.port = port;
	}

	@Override
	public void run() {
		logger.info("current time: " + System.currentTimeMillis());
		String json = "{\"value\":\"New\",\"onclick\":\"CreateNewDoc()\"}";
		String remoteURL = "http://" + this.url + ":" + this.port
				+ "/demo/devices";
		logger.info("remoteURL: " + remoteURL);
		HttpRequestUtil.doPost(remoteURL, null, json);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
