package lynn;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import structures.BinaryTreeNode;

public class LynnPostOrderIterator<T> implements Iterator<T>{
	
	private final Deque<BinaryTreeNode<T>> stack;
	private BinaryTreeNode lastVisited;
	
	public LynnPostOrderIterator(BinaryTreeNode<T> root){
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
		else if (n.hasRightChild()) {
			pushLeft(n.getRightChild());
		}
	}
	
	@Override
	public boolean hasNext() {
		return !stack.isEmpty();
	}

	@Override
	public T next() {
		BinaryTreeNode<T> toVisit = stack.pop();
		
		if (toVisit.hasLeftChild() && toVisit.getLeftChild().equals(lastVisited)) {
			stack.push(toVisit);
			if (toVisit.hasRightChild()) {
				pushLeft(toVisit.getRightChild());
			}
			lastVisited = null;
			return next();
		} else {
			lastVisited = toVisit;
			
			for(BinaryTreeNode<T> elem : stack) {
				System.out.print(elem.getData() + " ");
			}
			System.out.println();
			
			return toVisit.getData();
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
