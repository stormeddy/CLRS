package dynamicprogramming;

public class LPS {
	//最长回文子序列
	//将序列逆序后,求两者的LCS
	
	
	public  String reverse(String x){
		StringBuffer s=new StringBuffer(x);
		s=s.reverse();
		return s.toString();
	}
		
	public enum Dir{
		LEFTDOWN,DOWN,LEFT;
	}
	//不使用LCS的方法,直接写
	public void LPSLength(String[] x){
		int n=x.length;
		int[][] p=new int[n+1][n+1];
		Dir[][] b=new Dir[n+1][n+1];
		for (int i = 1; i <= n-1; i++) {
			p[i][i]=1;
			int j=i+1;
			if (x[i-1].equals(x[j-1])) {
				p[i][j]=2;
				b[i][j]=Dir.LEFTDOWN;
			}else {
				p[i][j]=1;
				b[i][j]=Dir.DOWN;
			}
		}
		p[n][n]=1;
		for (int i = n-2; i >= 1; i--) {
			for(int j=i+1;j<=n;j++){
				if (x[i-1].equals(x[j-1])) {
					p[i][j]=p[i+1][j-1]+2;
					b[i][j]=Dir.LEFTDOWN;
				}else if (p[i+1][j]>=p[i][j-1]) {
					p[i][j]=p[i+1][j];
					b[i][j]=Dir.DOWN;
				}else {
					p[i][j]=p[i][j-1];
					b[i][j]=Dir.LEFT;
				}
			}
		}
		System.out.println(p[1][n]);
		System.out.println(generateLPS(b, x, 1, n, ""));
	}
	
	public String generateLPS(Dir[][] b,String[] x,int i,int j,String s){
		if (i>j) {
			return s;
		}else if (i==j) {
			return s+x[i-1];
		}else if (b[i][j]==Dir.LEFTDOWN) {
			return x[i-1]+generateLPS(b, x, i+1, j-1, s)+x[i-1];
		}else if (b[i][j]==Dir.DOWN) {
			return generateLPS(b, x, i+1, j, s);
		}else {
			return generateLPS(b, x, i, j-1, s);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LPS lps=new LPS();
		LCS lcs=new LCS();
		String org="character";
		String[] s=org.split("");
		String[] t=lps.reverse(org).split("");
		lcs.LCSLength(s, t);
		System.out.println();
		lps.LPSLength(org.split(""));
	}

}
