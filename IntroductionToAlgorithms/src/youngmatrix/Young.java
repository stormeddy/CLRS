package youngmatrix;

public class Young {
	private static int MAX = Integer.MAX_VALUE;
	private int arr[][];
	private int m;
	private int n;
	public Young(int arr[][]){
		this.arr = arr;
		m = arr.length;
		n = arr[0].length;
	}
	public static void main(String[] args) {
		int arr[][] = {{2,3,5,12},{4,8,14,MAX},{9,16,MAX,MAX},{MAX,MAX,MAX,MAX}};
		Young young = new Young(arr);
		young.printTable();
		
		int min = young.EXTRACT_MIN();
		System.out.println("min="+min);
		young.printTable();
		
		young.INSERT(10);
		young.printTable();
		
		Point p = young.find(12);
		System.out.println(p);
		young.printTable();
		
		young.SORT();
		young.printTable();
		

	}
	/**
	 * 寻找在Young表中是否存在k
	 * @param k
	 * @return
	 */
	public Point find(int k){
		int x = m-1;
		int y = 0;
		while(x>=0&&y<n){
			if(arr[x][y]>k) x--;
			else if(arr[x][y]<k) y++;
			else if(arr[x][y]==k){
				Point p = new Point();
				p.x = x;
				p.y = y;
				return p;
			}
		}
		return new Point(-1,-1);
		
	}
	/**
	 * 在O(n^3)进行对Young排序
	 */
	public void SORT(){
		for(int i=0;i<m*n;i++){
			int min = EXTRACT_MIN();
			if(min==MAX){
				System.out.print("∞ ");
			}
			else
				System.out.print(min+" ");
		}
	}
	/**
	 * 向Young表插入k
	 * @param k
	 */
	public void INSERT(int k){
		arr[m-1][n-1] = k;
		MAX_HEAPIFY(m-1,n-1);
	}
	/**
	 * 在Young表中取出最小值A[1][1]
	 * @return
	 */
	public int EXTRACT_MIN() {
		int min = arr[0][0];
		arr[0][0] = arr[m-1][n-1];
		arr[m-1][n-1] = MAX;
		MIN_HEAPIFY(0,0);
		return min;
	}
	private void MIN_HEAPIFY(int x, int y) {
		//从左上角开始,看作最小堆
		int smallestx = x;
		int smallesty = y;
		if(x+1<m && arr[smallestx][smallesty]>arr[x+1][y]){
			smallestx = x+1;
			smallesty = y;
		}
		if(y+1<n && arr[smallestx][smallesty]>arr[x][y+1]){
			smallestx = x;
			smallesty = y+1;
		}
		if(smallestx!=x||smallesty!=y){
			swap(smallestx,smallesty,x,y);
			MIN_HEAPIFY(smallestx,smallesty);
		}
	}
	
	private void MAX_HEAPIFY(int i, int j) {
		//从右下角开始,看作最大堆
		int largestx = i;
		int largesty = j;
		if(i-1>=0 && arr[i-1][j]>arr[largestx][largesty]){
			largestx = i-1;
			largesty = j;
		}
		if(j-1>=0 && arr[i][j-1]>arr[largestx][largesty]){
			largestx = i;
			largesty = j-1;
		}
		if(largestx!=i|| largesty !=j){
			swap(largestx, largesty	, i	, j);
			MAX_HEAPIFY(largestx, largesty);
		}
	}
	
	public void printTable(){
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				if(arr[i][j]==MAX){
					System.out.print("∞ ");
				}
				else System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	private void swap(int smallestx, int smallesty, int x, int y) {
		int temp = arr[x][y];
		arr[x][y] = arr[smallestx][smallesty];
		arr[smallestx][smallesty] = temp;
	}
}
class Point{
	int x;
	int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Point() {}
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
}