package net.stuarthendren.jung.graph.generator;

import java.util.Random;

import net.stuarthendren.jung.utils.UnorderedPair;
import net.stuarthendren.jung.utils.UnorderedPairs;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

/**
 * 
 * Create a randomly generated graph with a given number of vertices and probability of a link between any two edges
 * 
 * @author Stuart Hendren
 * 
 */
public class RandomGraphGenerator<V, E> implements GraphGenerator<V, E> {

	private final Factory<UndirectedGraph<V, E>> graphFactory;
	private final Factory<V> vertexFactory;
	private final Factory<E> edgeFactory;
	private final int numberOfVertices;
	private final double probability;
	private Random rand;

	public RandomGraphGenerator(Factory<UndirectedGraph<V, E>> graphFactory, Factory<V> vertexFactory,
			Factory<E> edgeFactory, int numberOfVertices, double probability) {
		this(graphFactory, vertexFactory, edgeFactory, numberOfVertices, probability, System.currentTimeMillis());
	}

	public RandomGraphGenerator(Factory<UndirectedGraph<V, E>> graphFactory, Factory<V> vertexFactory,
			Factory<E> edgeFactory, int numberOfVertices, double probability, long seed) {
		if (numberOfVertices < 0) {
			throw new IllegalArgumentException("Number of edges must be positive");
		}
		if (probability < 0.0 || probability > 1.0) {
			throw new IllegalArgumentException("Probability must be between 0 and 1");
		}
		this.graphFactory = graphFactory;
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
		this.numberOfVertices = numberOfVertices;
		this.probability = probability;
		rand = new Random(seed);
	}

	private UndirectedGraph<V, E> populateGraph(UndirectedGraph<V, E> graph) {
		for (int i = 0; i < numberOfVertices; i++) {
			graph.addVertex(vertexFactory.create());
		}
		for (UnorderedPair<V> pair : new UnorderedPairs<V>(graph.getVertices())) {
			if (rand.nextDouble() < probability) {
				graph.addEdge(edgeFactory.create(), pair.getA(), pair.getB());
			}
		}
		return graph;
	}

	/**
	 * 
	 * Create a complete graph, of size specified in the constructor.
	 * 
	 * @return complete graph
	 */
	@Override
	public Graph<V, E> create() {
		return populateGraph(graphFactory.create());
	}
}
