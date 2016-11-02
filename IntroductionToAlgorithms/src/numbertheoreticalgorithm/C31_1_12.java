package numbertheoreticalgorithm;

public class C31_1_12 {

	public int AddWithoutArithmetic(int num1,int num2)  
	{  
	    if(num2==0) return num1;//没有进位的时候完成运算  
	    int sum,carry;  
	    sum=num1^num2;//完成第一步没有进位的加法运算  
	    carry=(num1&num2)<<1;//完成第二步进位并且左移运算  
	    return AddWithoutArithmetic(sum,carry);//进行递归，相加  
	}  
	
	public int Add_Recursive(int a,int b)  
	{  
	    return b!=0 ? Add_Recursive(a^b,(a&b)<<1) : a;  
	    /*if(b) 
	        return Add(a^b,(a&b)<<1); 
	    else 
	        return a;*/  
	} 
	
	public int Add(int a, int b)  
	{  
	    int ans;  
	    while(b!=0)  
	    {   //直到没有进位  
	        ans = a^b;        //不带进位加法  
	        b = ((a&b)<<1);   //进位  
	        a = ans;  
	    }  
	    return a;  
	}   
	
	//这个和加法一样了，首先取减数的补码，然后相加。  
	public int negtive(int a)   //取补码  
	{  
	    return Add(~a, 1);  
	}  
	
	public int Sub(int a, int b)  
	{  
	    return Add(a, negtive(b));  
	}  
	
	
	//正数乘法运算  
	public int Pos_Multiply(int a,int b)  
	{  
	    int ans = 0;  
	    while(b!=0)  
	    {  
	        if((b&1)!=0)  
	            ans = Add(ans, a);  
	        a = (a<<1);  
	        b = (b>>1);  
	    }  
	    return ans;  
	}  
	
	public int bit_Multiplication(int a, int b)
	{
		int ans = 0;
//		int count=0;
		for (int i = 1; i!=0; i <<= 1, a <<= 1)
		{
			if ((b&i)!=0)
			{
				ans += a;
			}
		}
		return ans;
	}
	
	public int Pos_Div(int x,int y)  
	{  
	    int ans=0;  
	    for(int i=31;i>=0;i--)  
	    {  
	        //比较x是否大于y的(1<<i)次方，避免将x与(y<<i)比较，因为不确定y的(1<<i)次方是否溢出  
	        if((x>>i)>=y)  
	        {  
	            ans+=(1<<i);  
	            x-=(y<<i);  
	        }  
	    }  
	    return ans;  
	}  
	
	//位运算的除法  
	public int bit_Division1(int x, int y)
	{
		int ans = 0;
		for (int i = 31; i >= 0; i--)
		{
			if ((x >> i) >= y)
			{
				ans += (1 << i);
				x -= (y << i);
			}
		}
		return ans;
	}
	
	//在不使用*、/、+、-、%操作符的情况下，如何求一个数的1/3？（用C语言实现）
	int divideby3(int num)  
	{  
	    int sum = 0;  
	    while(num > 3)  
	    {  
	        sum = Add(num>>2 , sum);  
	        num = Add(num>>2 , num&3);  
	    }  
	    if(num == 3)  
	        sum = Add(sum , 1);  
	    return sum;  
	}  
	
	//计算整数的二进制位数  
	int bit_num(int d)
	{
		int i = 0;
		while (d!=0)
		{
			d >>= 1;
			i++;
		}
		return i;
	}
	//位运算的除法 计算商  
	int bit_Division2_quotient(int x, int y)
	{
		int c2 = bit_num(x), c1 = bit_num(y), quotient = 0;
		for (int i = c2 - c1; i >= 0; i--)//i=c2-c1防止除数y移位后超过无符号整数最大值 时间复杂度O(c2-c1)  
		{
			int a = (y << i);//有了i=c2-c1保证了y<<i不会溢出 a有c1+c2-c1=c2位  
			if (a <= x)
			{
				quotient += (1 << i);
				x -= a;
			}
		}
		//总的时间复杂度为 O(c2)=O(x的二进制位数)=O(b^2) b为除数的十进制位数  
		return quotient;
	}
	//位运算的除法 计算余数 与计算商一样，只是返回值不同  
	int bit_Division2_Remainder(int x, int y)
	{
		int c2 = bit_num(x), c1 = bit_num(y), quotient = 0;
		for (int i = c2 - c1; i >= 0; i--)//i=c2-c1防止除数y移位后超过无符号整数最大值 时间复杂度O(c2-c1)  
		{
			int a = (y << i);//有了i=c2-c1保证了y<<i不会溢出 a有c1+c2-c1=c2位  
			if (a <= x)
			{
				quotient += (1 << i);
				x -= a;
			}
		}
		//总的时间复杂度为 O(c2)=O(x的二进制位数)=O(b^2) b为除数的十进制位数  
		return x;
	}
	
	public int binary_to_decimal(String num,int base){
		int len=num.length();
		if (len==1) {
			return base*(num.charAt(0)-'0');
		}
		int mid=len/2;
		String high=num.substring(0, mid);
		String low=num.substring(mid);
		return binary_to_decimal(high, base<<(len-mid))+binary_to_decimal(low, base);
	}
	
	public static void main(String[] args) {
		C31_1_12 cal=new C31_1_12();
		int x=350;
		int y=43;
		
//		int sum=cal.Add(x, y);
//		System.out.println(sum);
//		int sum_recur=cal.Add_Recursive(x, y);
//		System.out.println(sum_recur);
//		
//		int sub=cal.Sub(x, y);
//		System.out.println(sub);
//		
//		int mul=cal.Pos_Multiply(x, y);
//		System.out.println(mul);
//		
//		int div=cal.bit_Division1(x, y);
//		System.out.println(div);
//		
//		int divideby3=cal.divideby3(10);
//		System.out.println(divideby3);
//		
//		int quotient=cal.bit_Division2_quotient(x, y);
//		System.out.println(quotient);
//		int remainder=cal.bit_Division2_Remainder(x, y);
//		System.out.println(remainder);
		
		String num="1111";
		int base=1;
		int deci=cal.binary_to_decimal(num, base);
		System.out.println(deci);
	}
	
}
