package quicksort;

public class ThreeRandomizedQuicksort {
	public static void quicksort(int[]arr){
		random_quicksort(arr,0,arr.length-1);
	}

	private static void random_quicksort(int[] arr, int p, int r) {
		if(p < r){
			if(r-p>=2){
				int[] q = median_of_three_partition(arr,p,r);
				random_quicksort(arr,p,q[0]-1);
				random_quicksort(arr,q[1]+1,r);
			}
			else{
				if(arr[p]>arr[r]){
					swap(arr,p,r);
				}
			}
		}
	}
	
	//"三数取中"+"重复值加速"
	private static int[] median_of_three_partition(int[] arr, int p, int r) {
		//通过三数取中的方法选取主元，并交换到arr[r]处
		int x = median_of_three(arr,p,r);
		swap(arr,x,r);
		
		int pivot = arr[r];
		int i = p-1;
		int k = p-1;
		int j = p;
		for(j=p;j<=r-1;j++){
			if(arr[j]<pivot){
				k++;
				swap(arr,j,k);
				i++;
				swap(arr,i,k);
			}
			else if(arr[j]==pivot){
				k++;
				swap(arr,j,k);
			}
		}
		k++;
		swap(arr,k,r);
		return new int[]{i+1,k};
	}
	private static void swap(int[] arr, int j, int k) {
		int temp = arr[j];
		arr[j] = arr[k];
		arr[k] = temp;
	}
	private static int median_of_three(int[]arr,int p, int r) {
		RandomGenerator<Integer> generator = new RandomGenerator<Integer>();
		for(int i=p;i<=r;i++){
			generator.addElement(i);
		}
		int first = generator.getElement();
		generator.removeElement(first);
		int second = generator.getElement();
		generator.removeElement(second);
		int third = generator.getElement();
		generator.removeElement(third);
		if((arr[first]<=arr[second] && arr[second]<=arr[third]) || (arr[first]>=arr[second] && arr[second]>=arr[third])){
			return second;
		}
		else if((arr[second]<=arr[first] && arr[first]<=arr[third]) || (arr[second]>=arr[first] && arr[first]>=arr[third])){
			return first;
		}
		else{
			return third;
		}
	}
	public static void main(String[] args) {
		int arr[] = new int[]{100,2,3,4,5,7,2,3,4,51,2,3,4,5,6,76,1};
		System.out.print("输入:");
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
		}
		System.out.println();
		ThreeRandomizedQuicksort.quicksort(arr);
		System.out.print("输出:");
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
		}
	}
}