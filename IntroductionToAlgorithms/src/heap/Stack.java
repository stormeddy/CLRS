package heap;

import java.util.Arrays;

/**
 * ����С��ʵ��ջ���²����Ԫ�ص����ȼ�����С�ģ���˲�����ŵ��ѵĸ�
 * @author xiazdong
 *
 */
public class Stack {
	private Pair[] arr;
	private int heap_size;			//Ŀǰ�ѵĴ�С
	private int MEMORY_SIZE;		//�ܹ��洢���������С
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
			System.err.println("����ҪDECREASE");
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
		if(x>=(int)(MEMORY_SIZE)){	//����������
			MEMORY_SIZE = MEMORY_SIZE * 2;	
			arr = Arrays.copyOf(arr, MEMORY_SIZE);	
			//newarr = Arrays.copyOf(oldarr,newlength);	
			//��ʾ��oldarr��ȫ�����ݸ��Ƶ�newarr�У�newarr�ĳ���Ϊnewlength
		}
	}
}