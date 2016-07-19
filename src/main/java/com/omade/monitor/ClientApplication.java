/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.omade.monitor;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omade.monitor.utils.PropertiesUtil;

public class ClientApplication {

	private static final Logger logger = LoggerFactory
			.getLogger(ClientApplication.class);

	private static Properties config;

	public static void main(String[] args) throws Exception {

		logger.info("program start ...");

		@SuppressWarnings("deprecation")
		CommandLineParser parser = new BasicParser();
		Options options = new Options();
		options.addOption("h", "help", false, "Print this usage information");
		options.addOption("c", "config", true, "configure the properties");
		options.addOption("v", "version", false,
				"Print out Version information");
		CommandLine commandLine = parser.parse(options, args);
		String configPath = "application.properties";

		if (commandLine.hasOption('h')) {
			logger.info("Help Message");
			System.exit(0);
		}

		if (commandLine.hasOption('v')) {
			logger.info("Version Message");
		}

		if (commandLine.hasOption('c')) {
			logger.info("Configuration Message");
			configPath = commandLine.getOptionValue('c');
			logger.info("configPath" + configPath);
			try {
				config = PropertiesUtil.getProperties(configPath);
			} catch (IllegalStateException e) {
				logger.error(e.getMessage());
				System.exit(-1);
			}
		}

		executeFixedRate();
	}

	public static void executeFixedRate() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(
				new DoTask(config.getProperty("server.url"), config
						.getProperty("server.port")), 0, 1, TimeUnit.SECONDS);
	}

	public static void newVersionCheck() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(
				new DoTask(config.getProperty("server.url"), config
						.getProperty("server.port")), 0, 1, TimeUnit.SECONDS);
	}
}
