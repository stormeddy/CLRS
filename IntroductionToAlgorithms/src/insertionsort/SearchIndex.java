package insertionsort;


public class SearchIndex {
	private static Object search(double[] array,double value) {
		for (int i = 0; i < array.length; i++) {
			if (array[i]==value) {
				return i;
			}
			
		} 
		return null;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] old={3,4,2,1,7,5,8};
		System.out.println(search(old, 10.0));
	}

}
