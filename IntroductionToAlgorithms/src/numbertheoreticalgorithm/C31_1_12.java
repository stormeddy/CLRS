package numbertheoreticalgorithm;

public class C31_1_12 {

	public int AddWithoutArithmetic(int num1,int num2)  
	{  
	    if(num2==0) return num1;//û�н�λ��ʱ���������  
	    int sum,carry;  
	    sum=num1^num2;//��ɵ�һ��û�н�λ�ļӷ�����  
	    carry=(num1&num2)<<1;//��ɵڶ�����λ������������  
	    return AddWithoutArithmetic(sum,carry);//���еݹ飬���  
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
	    {   //ֱ��û�н�λ  
	        ans = a^b;        //������λ�ӷ�  
	        b = ((a&b)<<1);   //��λ  
	        a = ans;  
	    }  
	    return a;  
	}   
	
	//����ͼӷ�һ���ˣ�����ȡ�����Ĳ��룬Ȼ����ӡ�  
	public int negtive(int a)   //ȡ����  
	{  
	    return Add(~a, 1);  
	}  
	
	public int Sub(int a, int b)  
	{  
	    return Add(a, negtive(b));  
	}  
	
	
	//�����˷�����  
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
	        //�Ƚ�x�Ƿ����y��(1<<i)�η������⽫x��(y<<i)�Ƚϣ���Ϊ��ȷ��y��(1<<i)�η��Ƿ����  
	        if((x>>i)>=y)  
	        {  
	            ans+=(1<<i);  
	            x-=(y<<i);  
	        }  
	    }  
	    return ans;  
	}  
	
	//λ����ĳ���  
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
	
	//�ڲ�ʹ��*��/��+��-��%������������£������һ������1/3������C����ʵ�֣�
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
	
	//���������Ķ�����λ��  
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
	//λ����ĳ��� ������  
	int bit_Division2_quotient(int x, int y)
	{
		int c2 = bit_num(x), c1 = bit_num(y), quotient = 0;
		for (int i = c2 - c1; i >= 0; i--)//i=c2-c1��ֹ����y��λ�󳬹��޷����������ֵ ʱ�临�Ӷ�O(c2-c1)  
		{
			int a = (y << i);//����i=c2-c1��֤��y<<i������� a��c1+c2-c1=c2λ  
			if (a <= x)
			{
				quotient += (1 << i);
				x -= a;
			}
		}
		//�ܵ�ʱ�临�Ӷ�Ϊ O(c2)=O(x�Ķ�����λ��)=O(b^2) bΪ������ʮ����λ��  
		return quotient;
	}
	//λ����ĳ��� �������� �������һ����ֻ�Ƿ���ֵ��ͬ  
	int bit_Division2_Remainder(int x, int y)
	{
		int c2 = bit_num(x), c1 = bit_num(y), quotient = 0;
		for (int i = c2 - c1; i >= 0; i--)//i=c2-c1��ֹ����y��λ�󳬹��޷����������ֵ ʱ�临�Ӷ�O(c2-c1)  
		{
			int a = (y << i);//����i=c2-c1��֤��y<<i������� a��c1+c2-c1=c2λ  
			if (a <= x)
			{
				quotient += (1 << i);
				x -= a;
			}
		}
		//�ܵ�ʱ�临�Ӷ�Ϊ O(c2)=O(x�Ķ�����λ��)=O(b^2) bΪ������ʮ����λ��  
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
