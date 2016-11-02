package orderstatistics;

import java.util.Arrays;

public class Medianclosest {
	//O(n)时间内从n个互异元素中找到最接近中位数的k个元素
	public int[] find(int[] arr,int p,int r,int k){
		Select select=new Select();
		int median=select.select(arr, 0, arr.length-1, (arr.length+1)/2);//这一步也会对数组进行划分，具体见select方法
		int index=0;//此时中位数的下标
		for (int i = 0; i < arr.length; i++) {
			if (arr[i]==median) {
				index=i;
				break;
			}
		}
		
		int[] twokclosest=new int[2*k];
		for (int i = 0; i < k; i++) {
			if (i>index-p) {
				twokclosest[i]=Integer.MAX_VALUE;
			}else {
				twokclosest[i]=select.select(arr, p, index, index-i+1);
			}
			
		}
		for (int i = 0; i < k; i++) {
			if (i>r-index-1) {
				twokclosest[i+k]=Integer.MAX_VALUE;
			}else {
				twokclosest[i+k]=select.select(arr, index+1, r, i+1);
			}
			
		}
		

		int[] absdistance=new int[2*k];
		int[] origin=new int[2*k];
		int[] kclosest=new int[k];

		for (int i = 0; i < 2*k; i++) {
			absdistance[i]=Math.abs(twokclosest[i]-median);
		}		
		
		for (int i = 0; i < 2*k; i++) {
			origin[i]=absdistance[i];
		}
		
		for (int i = 0; i < k; i++) {
			int temp=select.select(absdistance, 0, absdistance.length-1, i+1);
			int j = 0;
			for (; j < origin.length; j++) {
				if (temp==origin[j]) {
					break;
				}
			}
			kclosest[i]=twokclosest[j];
			int j2 = j;
			for (; j2 < origin.length-1; j2++) {
				origin[j2]=origin[j2+1];
				twokclosest[j2]=twokclosest[j2+1];
			}
			origin[j2]=Integer.MAX_VALUE;
			
		}
		return kclosest;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr={1,2,3,4,5,6,7,8,9,11,10,13,14,15,12,19,17,18,16,20};
		Medianclosest medianclosest=new Medianclosest();
		System.out.println(Arrays.toString(medianclosest.find(arr, 0, arr.length-1, 15)));
	}

}
