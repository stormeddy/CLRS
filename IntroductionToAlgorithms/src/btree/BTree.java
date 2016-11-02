package btree;

import java.util.Stack;

public class BTree {
	
	private Node root;
	private int t;//��С����
	private int num;//�ڵ����
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
				z.child[j]=y.child[j+t];//ע����y.child
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
		//�����ڵ�,����·��,������Ҫ��Ѱ�Ĺؼ��ֵĽڵ�
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
		//Ѱ��x.key[i]��ǰ��
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
		//Ѱ��x.key[i]�ĺ��
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
			System.out.println(k+"����B����");
			return;
		}
		for (i = 0; i < x.n; i++) {
			if (x.key[i]>=k) {
				break;
			}
		}
		if (i<x.n&&x.key[i]==k) {
			if (x.leaf) {
				//x��Ҷ�ӽ�㣬��ֱ�Ӵ�x��ɾ��k  
				for (int j = i; j < x.n-1; j++) {
					x.key[j]=x.key[j+1];
				}
				x.n--;
				return;
			}
			Node y=x.child[i];
			Node z=x.child[i+1];
			//x���ڽڵ�
			//2-a��x��ǰ��k���ӽ��y��������t���ؼ���  
			if (y.n>=t) {
				//�ҳ�k����yΪ���������е�ǰ��d,��Ȼ����yΪ���������е����ڵ�
				Node d=y;
				while (!d.leaf) {
					d=d.child[d.n];
				}
				//��d.key[d.n-1]ȡ��k,�ݹ��ɾ��d.key[d.n-1]
				x.key[i]=d.key[d.n-1];
				bTreeDelete(y, d.key[d.n-1]);
			}else if (z.n>=t) {
				//2-b,��2-a�Գ�
				//�ҳ�k����zΪ���������е�ǰ��d,��Ȼ����zΪ���������е���С�ڵ�
				Node d=z;
				while (!d.leaf) {
					d=d.child[0];
				}
				//��dȡ��k,�ݹ��ɾ��d
				x.key[i]=d.key[0];
				bTreeDelete(z, d.key[0]);
			}else {
				//y��z��ֻ��t-1���ؼ��֣���k��z�����йؼ��ֺϲ���y��ʹ��xʧȥk��ָ��z��ָ��
				//Ȼ��k��y�еݹ�ɾ�� 
				y.key[y.n]=k;//��k�ؼ��ֺϲ���y
				for (int j = 0; j < z.n; j++) {
					y.key[y.n+j+1]=z.key[j];
				}
				//����к��ӣ�����ҲҪ�ϲ� 
				if (!y.leaf) {
					for (int j = 0; j <= z.n; j++) {
						y.child[y.n+j+1]=z.child[j];
					}
				}
				//y����2t-1���ؼ���  
				y.n=y.n+1+z.n;
				//ʹ��xʧȥ�ؼ���k
				for (int j = 0; j < x.n-1; j++) {
					x.key[j]=x.key[j+1];
				}
				//ʹxʧȥָ��z��ָ��
				for (int j = i+1; j < x.n; j++) {
					x.child[j]=x.child[j+1];
				}
				x.n--;
				if (x.n==0&&root==x) {
					//���x�Ǹ����,����Ҫ�޸ĸ�
					root=y;
				}
				//��k��y�еݹ�ɾ�� 
				bTreeDelete(y, k);
			}
		}
		//�ؼ��ֲ��ڽ��x�У���ض��ڰ���k����ȷ�������ĸ�x->child[i]��
		else {
			/*if (x.leaf) {
				//x��Ҷ�ӽ��,�ҵ�����㶼û���ҵ�k����k��������  
				System.out.println(k+"����B����");
				return;
			}*/
			Node y=x.child[i];
			Node z=x.child[i+1];
			//xΪ�ڲ��ڵ�
			//child[i]��ֻ��t-1���ؼ���
			if (y.n==t-1) {
				//3-a���ֵ�
				//y�����ֵ�x->child[i+1](��z��ʾ)��������t���ؼ��� 
				if (i<x.n&&z.n>=t) {
					//��x�еĹؼ���key[i]�½���y
					y.n++;
					y.key[y.n-1]=x.key[i];
					//��z����С�ؼ���������x.key[i]
					x.key[i]=z.key[0];
					for (int j = 0; j < z.n-1; j++) {
						z.key[j]=z.key[j+1];
					}
					//��z�ʺϵ���Ůָ���Ƶ�y 
					if (!y.leaf) {
						y.child[y.n]=z.child[0];
						for (int j = 0; j < z.n-1; j++) {
							z.child[j]=z.child[j+1];
						}
					}
					z.n--;
				}
				//3-a���ֵ�,�Գ�����
				//y�����ֵ�x->child[i-1](��w��ʾ)��������t���ؼ��� 
				else if (i>0&&x.child[i-1].n>=t) {
					//��x�еĹؼ���key[i-1]�½���y
					for (int j = y.n-1; j >=0; j--) {
						y.key[j+1]=y.key[j];
					}
					y.key[0]=x.key[i-1];
					y.n++;
					Node w=x.child[i-1];
					//��y�����ֵ�w�����ؼ���������x.key[i-1]
					x.key[i-1]=w.key[w.n-1];
					//��w�ʺϵ���Ůָ���Ƶ�y
					if (!y.leaf) {
						for (int j = y.n-1; j >=0; j--) {
							y.child[j+1]=y.child[j];
						}
						y.child[0]=w.child[w.n];
					}
					w.n--;
				}
				//y�������������ֵܶ�ֻ��t-1���ؼ��֣���������һ���ֵܺϲ�
				else {
					//y�������ֵ�z
					if (i<=x.n-1) {
						//��x->key[i]����y��
						y.key[y.n]=x.key[i];
						//��z�����йؼ��ֲ���y��
						for (int j = 0; j < z.n; j++) {
							y.key[y.n+j+1]=z.key[j];
						}
						//����к��ӣ����к���ҲҪ����
						if (!y.leaf) {
							for (int j = 0; j <= z.n; j++) {
								y.child[y.n+j+1]=z.child[j];
							}
						}
						y.n=y.n+z.n+1;
						//��x->key[i]��x���Ƴ�  
						for (int j = i; j < x.n-1; j++) {
							x.key[j]=x.key[j+1];
						}
						//��ָ��z��ָ���x->child���Ƴ�  
						for (int j = i+1; j < x.n; j++) {
							x.child[j]=x.child[j+1];
						}
						x.n--;
						if (x.n==0&&root==x) {
							root=y;
						}
					}
					//i=x.n,yΪ���ұߵĽڵ�,ֻ�������ֵܺϲ�
					else {
						//��y����y�����ֵ���
						z=y;i--;
						y=x.child[i];
						//��x->key[i]����y��
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
//		Key key=T.bTreeSearch(T.root, 'L'-'A');//'L'��ǰ��Ϊ'K'
//		System.out.println(T.bTreePredecessor(key.x, key.i));
//		key=T.bTreeSearch(T.root, 'H'-'A');//'H'��ǰ��Ϊ'F'
//		System.out.println(T.bTreePredecessor(key.x, key.i));
//		key=T.bTreeSearch(T.root, 'H'-'A');//'H'�ĺ��Ϊ'K'
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
