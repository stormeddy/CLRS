package elementarygraphalgorithm;

import java.util.List;


public class C5_6 {
//	private static int size;
//	private static Adjacent_List G;
	public static void main(String[] args) throws Exception {
		Adjacent_List g = GraphFactory.getAdjacentListInstance("graph\\22.5_6.txt");
//		C5_6.G = g;
//		System.out.println(g.getEdgeSize());
		C5_6 alg = new C5_6();
		try {
			Adjacent_List result = alg.getMinimunComponentGraph(g);
			result.printAllEdges();
			System.out.println(result.getEdgeSize());
			System.out.println(result.getSize());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public Adjacent_List getMinimunComponentGraph(Adjacent_List G) throws Exception{
		
		//1.��Gp�����ÿ��ǿ��ͨ��֧�ڵı�	O(V+E)
		Adjacent_List min_comp_graph = new Adjacent_List(G.getSize(),0);
//		min_comp_graph=G.clone_adjacent_list();
		C5_5 scc_alg=new C5_5(G);
		Adjacent_List ccg=scc_alg.constructComponentGraph();
		Adjacent_List GT=scc_alg.getGT();
		int cc=scc_alg.getComponentSize();
		//�����������ڵıߣ���һ����
		for(int i=0;i<cc;i++){
			String str=ccg.getVertexValue(i);
			int len=str.length();
			if (len>1) {
				for(int j=0;j<str.length()-1;j++){
					min_comp_graph.addEdge(str.charAt(j)+"", str.charAt(j+1)+"");
				}
				min_comp_graph.addEdge(str.charAt(len-1)+"", str.charAt(0)+"");
			}
		}
		int[] weight=scc_alg.getWeight();
		boolean[][] constructed=new boolean[cc][cc];
		//���Բ�ʹ��constructed��ֱ�Ӷ�component_vertex�ĸ���Ԫ�ر������мӱ߲���
		List<String> component_vertex=scc_alg.getComponenentVertex();
		for(int i=0;i<GT.getSize();i++){
			List<String> list = GT.getListByVertexIndex(i);
			for(String str:list){
				//weight[i]���е�i���Ƕ�Ӧ��GT�еĶ�������
				int v = GT.getVertexIndex(str);
				int v_comp=weight[v];
				int i_comp=weight[i];
				if(i_comp!=v_comp&&constructed[i_comp-1][v_comp-1]==false){
					//��ֹ����ر�
					//ͨ��weight[i]��÷��������������õ��ۺϺ�Ķ���
//					System.out.println(component_vertex.get(i_comp-1)+"-->"+component_vertex.get(v_comp-1));
					//ע��v��i��˳��GTΪԭͼ��ת��
					min_comp_graph.addEdge(component_vertex.get(v_comp-1).charAt(0)+"", component_vertex.get(i_comp-1).charAt(0)+"");
					constructed[i_comp-1][v_comp-1]=true;
				}
			}
		}
//		ccg.printAllEdges();
//		StrongConnectedComponents_Algorithm scc_alg = new StrongConnectedComponents_Algorithm(min_comp_graph);
//		scc_alg.SCC();
//		Adjacent_List GT=scc_alg.getGT();
////		size = scc_alg.getG().getSize();
//		int[] weight = scc_alg.getWeight();
//		int component_size = scc_alg.getComponentSize();
		
		
//		min_comp_graph.addAllVertexFromOtherGraph(G);
//		min_comp_graph.printAllEdges();
//		G.printAllEdges();
//
//		
//		List<List<Integer>> weightList = new ArrayList<List<Integer>>();
//		for(int i=0;i<component_size;i++){
//			weightList.add(new ArrayList<Integer>());
//		}
//		for(int i=0;i<weight.length;i++){
//			weightList.get(weight[i]-1).add(i);
//		}
//		for(int i=0;i<weightList.size();i++){
//			List<Integer> list = weightList.get(i);
//			if(list.size()==1) continue;
//			else{
//				for(int j=0;j<list.size();j++){
//					if(j==list.size()-1){
//						min_comp_graph.getListByVertexIndex(list.get(j)).add(min_comp_graph.getVertexValue(list.get(0)));
//					}
//					else min_comp_graph.getListByVertexIndex(list.get(j)).add(min_comp_graph.getVertexValue(list.get(j+1)));
//				}
//			}
//		}
//		//min_comp_graph.printAllEdges();
//		
//		//2.����G������֧ͼ�ı���ӽ�   O(V+E)
//		
//		for(int i=0;i<G.getSize();i++){
//			List<String> list = G.getListByVertexIndex(i);
//			for(String str:list){
//				int v = G.getVertexIndex(str);
//				if(weight[i]!=weight[v]){
//					min_comp_graph.getListByVertexIndex(i).add(str);
//				}
//			}
//		}
//		//System.out.println("=======");
//		//min_comp_graph.printAllEdges();
//		//3.��ÿ��ǿ��ͨ��֧�еĵ����һ��ķ��ʣ�������һ��ǿ��ͨ��֧��������visited���顣���Ѿ���һ��ǿ��ͨ��֧iָ��ǿ��ͨ��֧j�ıߣ����ٳ������ֱ߾�ɾ����
//		//O(V+E)
//		boolean visited[] = new boolean[component_size+1];
//		for(int i=0;i<weightList.size();i++){
//			for(int j=0;j<visited.length;j++)
//				visited[j] = false;
//			List<Integer> list = weightList.get(i);
//			for(int j=0;j<list.size();j++){
//				int u = list.get(j);
//				Iterator<String> iter = min_comp_graph.getListByVertexIndex(u).iterator();
//				while(iter.hasNext()){
//					String vstr = iter.next();
//					int v = min_comp_graph.getVertexIndex(vstr);
//					if(weight[i]!=weight[v]){
//						if(!visited[weight[v]])
//							visited[weight[v]] = true;
//						else{
//							iter.remove();
//						}
//					}
//				}
//			}
//		}
		return min_comp_graph;
	}
}

