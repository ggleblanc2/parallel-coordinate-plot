package com.ggl.pcc.model;

/**
 * This interface defines an abstract number range with a minimum value, a
 * maximum value, and an increment. Implementing classes can add additional
 * fields to fully define a range.
 * 
 * @author Gilbert G. Le Blanc
 *
 */
public interface NumberRange {

	public Number getMinimum();

	public Number getMaximum();

	public Number getIncrement();

}
