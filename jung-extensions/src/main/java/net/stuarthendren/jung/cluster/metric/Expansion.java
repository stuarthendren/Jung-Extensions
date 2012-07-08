package net.stuarthendren.jung.cluster.metric;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;

public class Expansion {

	public static <V, E> double expansion(Graph<V, E> graph, Set<V> cut) {
		return expansion(graph, cut, new Transformer<E, Integer>() {

			@Override
			public Integer transform(E input) {
				return Integer.valueOf(1);
			}
		});
	}

	public static <V, E> double expansion(Graph<V, E> graph, Set<V> cut, Transformer<E, ? extends Number> weights) {
		Set<V> complement = new HashSet<V>(graph.getVertices());
		complement.removeAll(cut);
		double sum = 0.0;
		for (V v : cut) {
			for (E e : graph.getIncidentEdges(v)) {
				if (complement.contains(graph.getOpposite(v, e))) {
					sum += weights.transform(e).doubleValue();
				}
			}
		}
		return sum / Math.min(cut.size(), complement.size());
	}
}
