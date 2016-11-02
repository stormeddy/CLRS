package heap;

import java.util.Arrays;

/**
 * 用最小堆实现栈，新插入的元素的优先级是最小的，因此插入后会放到堆的根
 * @author xiazdong
 *
 */
public class Stack {
	private Pair[] arr;
	private int heap_size;			//目前堆的大小
	private int MEMORY_SIZE;		//能够存储的总数组大小
	private long min_priority = 0;
	public Stack() {
		heap_size = 0;
		MEMORY_SIZE = 1024;
		arr = new Pair[MEMORY_SIZE];
	}
	public static void main(String[] args)throws Exception {
		Stack stack = new Stack();
		for(int i=0;i<100;i++)
			stack.PUSH(i);
		for(int i=0;i<100;i++){
			System.out.print(stack.POP()+" ");
		}
	}
	public void PUSH(int k){
		min_priority = min_priority-1;
		Pair p = new Pair(min_priority,k);
		INSERT(p);
	}
	private void INSERT(Pair p) {
		heap_size++;
		ensureCapacity(heap_size+1);
		arr[heap_size] = new Pair(1, p.value);
		DECREASE_KEY(heap_size,p.priority);
		
	}
	
	private void DECREASE_KEY(int x, long k) {
		
		if(arr[x].priority<k){
			System.err.println("不需要DECREASE");
			return;
		}
		arr[x].priority = k;
		
		while(x>1&&arr[x/2].priority>arr[x].priority){
				swap(x/2,x);
				x = x/2;
		}
		
	}
	public int POP() throws Exception{
		if(heap_size==0) throw new Exception("empty");
		int min = EXTRACT_MIN();
		return min;
	}
	private int EXTRACT_MIN() {
		Pair MIN = arr[1];
		arr[1] = arr[heap_size];
		heap_size--;
		MIN_HEAPIFY(1);
		return MIN.value;
	}
	private void MIN_HEAPIFY(int x) {
		int smallest = x;
		if(2*x<=heap_size&&arr[2*x].priority<arr[smallest].priority){
			smallest = 2*x;
		}
		if((2*x+1)<=heap_size&&arr[2*x+1].priority<arr[smallest].priority){
			smallest = 2*x+1;
		}
		if(smallest!=x){
			swap(smallest,x);
			MIN_HEAPIFY(smallest);
		}
	}
	private void swap(int smallest, int x) {
		Pair temp = arr[smallest];
		arr[smallest] = arr[x];
		arr[x] = temp;
	}
	private void ensureCapacity(int x){
		if(x>=(int)(MEMORY_SIZE)){	//自增长数组
			MEMORY_SIZE = MEMORY_SIZE * 2;	
			arr = Arrays.copyOf(arr, MEMORY_SIZE);	
			//newarr = Arrays.copyOf(oldarr,newlength);	
			//表示将oldarr的全部内容复制到newarr中，newarr的长度为newlength
		}
	}
}