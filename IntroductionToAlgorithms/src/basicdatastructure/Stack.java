package basicdatastructure;

public class Stack {

	public Stack(int n){
		arr=new int[n];
	}
	
	private int[] arr;
	private int top=-1;
	
	
	public void push(int x) {
		top++;
		arr[top]=x;
	}
	
	public boolean isEmpty() {
		if (top==-1) {
			return true;
		}else {
			return false;
		}
	}
	
	public int pop() throws Exception {
		if (this.isEmpty()) {
			throw new Exception("underflow");
		}else {
			top--;
			return arr[top+1];
		}
	}
	
	public void print() {
		for (int i = 0; i <= top; i++) {
			System.out.print(arr[i]+" ");
		}
	}
	
	public void reverseprint() {
		for (int i = top; i >= 0; i--) {
			System.out.print(arr[i]+" ");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stack stack=new Stack(10);
		
		try {
			stack.push(3);
			stack.push(6);
			stack.print();
			stack.pop();
			System.out.println();
			stack.print();
			stack.push(8);
			System.out.println();
			stack.print();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
