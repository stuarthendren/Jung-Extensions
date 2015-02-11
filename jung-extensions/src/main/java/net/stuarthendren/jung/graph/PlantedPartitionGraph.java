package net.stuarthendren.jung.graph;

import java.util.Random;

import net.stuarthendren.jung.element.IntegerFactory;
import net.stuarthendren.jung.graph.generator.PlantedPartitionGraphGenerator;

import org.apache.commons.collections4.Factory;

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
public class PlantedPartitionGraph extends DelegatingGraph<Integer, Integer> implements
		UndirectedGraph<Integer, Integer> {

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
	public PlantedPartitionGraph(int numberOfClusters, int sizeOfClusters, double intraClusterProbability,
			double interClusterProbability) {
		this(numberOfClusters, sizeOfClusters, intraClusterProbability, interClusterProbability, System
				.currentTimeMillis());
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
	public PlantedPartitionGraph(int numberOfClusters, int sizeOfClusters, double intraClusterProbability,
			double interClusterProbability, long seed) {
		super(generateGraph(numberOfClusters, sizeOfClusters, intraClusterProbability, interClusterProbability, seed));
	}

	private static UndirectedGraph<Integer, Integer> generateGraph(int numberOfClusters, int sizeOfClusters,
			double intraClusterProbability, double interClusterProbability, long seed) {
		PlantedPartitionGraphGenerator<Integer, Integer> generator = new PlantedPartitionGraphGenerator<Integer, Integer>(
				new Factory<UndirectedGraph<Integer, Integer>>() {

					@Override
					public UndirectedGraph<Integer, Integer> create() {
						return new UndirectedSparseGraph<Integer, Integer>();
					}
				}, new IntegerFactory(), new IntegerFactory(), numberOfClusters, sizeOfClusters,
				intraClusterProbability, interClusterProbability, seed);
		return (UndirectedGraph<Integer, Integer>) generator.create();
	}

}
