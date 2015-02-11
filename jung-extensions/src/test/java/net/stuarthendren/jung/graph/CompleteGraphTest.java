package net.stuarthendren.jung.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;

public class CompleteGraphTest {

	@Test
	public void testGenerateEmptyGraph() {
		Graph<Integer, Integer> graph = new CompleteGraph(0);
		assertNotNull(graph);
		assertEquals(0, graph.getVertexCount());
		assertEquals(0, graph.getEdgeCount());
	}

	@Test
	public void testGenerateSingleVertexGraph() {
		Graph<Integer, Integer> graph = new CompleteGraph(1);
		assertNotNull(graph);
		assertEquals(1, graph.getVertexCount());
		assertEquals(0, graph.getEdgeCount());
	}

	@Test
	public void testGenerateTwoVertexGraph() {
		Graph<Integer, Integer> graph = new CompleteGraph(2);
		assertNotNull(graph);
		assertEquals(2, graph.getVertexCount());
		assertEquals(1, graph.getEdgeCount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeSize() {
		new CompleteGraph(-1);
	}

	@Test
	public void testGenerateCompleteGraph() {
		int size = (int) (Math.random() * 100);
		Graph<Integer, Integer> graph = new CompleteGraph(size);
		assertNotNull(graph);
		assertEquals(size, graph.getVertexCount());
		assertEquals((size * (size - 1)) / 2, graph.getEdgeCount());

		Collection<Integer> vertices = graph.getVertices();
		for (Integer v : vertices) {
			Collection<Integer> neighbors = graph.getNeighbors(v);
			assertEquals(size - 1, neighbors.size());
		}
	}

}
