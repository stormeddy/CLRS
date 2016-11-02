package greedyalgorithm;

public class ComputeChange {

	public void computeChange(int n,int[] d,int k){
		int[] c=new int[n+1];
		int[] denom=new int[n+1];
		for(int j=1;j<=n;j++){
			c[j]=Integer.MAX_VALUE;
			for(int i=1;i<=k;i++){
				if (j>=d[i]&&1+c[j-d[i]]<c[j]) {
					c[j]=1+c[j-d[i]];
					denom[j]=d[i];
				}
			}
		}
		System.out.println(c[n]);
		giveChange(n,denom);
	}
	
	public void giveChange(int j,int[] denom){
		if (j>0) {
			System.out.println(denom[j]);
			giveChange(j-denom[j], denom);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=28;
		int[] d={0,1,10,25};
		ComputeChange cc=new ComputeChange();
		cc.computeChange(n, d, d.length-1);
//		String s="hsofsh@";
//		char[] a=s.toCharArray();
//		System.out.println(Arrays.toString(a));
//		Arrays.sort(a);
//		System.out.println(Arrays.toString(a));
//		int k=8,n=11;
//		;
//		System.out.println(Integer.bitCount(n));
	}

}
