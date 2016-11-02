package binarysearchtree;

public class RBTree {

	private  Node nil=new Node(0);//哨兵
	{	
		nil.left=nil;
		nil.right=nil;
	}
//	private static Node parentOfNil=new Node(-1);
	
//	{
//		nil.left=nil;
//		nil.right=nil;
//		nil.parent=nil;
//	}
////		parentOfNil.left=nil;
////		parentOfNil.right=nil;
////		parentOfNil.parent=parentOfNil;
//	}
	public enum Color{
		BLACK,RED;
	}
	
	 class Node{
		int value;
		Color color;
		Node left;
		Node right;
		Node parent;
		
		public Node(int value) {
			this.value=value;
			this.left=nil;
			this.right=nil;
//			this.parent=nil;
			this.color=Color.BLACK;
		}
		
		public Node(int value,Node left,Node right) {
			this.value=value;
			this.left=left;
			this.right=right;
			left.parent=this;
			right.parent=this;
		}
	}
	
	private Node root;
	public RBTree() {
		root=nil;
	}
	
	public RBTree(Node x) {
		root=x;
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
	
	public Node treeMinimumNode(Node x) {
		while (x.left!=nil) {
			x=x.left;
		}
		return x;
	}
	
	public Node treeMaximumNode(Node x) {
		while (x.right!=nil) {
			x=x.right;
		}
		return x;
	}
	
	public Node treeSuccessorNode (Node x) {
		if (x.right!=nil) {
			return treeMinimumNode(x.right);
		}
		Node y=x.parent;
		while (y!=nil&&x==y.right) {
			x=y;
			y=y.parent;
		}
		return y;
	}
	
	public Node treePredecessorNode(Node x) {
		if (x.left!=nil) {
			return treeMaximumNode(x.left);
		}
		Node y=x.parent;
		while (y!=nil&&x==y.left) {
			x=y;
			y=y.parent;
		}
		return y;
	}
	
	public void inOrderTreeWalk(RBTree T, Node x) {
		if (x!=T.nil) {
			inOrderTreeWalk(T,x.left);
			System.out.print(x.value+" "+x.color+" ");
			inOrderTreeWalk(T,x.right);
		}
	}
	
	private void leftRotate(RBTree T,Node x){
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
	}
	
	private void rightRotate(RBTree T,Node x){
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
	}
	
	public void RBInsert(RBTree T,Node z) {
		Node y=T.nil;
		Node x=T.root;
		while (x!=T.nil) {
			y=x;
			if (z.value<x.value) {
				x=x.left;
			}else {
				x=x.right;
			}
		}
		z.parent=y;
		if (y==T.nil) {
			T.root=z;
		}else if (z.value<y.value) {
			y.left=z;
		}else {
			y.right=z;
		}
		z.left=T.nil;
		z.right=T.nil;
		z.color=Color.RED;
		RBInsertFixup(T, z);
	}
	
	private void RBInsertFixup(RBTree T,Node z) {
		while (z.parent.color==Color.RED) {
			if (z.parent==z.parent.parent.left) {
				Node y=z.parent.parent.right;
				if (y.color==Color.RED) {
					z.parent.color=Color.BLACK;
					y.color=Color.BLACK;
					z.parent.parent.color=Color.RED;
					z=z.parent.parent;
				}else {
					if (z==z.parent.right) {
						z=z.parent;
						leftRotate(T, z);
					}
					z.parent.color=Color.BLACK;
					z.parent.parent.color=Color.RED;
//					T.nil.color=Color.BLACK;//T.nil可能被改动,确保为黑色(此处看错书上缩进,不需要)
					rightRotate(T, z.parent.parent);
				}
			}else if (z.parent==z.parent.parent.right) {
				//与上面情况对称
				Node y=z.parent.parent.left;
				if (y.color==Color.RED) {
					z.parent.color=Color.BLACK;
					y.color=Color.BLACK;
					z.parent.parent.color=Color.RED;
					z=z.parent.parent;
				}else { 
					if (z==z.parent.left) {
						z=z.parent;
						rightRotate(T, z);
					}
					z.parent.color=Color.BLACK;
					z.parent.parent.color=Color.RED;
//					T.nil.color=Color.BLACK;//T.nil可能被改动,确保为黑色(此处看错书上缩进,不需要)
					leftRotate(T, z.parent.parent);
				}
			}			
		}
		T.root.color=Color.BLACK;
	}
	
	public void RBTransplant(RBTree T,Node u,Node v) {
		if (u.parent==T.nil) {
			T.root=v;
		}else if (u==u.parent.left) {
			u.parent.left=v;
		}else {
			u.parent.right=v;
		}
		v.parent=u.parent;
	}
	
	public void RBDelete(RBTree T,Node z){
		Node y=z;
		Node x;
		Color yColor=y.color;
		if (z.left==T.nil) {
			x=z.right;
			RBTransplant(T, z, z.right);
		}else if (z.right==T.nil) {
			x=z.left;
			RBTransplant(T, z, z.left);
		}else {
			y=treeMinimumNode(z.right);
			yColor=y.color;
			x=y.right;
			if (y.parent==z) {
				x.parent=y;
			}else {
				RBTransplant(T, y, y.right);
				y.right=z.right;
				y.right.parent=y;
			}
			RBTransplant(T, z, y);
			y.left=z.left;
			y.left.parent=y;
			y.color=z.color;
		}
		if (yColor==Color.BLACK) {
			RBDeleteFixup(T, x);
		}
	}
	
	public void RBDeleteFixup(RBTree T,Node x) {
		while (x!=T.root&&x.color==Color.BLACK) {
			if (x==x.parent.left) {
				Node w=x.parent.right;
				if (w.color==Color.RED) {
					w.color=Color.BLACK;
					x.parent.color=Color.RED;
					leftRotate(T, x.parent);
					w=x.parent.right;
				}
				if (w.left.color==Color.BLACK&&w.right.color==Color.BLACK) {
					w.color=Color.RED;
					x=x.parent;
				}else {
					if (w.right.color==Color.BLACK) {
						w.right.color=Color.BLACK;
						w.color=Color.RED;
						rightRotate(T, w);
						w=x.parent.right;
					}
					w.color=x.parent.color;
					x.parent.color=Color.BLACK;
					w.right.color=Color.BLACK;
					leftRotate(T, x.parent);
					x=T.root;
				}
			}else if (x==x.parent.right){
				Node w=x.parent.left;
				if (w.color==Color.RED) {
					w.color=Color.BLACK;
					x.parent.color=Color.RED;
					rightRotate(T, x.parent);
					w=x.parent.left;
				}
				if (w.right.color==Color.BLACK&&w.left.color==Color.BLACK) {
					w.color=Color.RED;
					x=x.parent;
				}else {
					if (w.left.color==Color.BLACK) {
						w.right.color=Color.BLACK;
						w.color=Color.RED;
						leftRotate(T, w);
						w=x.parent.left;
					}
					w.color=x.parent.color;
					x.parent.color=Color.BLACK;
					w.left.color=Color.BLACK;
					rightRotate(T, x.parent);
					x=T.root;
				}
			}			
		}
		x.color=Color.BLACK;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(RBTree.nil.color);
//		int[] arr=new int[]{15,6,18,3,7,17,20,2,4,13,9};
		int[] arr=new int[]{41,38,31,12,19,8};
		RBTree tree=new RBTree();
		for (int i = 0; i < arr.length; i++) {
			Node x=tree.new Node(arr[i]);
			tree.RBInsert(tree, x);
		}
		int[] brr={8,12,19,31,38,41};
		for (int i = 0; i < brr.length; i++) {
			tree.RBDelete(tree, tree.treeSearchNode(tree.root, brr[i]));
			tree.inOrderTreeWalk(tree,tree.root);
			System.out.println();
		}
		System.out.println("end");
//		tree.inOrderTreeWalk(tree,tree.root);
//		System.out.println(tree.treeSearchNode(tree.root, 9).value);
//		tree.RBDelete(tree, tree.treeSearchNode(tree.root, 9));
//		tree.inOrderTreeWalk(tree,tree.root);
	}

}
