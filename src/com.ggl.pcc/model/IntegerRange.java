package com.ggl.pcc.model;

public class IntegerRange implements NumberRange {
	
	private final int minimum, maximum, increment;

	public IntegerRange(int minimum, int maximum, int increment) {
		this.minimum = minimum;
		this.maximum = maximum;
		this.increment = increment;
	}

	@Override
	public Integer getMinimum() {
		return minimum;
	}

	@Override
	public Integer getMaximum() {
		return maximum;
	}

	@Override
	public Integer getIncrement() {
		return increment;
	}

}
