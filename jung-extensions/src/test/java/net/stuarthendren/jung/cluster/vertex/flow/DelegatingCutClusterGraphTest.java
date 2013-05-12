package net.stuarthendren.jung.cluster.vertex.flow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import net.stuarthendren.jung.element.IntegerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class DelegatingCutClusterGraphTest {

	private static final String A = "A";

	private static final String B = "B";

	private static final String C = "C";

	private static final String TARGET = "TARGET";

	private DelegatingCutClusterGraph<String, Integer> cutClusterGraph;

	@Before
	public void setUp() throws Exception {
		DirectedGraph<String, Integer> base = new DirectedSparseGraph<String, Integer>();
		IntegerFactory factory = new IntegerFactory();
		base.addVertex(A);
		base.addVertex(B);
		base.addVertex(C);
		base.addEdge(factory.create(), A, B);
		base.addEdge(factory.create(), B, C);
		cutClusterGraph = new DelegatingCutClusterGraph<String, Integer>(base, TARGET, factory);
	}

	@After
	public void tearDown() throws Exception {
		cutClusterGraph = null;
	}

	public void testGetTarget() throws Exception {
		assertEquals(TARGET, cutClusterGraph.getTarget());
	}

	@Test
	public void testGetEdges() {
		assertEquals(5, cutClusterGraph.getEdges().size());
	}

	@Test
	public void testGetInEdges() {
		assertEquals(3, cutClusterGraph.getInEdges(TARGET).size());
		assertTrue(cutClusterGraph.getInEdges(B).contains(Integer.valueOf(0)));
	}

	@Test
	public void testGetVertices() {
		Collection<String> vertices = cutClusterGraph.getVertices();
		assertEquals(4, vertices.size());
		assertTrue(vertices.contains(A));
		assertTrue(vertices.contains(B));
		assertTrue(vertices.contains(C));
		assertTrue(vertices.contains(TARGET));
	}

	@Test
	public void testGetOutEdges() {
		assertTrue(cutClusterGraph.getOutEdges(TARGET).isEmpty());
		assertEquals(2, cutClusterGraph.getOutEdges(A).size());
	}

	@Test
	public void testContainsVertex() {
		assertTrue(cutClusterGraph.containsVertex(TARGET));
	}

	@Test
	public void testGetPredecessors() {
		Collection<String> vertices = cutClusterGraph.getPredecessors(TARGET);
		assertEquals(3, vertices.size());
		assertTrue(vertices.contains(A));
		assertTrue(vertices.contains(B));
		assertTrue(vertices.contains(C));
		assertFalse(vertices.contains(TARGET));
	}

	@Test
	public void testContainsEdge() {
		assertTrue(cutClusterGraph.containsEdge(0));
		assertTrue(cutClusterGraph.containsEdge(3));
		assertFalse(cutClusterGraph.containsEdge(6));
	}

	@Test
	public void testGetEdgeCount() {
		assertEquals(5, cutClusterGraph.getEdgeCount());
	}

	@Test
	public void testGetSuccessors() {
		Collection<String> vertices = cutClusterGraph.getSuccessors(A);
		assertEquals(2, vertices.size());
		assertTrue(vertices.contains(TARGET));
		assertTrue(vertices.contains(B));
		assertTrue(cutClusterGraph.getSuccessors(TARGET).isEmpty());
	}

	@Test
	public void testGetVertexCount() {
		assertEquals(4, cutClusterGraph.getVertexCount());
	}

	@Test
	public void testGetNeighbors() {
		Collection<String> vertices = cutClusterGraph.getNeighbors(TARGET);
		assertEquals(3, vertices.size());
		assertTrue(vertices.contains(A));
		assertTrue(vertices.contains(B));
		assertTrue(vertices.contains(C));

		Collection<String> neighbors = cutClusterGraph.getNeighbors(C);
		assertEquals(2, neighbors.size());
		assertTrue(neighbors.contains(B));
		assertTrue(neighbors.contains(TARGET));
	}

	@Test
	public void testInDegree() {
		assertEquals(3, cutClusterGraph.inDegree(TARGET));
	}

	@Test
	public void testGetIncidentEdges() {
		Collection<Integer> incidentEdges = cutClusterGraph.getIncidentEdges(TARGET);
		assertEquals(3, incidentEdges.size());
		assertTrue(incidentEdges.contains(2));
		assertTrue(incidentEdges.contains(3));
		assertTrue(incidentEdges.contains(4));
		assertFalse(incidentEdges.contains(5));

		incidentEdges = cutClusterGraph.getIncidentEdges(B);
		assertEquals(3, incidentEdges.size());
		assertTrue(incidentEdges.contains(0));
		assertTrue(incidentEdges.contains(1));
	}

	@Test
	public void testOutDegree() {
		assertEquals(0, cutClusterGraph.outDegree(TARGET));
		assertEquals(2, cutClusterGraph.outDegree(A));
		assertEquals(2, cutClusterGraph.outDegree(B));
		assertEquals(1, cutClusterGraph.outDegree(C));
	}

	@Test
	public void testGetIncidentVertices() {
		assertTrue(cutClusterGraph.getIncidentVertices(0).contains(A));
		assertTrue(cutClusterGraph.getIncidentVertices(0).contains(B));
		assertTrue(cutClusterGraph.getIncidentVertices(3).contains(TARGET));
	}

	@Test
	public void testIsPredecessor() {
		assertTrue(cutClusterGraph.isPredecessor(A, TARGET));
		assertFalse(cutClusterGraph.isPredecessor(TARGET, A));
	}

	@Test
	public void testIsSuccessor() {
		assertFalse(cutClusterGraph.isSuccessor(A, TARGET));
		assertTrue(cutClusterGraph.isSuccessor(TARGET, A));
	}

	@Test
	public void testFindEdge() {
		assertEquals(Integer.valueOf(0), cutClusterGraph.findEdge(A, B));
		assertNotNull(cutClusterGraph.findEdge(B, TARGET));
	}

	@Test
	public void testGetPredecessorCount() {
		assertEquals(3, cutClusterGraph.getPredecessorCount(TARGET));
		assertEquals(1, cutClusterGraph.getPredecessorCount(B));
	}

	@Test
	public void testGetSuccessorCount() {
		assertEquals(0, cutClusterGraph.getSuccessorCount(TARGET));
		assertEquals(2, cutClusterGraph.getSuccessorCount(B));
	}

	@Test
	public void testGetSource() {
		assertEquals(A, cutClusterGraph.getSource(0));
	}

	@Test
	public void testGetDest() {
		assertEquals(B, cutClusterGraph.getDest(0));
		assertEquals(TARGET, cutClusterGraph.getDest(3));
	}

	@Test
	public void testIsSource() {
		assertTrue(cutClusterGraph.isSource(A, 0));
		assertFalse(cutClusterGraph.isSource(TARGET, 3));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddVertex() {
		cutClusterGraph.addVertex("ERROR");
	}

	@Test
	public void testIsDest() {
		assertFalse(cutClusterGraph.isDest(A, 0));
		assertTrue(cutClusterGraph.isDest(TARGET, 3));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddEdge() {
		cutClusterGraph.addEdge(10, A, C);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveVertex() {
		cutClusterGraph.removeVertex(TARGET);
	}

	@Test
	public void testGetEndpoints() {
		Pair<String> endpoints = cutClusterGraph.getEndpoints(0);
		assertTrue(endpoints.contains(A));
		assertTrue(endpoints.contains(B));
		endpoints = cutClusterGraph.getEndpoints(4);
		assertTrue(endpoints.contains(TARGET));
	}

	@Test
	public void testGetOpposite() {
		assertEquals(B, cutClusterGraph.getOpposite(A, 0));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveEdge() {
		cutClusterGraph.removeEdge(0);
	}

	@Test
	public void testIsNeighbor() {
		assertTrue(cutClusterGraph.isNeighbor(TARGET, A));
		assertTrue(cutClusterGraph.isNeighbor(B, TARGET));
	}

	@Test
	public void testIsIncident() {
		assertTrue(cutClusterGraph.isIncident(TARGET, 3));
		assertTrue(cutClusterGraph.isIncident(A, 3) || cutClusterGraph.isIncident(B, 3)
				|| cutClusterGraph.isIncident(C, 3));
	}

	@Test
	public void testDegree() {
		assertEquals(2, cutClusterGraph.degree(A));
		assertEquals(3, cutClusterGraph.degree(TARGET));
	}

	@Test
	public void testGetNeighborCount() {
		assertEquals(2, cutClusterGraph.getNeighborCount(C));
		assertEquals(3, cutClusterGraph.getNeighborCount(TARGET));
	}

	@Test
	public void testGetIncidentCount() {
		assertEquals(2, cutClusterGraph.getIncidentCount(0));
		assertEquals(2, cutClusterGraph.getIncidentCount(3));
	}

	@Test
	public void testGetEdgeType() {
		assertEquals(EdgeType.DIRECTED, cutClusterGraph.getEdgeType(0));
		assertEquals(EdgeType.DIRECTED, cutClusterGraph.getEdgeType(4));
	}

	@Test
	public void testGetDefaultEdgeType() {
		assertEquals(EdgeType.DIRECTED, cutClusterGraph.getDefaultEdgeType());
	}

	@Test
	public void testGetEdgeCountEdgeType() {
		assertEquals(cutClusterGraph.getEdgeCount(), cutClusterGraph.getEdgeCount(EdgeType.DIRECTED));
		assertEquals(0, cutClusterGraph.getEdgeCount(EdgeType.UNDIRECTED));
	}

}
