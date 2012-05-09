package net.stuarthendren.jung.utils;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UnorderedPairTest {

	@Test
	public void testGettersWhenDifferent() {
		UnorderedPair<Integer> pair = new UnorderedPair<Integer>(1, 2);
		Integer a = pair.getA();
		Integer b = pair.getB();
		assertTrue((a.equals(1) || a.equals(2)));
		assertTrue((b.equals(1) || b.equals(2)));
	}

	@Test
	public void testGettersWhenSame() {
		UnorderedPair<Integer> pair = new UnorderedPair<Integer>(1, 1);
		assertEquals(Integer.valueOf(1), pair.getA());
		assertEquals(Integer.valueOf(1), pair.getB());
	}

	@Test
	public void testEqualsObject() {
		UnorderedPair<Integer> pair1 = new UnorderedPair<Integer>(1, 2);
		UnorderedPair<Integer> pair2 = new UnorderedPair<Integer>(1, 2);
		UnorderedPair<Integer> pair3 = new UnorderedPair<Integer>(2, 1);
		UnorderedPair<Integer> pair4 = new UnorderedPair<Integer>(3, 2);
		assertEquals(pair1, pair2);
		assertEquals(pair2, pair3);
		assertFalse(pair3.equals(pair4));
	}

	@Test
	public void testHashCode() {
		UnorderedPair<Integer> pair1 = new UnorderedPair<Integer>(1, 2);
		UnorderedPair<Integer> pair2 = new UnorderedPair<Integer>(1, 2);
		UnorderedPair<Integer> pair3 = new UnorderedPair<Integer>(2, 1);
		assertEquals(pair1.hashCode(), pair2.hashCode());
		assertEquals(pair2.hashCode(), pair3.hashCode());
	}
}
