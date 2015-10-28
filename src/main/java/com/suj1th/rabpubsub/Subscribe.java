package com.suj1th.rabpubsub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * Marks a method as an event consumer.
 *
 * <p>The type field in the annotation defines the event type.
 * @author suj1th
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface Subscribe {

	 /**
     * Returns an array of the kinds of events an annotation type
     * can be applied to.
     * @return an array of the kinds of events an annotation type
     * can be applied to
     */
	EventType[] value();
}
