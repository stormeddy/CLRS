package elementarygraphalgorithm;

import java.util.Iterator;
/**
 * 化简多重图
 * @author xiazdong
 *
 */
public class C1_4 {
	static Adjacent_List g;
	public static void main(String[] args) throws Exception {
		Adjacent_List adjacent_list = GraphFactory.getAdjacentListInstance("graph\\22.1_4.txt");	
		C1_4.g = adjacent_list;
		Adjacent_List new_adj = remove_multiedge(adjacent_list);
		adjacent_list.printAllEdges();
		System.out.println("==============");
		new_adj.printAllEdges();
	}
	/**
	 * 去掉重边和自循环边的函数
	 * @param g
	 * @return
	 */
	public static Adjacent_List remove_multiedge(Adjacent_List g){
		int size = g.getSize();
		Adjacent_List adj1 = new Adjacent_List(size);
		boolean visited[] = new boolean[size];
		for(int i=0;i<visited.length;i++) visited[i] = false;//O（V）
		for(int i=0;i<size;i++){		//O(V+E)
			visited[i] = true;
			Iterator<String> iter = g.getListByVertexIndex(i).iterator();
			while(iter.hasNext()){	
				String vstr = iter.next();
				int v = g.getVertexIndex(vstr);
				if(!visited[v]){
					visited[v] = true;
					adj1.addEdge(g.getVertexValue(i), vstr);
				}
			}
			/*下面一句话 对新图遍历比较快，虽然速度只会提升常数级别。
			 较快：iter = adj1.getListByVertexValue(g.getVertexValue(i)).iterator();
			 较慢：iter = g.getListByVertexIndex(i).iterator(); 因为有重边和自循环边，边的数目较多
			 */
			iter = adj1.getListByVertexValue(g.getVertexValue(i)).iterator();	
			while(iter.hasNext()){	//再一次遍历这个链表，并对这些元素清除数组 O(E)
				String vstr = iter.next();
				int v = g.getVertexIndex(vstr);
				visited[v] = false;
			}
			visited[i] = false;
		}
		return adj1;
	}
}
