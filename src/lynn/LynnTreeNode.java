package lynn;

import structures.BinaryTreeNode;

public class LynnTreeNode<T> implements BinaryTreeNode<T> {
	
	private T data;
	private BinaryTreeNode<T> left;
	private BinaryTreeNode<T> right;
	
	public LynnTreeNode(BinaryTreeNode<T> left, T elem, BinaryTreeNode<T> right) {
		this.data = elem;
		this.left = left;
		this.right = right;
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public void setData(T data) {
		this.data = data;
	}

	@Override
	public boolean hasLeftChild() {
		return left != null;
	}

	@Override
	public boolean hasRightChild() {
		return right != null;
	}

	@Override
	public BinaryTreeNode<T> getLeftChild() {
		if (left == null)
			throw new IllegalStateException();
		return left;
	}

	@Override
	public BinaryTreeNode<T> getRightChild() {
		if (right == null)
			throw new IllegalStateException();
		return right;
	}

	@Override
	public void setLeftChild(BinaryTreeNode<T> left) {
		this.left = left;
	}

	@Override
	public void setRightChild(BinaryTreeNode<T> right) {
		this.right = right;
	}

}
