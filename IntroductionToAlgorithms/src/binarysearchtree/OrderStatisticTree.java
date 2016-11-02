package binarysearchtree;

public class OrderStatisticTree {
	//顺序统计树
	private  Node nil=new Node(0);//哨兵
	{	//若不加此段，删除较多个节点时会出bug
		nil.left=nil;
		nil.right=nil;
	}

	public enum Color{
		BLACK,RED;
	}
	
	 class Node{
		int value;
		int size;
		Color color;
		Node left;
		Node right;
		Node parent;
		
		public Node(int value) {
			this.value=value;
			this.left=nil;
			this.right=nil;
//			this.parent=nil;
			this.size=0;
			this.color=Color.BLACK;
		}
		
		public Node(int value,Node left,Node right) {
			this.value=value;
			this.left=left;
			this.right=right;
			this.size=0;
			this.color=Color.BLACK;
			left.parent=this;
			right.parent=this;
			
		}
	}
	
	protected Node root;
	public OrderStatisticTree() {
		root=nil;
	}
	
	public OrderStatisticTree(Node x) {
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
	
	public void inOrderTreeWalk(OrderStatisticTree T, Node x) {
		if (x!=T.nil) {
			inOrderTreeWalk(T,x.left);
			System.out.println(x.value+" "+x.color+" "+x.size);
			inOrderTreeWalk(T,x.right);
		}
	}
	
	private void leftRotate(OrderStatisticTree T,Node x){
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
		
		y.size=x.size;
		x.size=x.left.size+x.right.size+1;
	}
	
	private void rightRotate(OrderStatisticTree T,Node x){
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
		
		y.size=x.size;
		x.size=x.left.size+x.right.size+1;
	}
	
	public void OrderStatisticInsert(OrderStatisticTree T,Node z) {
		Node y=T.nil;
		Node x=T.root;
		while (x!=T.nil) {
			y=x;
			y.size++;
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
		z.size=1;
		OrderStatisticInsertFixup(T, z);
	}
	
	private void OrderStatisticInsertFixup(OrderStatisticTree T,Node z) {
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
	
	public void OrderStatisticTransplant(OrderStatisticTree T,Node u,Node v) {
		if (u.parent==T.nil) {
			T.root=v;
		}else if (u==u.parent.left) {
			u.parent.left=v;
		}else {
			u.parent.right=v;
		}
		v.parent=u.parent;
	}
	
	public void OrderStatisticDelete(OrderStatisticTree T,Node z){
		Node y=z;
		Node x;
		Node p;//case 1,2:记录z在原树中的位置;case 3:记录y的父节点位置.并将root到p路径上的节点size减一
		Color yColor=y.color;
		if (z.left==T.nil) {
			x=z.right;
			OrderStatisticTransplant(T, z, z.right);
			p=x.parent;
		}else if (z.right==T.nil) {
			x=z.left;
			OrderStatisticTransplant(T, z, z.left);
			p=x.parent;
		}else {
			y=treeMinimumNode(z.right);
			p=y.parent;
			yColor=y.color;
			x=y.right;
			if (y.parent==z) {
				x.parent=y;
			}else {
				OrderStatisticTransplant(T, y, y.right);
				y.right=z.right;
				y.right.parent=y;
			}
			OrderStatisticTransplant(T, z, y);
			y.left=z.left;
			y.left.parent=y;
			y.color=z.color;
			
			y.size=z.size;//y.size此时不是最终值,只是以z为根的原子树的size
		}
		
		while (p!=T.nil) {
			p.size--;
			p=p.parent;
		}
		if (yColor==Color.BLACK) {
			OrderStatisticDeleteFixup(T, x);
		}
	}
	
	public void OrderStatisticDeleteFixup(OrderStatisticTree T,Node x) {
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
			x.color=Color.BLACK;
		}
	}
	
	public Node OSSelect(Node x,int i) {
		//查找具有给定秩的元素
		int r=x.left.size+1;
		if (i==r) {
			return x;
		}else if (i<r) {
			return OSSelect(x.left, i);
		}else {
			return OSSelect(x.right, i-r);
		}
		
		//非递归版本
//		while (x!=nil) {
//			int r=x.left.size+1;
//			if (i==r) {
//				return x;
//			}else if (i<r) {
//				x=x.left;
//			}else {
//				x=x.right;
//				i=i-r;
//			}
//		}
//		return nil;
	}
	
	public int OSRank(OrderStatisticTree T,Node x){
		//获取树中指定节点的秩
		int r=x.left.size+1;
		Node y=x;
		while (y!=T.root) {
			if (y==y.parent.right) {
				r=r+y.parent.left.size+1;
			}
			y=y.parent;
		}
		return r;
	}
	
	public int OSKeyRank(Node root,int k) {
		//获取树中指定关键字的秩
		int r=root.left.size+1;
		if (k==root.value) {
			return r;
		}else if (k<root.value) {
			return OSKeyRank(root.left, k);
		}else {
			return OSKeyRank(root.right, k)+r;
		}		
	}
	
	public Node OSSuccessor(Node root,int k,int i) {
		//获取树中指定关键字的第i个后继
		int r=OSKeyRank(root, k);
		return OSSelect(root, r+i);
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr=new int[]{15,6,18,3,7,17,20,2,4,13,9};
		int sum=0;//统计数组中逆序对的个数
		OrderStatisticTree T=new OrderStatisticTree();
		for (int i = 0; i < arr.length; i++) {
			Node x=T.new Node(arr[i]);
			T.OrderStatisticInsert(T, x);
			sum=sum+i+1-T.OSRank(T, x);
		}
		System.out.println(sum);
//		T.inOrderTreeWalk(T, T.root);
		System.out.println();
		for (int i = 0; i < arr.length; i++) {
			T.OrderStatisticDelete(T, T.treeSearchNode(T.root, arr[i]));
		}
		T.inOrderTreeWalk(T, T.root);
//		System.out.println(T.OSSelect(T.root, 5).value);
//		System.out.println(T.OSRank(T, T.treeSearchNode(T.root, 7)));
//		System.out.println(T.OSKeyRank(T.root, 7));
//		System.out.println(T.OSSuccessor(T.root,7, 3).value);
	}
}
