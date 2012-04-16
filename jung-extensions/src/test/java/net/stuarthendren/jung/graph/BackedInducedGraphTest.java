package net.stuarthendren.jung.graph;

import java.util.Set;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import static junit.framework.Assert.assertEquals;

public class BackedInducedGraphTest extends AbstractInducedGraphTest {

	@Override
	public Graph<Integer, Integer> createInducedGraph(Graph<Integer, Integer> base, Set<Integer> vertices) {
		return new BackedInducedGraph<Integer, Integer>(base, vertices);
	}

	@Test
	public void testDynamic() {
		int vertexCount1 = graph.getVertexCount();
		vertices.add(4);
		assertEquals(vertexCount1 + 1, graph.getVertexCount());
		assertEquals(6, graph.getEdgeCount());
	}
}
