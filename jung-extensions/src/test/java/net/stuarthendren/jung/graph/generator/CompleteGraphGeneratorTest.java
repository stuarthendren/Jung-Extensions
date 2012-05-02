package net.stuarthendren.jung.graph.generator;

import java.util.ArrayList;
import java.util.List;

import net.stuarthendren.jung.graph.CompleteGraph;

import org.junit.Test;

import edu.uci.ics.jung.graph.Graph;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class CompleteGraphGeneratorTest {

	@Test
	public void testIsComplete() {
		int size = (int) (Math.random() * 100);
		Graph<Integer, Integer> graph = new CompleteGraph(size);
		assertTrue(CompleteGraphGenerator.isComplete(graph));
	}

	@Test
	public void testIsNotComplete() {
		int size = (int) (Math.random() * 99) + 2;
		int remove = (int) (Math.random() * size) + 1;
		Graph<Integer, Integer> graph = new CompleteGraph(size);
		List<Integer> edges = new ArrayList<Integer>(graph.getEdges());
		for (int i = 0; i < remove; i++) {
			graph.removeEdge(edges.get(i));
		}

		assertFalse(CompleteGraphGenerator.isComplete(graph));
	}

}
