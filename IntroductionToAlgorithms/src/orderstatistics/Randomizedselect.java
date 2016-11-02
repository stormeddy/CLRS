package orderstatistics;

import java.util.Arrays;

import quicksort.RandomizedQuicksort;

public class Randomizedselect {
	//寻找第i小的数
	public int randomizedselect(int[] A,int p,int r,int i) {
		if (p==r) {
			return A[p];
		}
		int q=new RandomizedQuicksort().randomizedpartition(A,p,r);
		int k=q-p+1;
		if (i==k) {
			return A[q];
		}else if (i<k) {
			return randomizedselect(A, p, q-1, i);
		}else {
			return randomizedselect(A, q+1, r, i-k);
		}
		
/*		基于循环的版本
 * 		while (p<r) {
			int q=new RandomizedQuicksort().randomizedpartition(A,p,r);
			int k=q-p+1;
			if (k==i) {
				return A[q];
			}else if (i<k) {
				r=q-1;
			}else {
				r=q+1;
			}
		}
		return A[p];*/
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={1,3,6,2,4,9,5,7,8};
		Randomizedselect randomizedselect=new Randomizedselect();
		System.out.println(randomizedselect.randomizedselect(arr, 0, arr.length-1, 8));
		System.out.println(Arrays.toString(arr));

	}

}
