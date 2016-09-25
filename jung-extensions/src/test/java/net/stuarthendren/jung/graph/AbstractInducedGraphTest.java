package net.stuarthendren.jung.graph;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public abstract class AbstractInducedGraphTest {

	Graph<Integer, Integer> base;
	Graph<Integer, Integer> graph;
	Set<Integer> vertices;

	@Before
	public void setUp() throws Exception {
		vertices = new HashSet<Integer>();
		vertices.add(0);
		vertices.add(1);
		vertices.add(2);
		base = new CompleteGraph(6);
		graph = createInducedGraph(base, vertices);
	}

	abstract Graph<Integer, Integer> createInducedGraph(Graph<Integer, Integer> base2, Set<Integer> vertices2);

	@After
	public void tearDown() throws Exception {
		graph = null;
	}

	@Test
	public void testGetEdges() {
		Collection<Integer> edges = graph.getEdges();
		assertTrue(base.getEdges().containsAll(edges));
		assertEquals(3, edges.size());
		for (Integer e : edges) {
			assertTrue(vertices.containsAll(base.getEndpoints(e)));
		}
	}

	@Test
	public void testGetVertices() {
		assertTrue(CollectionUtils.isEqualCollection(vertices, graph.getVertices()));
	}

	@Test
	public void testGetInEdges() {
		assertEquals(2, graph.getInEdges(0).size());
	}

	@Test
	public void testGetOutEdges() {
		assertEquals(2, graph.getOutEdges(0).size());
	}

	@Test
	public void testContainsVertex() {
		assertTrue(graph.containsVertex(0));
		assertFalse(graph.containsVertex(4));
	}

	@Test
	public void testContainsEdge() {
		assertTrue(graph.containsEdge(base.findEdge(0, 1)));
		assertFalse(graph.containsEdge(base.findEdge(0, 3)));
	}

	@Test
	public void testGetEdgeCount() {
		assertEquals(3, graph.getEdgeCount());
	}

	@Test
	public void testGetVertexCount() {
		assertEquals(3, graph.getVertexCount());
	}

	@Test
	public void testGetNeighbors() {
		Collection<Integer> neighbors = graph.getNeighbors(0);
		assertEquals(2, neighbors.size());
		assertTrue(neighbors.contains(1));
		assertTrue(neighbors.contains(2));
	}

	@Test
	public void testGetIncidentEdges() {
		Collection<Integer> incidentEdges = graph.getIncidentEdges(0);
		assertEquals(2, incidentEdges.size());
	}

	@Test
	public void testGetIncidentVertices() {
		Collection<Integer> incidentVertices = graph.getIncidentVertices(base.findEdge(0, 1));
		assertEquals(2, incidentVertices.size());
		assertTrue(incidentVertices.contains(0));
		assertTrue(incidentVertices.contains(1));
	}

	@Test
	public void testFindEdge() {
		assertNull(graph.findEdge(0, 3));
		assertNotNull(graph.findEdge(0, 1));
	}

	@Test
	public void testFindEdgeSet() {
		assertTrue(graph.findEdgeSet(0, 3).isEmpty());
		Collection<Integer> edgeSet = graph.findEdgeSet(0, 1);
		assertEquals(1, edgeSet.size());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddVertex() {
		graph.addVertex(10);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddEdgeECollectionOfQextendsV() {
		graph.addEdge(10, Arrays.asList(1, 2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveVertex() {
		graph.removeVertex(0);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveEdge() {
		graph.removeEdge(0);
	}

	@Test
	public void testIsNeighbor() {
		assertTrue(graph.isNeighbor(0, 1));
		assertTrue(graph.isNeighbor(0, 1));
		assertFalse(graph.isNeighbor(4, 0));
	}

	@Test
	public void testIsIncident() {
		assertTrue(graph.isIncident(0, base.findEdge(0, 1)));
		assertFalse(graph.isIncident(0, base.findEdge(0, 5)));
	}

	@Test
	public void testDegree() {
		assertEquals(2, graph.degree(0));
	}

	@Test
	public void testInDegree() {
		assertEquals(2, graph.inDegree(1));
	}

	@Test
	public void testOutDegree() {
		assertEquals(2, graph.outDegree(2));
	}

	@Test
	public void testGetNeighborCount() {
		assertEquals(2, graph.getNeighborCount(0));
		assertEquals(2, graph.getNeighborCount(1));
		assertEquals(2, graph.getNeighborCount(2));
	}

	@Test
	public void testGetIncidentCount() {
		graph.getIncidentCount(base.findEdge(0, 1));
	}

	@Test
	public void testGetEdgeType() {
		assertEquals(graph.getEdgeType(0), base.getEdgeType(0));
	}

	@Test
	public void testGetDefaultEdgeType() {
		assertEquals(graph.getDefaultEdgeType(), base.getDefaultEdgeType());
	}

	@Test
	public void testGetEdgesEdgeType() {
		assertEquals(3, graph.getEdges(EdgeType.UNDIRECTED).size());
		assertEquals(0, graph.getEdges(EdgeType.DIRECTED).size());
	}

	@Test
	public void testGetEdgeCountEdgeType() {
		assertEquals(3, graph.getEdgeCount(EdgeType.UNDIRECTED));
		assertEquals(0, graph.getEdgeCount(EdgeType.DIRECTED));
	}

	@Test
	public void testGetPredecessorCount() {
		assertEquals(2, graph.getPredecessorCount(0));
	}

	@Test
	public void testGetSuccessorCount() {
		assertEquals(2, graph.getPredecessorCount(0));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddEdgeEVV() {
		graph.addEdge(10, 11, 12);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddEdgeEVVEdgeType() {
		graph.addEdge(10, 11, 12, EdgeType.DIRECTED);
	}

	@Test
	public void testGetEndpoints() {
		Pair<Integer> endpoints = graph.getEndpoints(base.findEdge(0, 1));
		assertTrue(endpoints.contains(0));
		assertTrue(endpoints.contains(1));
		assertNull(graph.getEndpoints(base.findEdge(0, 5)));
	}

	@Test
	public void testGetOpposite() {
		assertNull(graph.getOpposite(0, base.findEdge(0, 5)));
		assertEquals(Integer.valueOf(1), graph.getOpposite(0, base.findEdge(0, 1)));
	}

	@Test
	public void testGetIncidentVerticies() {
		Integer contained = base.findEdge(0, 1);
		Integer notContained = base.findEdge(0, 5);
		assertEquals(0, graph.getIncidentVertices(notContained).size());
		assertEquals(2, graph.getIncidentVertices(contained).size());
	}

	@Test
	public void testGetSucsessors() {
		assertEquals(2, graph.getSuccessors(0).size());
	}

	@Test
	public void testIsSucsessors() {
		assertTrue(graph.isSuccessor(0, 1));
		assertFalse(graph.isSuccessor(0, 3));
	}

	@Test
	public void testIsPredecessor() {
		assertTrue(graph.isPredecessor(0, 1));
		assertFalse(graph.isPredecessor(0, 3));
	}
}
