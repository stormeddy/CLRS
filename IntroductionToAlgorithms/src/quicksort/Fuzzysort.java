package quicksort;

public class Fuzzysort {
	//模糊排序
	public static void Fuzzy_Sort(int[]A,int[]B,int p,int r){
		if(p<r){
			int[]q = partition(A,B,p,r);
			Fuzzy_Sort(A,B,p,q[0]-1);
//			Insert_sort(A, B, q[0], q[1]);
			Fuzzy_Sort(A,B,q[1]+1,r);
		}
	}
	private static void swap(int[] arr, int j, int k) {
		int temp = arr[j];
		arr[j] = arr[k];
		arr[k] = temp;
	}
	
	public static void Insert_sort(int[]A,int[]B,int p,int r){
		for (int j = p+1; j <=r; j++) {
			int keyA=A[j];
			int keyB=B[j];
			int i=j-1;
			while (i>=p && A[i]>keyA) {
				A[i+1]=A[i];
				B[i+1]=B[i];
				i=i-1;
			}
			A[i+1]=keyA;
			B[i+1]=keyB;
		}
	}
	
	private static int[] partition(int[]A,int[]B,int p,int r){
		//pivot为[a,b]
		int a = A[r];
		int b = B[r];
		for (int j=p;j<=r-1;j++){
			if(A[j]<=b && B[j]>=a){
				if(A[j]>a) a = A[j];
				if(B[j]<b) b = B[j];
			}
		}
	    //以上是求出与pivot重叠的区间的交集
		int i = p-1;
		int k = p-1;
		for(int j=p;j<=r-1;j++){
			if(A[j]<=a && B[j]>=b){
				k++;
				swap(A,j,k);;
				swap(B,j,k);

			}
			else if(B[j]<a){
				k++;
				swap(A,j,k);;
				swap(B,j,k);
				i++;
				swap(A,i,k);
				swap(B,i,k);
			}
		}
		return new int[]{i+1,k};
	}
	public static void main(String[] args) {
		int a[] = new int[]{1,5,3,5,7,8,11,9,10};
		int b[] = new int[]{5,7,4,6,8,10,12,14,13};
		for(int i=0;i<a.length;i++){
			System.out.print("["+a[i]+","+b[i]+"]");
		}
		System.out.println();
		Fuzzy_Sort(a, b, 0, a.length-1);
		Insert_sort(a, b, 0, a.length-1);
		for(int i=0;i<a.length;i++){
			System.out.print("["+a[i]+","+b[i]+"]");
		}
	}
}
