package minimumspanningtree;

import java.util.LinkedList;
import java.util.List;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Pair;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class SecondBest_MST {

	public Edge[][] bfs_fill_max(Weighted_Adjacent_List mst){
		int size=mst.getSize();
		Edge[][] max=new Edge[size][size];
		for (int u = 0; u < size; u++) {
//			for (int v = 0; v < size; v++) {
//				max[u][v]=null;
//			}
			LinkedList<Integer> queque=new LinkedList<Integer>();
			queque.offer(u);
			while (!queque.isEmpty()) {
				int x=queque.poll();
				List<Pair> list=mst.getListByVertexIndex(x);
				for (int i = 0; i < list.size(); i++) {
					Pair p=list.get(i);
					int v=mst.getVertexIndex(p.end);//p代表索引表示的边(x,v)
					if (max[u][v]==null && u!=v) {
						if (x==u||p.weight>max[u][x].w) {
							max[u][v]=new Edge(x, v, p.weight);
						}else {
							max[u][v]=max[u][x];
						}
						queque.offer(v);
					}
					
				}
			}
		}
		return max;
	}
	
	public Weighted_Adjacent_List second_best_mst(Weighted_Adjacent_List g){
		Weighted_Adjacent_List mst=construct_MST(g);
		Weighted_Adjacent_List second_mst=mst.clone_adjacent_list();
		Edge[][] max=bfs_fill_max(mst);
		int size=g.getSize();
		String u_pre=null;//(u_pre,v_pre)为要替换的边
		String v_pre=null;
		String u_sub=null;//(u_sub,v_sub,weight_sub)为要加进来的边
		String v_sub=null;
		int weight_sub=-1;
		int min=Integer.MAX_VALUE;
		for (int i = 0; i < size; i++) {
			String u=g.getVertexValue(i);
			List<Pair> list=g.getListByVertexIndex(i);
			for (int j = 0; j < list.size(); j++) {
				Pair p=list.get(j);
				String v=p.end;
				if (!is_edge_of_mst(u, v, mst)) {
					int u_index=mst.getVertexIndex(u);
					int v_index=mst.getVertexIndex(v);
					Edge e=max[u_index][v_index];
					if (p.weight-e.w<min) {
						u_pre=mst.getVertexValue(e.i);
						v_pre=mst.getVertexValue(e.j);
						u_sub=u;
						v_sub=v;
						min=p.weight-e.w;
						weight_sub=p.weight;
					}
				}				
			}
		}
		
		
		if (u_pre!=null&&v_pre!=null) {
			//删除边sub,因为是无向图,所以要删除两条			
			List<Pair> u_list=second_mst.getListByVertexValue(u_pre);
			int u_index=-1;
			for (int i = 0; i < u_list.size(); i++) {
				if (u_list.get(i).end.equals(v_pre)) {
					u_index=i;
					break;
				}
			}
			if (u_index!=-1) {
				u_list.remove(u_index);
			}
			
			List<Pair> v_list=second_mst.getListByVertexValue(v_pre);
			int v_index=-1;
			for (int i = 0; i < v_list.size(); i++) {
				if (v_list.get(i).end.equals(u_pre)) {
					v_index=i;
					break;
				}
			}
			if (v_index!=-1) {
				v_list.remove(v_index);
			}
		}
		
		//加入边
		second_mst.addEdge(u_sub, v_sub, weight_sub);
		second_mst.addEdge(v_sub, u_sub, weight_sub);
		
		
		
		
//		System.out.println(mst.getVertexValue(sub.i)+"--"+mst.getVertexValue(sub.j)+" "+sub.w+" "+u_sub+" "+v_sub);
		return second_mst;
	}
	
	private boolean is_edge_of_mst(String u,String v,Weighted_Adjacent_List mst){
		//判断边(u,v)是否为最小生成树中的一条边
		List<Pair> search_list=mst.getListByVertexValue(u);
		for (int i = 0; i < search_list.size(); i++) {
			Pair p=search_list.get(i);
			if(p.end.equals(v)) return true;
		}
		return false;
	}
	
	public Weighted_Adjacent_List construct_MST(Weighted_Adjacent_List g){
		MST_Prim prim=new MST_Prim();
		List<Edge> edges=prim.prim(g, 0);
		Weighted_Adjacent_List mst=new Weighted_Adjacent_List(g.getSize());
		for (Edge edge : edges) {
			String u=g.getVertexValue(edge.i);
			String v=g.getVertexValue(edge.j);
			//无向图
			mst.addEdge(u, v, edge.w);
			mst.addEdge(v, u, edge.w);
		}
		return mst;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SecondBest_MST sb_mst=new SecondBest_MST();
		Weighted_Adjacent_List g = GraphFactory.getWeightedAdjacentListInstance("graph\\mst_input.txt");
		Weighted_Adjacent_List mst=sb_mst.construct_MST(g);
		//这个图最小生成树有两个，所以此处的次优最小生成树和最小生成树一样
		System.out.println("minimum spanning tree:");
		mst.printAllEdges();
//		Edge[][] max=sb_mst.bfs_fill_max(g, mst);
//		for (int u = 0; u < max.length; u++) {
//			for (int v = 0; v < max[0].length; v++) {
//				if (max[u][v]!=null) {
//					Edge e=max[u][v];
//					System.out.println(mst.getVertexValue(u)+"-->"+mst.getVertexValue(v)+":"+
//					mst.getVertexValue(e.i)+"-"+mst.getVertexValue(e.j)+" "+e.w);
//				}				
//			}
//		}
		System.out.println();
		System.out.println("second best minimum spanning tree:");
		//这个图最小生成树有两个，所以此处的次优最小生成树和最小生成树一样
//		Weighted_Adjacent_List second_mst=mst.clone_adjacent_list();
//		second_mst.printAllEdges();
		Weighted_Adjacent_List second_best_mst=sb_mst.second_best_mst(g);
		second_best_mst.printAllEdges();
	}

}
