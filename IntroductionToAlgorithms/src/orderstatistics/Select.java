package orderstatistics;

import insertionsort.InsertionSort;

import java.util.ArrayList;
import java.util.Arrays;

public class Select {
	//最坏情况为线性时间的选择算法,寻找第i小的数
	private int[] origin; //保存原数组
	public Select(){
		
	}
	
	public Select(int[] arr) {
		int[] origin=new int[arr.length];
		for (int i = 0; i < origin.length; i++) {
			origin[i]=arr[i];
		}
		this.setOrigin(origin);
	}
	
	public int[] getOrigin() {
		return origin;
	}

	public void setOrigin(int[] origin) {
		this.origin = origin;
	}
	
	
	
	public int select(int[] arr,int p,int r,int i) {
		if (p==r) {
			return arr[p];
		}
		int pivot=find(arr, p, r);
		int q=partition(arr, p, r, pivot);
		int k=q-p+1;
		if (i==k) {
			return arr[q];
		}else if (i<k) {
			return select(arr, p, q-1, i);
		}else {
			return select(arr, q+1, r, i-k);
		}
	}
	
	private int find(int[] arr,int p,int r) {
		//寻找中位数的中位数
		int k=r-p+1,h;
		int mod=5;
		int reserve=k%mod;
		if (reserve==0) {
			h=k/mod;
		}else {
			h=k/mod+1;
		}
		
		ArrayList<int[]> list=new ArrayList<int[]>(h);
		int[] mid=new int[h];
		
		for (int i = 0; i < h; i++) {
			if (i!=h-1) {
				int[] B=new int[mod];
				for (int l = 0; l < mod ; l++) {
					//arr中的数下标从p开始,而不是0,fuck!
					B[l]=arr[l+mod*i+p];
				}
				list.add(B);
			}else {
				if (reserve==0) {
					int[] B=new int[mod];
					for (int l = 0; l < mod ; l++) {
						B[l]=arr[l+mod*i+p];
					}
					list.add(B);
				}else {
					int[] B=new int[reserve];
					for (int l = 0; l < reserve ; l++) {
						B[l]=arr[l+mod*i+p];
					}
					list.add(B);
				}
			}			
		}
		
		for (int i = 0; i < h; i++) {
			int[] temp=list.get(i);
			new InsertionSort().sort(temp);
			mid[i]=temp[(temp.length-1)/2];
		}
		
		if (h==1) {
			return mid[0];
		}else {
			return select(mid, 0, h-1, (h-1)/2+1);
		}
		
		
	}
	
	private int partition(int[] arr,int p,int r,int pivot) {
		//先将主元放到最后面
		for (int i = 0; i < arr.length; i++) {
			if (pivot==arr[i]) {
				swap(arr, i, r);
				break;
			}
		}
		
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
	
	private void swap(int[] arr,int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={1,2,3,4,5,6,7,8,9,11,10,13,14,15,12,19,17,18,16};
		Select select=new Select();
		System.out.println(select.select(arr, 0, arr.length-1, 18));
		System.out.println(Arrays.toString(arr));
//		for (int i = 1; i <= arr.length; i++) {
//			System.out.println(select.select(arr, 0, arr.length-1,i));
//		}
		
	}



}
