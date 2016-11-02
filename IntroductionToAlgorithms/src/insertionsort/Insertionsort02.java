package insertionsort;

public class Insertionsort02 {
	//≤Â»Î≈≈–ÚΩµ–Ú
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] old={3,4,2,1,7,5,8};
		//int[] old1={5,2,4,6,1,3};
		InsertionSortReverse iSort=new InsertionSortReverse();
		iSort.sort(old);
		for (int i = 0; i < old.length; i++) {
			System.out.print(old[i]+" ");
		}
		
	}
	
}
class InsertionSortReverse{
	public void sort(int[] a){
		for (int j = 1; j < a.length; j++) {
			int key=a[j];
			int i=j-1;
			while (i>=0 && a[i]<key) {
				a[i+1]=a[i];
				i=i-1;
			}
			a[i+1]=key;
		}
	}
}