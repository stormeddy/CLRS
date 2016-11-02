package heap;

import java.util.Arrays;

/**
 * 用最小堆实现队列，并新插入的元素的优先级是最大的
 * @author st
 *
 */
public class Queue {
	private Pair[] arr;
	private int heap_size;			//目前堆的大小
	private int MEMORY_SIZE;		//能够存储的总数组大小
	private long max_priority = 0L;
	public static void main(String[] args)throws Exception {
		Queue q = new Queue();
		for(int i=100;i>0;i--)
			q.ENQUEUE(i);
		for(int i=0;i<100;i++)
			System.out.println(q.DEQUEUE());
	}
	
	public Queue() {
		heap_size = 0;
		MEMORY_SIZE = 1024;
		arr = new Pair[MEMORY_SIZE];
	}
	/**
	 * 复杂度:O(1)
	 * @param k
	 */
	public void ENQUEUE(int k){
		max_priority = max_priority+1L;
		Pair p = new Pair(max_priority,k);
		INSERT(p);
	}
	private void INSERT(Pair p) {
		heap_size++;
		ensureCapacity(heap_size+1);
		arr[heap_size] = p;
	}
	/**
	 * 复杂度 O(lgn)
	 * @return
	 * @throws Exception
	 */
	public int DEQUEUE() throws Exception{
		if(heap_size==0) throw new Exception("empty");
		int min = EXTRACT_MIN();
		return min;
	}
	private int EXTRACT_MIN() {
		Pair MIN = arr[1];
		arr[1] = arr[heap_size];
		heap_size--;
		//下标最小为1
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
class Pair{
	long priority;
	int value;
	public Pair(long priority, int value) {
		this.priority = priority;
		this.value = value;
	}
	public Pair() {}
	
}
