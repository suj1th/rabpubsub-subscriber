package com.suj1th.rabpubsub;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author suj1th
 *
 */
public class EventHandler {
	
	private final Object target;
	
	private final Method method;
	
	private final int hashCode;
	
	private boolean isValid;
	
	private String tag;
	
	private static long suffix = 0L;
	
	private static final String TAG_PREFIX = "HANDLER";
	
	
	public EventHandler(Object target,Method method) {
		if(target == null || method == null){
			throw new IllegalArgumentException("Null parameters used to initialise EventHandler.");
		}
		this.target=target;
		this.method=method;
		method.setAccessible(true);
		
		this.hashCode= hashCode();
		this.isValid=true;
		this.tag = TAG_PREFIX + ++suffix;
	}

	public void handleEvent(Object event) throws InvocationTargetException{
		
		if(!this.isValid){
			throw new IllegalStateException("EventHandler#handleEvent() called on an "
					+ "invalid EventHandler object.");
		}		
		
			try {
				method.invoke(target, event);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new InvocationTargetException(e);
			}
			
	}
	
	public boolean isValid() {
		return isValid;
	}


	public void invalidate() {
		this.isValid = false;
	}


	public Object getTarget() {
		return target;
	}


	public Method getMethod() {
		return method;
	}

	

	public int getHashCode() {
		return hashCode;
	}

	

	public String getTag() {
		return tag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashCode;
		result = prime * result + (isValid ? 1231 : 1237);
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventHandler other = (EventHandler) obj;
		if (hashCode != other.hashCode)
			return false;
		if (isValid != other.isValid)
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}
	
	

}
