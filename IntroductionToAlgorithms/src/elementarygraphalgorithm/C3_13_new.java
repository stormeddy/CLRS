package elementarygraphalgorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * �����Ƕ�ÿ���ڵ�DFS��Ѱ��BLACK�ĵ�
 * �ҵķ��������Ͻ����˸Ľ������ǲ��Ƕ�ÿ���ڵ㶼DFS�����Ǳ����һ�ζ�1����DFS������2 3 4����ڶ��β���Ҫ��2 3 4����DFS�ˣ�����DFS�����㣬��DFS������֮ǰ��Ҫ���color
 * O(V(V+E))
 * @author xiazdong
 *
 */

public class C3_13_new {
	private static int color[];
	private static final int WHITE = 0;
	private static final int GRAY = 1;
	private static final int BLACK = 2;
	private static Adjacent_List g;
	private static Adjacent_List undirected_g;
	private static int [] cc ;//����ͨ�������
	private static int weight = -1;	//��¼�ж��ٸ�����ͨͼ
	private static boolean flag = true;	//�����ж��Ƿ�Ϊ����ͨ
	private static int parent[];
	public static void main(String[] args) throws Exception {
		Adjacent_List adjlist = GraphFactory.getAdjacentListInstance("graph\\22.3_13_new.txt");
		C3_13_new.g = adjlist;
		//System.out.println(singly_connected());
		System.out.println(single_connected_2());
	}
	public static boolean single_connected_2() throws Exception{
		//1.��ʼ����������
		cc = new int[g.getSize()];
		for(int i=0;i<cc.length;i++){
			cc[i] = 0;
		}
		color = new int[g.getSize()];
		parent = new int[g.getSize()];
		for(int a = 0;a<color.length;a++)  color[a] = WHITE;
		for(int a = 0;a<parent.length;a++) parent[a] = -1;
		
		//2.��������ͼ
		System.out.println("=============\n��һ������������ͼ��\n============");
		undirected_g = new Adjacent_List(g.getSize());	//undirected_gΪԭͼg������ͼ������������ͨ��ͼ
		constructUndirectedGraph(g);	//��������ͼ
		undirected_g.printAllEdges();
		System.out.println("=============\n�ڶ�����������ͼ����ͨ��֧\n========");
		//2.������ͼ����ͨ��֧
		calculateConnectedComponents(undirected_g);
		List<List<String>> weak_connected_lists = new ArrayList<List<String>>();
		for(int i=0;i<=weight;i++){
			weak_connected_lists.add(i, new ArrayList<String>());
		}
		for(int i=0;i<cc.length;i++){
			if(weak_connected_lists.get(cc[i]).add(g.getVertexValue(i)));
		}
		System.out.println("����ͨ��֧��");
		for(int i=0;i<weak_connected_lists.size();i++){
			System.out.println(weak_connected_lists.get(i).toString());
		}
		
		//3.��ԭͼ�ֳɶ��������ͨͼ
		System.out.println("=============\n����������������ͨ����ͼ\n========");
		Adjacent_List weak_connected_graph[] = new Adjacent_List[weight+1];//weak_connected_graphΪ���������ͨͼ����Щͼƴ��������ͼG
		for(int i=0;i<weight+1;i++){
			weak_connected_graph[i] = new Adjacent_List(weak_connected_lists.get(i).size());
		}
		for(int u=0;u<g.getSize();u++){
			Iterator<String> iter = g.getListByVertexIndex(u).iterator();
			while(iter.hasNext()){
				String vstr = iter.next();
				weak_connected_graph[cc[u]].addEdge(g.getVertexValue(u), vstr);
			}
		}
		for(int i=0;i<weak_connected_graph.length;i++){
			weak_connected_graph[i].printAllEdges();
			System.out.println("====");
		}
		//4.��ÿ����ͨ����ͼ����SCC�����ж�SCC�ͷ�֧ͼ�Ƿ�Ϊ����ͨ��
		System.out.println("============\n���Ĳ�����ÿ����ͨ����ͼ�����֧ͼ������ÿ��ǿ��ͨ��֧�����Ƿ���ڵ���ͨ\n===========");
		Adjacent_List component_graph[] = new Adjacent_List[weight+1];
		for(int i=0;i<weight+1;i++){	//��ÿ����ͨͼ����SCC
			C5_5 alg = new C5_5(weak_connected_graph[i]);
			//����ͨͼ�еķ�֧ͼ
			component_graph[i] = alg.constructComponentGraph();
			int component_size = alg.getComponentSize();	
			List<List<String>>  componentList =  alg.getComponentList();
			int strong_weight[] = alg.getWeight();
			//ÿ��Ԫ����һ������ͨͼ�е�һ��ǿ��ͨ����ͼ
			Adjacent_List scc_graph[] = new Adjacent_List[component_size];
			for(int j=0;j<scc_graph.length;j++){
				scc_graph[j] = new Adjacent_List(componentList.get(j).size());
			}
			for(int u=0;u<weak_connected_graph[i].getSize();u++){
				Iterator<String> iter = weak_connected_graph[i].getListByVertexIndex(u).iterator();
				while(iter.hasNext()){
					String vstr = iter.next();
					if(strong_weight[u]==strong_weight[weak_connected_graph[i].getVertexIndex(vstr)]){
						scc_graph[strong_weight[u]-1].addEdge(weak_connected_graph[i].getVertexValue(u), vstr);	//���߼��뵽��Ӧ��ǿ��ͨ��֧��
					}
				}
			}
			System.out.println("**************");
			for(int z=0;z<scc_graph.length;z++){
				scc_graph[z].printAllEdges();
				System.out.println("***********");
			}
			
			//��ǿ��ͨ��֧�ж�
			for(int y=0;y<scc_graph.length;y++){
				judgeForwardEdge(scc_graph[y]);
				if(!flag) return false;
			}
			//�Է�֧ͼ�ж�
			Adjacent_List com_g = component_graph[i];
			com_g.printAllEdges();
			int[] indegree = new int[com_g.getSize()];
			for(int j=0;j<indegree.length;j++){
				indegree[j] = 0;
			}
			for(int k=0;k<com_g.getSize();k++){
				Iterator<String> iter = com_g.getListByVertexIndex(k).iterator();
				while(iter.hasNext()){
					String vstr = iter.next();
					int v = com_g.getVertexIndex(vstr);
					indegree[v]++;
				}
			}
			//2.�ҳ����Ϊ0�ĵ�.
			for(int c=0;c<indegree.length;c++){
				if(indegree[c]==0){
					judgeForwardEdge(com_g);
					if(!flag) return false;
				}
			}
			if(!flag) return false;
		}
		return flag;
		
	}

