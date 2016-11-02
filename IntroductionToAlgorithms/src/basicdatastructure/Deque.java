package basicdatastructure;

public class Deque {
	//双端队列
/*	addFirst(Q,x)
	if((tail[Q]+1)%length[Q]==head[Q])
		throw  "overflow";
	else if(head[Q]==tail[Q])	//如果队列为空
	addLast(Q,x);
	else		
		head[Q] = (head[Q]-1)%length[Q];
		Q[head[Q]] = x;
		
	addLast(Q,x)
	if((tail[Q]+1)%length[Q]==head[Q])
		throw  "overflow";
	else		
		Q[tail[Q]] = x;		
		tail[Q] = (tail[Q]+1)%length[Q];

	removeLast(Q)
	if(head[Q]==tail[Q])
		throw "underflow";
	else
		tail[Q] = (tail[Q]-1)%length[Q];
		return Q[tail[Q]];

	removeFirst(Q)
	if(head[Q]==tail[Q])
		throw "underflow";
	else
		x = Q[head[Q]];
		head[Q] = (head[Q]+1)%length[Q];
		return x;*/
	public Deque(int n){
		arr=new int[n];
	}
	
	private int[] arr;
	private int head=0,tail=0;
	
	public void addFirst(int x) throws Exception {
		if ((tail+1)%arr.length==head) {
			throw new Exception("overflow");
		}else {
			head=(head-1+arr.length)%arr.length;
			arr[head]=x;
		}		
	}
	
	public void addLast(int x) throws Exception {
		if ((tail+1)%arr.length==head) {
			throw new Exception("overflow");
		}else {
			arr[tail]=x;
			tail=(tail+1)%arr.length;
		}		
	}	
	
	public int removeFirst() throws Exception {
		if (tail==head) {
			throw new Exception("underflow");
		}else {
			int x=arr[head];
			head=(head+1)%arr.length;
			return x;
		}
	}
	
	public int removeLast() throws Exception {
		if (tail==head) {
			throw new Exception("underflow");
		}else {
			tail=(tail-1+arr.length)%arr.length;
			return arr[tail];
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
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		System.out.println(-1%10);
		Deque deque=new Deque(10);
		deque.addFirst(1);
		deque.addFirst(2);
		deque.addLast(3);
		deque.print();
		deque.removeFirst();
		System.out.println();
		deque.print();
		deque.removeLast();
		System.out.println();
		deque.print();
	}
}
