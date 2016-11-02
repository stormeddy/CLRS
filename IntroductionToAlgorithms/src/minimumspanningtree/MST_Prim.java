package minimumspanningtree;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import elementarygraphalgorithm.GraphFactory;
import elementarygraphalgorithm.Pair;
import elementarygraphalgorithm.Weighted_Adjacent_List;

public class MST_Prim {
	
	public List<Edge> prim(Weighted_Adjacent_List g,int r){
		int size=g.getSize();
		Vertex[] vertices=new Vertex[size];
		PriorityQueue<Vertex> queue=new PriorityQueue<Vertex>();
		List<Edge> ret=new ArrayList<Edge>();
		
		for (int i = 0; i < size; i++) {
			vertices[i]=new Vertex(i, Integer.MAX_VALUE, -1);
		}
		vertices[r].key=0;
		for (int i = 0; i < vertices.length; i++) {
			queue.offer(vertices[i]);
		}		
		while (!queue.isEmpty()) {
			Vertex vertex=queue.poll();
			int u=vertex.index;
			List<Pair> list=g.getListByVertexIndex(u);
			for (int i = 0; i < list.size(); i++) {
				int v=g.getVertexIndex(list.get(i).end);
				if (queue.contains(vertices[v])&&list.get(i).weight<vertices[v].key) {
					queue.remove(vertices[v]);
					vertices[v].parent=u;
					vertices[v].key=list.get(i).weight;
					//修改vertices[v]的key值,同时修改queue的时候要先移除vertices[v],再添加进来
					queue.offer(vertices[v]);
				}
			}
			
		}
		
		for (int i = 0; i < size; i++) {
			if (i!=r) {
				Edge e=new Edge(i, vertices[i].parent, vertices[i].key);
				ret.add(e);
			}
		}
		return ret;
		
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Weighted_Adjacent_List adj_list = GraphFactory.getWeightedAdjacentListInstance("graph\\mst_input.txt");
		MST_Prim prim=new MST_Prim();
		List<Edge> ret=prim.prim(adj_list,0);
		for (Edge edge : ret) {
			System.out.println(adj_list.getVertexValue(edge.i)+"--"+adj_list.getVertexValue(edge.j)+" "+edge.w);
		}
	}
	
	class Vertex implements Comparable<Vertex>{
		int index;
		int key;
		int parent;
		public Vertex(int i,int k,int p){
			index=i;
			key=k;
			parent=p;
		}
		@Override
		public int compareTo(Vertex o) {
			// TODO Auto-generated method stub
			if(this.key>o.key) return 1;  
	        else if(this.key==o.key) return 0;  
	        else return -1;  
		}
		
		@Override  
	    public String toString() {  
	        return "index="+index+"||key="+key+"||parent="+parent;  
	    }  
	}
	
	

}
