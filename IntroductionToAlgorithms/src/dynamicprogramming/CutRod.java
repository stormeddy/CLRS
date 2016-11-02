package dynamicprogramming;

public class CutRod {
	//朴素递归
	public int cutRod(int[] p,int n){
		if (n==0) {
			return 0;
		}
		int q=Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			q=Integer.max(q, p[i]+cutRod(p, n-i-1));
		}
		return q;
	}
	
	//带备忘的动态规划
	public int memoizedCutRod(int[] p,int n){
		int[] r=new int[n+1];
		for (int i = 0; i < r.length; i++) {
			r[i]=Integer.MIN_VALUE;
		}
		return memoizedCutRodAux(p, n, r);
	}
	
	private int memoizedCutRodAux(int[] p,int n,int[] r){
		int q;
		if (r[n]>=0) {
			return r[n];
		}
		if (n==0) {
			q=0;
		}else {
			q=Integer.MIN_VALUE;
			for (int i = 0; i < n; i++) {
				q=Integer.max(q,p[i]+memoizedCutRodAux(p, n-i-1, r));
			}
		}
		r[n]=q;
		return q;
	}
	
	//带备忘的重构解,保存最优解对应的切割长度
	public Mix extendedMemoizedCutRod(int[] p,int n){
		int[] r=new int[n+1];
		int[] s=new int[n+1];
		for (int i = 0; i < r.length; i++) {
			r[i]=Integer.MIN_VALUE;
		}
		return new Mix(extendedMemoizedCutRodAux(p, n, r,s),s);
	}
	
	private int extendedMemoizedCutRodAux(int[] p,int n,int[] r,int[] s){
		int q;
		if (r[n]>=0) {
			return r[n];
		}
		if (n==0) {
			q=0;
		}else {
			q=Integer.MIN_VALUE;
			for (int i = 0; i < n; i++) {
				int temp=p[i]+extendedMemoizedCutRodAux(p, n-i-1, r,s);
				if (q<temp) {
					q=temp;
					s[n]=i+1;
				}
				
			}
		}
		r[n]=q;
		return q;
	}
	
	public void printMemoizedCutRodSolution(int[] p,int n){
		Mix mix=extendedMemoizedCutRod(p, n);
		int r=mix.r;
		int[] s=mix.s;
		System.out.println(r);
		while (n>0) {
			System.out.println(s[n]);
			n=n-s[n];
		}
	}
	//自底向上版本
	public int bottomUpCutRod(int[] p,int n){
		int[] r=new int[n+1];
		r[0]=0;
		int q;
		for (int j = 1; j <= n; j++) {
			q=Integer.MIN_VALUE;
			for (int i = 0; i < j; i++) {
				q=Integer.max(q, p[i]+r[j-i-1]);
			}
			r[j]=q;
		}
		return r[n];
	}
	
	//自底向上重构解,保存最优解对应的切割长度
	class Mix{
		int r;
		int[] s;
		public Mix(int r,int[] s) {
			this.r=r;
			this.s=s;
		}
	}
	
	public Mix extendedBottomUpCutRod(int[] p,int n){
		int[] r=new int[n+1];
		int[] s=new int[n+1];
		int q;
		for (int j = 1; j <= n; j++) {
			q=Integer.MIN_VALUE;
			for (int i = 0; i < j; i++) {
				if (q<p[i]+r[j-i-1]) {
					q=p[i]+r[j-i-1];
					s[j]=i+1;
				}
			}
			r[j]=q;
		}
		return new Mix(r[n],s);
	}
	
	public void printCutRodSolution(int[] p,int n){
		Mix mix=extendedBottomUpCutRod(p, n);
		int r=mix.r;
		int[] s=mix.s;
		System.out.println(r);
		while (n>0) {
			System.out.println(s[n]);
			n=n-s[n];
		}
	}
	
	//15.1-3
	public Mix extendedBottomUpWithCostCutRod(int[] p,int n){
		int[] r=new int[n+1];
		int[] s=new int[n+1];
		int q;
		int c=1;//切割的固定成本
		for (int j = 1; j <= n; j++) {
			q=Integer.MIN_VALUE;
			for (int i = 0; i < j; i++) {
				int temp;
				if (i==j-1) {
					//无切割
					temp=p[i]+r[j-i-1];
				}else {
					//有切割
					temp=p[i]+r[j-i-1]-c;
				}
				if (q<temp) {
					q=temp;
					s[j]=i+1;
				}
			}
			r[j]=q;
		}
		return new Mix(r[n],s);
	}
	
	public void printWithCostCutRodSolution(int[] p,int n){
		Mix mix=extendedBottomUpWithCostCutRod(p, n);
		int r=mix.r;
		int[] s=mix.s;
		System.out.println(r);
		while (n>0) {
			System.out.println(s[n]);
			n=n-s[n];
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] p={1,5,8,9,10,17,17,20,24,30};//p中第i个元素对应长度为i
		int n=4;
		CutRod cutRod=new CutRod();
//		int ans1=cutRod.cutRod(p, n);
//		System.out.println(ans1);
//		int ans2=cutRod.memoizedCutRod(p, n);
//		System.out.println(ans2);
//		int ans3=cutRod.bottomUpCutRod(p,n);
//		System.out.println(ans3);
//		cutRod.printCutRodSolution(p, n);
//		cutRod.printMemoizedCutRodSolution(p, n);
		cutRod.printWithCostCutRodSolution(p, n);
	}

}
