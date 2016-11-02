package elementarygraphalgorithm;

import java.util.LinkedList;
import java.util.List;

public class C4_2_2 {
//	private static int count = 0;
	private static Adjacent_List g;
//	private static int f[];
	public static void main(String[] args) throws Exception {
		Adjacent_List adjlist = GraphFactory.getAdjacentListInstance("graph\\22.4_2.txt");
		C4_2_2.g = adjlist;
		System.out.println("p-->v: "+find_2("p","v"));
		System.out.println("p-->v: "+find_3("p","v"));
	}
	
	/*
	 * 正向一步一步走
	 */
	public static int find_2(String s,String t){		
		DFS_Algorithm dfs_alg = new DFS_Algorithm(g);
		dfs_alg.DFS();
		int[] finish = dfs_alg.getF();
		int count[] = new int[g.getSize()];
		int index_t = g.getVertexIndex(t);
		for(int i=0;i<count.length;i++)
			count[i] = 0;
		List<String> queue = new LinkedList<String>();
		queue.add(s);
		count[g.getVertexIndex(s)] = 1;
		while(!queue.isEmpty()){
			String u = queue.remove(0);
			List<String> list = g.getListByVertexValue(u);
			for(int i=0;i<list.size();i++){
				int v = g.getVertexIndex(list.get(i));
				if(finish[index_t] <= finish[v]) {
					queue.add(list.get(i));
					count[v]++;
				}
			}
		}
		return count[index_t];
	}
	
	
/*	
 	list = topological_sort(G); 
	create count[*] = 0;
	count[s]=1
	for each v in list
	    if(v!=t)
	        for each w in adj[v]
	            count[w] = count[w] + count[v];
	    else break;
	return count[t];
*/
	public static int find_3(String s,String t){
		TopologicalSort_Algorithm alg=new TopologicalSort_Algorithm(g);
		alg.DFS();
		List<String> ret=alg.getResultList();
		int count[] = new int[g.getSize()];
		for(int i=0;i<count.length;i++)
			count[i] = 0;
		count[g.getVertexIndex(s)] = 1;
		for(String e:ret){
			if (!e.equals(t)) {
				List<String> list = g.getListByVertexValue(e);
				int v=g.getVertexIndex(e);
				for(int i=0;i<list.size();i++){
					int w = g.getVertexIndex(list.get(i));
					count[w]=count[w]+count[v];
				}
			}
			else break;
		}
		return count[g.getVertexIndex(t)];
	}
}