package net.stuarthendren.jung.cluster.metric;

import java.util.Collections;

import net.stuarthendren.jung.graph.CompleteGraph;

import org.apache.commons.collections15.Transformer;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import static junit.framework.Assert.assertEquals;

public class ExpansionTest {

	@Test
	public void testCalculateGraphOfVESetOfV() {
		Graph<Integer, Integer> graph = new CompleteGraph(11);
		assertEquals(10.0, Expansion.expansion(graph, Collections.singleton(Integer.valueOf(0))), 0.000000000001);
	}

	@Test
	public void testCalculateGraphOfVESetOfVTransformerOfEQextendsNumber() {
		Graph<Integer, Integer> graph = new CompleteGraph(11);
		Transformer<Integer, Double> weights = new Transformer<Integer, Double>() {

			@Override
			public Double transform(Integer input) {
				return 1.0 / 10;
			}
		};

		assertEquals(1.0, Expansion.expansion(graph, Collections.singleton(Integer.valueOf(0)), weights),
				0.000000000001);
	}

}
