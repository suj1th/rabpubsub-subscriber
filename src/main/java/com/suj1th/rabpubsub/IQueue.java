package com.suj1th.rabpubsub;

public interface IQueue {
	
	public void consume(String queueName, EventHandler handler);

}
