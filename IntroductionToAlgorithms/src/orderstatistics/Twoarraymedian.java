package orderstatistics;

public class Twoarraymedian {
	//O(lgn)ʱ���ڴ������ֱ���n��Ԫ�ص��������ҳ���λ��
	public static int search(int[]a,int[]b,int low,int high) {
		int i;
		if ((i=searchMedian(a, b, low, high))==Integer.MIN_VALUE) {
			return search(b, a, low, high);
		}else {
			return i;
		}
	}
	
	public static int searchMedian(int[]a,int[]b,int low,int high){
        int n=a.length;
        while(low <= high)
    {
        int middle = (low + high)/2;
        //����߽�
        if(middle == n - 1){
            if(a[middle] <= b[0])
                return a[middle];
            else//a[middle] > b[0] ˵����λ��һ��������b��
                return Integer.MIN_VALUE;
        }   
         
        //�Ǳ߽�
        else if(middle < n - 1)
        {
            if(a[middle] <= b[n - middle - 1] && a[middle] >= b[n - middle -2])
            {
                return a[middle];
            }
            else if(a[middle] >= b[n - middle - 1])
            {
                high = middle - 1;
            }
            else
            {
                low = middle + 1;
            }
        }
    }
    return Integer.MIN_VALUE;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a={1,3,7,8,10};
		int[] b={2,4,5,6,9};
		System.out.println(Twoarraymedian.search(a, b, 0, a.length));
	}

}
