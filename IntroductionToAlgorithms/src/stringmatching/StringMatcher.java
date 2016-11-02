package stringmatching;

import java.util.Arrays;
import java.util.HashMap;

import numbertheoreticalgorithm.Euclid;

public class StringMatcher {
	
	public void naive_string_matcher(String t, String p){
		//朴素字符串匹配算法
		int n=t.length();
		int m=p.length();
		for (int s = 0; s <= n-m; s++) {
			if (p.equals(t.substring(s, s+m))) {
				System.out.println("Pattern occurs with shift:"+s);
			}
		}
	}
	
	
	public boolean gap_match(String text, String pattern){
		//有问题，不正确
		//错误实例：text="abccbda",pattern="ab*ba"
		int n=text.length();
		int m=pattern.length();
		for (int i = 0, j = 0; i < n; i++) {
			if (text.charAt(i)==pattern.charAt(j)) {
				j++;
			}else if (pattern.charAt(j)=='*') {
				j++;
				i--;
			}
			if (j==m) {
				return true;
			}			
		}
		return false;
	}
	
	//32.1-4
    public boolean isMatch(String str1, String str2)
    {
    	//dp[i][j]==true表示str1[1..i]与str2[1..j]匹配 并且 str1[i]与str2[j]匹配
    	//O(mn)    	
        if (null == str1 || null == str2 || str1.isEmpty() || str2.isEmpty())
            return false;
         
        int len1 = str1.length();
        int len2 = str2.length();
         
        boolean[][] dp = new boolean[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++)
            dp[i][0] = true;
         
        boolean flg = true;
        for (int i = 1; i <= len2; i++)
        {
            if (str2.charAt(i-1) != '*')
                flg = false;
             
            dp[0][i] = flg;
        }
 
        for (int i = 1; i <= len1; i++)
        {
            for (int j = 1; j <= len2; j++)
            {
                if (str2.charAt(j-1) == '*')
                {
                    boolean match = false;
                    for (int k = i; !match && k >= 0; k--)
                    {
                        if (dp[k][j-1])
                            match = true;
                    }
                     
                    dp[i][j] = match;
                }
                else
                {
                    if (str1.charAt(i-1) == str2.charAt(j-1))
                        dp[i][j] = dp[i-1][j-1];
                    else
                        dp[i][j] = false;
                }
            }
        }
         
        boolean match = false;
        for (int i = 0; i <= len1; i++)
        {
            if (dp[i][len2])
                match = true;
        }
        
        for (int i = 1; i <= len1; i++)
        {
            for (int j = 1; j <= len2; j++)
            {
            	if (dp[i][j]) {
					System.out.println(i+" "+j);
				}
            }
            System.out.println();
        }
        
        return match;
//      return dp[len1][len2];
    }
    
    public void rabin_karp_matcher(String T, String P, int d, int q){
    	//Rabin-Karp算法
    	//此处输入均为数字
    	int n=T.length();
    	int m=P.length();
    	Euclid eu=new Euclid();
    	int h=eu.modular_exponentiation(d, m-1, q);
    	int p=0,t=0;
    	for (int i = 0; i <= m-1; i++) {
			p=(d*p+(P.charAt(i)-'0'))%q;
			t=(d*t+(T.charAt(i)-'0'))%q;
		}
    	for (int s = 0; s <= n-m; s++) {
			if (p==t) {
				System.out.println(s);
				if (P.equals(T.substring(s, s+m))) {
					System.out.println("Pattern occurs with shift:"+s);
				}else {
					System.out.println("pseudo hit");
				}
			}
			if (s<n-m) {
				int temp=(t-(T.charAt(s)-'0')*h)%q;
				if (temp<0) {
					temp+=q;
				}
				t=(d*temp+(T.charAt(s+m)-'0'))%q;
			}
		}
    }
    
