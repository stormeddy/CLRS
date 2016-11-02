package basicdatastructure;

import java.util.Stack;

public class BinaryTree {
	//±éÀú¶þ²æÊ÷
	Node root;
	int size;
	public BinaryTree(Node root) {
		this.root = root;
		size = 0;
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		while(!stack.isEmpty()){
			Node u = stack.pop();
			size++;
			u.key = size;
			if(u.right!=null) stack.push(u.right);
			if(u.left!=null) stack.push(u.left);
		}
	}

	public BinaryTree() {
	}
}

class Node{
	int key;
	Node left;
	Node right;
	Node parent;
	int value;
	public Node(Node left, Node right, Node parent, int value) {
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.value = value;
	}
	public Node(Node left, Node right, int value) {
		this.left = left;
		this.right = right;
		this.value = value;
	}
	public Node(int value) {
		this.value = value;
	}
	
}