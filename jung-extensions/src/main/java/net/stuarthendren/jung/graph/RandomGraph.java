package net.stuarthendren.jung.graph;

import net.stuarthendren.jung.element.IntegerFactory;
import net.stuarthendren.jung.graph.generator.RandomGraphGenerator;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class RandomGraph extends DelegatingGraph<Integer, Integer> implements UndirectedGraph<Integer, Integer> {

	/**
	 * Create a randomly generated graph with a given number of vertices and probability of a link between any two edges
	 * 
	 * @param vertices
	 * @param probability
	 * @return Undirected randomly generated graph
	 */
	public RandomGraph(int numberOfVertices, double probability) {
		this(numberOfVertices, probability, System.currentTimeMillis());
	}

	/**
	 * Create a randomly generated graph with a given number of vertices and probability of a link between any two edges
	 * 
	 * @param vertices
	 * @param probability
	 * @param seed
	 *            for random number generation
	 * @return Undirected randomly generated graph
	 */
	public RandomGraph(int numberOfVertices, double probability, long seed) {
		super(generateGraph(numberOfVertices, probability, seed));
	}

	private static UndirectedGraph<Integer, Integer> generateGraph(int numberOfVertices, double probability, long seed) {
		RandomGraphGenerator<Integer, Integer> generator = new RandomGraphGenerator<Integer, Integer>(
				new Factory<UndirectedGraph<Integer, Integer>>() {

					@Override
					public UndirectedGraph<Integer, Integer> create() {
						return new UndirectedSparseGraph<Integer, Integer>();
					}
				}, new IntegerFactory(), new IntegerFactory(), numberOfVertices, probability, seed);
		return (UndirectedGraph<Integer, Integer>) generator.create();
	}

}
