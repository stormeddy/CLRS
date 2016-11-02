package quicksort;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomGenerator<T> {
	List<T> list;
	public RandomGenerator(){
		list = new ArrayList<T>();
	}
	public void addElement(T n){
		if(!list.contains(n))
			list.add(n);
	}
	public void addElements(T[]n){
		for(T t:n){
			addElement(t);
		}
	}
	public T getElement(){
		Random random = new Random();
		int index = random.nextInt(list.size());
		return list.get(index);
	}
	public void removeElement(T n){
		list.remove(n);
	}
}
