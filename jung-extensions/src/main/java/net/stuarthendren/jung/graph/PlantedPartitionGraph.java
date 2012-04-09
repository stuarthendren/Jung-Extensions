package net.stuarthendren.jung.graph;

import java.util.Random;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

/**
 * <p>
 * A planted partition graph is made up of <code>l</code> group each of size <code>k</code>. The probability of two
 * vertices in the same group having an edge between them is <code>p</code> and otherwise the probability is
 * <code>q</code> where <code>0 <= q < p <= 1</code>
 * </p>
 * <p>
 * It can be used to test clustering algorithms, the greater the difference between <code>p</code> and <code>q</code>
 * the easier it should be to detect the planted clusters.
 * </p>
 * 
 * @author Stuart Hendren
 * 
 */
@SuppressWarnings("serial")
public class PlantedPartitionGraph extends UndirectedSparseGraph<Integer, Integer> {

	/**
	 * Create a planted partition graph.
	 * 
	 * 
	 * @param numberOfClusters
	 *            the number of groups (or clusters)
	 * @param sizeOfClusters
	 *            the number of vertices in a group
	 * @param interClusterProbability
	 *            the probability of two vertices in the same group being connected
	 * @param intraClusterProbability
	 *            the probability of two vertices not in the same group being connected must be less than interGroup
	 *            probability
	 * 
	 */
	public static UndirectedGraph<Integer, Integer> generateGraph(int numberOfClusters, int sizeOfClusters,
			double interClusterProbability, double intraClusterProbability) {
		return generateGraph(numberOfClusters, sizeOfClusters, interClusterProbability, intraClusterProbability,
				System.currentTimeMillis());
	}

	/**
	 * Create a planted partition graph.
	 * 
	 * 
	 * @param numberOfClusters
	 *            the number of groups (or clusters)
	 * @param sizeOfClusters
	 *            the number of vertices in a group
	 * @param interClusterProbability
	 *            the probability of two vertices in the same group being connected
	 * @param intraClusterProbability
	 *            the probability of two vertices not in the same group being connected must be less than interGroup
	 *            probability
	 * @param seed
	 *            seed for {@link Random} number generation
	 * 
	 */
	public static UndirectedGraph<Integer, Integer> generateGraph(int numberOfClusters, int sizeOfClusters,
			double interClusterProbability, double intraClusterProbability, long seed) {
		if (numberOfClusters < 0) {
			throw new IllegalArgumentException("Number of groups must be positive");
		}
		if (sizeOfClusters < 0) {
			throw new IllegalArgumentException("Group size must be positive");
		}
		if (interClusterProbability < 0.0 || interClusterProbability > 1.0 || intraClusterProbability < 0.0
				|| intraClusterProbability > 1.0) {
			throw new IllegalArgumentException("Probabilities must be between 0 and 1");
		}
		if (interClusterProbability < intraClusterProbability) {
			throw new IllegalArgumentException("inter group probability must be less than the intra group probability");
		}
		UndirectedGraph<Integer, Integer> graph = new UndirectedSparseGraph<Integer, Integer>();
		return populateGraph(graph, numberOfClusters, sizeOfClusters, interClusterProbability, intraClusterProbability,
				seed);
	}

	private static UndirectedGraph<Integer, Integer> populateGraph(UndirectedGraph<Integer, Integer> graph,
			int numberOfClusters, int sizeOfClusters, double interClusterProbability, double intraClusterProbability,
			long seed) {
		int graphSize = numberOfClusters * sizeOfClusters;
		for (int i = 0; i < graphSize; i++) {
			graph.addVertex(i);
		}
		Random rand = new Random(seed);
		int edges = 0;
		for (int i = 0; i < graphSize; i++) {
			for (int j = 0; j < graphSize; j++) {
				if (i > j) {
					if (i % numberOfClusters == j % numberOfClusters) {
						if (rand.nextDouble() <= interClusterProbability) {
							graph.addEdge(edges++, i, j);
						}
					} else {
						if (rand.nextDouble() <= intraClusterProbability) {
							graph.addEdge(edges++, i, j);
						}
					}
				}
			}
		}
		return graph;
	}

}
