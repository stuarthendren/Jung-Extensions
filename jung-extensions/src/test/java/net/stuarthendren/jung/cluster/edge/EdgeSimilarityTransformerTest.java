package net.stuarthendren.jung.cluster.edge;

import net.stuarthendren.jung.cluster.edge.impl.EdgeSimilarityTransformer;
import net.stuarthendren.jung.cluster.edge.impl.KeyedEdgePair;

import org.junit.Test;

import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

import static org.junit.Assert.assertEquals;

/**
 * These tests are based on the similarity examples in Figure S1 of the paper (see {@link EdgeCommunityClusterer}).
 * 
 * @author Stuart Hendren
 * 
 */
public class EdgeSimilarityTransformerTest {

	@Test
	public void testSimilarityA() throws Exception {
		UndirectedGraph<Character, Integer> graph = new UndirectedSparseGraph<Character, Integer>();
		graph.addVertex(Character.valueOf('k'));
		graph.addVertex(Character.valueOf('i'));
		graph.addVertex(Character.valueOf('j'));
		graph.addVertex(Character.valueOf('a'));
		graph.addVertex(Character.valueOf('b'));
		graph.addVertex(Character.valueOf('c'));
		graph.addVertex(Character.valueOf('d'));
		graph.addVertex(Character.valueOf('e'));
		graph.addVertex(Character.valueOf('f'));
		graph.addVertex(Character.valueOf('g'));
		graph.addVertex(Character.valueOf('h'));
		graph.addVertex(Character.valueOf('l'));
		graph.addEdge(Integer.valueOf(1), Character.valueOf('i'), Character.valueOf('k'));
		graph.addEdge(Integer.valueOf(2), Character.valueOf('k'), Character.valueOf('j'));
		graph.addEdge(Integer.valueOf(3), Character.valueOf('i'), Character.valueOf('a'));
		graph.addEdge(Integer.valueOf(4), Character.valueOf('i'), Character.valueOf('b'));
		graph.addEdge(Integer.valueOf(5), Character.valueOf('i'), Character.valueOf('c'));
		graph.addEdge(Integer.valueOf(6), Character.valueOf('i'), Character.valueOf('d'));
		graph.addEdge(Integer.valueOf(7), Character.valueOf('i'), Character.valueOf('e'));
		graph.addEdge(Integer.valueOf(8), Character.valueOf('i'), Character.valueOf('f'));
		graph.addEdge(Integer.valueOf(9), Character.valueOf('i'), Character.valueOf('g'));
		graph.addEdge(Integer.valueOf(10), Character.valueOf('j'), Character.valueOf('e'));
		graph.addEdge(Integer.valueOf(11), Character.valueOf('j'), Character.valueOf('f'));
		graph.addEdge(Integer.valueOf(12), Character.valueOf('j'), Character.valueOf('g'));
		graph.addEdge(Integer.valueOf(13), Character.valueOf('j'), Character.valueOf('h'));
		graph.addEdge(Integer.valueOf(14), Character.valueOf('j'), Character.valueOf('l'));

		EdgeSimilarityTransformer<Character, Integer> transformer = new EdgeSimilarityTransformer<Character, Integer>(
				graph);

		assertEquals(Double.valueOf(1.0 / 3.0), transformer.transform(new KeyedEdgePair<Character, Integer>(Character
				.valueOf('k'), Integer.valueOf(1), Integer.valueOf(2))));

	}

	@Test
	public void testSimilarityB() throws Exception {
		UndirectedGraph<Character, Integer> graph = new UndirectedSparseGraph<Character, Integer>();
		graph.addVertex(Character.valueOf('a'));
		graph.addVertex(Character.valueOf('b'));
		graph.addVertex(Character.valueOf('c'));
		graph.addEdge(Integer.valueOf(1), Character.valueOf('a'), Character.valueOf('c'));
		graph.addEdge(Integer.valueOf(2), Character.valueOf('b'), Character.valueOf('c'));

		EdgeSimilarityTransformer<Character, Integer> transformer = new EdgeSimilarityTransformer<Character, Integer>(
				graph);
		Double similarity = transformer.transform(new KeyedEdgePair<Character, Integer>(Character.valueOf('c'), Integer
				.valueOf(1), Integer.valueOf(2)));
		assertEquals(Double.valueOf(1.0 / 3.0), similarity);

	}

	@Test
	public void testSimilarityC() throws Exception {
		UndirectedGraph<Character, Integer> graph = new UndirectedSparseGraph<Character, Integer>();
		graph.addVertex(Character.valueOf('a'));
		graph.addVertex(Character.valueOf('b'));
		graph.addVertex(Character.valueOf('c'));
		graph.addEdge(Integer.valueOf(1), Character.valueOf('a'), Character.valueOf('c'));
		graph.addEdge(Integer.valueOf(2), Character.valueOf('b'), Character.valueOf('c'));
		graph.addEdge(Integer.valueOf(3), Character.valueOf('a'), Character.valueOf('b'));

		Character.valueOf('c');
		EdgeSimilarityTransformer<Character, Integer> transformer = new EdgeSimilarityTransformer<Character, Integer>(
				graph);
		KeyedEdgePair<Character, Integer> keyedEdgePair = new KeyedEdgePair<Character, Integer>(Character.valueOf('c'),
				Integer.valueOf(1), Integer.valueOf(2));
		assertEquals(Double.valueOf(1.0), transformer.transform(keyedEdgePair));

	}
}
