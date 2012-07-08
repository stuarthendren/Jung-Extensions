package net.stuarthendren.jung.experiment;

import net.stuarthendren.jung.graph.CompleteGraph;
import net.stuarthendren.jung.visualization.GraphViewer;

public class View {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// GraphViewer.show(RelaxedCavemanGraph.generateGraph(0.4, 1.2, 3, 5, 20));
		GraphViewer.show(new CompleteGraph(10));
	}

}
