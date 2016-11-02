package maximumsubarray;

public class Boundsum {
	
	public Boundsum(int low,int high,int sum){
		this.low=low;
		this.high=high;
		this.sum=sum;
	
	}
	
	private int low,high,sum;
	
	public int getLow() {
		return low;
	}
	
	public int getHigh() {
		return high;
	}

	public int getSum() {
		return sum;
	}
}
