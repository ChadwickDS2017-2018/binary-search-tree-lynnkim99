package lynn;

import java.util.Iterator;

import structures.BinarySearchTree;
import structures.BinaryTreeNode;

public class LynnBST <T extends Comparable<? super T>> implements BinarySearchTree<T> {
	
	private BinaryTreeNode<T> root;
	private int size = 0;
	private static final boolean LEFT = true;
	private static final boolean RIGHT = false;
	
	public LynnBST() {
		
	}

	@Override
	public BinarySearchTree<T> add(T toAdd) {
		if (toAdd == null) {
			throw new NullPointerException();
		}
		debug("adding " + toAdd);
		
		if (root == null) {
			root = new LynnTreeNode<T>(null, toAdd, null);
			debug(" to root. ");
		} else {
			add(root, toAdd);
		}
		
		
		size++;
		return this;
	}
	
	private void add(BinaryTreeNode<T> n, T toAdd) {
		if (n.getData().compareTo(toAdd) >= 0) {
			if (!n.hasLeftChild()) {
				debug(" as left of " + n.getData());
				n.setLeftChild(new LynnTreeNode<T>(null, toAdd, null));
			} else {
				debug(" moving to left of " + n.getData());
				add(n.getLeftChild(), toAdd);
			}
		} else {
			if (!n.hasRightChild()) {
				debug(" as right of " + n.getData());
				n.setRightChild(new LynnTreeNode<T>(null, toAdd, null));
			} else {
				debug(" moving to right of " + n.getData());
				add(n.getRightChild(), toAdd);
			}
		}
	}

	@Override
	public boolean contains(T toFind) {
		if (toFind == null) {
			throw new NullPointerException();
		}
		if (root == null) {
			return false;
		}
		
		return contains(root, toFind);
	}

	private boolean contains(BinaryTreeNode<T> n, T toFind) {
		
		if (n.getData().equals(toFind)) {
			return true;
		} else if (n.getData().compareTo(toFind) >= 0) {
			if (!n.hasLeftChild()) {
				return false;
			} else {
				return contains(n.getLeftChild(), toFind);
			}
		} else {
			if (!n.hasRightChild()) {
				return false;
			} else {
				return contains(n.getRightChild(), toFind);
			}
		}
	}

	@Override
	public boolean remove(T toRemove) {
		if (root == null)
			return false;
		else {
			boolean ret = false;
			if (root.getData().equals(toRemove)) {
				BinaryTreeNode<T> newRoot = new LynnTreeNode<T>(root, null, null); // value doesn't matter

				ret = findAndRemove(newRoot, toRemove);
				root = (newRoot.hasLeftChild()) ? newRoot.getLeftChild() : null;
				
			} else {
				ret = findAndRemove(root, toRemove);
			}
			
			if (ret) size--;
			return ret;
		}
	}
	
	// make sure n is never the node to remove the value from.
	private boolean findAndRemove(BinaryTreeNode<T> n, T toRemove) {
		if (n==null) return false;
		
		// find the node
		if (n.getData() == null || toRemove.compareTo(n.getData())<=0) {
			// go left
			if (!n.hasLeftChild()) return false;
			if (n.getLeftChild().getData().equals(toRemove)) {
				remove(n, LEFT);
				return true;
			}
			return findAndRemove(n.getLeftChild(), toRemove);
			
		}
		else {
			// go right
			if (!n.hasRightChild()) return false;
			if (n.getRightChild().getData().equals(toRemove)) {
				remove(n, RIGHT);
				return true;
			}
			return findAndRemove(n.getRightChild(), toRemove);
		}
		
		
	}
	
	private void remove(BinaryTreeNode<T> parent, boolean direction) {
		
		BinaryTreeNode<T> target = (direction == LEFT) ? parent.getLeftChild() : parent.getRightChild();
		
		if (!target.hasLeftChild() && !target.hasRightChild()) {
			if (direction == LEFT) {
				parent.setLeftChild(null);
			} else {
				parent.setRightChild(null);
			}
		}
		else if (target.hasLeftChild() && !target.hasRightChild()) {
			if (direction == LEFT) {
				parent.setLeftChild(target.getLeftChild());
			} else {
				parent.setRightChild(target.getLeftChild());
			}
		}
		else if (!target.hasLeftChild() && target.hasRightChild()) {
			if (direction == LEFT) {
				parent.setLeftChild(target.getRightChild());
			} else {
				parent.setRightChild(target.getRightChild());
			}
		}
		else {
			// target has 2 children!
			BinaryTreeNode<T> toReplace = getMinimumNode(target.getRightChild());
			target.setData(toReplace.getData());
			
			// remove swapped node
			if(toReplace == target.getRightChild()) {
				target.setRightChild(null);
			} else {
				findAndRemove(target.getRightChild(), toReplace.getData());
			}
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public T getMinimum() {
		if (root == null) {
			throw new IllegalStateException();
		}
		
		return getMinimumNode(root).getData();
	}
	
	private BinaryTreeNode<T> getMinimumNode(BinaryTreeNode<T> n) {
		if(n == null) return null;
		
		while(n.hasLeftChild()) {
			n = n.getLeftChild();
		}
		
		return n;
	}
	
	@Override
	public T getMaximum() {
		if (root == null) {
			throw new IllegalStateException();
		}
		
		return getMaximumNode(root).getData();
	}
	
	private BinaryTreeNode<T> getMaximumNode(BinaryTreeNode<T> n) {
		if(n == null) return null;
		
		while(n.hasRightChild()) {
			n = n.getRightChild();
		}
		
		return n;
	}

	@Override
	public BinaryTreeNode<T> toBinaryTreeNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return new LynnInOrderIterator<>(root);
	}
	
	public static void main(String args[]) {
		LynnBST<Integer> tree = new LynnBST<>();
		tree.add(10);
		tree.add(5);
		tree.add(5);
		debug("size : " + tree.size());
		debug("contains 5 : " + tree.contains(5));
		debug("contains 6 : " + tree.contains(6));
		debug("removing 5 : " + tree.remove(10));
		debug("removing 5 : " + tree.remove(5));
		debug("removing 5 : " + tree.remove(5));
		debug("removing 6 : " + tree.remove(6));
	}
	
	private static void debug(String str) {
		//System.out.println(str);
		
	}
	
}
