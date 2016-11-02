package numbertheoreticalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Euclid {
	//欧几里得算法
	public int euclid(int a,int b){
		if (b==0) {
			return a;
		}else {
			return euclid(b, a%b);
		}
	}
	
	public int euclid_while(int a,int b){
		//
		while(b!=0){
			int c=b;
			b=a%b;
			a=c;
		}
		return a;
	}
	
	public Triplet extended_euclid(int a,int b){
		if (b==0) {
			return new Triplet(a, 1, 0);
		}else {
			Triplet temp=extended_euclid(b, a%b);
			int t_d=temp.d;
			int t_x=temp.x;
			int t_y=temp.y;
			Triplet ret=new Triplet(t_d, t_y, t_x-a/b*t_y);
			return ret;
		}
	}
	
	public Triplet extended_euclid(int a[]){
		//31.2-7
		if (a.length<=1) {
			return null;
		}
		int n=a.length;
		Triplet s1=extended_euclid(a[0], a[1]);
		Triplet s2,s3;
		s3=new Triplet(s1.d, s1.x, s1.y);
		s3.z=new int[n];
		s3.z[0]=s1.x;
		s3.z[1]=s1.y;
		int k=2;
		while (k<n) {
			s2=extended_euclid(s1.d, a[k]);
			s3.d=s1.d=s2.d;
			for (int j = 0; j < k; j++) {
				s3.z[j]*=s2.x;
			}
			s3.z[k]=s2.y;
			k++;
		}
		return s3;
	}
	
	public Triplet extended_euclid_lcm(int a[]){
		//31.2-8
		//这里的z[]没有用处
		if (a.length<=1) {
			return null;
		}
		int n=a.length;
		Triplet s1=extended_euclid(a[0], a[1]);
		s1.d=a[0]/s1.d*a[1];//s.d为lcm
		Triplet s2,s3;
		s3=new Triplet(s1.d, s1.x, s1.y);
		s3.z=new int[n];
		s3.z[0]=s1.x;
		s3.z[1]=s1.y;
		int k=2;
		while (k<n) {
			s2=extended_euclid(s1.d, a[k]);
			s1.d=s1.d/s2.d*a[k];//s1.d和a[k]的lcm
			for (int j = 0; j < k; j++) {
				s3.z[j]*=s2.x;
			}
			s3.z[k]=s2.y;
			k++;
		}
		s3.d=s1.d;
		return s3;
	}
	
	
	public int fibonacci(int n,int[] r){
//		int[] r=new int[n+1];
	    return fib_help(n, r);
	}
	
	private int fib_help(int n,int[] r){
		if (r[n]>0) {
			return r[n];
		}
		if (n==1) {
			r[1]=1;
			return 1;
		}
		if (n==0) {
			return 0;
		}
		return fib_help(n-1, r)+fib_help(n-2, r);
	}
	
	public int power(int k){  
	    if (k%2==0) {
			return 1;
		}else {
			return -1;
		}
	}  
	
	//求解模线性方程
	public int modular_linear_equation_solver(int a ,int b,int n){
		//gcd(a,n)=1时，即a与n互质的时候有唯一解，返回第一个解x
		Triplet t=extended_euclid(a, n);
		int d=t.d;
		int x_t=t.x;
		int x=-1;
		if (b%d==0) {
			x=x_t*(b/d)%n;//x=x_t*(b/d)%(n/d);两者等价
			if (x<0) {
				//mod n 值取0～n-1
				x=x+n;
			}
			for (int i = 0; i < d; i++) {
				System.out.println((x+i*n/d)%n);
			}
		}else {
			System.out.println("no solutions");
		}
		return x;
	}
	
	public int Chinese_remainder_theorem(int[] a,int[] n){
		int s=1;
		int size=n.length;
		for (int i = 0; i < size; i++) {
			s*=n[i];
		}
		
		int[] m=new int[size];
		int[] c=new int[size];
		for (int i = 0; i < size; i++) {
			m[i]=s/n[i];
		}
		int sum=0;
		for (int i = 0; i < size; i++) {
			c[i]=modular_linear_equation_solver(m[i], 1, n[i]);
			sum+=a[i]*m[i]*c[i];
		}
		return sum%s;
		
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
		
		////从右到左（从低位到高位检查）
//		int c=0,d=a,t=1,base=0;
//		String str=Integer.toBinaryString(b);
//		for (int i = str.length()-1; i >= 0; i--) {
//			if (str.charAt(i)=='1') {
////				c=c+1;
//				c=c+(1<<base);
//				t=t*d%n;
//			}			
//			base++;
//			d=d*d%n;
////			System.out.println(t+" "+c);
//		}
//		return t;
	}
	
	public enum CP{
		COMPOSITE,PRIME;
	}
	
	public CP pseudoprime(int n){
		//伪素数测试
		//n为大于2的整数
		if (modular_exponentiation(2, n-1, n)!=1) {
			return CP.COMPOSITE;
		}else {
			return CP.PRIME;
		}
	}
	
	
	private boolean witness(int a,int n){
		if (n%2==0) {
			//n为偶数
			return true;
		}
		int t=0;
		int u=n-1;
		while ((u&1)==0) {
			t++;
			u>>=1;
		}
//		System.out.println(u+" "+t);
		int[] x=new int[t+1];
		x[0]=modular_exponentiation(a, u, n);
		for (int i = 1; i <= t; i++) {
			x[i]=x[i-1]*x[i-1]%n;
			if (x[i]==1 && x[i-1]!=1 && x[i-1]!=n-1) {
				return true;
			}
		}
		if (x[t]!=1) {
			return true;
		}		
		return false;
	}
	
	public CP miller_rabin(int n,int s){
		//miller_rabin素数测试
		Random random=new Random();
		for (int j = 1; j <= s; j++) {
			int a=random.nextInt(n-1)+1;//1<=a<=n-1
			if (witness(a, n)) {
				return CP.COMPOSITE;
			}
		}
		return CP.PRIME;
	}
	
	
	public List<Integer> pollard_rho(int n){
		//启发式整数因子分解
		List<Integer> ret=new ArrayList<Integer>();
		int i=1;
		Random random=new Random();
		int x=random.nextInt(n);
//		int x=2;
		int y=x,k=2;
		while (true) {
			i++;
			x=(x*x-1)%n;
			int d=euclid(y-x, n);
			d=d>0?d:-d;
			if (d!=1&&d!=n) {
				ret.add(d);
				System.out.println(d+" "+x+" "+y);
				
				int product=1;
				for (Integer integer : ret) {
					product*=integer;
				}
				if (product==n) {
					return ret;
				}
			}
			if (i==k) {
				y=x;
				k=2*k;
			}
		}
	}
	
	
	public int binary_gcd(int a,int b){
		if (a==0) {
			return b;
		}
		if ((a&1)==0 && (b&1)==0) {
			//a,b为偶数
			return 2*binary_gcd(a>>1, b>>1);			
		}else if ((a&1)==1 && (b&1)==1) {
			//a,b为奇数
			if (a<b) {
				a=a^b;
				b=b^a;
				a=a^b;
			}
			return binary_gcd((a-b)>>1, b);
		}else if ((a&1)==1 && (b&1)==0) {
			//a为奇数,b为偶数
			return binary_gcd(a, b>>1);
		}else {
			return binary_gcd(a>>1, b);
		}
	}
	
	
	public int Dev(int a,int b){
	   //求a除以b
	   int ans=0,j=1,temp=a;  
	   while ((temp>>=1)!=0)j++;//计算a的位数为lga  
	   for (int i=j;i>=0;i--)//O(lga+1)  
	   {  
	       if ((a>>i)>=b)  
	       {  
	           ans+=(1<<i);  
	           a-=(b<<i);  
	       }  
	   }  
	   System.out.println("residual:"+a);
	   return ans;  
	}  
	
	
//	关于手算开方，就是利用 (10a+b)^2 - 100a^2 = 20ab + b^2
//			开方的每一步中：M是上一步的余数，a是到上一步为止已求出的部分结果。
//			1. 在M后面添上后2位，
//			2. 试商：取b=0..9，计算20ab + b^2，得到满足20ab + b^2<=M的最大b值
//			3. 求余：计算M = M - (20ab + b^2)
//			4. 在a后添上一位b
//			注意到b是一位数，以上计算和比较都可以在O(d)时间内完成。
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Euclid eu=new Euclid();
//		int a=4,b=3;
//		
//		int gcd=eu.euclid_while(a,b);
//		System.out.println(gcd);
//		
//		
//		Triplet ans=eu.extended_euclid(a, b);
//		System.out.println(ans.d+"="+a+"*"+ans.x+"+"+b+"*"+ans.y);
		
		//31.2-6
//		int n=40;
//		int[] r=new int[n+2];
//		Triplet ret=eu.extended_euclid(eu.fibonacci(n+1,r), eu.fibonacci(n,r));
//		System.out.println(ret);
//		System.out.println(eu.power(n-1)*eu.fibonacci(n-2,r)+" "+eu.power(n)*eu.fibonacci(n-1,r));
		
//		int[] a={788,460,286,257,5689};
//		int[] a={12,24,39,42,60};
//		int[] a={1,2,3,4,5,6,7,8,9,10};
//		Triplet s=eu.extended_euclid(a);
//		System.out.println(s.d);
//		for (int i = 0; i < a.length; i++) {
//			System.out.print(s.z[i]+" ");
//		}
		
		
//		int[] a={1,2,3,4,5,6,7,8,9,10};
//		Triplet s=eu.extended_euclid_lcm(a);
//		System.out.println(s.d);
//		for (int i = 0; i < a.length; i++) {
//			System.out.print(s.z[i]+" ");
//		}
		
//		int a=35,b=10,n=50;
//		int a=3,b=1,n=280;
//		eu.modular_linear_equation_solver(a, b, n);
		
		//a为余数数组，n为除数数组
//		int[] a={2,3,2};
//		int[] n={3,5,7};
//		int[] a={2,3};
//		int[] n={5,13};
//		int[] a={4,5};
//		int[] n={5,11};
//		int[] a={1,2,3};
//		int[] n={9,8,7};
//		int ret=eu.Chinese_remainder_theorem(a, n);
//		System.out.println(ret);
		
//		int a=7,b=560,n=561;
//		int a=100,b=3,n=319;
//		int a=254,b=187,n=319;
//		int ret=eu.modular_exponentiation(a, b, n);
//		System.out.println(ret);
		
		
//		for (int i = 3; i <= 10000; i++) {
//			if (eu.pseudoprime(i)==CP.PRIME) {
//				System.out.println(i);
//			}
//		}
		
		
		
//		int a=7,n=561;
//		System.out.println(eu.witness(a, n));
		
//		int s=5;
//		for (int i = 3; i <= 10000; i++) {
//			if (eu.miller_rabin(i, s)==CP.PRIME) {
//				System.out.println(i);
//			}
//		}
		
		
//		int n=1387;
//		List<Integer> list=eu.pollard_rho(n);
//		System.out.println(list);
		
//		https://zh.wikipedia.org/wiki/%E4%BA%8C%E6%AC%A1%E5%89%A9%E4%BD%99
//		二次剩余
//		int p=23,a=4,b=9,c=15;
//		System.out.println(eu.modular_exponentiation(a, (p-1)/2, p));
//		System.out.println(eu.modular_exponentiation(b, (p-1)/2, p));
//		System.out.println(eu.modular_exponentiation(c, (p-1)/2, p));
		
		
		int a=13,b=3;
		System.out.println(eu.Dev(a, b));
		
	}

	class Triplet{
		int d;//d为gcd或者lcm
		int x;//x,y为线性系数
		int y;
		int z[];//存放x0,x1,...,xn
		public Triplet(int d,int x,int y){
			this.d=d;
			this.x=x;
			this.y=y;
		}
		
		public String toString(){
			return d+" "+x+" "+y;
		}
	}
	
}
