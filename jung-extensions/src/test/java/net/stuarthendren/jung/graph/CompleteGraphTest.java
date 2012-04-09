package net.stuarthendren.jung.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CompleteGraphTest {

	@Test
	public void testGenerateEmptyGraph() {
		Graph<Integer, Integer> graph = CompleteGraph.generateGraph(0);
		assertNotNull(graph);
		assertEquals(0, graph.getVertexCount());
		assertEquals(0, graph.getEdgeCount());
	}

	@Test
	public void testGenerateSingleVertexGraph() {
		Graph<Integer, Integer> graph = CompleteGraph.generateGraph(1);
		assertNotNull(graph);
		assertEquals(1, graph.getVertexCount());
		assertEquals(0, graph.getEdgeCount());
	}

	@Test
	public void testGenerateTwoVertexGraph() {
		Graph<Integer, Integer> graph = CompleteGraph.generateGraph(2);
		assertNotNull(graph);
		assertEquals(2, graph.getVertexCount());
		assertEquals(1, graph.getEdgeCount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeSize() {
		CompleteGraph.generateGraph(-1);
	}

	@Test
	public void testGenerateCompleteGraph() {
		int size = (int) (Math.random() * 100);
		Graph<Integer, Integer> graph = CompleteGraph.generateGraph(size);
		assertNotNull(graph);
		assertEquals(size, graph.getVertexCount());
		assertEquals((size * (size - 1)) / 2, graph.getEdgeCount());

		Collection<Integer> vertices = graph.getVertices();
		for (Integer v : vertices) {
			Collection<Integer> neighbors = graph.getNeighbors(v);
			assertEquals(size - 1, neighbors.size());
		}
	}

	@Test
	public void testIsComplete() {
		int size = (int) (Math.random() * 100);
		Graph<Integer, Integer> graph = CompleteGraph.generateGraph(size);
		assertTrue(CompleteGraph.isComplete(graph));
	}

	@Test
	public void testIsNotComplete() {
		int size = (int) (Math.random() * 99) + 2;
		int remove = (int) (Math.random() * size) + 1;
		Graph<Integer, Integer> graph = CompleteGraph.generateGraph(size);
		List<Integer> edges = new ArrayList<Integer>(graph.getEdges());
		for (int i = 0; i < remove; i++) {
			graph.removeEdge(edges.get(i));
		}

		assertFalse(CompleteGraph.isComplete(graph));
	}

}
