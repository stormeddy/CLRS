package dynamicprogramming;

public class BitonicTSP {
	
	static class Coordinate{
		double x;
		double y;
		public Coordinate(double x,double y){
			this.x=x;
			this.y=y;
		}
	}
	
	private double dist(Coordinate p1,Coordinate p2){
		return Math.pow(Math.pow(p1.x-p2.x, 2)+Math.pow(p1.y-p2.y, 2),0.5);
	}
	
	public void euclideanTSP(Coordinate[] xy){
		int n=xy.length;
		double[][] b=new double[n+1][n+1];
		int[][] r=new int[n][n+1];
		b[1][2]=dist(xy[0], xy[1]);
		for (int j = 3; j <= n; j++) {
			for (int i = 1; i <= j-2; i++) {
				b[i][j]=b[i][j-1]+dist(xy[j-2], xy[j-1]);
				r[i][j]=j-1;
			}
			b[j-1][j]=Double.MAX_VALUE;
			for (int k = 1; k <= j-2; k++) {
				double q=b[k][j-1]+dist(xy[k-1], xy[j-1]);
				if (q<b[j-1][j]) {
					b[j-1][j]=q;
					r[j-1][j]=k;
				}
			}
		}
		b[n][n]=b[n-1][n]+dist(xy[n-2], xy[n-1]);
		System.out.println(b[n][n]);
		printTour(xy, r, n);
	}
	
	public void printTour(Coordinate[] xy,int[][] r,int n){
		System.out.println("("+xy[n-1].x+","+xy[n-1].y+")");
		System.out.println("("+xy[n-2].x+","+xy[n-2].y+")");
		int k=r[n-1][n];
		printPath(xy, r, k, n-1);
		System.out.println("("+xy[k-1].x+","+xy[k-1].y+")");
		
	}
	
	public void printPath(Coordinate[] xy,int[][] r,int i,int j){
		if (i<j) {
			//xy[k-1]为xy中第k个坐标
			int k=r[i][j];
			if (k!=i) {
				System.out.println("("+xy[k-1].x+","+xy[k-1].y+")");
			}
			if (k>1) {
				printPath(xy, r, i, k);
			}
		}else {
			int k=r[j][i];
			if (k>1) {
				printPath(xy, r, k, j);
				System.out.println("("+xy[k-1].x+","+xy[k-1].y+")");
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double [][] co={{0,1,2,5,6,7,8},
					 {6,0,3,4,1,5,2}};
		Coordinate [] xy=new Coordinate[co[0].length];
		for (int i = 0; i < xy.length; i++) {
			xy[i]=new Coordinate(co[0][i], co[1][i]);
		}
		BitonicTSP bitonicTSP=new BitonicTSP();
		bitonicTSP.euclideanTSP(xy);
	}

}
