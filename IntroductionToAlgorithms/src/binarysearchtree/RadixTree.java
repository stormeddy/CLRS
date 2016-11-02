package binarysearchtree;

public class RadixTree {
	//基数树,CLRS 8-3b 答案
	private static final int NUM=26;
	static class Node{
		String value;
		Node[] child=new Node[NUM];
		Node parent;
		int count;
		
		public Node(String value) {
			this.value=value;
			for (int i = 0; i < child.length; i++) {
				child[i]=null;
			}
			count=0;
		}
		
//		public Node(int value,Node left,Node right) {
//			this.value=value;
//			this.left=left;
//			this.right=right;
//			if (left!=null) {
//				left.parent=this;
//			}
//			if (right!=null) {
//				right.parent=this;
//			}
//		}
	}
	
	private Node root;
	public RadixTree() {
		root=new Node("");
	}
	
	public RadixTree(Node x) {
		root=x;
	}
	
	public void treeInsert(RadixTree T,Node z) {
		Node cur=T.root;
		String s=z.value;
		for (int i = 0; i < s.length(); i++) {
			int temp=s.charAt(i)-'a';
			if (cur.child[temp]==null) {
				cur.child[temp]=new Node(cur.value+s.charAt(i));
				cur.child[temp].parent=cur;		
				cur=cur.child[temp];
			}
			else {
				cur=cur.child[temp];
			}
		}
		cur.count++;
	}
	
	public void preOrderTreeWalk(Node x) {
		if (x!=null) {
			if (x.count!=0) {
				for (int i = 0; i < x.count; i++) {
					System.out.println(x.value);
				}
				
			}
			for (int i = 0; i < NUM; i++) {
				preOrderTreeWalk(x.child[i]);
			}
		}

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//全是小写字母
		String[] a={"dota","storm","zabab","zaba","panda","sosgmlsjs","lol","abd","abe","ab","stormy","abc","abe","abd"};
		RadixTree radixTree=new RadixTree();
		for (int i = 0; i < a.length; i++) {
			Node z=new Node(a[i]);
			radixTree.treeInsert(radixTree, z);
		}
		radixTree.preOrderTreeWalk(radixTree.root);
	}

}
