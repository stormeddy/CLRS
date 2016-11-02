package btree;

import java.util.Stack;

public class BTree {
	
	private Node root;
	private int t;//最小度数
	private int num;//节点个数
	class Node{
		int n;
		int[] key;
		boolean leaf;
		Node[] child;
		
		public Node(int n,boolean leaf){
			this.n=n;
			key=new int[2*t-1];
			this.leaf=leaf;
			if(!this.leaf)	child=new Node[2*t];
		}
	}
	
	class Key{
		Node x;
		int i;
		public Key(Node x,int i){
			this.x=x;
			this.i=i;
		}
	}
	
	public void bTreeCreate(int t){
		this.t=t;
		Node x=new Node(0, true);
		root=x;
		num=1;
	}
	
	public void bTreeSplit(Node x,int i){
		
		num++;
		
		Node y=x.child[i];		
		Node z=new Node(t-1, y.leaf);
		for (int j = 0; j <= t-2; j++) {
			z.key[j]=y.key[j+t];
		}
		if (!y.leaf) {
			for (int j = 0; j <= t-1; j++) {
				z.child[j]=y.child[j+t];//注意是y.child
			}
		}
		y.n=t-1;
		for (int j = x.n; j >= i+1; j--) {
			x.child[j+1]=x.child[j];
		}
		x.child[i+1]=z;
		for (int j = x.n-1; j >=i; j--) {
			x.key[j+1]=x.key[j];
		}
		x.key[i]=y.key[t-1];
		x.n++;		
	}
	
	public void bTreeInsert(BTree T,int k){
		Node r=T.root;
		if (r.n==2*T.t-1) {
			num++;
			Node s=new Node(0, false);
			T.root=s;
			s.child[0]=r;
			bTreeSplit(s, 0);
			bTreeInsertNonfull(s, k);			
		}else {
			bTreeInsertNonfull(r, k);
		}
	}
	
	public void bTreeInsertNonfull(Node x,int k){
		int i=x.n-1;
		if (x.leaf) {
			while (i>=0&&k<x.key[i]) {
				x.key[i+1]=x.key[i];
				i--;
			}
			x.key[i+1]=k;
			x.n++;
		}else {
			while (i>=0&&k<x.key[i]) {
				i--;
			}
			i=i+1;
			if (x.child[i].n==2*t-1) {
				bTreeSplit(x, i);
				if (k>x.key[i]) {
					i=i+1;
				}
			}
			bTreeInsertNonfull(x.child[i], k);
		}
	}
	
	public void traverse(Node x){
		for (int i = 0; i < x.n; i++) {
			if (!x.leaf) {
				traverse(x.child[i]);
			}
			System.out.print(x.key[i]+" ");
		}
		if (!x.leaf) {
			traverse(x.child[x.n]);
		}
	}
	
	public int bTreeMinimum(Node x){
		if (x==null) {
			return (Integer) null;
		}
		while (!x.leaf) {
			x=x.child[0];
		}
		return x.key[0];
	}
	
	public int bTreeMaximum(Node x){
		if (x==null) {
			return (Integer) null;
		}
		while (!x.leaf) {
			x=x.child[x.n];
		}
		return x.key[x.n-1];
	}
	
	public Key bTreeSearch(Node x,int k){
		int i=0;
		while (i<x.n && k>x.key[i]) {
			i=i+1;
		}
		if (i<x.n && k==x.key[i]) {
			return new Key(x, i);
		}else if (x.leaf) {
			return null;
		}else {
			return bTreeSearch(x.child[i], k);
		}
	}
	
	public void buildPath(Node x,int k,Stack<Node> s){
		//搜索节点,建立路径,不包括要搜寻的关键字的节点
		int i=0;
		while (i<x.n&&k>x.key[i]) {
			i++;
		}
		if (i<x.n&&k==x.key[i]) {
			return;
		}else if (x.leaf) {
			return;
		}else {
			s.push(x);
			buildPath(x.child[i], k, s);
		}
	}
	
	public int bTreePredecessor(Node x,int i){
		//寻找x.key[i]的前驱
		if (!x.leaf) {
			return bTreeMaximum(x.child[i]);
		}else if (i>=1) {
			return x.key[i-1];
		}else {
			Node z=x;
			Stack<Node> s=new Stack<Node>();
			buildPath(root, x.key[i], s);
			while (true) {
				if (s.isEmpty()) {
					return (Integer)null;
				}
				Node y=s.pop();
				int j=0;
				while (y.child[j]!=z) {
					j++;
				}
				if (j==0) {
					z=y;
				}else {
					return y.key[j-1];
				}
			}			
		}
	}
	
