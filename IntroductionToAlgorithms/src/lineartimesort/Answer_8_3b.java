package lineartimesort;

import java.util.Arrays;

public class Answer_8_3b {

	public String[] countingsort(String[] A,int k){
		int[] C=new int[k+1];
		int[] D=new int[k+1];
		String[] B=new String[A.length];
		for (int i = 0; i <= k; i++) {
			C[i]=0;
		}
		
		for (int j = 0; j <= A.length-1; j++) {
			if (A[j]==null||A[j].isEmpty()) {
				C[0]=C[0]+1;
			}else {
				C[A[j].toCharArray()[0]-'a'+1]=C[A[j].toCharArray()[0]-'a'+1]+1;
			}			
		}
		
		for (int i = 0; i < C.length; i++) {
			D[i]=C[i];
		}
		
		for (int i = 1; i <= k; i++) {
			C[i]=C[i]+C[i-1];
		}		

		for (int j = A.length-1; j >= 0; j--) {
			if (A[j]==null||A[j].isEmpty()) {
				B[C[0]-1]="";
				C[0]=C[0]-1;
			}else {
				B[C[A[j].toCharArray()[0]-'a'+1]-1]=A[j];
				C[A[j].toCharArray()[0]-'a'+1]=C[A[j].toCharArray()[0]-'a'+1]-1;
			}			
		}
		
		for (int i = 0,m=0; i < D.length; m=m+D[i],i++) {
			if (D[i]!=0 && !(B[m]==null||B[m].isEmpty())) {
				String[] E=new String[D[i]];
				String common=B[m].substring(0, 1);
				for (int j = 0; j < E.length; j++) {
					E[j]=B[m+j].substring(1, B[m+j].length());
					
				}
				E=countingsort(E, k);
				for (int j = 0; j < E.length; j++) {
					B[m+j]=common+E[j];
				}
			}
			
		}
		return B;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] a={"dota","storm","zabab","zaba","panda","sosgmlsjs","lol","abd","abe","ab","stormy"};
//		System.out.println(a[3]);
//		String b=a[3];
//		char[] c=b.toCharArray();
//		System.out.println(c[4]);
		Answer_8_3b exercise_8_3b=new Answer_8_3b();
		
		
		System.out.println(Arrays.toString(exercise_8_3b.countingsort(a, 26)));
	}

}
