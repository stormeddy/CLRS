package orderstatistics;

import java.util.Arrays;
import java.util.HashSet;

public class Quantile {
	//寻找k分位点
	
	/*quantile(A,p,q,k)
	{
		if k>1
			m = (q-p+1+1)/k*floor(k/2);	//分位数的中位数
			result = select(A,p,q,m);
			print(result);
			quantile(A,p,p+m-2,floor(k/2));
			quantile(A,p+m,q,k-floor(k/2));
	}*/
	
	public void quantile(int[] arr,int p,int q,int k) {
		if (k>1) {
			int m=(int) ((q-p+2)/k*((k+1)/2));
			int result=new Select().select(arr, p, q, m);
			System.out.println(result);
			quantile(arr, p, p+m-2, (k+1)/2);
			quantile(arr, p+m, q, k-(k+1)/2);
		}
	}
	
	public static void foo(int i){
		if (i>1) {
			foo(i/2);
			foo(i/2);
		}
		System.out.println("#");
	}
	
	public static int[] helper(int[] arr,int N){
		HashSet<Integer> set=new HashSet<Integer>();
		int l=0;
		int r=0;
		int len=arr.length;
		int temp=0;
		int left=0,right = 0;
		for (int i = 0; i < arr.length-N-1; i++) {
			l=i;
			for (int j = i+1; j < arr.length-N; j++) {			
				if (!set.contains(arr[j])&&set.size()<N) {
					set.add(arr[j]);
					r=j;
					temp=r-l;
				}else if(set.size()<N){
					;
				}else {
					set.clear();
					break;
				}
			}
			if (temp<len) {
				left=l;right=r;
				len=temp;
			}			
		}
		return new int[]{left,right};
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[] arr={1,2,3,4,5,6,7,8,9,11,10,13,14,15,12,19,17,18,16};
//		Quantile quantile=new Quantile();
//		quantile.quantile(arr, 0, arr.length-1, 5);
		foo(4);
		
		int[] arr={2,0,1,3,4,5,6,7,8,9,3,2,1,4,5,6,7,8,5,4,3,2,1,6,8,2,1};
		System.out.println(Arrays.toString(Quantile.helper(arr, 10)));
	}

}