    public void finite_automation_matcher(String T,String P,HashMap<Character, Integer> map){
    	//有限自动机算法
    	int n=T.length();
    	int m=P.length();
    	int[] pi=compute_prefix_function(P);
    	//两种计算转移函数的方法
    	int[][] delta=compute_transition_function(P, map, pi);
//    	int[][] delta=compute_transition_function(P, map);
    	for (int i = 0; i < delta.length; i++) {
			for (int j = 0; j < delta[0].length; j++) {
				System.out.print(delta[i][j]+" ");
			}
			System.out.println();
		}
    	int q=0;
    	for (int i = 0; i < n; i++) {
			q=delta[q][map.get(T.charAt(i))];
			if (q==m) {
				System.out.println("Pattern occurs with shift:"+(i-m+1));
			}
		}
    }
    
    private int[][] compute_transition_function(String P, HashMap<Character, Integer> map){
    	//计算转移函数
    	int m=P.length();
    	int size=map.size();
    	int[][] delta=new int[m+1][size];
    	for (int q = 0; q <= m; q++) {    		
			for (Character a : map.keySet()) {
				int k=Integer.min(m+1, q+2);
				do {
					k--;
				} while (!(P.substring(0, q)+a).endsWith(P.substring(0, k)));
				delta[q][map.get(a)]=k;
			}			
		}
    	return delta;
    }
    
    
    private int[][] compute_transition_function(String P, HashMap<Character, Integer> map, int[] pi){
    	//改进后的计算转移函数的方法
    	//32.4-8
    	int m=P.length();
    	int size=map.size();
    	int[][] delta=new int[m+1][size];
    	for (int q = 0; q <= m; q++) {    		
			for (Character a : map.keySet()) {
				if (q==m||P.charAt(q)!=a) {
					delta[q][map.get(a)]=delta[pi[q]][map.get(a)];
				}else {
					delta[q][map.get(a)]=q+1;
				}
			}			
		}
    	return delta;
    }
    
    public boolean kmp_matcher(String T,String P){
    	//KMP算法
    	boolean match=false;
    	int n=T.length();
    	int m=P.length();
    	int[] pi=compute_prefix_function(P);
    	for (int i = 1; i < pi.length; i++) {
			System.out.print(pi[i]+" ");
		}
    	System.out.println();
    	int q=0;
    	for (int i = 1; i <= n; i++) {
			while (q>0 && P.charAt(q)!=T.charAt(i-1)) {
				q=pi[q];
			}
			if (P.charAt(q)==T.charAt(i-1)) {
				q++;
			}
			if (q==m) {
				System.out.println("Pattern occurs with shift:"+(i-m));
				q=pi[q];
				match=true;
			}
			
		}
    	return match;
    }
    
	private int[] compute_prefix_function(String P) {
		int m=P.length();
		int[] pi=new int[m+1];
		pi[1]=0;
		int k=0;
		for (int q = 2; q <= m; q++) {
			while (k>0 && P.charAt(k)!=P.charAt(q-1)) {
				k=pi[k];
			}
			if (P.charAt(k)==P.charAt(q-1)) {
				k++;
			}
			pi[q]=k;
		}
		return pi;
	}
	
	/*
	 * 32.4-6 C版本
	#include<stdio.h>
	#include<string.h>
	#define maxn 1000
	#define maxm 100
	int f[maxm+1];
	int f2[maxm+1];
	int computf(char p[])
	{
	    int m=strlen(p);
	    f[1]=0;
	    int k=0,i;
	    for (i=2;i<=m;i++)
	    {
	        while (k>0&&p[k]!=p[i-1])
	            k=f[k];
	        if (p[k]==p[i-1])
	            k++;
	        f[i]=k;
	    }
	    for (i=1;i<m;i++)
	    {
	        if (f[i]==0||p[f[i]]!=p[i])
	            f2[i]=f[i];
	        int q=i;
	        while (f[q]!=0&&p[f[q]]==p[i])
	            q=f[q];
	    }
	    return 0;
	}
	int matcher(char t[],char p[])
	{
	    int n=strlen(t),m=strlen(p),i,q;
	    q=0;
	    for (i=0;i<n;i++)
	    {
	        while (q>0&&p[q]!=t[i])
	            q=f2[q];
	        if (p[q]==t[i])
	            q++;
	        if (q==m)
	        {
	            printf("%d ",i-m+1);
	            q=f[q];
	        }
	    }
	    return 0;
	}
	int main(void)
	{
	    char t[maxn],p[maxm];
	    scanf("%s",t);
	    scanf("%s",p);
	    computf(p);
	    matcher(t,p);
	    printf("\n");
	    for (int i=1;i<=strlen(p);i++)
	        printf("%d %d\n",f[i],f2[i]);
	    return 0;
	}*/
    
