package lineartimesort;

import java.util.Arrays;

public class Answer_8_3a {
	//针对位数不同的数字的线性时间排序：计数排序+基数排序
	public  int[] sort(int[] a) {
        int maximumDigit = getMaximumDigit(a); // θ(n)
        int[] count = new int[maximumDigit];
        int[] countingSort = countingsort(a,count,maximumDigit); // θ(n)
        
        //对相同位数的数字进行基数排序
        Radixsort radixsort=new Radixsort();
        int num=0;
        for (int i = 0; i < count.length; i++) {        	
			int[] temp=new int[count[i]];			
			for (int j = 0; j < temp.length; j++) {
				temp[j]=countingSort[j+num];
			}
			temp=radixsort.arrayToInteger(radixsort.radixsort(temp, i+1));
			for (int j = 0; j < temp.length; j++) {
				countingSort[j+num]=temp[j];
			}
			num=num+count[i];
		}
        
        return countingSort;
    }
	
	//计数排序
		private int[] countingsort(int[] a,int[] count,int k){
			//根据数字的位数排序
			int[] sortedArr = new int[a.length];
			for (int i = 0; i < a.length; i++) { // θ(n) 和getMaximumDigit方法同理
		        int currentDigit = getNumDigit(a[i]) - 1; // 由于数组下标从0开始，所以下标为0的代表1位数字，下标为1的代表2位数字......
		        count[currentDigit]++;
		    }
			
			int[] nums = count.clone(); // 为了保留count数组中的元素不被破坏      θ(count.length) count.length <= n

	        for (int i = 1; i < nums.length; i++) //θ(count.length)
	            nums[i] += nums[i - 1];
	        
	        for (int i = a.length - 1; i >= 0; i--) { // θ(n) 和getMaximumDigit方法同理
	            int currentDigit = getNumDigit(a[i]) - 1; // 由于数组下标从0开始，所以下标为0的代表1位数字，下标为1的代表2位数字......
	            sortedArr[nums[currentDigit] - 1] = a[i];
	            nums[currentDigit]--;
	        }

	        return sortedArr;
		}
	
    /**
     * 获取给定数组中所有元素中的最大位数
     */
    public int getMaximumDigit(int[] a) {
        int largest = getNumDigit(a[0]);

        for (int i = 1; i < a.length; i++) {
            int currentDigit = getNumDigit(a[i]);
            if (currentDigit > largest)
                largest = currentDigit;
        }

        return largest;
    }

    /**
     * 获取整数的位数
     */
    private int getNumDigit(int num) {
        int digit = 0;

        while (num > 0) {
            digit++;
            num /= 10;
        }

        return digit;
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 int[] a = { 133, 2, 433, 124, 3432434, 2322, 2345, 1, 231, 12, 45, 84, 21, 3457, 132356, 12, 5, 67773, 233 };
		 Answer_8_3a answer_8_3=new Answer_8_3a();
		 
		 System.out.println(Arrays.toString(answer_8_3.sort(a)));
		
	}

}
