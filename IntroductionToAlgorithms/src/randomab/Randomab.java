package randomab;

import java.util.Random;

public class Randomab {
	
	
	public int randomInteger(int a,int b) {
		int j = 0,count;
		Random random=new Random();
		if (a<=b) {
			j=(int) Math.ceil(Math.log(b-a+1)/Math.log(2));
		}else {
			return -1;
		}
		
		while (true) {
			count=0;
			for (int i = 0; i < j; i++) {
				count+=Math.pow(2, i)*random.nextInt(2);
			}
			if (count<=b-a) {
				break;
			}
		}
		return count+a;
	}
	
	public static void main(String[] args) {
		Randomab randomab=new Randomab();
		for (int i = 0; i < 10; i++) {
			System.out.print(randomab.randomInteger(3, 100)+" ");
		}
		
		
	}
}
