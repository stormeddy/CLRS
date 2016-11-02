package singlesourceshortestpaths;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class DifferenceConstraints {

	public void diff_constr(Weighted_Adjacent_List G){
		BellmanFord bf=new BellmanFord();
		String source="x0";
		boolean solved=bf.bellman_ford(G, source);
		if (solved) {
			System.out.println(solved);
			bf.print_AllDistances(G);
		}else {
			System.out.println(solved);
		}
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_List G=GraphFactory.getWeightedAdjacentListInstance_DifferenceConstraints("graph\\24.4-2.txt");
		DifferenceConstraints dc=new DifferenceConstraints();
		dc.diff_constr(G);
	}
}
