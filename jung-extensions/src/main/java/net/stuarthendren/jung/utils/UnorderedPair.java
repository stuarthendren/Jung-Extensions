package net.stuarthendren.jung.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * A pair of type T that ignores order
 * 
 * @author Stuart Hendren
 * 
 * @param <T>
 */
public class UnorderedPair<T> {

	Set<T> set = new HashSet<T>();
	private final T a;
	private final T b;

	public UnorderedPair(T a, T b) {
		this.a = a;
		this.b = b;
		set.add(a);
		set.add(b);
	}

	@Override
	public int hashCode() {
		return set.hashCode();
	}

	public T getA() {
		return a;
	}

	public T getB() {
		return b;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UnorderedPair<?> other = (UnorderedPair<?>) obj;
		if (set == null) {
			if (other.set != null) {
				return false;
			}
		} else if (!set.equals(other.set)) {
			return false;
		}
		return true;
	}

}
