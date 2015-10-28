package com.suj1th.rabpubsub;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/** Convenience Factory to facilitate opening a {@link Channel} to a RabbitMQ server.
 * 
 * @author suj1th
 *
 */
@Singleton
public class SubscriberChannelFactory {

	private static ConnectionFactory factory;
	private static SubscriberConnectionConfig config;
	private static Connection CONNECTION;
	
	private static final Logger LOGGER = Logger.getLogger(SubscriberChannelFactory.class);
	
	@Inject
	public static void setConfig(SubscriberConnectionConfig config) {
		SubscriberChannelFactory.config = config;
	}

	/**
	 * no-args constructor.
	 *  
	 */
	public SubscriberChannelFactory(){
		this.init();
	}
	
	
	private void init() {
		
		try {
			
			factory = new ConnectionFactory();
			factory.setHost(config.getHost());
			factory.setPort(Integer.parseInt((config.getPort())));
			factory.setUsername(config.getBucket());
			factory.setPassword(config.getPassword());
			
			
			CONNECTION = factory.newConnection();
			
		} catch (NumberFormatException e) {
			LOGGER.error(String.format("Invalid Port Number %s in Configuration",config.getPort()), e);
			System.exit(1);
		} catch (Exception e){
			LOGGER.error("Could not Initialise PublisherConnectionFactory for RabPubSub", e);
			System.exit(1);
		}
		
	}
	
	
	public Channel getChannel() throws IOException{
		return CONNECTION.createChannel();
	}
	
}
