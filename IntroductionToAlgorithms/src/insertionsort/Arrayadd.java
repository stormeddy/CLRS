package insertionsort;

public class Arrayadd {
	//二进制加法
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = { 1, 0, 1, 1, 1, 1 };
		int[] b = { 1, 1, 0, 1, 0, 1 };
		int[] c ;
		BinaryAdd binaryAdd = new BinaryAdd();
		c = binaryAdd.binaryArrayAdd(a, b);
		System.out.print("  ");
		for (int i = 0; i < a.length; i++) {
			System.out.print(" "+a[i]);
		}
		System.out.print("\n");
		System.out.print(" +");
		for (int i = 0; i < b.length; i++) {
			System.out.print(" "+b[i]);
		}
		System.out.print("\n");
		for (int i = 0; i < c.length; i++) {
			System.out.print(" "+c[i]);
		}
	}

}

class BinaryAdd {
	public int[] binaryArrayAdd(int[] a, int[] b) {
		int clength = a.length + 1;
		int[] c = new int[clength];
		int[] cin=new int[clength];
		SingleArrayAdd singleArrayAdd = new SingleArrayAdd();

		for (int i = c.length - 1; i >= 0; i--) {
			if (i == c.length - 1) {
				c[i] = singleArrayAdd.Sum(a[i - 1], b[i - 1],cin[i]);
			} else if (i < c.length - 1 && i > 0) {
				cin[i]=singleArrayAdd.Cout(a[i], b[i], cin[i+1]);
				c[i] = singleArrayAdd.Sum(a[i - 1], b[i - 1],cin[i]);
			} else if (i == 0) {
				cin[i]=singleArrayAdd.Cout(a[i], b[i], cin[i+1]);
				c[i] = singleArrayAdd.Sum(0, 0, cin[i]);
			}
		}
		return c;
	}
}

class SingleArrayAdd {
	public int Sum(int a, int b,int cin) {
		// TODO Auto-generated constructor stub
		int s = a^b^cin;
		return s;
	}

	public int Cout(int a, int b,int cin) {
		int cout = ((a^b)&cin)|(a&b);		
		return cout;
	}
}
