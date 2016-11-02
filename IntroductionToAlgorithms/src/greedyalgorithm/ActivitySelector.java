package greedyalgorithm;

import java.util.ArrayList;

public class ActivitySelector {

	public static ArrayList<Integer> recursiveActivitySelector(int[] s,int[] f,int k,int n,ArrayList<Integer> ans){
		//O(n)
		int m=k+1;
		while (m<=n && s[m]<f[k]) {
			m=m+1;
		}
		if (m<=n) {
			ans.add(m);
			return recursiveActivitySelector(s, f, m, n, ans);
			
		}else {
			return null;
		}
	}
	
	public static ArrayList<Integer> greeedyActivitySelector(int[] s,int[] f){
		int n=s.length-1;
		ArrayList<Integer> ans=new ArrayList<Integer>();
		int k=1;
		ans.add(1);
		for(int m=2;m<=n;m++){
			if (s[m]>=f[k]) {
				ans.add(m);
				k=m;
			}
		}
		return ans;
	}
	
	public static ArrayList<Integer> dynamicArctivitySelector(int[] s,int[] f){
		//O(n^3)
		int n=s.length-1;
		ArrayList<Integer> ans=new ArrayList<Integer>();
		int[][] c=new int[n+1][n+1];
		int[][] r=new int[n+1][n+1];
		for(int l=2;l<=n+1;l++){
			for(int i=0;i<=n-l;i++){
				int j=i+l;
				c[i][j]=0;
				for(int k=i+1;k<j;k++){
					if (s[k]>=f[i]&&f[k]<=s[j]) {
						int temp=c[i][k]+c[k][j]+1;
						if (temp>c[i][j]) {
							c[i][j]=temp;
							r[i][j]=k;
						}
					}
				}
			}
		}
		System.out.println(c[0][n]);
		traceRoute(r, 0, n, ans);
		return ans;
	}
	
	public static void traceRoute(int[][] r,int i,int j,ArrayList<Integer> ans){
		if (r[i][j]>0) {
			
			
			ans.add(r[i][j]);

			traceRoute(r, i, r[i][j],ans);
			traceRoute(r, r[i][j], j,ans);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] s={0,1,3,0,5,3,5,6,8,8,2,12};
		int[] f={0,4,5,6,7,9,9,10,11,12,14,16};
		ArrayList<Integer> ans=new ArrayList<Integer>();
		recursiveActivitySelector(s, f, 0, s.length-1, ans);
		System.out.println(ans);
		System.out.println(greeedyActivitySelector(s, f));
		int[] s1={0,1,3,0,5,3,5,6,8,8,2,12,Integer.MAX_VALUE};
		int[] f1={0,4,5,6,7,9,9,10,11,12,14,16,Integer.MAX_VALUE};
		System.out.println(dynamicArctivitySelector(s1, f1));
	}

}
