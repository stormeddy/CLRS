package elementarygraphalgorithm;

import java.util.List;

public class C4_2_1 {
	private static int count = 0;
	private static Adjacent_List g;
//	private static int f[];
	
	public static void main(String[] args) throws Exception {
		Adjacent_List adjlist = GraphFactory.getAdjacentListInstance("graph\\22.4_2.txt");
		C4_2_1.g = adjlist;
		System.out.println("p-->v: "+findPathCount("p","v"));
	}
	
	public static int findPathCount(String s,String t){
		//O(V+E)
//		DFS_Algorithm dfs_alg = new DFS_Algorithm(g);
//		f = dfs_alg.getF();
		DFS(s,t);
		return count;
	}
	
	
	
	private static void DFS(String s,String t) {
		//就是一个DFS，可以不用DFS所有节点，只DFS以s为源节点的树
//		Add a field to the vertex representation to hold an integer count. 
//		Initially, set vertex t’s count to 1 and other vertices’ count to 0. 
//		Start running DFS with s as the start vertex. When t is discovered, 
//		it should be immediately marked as finished (BLACK),
//		without further processing starting from it. Subsequently, 
//		each time DFS finishes a vertex v, set v’s count to the sum of the
//		counts of all vertices adjacent to v. When DFS finishes vertex s, 
//		stop and return the count computed for s.
		List<String> list = g.getListByVertexValue(s);
		for(int i=0;i<list.size();i++){
			String value = list.get(i);
//			int v = g.getVertexIndex(value);
			if(value.equals(t)) count++;
//			if(f[v]<f[g.getVertexIndex(t)]) return;
			else DFS(value,t);
		}
	}
}
