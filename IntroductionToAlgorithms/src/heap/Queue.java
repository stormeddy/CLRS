package heap;

import java.util.Arrays;

/**
 * ����С��ʵ�ֶ��У����²����Ԫ�ص����ȼ�������
 * @author st
 *
 */
public class Queue {
	private Pair[] arr;
	private int heap_size;			//Ŀǰ�ѵĴ�С
	private int MEMORY_SIZE;		//�ܹ��洢���������С
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
	 * ���Ӷ�:O(1)
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
	 * ���Ӷ� O(lgn)
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
		//�±���СΪ1
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
		if(x>=(int)(MEMORY_SIZE)){	//����������
			MEMORY_SIZE = MEMORY_SIZE * 2;	
			arr = Arrays.copyOf(arr, MEMORY_SIZE);	
			//newarr = Arrays.copyOf(oldarr,newlength);	
			//��ʾ��oldarr��ȫ�����ݸ��Ƶ�newarr�У�newarr�ĳ���Ϊnewlength
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
