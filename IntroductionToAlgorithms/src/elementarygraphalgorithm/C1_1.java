package elementarygraphalgorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;


public class C1_1 {
	//计算图的出度和入度
	HashMap<String, Integer> indegree;
	HashMap<String, Integer> outdegree;
	
	public static void main(String[] args) throws Exception {
		Adjacent_List adjacent_list = GraphFactory.getAdjacentListInstance("graph\\22_2.txt");
		C1_1 cal=new C1_1();
		cal.calculate_indegree_outdegree(adjacent_list);
		System.out.println("入度");
//		System.out.println(Arrays.toString(indegree));
		for (Entry<String, Integer> entry:cal.indegree.entrySet()) {
			System.out.println(entry.getKey()+" "+entry.getValue());
			
		}
		System.out.println("出度");
		for (Entry<String, Integer> entry:cal.outdegree.entrySet()) {
			System.out.println(entry.getKey()+" "+entry.getValue());
			
		}
//		System.out.println(Arrays.toString(outdegree));
	}
	public void calculate_indegree_outdegree(Adjacent_List adjacent_list){
		int vertex_size=adjacent_list.getSize();
		indegree=new HashMap<String, Integer>();
		outdegree=new HashMap<String, Integer>();
		for(int i=0;i<vertex_size;i++){
//			Iterator<String> iter=g.getListByVertexIndex(i).iterator();
//			while(iter.hasNext()){
//				String vstr = iter.next();
//				int v = g.getVertexIndex(vstr);
//				indegree[v] ++;
//				outdegree[i]++;
//			}
			String istr=adjacent_list.getVertexValue(i);
			List<String> adjelement = adjacent_list.getListByVertexIndex(i);
			outdegree.put(istr, adjelement.size());
			for(int j=0;j<adjelement.size();j++){
				String vstr=adjelement.get(j);				
				if (indegree.get(vstr) == null) {
					indegree.put(vstr, 1);
				}else {
					indegree.put(vstr, indegree.get(vstr)+1);
				}
//				if (outdegree.get(istr) == null) {
//					outdegree.put(istr, 0);
//					
//				}else {
//					outdegree.put(istr, outdegree.get(istr)+1);
//				}
				
				
			}
		}
		//有些顶点入度为零，不在indgree中，要加入进来，并且设置value为0
		Set<String> out_set=new HashSet<String>(outdegree.keySet());
		Set<String> in_set=indegree.keySet();
		out_set.removeAll(in_set);
		for(String s:out_set){
			indegree.put(s, 0);
		}
	}
}
