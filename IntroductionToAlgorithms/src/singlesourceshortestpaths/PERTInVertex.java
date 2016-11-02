package singlesourceshortestpaths;

import java.util.HashMap;
import java.util.List;

import elementarygraphalgorithm.Adjacent_List;
import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.TopologicalSort_Algorithm;

public class PERTInVertex {
	//图中节点代表要执行的工作，边代表工作之间的次序限制
	private int[] d;
	private int[] parent;
//PERT(G)
//    topologically sort the vertices of G
//    INITIALIZE(G)
//    for each vertex u, taken in topologically sorted order
//        do for each vertex v ∈ Adj[u]
//            do RELAX(u,v)
//
//INITIALIZE(G)
//    for each vertex v ∈ V[G]
//        do d[v] = w[v]
//           [v] = NIL
//
//RELAX(u, v)
//    if(d[v] < d[u] + w[v])
//        d[v] = d[u] + w[v]
//        π[v] = u

	public void pert(Adjacent_List G, HashMap<String, Integer> weight){
		TopologicalSort_Algorithm topo=new TopologicalSort_Algorithm(G);
		String[] vertex=topo.TopologicalSort();
		init_single_source(G,weight);
		for (int i = 0; i < vertex.length; i++) {
			int u=G.getVertexIndex(vertex[i]);
			List<String> list=G.getListByVertexIndex(u);			
			for (int j = 0; j < list.size(); j++) {
				String str=list.get(j);
				int v=G.getVertexIndex(str);
				relax(u, v, weight.get(str));
			}
		}		
	}
	
	private void init_single_source(Adjacent_List G,HashMap<String, Integer> weight){
		int size=G.getSize();
		d=new int[size];
		parent=new int[size];
		for (int i = 0; i < size; i++) {
			d[i]=weight.get(G.getVertexValue(i));
			parent[i]=-1;
		}
//		int s_index=G.getVertexIndex(s);
//		d[s_index]=0;
//		sum[s_index]=1;
		
	}
	
	private void relax(int u,int v,int weight){
		int	temp=d[u]+weight;
		if (d[v]<temp) {
			d[v]=temp;
			parent[v]=u;
		}
	}
	
	public void print_AllDistances(Adjacent_List G){
		for(int i=0;i<d.length;i++){
			System.out.println(G.getVertexValue(i)+":"+d[i]);
		}
	}
	
	public void print_AllParents(Adjacent_List G){
		for(int i=0;i<d.length;i++){
			System.out.println(G.getVertexValue(i)+":"+G.getVertexValue(parent[i]));
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Integer> map=new HashMap<String, Integer>();
		int[] cost={5,2,7,4,6,3};
		char[] vertex="rstxyz".toCharArray();
		for (int i = 0; i < vertex.length; i++) {
			map.put(vertex[i]+"",cost[i]);
		}
		PERTInVertex pert=new PERTInVertex();
		Adjacent_List G=GraphFactory.getAdjacentListInstance("graph\\pert_vertex.txt");
		pert.pert(G, map);
		pert.print_AllDistances(G);
		pert.print_AllParents(G);
		
	}

}
