package heap;

public class MaxHeap {
	//最大堆
	private int[] heap;
	private int heapSize;
	public MaxHeap(int[] arr){
		heap=arr;
		heapSize=arr.length;
	}
	
	private int Parent(int i) {
		return (i+1)/2-1;
	}
	
	private int Left(int i){
		return 2*i+1;
	}
	
	private int Right(int i){
		return 2*i+2;
	}
	
	private void swap(int[] arr, int x, int y) {
		int tmp = arr[x];
		arr[x] = arr[y];
		arr[y] = tmp;
	}
	
	public void MaxHeapify(int[] arr,int i){
		//维护最大堆
		int l=Left(i);
		int r=Right(i);
		int largest;
		
		if (l<heapSize && arr[l]>arr[i]) {
			largest=l;
		}else {
			largest=i;
		}
		if (r<heapSize && arr[r]>arr[largest]) {
			largest=r;
		}
		if (largest!=i) {
			swap(arr, i, largest);
			MaxHeapify(arr, largest);
		}
	}
	
	public void BuildMaxHeap(int[] arr) {
		for (int i = arr.length/2-1; i >= 0; i--) {
			MaxHeapify(arr, i);
		}
	}
	
	public void HeapSort(int[] arr){
		BuildMaxHeap(arr);
		for (int i = arr.length-1; i >=1 ; i--) {
			swap(arr, 0, i);
			heapSize--;
			MaxHeapify(arr, 0);
		}
	}
	
	public int HeapExtractMax(int[] arr) {
		if (heapSize<1) {
			try {
				throw new Exception("heap underflow");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int max=arr[0];
		arr[1]=arr[heapSize];
		heapSize--;
		MaxHeapify(arr, 1);
		return max;
	}
	
	public void HeapIncreaseKey(int[] arr,int i,int key) {
		if (key < arr[i]) {
			try {
				throw new Exception("new key is smaller than current key");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		arr[i]=key;
		while (i>0 && arr[Parent(i)]<arr[i]) {
			swap(arr, i, Parent(i));
			i=Parent(i);
		}
	}
	
	public int[] MaxHeapInsert(int[] arr, int key){
		//heapSize++;
		int[] array =new int[arr.length+1];
		for (int i = 0; i < arr.length; i++) {
			array[i]=arr[i];
		}
		array[arr.length]=Integer.MIN_VALUE;
		HeapIncreaseKey(array, arr.length, key);
		return array;
	}
	
	
	public void HeapDelete(int[] arr,int i){
		arr[i]=arr[arr.length-1];
		//heapSize--;
		MaxHeapify(arr, i);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A={4,1,3,2,16,9,10,14,8,7};
		MaxHeap maxHeap=new MaxHeap(A);
		maxHeap.BuildMaxHeap(A);
		for (int i = 0; i < A.length; i++) {
			System.out.print(A[i]+" " );
		}
		
		System.out.println();
		int[] array=maxHeap.MaxHeapInsert(A, 30);
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i]+" " );
		}
				
		System.out.println();
		maxHeap.HeapDelete(A, 3);
		for (int i = 0; i < A.length; i++) {
			System.out.print(A[i]+" " );
		}
		
		System.out.println();
		maxHeap.HeapSort(A);
		for (int i = 0; i < A.length; i++) {
			System.out.print(A[i]+" " );
		}
	}

}
