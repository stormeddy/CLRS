package dynamicprogramming;

import java.util.HashMap;

public class EditDistance {

	public enum Op{
		COPY,REPLACE,DELETE,INSERT,TWIDDLE,KILL
	}

	public void editDistance(String[] x,String[] y,int m,int n){
		int[][] c=new int[m+1][n+1];
		Op[][] op=new Op[m+1][n+1];
		int[] costInt={3,4,4,4,3,1};
		HashMap<Op, Integer> cost=new HashMap<EditDistance.Op, Integer>();
		int cnt=0;
		for (Op ops : Op.values()) {
			cost.put(ops, costInt[cnt]);
			
			cnt++;
		}	
		String[] replace=new String[n];
		String[] insert=new String[n];
		int k=0;
		for (int i = 0; i <= m; i++) {
			c[i][0]=i*cost.get(Op.DELETE);
			op[i][0]=Op.DELETE;
		}
		for (int j = 0; j <= n; j++) {
			c[0][j]=j*cost.get(Op.INSERT);
			op[0][j]=Op.INSERT;
		}
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				c[i][j]=Integer.MAX_VALUE;
				if (x[i-1].equals(y[j-1])) {
					c[i][j]=c[i-1][j-1]+cost.get(Op.COPY);
					op[i][j]=Op.COPY;
				}
				if (!x[i-1].equals(y[j-1])&&c[i-1][j-1]+cost.get(Op.REPLACE)<c[i][j]) {
					c[i][j]=c[i-1][j-1]+cost.get(Op.REPLACE);
					op[i][j]=Op.REPLACE;
					replace[j-1]=y[j-1];
				}
				if (i>=2&&j>=2&&x[i-1].equals(y[j-2])&&x[i-2].equals(y[j-1])
						&&c[i-2][j-2]+cost.get(Op.TWIDDLE)<c[i][j]) {
					c[i][j]=c[i-2][j-2]+cost.get(Op.TWIDDLE);
					op[i][j]=Op.TWIDDLE;
				}
				if (c[i-1][j]+cost.get(Op.DELETE)<c[i][j]) {
					c[i][j]=c[i-1][j]+cost.get(Op.DELETE);
					op[i][j]=Op.DELETE;
				}
				if (c[i][j-1]+cost.get(Op.INSERT)<c[i][j]) {
					c[i][j]=c[i][j-1]+cost.get(Op.INSERT);
					op[i][j]=Op.INSERT;
					insert[j-1]=y[j-1];
				}
				
			}
		}
		
		for (int i = 0; i <= m-1; i++) {
			if (c[i][n]+cost.get(Op.KILL)<c[m][n]) {
				c[m][n]=c[i][n]+cost.get(Op.KILL);
				op[m][n]=Op.KILL;
				k=i;
			}
		}
		System.out.println(c[m][n]);
		opSequence(op,m,n,k);
		
	}
	
	public void opSequence(Op[][] op,int i,int j,int kill){
		int ii,jj;
		if (i==0&&j==0) {
			return;
		}
		if (op[i][j]==Op.COPY||op[i][j]==Op.REPLACE) {
			ii=i-1;
			jj=j-1;
		}else if (op[i][j]==Op.TWIDDLE) {
			ii=i-2;
			jj=j-2;
		}else if (op[i][j]==Op.DELETE) {
			ii=i-1;
			jj=j;
		}else if (op[i][j]==Op.INSERT) {
			ii=i;
			jj=j-1;
		}else {
			ii=kill;
			jj=j;
		}
		opSequence(op, ii,jj,kill);
		System.out.println(op[i][j]);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EditDistance editDistance=new EditDistance();
		String[] x="algorithm".split("");
		String[] y="altruistic".split("");
		editDistance.editDistance(x, y, x.length, y.length);
	}

}
