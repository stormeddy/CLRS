package binarysearchtree;

import java.util.HashSet;

public class BinarySearchTree {
	//二叉搜索树
	static class Node{
		int value;
		Node left;
		Node right;
		Node parent;
		
		public Node(int value) {
			this.value=value;
			this.left=null;
			this.right=null;
		}
		
		public Node(int value,Node left,Node right) {
			this.value=value;
			this.left=left;
			this.right=right;
			if (left!=null) {
				left.parent=this;
			}
			if (right!=null) {
				right.parent=this;
			}
		}
	}
	
	private Node root;
	public BinarySearchTree() {
		root=null;
	}
	
	public BinarySearchTree(Node x) {
		root=x;
	}
	
	public Node treeSearchNode(Node x,int k) {
		while (x!=null&&k!=x.value) {
			if (k<x.value) {
				x=x.left;
			}else {
				x=x.right;
			}
		}
		return x;
		
//      查找的递归版本		
//		if (x==null||k==x.value) {
//			return x;
//		}
//		if (k<x.value) {
//			return treeSearchNode(x.left, k);
//		}else {
//			return treeSearchNode(x.right, k);
//		}
	}
	
	public Node treeMinimumNode(Node x) {
		while (x.left!=null) {
			x=x.left;
		}
		return x;
	}
	
	public Node treeMaximumNode(Node x) {
		while (x.right!=null) {
			x=x.right;
		}
		return x;
	}
	
	public Node treeSuccessorNode (Node x) {
		if (x.right!=null) {
			return treeMinimumNode(x.right);
		}
		Node y=x.parent;
		while (y!=null&&x==y.right) {
			x=y;
			y=y.parent;
		}
		return y;
	}
	
	public Node treePredecessorNode(Node x) {
		if (x.left!=null) {
			return treeMaximumNode(x.left);
		}
		Node y=x.parent;
		while (y!=null&&x==y.left) {
			x=y;
			y=y.parent;
		}
		return y;
	}
	
	public void treeInsert(BinarySearchTree T,Node z) {
		Node y=null;
		Node x=T.root;
		while (x!=null) {
			y=x;
			if (z.value<x.value) {
				x=x.left;
			}else {
				x=x.right;
			}			
		}
		z.parent=y;
		if (y==null) {
			T.root=z;
		}else if (z.value<y.value) {
			y.left=z;
		}else {
			y.right=z;
		}
	}
	
	//插入的递归版本
	public void treeRecursiveInsert(BinarySearchTree T,Node root,Node z){
		if (root==null) {
			T.root=z;
			z.left=null;
			z.right=null;
			return;
		}
		if (z.value<root.value) {
			if (root.left==null) {
				root.left=z;
				z.parent=root;
			}else {
				treeRecursiveInsert(T,root.left, z);
			}
		}else {
			if (root.right==null) {
				root.right=z;
				z.parent=root;
			}else {
				treeRecursiveInsert(T,root.right, z);
			}
		}
	}
	
	private void transplant(BinarySearchTree T,Node u,Node v){
		//用v替换u
		if (u.parent==null) {
			T.root=v;
		}else if (u==u.parent.left) {
			u.parent.left=v;
		}else {
			u.parent.right=v;
		}
		if (v!=null) {
			v.parent=u.parent;
		}
	}
	
	public void treeDeleteNode(BinarySearchTree T,Node z) {
		if (z==null) {
			return;
		}
		if (z.left==null) {
			transplant(T, z, z.right);
		}else if (z.right==null) {
			transplant(T, z, z.left);
		}else {
			//y为z的后继
			Node y=treeMinimumNode(z.right);
			if (y.parent!=z) {
				transplant(T, y, y.right);
				y.right=z.right;
				y.right.parent=y;
			}
			transplant(T, z, y);
			y.left=z.left;
			y.left.parent=y;
		}
	}
	
	public void inOrderTreeWalk(Node x) {
		if (x!=null) {
			inOrderTreeWalk(x.left);
			System.out.println(x.value);
			inOrderTreeWalk(x.right);
		}

	}
	
	
	private Node copyNode(Node x) {
		if (x==null) {
			return null;
		}
		Node y=new Node(x.value);
		y.left=x.left;
		y.right=x.right;
		return y;
	}
	
	public Node persistentTreeInsert(BinarySearchTree T,int k) {
		//持久化动态插入
		Node z=new Node(k);
		Node newRoot=copyNode(T.root);
		Node y=null;
		Node x=newRoot;
		while (x!=null) {
			y=x;
			if (z.value<x.value) {
				x=copyNode(x.left);
				y.left=x;
			}else {
				x=copyNode(x.right);
				y.right=x;
			}
		}
		if (y==null) {
			newRoot=z;
		}else if (z.value<y.value) {
			y.left=z;
		}else {
			y.right=z;
		}
		return newRoot;
	}
	
	public Node recursivePersistentTreeInsert(Node r,int k){
		//持久化动态插入的递归版本
		Node x;
		if (r==null) {
			x=new Node(k);
		}else {
			x=copyNode(r);
			if (k<r.value) {
				x.left=recursivePersistentTreeInsert(r.left, k);
			}else {
				x.right=recursivePersistentTreeInsert(r.right, k);						
			}
		}
		return x;
	}
	
