package amortizedanalysis;

public class BitReversedIncrement {

	public static int reverse(int n,int k){
		int mid=0,bit=0;
		//kΪ�����Ƶ�λ��
		for(;k>0;k--){
			mid=mid<<1;
			bit=n&1;
			mid=mid|bit;
			n=n>>1;			
		}
		return mid;
	}
	
	 public static int increase(int n) {
		 //��ͨ������
	        int b = 1;
	        while((n|b) == n) {
	            n &= ~b;//���ͼ�λ��1��λ
	            b <<= 1;
	        }
	        n |= b;
	        return n;
	    }
	    
	 public String reverseString(String s) {
		 StringBuffer st=new StringBuffer();
		 
	        for(int i=s.length()-1;i>=0;i--)
	            st.append(s.charAt(i));
	        return st.toString();
	    }
	 
	 public static int bitReversedIncrease(int n,int k) {
		 //λ��������Ƽ�����,kΪ�����Ƶ�λ��
	        int b = 1<<k-1;
	        while(b!=0 && (n|b) == n) {
	            n &= ~b;//���߼�λ��1��λ
	            b >>= 1;
	        }
	        n |= b;
	        return n;
	    }
	 
	    public static void main(String[] args) {
	        int n = 0;
	        for(int i=0; i<16; i++) {
	            System.out.print(n+" ");
	            n = increase(n);
	        }
	        
	        System.out.println();
	        n=0;
	        for(int i=0; i<16; i++) {
	            System.out.print(n+" ");
	            n = bitReversedIncrease(n, 4);
	        }
	        System.out.println();
	        System.out.println(bitReversedIncrease(15, 4));
	        
	        for(int i=0; i<16; i++) {
	            System.out.print(reverse(i, 4)+" ");
	        }
	        
	        System.out.println(Character.toLowerCase('a'));
	 
	        
	        
	    }

}
