package net.stuarthendren.jung.visualization;

import java.awt.Color;
import java.awt.Paint;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;

/**
 * Graph viewer shows the graph with basic control
 * 
 * Applies {@link FRLayout} and {@link DefaultModalGraphMouse}. Change from Picking 'p' and Transforming 't' using keys
 * 
 * @author Stuart Hendren
 * 
 */
public class GraphViewer {

	public static <V, E> void show(Graph<V, E> graph) {
		JFrame jf = new JFrame();
		final VisualizationViewer<V, E> vv = new VisualizationViewer<V, E>(new FRLayout<V, E>(graph));
		vv.setBackground(Color.white);
		vv.getRenderContext().setVertexDrawPaintTransformer(new Transformer<V, Paint>() {
			@Override
			public Paint transform(V v) {
				if (vv.getPickedVertexState().isPicked(v)) {
					return Color.cyan;
				} else {
					return Color.BLACK;
				}
			}
		});
		DefaultModalGraphMouse<V, E> gm = new DefaultModalGraphMouse<V, E>();
		vv.setGraphMouse(gm);
		vv.addKeyListener(gm.getModeKeyListener());
		jf.getContentPane().add(vv);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);
	}

}