	public Node lowestCommonAncestor(Node root, Node p, Node q) {
        if(root.value>p.value && root.value>q.value) return lowestCommonAncestor(root.left,p,q);
        else if(root.value<p.value && root.value<q.value) return lowestCommonAncestor(root.right,p,q);
        else return root;

    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Node node2=new Node(2);
//		Node node4=new Node(4);
//		Node node9=new Node(9);
//		Node node17=new Node(17);
//		Node node20=new Node(20);
//		Node node3=new Node(3, node2, node4);
//		Node node13=new Node(13, node9, null);
//		Node node7=new Node(7, null, node13);
//		Node node6=new Node(6, node3, node7);
//		Node node18=new Node(18, node17, node20);
//		Node node15=new Node(15, node6, node18);
//		BinarySearchTree tree=new BinarySearchTree(node15);
//		System.out.println(tree.treeMaximumNode(node15).value);
//		System.out.println(tree.treeMinimumNode(node15).value);
//		System.out.println(tree.treeSuccessorNode(node13).value);
//		System.out.println(tree.treePredecessorNode(node17).value);
//		System.out.println(tree.treeSearchNode(node15, 9).value);
//		System.out.println(tree.treeSearchNode(node15, 8));
		
//		BinarySearchTree tree=new BinarySearchTree();
//		tree.treeRecursiveInsert(tree,tree.root, node2);
//		tree.inOrderTreeWalk(tree.root);
//		System.out.println(tree.root==node2);
		
		int[] arr=new int[]{15,6,18,3,7,17,20,2,4,13,9};
		int[] arr1={5,3,6,2,4,1};
		BinarySearchTree tree=new BinarySearchTree();
		for (int i = 0; i < arr1.length; i++) {
			Node x=new Node(arr1[i]);
			tree.treeInsert(tree, x);
		}
		Node p=tree.treeSearchNode(tree.root, 1);
		Node q=tree.treeSearchNode(tree.root, 4);
		Node ans=tree.lowestCommonAncestor(tree.root, p, q);
		System.out.println(ans.value);
		System.out.println(Math.pow(2, 30));

//		tree.inOrderTreeWalk(tree.root);
		
////		System.out.println(tree.root.value);
//		tree.treeDeleteNode(tree, tree.treeSearchNode(tree.root, 4));
//		tree.inOrderTreeWalk(tree.root);
//		tree.treeRecursiveInsert(tree,tree.root,node2);
//		tree.inOrderTreeWalk(tree.root);
		
		
//		BinarySearchTree newTree=new BinarySearchTree();
//		newTree.root=tree.persistentTreeInsert(tree, arr[0]);
//		BinarySearchTree newTree2=new BinarySearchTree();
//		newTree2.root=tree.recursivePersistentTreeInsert(tree.root, arr[0]);
//		for (int i = 1; i < arr.length; i++) {
//			newTree.root=tree.persistentTreeInsert(newTree, arr[i]);
//			newTree2.root=tree.recursivePersistentTreeInsert(newTree2.root, arr[i]);
//		}
//		newTree.inOrderTreeWalk(newTree.root);
//		newTree2.inOrderTreeWalk(newTree2.root);
//		tree.inOrderTreeWalk(tree.root);

	}

}

/*
 *     a.判断一个数组是不是二叉搜索树的后序遍历
public class Offer24
{  
    public static void main(String[] args)  
    {  
        int[] a={5,7,6,9,11,10,8};  
        int len=a.length;  
       
        System.out.println(isProOfBST(a,len));  
    }  
    public static boolean isProOfBST(int[] a,int len)   
    {  
        if(a==null||len<=0)  
            return false;  
        int root=a[len-1];//后序遍历的最后一个为根节点  
        int i=0;  
        while(a[i]<root)//找到左树的个数  
            i++;  
        int j=i;//先看右树中是否有非法数字，即比根节点小的数字  
        while(j<len-1)  
        {  
            if(a[j]<root)  
                return false;  
            j++;  
        }  
        //若左右子树的数字都合法，即左子树都比根的值小，右子树都比根节点大;此时只需递归判断左右子树是否是二叉搜索树的后序遍历  
        //求左右子树的数组，到这儿明显发现用字符串很爽呀直接subString()  
        boolean left=true;  
        if(i>0)//必须要判断是否存在左树  
        {  
            int[] aleft=new int[i];  
            for(int x=0;x<i;x++)   
                aleft[x]=a[x];  
              left=isProOfBST(aleft,i);  
        }  
        boolean right=true;  
        if(i<len-1)//必须要判断是否存在右树  
        {  
            int[] aright=new int[len-i-1];  
//          for(int y=i;y<len-1;y++)//粗心啊！！！！  
//          {  
//              aright[y]=a[y];  
//          }  
            for(int y=0;y<len-i-1;y++)  
                aright[y]=a[i+y];  
              right=isProOfBST(aright,len-i-1);  
        }     
        return left&&right;  
    }  
}  
*/