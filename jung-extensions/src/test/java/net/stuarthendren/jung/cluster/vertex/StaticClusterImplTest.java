package net.stuarthendren.jung.cluster.vertex;

import net.stuarthendren.jung.cluster.vertex.impl.StaticClusterImpl;

import org.junit.Before;
import org.junit.Test;

public class StaticClusterImplTest extends AbstractClusterImplTest {

	@Before
	public void createCluster() {
		addVertices();
		cluster = new StaticClusterImpl<Integer, Integer>(graph, vertices);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testStatic() {
		Cluster<Integer, Integer> c = new StaticClusterImpl<Integer, Integer>(graph, vertices);
		c.add(1);
	}

}
