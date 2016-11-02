package dynamicprogramming;

public class OptimalBST {

	class Mix{
		double[][] e;
		int[][] root;
		public Mix(double[][] e,int[][] root) {
			this.e=e;
			this.root=root;

		}
	}
	
	public Mix optimalBST(double[] p,double[] q,int n){
		double[][] e=new double[n+2][n+1];
		double[][] w=new double[n+2][n+1];
		int[][] root=new int[n+1][n+1];
		for (int i = 1; i <= n+1; i++) {
			e[i][i-1]=q[i-1];
			w[i][i-1]=q[i-1];
		}
/*		for (int len = 1; len <= n; len++) {
			for (int i = 1; i <= n-len+1; i++) {
				int j=i+len-1;
				e[i][j]=Integer.MAX_VALUE;
				w[i][j]=w[i][j-1]+p[j]+q[j];
				for (int r = i; r <= j; r++) {
					double t=e[i][r-1]+e[r+1][j]+w[i][j];
					if (t<e[i][j]) {
						e[i][j]=t;
						root[i][j]=r;
					}
				}
			}
		}*/
		
		for (int len = 1; len <= n; len++) {
			for (int i = 1; i <= n-len+1; i++) {
				int j=i+len-1;
				e[i][j]=Integer.MAX_VALUE;
				w[i][j]=w[i][j-1]+p[j]+q[j];
				if (i==j) {
					root[i][j]=j;
					e[i][j]=e[i][j-1]+e[i+1][j]+w[i][j];
				}else{
				for (int r = root[i][j-1]; r <= root[i+1][j]; r++) {
					double t=e[i][r-1]+e[r+1][j]+w[i][j];
					if (t<e[i][j]) {
						e[i][j]=t;
						root[i][j]=r;
					}
				}
				}
			}
		}
		constructOptimalBST(root, 1, n);
		System.out.println(e[1][n]);
		return new Mix(e, root);
		
	}
	
	public void constructOptimalBST(int[][] root,int i,int j){
		int r=root[i][j];
		if (i==1&&j==root[0].length-1) {
			System.out.println("k"+r+"为根");
		}
		if (i==r) {
			System.out.println("d"+(i-1)+"为k"+r+"的左孩子");
		}else {
			System.out.println("k"+root[i][r-1]+"为k"+r+"的左孩子");
			constructOptimalBST(root, i, r-1);
		}
		if (j==r) {
			System.out.println("d"+j+"为k"+r+"的右孩子");
		}else {
			System.out.println("k"+root[r+1][j]+"为k"+r+"的右孩子");
			constructOptimalBST(root, r+1, j);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] p={0,0.15,0.10,0.05,0.10,0.20};
		double[] q={0.05,0.10,0.05,0.05,0.05,0.10};
		
		OptimalBST opt=new OptimalBST();
		Mix mix=opt.optimalBST(p, q, p.length-1);
		int[][] root=mix.root;
		System.out.println(root[1][p.length-1]);
		
		System.out.println();
		double[] p1={0,0.04,0.06,0.08,0.02,0.10,0.12,0.14};
		double[] q1={0.06,0.06,0.06,0.06,0.05,0.05,0.05,0.05};
		Mix mix1=opt.optimalBST(p1, q1, p1.length-1);
		int[][] root1=mix1.root;
		System.out.println(root1[1][p1.length-1]);
	}

}
