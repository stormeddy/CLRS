package minimumspanningtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import disjointset.DS_Forest;
import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Pair;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class MST_Kruskal {

	public List<Edge> kruskal(Weighted_Adjacent_List g){
		List<Edge> ret=new ArrayList<Edge>();
		DS_Forest ds_forest=new DS_Forest(g.getSize());
		for (int i = 0; i < g.getSize(); i++) {
			ds_forest.make_set(i);
		}
		
		List<Edge> sort_edge=new ArrayList<Edge>();
		for (int i = 0; i < g.getSize(); i++) {
			List<Pair> list=g.getListByVertexIndex(i);
			for (int j = 0; j < list.size(); j++) {
				Pair pair=list.get(j);
				Edge edge=new Edge(i, g.getVertexIndex(pair.end), pair.weight);
				sort_edge.add(edge);
			}
		}
		Collections.sort(sort_edge);
		
		for (int i = 0; i < sort_edge.size(); i++) {
			Edge edge=sort_edge.get(i);
			int u=edge.i;
			int v=edge.j;
			if (ds_forest.find_set(u)!=ds_forest.find_set(v)) {
				ret.add(edge);
				ds_forest.union(u, v);
			}
		}
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_List adj_list = GraphFactory.getWeightedAdjacentListInstance("graph\\mst_input.txt");
		MST_Kruskal kruskal=new MST_Kruskal();
		List<Edge> ret=kruskal.kruskal(adj_list);
		for (Edge edge : ret) {
			System.out.println(adj_list.getVertexValue(edge.i)+"--"+adj_list.getVertexValue(edge.j)+" "+edge.w);
		}
	}

}
