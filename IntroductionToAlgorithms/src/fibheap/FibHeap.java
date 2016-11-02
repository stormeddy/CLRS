package fibheap;

public class FibHeap {

	static class Node{
		Node parent;
		Node child;
		Node left;
		Node right;
		int degree;
		boolean mark;
		boolean isHandled;//指示consolidate()中每一个节点是否已经遍历过
		int key;
		public Node(int key){
			parent=null;
			child=null;
			left=this;
			right=this;
			degree=0;
			mark=false;
			isHandled=false;
			this.key=key;
		}
	}
	
	private int n;
	private Node min;
	
	public FibHeap makeFibHeap(){
		FibHeap fib=new FibHeap();
		fib.n=0;
		fib.min=null;
		return fib;
	}
	
	public void fibHeapInsert(FibHeap H,Node x){
		x.degree=0;
		x.parent=null;
		x.child=null;
		x.mark=false;
		if (H.min==null) {
			H.min=x;
		}else {
			insertNodeToRootList(H, x);
			if (x.key<H.min.key) {
				H.min=x;
			}
		}
		H.n++;
	}
	
	private void insertNodeToRootList(FibHeap H,Node x){
		if (x!=null) {
			x.parent=null;
			x.right=H.min.right;
			x.left=H.min;
			H.min.right.left=x;
			H.min.right=x;
		}		
	}
	
	public FibHeap fibHeapUnion(FibHeap H1,FibHeap H2){
		//H1和H2被“销毁”
		FibHeap H=makeFibHeap();
		if (H1.min!=null&&H2.min!=null) {
			H.min=H1.min;
			insertListToRootList(H, H2.min);
			if (H2.min.key<H1.min.key) {
				H.min=H2.min;
			}
		}else if (H1.min!=null) {
			H.min=H1.min;
		}else if (H2.min!=null) {
			H.min=H2.min;
		}		
		H.n=H1.n+H2.n;
		return H;
	}
	
	private void insertListToRootList(FibHeap H,Node x){
		//要求x!=null
		Node t=H.min.right;
		Node s=x.left;
		H.min.right=x;
		s.right=t;
		t.left=s;
		x.left=H.min;
		if (x.key<H.min.key) {
			H.min=x;
		}
	}
	
	public Node fibHeapExtractMin(FibHeap H){
		//不改变被移除节点z的任何属性
		Node z=H.min;
		if (z!=null) {
			Node childOfMin=z.child;
			if (childOfMin!=null) {
				while (childOfMin!=null&&childOfMin.parent==z) {
					childOfMin.parent=null;
					childOfMin=childOfMin.right;
				}
				//z.child=null;
				insertListToRootList(H, childOfMin);
			}
			removeNodeFromRootList(H, z);
			if (z==z.right) {
				H.min=null;
			}else {
				H.min=z.right;
				consolidate(H);
			}
			H.n--;
		}
		return z;
	}
	
	private void removeNodeFromRootList(FibHeap H,Node z){
		if (z.right==z) {
			H.min=null;
		}
		else {
			z.right.left=z.left;
			z.left.right=z.right;
		}
		
	}
	
	private void consolidate(FibHeap H){
		int Dn=(int) Math.floor(Math.log(H.n)/Math.log(2));
		Node[] A=new Node[Dn+1];
		for (int i = 0; i <= Dn; i++) {
			A[i]=null;
		}
		Node w=H.min;
		Node curNode=w;
		do {
			curNode.isHandled=false;
			curNode=curNode.right;
		} while (curNode!=w);
		
		while (!w.isHandled) {
			Node x=w;
			w.isHandled=true;
			w=w.right;
			int d=x.degree;
			while (A[d]!=null) {
				Node y=A[d];
				if (x.key>y.key) {
					Node temp=x;
					x=y;
					y=temp;
				}
				fibHeapLink(H, y, x);
				A[d]=null;
				d=d+1;
			}
			A[d]=x;
		}
		
		H.min=null;
		for (int i = 0; i <= Dn; i++) {
			if (A[i]!=null) {
				if (H.min==null) {
					//注意此处要求H的根链表只包含A[i]!!!
					H.min=A[i];
					A[i].right=A[i];
					A[i].left=A[i];
				}else {
					insertNodeToRootList(H, A[i]);
					if (A[i].key<H.min.key) {
						H.min=A[i];
					}
				}
			}
		}
	}
	
/*	private void swap(Node x,Node y){
		Node z=x;
		x=y;
		y=z;
	}*/
	
