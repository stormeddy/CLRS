package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class C22_3 {
	//ŷ����·��ÿ��������Ⱥ������Ȳ���ŷ����·
	private static Adjacent_List G;
	private static int[] outdegree;
	private static List<A> L;

	public static void main(String[] args) throws Exception {
		Adjacent_List adjacent_list = GraphFactory
				.getAdjacentListInstance("graph\\22_3.txt");
		C22_3.G = adjacent_list;
		List<String> result = Eular_Circuit();
		for(String s:result)
			System.out.println(s);
	}

	public static List<String> Eular_Circuit() {
		//1.�������
		outdegree = new int[G.getSize()];
		for (int i = 0; i < G.getSize(); i++) {
			outdegree[i] = 0;
		}

		for (int i = 0; i < G.getSize(); i++) {
			Iterator<String> iter = G.getListByVertexIndex(i).iterator();
			while (iter.hasNext()) {
				iter.next();
				outdegree[i]++;
			}
		}
		//2.������ӻ�·
		//T�洢�ܵ���ʾ����
		//L�洢��ǰ���Ȳ�Ϊ0�ĵ�
		//C�洢���ֵĻ�
		List<String> T = new ArrayList<String>();
		L = new ArrayList<A>();
		T = visit(0, -1);
		List<String> C = new ArrayList<String>();
		while (!L.isEmpty()) {
			A a = L.remove(0);
			C = visit(a.vertex, a.location_in_T);
			T.addAll(a.location_in_T, C);
		}
		return T;
	}

	private static List<String> visit(int v, int location) {
		List<String> C = new ArrayList<String>();
		int u = v;
		while (outdegree[u] > 0) {
			C.add(G.getVertexValue(u));
			List<String> list = G.getListByVertexIndex(u);
			String w = list.remove(0);
			outdegree[u]--;
			if (outdegree[u] > 0)
				L.add(new A(u, C.size() + (location==-1?0:location) - 1));
			u = G.getVertexIndex(w);
		}
		if (location == -1)
			C.add(G.getVertexValue(v));
		return C;
	}

}

class A {
	int vertex;
	int location_in_T;

	public A(int vertex, int location_in_T) {
		this.vertex = vertex;
		this.location_in_T = location_in_T;
	}

}