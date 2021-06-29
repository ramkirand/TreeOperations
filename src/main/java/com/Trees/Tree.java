package com.Trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import lombok.Data;

@Data
public class Tree {

	private TreeNode root;

	Tree() {
		root = null;
	}

	public void add(int x) {
		
		TreeNode p = new TreeNode();
		p.setData(x);
		p.setLeftChild(null);
		p.setRightChild(null);
		TreeNode tempRoot = root;
		if (root != null) {
			while (true) {
				if (tempRoot.getData() < x) {
					if (tempRoot.getRightChild() == null) {
						tempRoot.setRightChild(p);
						break;
					} else
						tempRoot = tempRoot.getRightChild();
				} else if (tempRoot.getData() > x) {
					if (tempRoot.getLeftChild() == null) {
						tempRoot.setLeftChild(p);
						break;
					} else
						tempRoot = tempRoot.getLeftChild();
				} else {
					System.out.println("Duplicate");
					break;
				}
			}

		} else {
			root = p;
		}

	}

	/*
	 * Given two integer arrays preorder and inorder where preorder is the preorder
	 * traversal of a binary tree and inorder is the inorder traversal of the same
	 * tree, construct and return the binary tree.
	 * 
	 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7] Output:
	 * [3,9,20,null,null,15,7] Example 2:
	 * 
	 * Input: preorder = [-1], inorder = [-1] Output: [-1]
	 * 
	 * 
	 * Constraints:
	 * 
	 * 1 <= preorder.length <= 3000 inorder.length == preorder.length -3000 <=
	 * preorder[i], inorder[i] <= 3000 preorder and inorder consist of unique
	 * values. Each value of inorder also appears in preorder. preorder is
	 * guaranteed to be the preorder traversal of the tree. inorder is guaranteed to
	 * be the inorder traversal of the tree.
	 */

	public TreeNode buildTree(int[] preorder, int[] inorder) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		for (int i = 0; i < inorder.length; i++) {
			map.put(inorder[i], i);
		}

		return helper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);

	}

	private TreeNode helper(int[] preorder, int pLeft, int pRight, int[] inorder, int iLeft, int iRight,
			Map<Integer, Integer> map) {
		if (pRight < pLeft || iLeft > iRight)
			return null;
		int index = map.get(preorder[pLeft]);
		TreeNode curr = new TreeNode();
		curr.setData(preorder[index]);

		curr.setLeftChild(helper(preorder, pLeft + 1, pLeft + index - iLeft, inorder, iLeft, index - 1, map));
		curr.setRightChild(helper(preorder, pLeft + index - iLeft + 1, pRight, inorder, index + 1, iRight, map));

		return curr;
	}

	public int height(TreeNode root) {
		if (root == null)
			return 0;
		return 1 + Math.max(height(root.getLeftChild()), height(root.getRightChild()));
	}

	public void printLevel(TreeNode root) {
		for (int i = 1; i <= height(root); i++)
			printAtlevel(root, i);
	}

	private void printAtlevel(TreeNode root, int level) {
		if (root == null)
			return;
		if (level == 1)
			System.out.println(root.getData());
		else if (level > 1) {
			printAtlevel(root.getLeftChild(), level - 1);
			printAtlevel(root.getRightChild(), level - 1);
		}
	}

	/*
	 * Given the root of a binary tree, return the level order traversal of its
	 * nodes' values. (i.e., from left to right, level by level).
	 * 
	 * 
	 * 
	 * Example 1:
	 * 
	 * 
	 * Input: root = [3,9,20,null,null,15,7] Output: [[3],[9,20],[15,7]] Example 2:
	 * 
	 * Input: root = [1] Output: [[1]] Example 3:
	 * 
	 * Input: root = [] Output: []
	 * 
	 * 
	 * Constraints:
	 * 
	 * The number of nodes in the tree is in the range [0, 2000]. -1000 <= Node.val
	 * <= 1000
	 */
	public List<List<Integer>> levelOrder(TreeNode root) {

		List<List<Integer>> levels = new ArrayList<List<Integer>>();

		Queue<TreeNode> q = new LinkedList<TreeNode>();

		q.add(root);

		while (!q.isEmpty()) {
			int size = q.size();
			List<Integer> level = new ArrayList<Integer>();
			for (int i = 0; i < size; i++) {
				TreeNode curr = q.remove();
				level.add(curr.getData());
				if (curr.getLeftChild() != null)
					q.add(curr.getLeftChild());
				if (curr.getRightChild() != null)
					q.add(curr.getRightChild());
			}
			levels.add(level);

		}

		return levels;
	}

	public void inorder(TreeNode root) {
		if (root != null) {
			inorder(root.getLeftChild());
			System.out.println(root.getData());
			inorder(root.getRightChild());
		}
	}

	public static void main(String[] args) {
		Tree T = new Tree();
		// T.add(12);
		// T.add(-4);
		// T.add(9);
		// T.inorder(T.getRoot());

		int[] preorder = { 3, 9, 20, 15, 7 };
		int[] inorder = { 9, 3, 15, 20, 7 };

		// T.printLevel(T.buildTree(preorder, inorder));
		T.root = T.buildTree(preorder, inorder);
		T.levelOrder(T.root).forEach(l -> System.out.println(l));
	}

}
