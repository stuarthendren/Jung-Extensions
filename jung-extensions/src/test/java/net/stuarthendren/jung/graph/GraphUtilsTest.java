package net.stuarthendren.jung.graph;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

public class GraphUtilsTest {

	@Test
	public void testSingleNodeIsComplete() {
		UndirectedGraph<Integer, Integer> graph = new CompleteGraph(1);
		assertTrue(GraphUtils.isComplete(graph));
	}

	@Test
	public void testIsComplete() {
		int size = (int) (Math.random() * 100);
		UndirectedGraph<Integer, Integer> graph = new CompleteGraph(size);
		assertTrue(GraphUtils.isComplete(graph));
	}

	@Test
	public void testLoopIsComplete() {
		int size = (int) (Math.random() * 100);
		UndirectedGraph<Integer, Integer> looped = new CompleteGraph(size);

		looped.addEdge(-1, 0, 0);
		assertTrue(GraphUtils.isComplete(looped));
	}

	@Test
	public void testIsNotComplete() {
		int size = (int) (Math.random() * 99) + 2;
		int remove = (int) (Math.random() * size) + 1;
		UndirectedGraph<Integer, Integer> graph = new CompleteGraph(size);
		List<Integer> edges = new ArrayList<Integer>(graph.getEdges());
		for (int i = 0; i < remove; i++) {
			graph.removeEdge(edges.get(i));
		}

		assertFalse(GraphUtils.isComplete(graph));
	}

	@Test
	public void testLoopIsNotComplete() {
		UndirectedGraph<Integer, Integer> looped = new CompleteGraph(10);
		looped.removeEdge(0);
		looped.addEdge(-1, 0, 0);

		assertFalse(GraphUtils.isComplete(looped));
	}

	@Test
	public void testIsUndirected() {
		Graph<Integer, Integer> graph = new SparseGraph<Integer, Integer>();
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addEdge(1, 1, 2, EdgeType.UNDIRECTED);
		assertTrue(GraphUtils.isUndirected(graph));
	}

	@Test
	public void testIsNotUndirected() {
		Graph<Integer, Integer> graph = new SparseGraph<Integer, Integer>();
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addEdge(1, 1, 2, EdgeType.DIRECTED);
		assertFalse(GraphUtils.isUndirected(graph));
	}

}
