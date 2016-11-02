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
	
	//�ǵݹ�汾���������
	/*
	 * 1.����ָ��x����ʼָ��root
	 * 2.һ·������������������ĵ����ջ��ֱ����Ϊֹ��
	 * 3.��ջ���ó�һ���ڵ�y���˽ڵ�Ϊx�ĸ��ף����y������Ҫ��x���׵��ұ߽ڵ��������x=y.right.
	 * 4.����һ·������������������ĵ����ջ�У�ֱ����Ϊֹ��
	 * 5.��ջ���ó�һ���ڵ�y���˽ڵ�Ϊx�ĸ��ף����y������Ҫ��x���׵��ұ߽ڵ��������x=y.right.
	 * �ظ�2,3 ֱ��xΪ�ղ���ջΪ��
	 */
	public static void nonrecursive_inorder_traverse(BinaryTree tree){
		Node root = tree.root;
		Stack<Node> stack = new Stack<Node>();
		Node x = root;
		//��ʼ��һ·�������
		while(x!=null){
			stack.push(x);
			x = x.left;
		}
		//��ջΪ�գ�����x��Ϊ��ʱ������Ҫ�����������Ϊ������ʵ������ұ�ʱ��ջΪ�գ����ǻ���Ҫ��������
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
			while(x!=NIL)	//����һ·���������һ����ȫ������ջ
				stack.push(x);
				x = left[x]
			while(stack!=��)	
				x = stack.pop();	//ÿ��popһ��Ԫ�أ���ӡ����������û�������������������һ�Σ�һ·����
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
	
	//�ǵݹ�汾��ǰ�����
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
	 * ��Ҫ��һ�������¼ÿ��Ԫ���Ƿ���ʹ���������ʹ��Ͳ�Ҫ������
	 */
	//�ǵݹ�汾�ĺ������
	public static void nonrecursive_postorder_traverse(BinaryTree tree){
		Node root = tree.root;
		Stack<Node> stack = new Stack<Node>();
		Node x = root;
		boolean visited[] = new boolean[tree.size];
		for(int i=0;i<visited.length;i++) visited[i] = false;
		//��ʼ��һ·�������
		while(x!=null){
			stack.push(x);
			x = x.left;
		}
		//��ջΪ�գ�����x��Ϊ��ʱ������Ҫ�����������Ϊ������ʵ������ұ�ʱ��ջΪ�գ����ǻ���Ҫ��������
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
				if(!visited[x.right.key-1]){//���x���ұ߻��нڵ㣬���Ƚ�x�ŵ�ջ���ٱ���ջ
					stack.push(x);
					x = x.right;
				}
				else{	//����ұߵĽڵ���ʹ��ˣ���˵���Ѿ����ʹ�����������ֻʣx�ڵ���
					System.out.println(x.value);
					x = null;
				}
			}
		}
	}
}