package greedyalgorithm;

public class Dynamic_0_1_Knapsack {

	public enum FLAG{
		IN,OUT;
	}
	
	public void knapsack(int[] v,int[] wt,int n,int W){
		int[][] c=new int[n+1][W+1];
		FLAG[][] flag=new FLAG[n+1][W+1];
		for(int w=0;w<=W;w++) c[0][w]=0;
		for(int i=1;i<=n;i++){
			c[i][0]=0;
			for(int w=1;w<=W;w++){
				if (wt[i]<=w) {
					if (v[i]+c[i-1][w-wt[i]]>c[i-1][w]) {
						c[i][w]=v[i]+c[i-1][w-wt[i]];
						flag[i][w]=FLAG.IN;
					}else {
						c[i][w]=c[i-1][w];
						flag[i][w]=FLAG.OUT;
					}
				}else {
					c[i][w]=c[i-1][w];
					flag[i][w]=FLAG.OUT;
				}
			}
		}
		System.out.println(c[n][W]);
		trace(flag, wt, n, W);
	}
	
	public void trace(FLAG[][] flag,int[] wt,int i,int w){
		if (i==0||w==0) {
			return;
		}
		if (flag[i][w]==FLAG.IN) {
			trace(flag, wt, i-1, w-wt[i]);
			System.out.println(i);//ÎïÆ·µÄÐòºÅ
		}else if (flag[i][w]==FLAG.OUT) {
			trace(flag, wt, i-1, w);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] v={0,60,100,120};
		int[] wt={0,10,20,30};
		int W=50;
		int n=3;
		Dynamic_0_1_Knapsack dy=new Dynamic_0_1_Knapsack();
		dy.knapsack(v, wt, n, W);
	}

}
