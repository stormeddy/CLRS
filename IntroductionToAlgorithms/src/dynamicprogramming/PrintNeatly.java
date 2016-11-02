package dynamicprogramming;

public class PrintNeatly {
	
	public void printNeatly(int[] l,int n,int M){
		int[][] extras=new int[n+1][n+1];
		int[][] lc=new int[n+1][n+1];
		int[] c=new int[n+1];
		int[] p=new int[n+1];
		for (int i = 1; i <= n; i++) {
			extras[i][i]=M-l[i-1];
			for (int j = i+1; j <= n; j++) {
				extras[i][j]=extras[i][j-1]-l[j-1]-1;
			}
		}
		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				if (extras[i][j]<0) {
					lc[i][j]=Integer.MAX_VALUE;
				}else if (j==n&&extras[i][j]>=0) {
					lc[i][j]=0;
				}else {
					lc[i][j]=(int) Math.pow(extras[i][j], 3);
				}
			}
		}
		c[0]=0;
		for (int j = 1; j <= n; j++) {
			c[j]=Integer.MAX_VALUE;
			for (int i = Integer.max(1,(int) (j-Math.ceil(M/2)+1)); i <= j; i++) {
				if (c[i-1]<c[j]-lc[i][j]) {//c[i-1]+lc[i][j]<c[j],·ÀÖ¹Òç³ö
					c[j]=c[i-1]+lc[i][j];
					p[j]=i;
				}
			}
		}
		giveLines(p, n);
	}
	
	public void printNeatly2(int[] l,int n,int M){
		int[] extras=new int[n+1];
		int[] lc=new int[n+1];
		int[] c=new int[n+1];
		int[] p=new int[n+1];
		extras[n]=M-l[n-1];
		for (int j = n-1; j >=1; j--) {
			extras[j]=extras[j+1]-l[j-1]-1;
		}
		
			for (int j = 1; j <= n; j++) {
				if (extras[j]<0) {
					lc[j]=Integer.MAX_VALUE;
				}else if (j==n&&extras[j]>=0) {
					lc[j]=0;
				}else {
					lc[j]=(int) Math.pow(extras[j], 3);
				}
			}
		
		int lenc=0;
		c[0]=0;
		for (int j = 1; j <= n; j++) {
			c[j]=Integer.MAX_VALUE;
			int extra=M-l[n-1];
			for (int i =j ; i >= Integer.max(1,(int) (j-Math.ceil(M/2)+1)); i--) {
				if (extra<0) {
					lenc=Integer.MAX_VALUE;
				}else if (extra==M-l[n-1]) {
					lenc=0;
				}else {
					lenc=(int) Math.pow(extra, 3);
				}
				if (c[i-1]<c[j]-lenc) {//c[i-1]+lc[i][j]<c[j],·ÀÖ¹Òç³ö
					c[j]=c[i-1]+lenc;
					p[j]=i;
				}
				extra=extra-l[i-1]-1;
			}
		}
		giveLines(p, n);
	}
	
	public int giveLines(int[] p,int j){
		int i=p[j];
		int k=0;
		if (i==1) {
			k=1;
		}else {
			k=giveLines(p, i-1)+1;
		}
		System.out.println(k+" "+i+" "+j);
		return k;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrintNeatly pn=new PrintNeatly();
		int[] len={3,4,5,2,1,6,7,4,2,3};
		//len[i]<=M
		int M=10;
		pn.printNeatly2(len, len.length, M);
	}

}
