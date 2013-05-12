package net.stuarthendren.jung.utils;

/**
 * Utility type for creating delegating graphs by wrapping the vertices and
 * edges of the original
 * 
 * If a wapper is not empty then equals and hashcode delegate the the wrapped
 * object. If empty then object equals and hashcode are used.
 * 
 * 
 * @param <T>
 */
public class Wrapper<T> {

	private final T t;

	public Wrapper() {
		this.t = null;
	}

	public Wrapper(T t) {
		this.t = t;
	}

	public T unWrap() {
		return t;
	}

	@Override
	public int hashCode() {
		if (t != null) {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((t == null) ? 0 : t.hashCode());
			return result;
		} else {
			return super.hashCode();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (t == null) {
			return super.equals(obj);
		} else {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Wrapper other = (Wrapper) obj;
			if (!t.equals(other.t)) {
				return false;
			}
			return true;
		}
	}

	public static <T> Wrapper<T> wrap(T toWrap) {
		return new Wrapper<T>(toWrap);
	}

	public static <T> Wrapper<T> emptyWraper() {
		return new Wrapper<T>();
	}
}
