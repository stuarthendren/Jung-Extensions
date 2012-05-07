package net.stuarthendren.jung.graph.generator;

import java.util.Collection;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

/**
 * 
 * A graph is complete if every vertex has an edge to every other vertex.
 * 
 * @author Stuart Hendren
 * 
 */
public class CompleteGraphGenerator<V, E> implements GraphGenerator<V, E> {

	private final Factory<UndirectedGraph<V, E>> graphFactory;
	private final Factory<V> vertexFactory;
	private final Factory<E> edgeFactory;
	private final int numberOfVertices;

	public CompleteGraphGenerator(Factory<UndirectedGraph<V, E>> graphFactory, Factory<V> vertexFactory,
			Factory<E> edgeFactory, int numberOfVertices) {
		if (numberOfVertices < 0) {
			throw new IllegalArgumentException("Number of edges must be positive");
		}
		this.graphFactory = graphFactory;
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
		this.numberOfVertices = numberOfVertices;
	}

	private UndirectedGraph<V, E> populateGraph(UndirectedGraph<V, E> graph) {
		for (int i = 0; i < numberOfVertices; i++) {
			graph.addVertex(vertexFactory.create());
		}
		Collection<V> vertices = graph.getVertices();
		for (V v1 : vertices) {
			for (V v2 : vertices) {
				if (!v1.equals(v2) && !graph.isNeighbor(v1, v2)) {
					graph.addEdge(edgeFactory.create(), v1, v2);
				}
			}
		}
		return graph;
	}

	@Override
	/**
	 * 
	 * Create a complete graph, of size specified in the constructor.
	 * 
	 * @return complete graph
	 */
	public Graph<V, E> create() {
		return populateGraph(graphFactory.create());
	}
}
