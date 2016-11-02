package dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;

public class LongestIncreasingString {
	//15-4-5
	public enum Dir{
		LEFTUP,UP,LEFT;
	}
	
	public int[] LCSLength(int[] x,int[] y){
		//可以用LCS.java中的LCSLength2改进空间复杂度
		int m=x.length;
		int n=y.length;
		int[][] c=new int[m+1][n+1];
		Dir[][] b=new Dir[m+1][n+1];
		for (int i = 1; i <= m; i++) 
			c[i][0]=0;
		for (int j = 0; j <= n; j++) 
			c[0][j]=0;
		for (int i = 1; i <= m; i++) {
			for(int j=1 ;j<= n; j++){
				if (x[i-1]==(y[j-1])) {
					c[i][j]=c[i-1][j-1]+1;
					b[i][j]=Dir.LEFTUP;
				}else if (c[i-1][j]>=c[i][j-1]) {
					c[i][j]=c[i-1][j];
					b[i][j]=Dir.UP;
				}else {
					c[i][j]=c[i][j-1];
					b[i][j]=Dir.LEFT;
				}
			}
		}
		System.out.println(c[m][n]);
		int[] ans=new int[c[m][n]];
		int k=c[m][n]-1;
		printLCS(b, x, m, n, ans ,k);
//		printLCSWithoutB(c, x, y, m, n);
		return ans;
	}
	
	public void printLCS(Dir[][] b,int[] x,int i,int j,int[] ans,int k){
		if (i==0||j==0) {
			return;
		}
		if (b[i][j]==Dir.LEFTUP) {
			ans[k]=x[i-1];
			k--;
			printLCS(b, x, i-1, j-1, ans, k);
			
//			System.out.print(x[i-1]);
			
		}else if (b[i][j]==Dir.UP) {
			printLCS(b, x, i-1, j, ans, k);
		}else {
			printLCS(b, x, i, j-1, ans, k);
		}
	}
	
	public int[] helper(int[] x){
		//把未排序的原序列设为数组x，复制数组x到数组y,然后对数组y排序，
		//使用的是需要最坏时间为O(n^2)的快速排序，然后再对两个序列进行求LCS，这样便得最长递增子序列。		
		int[] z=deleteDuplicate(x);
		Arrays.sort(z);		
		return LCSLength(x, z);
	}
	
	public int[] deleteDuplicate(int[] arr){
		//删除重复元素
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		for(int i=0; i<arr.length; i++)
		{
			if(!arrList.contains(arr[i]))
				arrList.add(arr[i]);
		}
		int[] res=new int[arrList.size()];
		for (int i = 0; i < res.length; i++) {
			res[i]=arrList.get(i);
		}
		return res;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//寻找最长递增子序列(若非递减,则不需要删除重复元素)
		int[] x={3,5,6,2,5,4,19,5,6,7,12};
		LongestIncreasingString lis=new LongestIncreasingString();
		System.out.println(Arrays.toString(lis.helper(x)));
	}

}
