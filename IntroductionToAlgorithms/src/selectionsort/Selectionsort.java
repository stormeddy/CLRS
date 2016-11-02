package selectionsort;

public class Selectionsort {

	public void sort(int[] a){
		 int n=a.length;
		 int min,tem;
		 for (int j = 0; j < n-1; j++) {
			min=j;
			for (int i = j+1; i < n; i++) {
				if (a[i]<a[min]) {
					min=i;
				}
			}
			tem=a[j];
			a[j]=a[min];
			a[min]=tem;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] old={3,4,2,-1,7,5,8};
		Selectionsort selectionsort=new Selectionsort();
		selectionsort.sort(old);
		for (int i = 0; i < old.length; i++) {
			System.out.print(old[i]+" ");
		}
	}

}
