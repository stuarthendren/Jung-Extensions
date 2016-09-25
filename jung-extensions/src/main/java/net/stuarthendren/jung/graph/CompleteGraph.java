package net.stuarthendren.jung.graph;

import net.stuarthendren.jung.element.IntegerFactory;
import net.stuarthendren.jung.graph.generator.CompleteGraphGenerator;

import org.apache.commons.collections4.Factory;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class CompleteGraph extends DelegatingGraph<Integer, Integer> implements UndirectedGraph<Integer, Integer> {

	public CompleteGraph(int numberOfVertices) {
		super(generateGraph(numberOfVertices));
	}

	private static UndirectedGraph<Integer, Integer> generateGraph(int numberOfVertices) {
		CompleteGraphGenerator<Integer, Integer> generator = new CompleteGraphGenerator<Integer, Integer>(
				new Factory<UndirectedGraph<Integer, Integer>>() {

					@Override
					public UndirectedGraph<Integer, Integer> create() {
						return new UndirectedSparseGraph<Integer, Integer>();
					}
				}, new IntegerFactory(), new IntegerFactory(), numberOfVertices);
		return (UndirectedGraph<Integer, Integer>) generator.create();
	}

}
