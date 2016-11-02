package dynamicprogramming;

public class BreakString {

	public void breakString(int n,int[] L){
		int m=L.length;
		int[][] cost=new int[m+1][m+1];
		int[][] brk=new int[m+1][m+1];
		for (int i = 0; i <= m-1; i++) {
			cost[i][i]=cost[i][i+1]=0;
		}
		cost[m][m]=0;
		for (int len = 3; len <= m; len++) {
			for (int i = 1; i <= m-len+1; i++) {
				int j=i+len-1;
				cost[i][j]=Integer.MAX_VALUE;
				for (int k = i+1; k <= j-1; k++) {
					if (cost[i][k]+cost[k][j]<cost[i][j]) {
						cost[i][j]=cost[i][k]+cost[k][j];
						brk[i][j]=k;
					}
				}
				cost[i][j]=cost[i][j]+L[j-1]-L[i-1];
				
			}
		}
		System.out.println("The minimum cost of breaking the string is "+cost[1][m]);
		printBreaks(L, brk, 1, m);
	}
	
	public void printBreaks(int[] L,int[][] brk,int i,int j){
		if (j-i>=2) {
			int k=brk[i][j];
			System.out.println("Break at "+L[k-1]);
			printBreaks(L, brk, i, k);
			printBreaks(L, brk, k, j);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] L={0,11,14,17,20,25,30};
		int n=L[L.length-1];
		BreakString brks=new BreakString();
		brks.breakString(n, L);
	}

}
