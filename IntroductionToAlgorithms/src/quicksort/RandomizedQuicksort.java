package quicksort;

import java.util.Random;

public class RandomizedQuicksort {
	//随机化的快速排序
	public int randomizedpartition(int[] arr,int p,int r) {
		Random random=new Random();
		int i=random.nextInt(r+1-p)+p;
		swap(arr, r, i);
		return new Quicksort().partition(arr, p, r);
	}
	
	public void randomizedquicksort(int[] arr,int p,int r) {
		if (p<r) {
			int q=randomizedpartition(arr, p, r);
			randomizedquicksort(arr, p, q-1);
			randomizedquicksort(arr, q+1, r);
		}
	}
	
	private void swap(int[] arr,int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={1,3,6,2,4,9,5,7,8};
		RandomizedQuicksort randomizedQuicksort=new RandomizedQuicksort();
		randomizedQuicksort.randomizedquicksort(arr, 0, arr.length-1);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
	}

}
