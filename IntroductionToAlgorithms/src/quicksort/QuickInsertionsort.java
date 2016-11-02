package quicksort;

public class QuickInsertionsort {
	//快速插入排序,k为划分因子
	public void insertionsort(int[] arr,int p,int r){
		for (int j = p+1; j <= r; j++) {
			int key=arr[j];
			int i=j-1;
			while (i>=0 && arr[i]>key) {
				arr[i+1]=arr[i];
				i=i-1;
			}
			arr[i+1]=key;
		}
	}
	
	public void quicksort(int[] arr,int p,int r,int k) {
		if (r-p+1<k) {
			int q=partition(arr, p, r);
			quicksort(arr, p, q-1,k);
			quicksort(arr, q+1, r,k);			
		}
	}
	
	public int partition(int[] arr,int p,int r) {
		int pivot=arr[r];
		int i=p-1;
		for (int j = p; j <= r-1; j++) {
			if (arr[j]<=pivot) {
				i++;
				swap(arr, i, j);				
			}
		}
		i++;
		swap(arr, i, r);
		return i;
	}
	
	private void swap(int[] arr,int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	
	public void sort(int[] arr,int p,int r,int k) {
		quicksort(arr, p, r, k);
		insertionsort(arr, p, r);
	}
	public static void main(String[] args) {
		int[] arr={1,3,6,2,4,9,5,7,8,10,14,13,12,11,19};
		QuickInsertionsort quickInsertionsort=new QuickInsertionsort();
		quickInsertionsort.sort(arr, 0, arr.length-1,4);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i]+" ");
		}
	}
}
