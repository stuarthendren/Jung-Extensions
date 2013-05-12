package net.stuarthendren.jung.cluster.vertex.flow;

import static net.stuarthendren.jung.utils.Wrapper.wrap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import net.stuarthendren.jung.element.IntegerFactory;
import net.stuarthendren.jung.utils.Wrapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

public class WrapperCutClusterGraphTest {

	private static final String A = "A";

	private static final String B = "B";

	private static final String C = "C";

	private Wrapper<String> TARGET;

	private WrappedCutClusterGraph<String, Integer> cutClusterGraph;

	@Before
	public void setUp() throws Exception {
		createCutClusterGraph();
	}

	protected void createCutClusterGraph() {
		DirectedGraph<String, Integer> base = new DirectedSparseGraph<String, Integer>();
		IntegerFactory factory = new IntegerFactory();
		base.addVertex(A);
		base.addVertex(B);
		base.addVertex(C);
		base.addEdge(factory.create(), A, B);
		base.addEdge(factory.create(), B, C);
		cutClusterGraph = new WrappedCutClusterGraph(base);
		TARGET = cutClusterGraph.getTarget();
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
		assertTrue(cutClusterGraph.getInEdges(wrap(B)).contains(wrap(0)));
	}

	@Test
	public void testGetVertices() {
		Collection<Wrapper<String>> vertices = cutClusterGraph.getVertices();
		assertEquals(4, vertices.size());
		assertTrue(vertices.contains(wrap(A)));
		assertTrue(vertices.contains(wrap(B)));
		assertTrue(vertices.contains(wrap(C)));
		assertTrue(vertices.contains(TARGET));
	}

	@Test
	public void testGetOutEdges() {
		assertTrue(cutClusterGraph.getOutEdges(TARGET).isEmpty());
		assertEquals(2, cutClusterGraph.getOutEdges(wrap(A)).size());
	}

	@Test
	public void testContainsVertex() {
		assertTrue(cutClusterGraph.containsVertex(TARGET));
	}

	@Test
	public void testGetPredecessors() {
		Collection<Wrapper<String>> vertices = cutClusterGraph.getPredecessors(TARGET);
		assertEquals(3, vertices.size());
		assertTrue(vertices.contains(wrap(A)));
		assertTrue(vertices.contains(wrap(B)));
		assertTrue(vertices.contains(wrap(C)));
		assertFalse(vertices.contains(TARGET));
	}

	@Test
	public void testContainsEdge() {
		assertTrue(cutClusterGraph.containsEdge(wrap(0)));
	}

	@Test
	public void testGetEdgeCount() {
		assertEquals(5, cutClusterGraph.getEdgeCount());
	}

	@Test
	public void testGetSuccessors() {
		Collection<Wrapper<String>> vertices = cutClusterGraph.getSuccessors(wrap(A));
		assertEquals(2, vertices.size());
		assertTrue(vertices.contains(TARGET));
		assertTrue(vertices.contains(wrap(B)));
		assertTrue(cutClusterGraph.getSuccessors(TARGET).isEmpty());
	}

	@Test
	public void testGetVertexCount() {
		assertEquals(4, cutClusterGraph.getVertexCount());
	}

	@Test
	public void testGetNeighbors() {
		Collection<Wrapper<String>> vertices = cutClusterGraph.getNeighbors(TARGET);
		assertEquals(3, vertices.size());
		assertTrue(vertices.contains(wrap(A)));
		assertTrue(vertices.contains(wrap(B)));
		assertTrue(vertices.contains(wrap(C)));

		Collection<Wrapper<String>> neighbors = cutClusterGraph.getNeighbors(wrap(C));
		assertEquals(2, neighbors.size());
		assertTrue(neighbors.contains(wrap(B)));
		assertTrue(neighbors.contains(TARGET));
	}

	@Test
	public void testInDegree() {
		assertEquals(3, cutClusterGraph.inDegree(TARGET));
	}

	@Test
	public void testGetIncidentEdges() {
		Collection<Wrapper<Integer>> incidentEdges = cutClusterGraph.getIncidentEdges(TARGET);
		assertEquals(3, incidentEdges.size());

		incidentEdges = cutClusterGraph.getIncidentEdges(wrap(B));
		assertEquals(3, incidentEdges.size());
		assertTrue(incidentEdges.contains(wrap(0)));
		assertTrue(incidentEdges.contains(wrap(1)));
	}

