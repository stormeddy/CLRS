package permutebysorting;

import java.util.Random;

public class Test {
	
	public static void main(String[] args) {
		int arr[] = new int[]{1,2,3,4};
		permute_by_sorting(arr,0,arr.length-1);
		
	}
	
	//递归的思想
	private static void permute_by_sorting(int[]arr,int begin,int end) {
		int pre = -1;
		int post = -1;
		int n = end-begin+1;
		int p[] = new int[n];
		for(int i=0;i<p.length;i++)
			p[i] = new Random().nextInt((int) Math.pow(n, 3))+1;
		System.out.println("ago");
		print(arr,begin,end);
		print(p,0,n-1);
		arr = bubble_sort(arr, p,begin,end);
		boolean flag = true;
		pre = begin;
		//flag为看是不是还存在优先级相同,如果存在，则将这些优先级相同的子数列取出并单独再进行随机排列
                //以下部分对优先级相同的部分进行递归随机排列
		while(flag){
			if(post!=-1)
			pre = post+1;
			post = -1;
			for(int i=pre+1;i<=end;i++){
				if(p[pre-begin]!=p[i-begin]) {
					if(post!=-1) {
						flag = true ;break;
					}
					pre = i;
				}
				else{
					post = i;
				}
				
			}
			if(post!=-1) flag = true;
			else flag = false;
			if(flag) permute_by_sorting(arr,pre,post);
			else break;
		}
		System.out.println("now");
		print(arr,begin,end);
		print(p,0,n-1);
	}
	
	private static int[] bubble_sort(int[] arr,int[]p,int begin,int end) {
		for (int i = begin; i <= end; i++) {
			for (int j = end; j > i; j--) {
				if (p[j-begin] < p[j - 1-begin]) {
					swap(arr,p, j, j - 1,begin);
				}
			}
		}
		return arr;
	}
	
	public static void swap(int[]arr,int[]p,int i,int j,int begin){
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
		
		temp = p[i-begin];
		p[i-begin] = p[j-begin];
		p[j-begin] = temp;
	}
	
	private static void print(int[] arr,int begin,int end ) {
		for(int i=begin;i<=end;i++)
			System.out.print(arr[i]+" ");
		System.out.println();
	}
}