package basicdatastructure;

public class Queue {
	
	public Queue(int n){
		arr=new int[n];
	}
	
	private int[] arr;
	private int head=0,tail=0;
	
	public void enqueue(int x) throws Exception {
		if ((tail+1)%arr.length==head) {
			throw new Exception("overflow");
		}else {
			arr[tail]=x;
			tail=(tail+1)%arr.length;
		}		
	}
	
	public int dequeue() throws Exception {
		if (tail==head) {
			throw new Exception("underflow");
		}else {
			int x=arr[head];
			head=(head+1)%arr.length;
			return x;
		}
	}
	
	public boolean isEmpty() {
		if (head==tail) {
			return true;
		}else {
			return false;
		}
	}
	
	public void print() {		
		if (head<=tail) {
			//head==tail,队列为空
			for (int i=head; i < tail; i++) {
				System.out.print(arr[i]+" ");
			}
		}else {
			for (int i = head; i < arr.length; i++) {
				System.out.print(arr[i]+" ");
			}
			for (int i = 0; i < tail; i++) {
				System.out.print(arr[i]+" ");
			}
		}		
	}
	
	
	public void reverseprint() {
		if (head<=tail) {
			//head==tail,队列为空
			for (int i=tail-1; i >= head; i--) {
				System.out.print(arr[i]+" ");
			}
		}else {
			for (int i = tail-1; i >= 0; i--) {
				System.out.print(arr[i]+" ");
			}
			for (int i = arr.length-1; i >= head; i--) {
				System.out.print(arr[i]+" ");
			}
		}
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Queue queue=new Queue(10);
		queue.enqueue(3);
		queue.enqueue(5);
		queue.enqueue(7);
		queue.enqueue(9);
		queue.print();
		queue.dequeue();
		System.out.println();
		queue.print();
	}

}
