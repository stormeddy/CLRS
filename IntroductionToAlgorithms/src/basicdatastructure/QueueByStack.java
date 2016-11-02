package basicdatastructure;

public class QueueByStack {
	//用两个栈实现一个队列
/*	ENQUEUE(Q,x)    //O(n)
	while(!s2.empty()) push(s1,pop(s2));
	push(s1,x);

	DEQUEUE(Q)    //O(n)
	while(!s1.empty())	push(s2,pop(s1));
	return pop(s2);*/
	
	public QueueByStack(int n) {
		stack1=new Stack(n);
		stack2=new Stack(n);
	}
	
	private Stack stack1;
	private Stack stack2;
	
	public void enqueue(int x) throws Exception {
		while (!stack2.isEmpty()) {
			stack1.push(stack2.pop());
		}
		stack1.push(x);
	}
	
	public int dequeue() throws Exception {
		while (!stack1.isEmpty()) {
			stack2.push(stack1.pop());			
		}
		return stack2.pop();
	}
	
	public void print() {
		if (stack1.isEmpty()) {
			//stack2要倒序输出
			stack2.reverseprint();
		}else {
			stack1.print();
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		QueueByStack queueByStack=new QueueByStack(10);
		queueByStack.enqueue(2);
		queueByStack.enqueue(4);
		queueByStack.enqueue(6);
		queueByStack.print();
		queueByStack.dequeue();
		System.out.println();
		queueByStack.print();
		queueByStack.enqueue(8);
		System.out.println();
		queueByStack.print();
	}

}
