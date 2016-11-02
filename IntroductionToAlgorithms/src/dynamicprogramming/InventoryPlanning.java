package dynamicprogramming;

public class InventoryPlanning {

	public void inventoryPlanning(int n,int m,int c,int D,int[] d, FuncH h){
//		System.out.println(h.func(i));
		int[][] cost=new int[n+1][D+1];
		int[][] make=new int[n+1][D+1];
		for (int s = 0; s <= D; s++) {
			int f=Integer.max(d[n-1]-s, 0);
			cost[n][s]=c*Integer.max(f-m, 0)+h.func(s+f-d[n-1]);
			make[n][s]=f;
		}
		int U=d[n-1];
		for (int k = n-1; k >= 1; k--) {
			U=U+d[k-1];
			for (int s = 0; s <= D; s++) {
				cost[k][s]=Integer.MAX_VALUE;
				for (int f = Integer.max(d[k-1]-s, 0); f <= U-s; f++) {
					int val=cost[k+1][s+f-d[k-1]]+c*Integer.max(f-m, 0)+h.func(s+f-d[k-1]);
					if (val<cost[k][s]) {
						cost[k][s]=val;
						make[k][s]=f;
					}
				}
			}
		}
		System.out.println(cost[1][0]);
		printPlan(make, n, d);
		
	}
	
	public void printPlan(int[][] make,int n,int[] d){
		int s=0;
		for (int k = 1; k <= n; k++) {
			System.out.println("For month "+k+" manufacture "+make[k][s]+" machines");
			s=s+make[k][s]-d[k-1];
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InventoryPlanning ip=new InventoryPlanning();
		int[] d={10,9,8,7,5,6,8,9,10,11};
		int m=8;
		int n=d.length;
		int D=0;
		for (int i = 0; i < d.length; i++) {
			D=D+d[i];
		}
		int c=5;
		ip.inventoryPlanning(n,m,c,D,d,new FuncH() {
			
			@Override
			public int func(int i) {
				// TODO Auto-generated method stub
				return (int) Math.pow(i, 1);
			}
		});
	}

}

interface FuncH{
	public int func(int i);
}


