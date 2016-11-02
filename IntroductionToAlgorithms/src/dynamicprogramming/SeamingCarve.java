package dynamicprogramming;

public class SeamingCarve {

	
	public void compressImage(int[][] d){
		int m=d.length;
		int n=d[0].length;
		int[][] disr=new int[m+1][n+1];
		int[][] next=new int[m+1][n+1];
		for (int j = 1; j <= n; j++) {
			disr[m][j]=d[m-1][j-1];
		}
		for (int i = m-1; i >= 1; i--) {
			for (int j = 1; j <= n; j++) {
				int low=Integer.max(-1, 1-j);
				int high=Integer.min(1, n-j);
				disr[i][j]=Integer.MAX_VALUE;
				for (int k = low; k <= high; k++) {
					if (disr[i+1][j+k]<disr[i][j]) {
						disr[i][j]=disr[i+1][j+k];
						next[i][j]=j+k;
					}
				}
				disr[i][j]=disr[i][j]+d[i-1][j-1];
			}
		}
		int val=Integer.MAX_VALUE;
		int start=1;
		for (int j = 1; j <= n; j++) {
			if (disr[1][j]<val) {
				val=disr[1][j];
				start=j;
			}
		}
		System.out.println("The minimum value of the disruptive measure is "+val);
		for (int i = 1; i <= m; i++) {
			System.out.println("cut point at ("+i+","+start+")");
			start=next[i][start];
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] d={{3,2,2,3,3},{1,2,1,2,1},
				{3,2,1,3,1},{2,1,1,2,2},{1,2,2,3,3},{2,2,2,1,1}};
		SeamingCarve sCarve=new SeamingCarve();
		sCarve.compressImage(d);
	}

}
