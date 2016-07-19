package com.omade.monitor.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class PropertiesUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesUtil.class);

	public static void main(String[] args) {
		try {
			PropertiesUtil.getProperties("application.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getProperties(String filePath) throws IOException {

		if (Strings.isNullOrEmpty(filePath)) {
			filePath = "application.properties";
			logger.info("filePath is null or empty,set default to application.properties");
		}

		InputStream inputStream = PropertiesUtil.class.getClassLoader()
				.getResourceAsStream(filePath);

		Properties properties = new Properties();
		try {
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new IllegalStateException("config file not found !");
			}
		} catch (IOException ioE) {
			ioE.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
		System.out.println("properties: " + properties.toString());
		return properties;
	}
}