	private void fibHeapLink(FibHeap H,Node y,Node x){
		removeNodeFromRootList(H, y);
		addChildToParent(y, x);
		y.mark=false;
	}
	
	private void addChildToParent(Node y,Node x){
		//使y成为x一个孩子
		if (x.child==null) {
			x.child=y;
			y.left=y;
			y.right=y;
		}else {
			Node z=x.child;
			y.right=z.right;
			y.left=z;
			z.right.left=y;
			z.right=y;
		}
		y.parent=x;
		x.degree++;
	}
	
	public void fibHeapDecreaseKey(FibHeap H,Node x,int k){
		if (k>x.key) {
			try {
				throw new Exception("不能减小到"+k);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		x.key=k;
		Node y=x.parent;
		if (y!=null&&x.key<y.key) {
			cut(H, x, y);
			cascadingCut(H, y);
		}
		if (x.key<H.min.key) {
			H.min=x;
		}
	}
	
	private void cut(FibHeap H,Node x,Node y){
		removeChildFromParent(y, x);
		insertNodeToRootList(H, x);
		//x.parent=null;
		x.mark=false;		
	}
	
	private void cascadingCut(FibHeap H,Node y){
		Node z=y.parent;
		if (z!=null) {
			if (!y.mark) {
				y.mark=true;
			}else {
				cut(H, y, z);
				cascadingCut(H, z);
			}
		}
	}
	
	private void removeChildFromParent(Node x,Node y){
		//将x的一个孩子y移除
		if (y.right==y) {
			x.child=null;
		}else {
			if (x.child==y) {
				//x直接与y相连
				x.child=y.right;
			}
			y.right.left=y.left;
			y.left.right=y.right;
		}
		x.degree--;
	}
	
	public void fibHeapDelete(FibHeap H,Node x){
		fibHeapDecreaseKey(H, x, Integer.MIN_VALUE);
		fibHeapExtractMin(H);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a={23,7,21,18,39,52,38,41,17,24,26,35,46};
		int[] b={1,3,100,2,56};
		FibHeap H1=new FibHeap();
		H1=H1.makeFibHeap();
		Node temp=null;
		for (int i = 0; i < a.length; i++) {
			temp=new Node(a[i]);
			H1.fibHeapInsert(H1, temp);
		}
		System.out.println(H1.fibHeapExtractMin(H1).key);
//		H1.fibHeapDecreaseKey(H1, temp, 2);
//		System.out.println(H1.fibHeapExtractMin(H1).key);
//		
		H1.fibHeapDelete(H1, temp);
		
//		FibHeap H2=new FibHeap();
//		H2=H2.makeFibHeap();
//		for (int i = 0; i < b.length; i++) {
//			H2.fibHeapInsert(H2, new Node(b[i]));
//		}

		for (int i = 0; i < a.length-2; i++) {
			System.out.print(H1.fibHeapExtractMin(H1).key+" ");
		}
		System.out.println();
//		for (int i = 0; i < b.length; i++) {
//			System.out.print(H2.fibHeapExtractMin(H2).key+" ");
//		}
		System.out.println();
		
//		FibHeap H3=H1.fibHeapUnion(H1, H2);
//		for (int i = 0; i < a.length+b.length; i++) {
//			System.out.print(H3.fibHeapExtractMin(H3).key+" ");
//		}
		
		

		
	}

}
