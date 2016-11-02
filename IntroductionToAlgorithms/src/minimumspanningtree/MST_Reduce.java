package minimumspanningtree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import disjointset.DS_Forest;
import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Pair;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class MST_Reduce {

	public Weighted_Adjacent_List reduce(Weighted_Adjacent_List G, Weighted_Adjacent_List T){
		G=init(G);
		int size=G.getSize();		
		boolean[] mark=new boolean[size];
		DS_Forest ds_forest=new DS_Forest(size);
		for (int i = 0; i < size; i++) {
			ds_forest.make_set(i);
		}
		
		for (int u = 0; u < size; u++) {
			if (!mark[u]) {
				List<Pair> list=G.getListByVertexIndex(u);
				int min_weight=Integer.MAX_VALUE;
				int v=0;
				for (int i = 0; i < list.size(); i++) {
					Pair p=list.get(i);
					if (p.weight<min_weight) {
						//选出顶点u关联的最小权重的边(u,v)
						v=G.getVertexIndex(p.end);
						min_weight=p.weight;
					}					
				}
				ds_forest.union(u, v);
				T.addEdge(G.getVertexValue(u), G.getVertexValue(v), min_weight);
				T.addEdge(G.getVertexValue(v), G.getVertexValue(u), min_weight);
				mark[u]=true;
				mark[v]=true;
			}
		}
		
		int count=0;
		//图G中顶点到图G'的索引映射
		HashMap<Integer,Integer> vertex_map=new HashMap<>();
		//图G中顶点的聚类集合
		HashSet<Integer> component_set=new HashSet<>();
		for (int i = 0; i < size; i++) {
			int index=ds_forest.find_set(i);
			if (!component_set.contains(index)) {
				vertex_map.put(i, count);
				component_set.add(index);
				count++;
			}
		}
		Weighted_Adjacent_List rG=new Weighted_Adjacent_List(count);
		boolean[][] rEdge_set=new boolean[count][count];//用于判断边(u,v)是否在G’.E中
		for (int x = 0; x < size; x++) {
			int u=ds_forest.find_set(x);
			String u_str=G.getVertexValue(u);//使用顶点集合中的代表节点
			List<Pair> list=G.getListByVertexIndex(x);
			for (int i = 0; i < list.size(); i++) {
				Pair p=list.get(i);
				int y=G.getVertexIndex(p.end);
				int v=ds_forest.find_set(y);
				String v_str=G.getVertexValue(v);
				Integer u_r=rG.getVertexIndex(u_str);
				Integer v_r=rG.getVertexIndex(v_str);
				if ( u != v && ((u_r==null||v_r==null) || !rEdge_set[u_r][v_r])) {
					//边(u,v)不在G’.E中
					rG.addEdge(u_str, v_str, p.weight,new Edge(x, y));
					rG.addEdge(v_str, u_str, p.weight,new Edge(y, x));
					u_r=rG.getVertexIndex(u_str);
					v_r=rG.getVertexIndex(v_str);
					rEdge_set[u_r][v_r]=true;
					rEdge_set[v_r][u_r]=true;
				}else if(u != v && rEdge_set[u_r][v_r]){
					//边(u,v)在G’.E中
					List<Pair> rlist_u=rG.getListByVertexValue(u_str);
					Pair rp_u=null;
					for (int j = 0; j < rlist_u.size(); j++) {
						rp_u=rlist_u.get(j);
						if (rp_u.end.equals(v_str)) 
							break;						
					}
					if (p.weight<rp_u.weight) {
						rp_u.orig=new Edge(p.orig.i, p.orig.j);
						rp_u.weight=p.weight;
					}
					
					List<Pair> rlist_v=rG.getListByVertexValue(v_str);
					Pair rp_v=null;
					for (int j = 0; j < rlist_v.size(); j++) {
						rp_v=rlist_v.get(j);
						if (p.end.equals(u_str)) 
							break;						
					}
					if (p.weight<rp_v.weight) {
						rp_v.orig=new Edge(p.orig.j, p.orig.i);//无向图i和j的顺序不重要，但此处更准确
						rp_v.weight=p.weight;
					}
				}
			}
		}
		
		//修改T,T+{(x,y).orig:(x,y)属于G'的最小生成树}
		MST_Prim prim=new MST_Prim();
		List<Edge> list=prim.prim(rG, 0);
		for (Edge edge : list) {
			String x=rG.getVertexValue(edge.i);
			String y=rG.getVertexValue(edge.j);
			List<Pair> rlist=rG.getListByVertexValue(x);
			Pair p=null;
			for (int j = 0; j < rlist.size(); j++) {
				p=rlist.get(j);
				if (p.end.equals(y))
					break;
			}
			String u=G.getVertexValue(p.orig.i);
			String v=G.getVertexValue(p.orig.j);
			T.addEdge(u, v, p.weight);
			T.addEdge(v, u, p.weight);
		}
		
		return rG;
	}
	
	private Weighted_Adjacent_List init(Weighted_Adjacent_List G){
		for (int i = 0; i < G.getSize(); i++) {
			List<Pair> list=G.getListByVertexIndex(i);
			for (int j = 0; j < list.size(); j++) {
				Pair p=list.get(j);
				int index=G.getVertexIndex(p.end);
				p.orig=new Edge(i, index);
			}
		}
		return G;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_List G = GraphFactory.getWeightedAdjacentListInstance("graph\\mst_input.txt");
		Weighted_Adjacent_List T=new Weighted_Adjacent_List(G.getSize());
		MST_Reduce reduce=new MST_Reduce();
		Weighted_Adjacent_List rG=reduce.reduce(G, T);
		System.out.println("G':");
		rG.printAllEdges();
		System.out.println();
		System.out.println("minimum spanning tree:");
		T.printAllEdges();
	}

}
