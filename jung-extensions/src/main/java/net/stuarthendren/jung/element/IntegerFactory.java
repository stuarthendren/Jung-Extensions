package net.stuarthendren.jung.element;

import org.apache.commons.collections4.Factory;


public class IntegerFactory implements Factory<Integer> {

	int count = 0;

	@Override
	public Integer create() {
		return count++;
	}

}
