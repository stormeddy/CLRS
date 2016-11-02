package basicdatastructure;

public class StackByQueue {
	//����������ʵ��һ��ջ
	//��ջ�����������У�����һ���ǿն��У���ֵ������ն��У�Ȼ����һ���ǿն��е�ֵ����ȡ�����������������O(n)
	//��ջ��ֱ�ӴӷǿյĶ�����ȡ��O(1)
	public StackByQueue(int n){
		queue1=new Queue(n);
		queue2=new Queue(n);
	}
	
	private Queue queue1;
	private Queue queue2;
	
	public void push(int x) throws Exception{
		if (queue1.isEmpty()) {
			queue1.enqueue(x);
			while (!queue2.isEmpty()) {
				queue1.enqueue(queue2.dequeue());
			}
		}else {
			queue2.enqueue(x);
			while (!queue1.isEmpty()) {
				queue2.enqueue(queue1.dequeue());
			}
		}
	}
	
	public int pop() throws Exception{
		if (!queue1.isEmpty()) {
			return queue1.dequeue();
		}else {
			return queue2.dequeue();
		}
	}
	
	public void print() {
		if (!queue1.isEmpty()) {
			queue1.reverseprint();
		}else {
			queue2.reverseprint();
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		StackByQueue stackByQueue=new StackByQueue(10);
		stackByQueue.push(1);
		stackByQueue.push(3);
		stackByQueue.push(5);
		stackByQueue.print();
		stackByQueue.pop();
		System.out.println();
		stackByQueue.print();
		
	}

}
