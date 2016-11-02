package binarysearch;

import selectionsort.Selectionsort;

public class BinarySearch {
	
	public Object iterativeBinarySearch(int[] a,int v,int low,int high){
		int mid=0;
		while (low<=high) {
			mid=(low+high)/2;
			if (a[mid]==v) {
				return mid;
			}else if (v>a[mid]) {
				low=mid+1;
			}else {
				high=mid-1;
			}
		}
		return null;		
	}
	
	public Object recursiveBinarySearch(int[] a,int v,int low,int high){
		if (low>high) {
			return null;
		}
		int mid=0;
		mid=(low+high)/2;
		if (v==a[mid]) {
			return mid;
		}else if (v>a[mid]) {
			return recursiveBinarySearch(a, v, mid+1, high);
		}else {
			return recursiveBinarySearch(a, v, low, mid-1);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] old={3,4,2,-1,7,5,8};
		Selectionsort selectionsort=new Selectionsort();
		selectionsort.sort(old);
		for (int i = 0; i < old.length; i++) {
			System.out.print(old[i]+" ");
		}
		System.out.println();
		BinarySearch binarySearch=new BinarySearch();
		System.out.println(binarySearch.iterativeBinarySearch(old, 4, 0, 1));
		System.out.println(binarySearch.recursiveBinarySearch(old, 4, 0, 5));
	}

}
