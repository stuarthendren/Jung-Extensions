package net.stuarthendren.jung.graph;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;

import static org.junit.Assert.assertTrue;

public class RelaxedCavemanGraphTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeProbability() {
		RelaxedCavemanGraph.generateGraph(-0.1, 10, 1, 5, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeScalingFactor() {
		RelaxedCavemanGraph.generateGraph(0.5, -10, 1, 5, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProbabilityGreaterThanOne() {
		RelaxedCavemanGraph.generateGraph(1.2, 1.2, 1, 5, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeLevels() {
		RelaxedCavemanGraph.generateGraph(0.5, 1.2, -10, 1, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeMin() {
		RelaxedCavemanGraph.generateGraph(0.5, 1.2, 1, -1, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeMax() {
		RelaxedCavemanGraph.generateGraph(0.5, 1.2, 1, 5, -10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMinNotGreaterThanMaxCaveSize() {
		RelaxedCavemanGraph.generateGraph(0.5, 1.2, 1, 10, 5);
	}

	@Test()
	public void testSizeOfGraph() {
		int levels = 3;
		int min = 5;
		int max = 10;
		Graph<Integer, Integer> graph = RelaxedCavemanGraph.generateGraph(0.5, 1.2, levels, min, max);
		double maxVerticies = Math.pow(max, levels + 1);
		int vertexCount = graph.getVertexCount();
		assertTrue("Number of verticies " + vertexCount + " exceeds max " + maxVerticies, maxVerticies > vertexCount);
	}
}
