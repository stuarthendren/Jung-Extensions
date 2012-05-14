package net.stuarthendren.jung.graph.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.generators.GraphGenerator;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;

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
public class PlantedPartitionGraphGenerator<V, E> implements GraphGenerator<V, E> {

	private final Factory<UndirectedGraph<V, E>> graphFactory;
	private final Factory<V> vertexFactory;
	private final Factory<E> edgeFactory;
	private final int numberOfClusters;
	private final int sizeOfClusters;
	private final double intraClusterProbability;
	private final double interClusterProbability;
	private Random rand;

	public PlantedPartitionGraphGenerator(Factory<UndirectedGraph<V, E>> graphFactory, Factory<V> vertexFactory,
			Factory<E> edgeFactory, int numberOfClusters, int sizeOfClusters, double intraClusterProbability,
			double interClusterProbability) {
		this(graphFactory, vertexFactory, edgeFactory, numberOfClusters, sizeOfClusters, intraClusterProbability,
				interClusterProbability, System.currentTimeMillis());
	}

	public PlantedPartitionGraphGenerator(Factory<UndirectedGraph<V, E>> graphFactory, Factory<V> vertexFactory,
			Factory<E> edgeFactory, int numberOfClusters, int sizeOfClusters, double intraClusterProbability,
			double interClusterProbability, long seed) {
		if (numberOfClusters < 0) {
			throw new IllegalArgumentException("Number of groups must be positive");
		}
		if (sizeOfClusters < 0) {
			throw new IllegalArgumentException("Group size must be positive");
		}
		if (intraClusterProbability < 0.0 || intraClusterProbability > 1.0 || interClusterProbability < 0.0
				|| interClusterProbability > 1.0) {
			throw new IllegalArgumentException("Probabilities must be between 0 and 1");
		}
		if (intraClusterProbability < interClusterProbability) {
			throw new IllegalArgumentException("inter group probability must be less than the intra group probability");
		}
		this.numberOfClusters = numberOfClusters;
		this.sizeOfClusters = sizeOfClusters;
		this.intraClusterProbability = intraClusterProbability;
		this.interClusterProbability = interClusterProbability;
		this.graphFactory = graphFactory;
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
		rand = new Random(seed);
	}

	private UndirectedGraph<V, E> populateGraph(UndirectedGraph<V, E> graph) {
		Map<Integer, V> vertexMap = new HashMap<Integer, V>();
		int graphSize = numberOfClusters * sizeOfClusters;
		for (int i = 0; i < graphSize; i++) {
			V v = vertexFactory.create();
			graph.addVertex(v);
			vertexMap.put(i, v);
		}
		for (int i = 0; i < graphSize; i++) {
			for (int j = 0; j < graphSize; j++) {
				if (i > j) {
					if (i % numberOfClusters == j % numberOfClusters) {
						if (rand.nextDouble() <= intraClusterProbability) {
							graph.addEdge(edgeFactory.create(), vertexMap.get(i), vertexMap.get(j));
						}
					} else {
						if (rand.nextDouble() <= interClusterProbability) {
							graph.addEdge(edgeFactory.create(), vertexMap.get(i), vertexMap.get(j));
						}
					}
				}
			}
		}
		return graph;
	}

	@Override
	/**
	 * 
	 * Create a complete graph, of size specified in the constructor.
	 * 
	 * @return complete graph
	 */
	public Graph<V, E> create() {
		return populateGraph(graphFactory.create());
	}
}
