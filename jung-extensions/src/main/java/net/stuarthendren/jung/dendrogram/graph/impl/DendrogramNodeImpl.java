package net.stuarthendren.jung.dendrogram.graph.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.stuarthendren.jung.dendrogram.graph.DendrogramNode;

public class DendrogramNodeImpl<T> implements DendrogramNode<T> {

	protected DendrogramNodeImpl<T> parent;

	protected Collection<DendrogramNodeImpl<T>> children = new HashSet<DendrogramNodeImpl<T>>();

	private Collection<? extends DendrogramNode<T>> unmodifiableChildren = Collections.unmodifiableCollection(children);

	@Override
	public DendrogramNode<T> getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	public DendrogramNodeImpl<T> setParent(DendrogramNodeImpl<T> parent) {
		if (this.parent == parent) {
			return null;
		}
		DendrogramNode<T> holder = parent;
		while (holder != null) {
			if (this == holder) {
				throw new IllegalArgumentException("Loop!");
			}
			holder = holder.getParent();
		}

		DendrogramNodeImpl<T> oldParent = this.parent;
		if (oldParent != null) {
			oldParent.removeChild(this);
		}
		this.parent = parent;
		if (this.parent != null) {
			this.parent.addChild(this);
		}

		return oldParent;
	}

	public void addChild(DendrogramNodeImpl<T> dendrogramNode) {
		children.add(dendrogramNode);
	}

	public void removeChild(DendrogramNodeImpl<T> dendrogramNode) {
		children.remove(dendrogramNode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<DendrogramNode<T>> getChildren() {
		return (Collection<DendrogramNode<T>>) unmodifiableChildren;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		Iterator<DendrogramNodeImpl<T>> i = children.iterator();
		if (!i.hasNext()) {
			return "[ ]";
		}
		for (;;) {
			DendrogramNode<T> node = i.next();
			builder.append(node.toString());
			if (!i.hasNext()) {
				return builder.append(']').toString();
			} else {
				builder.append(", ");
			}
		}
	}

	@Override
	public Set<T> getContents() {
		Set<T> contents = new HashSet<T>();
		for (DendrogramNode<T> child : children) {
			contents.addAll(child.getContents());
		}
		return contents;
	}
}
