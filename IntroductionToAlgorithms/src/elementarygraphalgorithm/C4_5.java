package elementarygraphalgorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class C4_5 {
	//��һ����������
	public static void main(String[] args) throws Exception {
		Adjacent_List adjacent_list  = GraphFactory.getAdjacentListInstance("graph\\topo_input.txt");
		System.out.println(topo(adjacent_list));
	}
	public static boolean topo(Adjacent_List g){
		//1.�������
		int[] indegree = new int[g.getSize()];
		for(int i=0;i<g.getSize();i++){
			indegree[i] = 0;
		}
		for(int i=0;i<g.getSize();i++){
			Iterator<String> iter = g.getListByVertexIndex(i).iterator();
			while(iter.hasNext()){
				String vstr = iter.next();
				int v = g.getVertexIndex(vstr);
				indegree[v]++;
			}
		}
		//����BFS
		List<Integer> q = new LinkedList<Integer>();
		for(int i=0;i<indegree.length;i++){
			if(indegree[i]==0)
				q.add(i);
		}
		while(!q.isEmpty()){
			int u = q.remove(0);
			System.out.print( g.getVertexValue(u) + " ");
			Iterator<String> iter = g.getListByVertexIndex(u).iterator();
			while(iter.hasNext()){
				String vstr = iter.next();
				int v = g.getVertexIndex(vstr);
				indegree[v] --;
				if(indegree[v]==0) q.add(v);
			}
		}
		System.out.println();
		for (int i = 0; i < indegree.length; i++) {
			if (indegree[i]!=0) {
				return true;//�л�·
			}
		}
		return false;//û�л�·

	}
}