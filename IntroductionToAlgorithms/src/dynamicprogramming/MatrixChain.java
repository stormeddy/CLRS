package dynamicprogramming;

public class MatrixChain {
	
	class Mix{
		int[][] m;
		int[][] s;
		Mix(int[][] m,int[][] s){
			this.m=m;
			this.s=s;
		}
	}
	
	public Mix memoizedMatrixChain(int[] p){
		int n=p.length-1;
		int[][] m=new int[n+1][n+1];
		int[][] s=new int[n][n+1];
		for (int i = 1; i <= n; i++) {
			for (int j = i; j <= n; j++) {
				m[i][j]=Integer.MAX_VALUE;
			}
		}
		lookUpChain(m, p, 1, n, s);
		System.out.println(m[1][n]);
		printOptimalParens(s, 1, n);
		return new Mix(m, s);
	}
	
	public int lookUpChain(int[][] m,int[] p,int i,int j,int[][] s){
		if (m[i][j]<Integer.MAX_VALUE) {
			return m[i][j];
		}
		if (i==j) {
			m[i][j]=0;
		}else {
			for (int k = i; k <= j-1; k++) {
				int q=lookUpChain(m, p, i, k, s)+lookUpChain(m, p, k+1, j, s)+p[i-1]*p[k]*p[j];
				if (q<m[i][j]) {
					m[i][j]=q;
					s[i][j]=k;
				}
			}
		}
		return m[i][j];
	}
	//自底向上版本
	public Mix matrixChainOrder(int[] p){
		int n=p.length-1;
		int[][] m=new int[n+1][n+1];
		int[][] s=new int[n][n+1];
		for (int i = 1; i <= n; i++) {
			m[i][i]=0;
		}
		for (int l = 2; l <= n; l++) {
			for (int i = 1; i <= n-l+1; i++) {
				int j=i+l-1;
				m[i][j]=Integer.MAX_VALUE;
				for (int k = i; k <= j-1; k++) {
					int q=m[i][k]+m[k+1][j]+p[i-1]*p[k]*p[j];
					if (q<m[i][j]) {
						m[i][j]=q;
						s[i][j]=k;
					}
				}
			}
		}
		System.out.println(m[1][n]);
		printOptimalParens(s, 1, n);
		return new Mix(m, s);
	}
	
	public void printOptimalParens(int[][] s,int i,int j){
		if (i==j) {
			System.out.print("A"+i);
		}else {
			System.out.print("(");
			printOptimalParens(s, i, s[i][j]);
			printOptimalParens(s, s[i][j]+1, j);
			System.out.print(")");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] p1={30,35,15,5,10,20,25};
		int[] p2={5,10,3,12,5,50,6};
		int[] p3={5,10,20,25};
		MatrixChain matrix=new MatrixChain();
		matrix.matrixChainOrder(p1);
		System.out.println();
		matrix.matrixChainOrder(p2);
		System.out.println();
		matrix.memoizedMatrixChain(p2);
		System.out.println();
		matrix.memoizedMatrixChain(p3);
	}

}
