package com.suj1th.rabpubsub;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitQueueImpl implements IQueue {

	@Inject
	private SubscriberChannelFactory channelFactory ;
	
	@Inject
	private IMapperService mapperService;

	private static final boolean AUTO_ACK = true;

	/**
	 * no-args constructor
	 */
	RabbitQueueImpl(){
		
	}

	@Override
	public void consume(String queueName, final EventHandler handler)  {

		try {
			final Channel channel = channelFactory.getChannel();

			channel.basicConsume(queueName, AUTO_ACK, handler.getTag(),
					new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag,
						Envelope envelope,
						com.rabbitmq.client.AMQP.BasicProperties properties,
						byte[] body)
								throws IOException
				{
					
					long deliveryTag = envelope.getDeliveryTag();

					Object message = mapperService.deserialize(body, handler.getMethod().getGenericParameterTypes()[0].getClass());
					try {
						handler.handleEvent(message);
					} catch (InvocationTargetException e) {
						
					}
					
					/*channel.basicAck(deliveryTag, false);*/
				}
			});
		} catch (IOException e) {

		}
	}

}
