package amortizedanalysis;

import java.util.Scanner;


public class MSTest7
{
	public int cal(int a,int b,int k){
		int cnt=0;
		for (int i = a; i <= b; i++) {
			if (i%k==0) {
				if (is_prime(k)&& is_least(i, k)) {
					cnt++;
				}				
			}
		}
		return cnt;
	}
	
	public int modular_exponentiation(int a,int b,int n){
		//反复平方法求模取幂
		//从左到右（从高位到低位检查）
		int c=0,d=1;
		String str=Integer.toBinaryString(b);
		for (int i = 0; i < str.length(); i++) {
			c=c*2;
			d=d*d%n;
			if (str.charAt(i)=='1') {
				c=c+1;
				d=d*a%n;
			}
//			System.out.println(d+" "+c);
		}
		return d;
	}
	
	private boolean is_least(int a,int k) {
		for (int i = 2; i<k; i++) {
			if (is_prime(i)&& a%i==0) {
				return false;
			}
		}
		return true;
	}
	
	private boolean is_prime(int n){
		//伪素数测试
				//n为大于2的整数
		if (n==2) {
			return true;
		}
				if (modular_exponentiation(2, n-1, n)!=1) {
					return false;
				}else {
					return true;
				}
	}
	
    public static void main(String args[])
    {
    	MSTest7 mn=new MSTest7();
        Scanner cin = new Scanner(System.in);
        int a = 0, b=0,k=0;
        while(cin.hasNextInt())
        {
            a = cin.nextInt();
            b = cin.nextInt();
            k = cin.nextInt();
            System.out.println(mn.cal(a, b, k));
            
        }
        
    }
}