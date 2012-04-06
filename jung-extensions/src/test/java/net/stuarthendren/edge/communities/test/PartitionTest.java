package net.stuarthendren.edge.communities.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.stuarthendren.jung.partition.Partition;
import net.stuarthendren.jung.partition.impl.PartitionImpl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PartitionTest extends AbstractEdgeClusterTest {

	@Test
	public void maximumEdgePartitionContainsAllEdges() {
		Partition<Character> partition = PartitionImpl.<Character> createMaximumPartition(graph.getEdges());
		assertEquals(graph.getEdgeCount(), partition.getPartitioning().size());
	}

	@Test
	public void canAddClusterToEdgePartition() {
		Partition<Character> partition = new PartitionImpl<Character>(graph.getEdges());
		partition.addCluster('a');

		assertTrue(partition.contains('a'));
	}

	@Test
	public void canMergeClustersOfEdgePartition() {
		Partition<Character> partition = new PartitionImpl<Character>(graph.getEdges());

		partition.addCluster('a', 'b');
		partition.addCluster('c');
		partition.addCluster('d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm');
		Collection<Set<Character>> partitioningBefore = partition.getPartitioning();
		assertEquals(3, partitioningBefore.size());
		partition.mergeCluster('a', 'c');

		Collection<Set<Character>> partitioningAfter = partition.getPartitioning();
		assertEquals(2, partitioningAfter.size());

	}

	@Test
	public void singleClusterEdgePartitionIsComplete() {
		Partition<Character> partition = new PartitionImpl<Character>(graph.getEdges());
		partition.addCluster(new HashSet<Character>(graph.getEdges()));
		assertTrue(partition.isComplete());

	}

	@Test
	public void emptyEdgePartitionIsNotComplete() {
		Partition<Character> partition = new PartitionImpl<Character>(graph.getEdges());
		assertFalse(partition.isComplete());
	}

	@Test(expected = IllegalArgumentException.class)
	public void duplicateEdgeCannotBeAddedToEgdePartition() {
		Partition<Character> partition = new PartitionImpl<Character>(graph.getEdges());

		partition.addCluster('a');
		partition.addCluster('a');
	}

	@Test()
	public void nonExistantEgdeCanBeAddedToEdgePartitionForPerformanceReasons() {
		Partition<Character> partition = new PartitionImpl<Character>(graph.getEdges());
		partition.addCluster('z');
	}

	@Test(expected = UnsupportedOperationException.class)
	public void partitionSetIsNotModifiable() {
		Partition<Character> partition = new PartitionImpl<Character>(graph.getEdges());

		partition.addCluster('a', 'b');
		partition.addCluster('c');
		partition.addCluster('d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm');
		partition.getPartitioning().add(new HashSet<Character>());
	}

	@Test()
	public void copyOfPartitionIsTheSame() {
		Partition<Character> partition = new PartitionImpl<Character>(graph.getEdges());

		partition.addCluster('a', 'b');
		partition.addCluster('c');
		partition.addCluster('d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm');

		Partition<Character> copy = partition.copy();
		Collection<Set<Character>> partitioning = copy.getPartitioning();
		assertEquals(3, partitioning.size());
		assertEquals(2, partition.getCluster('a').size());
		assertEquals(2, partition.getCluster('b').size());
		assertEquals(1, partition.getCluster('c').size());
		assertEquals(10, partition.getCluster('d').size());
	}

	@Test()
	public void copyOfPartitionIsEqual() {
		Partition<Character> partition = new PartitionImpl<Character>(graph.getEdges());

		partition.addCluster('a', 'b');
		partition.addCluster('c');
		partition.addCluster('d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm');

		assertEquals(partition, partition.copy());
	}

	@Test()
	public void differentPartitionsAreNotEqual() {
		Partition<Character> partition1 = new PartitionImpl<Character>(graph.getEdges());

		partition1.addCluster('a', 'b');
		partition1.addCluster('c');
		partition1.addCluster('d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm');

		Partition<Character> partition2 = new PartitionImpl<Character>(graph.getEdges());

		partition2.addCluster('a');
		partition2.addCluster('b', 'c');
		partition2.addCluster('d', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm');

		assertFalse(partition1.equals(partition2));
	}

	@Test()
	public void singleClusterEdgePartitionCoversMaximumEdgePartitions() {
		Partition<Character> singleClusterPartition = new PartitionImpl<Character>(graph.getEdges());
		singleClusterPartition.addCluster(graph.getEdges().toArray(new Character[graph.getEdgeCount()]));

		Partition<Character> maximumPartition = PartitionImpl.<Character> createMaximumPartition(graph.getEdges());

		assertTrue(singleClusterPartition.covers(maximumPartition));
	}

	@Test()
	public void maximumEdgePartitionsDoesNotCoverSingleClusterEdgePartition() {
		Partition<Character> singleClusterPartition = new PartitionImpl<Character>(graph.getEdges());
		singleClusterPartition.addCluster(graph.getEdges().toArray(new Character[graph.getEdgeCount()]));

		Partition<Character> maximumPartition = PartitionImpl.<Character> createMaximumPartition(graph.getEdges());

		assertFalse(maximumPartition.covers(singleClusterPartition));
	}

}
