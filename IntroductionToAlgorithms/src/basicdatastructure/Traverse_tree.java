package basicdatastructure;

import java.util.Stack;

public class Traverse_tree {

	public static void main(String[] args) {
		Node root = new Node(1);
		Node node2 = new Node(2);
		Node node3 = new Node(3);
		Node node4 = new Node(4);
		Node node5 = new Node(5);
		Node node6 = new Node(6);
		Node node7 = new Node(7);
		Node node8 = new Node(8);
		Node node9 = new Node(9);
		Node node10 = new Node(10);
		Node node11 = new Node(11);
		root.left = node2;
		root.right = node3;
		node2.left = node4;
		node2.right = node5;
		node3.left = node6;
		node3.right = node7;
		node5.left = node8;
		node5.right = node9;
		node7.left = node10;
		node7.right = node11;
		
		node2.parent = root;
		node3.parent = root;
		node4.parent = node2;
		node5.parent = node2;
		node6.parent = node3;
		node7.parent = node3;
		node8.parent = node5;
		node9.parent = node5;
		node10.parent = node7;
		node11.parent = node7;
		BinaryTree tree = new BinaryTree(root);
//		nonrecursive_inorder_traverse(tree);
		nonrecursive_preorder_traverse(tree);
//		nonrecursive_postorder_traverse(tree);
	}
	
	//非递归版本的中序遍历
	/*
	 * 1.设置指针x，初始指向root
	 * 2.一路向左遍历，并将遍历的点放入栈，直到空为止。
	 * 3.从栈中拿出一个节点y，此节点为x的父亲，输出y，并需要将x父亲的右边节点输出，则x=y.right.
	 * 4.继续一路向左遍历，并将遍历的点放入栈中，直到空为止。
	 * 5.从栈中拿出一个节点y，此节点为x的父亲，输出y，并需要将x父亲的右边节点输出，则x=y.right.
	 * 重复2,3 直到x为空并且栈为空
	 */
	public static void nonrecursive_inorder_traverse(BinaryTree tree){
		Node root = tree.root;
		Stack<Node> stack = new Stack<Node>();
		Node x = root;
		//开始先一路向左遍历
		while(x!=null){
			stack.push(x);
			x = x.left;
		}
		//当栈为空，但是x不为空时，还是要继续输出，因为如果访问到根的右边时，栈为空，但是还是要继续运行
		while(!stack.isEmpty()||x!=null){	
			while(x!=null){
				stack.push(x);
				x = x.left;
			}
			x = stack.pop();
			System.out.println(x.value);
			x = x.right;
		}
		
		/*inorder_walk(root)
		{
			stack = {}
			x = root
			while(x!=NIL)	//首先一路向左将最左边一个链全部放入栈
				stack.push(x);
				x = left[x]
			while(stack!=Φ)	
				x = stack.pop();	//每次pop一个元素，打印，并看看有没有右子树，如果有则右一次，一路向左
				print(x);
				if(right[x]!=NIL)
					x = right[x];
					while(x!=NIL)
						stack.push(x)
						x = left[x];
		}*/
	/*	while (x!=null) {
			stack.push(x);
			x=x.left;
		}
		while (!stack.isEmpty()) {
			x=stack.pop();
			System.out.println(x.value);
			if (x.right!=null) {
				x=x.right;
				while (x!=null) {
					stack.push(x);
					x=x.left;
				}
			}
		}*/
	}
	
	//非递归版本的前序遍历
	public static void nonrecursive_preorder_traverse(BinaryTree tree){
		Node root = tree.root;
		Stack<Node> stack = new Stack<Node>();
		stack.push(root);
		while(!stack.isEmpty()){
			Node u = stack.pop();
			System.out.println(u.value+" "+u.key);
			if(u.right!=null) stack.push(u.right);
			if(u.left!=null) stack.push(u.left);
		}
	}
	/*
	 * 需要有一个数组记录每个元素是否访问过，如果访问过就不要访问了
	 */
	//非递归版本的后序遍历
	public static void nonrecursive_postorder_traverse(BinaryTree tree){
		Node root = tree.root;
		Stack<Node> stack = new Stack<Node>();
		Node x = root;
		boolean visited[] = new boolean[tree.size];
		for(int i=0;i<visited.length;i++) visited[i] = false;
		//开始先一路向左遍历
		while(x!=null){
			stack.push(x);
			x = x.left;
		}
		//当栈为空，但是x不为空时，还是要继续输出，因为如果访问到根的右边时，栈为空，但是还是要继续运行
		while(!stack.isEmpty()||x!=null){	
			while(x!=null){
				stack.push(x);
				visited[x.key-1] = true; 
				x = x.left;
			}
			x = stack.pop();
			if(x.right==null) {
				System.out.println(x.value);
				x = null;
			}
			else {	
				if(!visited[x.right.key-1]){//如果x的右边还有节点，则先将x放到栈，再遍历栈
					stack.push(x);
					x = x.right;
				}
				else{	//如果右边的节点访问过了，则说明已经访问过左右子树，只剩x节点了
					System.out.println(x.value);
					x = null;
				}
			}
		}
	}
}