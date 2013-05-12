package net.stuarthendren.jung.cluster.vertex.flow;

import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * For cut based cluster calculations the graph needs an additional target
 * vertex to be added and connected to each vertex of the base graph - this
 * isterface allows different methods to produce that graph
 * 
 * @param <V>
 * @param <E>
 */
public interface CutClusterGraph<V, E> extends DirectedGraph<V, E> {

	V getTarget();

}