	public int bTreeSuccessor(Node x,int i){
		//寻找x.key[i]的后继
		if (!x.leaf) {
			return bTreeMinimum(x.child[x.n-1]);
		}else if (i<=x.n-2) {
			return x.key[i+1];
		}else {
			Node z=x;
			Stack<Node> s=new Stack<Node>();
			buildPath(root, x.key[i], s);
			while (true) {
				if (s.isEmpty()) {
					return (Integer)null;
				}
				Node y=s.pop();
				int j=0;
				while (y.child[j]!=z) {
					j++;
				}
				if (j==y.n) {
					z=y;
				}else {
					return y.key[j];
				}
			}			
		}
	}
	
	public void bTreeDelete(Node x, int k){
		int i;
		if (bTreeSearch(x, k)==null) {
			System.out.println(k+"不在B树中");
			return;
		}
		for (i = 0; i < x.n; i++) {
			if (x.key[i]>=k) {
				break;
			}
		}
		if (i<x.n&&x.key[i]==k) {
			if (x.leaf) {
				//x是叶子结点，则直接从x中删除k  
				for (int j = i; j < x.n-1; j++) {
					x.key[j]=x.key[j+1];
				}
				x.n--;
				return;
			}
			Node y=x.child[i];
			Node z=x.child[i+1];
			//x是内节点
			//2-a：x中前于k的子结点y包含至少t个关键字  
			if (y.n>=t) {
				//找出k在以y为根的子树中的前驱d,必然是以y为根的子树中的最大节点
				Node d=y;
				while (!d.leaf) {
					d=d.child[d.n];
				}
				//用d.key[d.n-1]取代k,递归地删除d.key[d.n-1]
				x.key[i]=d.key[d.n-1];
				bTreeDelete(y, d.key[d.n-1]);
			}else if (z.n>=t) {
				//2-b,与2-a对称
				//找出k在以z为根的子树中的前驱d,必然是以z为根的子树中的最小节点
				Node d=z;
				while (!d.leaf) {
					d=d.child[0];
				}
				//用d取代k,递归地删除d
				x.key[i]=d.key[0];
				bTreeDelete(z, d.key[0]);
			}else {
				//y和z都只有t-1个关键字，将k和z中所有关键字合并进y，使得x失去k和指向z的指针
				//然后将k从y中递归删除 
				y.key[y.n]=k;//将k关键字合并进y
				for (int j = 0; j < z.n; j++) {
					y.key[y.n+j+1]=z.key[j];
				}
				//如果有孩子，孩子也要合并 
				if (!y.leaf) {
					for (int j = 0; j <= z.n; j++) {
						y.child[y.n+j+1]=z.child[j];
					}
				}
				//y包含2t-1个关键字  
				y.n=y.n+1+z.n;
				//使得x失去关键字k
				for (int j = 0; j < x.n-1; j++) {
					x.key[j]=x.key[j+1];
				}
				//使x失去指向z的指针
				for (int j = i+1; j < x.n; j++) {
					x.child[j]=x.child[j+1];
				}
				x.n--;
				if (x.n==0&&root==x) {
					//如果x是根结点,则需要修改根
					root=y;
				}
				//将k从y中递归删除 
				bTreeDelete(y, k);
			}
		}
		//关键字不在结点x中，则必定在包含k的正确的子树的根x->child[i]中
		else {
			/*if (x.leaf) {
				//x是叶子结点,找到根结点都没有找到k，则k不在树中  
				System.out.println(k+"不在B树中");
				return;
			}*/
			Node y=x.child[i];
			Node z=x.child[i+1];
			//x为内部节点
			//child[i]中只有t-1个关键字
			if (y.n==t-1) {
				//3-a右兄弟
				//y的右兄弟x->child[i+1](用z表示)包含至少t个关键字 
				if (i<x.n&&z.n>=t) {
					//将x中的关键字key[i]下降至y
					y.n++;
					y.key[y.n-1]=x.key[i];
					//将z的最小关键字上升至x.key[i]
					x.key[i]=z.key[0];
					for (int j = 0; j < z.n-1; j++) {
						z.key[j]=z.key[j+1];
					}
					//将z适合的子女指针移到y 
					if (!y.leaf) {
						y.child[y.n]=z.child[0];
						for (int j = 0; j < z.n-1; j++) {
							z.child[j]=z.child[j+1];
						}
					}
					z.n--;
				}
				//3-a左兄弟,对称类似
				//y的左兄弟x->child[i-1](用w表示)包含至少t个关键字 
				else if (i>0&&x.child[i-1].n>=t) {
					//将x中的关键字key[i-1]下降至y
					for (int j = y.n-1; j >=0; j--) {
						y.key[j+1]=y.key[j];
					}
					y.key[0]=x.key[i-1];
					y.n++;
					Node w=x.child[i-1];
					//将y的左兄弟w的最大关键字上升至x.key[i-1]
					x.key[i-1]=w.key[w.n-1];
					//将w适合的子女指针移到y
					if (!y.leaf) {
						for (int j = y.n-1; j >=0; j--) {
							y.child[j+1]=y.child[j];
						}
						y.child[0]=w.child[w.n];
					}
					w.n--;
				}
				//y和其所有相邻兄弟都只有t-1个关键字，则与其中一个兄弟合并
				else {
					//y存在右兄弟z
					if (i<=x.n-1) {
						//将x->key[i]并入y中
						y.key[y.n]=x.key[i];
						//将z中所有关键字并入y中
						for (int j = 0; j < z.n; j++) {
							y.key[y.n+j+1]=z.key[j];
						}
						//如果有孩子，所有孩子也要并入
						if (!y.leaf) {
							for (int j = 0; j <= z.n; j++) {
								y.child[y.n+j+1]=z.child[j];
							}
						}
						y.n=y.n+z.n+1;
						//将x->key[i]从x中移出  
						for (int j = i; j < x.n-1; j++) {
							x.key[j]=x.key[j+1];
						}
						//把指向z的指针从x->child中移出  
						for (int j = i+1; j < x.n; j++) {
							x.child[j]=x.child[j+1];
						}
						x.n--;
						if (x.n==0&&root==x) {
							root=y;
						}
					}
					//i=x.n,y为最右边的节点,只能与左兄弟合并
					else {
						//把y并入y的左兄弟中
						z=y;i--;
						y=x.child[i];
						//将x->key[i]并入y中
						y.key[y.n]=x.key[i];
						for (int j = 1; j < z.n; j++) {
							y.key[y.n+1+j]=z.key[j];
						}
						if (!y.leaf) {
							for (int j = 0; j <= z.n; j++) {
								y.child[y.n+1+j]=z.child[j];
							}
						}
						y.n=y.n+z.n+1;
						for (int j = i; j < x.n-1; j++) {
							x.key[j]=x.key[j+1];
						}
						for (int j = i+1; j < x.n; j++) {
							x.child[j]=x.child[j+1];							
						}
						x.n--;
						if (x.n==0&&root==x) {
							root=y;
						}						
					}					
				}
				
			}
			bTreeDelete(y, k);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char ch[] = {'F','S','Q','K','C','L','H','T','V','W','M','R','N','P','A','B','X','Y','D','Z','E'};  
		System.out.println(ch.length);
		int[] a=new int[ch.length];
		for (int i = 0; i < ch.length; i++) {
			a[i]=ch[i]-'A';
		}
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i]+" ");
		}
		System.out.println();
		BTree T=new BTree();
		T.bTreeCreate(2);
		for (int i = 0; i < a.length; i++) {
			T.bTreeInsert(T, a[i]);
//			T.traverse(T.root);
//			System.out.println();
		}
		T.traverse(T.root);
		System.out.println();
