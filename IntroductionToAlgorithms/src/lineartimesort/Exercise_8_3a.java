package lineartimesort;

import java.util.Arrays;

public class Exercise_8_3a {

    public static void main(String[] args) {
        int[] a = { 133, 2, 433, 124, 3432434, 2322, 2345, 1, 231, 12, 45, 84, 21, 3457, 132356, 12, 5, 67773, 233 };

        System.out.println(Arrays.toString(Exercise_8_3a.sort(a)));
    }

    public static int[] sort(int[] a) {
        int maximumDigit = getMaximumDigit(a); // ��(n)
        int[] count = new int[maximumDigit];

        int[] countingSort = countingSort(a, count); // ��(n)

        int lo = 0;
        int hi = 0;
        for (int i = 0; i < count.length; i++) {
            int nums = count[i];
            if (nums > 1) {
                lo = hi;
                hi += nums;

                radixSort(countingSort, lo, hi, i + 1);
            }
        }

        return countingSort;
    }

    /**
     * ��������
     * 
     * @param a
     *            �����������
     * @param d
     *            ������Ԫ�ص�λ��
     */
    public static void radixSort(int[] a, int lo, int hi, int d) {
        int[] countArray = new int[10]; // ��¼ÿ������������
        int[][] kindArray = new int[10][hi]; // ������a�е�Ԫ�ذ��ջ������࣬һά�ĺ�����0-9�����֣���ά�ĺ����Ǻ���һά������a�е�Ԫ��

        int n = 1;

        while (d-- > 0) {
            int k = lo;

            for (int i = lo; i < hi; i++) {
                int radix = (a[i] / n) % 10; // n�������������ֳ�����ÿһ��whileѭ����n�ͻ�����10��
                kindArray[radix][countArray[radix]] = a[i];
                countArray[radix]++;
            }

            // ��ÿ�������������Ľ�����·���a������
            for (int i = 0; i < kindArray.length; i++) {
                for (int j = 0; j < countArray[i]; j++) {
                    a[k] = kindArray[i][j];
                    k++;
                }

                countArray[i] = 0; // Ϊ�������Ļ������㣬�������������0
            }

            n *= 10;
        }
    }

    /**
     * �������ֵ�λ�����м�������
     * 
     * @param a
     * @param count
     * @param nums
     * @return
     */
    private static int[] countingSort(int[] a, int[] count) {
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
    public static int getMaximumDigit(int[] a) {
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
    private static int getNumDigit(int num) {
        int digit = 0;

        while (num > 0) {
            digit++;
            num /= 10;
        }

        return digit;
    }
}