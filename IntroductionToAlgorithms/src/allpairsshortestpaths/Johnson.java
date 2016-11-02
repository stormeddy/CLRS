package allpairsshortestpaths;

import java.util.Arrays;
import java.util.List;

import singlesourceshortestpaths.BellmanFord;
import singlesourceshortestpaths.Dijkstra;
import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Pair;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class Johnson {

	public int[][] johnson(Weighted_Adjacent_List G){
		int g_size=G.getSize();//G图的顶点个数
		Weighted_Adjacent_List Gp=new Weighted_Adjacent_List(g_size+1);
		
		for(int i=0;i<g_size;i++){
			List<Pair> list = G.getListByVertexIndex(i);
			for(Pair p:list){
				Gp.addEdge(G.getVertexValue(i), p.end, p.weight);				
			}
		}
		
		String src="0";
		for (int i = 0; i < g_size; i++) {
			Gp.addEdge(src, G.getVertexValue(i), 0);
		}
//		Gp.printAllEdges();
		
		int[][] D=new int[g_size][g_size];
		BellmanFord bf=new BellmanFord();
		boolean neg_circle=bf.bellman_ford(Gp, src);
//		bf.print_AllDistances(Gp);
		if (!neg_circle) {
			System.out.println("the input graph contains a negative-weight circle");
		}else {
			int[] delta=bf.get_d();
			System.out.println(Arrays.toString(delta));
			for (int i = 0; i < Gp.getSize(); i++) {
				List<Pair> list=Gp.getListByVertexIndex(i);
				int u=i;
				for (int j = 0; j < list.size(); j++) {
					Pair p=list.get(j);
					int v=Gp.getVertexIndex(p.end);
					p.weight=p.weight+delta[u]-delta[v];
					
				}
			}
			
			for (int u = 0; u < g_size; u++) {
				Dijkstra dijkstra=new Dijkstra();
				int u_index=G.getVertexIndex(Gp.getVertexValue(u));
				dijkstra.dijkstra(Gp, Gp.getVertexValue(u));
				
				
				//通过dijkstra算法输出最短路径
//				for (int v = 0; v < g_size; v++) {
//					String source=Gp.getVertexValue(u);
//					String dst=Gp.getVertexValue(v);
//					System.out.print(source+"->"+dst+":");
//					dijkstra.print_path(Gp, source, dst);
//					System.out.println();
//				}
//				System.out.println();
				
				
				int[] delta_p=dijkstra.get_d();
//				System.out.println(Arrays.toString(delta_p));
				for (int v = 0; v < g_size; v++) {
					int v_index=G.getVertexIndex(Gp.getVertexValue(v));
					//D[][]的索引对应图G的索引
					//delta_p[]和delta[]的索引对应图Gp的索引
					if (delta_p[v]!=Integer.MAX_VALUE) {
						D[u_index][v_index]=delta_p[v]+delta[v]-delta[u];
					}else {
						D[u_index][v_index]=Integer.MAX_VALUE;
					}
					
				}
			}
		}	
		
		
		return D;
	}
	
	
	public void print_all_pairs_shortest_paths(Weighted_Adjacent_List G){
		int[][] d=johnson(G);
		int size=G.getSize();
		System.out.print("\t");
		for (int i = 0; i < size; i++) {
			System.out.print(G.getVertexValue(i)+"\t");
		}
		System.out.println();
		for (int i = 0; i < size; i++) {
			System.out.print(G.getVertexValue(i)+"\t");
			for (int j = 0; j < size; j++) {
				System.out.print(d[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_List G=GraphFactory.getWeightedAdjacentListInstance("graph\\25-2.txt");
		Johnson js=new Johnson();		
		js.print_all_pairs_shortest_paths(G);
		
	}

}
