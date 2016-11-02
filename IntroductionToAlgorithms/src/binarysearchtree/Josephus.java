package binarysearchtree;

import java.util.Arrays;

import binarysearchtree.OrderStatisticTree.Node;

public class Josephus {

	public void josephus1(int n,int m){
		OrderStatisticTree T=new OrderStatisticTree();
		for (int i = 0; i < n; i++) {
			Node x=T.new Node(i+1);
			T.OrderStatisticInsert(T, x);
		}
		int k=n;
		int j=m;
		while (k>=2) {
			Node x=T.OSSelect(T.root, j);
			System.out.println(x.value);
			T.OrderStatisticDelete(T, x);
			k--;
			j=(j+m-2)%k+1;
			
		}
		System.out.println(T.OSSelect(T.root, 1).value);
	}
	
	public int[] josephus2(int n,int m){
		int[] ret=new int[n];
		int ind=0;
		OrderStatisticTree T=new OrderStatisticTree();		
		for (int i = 0; i < n; i++) {
			Node x=T.new Node(i+1);
			T.OrderStatisticInsert(T, x);
		}
		int k=n;
		int j=1;
		while (k>=1) {
			j=((j+m-2)%k)+1;
			Node x=T.OSSelect(T.root, j);
//			System.out.println(x.value);
			ret[ind]=x.value;
			++ind;
			T.OrderStatisticDelete(T, x);
			k--;			
		}
		return ret;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Josephus josephus=new Josephus();
		josephus.josephus1(7, 3);
		int[] ret=josephus.josephus2(7, 3);
		System.out.println(Arrays.toString(ret));
	}

}
