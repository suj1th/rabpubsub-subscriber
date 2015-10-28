package com.suj1th.rabpubsub;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AnnotatedSubscriberFinder {

	private static final ConcurrentMap<Class<?>, ConcurrentMap<EventType, Method>> SUBSCRIBERS_CACHE = new ConcurrentHashMap<>();


	public static void loadAnnotatedMethods(Class<?> subscriberClass, ConcurrentMap<EventType, Method> methodMap){

		Method[] methods = subscriberClass.getDeclaredMethods();
		for(Method method: methods){

			if(method.isBridge()){
				continue;
			}

			if(method.isAnnotationPresent(Subscribe.class)){

				if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
					throw new IllegalArgumentException("Method " + method + " has @Subscribe annotation but is not 'public'.");
				}

				if(method.getGenericParameterTypes().length!=1){
					throw new IllegalArgumentException("Method " + method + " with @Subscribe annotation needs to have a single formal parameter");
				}

				EventType[] events = subscriberClass.getAnnotation(Subscribe.class).value();
				
				ConcurrentMap<EventType, Method> subscriberMethods = SUBSCRIBERS_CACHE.get(subscriberClass);
				if(subscriberMethods == null){
					subscriberMethods = new ConcurrentHashMap<>();
					SUBSCRIBERS_CACHE.put(subscriberClass, subscriberMethods);
				}

				for(EventType event : events){
					if(subscriberMethods.containsKey(event)){
						throw new DuplicateEventListenerException("Listener for Event " + event
								+" already exists. The existing listener is " 
								+ subscriberMethods.get(event));
					}

					Method existingMethod = methodMap.putIfAbsent(event, method);
					if(existingMethod != null){
						throw new DuplicateEventListenerException("Listener for Event " + event
								+" already exists. The existing listener is " 
								+ existingMethod);
					}

					subscriberMethods.put(event, method);

				}
			}
		}			
	}

	public static Map<EventType, EventHandler> findAllSubscribers(Object listener) {

		Map<EventType, EventHandler> handlersByType = new HashMap<>();

		Class<?> listenerClass = listener.getClass();
		ConcurrentMap<EventType, Method> methodsMap = SUBSCRIBERS_CACHE.get(listenerClass);

		if(methodsMap == null){
			methodsMap = new ConcurrentHashMap<>();
			loadAnnotatedMethods(listenerClass, methodsMap);
		}

		if(!methodsMap.isEmpty()){
			for(Entry<EventType, Method> methodEntry: methodsMap.entrySet()){
				EventHandler handler = new EventHandler(listener, methodEntry.getValue());
				handlersByType.putIfAbsent(methodEntry.getKey(), handler);
			}
		}
		
		return handlersByType;
	}
}
