package elementarygraphalgorithm;

import java.util.Iterator;
/**
 * �������ͼ
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
	 * ȥ���رߺ���ѭ���ߵĺ���
	 * @param g
	 * @return
	 */
	public static Adjacent_List remove_multiedge(Adjacent_List g){
		int size = g.getSize();
		Adjacent_List adj1 = new Adjacent_List(size);
		boolean visited[] = new boolean[size];
		for(int i=0;i<visited.length;i++) visited[i] = false;//O��V��
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
			/*����һ�仰 ����ͼ�����ȽϿ죬��Ȼ�ٶ�ֻ��������������
			 �Ͽ죺iter = adj1.getListByVertexValue(g.getVertexValue(i)).iterator();
			 ������iter = g.getListByVertexIndex(i).iterator(); ��Ϊ���رߺ���ѭ���ߣ��ߵ���Ŀ�϶�
			 */
			iter = adj1.getListByVertexValue(g.getVertexValue(i)).iterator();	
			while(iter.hasNext()){	//��һ�α����������������ЩԪ��������� O(E)
				String vstr = iter.next();
				int v = g.getVertexIndex(vstr);
				visited[v] = false;
			}
			visited[i] = false;
		}
		return adj1;
	}
}
