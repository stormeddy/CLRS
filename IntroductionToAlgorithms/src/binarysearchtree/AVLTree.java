package binarysearchtree;

public class AVLTree {
	
	private  Node nil=new Node(0);//哨兵
//	{
//		nil.left=nil;
//		nil.parent=nil;
//		nil.right=nil;
//		
//	}
	{	
		nil.left=nil;
		nil.right=nil;
	}
	class Node{
		int value;
		int height;
		Node left;
		Node right;
		Node parent;
		
		public Node(int value) {
			this.value=value;
			this.left=nil;
			this.right=nil;
			this.height=0;
			this.parent=nil;
		}
		
		public Node(int value,Node left,Node right) {
			this.value=value;
			this.left=left;
			this.right=right;
			this.height=0;
			left.parent=this;
			right.parent=this;
		}
	}
	
	private Node root;
	public AVLTree() {
		root=nil;
	}
	
	public AVLTree(Node x) {
		root=x;
	}
	
	public void inOrderTreeWalk(AVLTree T, Node x) {
		if (x!=T.nil) {
			inOrderTreeWalk(T,x.left);
			System.out.println(x.value+" "+x.height);
			inOrderTreeWalk(T,x.right);
		}
	}
	
	private void leftRotate(AVLTree T,Node x){
		Node y=x.right;//y不能为T.nil
		if (y==T.nil) {
			return;
		}
		x.right=y.left;
		if (y.left!=T.nil) {
			y.left.parent=x;
		}
		y.parent=x.parent;
		if (x.parent==T.nil) {
			T.root=y;
		}else if (x==x.parent.left) {
			x.parent.left=y;
		}else {
			x.parent.right=y;
		}
		y.left=x;
		x.parent=y;
		//更新高度，注意到x和y的原子节点a,b,c高度是不变的
		x.height=Math.max(x.left.height, x.right.height)+1;
		y.height=Math.max(y.left.height, y.right.height)+1;
	}
	
	private void rightRotate(AVLTree T,Node x){
		Node y=x.left;//y不能为T.nil
		if (y==T.nil) {
			return;
		}
		x.left=y.right;
		if (y.right!=T.nil) {
			y.right.parent=x;
		}
		y.parent=x.parent;
		if (x.parent==T.nil) {
			T.root=y;
		}else if (x==x.parent.right) {
			x.parent.right=y;
		}else {
			x.parent.left=y;
		}
		y.right=x;
		x.parent=y;
		//更新高度，注意到x和y的原子节点a,b,c高度是不变的
		x.height=Math.max(x.left.height, x.right.height)+1;
		y.height=Math.max(y.left.height, y.right.height)+1;
	}
	
	public Node AVLTreeInsert(AVLTree T,Node x,Node z){
		//将z添加到以x为根的子树中
		if (x==nil) {
			if (x==T.root) {
				//以x为根的子树为空
				root=z;
			}
			z.height=0;
			return z;
		}
		Node y;
		if (z.value<x.value) {
			y=AVLTreeInsert(T,x.left, z);
			x.left=y;
		}else {
			y=AVLTreeInsert(T,x.right, z);
			x.right=y;
		}
		y.parent=x;
		x.height=y.height+1;
		Balance(T,x);
		return x;		
	}
	
	private void transplant(AVLTree T,Node u,Node v){
		//用v替换u
		if (u.parent==nil) {
			T.root=v;
		}else if (u==u.parent.left) {
			u.parent.left=v;
		}else {
			u.parent.right=v;
		}
		if (v!=nil) {
			v.parent=u.parent;
		}
	}
	
	public Node treeMinimumNode(Node x) {
		while (x.left!=nil) {
			x=x.left;
		}
		return x;
	}
	
	public Node treeSearchNode(Node x,int k) {
		while (x!=nil&&k!=x.value) {
			if (k<x.value) {
				x=x.left;
			}else {
				x=x.right;
			}
		}
		return x;
	}
	
	public void AVLTreeDelete(AVLTree T,Node z) {
		if (z==nil) {
			return;
		}
		if (z.left==nil) {
			Node p=z.right;
			transplant(T, z, z.right);
			
			while (p!=nil) {
				Balance(T, p);
				p=p.parent;
			}
			
		}else if (z.right==nil) {
			Node p=z.right;
			transplant(T, z, z.left);
			while (p!=nil) {
				Balance(T, p);
				p=p.parent;
			}
		}else {
			//y为z的后继
			Node y=treeMinimumNode(z.right);
			Node p;
			if (y.parent!=z) {
				p=y.parent;
			}else {
				p=y;
			}
			if (y.parent!=z) {
				transplant(T, y, y.right);
				y.right=z.right;
				y.right.parent=y;
			}
			transplant(T, z, y);
			y.left=z.left;
			y.left.parent=y;
			y.height=Math.max(y.left.height, y.right.height)+1;
			while (p!=nil) {
				Balance(T, p);
				p=p.parent;
			}
		}
	}
	
	public void Balance(AVLTree T,Node x){
		if (Math.abs(x.left.height-x.right.height)<=1) {
			return;
		}
		//在左旋与右旋中更新高度
		if (x.left.height<x.right.height) {
			Node y=x.right;
			if (y.left.height<y.right.height) {
				leftRotate(T, x);
//				x.height--;
			}else {
				rightRotate(T, y);
				leftRotate(T, x);
//				x.height=x.height-2;
//				y.height--;
//				y.left.height++;
			}
		}else {
			Node y=x.left;
			if (y.right.height<y.left.height) {
				rightRotate(T, x);
//				x.height--;
			}else {
				leftRotate(T, y);
				rightRotate(T, x);
//				x.height=x.height-2;
//				y.height--;
//				y.right.height++;
			}
		}
	}
	
	
	public static void main(String[] args) {
		int[] arr=new int[]{15,6,18,3,7,17,20,2,4,13,9};
		AVLTree tree=new AVLTree();
		for (int i = 0; i < arr.length; i++) {
			Node x=tree.new Node(arr[i]);
			tree.AVLTreeInsert(tree,tree.root, x);
		}
		tree.inOrderTreeWalk(tree, tree.root);
		System.out.println();
//		for (int i = 0; i < arr.length; i++) {
			tree.AVLTreeDelete(tree, tree.treeSearchNode(tree.root, 3));
		tree.inOrderTreeWalk(tree, tree.root);
	}
}
