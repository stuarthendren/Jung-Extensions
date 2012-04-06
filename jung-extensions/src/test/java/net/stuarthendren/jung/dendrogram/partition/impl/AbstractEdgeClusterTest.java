package net.stuarthendren.jung.dendrogram.partition.impl;

import net.stuarthendren.jung.partition.Partition;
import net.stuarthendren.jung.partition.impl.PartitionImpl;

import org.junit.After;
import org.junit.Before;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * The main graph used in the tests and defined in this abstract class is the example from pare 6 of the paper
 * (http://arxiv.org/abs/0903.3178)
 * 
 * @author Stuart Hendren
 * 
 */
public abstract class AbstractEdgeClusterTest {

	protected UndirectedGraph<Integer, Character> graph;

	protected Partition<Character> maximalEdgePartition;

	protected Partition<Character> singleClusterEdgePartition;

	protected Partition<Character> maxDensityEdgePartition;

	protected Partition<Character> nonCoveringEdgePartition;

	@Before
	public void createTestGraph() throws Exception {
		graph = new UndirectedSparseGraph<Integer, Character>();
		graph.addVertex(1);
		graph.addVertex(2);
		graph.addVertex(3);
		graph.addVertex(4);
		graph.addVertex(5);
		graph.addVertex(6);
		graph.addVertex(7);
		graph.addVertex(8);
		graph.addVertex(9);

		graph.addEdge('a', 1, 2);
		graph.addEdge('b', 1, 3);
		graph.addEdge('c', 2, 3);
		graph.addEdge('d', 1, 4);
		graph.addEdge('e', 4, 2);
		graph.addEdge('f', 4, 3);
		graph.addEdge('g', 4, 5);
		graph.addEdge('h', 4, 6);
		graph.addEdge('i', 5, 6);
		graph.addEdge('j', 4, 7);
		graph.addEdge('k', 7, 8);
		graph.addEdge('l', 7, 9);
		graph.addEdge('m', 8, 9);

		maximalEdgePartition = new PartitionImpl<Character>(graph.getEdges());
		for (Character i : graph.getEdges()) {
			maximalEdgePartition.addCluster(i);
		}
		singleClusterEdgePartition = new PartitionImpl<Character>(graph.getEdges());

		singleClusterEdgePartition.addCluster(graph.getEdges().toArray(new Character[graph.getEdgeCount()]));
		maxDensityEdgePartition = maximalEdgePartition.copy();

		maxDensityEdgePartition.mergeCluster('a', 'b');
		maxDensityEdgePartition.mergeCluster('b', 'c');
		maxDensityEdgePartition.mergeCluster('c', 'd');
		maxDensityEdgePartition.mergeCluster('d', 'e');
		maxDensityEdgePartition.mergeCluster('e', 'f');
		maxDensityEdgePartition.mergeCluster('g', 'h');
		maxDensityEdgePartition.mergeCluster('h', 'i');
		maxDensityEdgePartition.mergeCluster('k', 'l');
		maxDensityEdgePartition.mergeCluster('l', 'm');

		nonCoveringEdgePartition = maximalEdgePartition.copy();

		nonCoveringEdgePartition.mergeCluster('c', 'd');
		nonCoveringEdgePartition.mergeCluster('e', 'f');

	}

	@After
	public void clearFields() {
		graph = null;
		singleClusterEdgePartition = null;
		maxDensityEdgePartition = null;
		maximalEdgePartition = null;
		nonCoveringEdgePartition = null;
	}
}
