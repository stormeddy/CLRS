package quicksort;

public class Quicksort {
	//快速排序
	
	//针对相同元素值的快速排序,划分出相同元素的区域
	/*PARTITION'(A,p,r){
	pivot = A[r];
	i = p-1;
	k = p-1;
	for j=p to r-1{
		if (A[j]==pivot){
			k++;
			swap A[k]<->A[j];
		}
		else if(A[j]<pivot){
			k++;
			swap A[k]<->A[j];
			i++;
			swap A[k]<->A[i];
		}
	}
	k++;
	swap A[k]<->A[r];
	return i+1,k;
	}
	RANDOMIZED_QUICKSORT'(A,p,r){
		if(p<r){
		q,t = RANDOMIZED_PARTITION'(A,p,r);
		RANDOMIZED_QUICKSORT'(A,p,q-1);
		RANDOMIZED_QUICKSORT'(A,t+1,r);
		}
	}

	RANDOMIZED_PARTITION'(A,p,r){
		i = RANDOM(p,r);
		swap A[i]<->A[r];
		return PARTITION'(A,p,r);
	}*/

	
	public void quicksort(int[] arr,int p,int r) {
		if (p<r) {
			int q=partition(arr, p, r);
			quicksort(arr, p, q-1);
			quicksort(arr, q+1, r);
		}
	}
	
	public int partition(int[] arr,int p,int r) {
		int pivot=arr[r];
		int i=p-1;
		for (int j = p; j <= r-1; j++) {
			if (arr[j]<=pivot) {
				i++;
				swap(arr, i, j);				
			}
		}
		i++;
		swap(arr, i, r);
		return i;
	}
	
	//尾递归
	public void tailquicksort(int[] arr,int p,int r){
		while (p<r) {
			int q=partition(arr, p, r);
			tailquicksort(arr, p, q-1);
			p=q+1;
		}
	}
	
	//优化后的尾递归，最大栈深度O(lgn)
	public void tailrecursivequicksort(int[] arr,int p,int r) {
		while (p<r) {
			int q=partition(arr, p, r);
			if (r-q>q-p) {
				tailrecursivequicksort(arr, p, q-1);
				p=q+1;
			}else {
				tailrecursivequicksort(arr, q+1, r);
				r=q-1;
			}
		}
	}
	
	//Hoare划分
	public void hoarequicksort(int[] arr,int p,int r) {
		if (p<r) {
			int q=hoarepartition(arr, p, r);
			hoarequicksort(arr, p, q);
			hoarequicksort(arr, q+1, r);
		}
	}
	
	public int hoarepartition(int[] arr,int p,int r){
		int pivot=arr[p];
		int i=p-1,j=r+1;
		while (true) {
			do {
				j=j-1;
			} while (arr[j]>pivot);
			do {
				i=i+1;
			} while (arr[i]<pivot);
			if (i<j) {
				swap(arr, i, j);
			}else {
				return j;
			}
		}
	}
	
	private void swap(int[] arr,int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	public static void main(String[] args) {
		int[] arr={1,3,6,2,4,9,5,7,8};
		Quicksort quicksort=new Quicksort();
		//quicksort.quicksort(arr, 0, arr.length-1);
		//quicksort.hoarequicksort(arr, 0, arr.length-1);
		//quicksort.tailquicksort(arr, 0, arr.length-1);
		quicksort.tailrecursivequicksort(arr, 0, arr.length-1);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
	}
}
