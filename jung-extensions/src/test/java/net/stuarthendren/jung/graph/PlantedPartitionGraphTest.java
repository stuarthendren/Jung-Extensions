package net.stuarthendren.jung.graph;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import static junit.framework.Assert.assertEquals;

public class PlantedPartitionGraphTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeGroups() {
		PlantedPartitionGraph.generateGraph(-2, 10, 0.7, 0.3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeVerticies() {
		PlantedPartitionGraph.generateGraph(10, -10, 0.7, 0.3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeTnterGroupProbability() {
		PlantedPartitionGraph.generateGraph(5, 10, 0.7, -0.3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeIntraGroupProbability() {
		PlantedPartitionGraph.generateGraph(5, 10, 0.7, -0.6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntraNotGreaterThanInterProbability() {
		PlantedPartitionGraph.generateGraph(5, 10, 0.7, 0.8);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInterProbabilityLessThanOne() {
		PlantedPartitionGraph.generateGraph(5, 10, 1.7, 0.8);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntraProbabilityLessThanOne() {
		PlantedPartitionGraph.generateGraph(5, 10, 0.7, 1.8);
	}

	@Test()
	public void testSizeOfGraph() {
		int numberOfClusters = 5;
		int sizeOfClusters = 10;
		Graph<Integer, Integer> graph = PlantedPartitionGraph.generateGraph(numberOfClusters, sizeOfClusters, 0.9, 0.1);
		assertEquals(numberOfClusters * sizeOfClusters, graph.getVertexCount());
	}

	@Test()
	public void testProbabilitites() {
		int numberOfClusters = 50;
		int sizeOfClusters = 10;
		int sizeOfGraph = numberOfClusters * sizeOfClusters;
		double interClusterProbability = 0.9;
		double intraClusterProbability = 0.1;
		Graph<Integer, Integer> graph = PlantedPartitionGraph.generateGraph(numberOfClusters, sizeOfClusters,
				interClusterProbability, intraClusterProbability, 0);
		int numberOfInterEdges = 0;
		int numberOfIntraEdges = 0;
		for (Integer edge : graph.getEdges()) {
			Pair<Integer> endpoints = graph.getEndpoints(edge);
			if (endpoints.getFirst() % numberOfClusters == endpoints.getSecond() % numberOfClusters) {
				numberOfInterEdges++;
			} else {
				numberOfIntraEdges++;
			}
		}

		double maxNumberOfInterEdges = numberOfClusters * ((sizeOfClusters * (sizeOfClusters - 1)) / 2);
		double maxNumberOfIntraEdges = ((sizeOfGraph * (sizeOfGraph - 1)) / 2) - maxNumberOfInterEdges;

		assertEquals(interClusterProbability, numberOfInterEdges / maxNumberOfInterEdges, 0.004);
		assertEquals(intraClusterProbability, numberOfIntraEdges / maxNumberOfIntraEdges, 0.001);

	}
}