//		T.traverse(T.root);
//		System.out.println(T.bTreeMinimum(null));
//		System.out.println(T.bTreeMinimum(T.root));
//		System.out.println(T.bTreeMaximum(T.root));
//		Key key=T.bTreeSearch(T.root, 'L'-'A');//'L'的前驱为'K'
//		System.out.println(T.bTreePredecessor(key.x, key.i));
//		key=T.bTreeSearch(T.root, 'H'-'A');//'H'的前驱为'F'
//		System.out.println(T.bTreePredecessor(key.x, key.i));
//		key=T.bTreeSearch(T.root, 'H'-'A');//'H'的后继为'K'
//		System.out.println(T.bTreeSuccessor(key.x, key.i));
//		
//		int[] arr={1,2,3,4,5,6,7,8,9,10};
//		BTree bTree=new BTree();
//		bTree.bTreeCreate(2);
//		for (int i = 0; i < arr.length; i++) {
//			bTree.bTreeInsert(bTree, arr[i]);
//			System.out.println(i+1+" "+bTree.num);
//		}
		System.out.println();
		for (int i = 0; i < a.length; i++) {
			T.bTreeDelete(T.root, a[i]);
			T.traverse(T.root);
			T.bTreeDelete(T.root, 100);			
		}
		T.traverse(T.root);
		
	}

}
