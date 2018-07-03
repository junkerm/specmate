package com.specmate.metrics;

/**
 * A metric that can go up and down.
 */
public interface IGauge {
	public void inc();

	public void dec();

	void dec(double amt);

	void inc(double amt);
}
