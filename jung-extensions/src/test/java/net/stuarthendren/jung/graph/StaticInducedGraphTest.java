package net.stuarthendren.jung.graph;

import static junit.framework.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;

public class StaticInducedGraphTest extends AbstractInducedGraphTest {

	@Override
	public Graph<Integer, Integer> createInducedGraph(Graph<Integer, Integer> base, Set<Integer> vertices) {
		return new StaticInducedGraph<Integer, Integer>(base, vertices);
	}

	@Test
	public void testStatic() {
		int vertexCount1 = graph.getVertexCount();
		vertices.add(4);
		assertEquals(vertexCount1, graph.getVertexCount());
	}
}
