package net.stuarthendren.jung.graph;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

public class PlantedPartitionGraphTest {

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeGroups() {
		new PlantedPartitionGraph(-2, 10, 0.7, 0.3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeVerticies() {
		new PlantedPartitionGraph(10, -10, 0.7, 0.3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeTnterGroupProbability() {
		new PlantedPartitionGraph(5, 10, 0.7, -0.3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeIntraGroupProbability() {
		new PlantedPartitionGraph(5, 10, 0.7, -0.6);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInterNotGreaterThanIntraProbability() {
		new PlantedPartitionGraph(5, 10, 0.7, 0.8);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIntraProbabilityLessThanOne() {
		new PlantedPartitionGraph(5, 10, 1.7, 0.8);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInterProbabilityLessThanOne() {
		new PlantedPartitionGraph(5, 10, 0.7, 1.8);
	}

	@Test()
	public void testSizeOfGraph() {
		int numberOfClusters = 5;
		int sizeOfClusters = 10;
		Graph<Integer, Integer> graph = new PlantedPartitionGraph(numberOfClusters, sizeOfClusters, 0.9, 0.1);
		assertEquals(numberOfClusters * sizeOfClusters, graph.getVertexCount());
	}

	@Test()
	public void testProbabilitites() {
		int numberOfClusters = 50;
		int sizeOfClusters = 10;
		int sizeOfGraph = numberOfClusters * sizeOfClusters;
		double intraClusterProbability = 0.9;
		double interClusterProbability = 0.1;
		Graph<Integer, Integer> graph = new PlantedPartitionGraph(numberOfClusters, sizeOfClusters,
				intraClusterProbability, interClusterProbability, 0);
		int numberOfInterEdges = 0;
		int numberOfIntraEdges = 0;
		for (Integer edge : graph.getEdges()) {
			Pair<Integer> endpoints = graph.getEndpoints(edge);
			if (endpoints.getFirst() % numberOfClusters == endpoints.getSecond() % numberOfClusters) {
				numberOfIntraEdges++;
			} else {
				numberOfInterEdges++;
			}
		}

		double maxNumberOfIntraEdges = numberOfClusters * ((sizeOfClusters * (sizeOfClusters - 1)) / 2);
		double maxNumberOfInterEdges = ((sizeOfGraph * (sizeOfGraph - 1)) / 2) - maxNumberOfIntraEdges;

		assertEquals(intraClusterProbability, numberOfIntraEdges / maxNumberOfIntraEdges, 0.004);
		assertEquals(interClusterProbability, numberOfInterEdges / maxNumberOfInterEdges, 0.001);

	}
}
