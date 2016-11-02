package elementarygraphalgorithm;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class C2_7 {
	//二分图问题
	private static Adjacent_List g;
	private static boolean visited[];//相当于颜色，只需要两种颜色
	private static int weight[];	//对每个节点标记 初始为0，好为1，坏为-1
	public static boolean isOK(){
		int size = g.getSize();
		visited = new boolean[size];
		weight = new int[size];
		for(int i=0;i<size;i++){
			visited[i] = false;
			weight[i] = 0;
		}
		for(int i=0;i<size;i++){
			if(!visited[i])
				BFS(i);
		}
		//遍历每条边，如果是好选手与差选手比，则ok
		for(int a=0;a<g.getSize();a++){
			Iterator<String> iter = g.getListByVertexIndex(a).iterator();
			while(iter.hasNext()){
				String vstr = iter.next();
				int v = g.getVertexIndex(vstr);
				if(weight[a]==weight[v]){
					return false;
				}
			}
		}
		return true;
	}

	private static void BFS(int i) {
		visited[i] = true;
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(i);
		weight[i] = 1;
		while(!q.isEmpty()){
			int u = q.removeFirst();
			List<String> list = g.getListByVertexIndex(u);
			for(int j=0;j<list.size();j++){
				int v = g.getVertexIndex(list.get(j));
				if(!visited[v]){
					weight[v] = -weight[u];
					visited[v] = true;
					q.addLast(v);
				}
			}
		}
		//现已标记完毕
	}
	
	public static void print_classification(){
		for (int i = 0; i < weight.length; i++) {
			System.out.println(g.getVertexValue(i)+" "+weight[i]);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Adjacent_List adj_list = GraphFactory.getAdjacentListInstance("graph\\22.2_7_good.txt");
//		Adjacent_List adj_list = GraphFactory.getAdjacentListInstance("graph\\22.2_7_bad.txt");
		C2_7.g = adj_list;
		System.out.println(isOK()?"匹配成功":"匹配不成功");
		C2_7.print_classification();
	}
}