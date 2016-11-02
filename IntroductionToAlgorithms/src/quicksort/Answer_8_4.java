package quicksort;

import java.util.Random;

public class Answer_8_4 {
	//交叉快速排序
/*	Match(RED,BLUE,p,r)  
	1.let C[p,...,r] be a new array//记录配对信息   
	2.if RED.length==1  
	3.  C[p]=p  
	4.i=random(p,r)  
	5.q=FAST-SORT(BLUE,p,r,RED[i])//以RED[i]为主元快排BLUE              
	6.q=FAST-SORT(RED,p,r,BLUE[q])//以BLUE[q] 为主元快排RED            
	7.Match(RED,BLUE,p,q-1)       
	8.Match(RED,BLUE,q+1,r)*/  
	public void match(int[] red,int[] blue,int p,int r) {
		Random random=new Random();
		if (p<r) {
			int i=random.nextInt(r-p+1)+p;
			int q=partition(blue, p, r, red[i]);
			int t=partition(red, p, r, blue[q]);
			match(red, blue, p, t-1);
//			match(red, blue, p, r);
			match(red, blue, t+1, r);
		}
		
		
	}
	
//	public void quicksort(int[] arr,int p,int r,int pivot) {
//		if (p<r) {
//			int q=partition(arr, p, r, pivot);
//			quicksort(arr, p, q-1);
//			quicksort(arr, q+1, r);
//			
//		}
//	}
	
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
		int[] red={3,9,6,8,1,2,5,4,7,0,5};
		int[] blue={7,8,6,3,5,2,4,1,0,9,5};
		Answer_8_4 answer_8_4=new Answer_8_4();
		answer_8_4.match(red, blue, 0, red.length-1);
		for (int i = 0; i < red.length; i++) {
			System.out.print(red[i]+" ");
		}
		System.out.println();
		for (int i = 0; i < blue.length; i++) {
			System.out.print(blue[i]+" ");
		}
	}

}
