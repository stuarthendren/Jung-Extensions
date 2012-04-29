package net.stuarthendren.jung.cluster.vertex;

import java.util.ArrayList;
import java.util.Collection;

import net.stuarthendren.jung.cluster.vertex.impl.AbstractClusterImpl;
import net.stuarthendren.jung.graph.CompleteGraph;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import static junit.framework.Assert.assertEquals;

public abstract class AbstractClusterImplTest {

	Collection<Integer> vertices = new ArrayList<Integer>();

	Graph<Integer, Integer> graph = CompleteGraph.generateGraph(5);
	AbstractClusterImpl<Integer, Integer> cluster;

	public void addVertices() {
		vertices.add(0);
		vertices.add(1);
		vertices.add(2);
	}

	@Test
	public void testGetGraph() {
		assertEquals(graph, cluster.getGraph());
	}

	@Test
	public void testGetDensity() {
		assertEquals(0.5, cluster.getDensity());
	}

	@Test
	public void testGetInducedGraph() {
		Graph<Integer, Integer> inducedGraph = cluster.getInducedGraph();
		assertEquals(3, inducedGraph.getVertexCount());
		CompleteGraph.isComplete(inducedGraph);
	}

}