	@Test
	public void testOutDegree() {
		assertEquals(0, cutClusterGraph.outDegree(TARGET));
		assertEquals(2, cutClusterGraph.outDegree(wrap(A)));
		assertEquals(2, cutClusterGraph.outDegree(wrap(B)));
		assertEquals(1, cutClusterGraph.outDegree(wrap(C)));
	}

	@Test
	public void testGetIncidentVertices() {
		assertTrue(cutClusterGraph.getIncidentVertices(wrap(0)).contains(wrap(A)));
		assertTrue(cutClusterGraph.getIncidentVertices(wrap(0)).contains(wrap(B)));
	}

	@Test
	public void testIsPredecessor() {
		// API wrong way round!
		assertFalse(cutClusterGraph.isPredecessor(wrap(A), TARGET));
		assertTrue(cutClusterGraph.isPredecessor(TARGET, wrap(A)));
	}

	@Test
	public void testIsSuccessor() {
		// API wrong way round!
		assertTrue(cutClusterGraph.isSuccessor(wrap(A), TARGET));
		assertFalse(cutClusterGraph.isSuccessor(TARGET, wrap(A)));
	}

	@Test
	public void testFindEdge() {
		assertEquals(wrap(0), cutClusterGraph.findEdge(wrap(A), wrap(B)));
		assertNotNull(cutClusterGraph.findEdge(wrap(B), TARGET));
	}

	@Test
	public void testGetPredecessorCount() {
		assertEquals(3, cutClusterGraph.getPredecessorCount(TARGET));
		assertEquals(1, cutClusterGraph.getPredecessorCount(wrap(B)));
	}

	@Test
	public void testGetSuccessorCount() {
		assertEquals(0, cutClusterGraph.getSuccessorCount(TARGET));
		assertEquals(2, cutClusterGraph.getSuccessorCount(wrap(B)));
	}

	@Test
	public void testGetSource() {
		assertEquals(wrap(A), cutClusterGraph.getSource(wrap(0)));
	}

	@Test
	public void testGetDest() {
		assertEquals(wrap(B), cutClusterGraph.getDest(wrap(0)));
	}

	@Test
	public void testIsSource() {
		assertTrue(cutClusterGraph.isSource(wrap(A), wrap(0)));
		assertFalse(cutClusterGraph.isSource(TARGET, wrap(3)));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddVertex() {
		cutClusterGraph.addVertex(wrap("ERROR"));
	}

	@Test
	public void testIsDest() {
		assertFalse(cutClusterGraph.isDest(wrap(A), wrap(0)));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAddEdge() {
		cutClusterGraph.addEdge(wrap(10), wrap(A), wrap(C));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveVertex() {
		cutClusterGraph.removeVertex(TARGET);
	}

	@Test
	public void testGetEndpoints() {
		Pair<Wrapper<String>> endpoints = cutClusterGraph.getEndpoints(wrap(0));
		assertTrue(endpoints.contains(wrap(A)));
		assertTrue(endpoints.contains(wrap(B)));
	}

	@Test
	public void testGetOpposite() {
		assertEquals(wrap(B), cutClusterGraph.getOpposite(wrap(A), wrap(0)));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemoveEdge() {
		cutClusterGraph.removeEdge(wrap(0));
	}

	@Test
	public void testIsNeighbor() {
		assertTrue(cutClusterGraph.isNeighbor(TARGET, wrap(A)));
		assertTrue(cutClusterGraph.isNeighbor(wrap(B), TARGET));
	}

	@Test
	public void testIsIncident() {
		assertTrue(cutClusterGraph.isIncident(wrap(A), wrap(0)));
	}

	@Test
	public void testDegree() {
		assertEquals(2, cutClusterGraph.degree(wrap(A)));
		assertEquals(3, cutClusterGraph.degree(TARGET));
	}

	@Test
	public void testGetNeighborCount() {
		assertEquals(2, cutClusterGraph.getNeighborCount(wrap(C)));
		assertEquals(3, cutClusterGraph.getNeighborCount(TARGET));
	}

	@Test
	public void testGetIncidentCount() {
		assertEquals(2, cutClusterGraph.getIncidentCount(wrap(0)));
	}

	@Test
	public void testGetEdgeType() {
		assertEquals(EdgeType.DIRECTED, cutClusterGraph.getEdgeType(wrap(0)));
		assertEquals(EdgeType.DIRECTED, cutClusterGraph.getEdgeType(wrap(4)));
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
