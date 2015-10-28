package com.suj1th.rabpubsub;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class SubscriberConnectionConfig {
	
	private String host;
	private String port;
	private String bucket;
	private String password;
	
	private Properties config; 
	
	private final static String DEFAULT_CONFIG = "/com/suj1th/rabpubsub/rabpubsubConfig.properties";
	private final static String CUSTOM_CONFIG = "/rabpubsubConfig-custom.properties";
	private final static Logger LOGGER = Logger.getLogger(SubscriberConnectionConfig.class);
	
 
	
	/**
	 * no-args constructor.
	 * Loads the custom configuration for connecting to the RabbitMQ server; 
	 * In case of no custom configuration, uses the default configuration.
	 */
	public SubscriberConnectionConfig(){
		Class<?> claas;
		try {
			claas = Class.forName("/com/suj1th/rabpubsub/PublisherConnectionConfig");
			InputStream inputStream = claas.getResourceAsStream(DEFAULT_CONFIG);
			config.load(inputStream);
			
			/*Load properties from custom configuration, if available.*/
			inputStream = claas.getResourceAsStream(CUSTOM_CONFIG);
			if(inputStream!=null){
				config.load(inputStream);
			}
			
			host = config.getProperty("host");
			port = config.getProperty("port");
			bucket = config.getProperty("bucket");
			password = config.getProperty("password");
			
			
		} catch (ClassNotFoundException e) {
			LOGGER.error("Failed to load class 'PublisherConnectionConfig'", e);
			System.exit(1);
		} catch (IOException e) {
			LOGGER.error("Failed to load default configuration in 'PublisherConnectionConfig'", e);
			System.exit(1);
		}
		
	}



	public String getHost() {
		return host;
	}



	public void setHost(String host) {
		this.host = host;
	}



	public String getPort() {
		return port;
	}



	public void setPort(String port) {
		this.port = port;
	}



	public String getBucket() {
		return bucket;
	}



	public void setBucket(String bucket) {
		this.bucket = bucket;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PublisherConnectionConfig [host=").append(host)
				.append(", port=").append(port).append(", bucket=")
				.append(bucket).append(", password=").append(password)
				.append("]");
		return builder.toString();
	}
	

}
