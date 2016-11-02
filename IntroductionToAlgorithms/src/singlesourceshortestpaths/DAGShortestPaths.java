package singlesourceshortestpaths;

import java.util.List;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Pair;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class DAGShortestPaths {
	//只适用于有向无环图(DAG)
	private int[] d;
	private int[] parent;
	private int[] sum;//DAG中的路径数目
	
	public void dag_shortest_paths(Weighted_Adjacent_List G,String s){
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
				sum[v]+=sum[u];
			}
		}		
	}
	
	private void relax(int u,int v,int weight){
		int temp;
		if (d[u]==Integer.MAX_VALUE) {
			temp=Integer.MAX_VALUE;
		}else {
			temp=d[u]+weight;
		}
		if (d[v]>temp) {
			d[v]=temp;
			parent[v]=u;
		}
	}
	
	private void init_single_source(Weighted_Adjacent_List G,String s){
		int size=G.getSize();
		d=new int[size];
		parent=new int[size];
		sum=new int[size];
		for (int i = 0; i < size; i++) {
			d[i]=Integer.MAX_VALUE;
			parent[i]=-1;
		}
		int s_index=G.getVertexIndex(s);
		d[s_index]=0;
		sum[s_index]=1;
		
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
			System.out.println(G.getVertexValue(i)+":"+d[i]);
		}
	}
	
	/**
	 * 输出从源结点到全部的节点对应的路径数目
	 */
	public void print_AllDistanceNumbers(Weighted_Adjacent_List G,String s){
		for(int i=0;i<d.length;i++){
			System.out.println(s+"-->"+G.getVertexValue(i)+":"+sum[i]);
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_List G = GraphFactory.getWeightedAdjacentListInstance("graph\\dag_shortest.txt");
		DAGShortestPaths dag=new DAGShortestPaths();
		String source="s";
		dag.dag_shortest_paths(G, source);
		
		dag.print_AllDistances(G);
		System.out.println();
		
		dag.print_AllDistanceNumbers(G,source);
		System.out.println();
		
		for (int i = 0; i < G.getSize(); i++) {
			String dest=G.getVertexValue(i);
			System.out.print(source+"-->"+dest+":");
			dag.print_path(G, source, dest);
			System.out.println();
		}
	}

}
