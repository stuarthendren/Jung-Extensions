package net.stuarthendren.jung.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

import static org.junit.Assert.assertEquals;

public class UnorderedPairsTest {

	@Test
	public void testIteratorWithSame() {
		Set<Integer> testSet = new HashSet<Integer>();
		testSet.add(1);
		testSet.add(2);
		UnorderedPairs<Integer> pairs = new UnorderedPairs<Integer>(testSet, true);
		Collection<UnorderedPair<Integer>> appearedSet = new HashSet<UnorderedPair<Integer>>();
		for (UnorderedPair<Integer> pair : pairs) {
			appearedSet.add(pair);
		}
		assertTrue(appearedSet.contains(new UnorderedPair<Integer>(1, 1)));
		assertTrue(appearedSet.contains(new UnorderedPair<Integer>(1, 2)));
		assertTrue(appearedSet.contains(new UnorderedPair<Integer>(2, 2)));
		assertEquals(3, appearedSet.size());
	}

	@Test
	public void testIteratorWithoutSame() {
		Set<Integer> testSet = new HashSet<Integer>();
		testSet.add(1);
		testSet.add(2);
		UnorderedPairs<Integer> pairs = new UnorderedPairs<Integer>(testSet);
		Collection<UnorderedPair<Integer>> appearedSet = new HashSet<UnorderedPair<Integer>>();
		for (UnorderedPair<Integer> pair : pairs) {
			appearedSet.add(pair);
		}
		assertEquals(1, appearedSet.size());
		assertTrue(appearedSet.contains(new UnorderedPair<Integer>(1, 2)));
	}
}
