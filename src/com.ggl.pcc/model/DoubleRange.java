package com.ggl.pcc.model;

public class DoubleRange implements NumberRange {

	private final double minimum, maximum, increment;

	private final int precision;

	public DoubleRange(double minimum, double maximum, double increment,
			int precision) {
		this.minimum = minimum;
		this.maximum = maximum;
		this.increment = increment;
		this.precision = precision;
	}

	@Override
	public Double getMinimum() {
		return minimum;
	}

	@Override
	public Double getMaximum() {
		return maximum;
	}

	@Override
	public Double getIncrement() {
		return increment;
	}

	public int getPrecision() {
		return precision;
	}

}
