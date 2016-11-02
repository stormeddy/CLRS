package insertionsort;

public class InsertionSort{
	//≤Â»Î≈≈–Ú,…˝–Ú
	public void sort(int[] a){
		for (int j = 1; j < a.length; j++) {
			int key=a[j];
			int i=j-1;
			while (i>=0 && a[i]>key) {
				a[i+1]=a[i];
				i=i-1;
			}
			a[i+1]=key;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] old={3,4,2,1,7,5,8};
		//int[] old1={5,2,4,6,1,3};
		InsertionSort iSort=new InsertionSort();
		iSort.sort(old);
		for (int i = 0; i < old.length; i++) {
			System.out.print(old[i]+" ");
		}
		
	}
}