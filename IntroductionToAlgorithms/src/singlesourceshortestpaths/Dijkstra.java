package singlesourceshortestpaths;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Pair;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class Dijkstra {

	private int[] d;
	private int[] parent;
	
	public int[] get_d(){
		return d;
	}
	
	public void dijkstra(Weighted_Adjacent_List G,String s){
		init_single_source(G, s);
		HashSet<String> set=new HashSet<String>();
		int size=G.getSize();
		PriorityQueue<Vertex> queue=new PriorityQueue<Vertex>();
		Vertex[] vertices=new Vertex[size];
		for (int i = 0; i < vertices.length; i++) {
			vertices[i]=new Vertex(i, d[i], parent[i]);
			queue.offer(vertices[i]);
		}
		while (!queue.isEmpty()) {
			Vertex u_vertex=queue.poll();
			int u=u_vertex.index;
			set.add(G.getVertexValue(u));
			List<Pair> list=G.getListByVertexIndex(u);
			for (int i = 0; i < list.size(); i++) {
				Pair p=list.get(i);
				int v=G.getVertexIndex(p.end);
				int weight=p.weight;
				
				//relax操作，同时要更新queue中vertex的属性
				int temp;
				boolean change=false;
				if (d[u]==Integer.MAX_VALUE) {
					temp=Integer.MAX_VALUE;
				}else {
					temp=d[u]+weight;
				}
				if (d[v]>temp) {
					d[v]=temp;
					parent[v]=u;
					change=true;
				}
				if (change) {
					//更新queue中vertex的属性
					queue.remove(vertices[v]);
					vertices[v].d=d[v];
					vertices[v].parent=parent[v];
					queue.offer(vertices[v]);
				}
			}
		}
		
	}
	
	private void init_single_source(Weighted_Adjacent_List G,String s){
		int size=G.getSize();
		d=new int[size];
		parent=new int[size];
		for (int i = 0; i < size; i++) {
			d[i]=Integer.MAX_VALUE;
			parent[i]=-1;
		}
		int s_index=G.getVertexIndex(s);
		d[s_index]=0;
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
	 * 给定起点、终点，输出路径
	 * @param s
	 * @param t
	 */
	public void print_path(Weighted_Adjacent_List G,String s,String t){
		int s_index = G.getVertexIndex(s);
		int t_index = G.getVertexIndex(t);
		if(s_index == t_index) System.out.print(s+" ");
		else if(parent[t_index]==-1) System.out.println("no path from "+s + " to "+ t + " exists");
		else {
			print_path(G,s, G.getVertexValue(parent[t_index]));
			System.out.print(t+" ");
		}
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_List G = GraphFactory.getWeightedAdjacentListInstance("graph\\dijkstra.txt");
		Dijkstra dijkstra=new Dijkstra();
		String source="s";
		dijkstra.dijkstra(G, source);
		dijkstra.print_AllDistances(G);
		for (int i = 0; i < G.getSize(); i++) {
			String dest=G.getVertexValue(i);
			System.out.print(source+"-->"+dest+":");
			dijkstra.print_path(G, source, dest);
			System.out.println();
		}
	}
	
	class Vertex implements Comparable<Vertex>{
		int index;
		int d;
		int parent;
		public Vertex(int i,int d,int p){
			index=i;
			this.d=d;
			parent=p;
		}
		@Override
		public int compareTo(Vertex o) {
			// TODO Auto-generated method stub
			if(this.d>o.d) return 1;  
	        else if(this.d==o.d) return 0;  
	        else return -1;  
		}
		
		@Override  
	    public String toString() {  
	        return "index="+index+"||d="+d+"||parent="+parent;  
		}  
	}

}
