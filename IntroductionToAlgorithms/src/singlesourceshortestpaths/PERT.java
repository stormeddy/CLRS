package singlesourceshortestpaths;

import java.util.List;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Pair;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class PERT {
	//寻找关键路径
	//PERT图是有向无环图，边权值为正
	private int[] d;
	private int[] parent;
	
	public void dag_shortest_paths(Weighted_Adjacent_List Orig,String s){
		Weighted_Adjacent_List G=clone_adjacent_list(Orig);
		TopologicalSort_WeightedGraph topo=new TopologicalSort_WeightedGraph(G);		
		String[] vertex=topo.TopologicalSort();
		init_single_source(G, s);
		for (int i = 0; i < vertex.length; i++) {
			int u=G.getVertexIndex(vertex[i]);
			List<Pair> list=G.getListByVertexIndex(u);			
			for (int j = 0; j < list.size(); j++) {
				Pair pair=list.get(j);
				int v=G.getVertexIndex(pair.end);
				relax(u, v, pair.weight);
			}
		}		
	}
	
	public Weighted_Adjacent_List clone_adjacent_list(Weighted_Adjacent_List G){
		//将所有权值取负
		int vertex_size=G.getSize();
		Weighted_Adjacent_List ret=new Weighted_Adjacent_List(vertex_size);
		for(int i=0;i<vertex_size;i++){
			List<Pair> list = G.getListByVertexIndex(i);
			for(Pair p:list){
				ret.addEdge(G.getVertexValue(i), p.end, -p.weight);				
			}
		}
		return ret;
	}
	
	private void relax(int u,int v,int weight){
		int temp;
		if (d[u]==Integer.MIN_VALUE) {
			temp=Integer.MIN_VALUE;
		}else {
			temp=d[u]+weight;
		}
		if (d[v]<temp) {
			d[v]=temp;
			parent[v]=u;
		}
	}
	
	private void init_single_source(Weighted_Adjacent_List G,String s){
		int size=G.getSize();
		d=new int[size];
		parent=new int[size];
		for (int i = 0; i < size; i++) {
			d[i]=Integer.MIN_VALUE;
			parent[i]=-1;
		}
		int s_index=G.getVertexIndex(s);
		d[s_index]=0;
	}
	
	/**
	 * 给定起点、终点，输出路径
	 * @param s
	 * @param t
	 */
	public void print_path(Weighted_Adjacent_List G,String s,String t){
		int s_index = G.getVertexIndex(s);
		int t_index = G.getVertexIndex(t);
		if(s_index == t_index) System.out.print(s+" ");
		else if(parent[t_index]==-1) System.out.print("no path from "+s + " to "+ t + " exists");
		else {
			print_path(G,s, G.getVertexValue(parent[t_index]));
			System.out.print(t+" ");
		}
	}
	
	/**
	 * 输出从源结点到全部的节点对应的路径距离
	 */
	public void print_AllDistances(Weighted_Adjacent_List G){
		for(int i=0;i<d.length;i++){
			System.out.println(G.getVertexValue(i)+":"+(-d[i]));
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_List G = GraphFactory.getWeightedAdjacentListInstance("graph\\pert_input.txt");
		PERT pert=new PERT();
		String source="r";
		pert.dag_shortest_paths(G, source);
		
		pert.print_AllDistances(G);
		//此处关键路径为r-->t-->x
		for (int i = 0; i < G.getSize(); i++) {
			String dest=G.getVertexValue(i);
			System.out.print(source+"-->"+dest+":");
			pert.print_path(G, source, dest);
			System.out.println();
		}
	}

}
