package randomab;

import java.util.Random;

public class Random01 {

	public int  biasedRandom() {
		Random random=new Random();
		int i=random.nextInt(3);
		if (i>0) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public int evenRandom() {
		int j1=0,j2=0;
		while (true) {
			j1=biasedRandom();
			j2=biasedRandom();
			if (j1+j2!=1) {
				continue;
			}
			if (j1==1) {
				return 0;
			}else {
				return 1;
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random01 random01=new Random01();
		int a=0,b=0;
		for (int i = 0; i <50000; i++) {
			if (random01.evenRandom()==0) {
				a++;
			}else {
				b++;
			}
		}
		System.out.println(a+" "+b);
	}

}
