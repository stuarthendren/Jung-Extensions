package net.stuarthendren.jung.cluster.metric;

import static junit.framework.Assert.assertEquals;

import java.util.Collections;

import net.stuarthendren.jung.graph.CompleteGraph;

import org.apache.commons.collections4.Transformer;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;

public class ConductanceTest {

	@Test
	public void testConductanceGraphOfVESetOfV() {
		Graph<Integer, Integer> graph = new CompleteGraph(11);
		assertEquals(1.0, Conductance.conductance(graph, Collections.singleton(Integer.valueOf(0))), 0.000000000001);
	}

	@Test
	public void testContuctanceGraphOfVESetOfVTransformerOfEQextendsNumber() {
		Graph<Integer, Integer> graph = new CompleteGraph(11);
		Transformer<Integer, Double> weights = new Transformer<Integer, Double>() {

			@Override
			public Double transform(Integer input) {
				return 1.0 / 10;
			}
		};

		assertEquals(1.0, Conductance.conductance(graph, Collections.singleton(Integer.valueOf(0)), weights),
				0.000000000001);
	}

}
