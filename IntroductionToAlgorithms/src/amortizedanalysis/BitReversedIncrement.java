package amortizedanalysis;

public class BitReversedIncrement {

	public static int reverse(int n,int k){
		int mid=0,bit=0;
		//k为二进制的位数
		for(;k>0;k--){
			mid=mid<<1;
			bit=n&1;
			mid=mid|bit;
			n=n>>1;			
		}
		return mid;
	}
	
	 public static int increase(int n) {
		 //普通计数器
	        int b = 1;
	        while((n|b) == n) {
	            n &= ~b;//将低几位的1复位
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
		 //位逆序二进制计数器,k为二进制的位数
	        int b = 1<<k-1;
	        while(b!=0 && (n|b) == n) {
	            n &= ~b;//将高几位的1复位
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
