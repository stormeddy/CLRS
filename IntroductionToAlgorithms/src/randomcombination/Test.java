package randomcombination;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Test {
	//从{1,2,...,n}中取出随机包含m个元素的样本
	public static Set<Integer> randomSample(int m, int n) {
		Set<Integer> set =new HashSet<Integer>();
		if (m == 0) {
			return set;
		} else {
			set = randomSample(m - 1, n - 1);
			Random random = new Random();
			int i = random.nextInt(n) + 1;
			if (set.contains(i)) {
				set.add(n);
			} else {
				set.add(i);
			}
			return set;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Set<Integer> set = randomSample(5, 10);
		for (Iterator<Integer> iterator = set.iterator(); iterator.hasNext();) {
			System.out.print(iterator.next() + " ");

		}

	}
}
