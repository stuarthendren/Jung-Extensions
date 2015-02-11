package net.stuarthendren.jung.graph;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

@SuppressWarnings("unchecked")
public class UndirectedGraphWrapperTest {

	@Test
	public void testUndirectedGraphWrapperGraph() {
		Graph<Integer, Integer> graph = mock(Graph.class);
		UndirectedGraphWrapper<Integer, Integer> undirectedGraphWrapper = new UndirectedGraphWrapper<Integer, Integer>(
				graph);
		assertTrue(undirectedGraphWrapper instanceof UndirectedGraph);
	}

	@Test
	public void testUndirectedGraphWrapperWithCheck() {
		Graph<Integer, Integer> graph = mock(Graph.class);
		UndirectedGraphWrapper<Integer, Integer> undirectedGraphWrapper = new UndirectedGraphWrapper<Integer, Integer>(
				graph, true);
		assertTrue(undirectedGraphWrapper instanceof UndirectedGraph);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testUndirectedGraphWrapperWithCheckThrowsException() {
		Graph<Integer, Integer> graph = mock(Graph.class);
		when(graph.getEdgeCount(EdgeType.DIRECTED)).thenReturn(10);
		UndirectedGraphWrapper<Integer, Integer> undirectedGraphWrapper = new UndirectedGraphWrapper<Integer, Integer>(
				graph, true);
	}

}
