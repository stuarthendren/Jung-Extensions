package net.stuarthendren.edge.communities.test;

import junit.framework.Assert;
import net.stuarthendren.jung.dendrogram.partition.impl.PartitionDendogramImpl;

import org.junit.Test;

public class PartitionDendogramImplTest extends AbstractEdgeClusterTest {

	protected PartitionDendogramImpl<Character> getEdgeDendogram() {
		return new PartitionDendogramImpl<Character>();
	}

	@Test
	public void basicDendogramIsValid() {
		PartitionDendogramImpl<Character> dendogram = getEdgeDendogram();
		dendogram.addPartition(maximalEdgePartition);
		dendogram.addPartition(singleClusterEdgePartition);
		Assert.assertTrue(dendogram.isValid());

	}

	@Test
	public void extendedDendogramIsValid() {
		PartitionDendogramImpl<Character> dendogram = getEdgeDendogram();
		dendogram.addPartition(maximalEdgePartition);
		dendogram.addPartition(maxDensityEdgePartition);
		dendogram.addPartition(singleClusterEdgePartition);
		Assert.assertTrue(dendogram.isValid());
	}

	@Test
	public void nonCoveringDendogramIsNotValid() {
		PartitionDendogramImpl<Character> dendogram = getEdgeDendogram();
		dendogram.addPartition(singleClusterEdgePartition);
		dendogram.addPartition(maximalEdgePartition);
		Assert.assertFalse(dendogram.isValid());
	}

	@Test
	public void nonCoveringExtendedDendogramIsInvalid() {
		PartitionDendogramImpl<Character> dendogram = getEdgeDendogram();
		dendogram.addPartition(maximalEdgePartition);
		dendogram.addPartition(maxDensityEdgePartition);
		dendogram.addPartition(nonCoveringEdgePartition);
		dendogram.addPartition(singleClusterEdgePartition);
		Assert.assertFalse(dendogram.isValid());
	}

	@Test
	public void getPartitionByIndex() {
		PartitionDendogramImpl<Character> dendogram = getEdgeDendogram();
		dendogram.addPartition(maximalEdgePartition);
		dendogram.addPartition(maxDensityEdgePartition);
		dendogram.addPartition(singleClusterEdgePartition);
		Assert.assertEquals(maximalEdgePartition, dendogram.getPartition(0));
		Assert.assertEquals(maxDensityEdgePartition, dendogram.getPartition(1));
		Assert.assertEquals(singleClusterEdgePartition, dendogram.getPartition(2));
	}

	@Test
	public void insertPartitionByIndex() {
		PartitionDendogramImpl<Character> dendogram = getEdgeDendogram();
		dendogram.addPartition(maximalEdgePartition);
		dendogram.addPartition(singleClusterEdgePartition);
		dendogram.insertNewPartition(1);
		Assert.assertEquals(maximalEdgePartition, dendogram.getPartition(0));
		Assert.assertEquals(maximalEdgePartition, dendogram.getPartition(1));
		Assert.assertEquals(singleClusterEdgePartition, dendogram.getPartition(2));
	}

}
