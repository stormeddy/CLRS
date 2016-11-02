package elementarygraphalgorithm;

import minimumspanningtree.Edge;

public class Pair{
	public String end;
	public int weight;
	public Edge orig;
//	public int f;//flow,Á÷,Ä¬ÈÏÎª0
	public Pair(String end, int weight) {
		this.end = end;
		this.weight = weight;
		this.orig=null;
	}
	
	public Pair(String end, int weight,Edge edge) {
		this.end = end;
		this.weight = weight;
		this.orig=edge;
	}
	public Pair() {
	}
	@Override
	public String toString() {
		if (orig==null) {
			return "Pair [end=" + end + ", weight=" + weight + "]";
		}else {
			return "Pair [end=" + end + ", weight=" + weight + ", orig=("+ orig.i +","+orig.j+")]";
		}
		
	}
}
