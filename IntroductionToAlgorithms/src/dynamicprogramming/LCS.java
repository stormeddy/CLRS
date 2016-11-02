package dynamicprogramming;

public class LCS {
	//最长公共子序列
	public enum Dir{
		LEFTUP,UP,LEFT;
	}
	
	class Mix{
		int[][] c;
		Dir[][] b;
		public Mix(int[][] c,Dir[][] b) {
			this.c=c;
			this.b=b;

		}
	}
	public Mix LCSLength(String[] x,String[] y){
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
				if (x[i-1].equals(y[j-1])) {
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
//		printLCS(b, x, m, n);
		printLCSWithoutB(c, x, y, m, n);
		return new Mix(c, b);
	}
	
	
	//带备忘的版本
	public int memoizedLCSLength(String[] x,String[] y,int[][] c,int i,int j){
		if (i==0||j==0) {
			c[i][j]=0;
		}else if (c[i][j]>0) {
			return c[i][j];
		}else {
			if (x[i-1].equals(y[j-1])) {
				c[i][j]=memoizedLCSLength(x, y, c, i-1, j-1)+1;
			}else {
				c[i][j]=Integer.max(memoizedLCSLength(x, y, c, i-1, j), memoizedLCSLength(x, y, c, i, j-1));
			}
		}
		return c[i][j];
	}
	
	
	
	public void printLCS(Dir[][] b,String[] x,int i,int j){
		if (i==0||j==0) {
			return;
		}
		if (b[i][j]==Dir.LEFTUP) {
			printLCS(b, x, i-1, j-1);
			System.out.print(x[i-1]);
		}else if (b[i][j]==Dir.UP) {
			printLCS(b, x, i-1, j);
		}else {
			printLCS(b, x, i, j-1);
		}
	}
	
	//不使用b来进行重构
	public void printLCSWithoutB(int[][] c,String[] x,String[] y, int i,int j){
		if (i==0||j==0) {
			return;
		}
		if (x[i-1].equals(y[j-1])) {//此处不要写成c[i][j]==c[i-1][j-1]+1,会有bug
			printLCSWithoutB(c, x, y, i-1, j-1);
			System.out.print(x[i-1]);
		}else if (c[i-1][j]>=c[i][j-1]) {
			printLCSWithoutB(c, x, y, i-1, j);
		}else {
			printLCSWithoutB(c, x, y, i, j-1);
		}
	}
	
	
	//15-4-4
	//2*min(m,n)
	//http://blog.csdn.net/z84616995z/article/details/38011391
	public int LCSLength2(String[] x,String[] y){
		//要求输入x.length>=y.length;
		int t=0;
		int m=y.length;
		int[][] c=new int[2][m+1];
		for (int i = 1; i <= x.length; i++,t^=1) {
			for (int j = 1; j <= y.length; j++) {
				if (x[i-1].equals(y[j-1])) {
					c[t][j]=c[t^1][j-1]+1;
				}else {
					if (c[t^1][j]>=c[t][j-1]) {
						c[t][j]=c[t^1][j];
					}else {
						c[t][j]=c[t][j-1];
					}
				}
			}
		}
		System.out.println(Integer.max(c[0][m], c[1][m]));
		return Integer.max(c[0][m], c[1][m]);
	}
	
	
	public int LCSLength3(String[] x,String[] y){
		//要求输入x.length>=y.length;
		//a[j-1]即c[i-1,j-1],a[0]即c[i,j-1],a[j]即c[i-1][j]
		//http://blog.csdn.net/z84616995z/article/details/38011391
		int q=0;
		int m=y.length;
		int[] a=new int[m+1];
		for (int i = 1; i <= x.length; i++) {
			for (int j = 1; j <= y.length; j++) {
				if (x[i-1].equals(y[j-1])) {
					q=a[j-1]+1;
				}else {
					if (a[j]>=a[0]) {
						q=a[j];
					}else {
						q=a[0];
					}
				}
				a[j-1]=a[0];
				a[0]=q;
			}
			a[m]=q;
			a[0]=0;
		}
		System.out.println(a[m]);
		return a[m];
	}
	
	
	
	
	public static void main(String[] args) {
		String[] x={"A","B","C","B","D","A","B"};
		String[] y={"B","D","C","A","B","A"};
		
		String[] a={"1","0","0","1","0","1","0","1"};
		String[] b={"0","1","0","1","1","0","1","1","0"};
		LCS lcs=new LCS();

		lcs.LCSLength(x, y);
		System.out.println();
		lcs.LCSLength(a, b);
		System.out.println();
		int[][] c=new int[x.length+1][y.length+1];
		System.out.println(lcs.memoizedLCSLength(x, y, c, x.length, y.length));
		lcs.printLCSWithoutB(c, x, y, x.length, y.length);
		
		System.out.println();
		lcs.LCSLength2(b, a);
		lcs.LCSLength3(x, y);
		
		
	}
}
