package net.stuarthendren.jung.dendrogram.graph;

import java.util.Collection;
import java.util.Set;

/**
 * A DendrogramNode is a node of the graph encoded dendrogram {@link GraphDendrogram}.
 * 
 * @author Stuart Hendren
 * 
 * @param <T>
 */
public interface DendrogramNode<T> {

	/**
	 * Every node from a complete graph dendrogram has a parent except the root node.
	 * 
	 * @return the parent node.
	 */
	DendrogramNode<T> getParent();

	/**
	 * Get the children of a node. All nodes from a complete graph dendrogram should have two or more children except
	 * the leaf nodes that have no children
	 * 
	 * @return
	 */
	Collection<DendrogramNode<T>> getChildren();

	/**
	 * @return true is the node is a leaf (ie has no children)
	 */
	boolean isLeaf();

	/**
	 * @return the set of objects contained in this node, that is the cluster of objects that this node represents.
	 * 
	 */
	Set<T> getContents();

}
