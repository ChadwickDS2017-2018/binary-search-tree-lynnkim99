package lynn;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import structures.BinaryTreeNode;

public class LynnInOrderIterator<T> implements Iterator<T>{
	
	private final Deque<BinaryTreeNode<T>> stack;
	
	public LynnInOrderIterator(BinaryTreeNode<T> root){
		if (root == null) {
			throw new NullPointerException();
		}
		stack = new LinkedList<BinaryTreeNode<T>>();
		pushLeft(root);
	}
	
	private void pushLeft(BinaryTreeNode<T> n) {
		stack.push(n);
		if (n.hasLeftChild()) {
			pushLeft(n.getLeftChild());
		}
	}
	
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public T next() {
		BinaryTreeNode<T> toVisit = stack.pop();
		
		if (toVisit.hasRightChild()) {
			pushLeft(toVisit.getRightChild());
		}
		
		return toVisit.getData();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
