package net.stuarthendren.jung.dendrogram.graph;

import java.util.List;

import net.stuarthendren.jung.dendrogram.Dendrogram;

/**
 * A Graph Dendrogram is a graph representation of a {@link Dendrogram}.
 * 
 * The graph is made up of {@link DendrogramNode}s and forms a Tree structure.
 * 
 * The leafs of the tree represent a cluster containing a single object of the clustering set. The root node is the
 * cluster containing all the objects. Each node in the tree encodes a cluster of the hierarchy containing the object in
 * the leaf of the tree from that branch.
 * 
 * @author Stuart Hendren
 * 
 * @param <T>
 */
public interface GraphDendrogram<T> extends Dendrogram<T> {

	/**
	 * @return a list of the leafs in an order that is suitable for visualising the clustering
	 */
	List<T> getSortedLeafs();

	/**
	 * @return the root node of the dendrogram
	 */
	DendrogramNode<T> getRootNode();

	/**
	 * Returns the distance of <code>node</code> from the root. That is the number of parents to reach the root.
	 * 
	 */
	int getDepth(DendrogramNode<T> node);

	/**
	 * @return the maximum depth of all the nodes
	 */
	int getHeight();

	/**
	 * @param t
	 *            the object from the set to cluster
	 * @return the leaf node that represents the cluster containing just that object
	 */
	DendrogramNode<T> getLeaf(T t);

}