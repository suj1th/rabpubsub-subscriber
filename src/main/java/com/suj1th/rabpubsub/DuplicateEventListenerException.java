package com.suj1th.rabpubsub;

/**
 * @author suj1th
 *
 */
public class DuplicateEventListenerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2770476084036876111L;

	/**
	 * 
	 */
	public DuplicateEventListenerException(){
		super();
	}
	
	/**
	 * @param message
	 */
	public DuplicateEventListenerException(String message){
		super(message);
	}

	/**
	 * @param message
	 * @param throwable
	 */
	public DuplicateEventListenerException(String message, Throwable throwable){
		super(message, throwable);
	}
	
	/**
	 * @param throwable
	 */
	public DuplicateEventListenerException(Throwable throwable){
		super(throwable);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DuplicateEventListenerException []: ");
		builder.append(super.getStackTrace());
		return builder.toString();
	}
	
	
}
