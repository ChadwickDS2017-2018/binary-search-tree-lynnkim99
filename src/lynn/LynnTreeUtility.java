package lynn;

import java.util.Iterator;

import structures.BinaryTreeNode;
import structures.BinaryTreeUtility;

public class LynnTreeUtility implements BinaryTreeUtility {

	@Override
	public <T> Iterator<T> getPreOrderIterator(BinaryTreeNode<T> root) {
		return new LynnPreOrderIterator<>(root);
	}

	@Override
	public <T> Iterator<T> getInOrderIterator(BinaryTreeNode<T> root) {
		return new LynnInOrderIterator<>(root);
	}

	@Override
	public <T> Iterator<T> getPostOrderIterator(BinaryTreeNode<T> root) {
		return new LynnPostOrderIterator<>(root);
	}

	@Override
	public <T> int getDepth(BinaryTreeNode<T> root) {
		if (root == null) {
			throw new NullPointerException();
		}

		int leftDepth = (root.hasLeftChild()) ? getDepth(root.getLeftChild()) : 0;
		int rightDepth = (root.hasRightChild()) ? getDepth(root.getRightChild()) : 0;
		int ownDepth = (root.hasLeftChild() || root.hasRightChild()) ? 1 : 0;

		return Math.max(leftDepth, rightDepth) + ownDepth;
	}

	@Override
	public <T> boolean isBalanced(BinaryTreeNode<T> root, int tolerance) {
		if (tolerance < 0) {
			throw new IllegalArgumentException();
		}
		if (root == null) {
			throw new NullPointerException();
		}

		int leftDepth, rightDepth;
		boolean leftBalanced, rightBalanced;

		if (root.hasLeftChild()) {
			leftDepth = getDepth(root.getLeftChild()) + 1;
			leftBalanced = isBalanced(root.getLeftChild(), tolerance);
		} else {
			leftDepth = 0;
			leftBalanced = true;
		}

		if (root.hasRightChild()) {
			rightDepth = getDepth(root.getRightChild()) + 1;
			rightBalanced = isBalanced(root.getRightChild(), tolerance);
		} else {
			rightDepth = 0;
			rightBalanced = true;
		}

		return Math.abs(leftDepth - rightDepth) <= tolerance && leftBalanced && rightBalanced;
	}

	@Override
	public <T extends Comparable<? super T>> boolean isBST(BinaryTreeNode<T> root) {
		if (root == null) {
			throw new NullPointerException();
		}

		return (!root.hasLeftChild() || root.getLeftChild().getData().compareTo(root.getData()) <= 0)
				&& (!root.hasRightChild() || root.getRightChild().getData().compareTo(root.getData()) > 0)
				&& (!root.hasLeftChild() || isBST(root.getLeftChild()))
				&& (!root.hasRightChild() || isBST(root.getRightChild()));

	}

}
