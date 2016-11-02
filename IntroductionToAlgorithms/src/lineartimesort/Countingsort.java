package lineartimesort;

public class Countingsort {
	//计数排序
	public int[] countingsort(int[] A,int k){
		int[] C=new int[k+1];
		int[] B=new int[A.length];
		for (int i = 0; i <= k; i++) {
			C[i]=0;
		}
		for (int j = 0; j <= A.length-1; j++) {
			C[A[j]]=C[A[j]]+1;
		}
		for (int i = 1; i <= k; i++) {
			C[i]=C[i]+C[i-1];
		}
		

		for (int j = A.length-1; j >= 0; j--) {
			B[C[A[j]]-1]=A[j];
			C[A[j]]=C[A[j]]-1;
		}
		return B;
 /*		原址排序，不是稳定的
		int temp=0,j=0;
		for (int i = 0; i < C.length; i++) {
			if (temp<C[i]) {
				int gap=C[i]-temp;
				temp=C[i];				
				while (gap>0) {
					A[j]=i;
					j++;
					gap--;
				}				
			}			
		}
		return A;*/
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A={2,5,3,0,2,3,0,3};
		int[] B=new int[A.length];
		Countingsort countingsort=new Countingsort();
		B=countingsort.countingsort(A, 5);
		for (int i = 0; i < B.length; i++) {
			System.out.print(B[i]+" ");
		}
	}

}
