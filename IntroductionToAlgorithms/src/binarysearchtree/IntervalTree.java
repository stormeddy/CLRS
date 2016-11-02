package binarysearchtree;

public class IntervalTree {
	//区间树
	private  Node nil=new Node(new Interval(0,0));//哨兵
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
	
	 class Interval{
		 int low;
		 int high;
		 
		 public Interval(int low,int high) {
			// TODO Auto-generated constructor stub
			 this.low=low;
			 this.high=high;
		}
	 }
	
	 class Node{
		Interval interval;
		int max;
		Color color;
		Node left;
		Node right;
		Node parent;
		
		public Node(Interval interval) {
			this.interval=interval;
			this.max=interval.high;
			this.left=nil;
			this.right=nil;
//			this.parent=nil;
			this.color=Color.BLACK;
		}
		
		public Node(Interval interval,Node left,Node right) {
			this.interval=interval;
			this.max=interval.high;
			this.left=left;
			this.right=right;
			left.parent=this;
			right.parent=this;
		}
	}
	
	private Node root;
	public IntervalTree() {
		root=nil;
	}
	
	public IntervalTree(Node x) {
		root=x;
	}
	
	private boolean isOverlapped(Interval x,Interval y){
		//判断两个区间是否重叠
		if (x.low<=y.high && y.low<=x.high) {
			return true;
		}else {
			return false;
		}
	}
	
	public Node treeSearchNode(Node x,Interval i) {
		//返回与区间i重叠的区间
		while (x!=nil && !isOverlapped(x.interval, i)) {
			if (x.left!=nil&&x.left.max>=i.low) {
				x=x.left;
			}else {
				x=x.right;
			}
		}
		return x;
	}
	
	public Node treeSearchNodeExactly(Node x,Interval i) {
		while (x!=nil) {
			if (x.interval.low==i.low&&x.interval.high==i.high) {
				return x;
			}else if (i.low<x.interval.low) {
				x=x.left;
			}else {
				x=x.right;
			}
		}
		return x;		
	}
	
	public Node treeSearchLowestNode(Node x,Interval i) {
		if (x==nil) {
			return nil;
		}else if (x.left!=nil && x.left.max>=i.low) {
			Node y=treeSearchLowestNode(x.left, i);
			if (y!=nil) {
				return y;
//			}else if (isOverlapped(x.interval, i)) {
//				return x;				
			}else {
				return nil;
			}
		}else if (isOverlapped(x.interval, i)) {
			return x;
		}else {
			return treeSearchLowestNode(x.right, i);
		}
		
//		非递归版本
//		boolean flag=false;
//		Node min=new Node(new Interval(Integer.MAX_VALUE, Integer.MAX_VALUE));
//		while (x!=nil) {
//			if (isOverlapped(x.interval, i)) {
//				flag=true;//重叠
//			}else {
//				flag=false;//不重叠
//			}
//			if (flag&&x.interval.low<min.interval.low) {
//				min=x;
//			}
//			if (x.left!=nil&&x.left.max>=i.low) {
//				x=x.left;
//			}else {
//				x=x.right;
//			}
//		}
//		if (min.interval.low==Integer.MAX_VALUE) {
//			return nil;
//		}
//		return min;
		
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
	
	public void inOrderTreeWalk(IntervalTree T, Node x) {
		if (x!=T.nil) {
			inOrderTreeWalk(T,x.left);
			System.out.print("["+x.interval.low+","+x.interval.high+"]"+" "+x.color+" "+x.max);
			System.out.println();
			inOrderTreeWalk(T,x.right);
		}
	}
	
	public void treeSearchAllNode(Node x,Interval i){
		if (isOverlapped(x.interval, i)) {
			System.out.print("["+x.interval.low+","+x.interval.high+"]"+" ");
		}
		if (x.left!=nil && x.left.max>=i.low) {
			treeSearchAllNode(x.left, i);
		}
		if (x.right!=nil && x.right.max>=i.low) {
			treeSearchAllNode(x.right, i);
		}
	}
	
	private void leftRotate(IntervalTree T,Node x){
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
		
		y.max=x.max;
		x.max=Math.max(x.interval.high, Math.max(x.left.max, x.right.max));
	}
	
	private void rightRotate(IntervalTree T,Node x){
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
		
		y.max=x.max;
		x.max=Math.max(x.interval.high, Math.max(x.left.max, x.right.max));
	}
	
	public void IntervalInsert(IntervalTree T,Node z) {
		Node y=T.nil;
		Node x=T.root;
		while (x!=T.nil) {
			y=x;
			x.max=Math.max(x.max, z.max);
			if (z.interval.low<x.interval.low) {				
				x=x.left;
			}else {
				x=x.right;				
			}
		}
		z.parent=y;
		if (y==T.nil) {
			T.root=z;
		}else if (z.interval.low<y.interval.low) {
			y.left=z;
		}else {
			y.right=z;			
		}
		z.left=T.nil;
		z.right=T.nil;
		z.color=Color.RED;
		IntervalInsertFixup(T, z);
	}
	
	private void IntervalInsertFixup(IntervalTree T,Node z) {
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
	
	public void IntervalTransplant(IntervalTree T,Node u,Node v) {
		if (u.parent==T.nil) {
			T.root=v;
		}else if (u==u.parent.left) {
			u.parent.left=v;
		}else {
			u.parent.right=v;
		}
		v.parent=u.parent;
	}
	
	public void IntervalDelete(IntervalTree T,Node z){
		Node y=z;
		Node x;
		Node p;
		Color yColor=y.color;
		if (z.left==T.nil) {
			x=z.right;
			IntervalTransplant(T, z, z.right);
			p=x.parent;
		}else if (z.right==T.nil) {
			x=z.left;
			IntervalTransplant(T, z, z.left);
			p=x.parent;
		}else {
			y=treeMinimumNode(z.right);
			p=y;
			yColor=y.color;
			x=y.right;
			if (y.parent==z) {
				x.parent=y;
			}else {
				IntervalTransplant(T, y, y.right);
				y.right=z.right;
				y.right.parent=y;
			}
			IntervalTransplant(T, z, y);
			y.left=z.left;
			y.left.parent=y;
			y.color=z.color;
		}
		
		while (p!=T.nil) {
			//注意max()第一个参数一定是p.interval.high,不能是p.max
			p.max=Math.max(p.interval.high, Math.max(p.left.max, p.right.max));;
			p=p.parent;			
		}
		
		if (yColor==Color.BLACK) {
			IntervalDeleteFixup(T, x);
		}
	}
	
	public void IntervalDeleteFixup(IntervalTree T,Node x) {
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] low={16,8,25,5,15,17,26,0,6,19};
		int[] high={21,9,30,8,23,19,26,3,10,20};
		IntervalTree T=new IntervalTree();
		for (int i = 0; i < high.length; i++) {
			Node x=T.new  Node(T.new Interval(low[i],high[i]));
			T.IntervalInsert(T, x);
		}
		T.inOrderTreeWalk(T, T.root);
		Interval interval=T.new Interval(25,30);
		Node result1=T.treeSearchNode(T.root,interval);
		System.out.println(result1.interval.low+" "+result1.interval.high);
		
		Node result2=T.treeSearchLowestNode(T.root, interval);
		System.out.println(result2.interval.low+" "+result2.interval.high);
		
		T.treeSearchAllNode(T.root, interval);
		
		System.out.println();
		T.IntervalDelete(T, T.treeSearchNodeExactly(T.root, interval));
		T.inOrderTreeWalk(T, T.root);
		
	}

}
