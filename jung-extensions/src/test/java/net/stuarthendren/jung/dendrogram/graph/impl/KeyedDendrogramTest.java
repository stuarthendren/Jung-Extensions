package net.stuarthendren.jung.dendrogram.graph.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import net.stuarthendren.jung.dendrogram.graph.DendrogramNode;

import org.apache.commons.collections4.comparators.ComparableComparator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KeyedDendrogramTest {

	private Collection<Integer> intergers;

	private KeyedDendrogram<Integer, Integer> dendrogram;

	@Before
	public void createTestCollectiob() throws Exception {
		intergers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		dendrogram = new KeyedDendrogram<Integer, Integer>(intergers, new ComparableComparator<Integer>());
	}

	@After
	public void cleanUp() throws Exception {
		intergers = null;
		dendrogram = null;
	}

	@Test
	public void dendrogramInitalStateHeight1() {
		Assert.assertEquals(1, dendrogram.getHeight());
	}

	@Test
	public void dendrogramInitalStateLeafsAndRootNode() {
		DendrogramNode<Integer> rootNode = dendrogram.getRootNode();
		Collection<DendrogramNode<Integer>> children = rootNode.getChildren();
		for (Integer i : intergers) {
			LeafDendrogramNode<Integer> leaf = dendrogram.getLeafNode(i);
			Assert.assertEquals(rootNode, leaf.getParent());
			Assert.assertTrue(children.contains(leaf));
		}
		Assert.assertEquals(9, children.size());
	}

	@Test
	public void dendrogramWithSimpleMerge() {
		dendrogram.merge(1, 1, 2);
		DendrogramNode<Integer> rootNode = dendrogram.getRootNode();
		Collection<DendrogramNode<Integer>> children = rootNode.getChildren();
		for (Integer i : intergers) {
			if (!i.equals(1) && !i.equals(2)) {
				LeafDendrogramNode<Integer> leaf = dendrogram.getLeafNode(i);
				Assert.assertEquals("parent of " + i + " should be the root", rootNode, leaf.getParent());
				Assert.assertTrue(children.contains(leaf));
			}
		}
		Assert.assertEquals(8, children.size());

		LeafDendrogramNode<Integer> leaf1 = dendrogram.getLeafNode(1);
		LeafDendrogramNode<Integer> leaf2 = dendrogram.getLeafNode(2);

		DendrogramNode<Integer> parent1 = leaf1.getParent();
		DendrogramNode<Integer> parent2 = leaf2.getParent();

		Assert.assertEquals(parent1, parent2);
		Assert.assertNotSame(rootNode, parent1);
		Assert.assertEquals(rootNode, parent1.getParent());
		Assert.assertTrue(children.contains(parent1));
		Assert.assertEquals(2, parent1.getChildren().size());
		Assert.assertTrue(parent1.getChildren().contains(leaf1));
		Assert.assertTrue(parent1.getChildren().contains(leaf2));

		Assert.assertEquals(2, dendrogram.getHeight());

	}

	@Test
	public void dendrogramWithTwoSimpleMerges() {
		dendrogram.merge(1, 1, 2);
		dendrogram.merge(2, 2, 3);
		DendrogramNode<Integer> rootNode = dendrogram.getRootNode();
		Collection<DendrogramNode<Integer>> children = rootNode.getChildren();
		for (Integer i : intergers) {
			if (!i.equals(1) && !i.equals(2) && !i.equals(3)) {
				LeafDendrogramNode<Integer> leaf = dendrogram.getLeafNode(i);
				Assert.assertEquals("parent of " + i + " should be the root", rootNode, leaf.getParent());
				Assert.assertTrue(children.contains(leaf));
			}
		}
		Assert.assertEquals(7, children.size());

		LeafDendrogramNode<Integer> leaf1 = dendrogram.getLeafNode(1);
		LeafDendrogramNode<Integer> leaf2 = dendrogram.getLeafNode(2);
		LeafDendrogramNode<Integer> leaf3 = dendrogram.getLeafNode(3);

		DendrogramNode<Integer> parent1 = leaf1.getParent();
		DendrogramNode<Integer> parent2 = leaf2.getParent();
		DendrogramNode<Integer> parent3 = leaf3.getParent();

		Assert.assertEquals(parent1, parent2);
		Assert.assertNotSame(rootNode, parent1);
		Assert.assertNotSame(rootNode, parent3);
		Assert.assertEquals(parent3, parent1.getParent());
		Assert.assertEquals(parent3, parent2.getParent());
		Assert.assertTrue(children.contains(parent3));
		Assert.assertEquals(2, parent1.getChildren().size());
		Assert.assertTrue(parent1.getChildren().contains(leaf1));
		Assert.assertTrue(parent1.getChildren().contains(leaf2));
		Assert.assertTrue(parent3.getChildren().contains(parent1));
		Assert.assertTrue(parent3.getChildren().contains(leaf3));
		Assert.assertEquals(2, parent3.getChildren().size());

		Assert.assertEquals(3, dendrogram.getHeight());

	}

	@Test
	public void dendrogramWithTwoSimpleMergesInReverseOrder() {
		dendrogram.merge(2, 2, 3);
		dendrogram.merge(1, 1, 2);
		DendrogramNode<Integer> rootNode = dendrogram.getRootNode();
		Collection<DendrogramNode<Integer>> children = rootNode.getChildren();
		for (Integer i : intergers) {
			if (!i.equals(1) && !i.equals(2) && !i.equals(3)) {
				LeafDendrogramNode<Integer> leaf = dendrogram.getLeafNode(i);
				Assert.assertEquals("parent of " + i + " should be the root", rootNode, leaf.getParent());
				Assert.assertTrue(children.contains(leaf));
			}
		}
		Assert.assertEquals(7, children.size());

		LeafDendrogramNode<Integer> leaf1 = dendrogram.getLeafNode(1);
		LeafDendrogramNode<Integer> leaf2 = dendrogram.getLeafNode(2);
		LeafDendrogramNode<Integer> leaf3 = dendrogram.getLeafNode(3);

		DendrogramNode<Integer> parent1 = leaf1.getParent();
		DendrogramNode<Integer> parent2 = leaf2.getParent();
		DendrogramNode<Integer> parent3 = leaf3.getParent();

		Assert.assertEquals(parent1, parent2);
		Assert.assertNotSame(rootNode, parent1);
		Assert.assertNotSame(rootNode, parent3);
		Assert.assertEquals(parent3, parent1.getParent());
		Assert.assertEquals(parent3, parent2.getParent());
		Assert.assertTrue(children.contains(parent3));
		Assert.assertEquals(2, parent1.getChildren().size());
		Assert.assertTrue(parent1.getChildren().contains(leaf1));
		Assert.assertTrue(parent1.getChildren().contains(leaf2));
		Assert.assertTrue(parent3.getChildren().contains(parent1));
		Assert.assertTrue(parent3.getChildren().contains(leaf3));
		Assert.assertEquals(2, parent3.getChildren().size());

		Assert.assertEquals(3, dendrogram.getHeight());

	}

	@Test
	public void dendrogramWithTwoSimpleMergesAtSameLevel() {
		dendrogram.merge(1, 2, 3);
		dendrogram.merge(1, 1, 2);
		DendrogramNode<Integer> rootNode = dendrogram.getRootNode();
		Collection<DendrogramNode<Integer>> children = rootNode.getChildren();
		for (Integer i : intergers) {
			if (!i.equals(1) && !i.equals(2) && !i.equals(3)) {
				LeafDendrogramNode<Integer> leaf = dendrogram.getLeafNode(i);
				Assert.assertEquals("parent of " + i + " should be the root", rootNode, leaf.getParent());
				Assert.assertTrue(children.contains(leaf));
			}
		}
		Assert.assertEquals(7, children.size());

		LeafDendrogramNode<Integer> leaf1 = dendrogram.getLeafNode(1);
		LeafDendrogramNode<Integer> leaf2 = dendrogram.getLeafNode(2);
		LeafDendrogramNode<Integer> leaf3 = dendrogram.getLeafNode(3);

		DendrogramNode<Integer> parent1 = leaf1.getParent();
		DendrogramNode<Integer> parent2 = leaf2.getParent();
		DendrogramNode<Integer> parent3 = leaf3.getParent();

		Assert.assertEquals(parent1, parent2);
		Assert.assertEquals(parent3, parent2);
		Assert.assertNotSame(rootNode, parent1);
		Assert.assertEquals(rootNode, parent1.getParent());
		Assert.assertTrue(children.contains(parent1));
		Assert.assertEquals(3, parent1.getChildren().size());
		Assert.assertTrue(parent1.getChildren().contains(leaf1));
		Assert.assertTrue(parent1.getChildren().contains(leaf2));
		Assert.assertTrue(parent1.getChildren().contains(leaf3));
		Assert.assertEquals(2, dendrogram.getHeight());

	}

	@Test
	public void dendrogramMultipleMerges() {
		dendrogram.merge(1, 1, 2);
		dendrogram.merge(1, 3, 4);
		dendrogram.merge(4, 5, 4);
		dendrogram.merge(3, 2, 3);
		DendrogramNode<Integer> rootNode = dendrogram.getRootNode();
		Collection<DendrogramNode<Integer>> children = rootNode.getChildren();
		for (Integer i : intergers) {
			if (!i.equals(1) && !i.equals(2) && !i.equals(3) && !i.equals(4) && !i.equals(5)) {
				LeafDendrogramNode<Integer> leaf = dendrogram.getLeafNode(i);
				Assert.assertEquals("parent of " + i + " should be the root", rootNode, leaf.getParent());
				Assert.assertTrue(children.contains(leaf));
			}
		}
		Assert.assertEquals(5, children.size());

		LeafDendrogramNode<Integer> leaf1 = dendrogram.getLeafNode(1);
		LeafDendrogramNode<Integer> leaf2 = dendrogram.getLeafNode(2);
		LeafDendrogramNode<Integer> leaf3 = dendrogram.getLeafNode(3);
		LeafDendrogramNode<Integer> leaf4 = dendrogram.getLeafNode(4);
		LeafDendrogramNode<Integer> leaf5 = dendrogram.getLeafNode(5);

		Assert.assertEquals(leaf1.getParent(), leaf2.getParent());
		Assert.assertEquals(leaf3.getParent(), leaf4.getParent());
		Assert.assertEquals(leaf1.getParent().getParent(), leaf3.getParent().getParent());
		Assert.assertEquals(leaf1.getParent().getParent().getParent(), leaf5.getParent());
		Assert.assertEquals(rootNode, leaf5.getParent().getParent());

		Assert.assertTrue(leaf1.getParent().getChildren().contains(leaf1));
		Assert.assertTrue(leaf1.getParent().getChildren().contains(leaf2));
		Assert.assertTrue(leaf3.getParent().getChildren().contains(leaf3));
		Assert.assertTrue(leaf3.getParent().getChildren().contains(leaf4));
		Assert.assertTrue(leaf1.getParent().getParent().getChildren().contains(leaf4.getParent()));
		Assert.assertTrue(leaf1.getParent().getParent().getChildren().contains(leaf1.getParent()));
		Assert.assertTrue(leaf3.getParent().getParent().getParent().getChildren().contains(leaf5));
		Assert.assertTrue(leaf3.getParent().getParent().getParent().getChildren()
				.contains(leaf1.getParent().getParent()));

		Assert.assertEquals(4, dendrogram.getHeight());

	}

	@Test
	public void dendrogramMultipleMergesSomeLowerThanPrevious() {
		dendrogram.merge(2, 1, 2);
		dendrogram.merge(2, 3, 4);
		dendrogram.merge(3, 5, 4);
		dendrogram.merge(1, 1, 3);
		dendrogram.merge(1, 2, 3);
		DendrogramNode<Integer> rootNode = dendrogram.getRootNode();
		Collection<DendrogramNode<Integer>> children = rootNode.getChildren();
		for (Integer i : intergers) {
			if (!i.equals(1) && !i.equals(2) && !i.equals(3) && !i.equals(4) && !i.equals(5)) {
				LeafDendrogramNode<Integer> leaf = dendrogram.getLeafNode(i);
				Assert.assertEquals("parent of " + i + " should be the root", rootNode, leaf.getParent());
				Assert.assertTrue(children.contains(leaf));
			}
		}
		Assert.assertEquals(5, children.size());

		LeafDendrogramNode<Integer> leaf1 = dendrogram.getLeafNode(1);
		LeafDendrogramNode<Integer> leaf2 = dendrogram.getLeafNode(2);
		LeafDendrogramNode<Integer> leaf3 = dendrogram.getLeafNode(3);
		LeafDendrogramNode<Integer> leaf4 = dendrogram.getLeafNode(4);
		LeafDendrogramNode<Integer> leaf5 = dendrogram.getLeafNode(5);

		Assert.assertEquals(leaf1.getParent(), leaf2.getParent());
		Assert.assertEquals(leaf2.getParent(), leaf3.getParent());
		Assert.assertEquals(leaf1.getParent().getParent(), leaf4.getParent());
		Assert.assertEquals(leaf1.getParent().getParent().getParent(), leaf5.getParent());
		Assert.assertEquals(rootNode, leaf5.getParent().getParent());

		Assert.assertEquals(4, dendrogram.getHeight());

	}

	@Test
	public void dendrogramMultipleMergesWithRecursiveMerge() {
		dendrogram.merge(2, 1, 2);
		dendrogram.merge(2, 3, 4);
		dendrogram.merge(3, 5, 4);
		dendrogram.merge(3, 2, 6);
		dendrogram.merge(1, 1, 3);
		DendrogramNode<Integer> rootNode = dendrogram.getRootNode();
		Collection<DendrogramNode<Integer>> children = rootNode.getChildren();
		for (Integer i : intergers) {
			if (!i.equals(1) && !i.equals(2) && !i.equals(3) && !i.equals(4) && !i.equals(5) && !i.equals(6)) {
				LeafDendrogramNode<Integer> leaf = dendrogram.getLeafNode(i);
				Assert.assertEquals("parent of " + i + " should be the root", rootNode, leaf.getParent());
				Assert.assertTrue(children.contains(leaf));
			}
		}
		Assert.assertEquals(4, children.size());

		LeafDendrogramNode<Integer> leaf1 = dendrogram.getLeafNode(1);
		LeafDendrogramNode<Integer> leaf2 = dendrogram.getLeafNode(2);
		LeafDendrogramNode<Integer> leaf3 = dendrogram.getLeafNode(3);
		LeafDendrogramNode<Integer> leaf4 = dendrogram.getLeafNode(4);
		LeafDendrogramNode<Integer> leaf5 = dendrogram.getLeafNode(5);
		LeafDendrogramNode<Integer> leaf6 = dendrogram.getLeafNode(6);

		Assert.assertEquals(leaf1.getParent(), leaf3.getParent());
		Assert.assertEquals(leaf2.getParent(), leaf3.getParent().getParent());
		Assert.assertEquals(leaf1.getParent().getParent(), leaf2.getParent());
		Assert.assertEquals(leaf1.getParent().getParent(), leaf4.getParent());
		Assert.assertEquals(leaf1.getParent().getParent().getParent(), leaf6.getParent());
		Assert.assertEquals(leaf1.getParent().getParent().getParent(), leaf5.getParent());
		Assert.assertEquals(rootNode, leaf1.getParent().getParent().getParent().getParent());

		Assert.assertEquals(4, dendrogram.getHeight());

	}

	@Test
	public void dendrogramSortting() {
		dendrogram.merge(2, 1, 2);
		dendrogram.merge(2, 3, 4);
		dendrogram.merge(3, 5, 4);
		dendrogram.merge(3, 2, 6);
		dendrogram.merge(1, 1, 3);
		List<Integer> sortedLeafs = dendrogram.getSortedLeafs();

		Assert.assertEquals(1, Math.abs(sortedLeafs.indexOf(1) - sortedLeafs.indexOf(3)));
		Assert.assertTrue(Math.abs(sortedLeafs.indexOf(2) - sortedLeafs.indexOf(4)) < 4);
		Assert.assertTrue(Math.abs(sortedLeafs.indexOf(3) - sortedLeafs.indexOf(4)) < 4);
		Assert.assertTrue(Math.abs(sortedLeafs.indexOf(1) - sortedLeafs.indexOf(4)) < 4);
		Assert.assertTrue(Math.abs(sortedLeafs.indexOf(5) - sortedLeafs.indexOf(6)) < 6);
	}

	@Test
	public void dendrogramPruning() {
		dendrogram.merge(2, 1, 2);
		dendrogram.merge(1, 1, 2);
		Assert.assertEquals(2, dendrogram.getHeight());
	}

	@Test
	public void dendrogramWithHigherMerge() {
		dendrogram.merge(1, 1, 2);
		dendrogram.merge(2, 1, 2);
		Assert.assertEquals(2, dendrogram.getHeight());
	}

	@Test
	public void dendrogramWithLowerMerge() {
		dendrogram.merge(1, 1, 2);
		dendrogram.merge(1, 3, 4);
		dendrogram.merge(2, 1, 3);
		dendrogram.merge(3, 1, 5);
		dendrogram.merge(1, 1, 5);
		Assert.assertEquals(3, dendrogram.getHeight());
	}

	@Test
	public void dendrogramPruningWithRoot() {
		dendrogram.merge(1, 1, 2);
		dendrogram.merge(1, 1, 3);
		dendrogram.merge(1, 1, 4);
		dendrogram.merge(1, 1, 5);
		dendrogram.merge(1, 1, 6);
		dendrogram.merge(1, 1, 7);
		dendrogram.merge(1, 1, 8);
		dendrogram.merge(1, 1, 9);
		Assert.assertEquals(1, dendrogram.getHeight());
	}

	@Test
	public void dendrogramMergeError() {
		dendrogram.merge(1, 1, 2);
		dendrogram.merge(4, 1, 3);
		dendrogram.merge(6, 1, 4);
		dendrogram.merge(7, 1, 5);
		dendrogram.merge(9, 1, 6);
		dendrogram.merge(1, 8, 9);
		dendrogram.merge(5, 7, 9);
		dendrogram.merge(6, 1, 9);
		dendrogram.merge(3, 1, 9);

		Iterator<KeyedDendrogramNode<Integer, Integer>> keyedIterator = dendrogram.getKeyedIterator();
		while (keyedIterator.hasNext()) {
			KeyedDendrogramNode<Integer, Integer> next = keyedIterator.next();
			if (next.getChildren().size() == 0) {
				Assert.fail(next + " should have children");
			}
		}
	}

	@Test
	public void dendrogramLoopBug() {
		Collection<Integer> leafs = new ArrayList<Integer>();
		for (int i = 0; i < 38; i++) {
			leafs.add(i);
		}

		KeyedDendrogram<Integer, Double> dendrogram = new KeyedDendrogram<Integer, Double>(leafs,
				new Comparator<Double>() {
					@Override
					public int compare(Double o1, Double o2) {
						return -o1.compareTo(o2);
					}
				});

		dendrogram.merge(0.6, 0, 21);
		dendrogram.merge(0.4, 0, 1);
		dendrogram.merge(0.5555555555555556, 0, 2);
		dendrogram.merge(0.6363636363636364, 0, 3);
		dendrogram.merge(0.5555555555555556, 1, 21);
		dendrogram.merge(0.5, 1, 2);
		dendrogram.merge(0.6, 1, 3);
		dendrogram.merge(0.4, 2, 21);
		dendrogram.merge(0.6, 2, 3);
		dendrogram.merge(0.6363636363636364, 3, 21);
		dendrogram.merge(0.5, 4, 10);
		dendrogram.merge(0.45454545454545453, 4, 5);
		dendrogram.merge(0.4, 4, 6);
		dendrogram.merge(0.5555555555555556, 4, 7);
		dendrogram.merge(0.6363636363636364, 4, 8);
		dendrogram.merge(0.5555555555555556, 4, 9);
		dendrogram.merge(0.6666666666666666, 5, 10);
		dendrogram.merge(0.5555555555555556, 5, 6);
		dendrogram.merge(0.4, 5, 7);
		dendrogram.merge(0.6363636363636364, 5, 8);
		dendrogram.merge(0.5555555555555556, 5, 9);
		dendrogram.merge(0.3, 6, 10);
		dendrogram.merge(0.5, 6, 7);
		dendrogram.merge(0.6, 6, 8);
		dendrogram.merge(0.3333333333333333, 6, 9);
		dendrogram.merge(0.4444444444444444, 7, 10);
		dendrogram.merge(0.6, 7, 8);
	}

	@Test
	public void dendrogramMissingChildrenBug() {
		Collection<Integer> leafs = new ArrayList<Integer>();
		for (int i = 0; i < 113; i++) {
			leafs.add(i);
		}

		KeyedDendrogram<Integer, Double> dendrogram = new KeyedDendrogram<Integer, Double>(leafs,
				new Comparator<Double>() {
					@Override
					public int compare(Double o1, Double o2) {
						return -o1.compareTo(o2);
					}
				});

		dendrogram.merge(0.47058823529411764, 0, 107);
		dendrogram.merge(0.46153846153846156, 0, 13);
		dendrogram.merge(0.5333333333333333, 0, 1);
		dendrogram.merge(0.5294117647058824, 0, 34);
		dendrogram.merge(0.5333333333333333, 0, 2);
		dendrogram.merge(0.5714285714285714, 0, 3);
		dendrogram.merge(0.5, 0, 4);
		dendrogram.merge(0.5333333333333333, 0, 73);
		dendrogram.merge(0.5384615384615384, 0, 5);
		dendrogram.merge(0.5625, 13, 34);
		dendrogram.merge(0.75, 1, 107);
		dendrogram.merge(0.7058823529411765, 1, 34);
		dendrogram.merge(0.625, 1, 2);
		dendrogram.merge(0.5625, 1, 3);
		dendrogram.merge(0.6875, 1, 4);
		dendrogram.merge(0.8571428571428571, 1, 73);
		dendrogram.merge(0.8235294117647058, 34, 107);
		dendrogram.merge(0.6470588235294118, 2, 107);
		dendrogram.merge(0.7058823529411765, 2, 34);
		dendrogram.merge(0.5625, 2, 3);
		dendrogram.merge(0.8, 2, 4);
		dendrogram.merge(0.5333333333333333, 2, 5);
		dendrogram.merge(0.5882352941176471, 3, 107);
		dendrogram.merge(0.6470588235294118, 3, 34);
		dendrogram.merge(0.5714285714285714, 3, 5);
		dendrogram.merge(0.8125, 4, 107);
		dendrogram.merge(0.75, 73, 107);
		dendrogram.merge(0.5882352941176471, 12, 18);
		dendrogram.merge(0.75, 12, 74);
		dendrogram.merge(0.6470588235294118, 12, 81);
		dendrogram.merge(0.5625, 6, 12);
		dendrogram.merge(0.5714285714285714, 6, 18);
		dendrogram.merge(0.5294117647058824, 6, 7);
		dendrogram.merge(0.6428571428571429, 6, 8);
		dendrogram.merge(0.5384615384615384, 6, 9);
		dendrogram.merge(0.7142857142857143, 6, 10);
		dendrogram.merge(0.6153846153846154, 6, 11);
		dendrogram.merge(0.7857142857142857, 18, 74);
		dendrogram.merge(0.8235294117647058, 7, 12);
		dendrogram.merge(0.7058823529411765, 7, 8);
		dendrogram.merge(0.7647058823529411, 7, 10);
		dendrogram.merge(0.7058823529411765, 7, 81);
		dendrogram.merge(0.5882352941176471, 7, 11);
		dendrogram.merge(0.8, 8, 10);
		dendrogram.merge(0.7142857142857143, 8, 11);
		dendrogram.merge(0.5625, 9, 12);
		dendrogram.merge(0.6, 9, 10);
		dendrogram.merge(0.6428571428571429, 9, 81);
		dendrogram.merge(0.8125, 10, 12);
		dendrogram.merge(0.7692307692307693, 11, 18);
		dendrogram.merge(0.5714285714285714, 13, 14);
		dendrogram.merge(0.5294117647058824, 13, 35);
		dendrogram.merge(0.5333333333333333, 13, 15);
		dendrogram.merge(0.5714285714285714, 13, 16);
		dendrogram.merge(0.5333333333333333, 13, 82);
		dendrogram.merge(0.4, 13, 89);
		dendrogram.merge(0.5384615384615384, 13, 17);
		dendrogram.merge(0.6470588235294118, 14, 35);
		dendrogram.merge(0.6666666666666666, 14, 15);
		dendrogram.merge(0.5625, 14, 82);
		dendrogram.merge(0.7692307692307693, 14, 89);
		dendrogram.merge(0.7058823529411765, 35, 82);
		dendrogram.merge(0.7058823529411765, 15, 35);
		dendrogram.merge(0.7142857142857143, 15, 89);
		dendrogram.merge(0.6470588235294118, 16, 35);
		dendrogram.merge(0.5714285714285714, 17, 16);
		dendrogram.merge(0.8125, 26, 64);
		dendrogram.merge(0.5294117647058824, 26, 90);
		dendrogram.merge(0.6875, 26, 95);
		dendrogram.merge(0.5625, 18, 26);
		dendrogram.merge(0.5714285714285714, 18, 20);
		dendrogram.merge(0.5333333333333333, 18, 21);
		dendrogram.merge(0.5294117647058824, 18, 22);
		dendrogram.merge(0.6428571428571429, 18, 23);
		dendrogram.merge(0.375, 18, 24);
		dendrogram.merge(0.7142857142857143, 18, 64);
		dendrogram.merge(0.5333333333333333, 18, 25);
		dendrogram.merge(0.6153846153846154, 18, 90);
		dendrogram.merge(0.5714285714285714, 18, 95);
		dendrogram.merge(0.4117647058823529, 14, 26);
		dendrogram.merge(0.4666666666666667, 14, 21);
		dendrogram.merge(0.5625, 14, 22);
		dendrogram.merge(0.5, 14, 24);
		dendrogram.merge(0.5882352941176471, 20, 26);
		dendrogram.merge(0.6470588235294118, 20, 22);
		dendrogram.merge(0.6666666666666666, 20, 23);
		dendrogram.merge(0.625, 20, 64);
		dendrogram.merge(0.7857142857142857, 20, 25);
		dendrogram.merge(0.7692307692307693, 20, 90);
		dendrogram.merge(0.6, 20, 95);
		dendrogram.merge(0.75, 21, 26);
		dendrogram.merge(0.6666666666666666, 21, 20);
		dendrogram.merge(0.7058823529411765, 21, 22);
		dendrogram.merge(0.5625, 21, 24);
		dendrogram.merge(0.8571428571428571, 21, 25);
		dendrogram.merge(0.6666666666666666, 21, 95);
		dendrogram.merge(0.8235294117647058, 22, 26);
		dendrogram.merge(0.6470588235294118, 22, 24);
		dendrogram.merge(0.7058823529411765, 22, 25);
		dendrogram.merge(0.7058823529411765, 23, 22);
		dendrogram.merge(0.8, 23, 64);
		dendrogram.merge(0.7142857142857143, 23, 90);
		dendrogram.merge(0.7333333333333333, 64, 95);
		dendrogram.merge(0.75, 25, 26);
		dendrogram.merge(0.47058823529411764, 1, 33);
		dendrogram.merge(0.5714285714285714, 1, 21);
		dendrogram.merge(0.5294117647058824, 1, 37);
		dendrogram.merge(0.5333333333333333, 1, 27);
		dendrogram.merge(0.38461538461538464, 1, 46);
		dendrogram.merge(0.5714285714285714, 1, 28);
		dendrogram.merge(0.5, 1, 29);
		dendrogram.merge(0.5333333333333333, 1, 30);
		dendrogram.merge(0.5333333333333333, 1, 31);
		dendrogram.merge(0.4, 1, 32);
		dendrogram.merge(0.4666666666666667, 1, 96);
		dendrogram.merge(0.8235294117647058, 33, 37);
		dendrogram.merge(0.4375, 33, 46);
		dendrogram.merge(0.6875, 33, 96);
		dendrogram.merge(0.5882352941176471, 21, 33);
		dendrogram.merge(0.6470588235294118, 21, 37);
		dendrogram.merge(0.6666666666666666, 21, 27);
		dendrogram.merge(0.625, 21, 29);
		dendrogram.merge(0.7857142857142857, 21, 30);
		dendrogram.merge(0.5625, 21, 31);
		dendrogram.merge(0.7692307692307693, 21, 32);
		dendrogram.merge(0.6470588235294118, 27, 33);
		dendrogram.merge(0.7058823529411765, 27, 37);
		dendrogram.merge(0.8, 27, 29);
		dendrogram.merge(0.7142857142857143, 27, 32);
		dendrogram.merge(0.5882352941176471, 28, 33);
		dendrogram.merge(0.6470588235294118, 28, 37);
		dendrogram.merge(0.5384615384615384, 28, 46);
		dendrogram.merge(0.5625, 28, 31);
		dendrogram.merge(0.8125, 29, 33);
		dendrogram.merge(0.5882352941176471, 29, 31);
		dendrogram.merge(0.7333333333333333, 29, 96);
		dendrogram.merge(0.75, 30, 33);
		dendrogram.merge(0.6470588235294118, 31, 33);
		dendrogram.merge(0.7058823529411765, 31, 37);
		dendrogram.merge(0.5333333333333333, 34, 38);
		dendrogram.merge(0.38461538461538464, 34, 47);
		dendrogram.merge(0.42857142857142855, 34, 39);
		dendrogram.merge(0.5714285714285714, 34, 60);
		dendrogram.merge(0.5, 34, 65);
		dendrogram.merge(0.5333333333333333, 34, 40);
		dendrogram.merge(0.5333333333333333, 34, 83);
		dendrogram.merge(0.4, 34, 41);
		dendrogram.merge(0.4666666666666667, 34, 97);
		dendrogram.merge(0.5384615384615384, 34, 103);
		dendrogram.merge(0.6428571428571429, 7, 38);
		dendrogram.merge(0.5384615384615384, 7, 39);
		dendrogram.merge(0.7142857142857143, 7, 65);
		dendrogram.merge(0.5333333333333333, 7, 40);
		dendrogram.merge(0.5333333333333333, 7, 83);
		dendrogram.merge(0.6153846153846154, 7, 41);
		dendrogram.merge(0.5714285714285714, 7, 97);
		dendrogram.merge(0.5714285714285714, 22, 34);
		dendrogram.merge(0.6666666666666666, 22, 38);
		dendrogram.merge(0.42857142857142855, 22, 47);
		dendrogram.merge(0.7857142857142857, 22, 40);
		dendrogram.merge(0.5625, 22, 83);
		dendrogram.merge(0.7692307692307693, 22, 41);
		dendrogram.merge(0.6, 22, 97);
		dendrogram.merge(0.6153846153846154, 37, 47);
		dendrogram.merge(0.8571428571428571, 37, 40);
		dendrogram.merge(0.6666666666666666, 37, 97);
		dendrogram.merge(0.8, 38, 65);
		dendrogram.merge(0.7142857142857143, 38, 41);
		dendrogram.merge(0.6, 39, 65);
		dendrogram.merge(0.6428571428571429, 39, 83);
		dendrogram.merge(0.6666666666666666, 39, 103);
		dendrogram.merge(0.5714285714285714, 60, 103);
		dendrogram.merge(0.7333333333333333, 65, 97);
		dendrogram.merge(0.47058823529411764, 2, 110);
		dendrogram.merge(0.5714285714285714, 2, 23);
		dendrogram.merge(0.42857142857142855, 2, 52);
		dendrogram.merge(0.5, 2, 66);
		dendrogram.merge(0.5333333333333333, 2, 75);
		dendrogram.merge(0.5333333333333333, 2, 44);
		dendrogram.merge(0.4666666666666667, 2, 45);
		dendrogram.merge(0.5625, 8, 110);
		dendrogram.merge(0.5384615384615384, 8, 52);
		dendrogram.merge(0.7142857142857143, 8, 66);
		dendrogram.merge(0.5333333333333333, 8, 75);
		dendrogram.merge(0.5333333333333333, 8, 44);
		dendrogram.merge(0.5714285714285714, 8, 45);
		dendrogram.merge(0.5625, 15, 38);
		dendrogram.merge(0.5882352941176471, 23, 110);
		dendrogram.merge(0.7857142857142857, 23, 75);
		dendrogram.merge(0.5625, 23, 44);
		dendrogram.merge(0.6, 23, 45);
		dendrogram.merge(0.75, 27, 110);
		dendrogram.merge(0.8571428571428571, 27, 75);
		dendrogram.merge(0.6666666666666666, 27, 45);
		dendrogram.merge(0.8235294117647058, 38, 110);
		dendrogram.merge(0.7647058823529411, 38, 66);
		dendrogram.merge(0.7058823529411765, 38, 44);
		dendrogram.merge(0.5625, 52, 110);
		dendrogram.merge(0.6, 52, 66);
		dendrogram.merge(0.8125, 66, 110);
		dendrogram.merge(0.75, 75, 110);
		dendrogram.merge(0.6428571428571429, 44, 52);
		dendrogram.merge(0.6875, 45, 110);
		dendrogram.merge(0.7333333333333333, 45, 66);
		dendrogram.merge(0.5882352941176471, 51, 48);
		dendrogram.merge(0.75, 51, 49);
		dendrogram.merge(0.6470588235294118, 51, 85);
		dendrogram.merge(0.6875, 51, 50);
		dendrogram.merge(0.75, 46, 51);
		dendrogram.merge(0.7058823529411765, 46, 47);
		dendrogram.merge(0.8571428571428571, 46, 49);
		dendrogram.merge(0.8235294117647058, 47, 51);
		dendrogram.merge(0.6470588235294118, 47, 48);
		dendrogram.merge(0.7058823529411765, 47, 85);
		dendrogram.merge(0.5625, 9, 112);
		dendrogram.merge(0.6428571428571429, 9, 52);
		dendrogram.merge(0.5384615384615384, 9, 53);
		dendrogram.merge(0.375, 9, 54);
		dendrogram.merge(0.7142857142857143, 9, 55);
		dendrogram.merge(0.5333333333333333, 9, 86);
		dendrogram.merge(0.5714285714285714, 9, 56);
		dendrogram.merge(0.3333333333333333, 9, 57);
		dendrogram.merge(0.8235294117647058, 39, 112);
		dendrogram.merge(0.7058823529411765, 39, 52);
		dendrogram.merge(0.6470588235294118, 39, 54);
		dendrogram.merge(0.7647058823529411, 39, 55);
		dendrogram.merge(0.7058823529411765, 39, 86);
		dendrogram.merge(0.6470588235294118, 39, 56);
		dendrogram.merge(0.5294117647058824, 39, 57);
		dendrogram.merge(0.6470588235294118, 52, 112);
		dendrogram.merge(0.5333333333333333, 52, 57);
		dendrogram.merge(0.5625, 53, 112);
		dendrogram.merge(0.6428571428571429, 53, 86);
		dendrogram.merge(0.6666666666666666, 53, 57);
		dendrogram.merge(0.8125, 55, 112);
		dendrogram.merge(0.8, 55, 52);
		dendrogram.merge(0.7333333333333333, 55, 56);
		dendrogram.merge(0.47058823529411764, 3, 63);
		dendrogram.merge(0.5714285714285714, 3, 24);
		dendrogram.merge(0.38461538461538464, 3, 48);
		dendrogram.merge(0.5333333333333333, 3, 76);
		dendrogram.merge(0.5333333333333333, 3, 61);
		dendrogram.merge(0.5384615384615384, 3, 62);
		dendrogram.merge(0.8235294117647058, 63, 60);
		dendrogram.merge(0.75, 63, 76);
		dendrogram.merge(0.6470588235294118, 63, 61);
		dendrogram.merge(0.5625, 16, 60);
		dendrogram.merge(0.5882352941176471, 24, 63);
		dendrogram.merge(0.6666666666666666, 24, 28);
		dendrogram.merge(0.6470588235294118, 24, 60);

		Iterator<KeyedDendrogramNode<Integer, Double>> keyedIterator = dendrogram.getKeyedIterator();
		while (keyedIterator.hasNext()) {
			KeyedDendrogramNode<Integer, Double> next = keyedIterator.next();
			if (next.getChildren().size() == 0) {
				Assert.fail(next + " should have children");
			}
		}
	}
}
