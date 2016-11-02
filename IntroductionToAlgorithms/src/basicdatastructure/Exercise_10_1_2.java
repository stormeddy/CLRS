package basicdatastructure;

public class Exercise_10_1_2 {
	
	public static void main(String[] args){
		MyStack s1 = MyStack.getStack1();
		MyStack s2 = MyStack.getStack2();
		try{
			s1.push(1);
			s1.push(2);
			s2.push(1);
			s2.push(2);
//			s2.push(3);
//			s2.push(4);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			MyStack.print();
		}
	}

}
class MyStack{
	private static MyStack s1;
	private static MyStack s2;
	private static int arr[];
	private int top;
	static{
		s1 = new MyStack();
		s2 = new MyStack();
		arr  = new int[5];
		s1.top = -1;
		s2.top = arr.length;
	}
	public static MyStack getStack1(){
		return s1;
	}
	public static MyStack getStack2(){
		return s2;
	}
	public static void print(){
		for(int i=0;i<arr.length;i++)
			System.out.println(arr[i]);
	}
	public void push(int x) throws Exception{
		if(s1.top+1==s2.top) throw new Exception("overflow");
		if(this==s1){
			top++;
		}
		else{
			top--;
		}
		arr[top] = x;
	}
	public int pop() throws Exception{
		if(this==s1){
			if(top==-1) throw new Exception("s1 underflow");
			top--;
			return arr[top+1];
		}
		else{
			if(top==arr.length) throw new Exception("s2 underflow");
			top++;
			return arr[top-1];
		}
	}
}
