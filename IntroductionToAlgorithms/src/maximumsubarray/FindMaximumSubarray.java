package maximumsubarray;

public class FindMaximumSubarray {
	//O(nlgn)
	public Boundsum findMaxCrossingSubarray(int[] A,int low,int mid,int high) {
		int leftSum,rightSum,sum;
		int maxLeft = 0,maxRight = 0;
		leftSum=rightSum=Integer.MIN_VALUE;
		sum=0;
		for (int i = mid; i >=low; i--) {
			sum=sum+A[i];
			if (sum>leftSum) {
				leftSum=sum;
				maxLeft=i;
			}
		}
		sum=0;
		for(int j=mid+1;j<=high;j++){
			sum=sum+A[j];
			if (sum>rightSum) {
				rightSum=sum;
				maxRight=j;
			}
		}
		return new Boundsum(maxLeft, maxRight, leftSum+rightSum);
		
	}
	
	public Boundsum findMaximumSubarray(int[] A,int low,int high) {
		if (low==high) {
			return new Boundsum(low, high, A[low]);
		}
		else {
			int mid=(low+high)/2;
			Boundsum boundsumLeft=findMaximumSubarray(A, low, mid);
			Boundsum boundsumRight=findMaximumSubarray(A, mid+1, high);
			Boundsum boundsumCross=findMaxCrossingSubarray(A, low, mid, high);
			if (boundsumLeft.getSum()>=boundsumRight.getSum() && boundsumLeft.getSum()>=boundsumCross.getSum()) {
				return boundsumLeft;
			}else if (boundsumRight.getSum()>=boundsumLeft.getSum() && boundsumRight.getSum()>=boundsumCross.getSum()) {
				return boundsumRight;
			}else {
				return boundsumCross;
			}
		}
	}
	
	public Boundsum quadraticFindMaximumSubarray(int[] A) {
		//O(n^2)
		int low=0,high=0;
		int sum=A[0];
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j <= i; j++) {
				int totalSum=0;
				for (int k = j; k <= i; k++) {
					totalSum=totalSum+A[k];					
				}
				if (totalSum>sum) {
					sum=totalSum;
					low=j;
					high=i;
				}
			}
		}
		return new Boundsum(low, high, sum);
	}
	
	public Boundsum linearFindMaximumSubarray(int[] A) {
		//O(n)
		int low=0,high=0;
		int sum=A[0],cursum=A[0];
		for (int i = 0; i < A.length; i++) {
			if (cursum<0) {
				cursum=0;
				low=i;
			}
			cursum=cursum+A[i];
			if (cursum>sum) {
				sum=cursum;
				high=i;				
			}
		}
		return new Boundsum(low, high, sum);
	}
	
	public static void main(String[] args) {
		int[] A={100,113,110,85,105,102,86,63,81,101,94,106,101,79,94,90,97};
		int[] B=new int[A.length-1];
		for (int i = 0; i < B.length; i++) {
			B[i]=A[i+1]-A[i];
		}
		FindMaximumSubarray f=new FindMaximumSubarray();
		Boundsum boundsum1=f.findMaximumSubarray(B, 0, B.length-1);
		int low=boundsum1.getLow(),high=boundsum1.getHigh()+1,sum=boundsum1.getSum();		
		System.out.println("maximum subarray low bound:"+low);
		System.out.println("maximum subarray high bound:"+high);
		System.out.println("maximum subarray profit:"+sum);
		
		Boundsum boundsum2=f.quadraticFindMaximumSubarray(B);
		low=boundsum2.getLow();
		high=boundsum2.getHigh()+1;
		sum=boundsum2.getSum();
		System.out.println("maximum subarray low bound:"+low);
		System.out.println("maximum subarray high bound:"+high);	
		System.out.println("maximum subarray profit:"+sum);
		
		Boundsum boundsum3=f.linearFindMaximumSubarray(B);
		low=boundsum3.getLow();
		high=boundsum3.getHigh()+1;
		sum=boundsum3.getSum();
		System.out.println("maximum subarray low bound:"+low);
		System.out.println("maximum subarray high bound:"+high);	
		System.out.println("maximum subarray profit:"+sum);
	}
	
}
