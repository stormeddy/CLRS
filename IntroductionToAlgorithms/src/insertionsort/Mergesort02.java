package insertionsort;

public class Mergesort02 {
	//πÈ≤¢≈≈–ÚΩµ–Ú	
	private void mergeSort(double[] A,int p, int r){
		if (p<r) {
			int q=(int) Math.floor((p+r)/2);
			mergeSort(A, p, q);
			mergeSort(A, q+1, r);
			merge(A, p, q, r);
		}
	}
	
	
	private void merge(double[] A,int p,int q, int r){
		int n1=q-p+1;
		int n2=r-q;
		int i,j,k;
		double[] L=new double[n1+1];
		double[] R=new double[n2+1];
		for (i = 0; i < n1; i++) {
			L[i]=A[p+i];
		}
		for (i = 0; i < n2; i++) {
			R[i]=A[q+i+1];
		}
		L[n1]=Double.MAX_VALUE;
		R[n2]=Double.MAX_VALUE;
		i=0;
		j=0;
		for (k=p; k<=r; k++) {
			if (L[i]<=R[j]) {
				A[k]=L[i];
				i++;
			}else {
				A[k]=R[j];
				j++;
			}
		}
	}
	
	public static void main(String[] args) {
		double[] old={3.1,4.2,2.4,1.5,7.7,5.9,8.0,9.9};
		Mergesort02 ms=new Mergesort02();
		ms.mergeSort(old, 0, old.length-1);
		for (int i = 0; i < old.length; i++) {
			System.out.print(old[i]+" ");
		}
	}
}