	private static void judgeForwardEdge(Adjacent_List g) {
		for(int i=0;i<g.getSize();i++){
			color[i] = WHITE;
		}
		
		for(int i=0;i<g.getSize();i++){
			if(color[i]==WHITE){
				DFS_VISIT3(g,i);
			}
		}
	}
	private static void DFS_VISIT3(Adjacent_List g, int u) {
		color[u] = GRAY;
		List<String> list = g.getListByVertexIndex(u);
		for(int i=0;i<list.size();i++){
			int v = g.getVertexIndex(list.get(i));
			if(color[v]==WHITE) {
				DFS_VISIT3(g,v);
			}
			else if(color[v]==BLACK){
				//����ǰ��߻��ߺ���ߣ����ǵ���ͨͼ
				flag = false;
			}
		}
		color[u] = BLACK;
	}
	/**
	 * �������Ϊ����ͼ��������ͼ����ͨ��֧
	 * ��ͨ��֧��ΧΪ0~weight,������n����ͨ��֧����weight=n-1
	 */
	private static void calculateConnectedComponents(Adjacent_List g) {
		for(int i=0;i<g.getSize();i++){
			color[i] = WHITE;
			parent[i] = -1;
		}
		weight = -1;
		for(int i=0;i<g.getSize();i++){
			if(color[i]==WHITE){
				weight++;
				DFS_VISIT2(g,i,weight);
			}
		}
	}
	private static void DFS_VISIT2(Adjacent_List g,int u,int weight) {
		color[u] = GRAY;
		cc[u] = weight;
		List<String> list = g.getListByVertexIndex(u);
		for(int i=0;i<list.size();i++){
			int v = g.getVertexIndex(list.get(i));
			if(color[v]==WHITE) {
				DFS_VISIT2(g,v,weight);
			}
		}
		color[u] = BLACK;
	}
	/**
	 * ����ͼ������ͼ
	 * @param g
	 */
	private static void constructUndirectedGraph(Adjacent_List g) {
		for(int i=0;i<g.getSize();i++){
			color[i] = WHITE;
		}
		for(int i=0;i<g.getSize();i++){
			if(color[i]==WHITE)
				DFS_VISIT1(i);
		}
	}
	private static void DFS_VISIT1(int u) {
		color[u] = GRAY;
		List<String> list = g.getListByVertexIndex(u);
		for(int i=0;i<list.size();i++){
			int v = g.getVertexIndex(list.get(i));
			//��(u,v)û�б����ʹ�
			if(parent[u]!=v){
				undirected_g.addEdge(g.getVertexValue(u), list.get(i));
				undirected_g.addEdge(list.get(i), g.getVertexValue(u));
			}
			if(color[v]==WHITE) {
				parent[v] = u;
				DFS_VISIT1(v);
			}
		}
		color[u] = BLACK;
	}
	
}
