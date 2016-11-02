package binarysearchtree;

public class MinGapTree {
	//最小间隔树
	private  Node nil=new Node(0);//哨兵
	{	
		nil.left=nil;
		nil.right=nil;
	}
//	{
//		nil.max=Integer.MIN_VALUE;
//		nil.min=Integer.MAX_VALUE;
//	}
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
		int min;//以此节点为根的树中最小的数
		int max;//以此节点为根的树中最大的数
		int gap;//以此节点为根的树中最接近的数之间的差值
		Color color;
		Node left;
		Node right;
		Node parent;
		
		public Node(int value) {
			this.value=value;
			this.left=nil;
			this.right=nil;
			this.min=value;
			this.max=value;
			this.gap=Integer.MAX_VALUE;
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
	public MinGapTree() {
		root=nil;
	}
	
	public MinGapTree(Node x) {
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
	
	public void inOrderTreeWalk(MinGapTree T, Node x) {
		if (x!=T.nil) {
			inOrderTreeWalk(T,x.left);
			System.out.print(x.value+" "+x.color+" "+x.min+" "+x.max+" "+x.gap);
			System.out.println();
			inOrderTreeWalk(T,x.right);
		}
	}
	
	private void leftRotate(MinGapTree T,Node x){
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
		
		Maintain(T, x);
//		y.max=x.max;
//		y.min=x.min;
//		x.max=Math.max(x.value, x.right.max);
//		x.min=Math.min(x.value, x.left.min);
	}
	
	private void rightRotate(MinGapTree T,Node x){
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
		
		Maintain(T, x);		
//		y.max=x.max;
//		y.min=x.min;
//		x.max=Math.max(x.value, x.right.max);
//		x.min=Math.min(x.value, x.left.min);
	}
	
	private void Maintain(MinGapTree T,Node x){
		while (x!=T.nil) {
			x.min=(x.left!=T.nil)?x.left.min:x.value;
			x.max=(x.right!=T.nil)?x.right.max:x.value;			
			//维护gap信息
			int a=(x.left!=T.nil)?x.left.gap:Integer.MAX_VALUE;
			int b=(x.right!=T.nil)?x.right.gap:Integer.MAX_VALUE;
			int c=(x.left!=T.nil)?(x.value-x.left.max):Integer.MAX_VALUE;
			int d=(x.right!=T.nil)?(x.right.min-x.value):Integer.MAX_VALUE;
			x.gap=Min(a, b, c, d);
			x=x.parent;
		}
	}
	
	private int Min(int a,int b,int c,int d) {
		 a=a<b?a:b;
		 c=c<d?c:d;
		 return a<c?a:c;
	}
	
	public void MinGapInsert(MinGapTree T,Node z) {
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
		MinGapInsertFixup(T, z);
		Maintain(T, z);
	}
	
	private void MinGapInsertFixup(MinGapTree T,Node z) {
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
	
	private void MinGapTransplant(MinGapTree T,Node u,Node v) {
		if (u.parent==T.nil) {
			T.root=v;
		}else if (u==u.parent.left) {
			u.parent.left=v;
		}else {
			u.parent.right=v;
		}
		v.parent=u.parent;
	}
	
	public void MinGapDelete(MinGapTree T,Node z){
		Node y=z;
		Node x;
		Node p;//参考OrderStatisticTree.java
		Color yColor=y.color;
		if (z.left==T.nil) {
			x=z.right;
			MinGapTransplant(T, z, z.right);
			p=x.parent;
		}else if (z.right==T.nil) {
			x=z.left;
			MinGapTransplant(T, z, z.left);
			p=x.parent;
		}else {
			y=treeMinimumNode(z.right);
			p=y;//此处与OrderStatisticTree.java不同,但y的属性都是要修改的
			yColor=y.color;
			x=y.right;
			if (y.parent==z) {
				x.parent=y;
			}else {
				MinGapTransplant(T, y, y.right);
				y.right=z.right;
				y.right.parent=y;
			}
			MinGapTransplant(T, z, y);
			y.left=z.left;
			y.left.parent=y;
			y.color=z.color;
		}
		
		while (p!=T.nil) {
			Maintain(T, p);
			p=p.parent;
		}
		if (yColor==Color.BLACK) {
			MinGapDeleteFixup(T, x);
		}
	}
	
	public void MinGapDeleteFixup(MinGapTree T,Node x) {
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
			}
			
			else {
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
	
	public int MinGap(){
		return root.gap;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr=new int[]{49,21,57,11,32,54,66,10,17,42,40};
		MinGapTree T=new MinGapTree();
		for (int i = 0; i < arr.length; i++) {
			Node x=T.new Node(arr[i]);
			T.MinGapInsert(T, x);
		}
		T.inOrderTreeWalk(T, T.root);
		System.out.println();
		T.MinGapDelete(T, T.treeSearchNode(T.root, 40));
		T.inOrderTreeWalk(T, T.root);
		System.out.println();
		System.out.println(T.MinGap());
	}

}
