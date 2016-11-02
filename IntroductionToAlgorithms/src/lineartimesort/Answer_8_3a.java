package lineartimesort;

import java.util.Arrays;

public class Answer_8_3a {
	//���λ����ͬ�����ֵ�����ʱ�����򣺼�������+��������
	public  int[] sort(int[] a) {
        int maximumDigit = getMaximumDigit(a); // ��(n)
        int[] count = new int[maximumDigit];
        int[] countingSort = countingsort(a,count,maximumDigit); // ��(n)
        
        //����ͬλ�������ֽ��л�������
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
	
	//��������
		private int[] countingsort(int[] a,int[] count,int k){
			//�������ֵ�λ������
			int[] sortedArr = new int[a.length];
			for (int i = 0; i < a.length; i++) { // ��(n) ��getMaximumDigit����ͬ��
		        int currentDigit = getNumDigit(a[i]) - 1; // ���������±��0��ʼ�������±�Ϊ0�Ĵ���1λ���֣��±�Ϊ1�Ĵ���2λ����......
		        count[currentDigit]++;
		    }
			
			int[] nums = count.clone(); // Ϊ�˱���count�����е�Ԫ�ز����ƻ�      ��(count.length) count.length <= n

	        for (int i = 1; i < nums.length; i++) //��(count.length)
	            nums[i] += nums[i - 1];
	        
	        for (int i = a.length - 1; i >= 0; i--) { // ��(n) ��getMaximumDigit����ͬ��
	            int currentDigit = getNumDigit(a[i]) - 1; // ���������±��0��ʼ�������±�Ϊ0�Ĵ���1λ���֣��±�Ϊ1�Ĵ���2λ����......
	            sortedArr[nums[currentDigit] - 1] = a[i];
	            nums[currentDigit]--;
	        }

	        return sortedArr;
		}
	
    /**
     * ��ȡ��������������Ԫ���е����λ��
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
     * ��ȡ������λ��
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
