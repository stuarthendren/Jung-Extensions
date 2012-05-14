package net.stuarthendren.jung.graph.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * Relaxed caveman graph generator
 * 
 * A relaxed caveman graph is constructed in layers. The first layer is a cave - a random graph with probability of
 * connection <code>p</code> and size randomly chosen between a declared min and max cave size. Each subsequent layer
 * replaces the vertices of the previous layer with a cave with probability differing from the previous probability by
 * multiplying by the scaling coefficient, the links of the original vertex are distributed across the added vertices
 * randomly.
 * 
 * This graph can be used to test hierarchical clustering
 * 
 * @author Stuart Hendren
 * 
 */
public class RelaxedCavemanGraphGenerator<V, E> implements GraphGenerator<V, E> {

	private final Factory<UndirectedGraph<V, E>> graphFactory;
	private final Factory<V> vertexFactory;
	private final Factory<E> edgeFactory;
	private Random rand;
	private final double probability;
	private final double scalingCoefficient;
	private final int levels;
	private final int minCaveSize;
	private final int maxCaveSize;
	private final long seed;

	public RelaxedCavemanGraphGenerator(Factory<UndirectedGraph<V, E>> graphFactory, Factory<V> vertexFactory,
			Factory<E> edgeFactory, double probability, double scalingCoefficient, int levels, int minCaveSize,
			int maxCaveSize) {
		this(graphFactory, vertexFactory, edgeFactory, probability, scalingCoefficient, levels, minCaveSize,
				maxCaveSize, System.currentTimeMillis());
	}

	public RelaxedCavemanGraphGenerator(Factory<UndirectedGraph<V, E>> graphFactory, Factory<V> vertexFactory,
			Factory<E> edgeFactory, double probability, double scalingCoefficient, int levels, int minCaveSize,
			int maxCaveSize, long seed) {
		if (probability < 0.0 || probability > 1.0) {
			throw new IllegalArgumentException("Probability must be between 0 and 1");
		}
		if (scalingCoefficient < 0) {
			throw new IllegalArgumentException("Scaling coefficient of groups must be positive");
		}
		if (levels < 1) {
			throw new IllegalArgumentException("Number of levels must be positive");
		}
		if (minCaveSize < 0) {
			throw new IllegalArgumentException("Minimum cave size must be positive of levels must be positive");
		}
		if (maxCaveSize < minCaveSize) {
			throw new IllegalArgumentException("Max must be greater than min");
		}

		this.probability = probability;
		this.scalingCoefficient = scalingCoefficient;
		this.levels = levels;
		this.minCaveSize = minCaveSize;
		this.maxCaveSize = maxCaveSize;
		this.graphFactory = graphFactory;
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
		this.seed = seed;

		rand = new Random(seed);
	}

	/**
	 * 
	 * Create a relaxed caveman graph as specified in the constructor.
	 * 
	 * @return complete graph
	 */
	@Override
	public Graph<V, E> create() {
		// Use determined seed so random properties are different but consistent for the same input seed
		Graph<V, E> graph = getRandomGraph(getRandomSize(minCaveSize, maxCaveSize), probability, seed + 1);
		for (int i = 1; i <= levels; i++) {
			addLevel(getProbability(i, probability, scalingCoefficient), graph, minCaveSize, maxCaveSize);
		}
		return graph;
	}

	private void addLevel(double probability, Graph<V, E> graph, int minCaveSize, int maxCaveSize) {
		for (V v : new ArrayList<V>(graph.getVertices())) {
			Graph<V, E> cave = getRandomGraph(getRandomSize(minCaveSize, maxCaveSize), probability, seed + 1);
			replace(graph, v, cave);
		}
	}

	private void replace(Graph<V, E> graph, V vertex, Graph<V, E> cave) {
		Map<V, V> newVertices = new HashMap<V, V>();
		int baseVerticies = graph.getVertexCount();
		int baseEdges = graph.getEdgeCount();
		int caveSize = cave.getVertexCount();
		Iterator<V> iterator = cave.getVertices().iterator();
		// Map first to original vertex
		newVertices.put(iterator.next(), vertex);
		while (iterator.hasNext()) {
			V v = vertexFactory.create();
			newVertices.put(iterator.next(), v);
			graph.addVertex(v);
		}
		// re-distribute edges of v
		List<V> values = new ArrayList<V>(newVertices.values());
		Collection<E> incidentEdges = graph.getIncidentEdges(vertex);
		if (incidentEdges != null) {
			for (E edge : new ArrayList<E>(incidentEdges)) {
				V opposite = graph.getOpposite(vertex, edge);
				graph.removeEdge(edge);
				V replaceWith = values.get(getRandomSize(0, caveSize));
				graph.addEdge(edge, opposite, replaceWith);
			}
		}
		// add edges from cave
		for (E j : cave.getEdges()) {
			Pair<V> endpoints = cave.getEndpoints(j);
			graph.addEdge(edgeFactory.create(), newVertices.get(endpoints.getFirst()),
					newVertices.get(endpoints.getSecond()));
		}

	}

	private double getProbability(int level, double probability, double scalingCoefficient) {
		return Math.min(1.0, level * scalingCoefficient * probability);
	}

	private int getRandomSize(int min, int max) {
		return min + (rand.nextInt(max - min));
	}

	private Graph<V, E> getRandomGraph(int numberOfVertices, double prob, long subSeed) {
		return new RandomGraphGenerator<V, E>(graphFactory, vertexFactory, edgeFactory, numberOfVertices, prob, subSeed)
				.create();
	}

}
