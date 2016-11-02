package lineartimesort;

import java.util.Arrays;

public class Exercise_8_3a {

    public static void main(String[] args) {
        int[] a = { 133, 2, 433, 124, 3432434, 2322, 2345, 1, 231, 12, 45, 84, 21, 3457, 132356, 12, 5, 67773, 233 };

        System.out.println(Arrays.toString(Exercise_8_3a.sort(a)));
    }

    public static int[] sort(int[] a) {
        int maximumDigit = getMaximumDigit(a); // θ(n)
        int[] count = new int[maximumDigit];

        int[] countingSort = countingSort(a, count); // θ(n)

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
     * 基数排序
     * 
     * @param a
     *            待排序的数组
     * @param d
     *            数组中元素的位数
     */
    public static void radixSort(int[] a, int lo, int hi, int d) {
        int[] countArray = new int[10]; // 记录每个基数的数量
        int[][] kindArray = new int[10][hi]; // 把数组a中的元素按照基数归类，一维的含义是0-9个数字，二维的含义是含有一维基数的a中的元素

        int n = 1;

        while (d-- > 0) {
            int k = lo;

            for (int i = lo; i < hi; i++) {
                int radix = (a[i] / n) % 10; // n的作用在这体现出来，每一轮while循环，n就会扩大10倍
                kindArray[radix][countArray[radix]] = a[i];
                countArray[radix]++;
            }

            // 将每个基数排完序后的结果重新放入a数组中
            for (int i = 0; i < kindArray.length; i++) {
                for (int j = 0; j < countArray[i]; j++) {
                    a[k] = kindArray[i][j];
                    k++;
                }

                countArray[i] = 0; // 为了其它的基数计算，计数数组必须清0
            }

            n *= 10;
        }
    }

    /**
     * 按照数字的位数进行计数排序
     * 
     * @param a
     * @param count
     * @param nums
     * @return
     */
    private static int[] countingSort(int[] a, int[] count) {
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
     * 获取整数的位数
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