    public boolean is_circular_rotate(String A, String B) {
    	//32.4-7
    	//测试A是否为B的循环版本
		A=A+A;
		if (kmp_matcher(A, B)) {
			return true;
		}
		return false;
		
	}
    
    
    public void repetition_matcher(String P,String T){
    	//32-1
    	int m=P.length();
    	int n=T.length();
    	int[] rou=compute_rou(P);
    	int max_rou=rou[1];
    	for (int i = 2; i < rou.length; i++) {
			if (rou[i]>max_rou) {
				max_rou=rou[i];
			}
		}
    	int k=1+max_rou;
    	int q=0,s=0;
    	while (s<=n-m) {
			if (T.charAt(s+q)==P.charAt(q)) {
				q++;
				if (q==m) {
					System.out.println("Pattern occurs with shift:"+s);
				}
			}
			if (q==m||T.charAt(s+q)!=P.charAt(q)) {
				//跳过一定次数的重复因子，加速匹配
				//q记录了重复因子的字符个数
				s=s+Integer.max(1, (int) Math.ceil((q+0.0)/k));
				q=0;
			}
		}
    }    	
    
    private int[] compute_rou(String P){
//    	Compute π, let l=m-π[m], if m mod l=0 and for all p=m-i*l>0, p-π[p]=l, 
//    	then ρ(Pi)=m/l, otherwise ρ(Pi)=1. The running time is Θ(n).
    	int m=P.length();
    	int[] r=new int[m+1];
    	int[] pi=compute_prefix_function(P);
//    	System.out.println(Arrays.toString(pi));
    	for (int i = 1; i <= m; i++) {
			int len=i-pi[i];
			if (i%len==0) {
				int pre=i-len;
				int count=1;
				while (pre>0) {
					if (pre-pi[pre]==len) {
						pre=pre-len;
						count++;
					}else {
						
						break;
					}
				}
				r[i]=count;
			}else {
				r[i]=1;
			}
		}
    	return r;
    }
    
	public static void main(String[] args) {
		
		StringMatcher sm=new StringMatcher();
		
//		String t="acaabc";
//		String p="aab";
//		sm.naive_string_matcher(t, p);
		
//		String t1="cabccbacbacab";
//		String p1="ab*ba*c";
//		System.out.println(sm.isMatch(t1, p1));
		
//		System.out.println(sm.gap_match(t1, p1));
//		System.out.println(67399%13);
		
//		String T="2359023141526739921";
//		String P="31415";
//		int d=10,q=13;
		//32.2-1
//		String T="3141592653589793";
//		String P="26";
//		int d=10,q=11;		
//		sm.rabin_karp_matcher(T, P, d, q);
		
//		String P="ababaca";
//		String T="abababacababaca";
////		String P="aabab";
////		String T="aaababaabaababaab";
//		HashMap<Character, Integer> map=new HashMap<Character, Integer>();
//		map.put('a', 0);
//		map.put('b', 1);
//		map.put('c', 2);		
//		sm.finite_automation_matcher(T, P, map);
		
		String P="ababaca";
		String T="abababacababaca";
		sm.kmp_matcher(T, P);
//		
//		String A="arc";
//		String B="car";
//		System.out.println(sm.is_circular_rotate(A, B));
		
//		String P="ababaca";
//		String T="abababacababaca";
//		int[] r=sm.compute_rou(P);
////		System.out.println(Arrays.toString(r));
//		sm.repetition_matcher(P, T);
		
		
	}
	
}
