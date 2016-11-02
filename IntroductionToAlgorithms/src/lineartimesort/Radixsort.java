package lineartimesort;

public class Radixsort {
	//基数排序
	public int[][] countingsort(int[][] A,int j,int k){
		//按A的第j列进行计数排序
		int[] C=new int[k+1];
		int[][] B=new int[A.length][A[0].length];
		for (int i = 0; i <= k; i++) {
			C[i]=0;
		}
		for (int i = 0; i < A.length; i++) {
			C[A[i][j]]=C[A[i][j]]+1;
		}
		for (int i = 1; i <= k; i++) {
			C[i]=C[i]+C[i-1];
		}
		for (int i = A.length-1; i >= 0; i--) {
			
			B[C[A[i][j]]-1]=A[i];
			C[A[i][j]]=C[A[i][j]]-1;
		}
		return B;
	}
	
	public int[][] radixsort(int[] arr,int d){
		int[][] array=new int[arr.length][d];
		array=integerToArray(arr, d);
		for (int i = d-1; i >= 0; i--) {
			array=countingsort(array,i,9);
		}
		return array;
	}
	
	public int[][] integerToArray(int[] arr,int d) {
		//将十进制整数拆分成每一位数字，以二维数组保存,高位在前，低位在后
		//d为数字的最大位数
		int[][] array=new int[arr.length][d];
		for (int i = 0; i < arr.length; i++) {
			int x=arr[i],j=d-1;
			while (x>0) {
				array[i][j]=x%10;
				x=x/10;
				j--;
			}
		}
		return array;
	}
	
	public int[] arrayToInteger(int[][] array) {
		int[] arr=new int[array.length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				arr[i]=arr[i]*10+array[i][j];
			}
		}
		return arr;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] A={329,457,657,839,436,720,355};
		int d=3;
		int[][] B=new int[A.length][d];
		Radixsort radixsort=new Radixsort();
		B=radixsort.integerToArray(A, d);
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < d; j++) {
				System.out.print(B[i][j]+" ");
			}
			System.out.println();
		}
		B=radixsort.radixsort(A, d);
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < d; j++) {
				System.out.print(B[i][j]+" ");
			}
			System.out.println();
		}
		int[] C=new int[A.length];
		C=radixsort.arrayToInteger(B);
		for (int i = 0; i < C.length; i++) {
			System.out.print(C[i]+" ");
		}
	}

}
