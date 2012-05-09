package net.stuarthendren.jung.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * UnorderdPair allows the creation of iterators of unordered pair from the base collection
 * 
 * @author Stuart Hendren
 * 
 * @param <T>
 */
public class UnorderedPairs<T> implements Iterable<UnorderedPair<T>> {

	Set<UnorderedPair<T>> backingSet = new HashSet<UnorderedPair<T>>();

	/**
	 * By default iterator does not include self pairs
	 * 
	 * @param collection
	 *            to choose pairs from
	 */
	public UnorderedPairs(Collection<T> collection) {
		this(collection, false);
	}

	/**
	 * @param collection
	 *            to choose pairs from
	 * @param includeSelfPairs
	 *            true to include pairs where both elements are the same
	 */
	public UnorderedPairs(Collection<T> collection, boolean includeSelfPairs) {
		for (T t1 : collection) {
			for (T t2 : collection) {
				if (includeSelfPairs || !t1.equals(t2)) {
					backingSet.add(new UnorderedPair<T>(t1, t2));
				}
			}
		}
	}

	@Override
	public Iterator<UnorderedPair<T>> iterator() {
		return backingSet.iterator();
	}

}
