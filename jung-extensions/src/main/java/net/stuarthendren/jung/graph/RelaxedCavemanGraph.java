package net.stuarthendren.jung.graph;

import net.stuarthendren.jung.element.IntegerFactory;
import net.stuarthendren.jung.graph.generator.RelaxedCavemanGraphGenerator;

import org.apache.commons.collections4.Factory;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class RelaxedCavemanGraph extends DelegatingGraph<Integer, Integer> implements UndirectedGraph<Integer, Integer> {

	public RelaxedCavemanGraph(double probability, double scalingCoefficient, int levels, int minCaveSize,
			int maxCaveSize) {
		this(probability, scalingCoefficient, levels, minCaveSize, maxCaveSize, System.currentTimeMillis());
	}

	public RelaxedCavemanGraph(double probability, double scalingCoefficient, int levels, int minCaveSize,
			int maxCaveSize, long seed) {
		super(generateGraph(probability, scalingCoefficient, levels, minCaveSize, maxCaveSize, seed));
	}

	private static UndirectedGraph<Integer, Integer> generateGraph(double probability, double scalingCoefficient,
			int levels, int minCaveSize, int maxCaveSize, long seed) {
		RelaxedCavemanGraphGenerator<Integer, Integer> generator = new RelaxedCavemanGraphGenerator<Integer, Integer>(
				new Factory<UndirectedGraph<Integer, Integer>>() {

					@Override
					public UndirectedGraph<Integer, Integer> create() {
						return new UndirectedSparseGraph<Integer, Integer>();
					}
				}, new IntegerFactory(), new IntegerFactory(), probability, scalingCoefficient, levels, minCaveSize,
				maxCaveSize, seed);
		return (UndirectedGraph<Integer, Integer>) generator.create();
	}

}
