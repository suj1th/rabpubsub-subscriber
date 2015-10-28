package com.suj1th.rabpubsub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;



/**
 * @author suj1th
 *
 */
public class Subscriber {


	private ISubscriberFinder subscriberFinder;

	/** All registered event handlers, indexed by event type. */
	private final ConcurrentMap<EventType, EventHandler> handlersByType = new ConcurrentHashMap<>();
	
	private volatile IQueue queue;

	/**
	 * no-args constructor
	 */
	public Subscriber() {
		subscriberFinder = ISubscriberFinder.ANNOTATED;
		queue = new RabbitQueueImpl();
	}



	public void register(Object listener){

		if(listener == null){
			throw new IllegalArgumentException("A null Object cannot be registered as a Listener.");
		}

		Map<EventType, EventHandler> cachedSubscribersMap = this.subscriberFinder.findAllSubscribers(listener);
		for(EventType type: cachedSubscribersMap.keySet()){
			EventHandler subscriber = this._getHandlerByType(type);

			if(subscriber == null){
				final EventHandler cachedSubscriber = cachedSubscribersMap.get(type);
				if(handlersByType.putIfAbsent(type, cachedSubscriber) != null){
					throw new DuplicateEventListenerException();
				}
			}

			_setHandlerByType(type, subscriber);
			
			queue.consume(type.toString(), subscriber);
		}
	}

	private EventHandler _getHandlerByType(EventType type) {
		return handlersByType.get(type);
	}
	
	private EventHandler _setHandlerByType(EventType type,EventHandler handler) {
		return handlersByType.put(type, handler);
	}

}
