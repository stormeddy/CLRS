package threegate;

import java.util.Random;

public class Test {
	static int swap=0;
	static int nswap=0;
	static double total=0;
	public static void threegate(int count){
		boolean[] gate={false,false,true};
		Random random=new Random();
		for (int i = 0; i < count; i++) {
			int j=random.nextInt(gate.length);
			if (j!=gate.length-1) {
				swap++;
			}else {
				int k=random.nextInt(gate.length-1);
				nswap++;
			}
		}
		total=swap+nswap;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		threegate(100000000);
		System.out.println("swap number:"+swap);
		System.out.println("swap rate:"+swap/total);
	}

}
